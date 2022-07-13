package ext.ket.cost.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import wt.fc.Persistable;
import wt.method.RemoteAccess;
import e3ps.common.code.NumberCode;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import ext.ket.cost.entity.CostAttribute;
import ext.ket.cost.entity.CostFormula;
import ext.ket.cost.entity.CostPart;
import ext.ket.cost.service.CostCacheManager;
import ext.ket.cost.service.CostHelper;

/*********************************************************
 * @description
 * @author dhkim
 * @date 2018. 1. 23. 오전 10:18:14
 * @Pakage ext.ket.cost.util
 * @class CostCalculateUtil
 *********************************************************/
@SuppressWarnings("unused")
public class CostCalculateUtil implements RemoteAccess {

	private static final String ROUND = "round";
	private static final String CEIL = "ceil";
	private static final String FLOOR = "floor";

	private static String defaultOption = ROUND;
	private static String defaultDecimal = "2";

	private static final Logger LOGGER = Logger.getLogger(CostCalculateUtil.class);
	public static CostCalculateUtil manager = new CostCalculateUtil();
	public static ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");

	// DefaultMutableTreeNode currentF = CostTreeUtil.manager.getCostTree(costF);
	/**
	 * <pre>
	 * @description 수식 속성 체크
	 * @author dhkim
	 * @date 2018. 10. 25. 오전 9:35:53
	 * @method checkFormula
	 * @param reqMap
	 * @return Map<String,Object>
	 * @throws Exception
	 * </pre>
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> checkFormula(Map<String, Object> reqMap) throws Exception {

		Map<String, Object> resMap = new HashMap<String, Object>();

		String formula = (String) reqMap.get("formula");
		System.out.println("formula : "+formula);
		List<String> formulaKeys = (List<String>) reqMap.get("formulaKeys");
		List<String> changeKeys = new ArrayList<String>();

		for (int i = 0; i < formulaKeys.size(); i++) {

			String formulaKey = formulaKeys.get(i);

			String attrName = formula.substring(formula.indexOf("[") + 1, formula.indexOf("]"));

			// 저장 안된 수식 객체 스킵
			if (formulaKey.indexOf("ext.ket.cost.entity") < 0)
				continue;

			Persistable obj = CommonUtil.getObject(formulaKey);

			// 속성명 유효성 체크
			if (obj instanceof CostAttribute) {
				CostAttribute attr = (CostAttribute) obj;

				if (!attrName.equals(attr.getName())) {
					attr = CostUtil.manager.getCostAttributeForName(attrName);

					if (attr == null) {
						resMap.put("message", "속성 [" + attrName + "]에 대한 객체 정보가 없습니다.");
						resMap.put("result", false);
						break;
					}

					formulaKeys.set(i, CommonUtil.getOIDString(attr));
					changeKeys.add(attrName);
				}
			}

			formula = formula.substring(formula.indexOf("]") + 1);
		}

		resMap.put("formulaKeys", formulaKeys);
		resMap.put("result", true);

		if (changeKeys.size() > 0) {

			String message = "속성";

			for (String changeKey : changeKeys) {
				message += " [" + changeKey + "],";
			}

			message = StringUtils.removeEnd(message, ",");
			message += " 키값 변경 처리되었습니다.";

			resMap.put("message", message);
		}

		return resMap;
	}

	/**
	 * <pre>
	 * @description 특정 속성 수식 계산
	 * @author dhkim
	 * @date 2018. 10. 16. 오전 11:43:25
	 * @method getAttrCaluateFormula
	 * @param partMap
	 * @param attrCode
	 * @return Map<String,String>
	 * @throws Exception
	 * </pre>
	 */
	public Map<String, Object> getAttrCaluateFormula(Map<String, Object> partMap, String attrCode) throws Exception {

		Map<String, Object> resMap = new HashMap<String, Object>();

		CostAttribute attr = CostUtil.manager.getCostAttribute(attrCode);
		CostFormula costF = CostUtil.manager.getCostFormula(attr, Integer.valueOf((String) partMap.get("formulaVersion")));

		if (costF != null) {
			String[] resultFormula = calculateFormula(costF, partMap);
			String result = (String) partMap.get(CommonUtil.getOIDString(costF));
			if (StringUtil.checkString(resultFormula[0])) {
				resMap.put("resultFormula", resultFormula[0]);
				resMap.put("resultExp", result + " = " + resultFormula[0]);
				resMap.put("keyFormula", "[" + costF.getName() + "] = " + resultFormula[1]);

				System.out.println("keyFormula : " + "[" + costF.getName() + "] = " + resultFormula[1]);
				System.out.println("resultExp : " + result + " = " + resultFormula[0]);
			}
		}

		return resMap;
	}

	/**
	 * <pre>
	 * @description
	 * @author dhkim
	 * @date 2018. 1. 23. 오전 10:15:09
	 * @method calculateTotalCost
	 * @param part
	 * @return CostPart
	 * @throws Exception
	 * </pre>
	 */
	public CostPart calculateTotalCost(CostPart part) throws Exception {

		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("DATATYPE", "COSTFORMULA");
		reqMap.put("deptType", "AUTO");
		reqMap.put("version", String.valueOf(part.getFormulaVersion()));
		LOGGER.setLevel(Level.INFO);

		List<Persistable> rootList = CostHelper.service.getCostRootList(reqMap);

		if (rootList.size() > 0) {

			Map<String, Object> partMap = CostUtil.manager.converObjectToMap(part);

			// LOGGER.info("################## CALCULATE TOTAL COST START ###################################");

			CostFormula costF = (CostFormula) rootList.get(0);
			String[] resultFormula = calculateFormula(costF, partMap);

			// LOGGER.info("[총원가] : " + resultFormula[0] + " = " + partMap.get("totalCostExpr"));
			// LOGGER.info("총원가 : " + partMap.get("totalCostExpr"));

			// 하드코딩 연산
			staticCacluate(partMap);

			CostUtil.manager.convertMapToObject(partMap, part);

			// LOGGER.info("################## CALCULATE TOTAL COST END ###################################");

		}

		return part;
	}

	/**
	 * <pre>
	 * @description  
	 * @author dhkim
	 * @date 2018. 3. 13. 오전 9:29:50
	 * @method calculateTotalCost
	 * @param partMap
	 * @return Map<String,Object>
	 * @throws Exception
	 * </pre>
	 */
	public Map<String, Object> calculateTotalCost(Map<String, Object> partMap) throws Exception {

		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("DATATYPE", "COSTFORMULA");
		reqMap.put("deptType", "AUTO");
		reqMap.put("version", partMap.get("formulaVersion"));
		LOGGER.setLevel(Level.INFO);

		List<Persistable> rootList = CostHelper.service.getCostRootList(reqMap);

		if (rootList.size() > 0) {

			// LOGGER.info("################## CALCULATE TOTAL COST START ###################################");

			CostFormula costF = (CostFormula) rootList.get(0);

			String[] resultFormula = calculateFormula(costF, partMap);
			partMap.put("resultFormula", resultFormula);
			// LOGGER.info("[총원가] : " + resultFormula[0] + " = " + partMap.get("totalCostExpr"));

			Set<String> st = partMap.keySet();
			Iterator<String> it = st.iterator();

			List<String> keyList = new ArrayList<String>();
			// ################# 연산결과 메모리 초기화 START ##########################
			while (it.hasNext()) {
				String key = it.next();
				if (key.indexOf(CostFormula.class.getName()) >= 0) {
					keyList.add(key);
				}
			}
			for (String key : keyList) {
				partMap.remove(key);
			}

			// 하드코딩 연산
			staticCacluate(partMap);
			// LOGGER.info("################## CALCULATE TOTAL COST END ###################################");

		}

		return partMap;
	}

	/**
	 * <pre>
	 * @description 하드코딩 연산처리
	 * @author dhkim
	 * @date 2018. 2. 5. 오전 11:07:18
	 * @method staticCacluate
	 * @param partMap
	 * @throws Exception
	 * </pre>
	 */
	public void staticCacluate(Map<String, Object> partMap) throws Exception {

		// ############## 생산량 (EA) ###########################
		String outputExpr = (String) partMap.get("outputExpr");
		String workHour = (String) partMap.get("workHour");
		String workDay = (String) partMap.get("workDay");
		String workYear = (String) partMap.get("workYear");

		String expression = outputExpr + "*" + workHour + "*" + workDay + "*" + workYear;

		// System.out.println("생산량 (EA) expression ==== " + expression);
		String eaOutput = (String) eval(expression);
		// System.out.println("생산량 (EA) result ==== " + eaOutput);

		partMap.put("eaOutput", eaOutput);

		// ############## CAPA ###########################

		String quantityMax = StringUtils.isEmpty((String) partMap.get("quantityMax")) ? "0" : (String) partMap.get("quantityMax");
		expression = quantityMax + "/" + eaOutput;
		// System.out.println("CAPA expression ==== " + expression);
		String capa = (String) eval("Math.ceil("+expression+")");
		// System.out.println("CAPA result ==== " + capa);

		partMap.put("capa", capa);

		// ############## 제조원가 ###########################
		String materialCostExpr = StringUtil.checkReplaceStr((String) partMap.get("materialCostExpr"), "0"); // 재료비
		String laborCostExpr = StringUtil.checkReplaceStr((String) partMap.get("laborCostExpr"), "0"); // 노무비
		String expenseExpr = StringUtil.checkReplaceStr((String) partMap.get("expenseExpr"), "0"); // 경비

		expression = materialCostExpr + "+" + laborCostExpr + "+" + expenseExpr;
		// System.out.println("제조원가 expression ==== " + expression);
		String mfcCostExpr = (String) eval(expression);// 제조원가
		// System.out.println("제조원가 result ==== " + mfcCostExpr);

		partMap.put("mfcCostExpr", mfcCostExpr);

		String totalCost = (String) partMap.get("totalCostExpr");
		String us = StringUtil.checkReplaceStr((String) partMap.get("us"), "0");

		String mftFactory2 = (String) partMap.get("mftFactory2");
		String mftFactory2Name = "";
		if (StringUtil.checkString(mftFactory2)) {
			NumberCode mft2Code = (NumberCode) CommonUtil.getObject(NumberCode.class.getName() + ":" + mftFactory2);
			mftFactory2Name = StringUtil.checkNull(mft2Code.getName());
		}
		
		if ("무상".equals(mftFactory2Name.trim())) {
		    	String freeRawMaterialCostTotal = (String) CostCalculateUtil.manager.eval(materialCostExpr + "*" + us);
			partMap.put("freeRawMaterialCostTotal", freeRawMaterialCostTotal);
			partMap.put("materialCostExpr", "0");
			totalCost = (String) CostCalculateUtil.manager.eval(totalCost + "-" + materialCostExpr);
			partMap.put("totalCostExpr", totalCost);

			mfcCostExpr = (String) CostCalculateUtil.manager.eval(mfcCostExpr + "-" + materialCostExpr);
			partMap.put("mfcCostExpr", mfcCostExpr);
			partMap.put("mftFactory2Name", "무상");
		}

		// US까지 계산된 총원가
		totalCost = (String) CostCalculateUtil.manager.eval(totalCost + "*" + us);
		partMap.put("totalCost", totalCost);

	}

	/**
	 * <pre>
	 * @description  
	 * @author dhkim
	 * @date 2018. 1. 23. 오전 10:15:46
	 * @method calculateFormula
	 * @param costF
	 * @param partMap
	 * @return String
	 * @throws Exception
	 * </pre>
	 */
	public String[] calculateFormula(CostFormula costF, Map<String, Object> partMap) throws Exception {

		String formulaKeys = costF.getFormulaKeys();
		String formula = costF.getFormula();

		String[] resultFormula = new String[2];
		resultFormula[0] = "";
		resultFormula[1] = "";

		if (formula != null) {

			Map<String, String> values = new HashMap<String, String>();

			if (formulaKeys != null) {

				String[] keys = formulaKeys.split(",");

				for (String key : keys) {

					Persistable obj = CommonUtil.getObject(key);

					if (obj instanceof CostFormula) {

						CostFormula cf = (CostFormula) obj;

						String calcResult = (String) partMap.get(key);

						if (!StringUtil.checkString(calcResult)) {
							calculateFormula(cf, partMap);
							calcResult = (String) partMap.get(key);
						}

						// LOGGER.info(partMap.get("partNo") + " ### [" + cf.getName() + "] : " + partMap.get("CALCFORMULA") + " = " + calcResult);

						String attrOid = cf.getMappingCode();

						if (attrOid != null) {
							CostAttribute ca = (CostAttribute) CommonUtil.getObject(attrOid);
							partMap.put(ca.getCode(), calcResult);
							// LOGGER.info(partMap.get("partNo") + " ### [" + ca.getName() + "," + ca.getCode() + "] : " + calcResult);
						}

						values.put(cf.getName(), calcResult);

					} else {

						CostAttribute attr = (CostAttribute) obj;
						String name = attr.getName();
						String value = (String) partMap.get(attr.getCode());

						value = StringUtil.checkReplaceStr(value, "0");

						values.put(name, value);
					}
				}
			}

			try {

				// System.out.println("산출식 ######### [" + costF.getName() + "] : " + formula + " == " + values);
				String result = calculate(formula, values, partMap);
				partMap.put(CommonUtil.getOIDString(costF), result);
				// partMap.put("CALCRESULT", result);
				resultFormula[0] += (String) partMap.get("CALCFORMULA");
				resultFormula[1] += formula;
				if (costF.getDeptType() != null) {
					partMap.put("totalCostExpr", result);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {

			String costFOid = CommonUtil.getOIDString(costF);
			DefaultMutableTreeNode currentF = CostCacheManager.getCostFItem(costFOid);

			if (currentF == null) {
				currentF = CostTreeUtil.manager.getCostTree(costF);
				CostCacheManager.updateCostFByKey(costFOid, currentF);
			}

			String attrOid = costF.getMappingCode();
			CostAttribute ca = null;
			if (attrOid != null) {
				ca = (CostAttribute) CommonUtil.getObject(attrOid);
			}

			if (currentF.getChildCount() > 0) {

				String partType = StringUtil.checkNull((String) partMap.get("partType"));
				String mftFactory1 = StringUtil.checkNull((String) partMap.get("mftFactory1"));
				String mftFactory2 = StringUtil.checkNull((String) partMap.get("mftFactory2"));
				String[] mftFactory2s = mftFactory2.split(";");

				boolean isCalculate = false;

				F1: for (int i = 0; i < currentF.getChildCount(); i++) {

					DefaultMutableTreeNode child = (DefaultMutableTreeNode) currentF.getChildAt(i);

					CostFormula childF = (CostFormula) child.getUserObject();

					String calcStdMaterial = (String) partMap.get("calcStdMaterial");
					String calcStdLabor = (String) partMap.get("calcStdLabor");
					String calcStdExpense = (String) partMap.get("calcStdExpense");
					String calcStdManage = (String) partMap.get("calcStdManage");
					String calcStdOutPut = (String) partMap.get("calcStdOutPut");

					String calcStdF = StringUtil.checkNull(childF.getCalculationStd());

					if (!(calcStdF.equals(calcStdMaterial) || calcStdF.equals(calcStdLabor) || calcStdF.equals(calcStdExpense) || calcStdF.equals(calcStdManage) || calcStdF
							.equals(calcStdOutPut))) {
						continue;
					}

					String pType = StringUtil.checkNull(childF.getPartType());
					String mFactory1 = StringUtil.checkNull(childF.getMftFactory1());
					String mFactory2 = "";
					if(mFactory2 != null){
					    mFactory2 = childF.getMftFactory2().toString();
					}
					//String mFactory2 = StringUtil.checkNull(childF.getMftFactory2().toString());

					String[] partTypeList = pType.split("\\|");
					String[] mftFactory1List = mFactory1.split("\\|", -1);
					String[] mftFactory2List = mFactory2.split("\\|", -1);

					F2: for (int j = 0; j < partTypeList.length; j++) {
						pType = partTypeList[j];
						mFactory1 = mftFactory1List[j];
						String[] temp = mftFactory2List[j].split(",");
						List<String> tempMFT2 = Arrays.asList(temp);

						if (pType.indexOf(partType) < 0)
							continue;
						if (mFactory1.indexOf(mftFactory1) < 0)
							continue;

						for (int k = 0; k < mftFactory2s.length; k++) {
							if (!tempMFT2.contains(mftFactory2s[k])) {
								continue F2;
							}
						}

						isCalculate = true;
						break;
					}

					if (isCalculate) {
						String[] caclFString = calculateFormula(childF, partMap);

						String calcResult = (String) partMap.get(CommonUtil.getOIDString(childF));
						partMap.put(CommonUtil.getOIDString(costF), calcResult);

						resultFormula[0] += caclFString[0];
						resultFormula[1] += caclFString[1];
						break;
					}
				}

				if (!isCalculate) {
					partMap.put(CommonUtil.getOIDString(costF), "0");
					partMap.put("CALCFORMULA", "0");
				}
			}
		}

		return resultFormula;
	}

	/**
	 * <pre>
	 * @description  
	 * @author neho
	 * @date 2018. 1. 23. 오전 10:16:07
	 * @method calculate
	 * @param expression
	 * @param values
	 * @param formula
	 * @return String
	 * @throws Exception
	 * </pre>
	 */
	public String calculate(String expression, Map<String, String> values, Map<String, Object> formula) throws Exception {

		String convertExpr = convert(expression, values, formula);

		Object result = eval(convertExpr);

		return result.toString();
	}

	/**
	 * <pre>
	 * @description  
	 * @author neho
	 * @date 2018. 1. 23. 오전 10:16:14
	 * @method convert
	 * @param expression
	 * @param values
	 * @param formula
	 * @return String
	 * </pre>
	 */
	private String convert(String expression, Map<String, String> values, Map<String, Object> formula) {

		List<String> operandList = new ArrayList<String>();

		Pattern p = Pattern.compile("\\[([^\\]]+)");

		Matcher m = p.matcher(expression);

		while (m.find()) {

			operandList.add(m.group(1));

		}

		for (String operand : operandList) {
			String value = (String) values.get(operand);
			if (value.startsWith("-")) {
				value = "(" + value + ")";
			}
			expression = expression.replaceAll("\\[" + operand + "\\]", (String) values.get(operand));

		}

		formula.put("CALCFORMULA", expression);

		return expression;

	}

	public static int searchAt(String target, String delim) {
		char[] array_word = new char[target.length()];
		int cnt = 0;
		boolean goFlag = true;
		for (int i = 0; i < array_word.length; i++) {
			String compareChar = Character.toString(target.charAt(i));
			if (compareChar.equals(delim)) {
				goFlag = false;
			}
			if (!goFlag) {
				if (compareChar.equals(")")) {
					cnt = i;
					break;
				}
			}
		}
		return cnt;
	}

	/**
	 * <pre>
	 * @description  
	 * @author neho
	 * @date 2018. 1. 23. 오전 10:16:18
	 * @method eval
	 * @param expression
	 * @return Object
	 * @throws Exception
	 * </pre>
	 */
	public Object eval(String expression) throws Exception {
		return eval(expression, BigDecimal.ROUND_HALF_UP); // 반올림
	}

	public Object eval(String expression, int round) throws Exception {

		Object result = null;
		Object number = "";

		expression = expression.replaceAll("--", "+");

		if (StringUtils.isEmpty(expression) || "0".equals(expression)) {
			number = "0";
		} else {

			while (expression.indexOf("^") != -1) {// 거듭제곱 처리
				String expr[] = expression.split("\\^");

				int start = expr[0].lastIndexOf("(");
				int end = searchAt(expression, "^");

				String tempString = expression.substring(start, end + 1);

				String tempString2 = tempString.replace("^", ",");
				tempString2 = "Math.pow(" + tempString2 + ")";

				expression = expression.replace(tempString, "Math.pow(" + tempString + ")");
				expression = expression.replace("Math.pow(" + tempString + ")", tempString2);
			}

			result = engine.eval(expression);

			if (String.valueOf(result).equals("null") || String.valueOf(result).equals("NaN") || String.valueOf(result).equals("Infinity")
					|| String.valueOf(result).equals("-Infinity")) {
				// LOGGER.info("######## 계산 할 수 없는 수식이 있습니다 ######## " + String.valueOf(result));
				result = "0";
			}

			BigDecimal converter = new BigDecimal(String.valueOf(result));// 자바스크립트의 숫자범위한계때문에 BigDeciaml 활용

			converter = converter.setScale(4, round);

			result = converter.toPlainString();

			number = result.toString();

			// number = numberTrans(result.toString());

			// System.out.println("result : " + result);
			// int start = StringUtils.indexOf(result.toString(), ".");
			// int length = result.toString().length() - 1 - start;
			// System.out.println(".부터 길이 :" + length);
			/*
			 * result += ".toFixed(1)";//정해진 소수점 자리수로 반올림
			 * 
			 * result = engine.eval(result.toString());
			 */
		}

		return number;
	}

	/**
	 * <pre>
	 * @description  
	 * @author neho
	 * @date 2018. 1. 23. 오전 10:17:17
	 * @method numberTrans
	 * @param number
	 * @return String
	 * @throws Exception
	 * </pre>
	 */
	private static String numberTrans(String number) throws Exception {

		String expression = "Math.pow(10," + defaultDecimal + ")";
		String count = engine.eval(expression).toString();

		expression = "Math." + defaultOption + "(" + number + "*" + count + ")/" + count;

		number = engine.eval(expression).toString();

		return number;
	}

	// 기계감가 표준 (올림처리)
	public String getMachineReduceCost(String investReduceCost, String workUseHour, String workUseDay, String workUseYear) throws Exception {
		String exp = "((" + investReduceCost + ")/(" + workUseHour + "*" + workUseDay + "*" + workUseYear + "))";
		Object machineReduceCost = engine.eval(exp);
		BigDecimal bd = new BigDecimal(String.valueOf(machineReduceCost));
		bd = bd.setScale(-1, BigDecimal.ROUND_UP);

		return (bd.toPlainString()).toString();
	}

	// @SuppressWarnings("rawtypes")
	// public static void test() throws Exception {
	//
	// if (!RemoteMethodServer.ServerFlag) {
	// Class c[] = new Class[] { String.class };
	// Object o[] = new Object[] { };
	//
	// RemoteMethodServer.getDefault().invoke("test",
	// CostCalculateUtil.class.getName(), null, c, o);
	// return;
	// }
	// //TEST
	// CostPart part = (CostPart)
	// CommonUtil.getObject("ext.ket.cost.entity.CostPart:993575616");
	// CostCalculateUtil.manager.calculateTotalCost(part);
	//
	// }
	//
	// public static void main(String args[]) {
	//
	// try {
	// RemoteMethodServer server = RemoteMethodServer.getDefault();
	//
	// server.setAuthenticator(AuthHandler.getMethodAuthenticator("wcadmin"));
	//
	// LOGGER.setLevel(Level.INFO);
	//
	// //LOGGER.info("############TEST START####################");
	// test();
	// //LOGGER.info("############TEST COMPLETE####################");
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

}
