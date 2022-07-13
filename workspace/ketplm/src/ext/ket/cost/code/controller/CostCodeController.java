package ext.ket.cost.code.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.message.KETMessageService;
import e3ps.common.util.CommonUtil;
import ext.ket.cost.code.service.CostCodeHelper;
import ext.ket.cost.entity.CSInfo;
import ext.ket.cost.entity.CSInfoItemDTO;
import ext.ket.cost.entity.CostPart;
import ext.ket.cost.service.CostCacheManager;
import ext.ket.cost.util.CostUtil;
import ext.ket.shared.log.Kogger;

/*********************************************************
 * @description
 * @author dhkim
 * @date 2017. 11. 29. 오후 1:28:58
 * @Pakage ext.ket.project.cost.controller
 * @class CostController
 *********************************************************/
@Controller
@RequestMapping("/cost/code/*")
public class CostCodeController {

	private static final Logger LOGGER = Logger.getLogger(CostCodeController.class);

	/**
	 * <pre>
	 * @description  
	 * @author dhkim
	 * @date 2017. 12. 5. 오전 11:11:20
	 * @method costBomEditor
	 * @param model
	 * @return Model
	 * @throws Exception
	 * </pre>
	 */
	@SuppressWarnings({ "unchecked", "deprecation", "static-access" })
	@RequestMapping("/costPartTypeEditor")
	public Model costPartTypeEditor(Model model) throws Exception {
		LOGGER.info("######## CALL costPartTypeEditor ########");
		List<NumberCode> codeList = NumberCodeHelper.manager.getNumberCodeList("MFTFACTORY");

		JSONArray mftFactoryList = new JSONArray();

		for (NumberCode code : codeList) {

			JSONObject obj = new JSONObject();

			obj.put("code", CommonUtil.getOIDLongValue(code));
			obj.put("name", code.getName());

			if (code.getParent() != null) {
				obj.put("pCode", CommonUtil.getOIDLongValue(code.getParent()));
			}

			mftFactoryList.put(obj);
		}
		//
		// Map<String, String> monetaryMap = NumberCodeHelper.manager.getNumberCode("MONETARYUNIT");
		//
		// Set<String> st = monetaryMap.keySet();
		// Iterator<String> it = st.iterator();
		//
		// String monetary = "";
		// while (it.hasNext()) {
		// monetary = "|" + it.next() + monetary;
		// }

		codeList = NumberCodeHelper.manager.getNumberCodeList("TASKTYPE");
		String eNumTaskType = "";
		String eNumKeysTaskType = "";

		for (NumberCode code : codeList) {
			if (code.getParent() != null) {
				if ("COST".equals(code.getParent().getCode())) {
					eNumTaskType += "|" + code.getName();
					eNumKeysTaskType += "|" + code.getCode();
				}

			}

		}

		// eNumTaskType = StringUtils.removeStart(eNumTaskType, "|");
		// eNumTaskType = "||"+eNumTaskType;
		//
		// eNumKeysTaskType = StringUtils.removeStart(eNumKeysTaskType, "|");
		// eNumKeysTaskType = "||"+eNumKeysTaskType;

		model.addAttribute("eNumTaskType", eNumTaskType);
		model.addAttribute("eNumKeysTaskType", eNumKeysTaskType);

		model.addAttribute("mftFactoryList", mftFactoryList.toString());

		//
		//
		// model.addAttribute("monetary", monetary);
		// model.addAttribute("mftFactoryList", mftFactoryList.toString());

		return model;
	}

	/**
	 * <pre>
	 * @description   
	 * @author dhkim
	 * @date 2017. 11. 30. 오후 1:53:25
	 * @method saveCostPartTypeLink
	 * @param reqMap
	 * @return Map<String,Object>
	 * </pre>
	 */
	@ResponseBody
	@RequestMapping("/saveCostPartTypeLink")
	public Map<String, Object> saveCostPartTypeLink(@RequestParam Map<String, Object> reqMap) {

		Map<String, Object> resMap = new HashMap<String, Object>();
		Map<String, Object> ioMap = new HashMap<String, Object>();

		try {
			resMap = CostCodeHelper.service.saveCostPartTypeLink(reqMap);

			ioMap.put("Result", 0);
			ioMap.put("Reload", 1);
			resMap.put("IO", ioMap);

		} catch (Exception e) {
			ioMap.put("Result", -1);
			resMap.put("IO", ioMap);
			e.printStackTrace();
		}

		return resMap;
	}

	/**
	 * <pre>
	 * @description  
	 * @author dhkim
	 * @date 2017. 12. 8. 오후 12:12:08
	 * @method costBomEditorData
	 * @param reqMap
	 * @return Map<String,Object>
	 * </pre>
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/costPartTypeLinkList")
	public Map<String, Object> costPartTypeLinkList(@RequestParam Map<String, Object> reqMap) {

		Map<String, Object> result = new HashMap<String, Object>();
		try {

			List<Object> body = new ArrayList<Object>();

			List<Map<String, Object>> data = CostCodeHelper.service.getCostPartTypeLinkList();
			//List<Map<String, Object>> data = CostCacheManager.getCostPTItem("PARTTYPELINKLIST");

			body.add(data);

			result.put("Body", body);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	@SuppressWarnings({ "unchecked", "deprecation", "static-access" })
	@RequestMapping("/costPartTypeLink")
	public Model costPartTypeLink(Model model) throws Exception {

		LOGGER.info("######## CALL costPartTypeLink ########");

		List<NumberCode> codeList = NumberCodeHelper.manager.getNumberCodeList("MFTFACTORY");

		JSONArray mftFactoryList = new JSONArray();

		for (NumberCode code : codeList) {

			JSONObject obj = new JSONObject();

			obj.put("code", CommonUtil.getOIDLongValue(code));
			obj.put("name", code.getName());

			if (code.getParent() != null) {
				obj.put("pCode", CommonUtil.getOIDLongValue(code.getParent()));
			}

			mftFactoryList.put(obj);
		}

		Map<String, String> monetaryMap = NumberCodeHelper.manager.getNumberCode("MONETARYUNIT");

		Set<String> st = monetaryMap.keySet();
		Iterator<String> it = st.iterator();

		String monetary = "";
		while (it.hasNext()) {
			monetary = "|" + it.next() + monetary;
		}
		model.addAttribute("monetary", monetary);
		model.addAttribute("mftFactoryList", mftFactoryList.toString());

		return model;
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/getCostCodeByPartType")
	public String getCostCodeByPartType(@RequestBody Map<String, Object> reqMap) {
	    	JSONArray jsonData = null;
		JSONArray rvJsonData = null;
		List<Map<String, Object>> list = null;

		String id = "";
		try {

		    List<Map<String, String>> paramData = (List<Map<String, String>>) reqMap.get("paramData");

		    for (Map<String, String> paramMap : paramData) {
			id += "'" + CommonUtil.getOIDLongValue2Str(paramMap.get("id")) + "',";
		    }

		    id = StringUtils.removeEnd(id, ",");

		    list = CostCodeHelper.service.getCostFactoryTreeInfoByPartType(id);

		    rvJsonData = new JSONArray(list.toArray());
		} catch (JSONException e) {
		    e.printStackTrace();
		} catch (Exception e) {
		    e.printStackTrace();
		}
		return rvJsonData.toJSONString();

	}

	@ResponseBody
	@RequestMapping("/purchasePriceSapInterface")
	public String purchasePriceSapInterface() {
		String rvMsg = "S";
		try {
			CostCodeHelper.service.purchasePriceSapInterface();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rvMsg = "F";
		}
		return rvMsg;
	}

	@RequestMapping("/costReducePopup")
	public Model costReducePopup(Model model) throws Exception {

		LOGGER.info("######## CALL costReducePopup ########");

		List<NumberCode> codeList = NumberCodeHelper.manager.getNumberCodeList("MFTFACTORY");
		String eNumFty = "";
		String eNumKeysFty = "";
		for (NumberCode code : codeList) {

			if (code.getParent() == null) {
				eNumFty += "|" + code.getName();
				eNumKeysFty += "|" + CommonUtil.getOIDLongValue(code);
			}
		}

		codeList = NumberCodeHelper.manager.getNumberCodeList("COSTMAKING");
		String eNumMaking = "";
		String eNumKeysMaking = "";
		for (NumberCode code : codeList) {

			if (code.getParent() == null) {
				eNumMaking += "|" + code.getName();
				eNumKeysMaking += "|" + CommonUtil.getOIDLongValue(code);
			}
		}

		model.addAttribute("eNumFty", eNumFty);
		model.addAttribute("eNumKeysFty", eNumKeysFty);
		model.addAttribute("eNumMaking", eNumMaking);
		model.addAttribute("eNumKeysMaking", eNumKeysMaking);

		return model;
	}

	@RequestMapping("/CostLogisticsMtrPopup")
	public Model CostLogisticsMtrPopup(Model model) throws Exception {

		LOGGER.info("######## CALL CostLogisticsMtrPopup ########");

		List<NumberCode> codeList = NumberCodeHelper.manager.getNumberCodeList("MFTFACTORY");
		String eNumFty = "";
		String eNumKeysFty = "";
		String eNumExRate = "";
		String eNumKeysExRate = "";

		for (NumberCode code : codeList) {

			if (code.getParent() == null) {
				eNumFty += "|" + code.getName();
				eNumKeysFty += "|" + CommonUtil.getOIDLongValue(code);
			}
		}

		eNumFty = StringUtils.removeStart(eNumFty, "|");
		eNumFty = "||" + eNumFty;

		eNumKeysFty = StringUtils.removeStart(eNumKeysFty, "|");
		eNumKeysFty = "||" + eNumKeysFty;

		CSInfo csInfo = CostUtil.manager.getLatestCSInfo("EXRATE");

		if (csInfo != null) {
			List<CSInfoItemDTO> csInfoItemList = CostUtil.manager.getCSInfoItemList(csInfo);
			for (CSInfoItemDTO dto : csInfoItemList) {
				eNumExRate += "|" + dto.getValue1CodeName();
				eNumKeysExRate += "|" + dto.getValue2();
			}

		}
		eNumExRate = StringUtils.removeStart(eNumExRate, "|");
		eNumExRate = "||" + eNumExRate;

		eNumKeysExRate = StringUtils.removeStart(eNumKeysExRate, "|");
		eNumKeysExRate = "||" + eNumKeysExRate;

		JSONArray mftFactoryList = new JSONArray();

		for (NumberCode code : codeList) {

			JSONObject obj = new JSONObject();

			obj.put("code", CommonUtil.getOIDLongValue(code));
			obj.put("name", code.getName());

			if (code.getParent() != null) {
				obj.put("pCode", CommonUtil.getOIDLongValue(code.getParent()));
				obj.put("pName", code.getParent().getName());
			}

			mftFactoryList.put(obj);
		}

		model.addAttribute("mftFactoryList", mftFactoryList.toString());

		model.addAttribute("eNumFty", eNumFty);
		model.addAttribute("eNumKeysFty", eNumKeysFty);

		model.addAttribute("eNumExRate", eNumExRate);
		model.addAttribute("eNumKeysExRate", eNumKeysExRate);

		return model;
	}

	@RequestMapping("/CostLogisticsPartPopup")
	public Model CostLogisticsPartPopup(Model model) throws Exception {

		LOGGER.info("######## CALL CostLogisticsPartPopup ########");

		List<NumberCode> codeList = NumberCodeHelper.manager.getNumberCodeList("MFTFACTORY");
		String eNumFty = "";
		String eNumKeysFty = "";
		String eNumExRate = "";
		String eNumKeysExRate = "";

		for (NumberCode code : codeList) {

			if (code.getParent() == null) {
				eNumFty += "|" + code.getName();
				eNumKeysFty += "|" + CommonUtil.getOIDLongValue(code);
			}
		}

		eNumFty = StringUtils.removeStart(eNumFty, "|");
		eNumFty = "||" + eNumFty;

		eNumKeysFty = StringUtils.removeStart(eNumKeysFty, "|");
		eNumKeysFty = "||" + eNumKeysFty;

		CSInfo csInfo = CostUtil.manager.getLatestCSInfo("EXRATE");

		if (csInfo != null) {
			List<CSInfoItemDTO> csInfoItemList = CostUtil.manager.getCSInfoItemList(csInfo);
			for (CSInfoItemDTO dto : csInfoItemList) {
				eNumExRate += "|" + dto.getValue1CodeName();
				eNumKeysExRate += "|" + dto.getValue2();
			}

		}
		eNumExRate = StringUtils.removeStart(eNumExRate, "|");
		eNumExRate = "||" + eNumExRate;

		eNumKeysExRate = StringUtils.removeStart(eNumKeysExRate, "|");
		eNumKeysExRate = "||" + eNumKeysExRate;

		JSONArray mftFactoryList = new JSONArray();

		for (NumberCode code : codeList) {

			JSONObject obj = new JSONObject();

			obj.put("code", CommonUtil.getOIDLongValue(code));
			obj.put("name", code.getName());

			if (code.getParent() != null) {
				obj.put("pCode", CommonUtil.getOIDLongValue(code.getParent()));
				obj.put("pName", code.getParent().getName());
			}

			mftFactoryList.put(obj);
		}

		model.addAttribute("mftFactoryList", mftFactoryList.toString());

		model.addAttribute("eNumFty", eNumFty);
		model.addAttribute("eNumKeysFty", eNumKeysFty);

		model.addAttribute("eNumExRate", eNumExRate);
		model.addAttribute("eNumKeysExRate", eNumKeysExRate);

		return model;
	}

	@RequestMapping("/CostPackingPopup")
	public Model CostPackingPopup(String oid, String editMode, Model model) throws Exception {

		LOGGER.info("######## CALL CostPackingPopup ########");

		List<NumberCode> codeList = NumberCodeHelper.manager.getNumberCodeList("COSTPACKING");
		String eNumPacking = "";
		String eNumKeysPacking = "";
		String eNumExRate = "";
		String eNumKeysExRate = "";

		for (NumberCode code : codeList) {
			eNumPacking += "|" + code.getName();
			eNumKeysPacking += "|" + CommonUtil.getOIDLongValue(code);
		}

		eNumPacking = StringUtils.removeStart(eNumPacking, "|");
		eNumPacking = "||" + eNumPacking;

		eNumKeysPacking = StringUtils.removeStart(eNumKeysPacking, "|");
		eNumKeysPacking = "||" + eNumKeysPacking;

		Map<String, String> rateMap = CostCodeHelper.service.getMyExRate(oid);

		// CostCodeHelper.service.syncPartByMyCurrency("ext.ket.cost.entity.PartList:993742075", "0");

		eNumExRate = rateMap.get("eNumExRate");
		eNumKeysExRate = rateMap.get("eNumKeysExRate");

		CostPart part = (CostPart) CommonUtil.getObject(oid);

		// ProductMaster master = (ProductMaster)CommonUtil.getObject(productOid);

		String ProductMaxQty = ""; // 제품 최대 물량
		String ProductTotalQty = "";// 제품 총물량
		String Productus = ""; // 제품 u/s
		String packBoxUnit = ""; // 제품 포장사양(box)
		String packPalletUnit = "";// 제품 포장사양(pallet)
		String packUnitPriceSum = "";// 제품 개별포장비 합계

		boolean isOpen = false;

		if (part != null) {
			Productus = part.getUs();
			packBoxUnit = part.getPackBoxUnit();
			packPalletUnit = part.getPackPalletUnit();
			packUnitPriceSum = part.getPackUnitPriceSum();
			ProductMaxQty = part.getQuantityMax();
			ProductTotalQty = part.getQuantityTotal();
			isOpen = true;
		}

		// model.addAttribute("productOid", productOid);
		model.addAttribute("partOid", oid);
		model.addAttribute("isOpen", isOpen);
		model.addAttribute("packBoxUnit", packBoxUnit);
		model.addAttribute("packPalletUnit", packPalletUnit);
		model.addAttribute("packUnitPriceSum", packUnitPriceSum);
		model.addAttribute("Productus", Productus);
		model.addAttribute("ProductMaxQty", ProductMaxQty);
		model.addAttribute("ProductTotalQty", ProductTotalQty);

		model.addAttribute("eNumPacking", eNumPacking);
		model.addAttribute("eNumKeysPacking", eNumKeysPacking);

		model.addAttribute("eNumExRate", eNumExRate);
		model.addAttribute("eNumKeysExRate", eNumKeysExRate);
		model.addAttribute("editMode", editMode);

		return model;
	}

	@RequestMapping("/CostMaterialPopup")
	public Model CostMaterialPopup(String oid, Model model) throws Exception {
		String partOid = oid;
		boolean isOpen = false;

		CostPart part = (CostPart) CommonUtil.getObject(oid);
		if (part != null) {
			isOpen = true;
		}

		model.addAttribute("isOpen", isOpen);
		model.addAttribute("partOid", partOid);
		return model;
	}

	@RequestMapping("/CostEtcInvestPopup")
	public Model CostEtcInvestPopup(String oid, String editMode, Model model) throws Exception {
		String partOid = oid;
		boolean isOpen = false;

		CostPart part = (CostPart) CommonUtil.getObject(oid);
		if (part != null) {
			isOpen = true;
		}

		List<NumberCode> codeList = NumberCodeHelper.manager.getNumberCodeList("MONETARYUNIT");
		String eNum = "";
		String eNumKeys = "";

		for (NumberCode code : codeList) {
			eNum += "|" + code.getName();
			eNumKeys += "|" + code.getCode();
		}

		model.addAttribute("eNumUnit", eNum);
		model.addAttribute("eNumKeysUnit", eNumKeys);

		codeList = NumberCodeHelper.manager.getNumberCodeList("COSTMAKING");
		eNum = "";
		eNumKeys = "";

		for (NumberCode code : codeList) {
			eNum += "|" + code.getName();
			eNumKeys += "|" + code.getCode();
		}

		model.addAttribute("eNumMaking", eNum);
		model.addAttribute("eNumKeysMaking", eNumKeys);

		model.addAttribute("isOpen", isOpen);
		model.addAttribute("partOid", partOid);
		model.addAttribute("editMode", editMode);
		return model;
	}

	@RequestMapping("/CostQuantityPopup")
	public Model CostQuantityPopup(String oid, String version, String us, String editMode, Model model) throws Exception {
		// CostCodeHelper.service.syncPartByCodeMaster("");
		String productOid = oid;
		boolean isOpen = false;

		// ProductMaster master = (ProductMaster)CommonUtil.getObject(oid);
		CostPart part = (CostPart) CommonUtil.getObject(oid);
		// CostCodeHelper.service.syncPartByMyCurrency("ext.ket.cost.entity.PartList:993742075", "0");
		if (part != null) {
			isOpen = true;
		}
		String prodUS = "1";

		if (StringUtils.isNotEmpty(us)) {
			prodUS = us;
		}

		String maxVersion = CostCodeHelper.service.QtyMaxVersion(oid);

		if (StringUtils.isEmpty(version)) {
			version = maxVersion;
		}
		Map<String, String> qty = new HashMap<String, String>();
		String payPlace = part.getPayPlace();
		// String sopYear = part.getSopYear();
		// String deliverName = part.getDeliverName();

		// String disposableCr = part.getDisposableCr(); //CR 적용율
		// String disposableApplyYear = part.getDisposableApplyYear(); //CR 적용년수

		qty.put("payPlace", payPlace);
		qty.put("prodUS", prodUS);
		// qty.put("sopYear", sopYear);
		qty.put("version", version);
		qty.put("maxVersion", maxVersion);
		// qty.put("deliverName", deliverName);
		// qty.put("disposableCr", disposableCr);
		// qty.put("disposableApplyYear", disposableApplyYear);

		model.addAttribute("qty", qty);
		model.addAttribute("isOpen", isOpen);
		model.addAttribute("productOid", productOid);
		model.addAttribute("editMode", editMode);
		return model;
	}

	@ResponseBody
	@RequestMapping("/costCodeGridData")
	public Map<String, Object> costTreeGridData(@RequestParam Map<String, Object> reqMap) {

		LOGGER.info("########################## costCodeGridData call ##############");

		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<Object> body = new ArrayList<Object>();

			List<Map<String, Object>> data = CostCodeHelper.service.getCostGridData(reqMap);
			body.add(data);

			result.put("Body", body);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	@ResponseBody
	@RequestMapping("/saveCostReduceCode")
	public Map<String, Object> saveCostReduceCode(@RequestParam Map<String, Object> reqMap) {

		Map<String, Object> resMap = new HashMap<String, Object>();
		Map<String, Object> ioMap = new HashMap<String, Object>();

		try {
			resMap = CostCodeHelper.service.saveCostReduceCode(reqMap);

			ioMap.put("Result", 0);
			ioMap.put("Reload", 1);
			resMap.put("IO", ioMap);

		} catch (Exception e) {
			ioMap.put("Result", -1);
			resMap.put("IO", ioMap);
			e.printStackTrace();
		}

		return resMap;
	}

	@ResponseBody
	@RequestMapping("/saveCostLogiticsCode")
	public Map<String, Object> saveCostLogiticsCode(@RequestParam Map<String, Object> reqMap) {

		Map<String, Object> resMap = new HashMap<String, Object>();
		Map<String, Object> ioMap = new HashMap<String, Object>();

		try {
			resMap = CostCodeHelper.service.saveCostLogiticsCode(reqMap);

			ioMap.put("Result", 0);
			ioMap.put("Reload", 1);
			resMap.put("IO", ioMap);

		} catch (Exception e) {
			ioMap.put("Result", -1);
			resMap.put("IO", ioMap);
			e.printStackTrace();
		}

		return resMap;
	}

	@ResponseBody
	@RequestMapping("/saveCostPacking")
	public Map<String, Object> saveCostPacking(@RequestParam Map<String, Object> reqMap) {

		Map<String, Object> resMap = new HashMap<String, Object>();
		Map<String, Object> ioMap = new HashMap<String, Object>();

		try {
			resMap = CostCodeHelper.service.saveCostPacking(reqMap);

			ioMap.put("Result", 0);
			ioMap.put("Reload", 1);
			resMap.put("IO", ioMap);

		} catch (Exception e) {
			ioMap.put("Result", -1);
			resMap.put("IO", ioMap);
			e.printStackTrace();
		}

		return resMap;
	}

	@ResponseBody
	@RequestMapping("/saveCostMaterial")
	public Map<String, Object> saveCostMaterial(@RequestParam Map<String, Object> reqMap) {

		Map<String, Object> resMap = new HashMap<String, Object>();
		Map<String, Object> ioMap = new HashMap<String, Object>();

		try {
			resMap = CostCodeHelper.service.saveCostMaterial(reqMap);

			ioMap.put("Result", 0);
			ioMap.put("Reload", 1);
			resMap.put("IO", ioMap);

		} catch (Exception e) {
			ioMap.put("Result", -1);
			resMap.put("IO", ioMap);
			e.printStackTrace();
		}

		return resMap;
	}

	@ResponseBody
	@RequestMapping("/saveCostEtcInvest")
	public Map<String, Object> saveCostEtcInvest(@RequestParam Map<String, Object> reqMap) {

		Map<String, Object> resMap = new HashMap<String, Object>();
		Map<String, Object> ioMap = new HashMap<String, Object>();

		try {
			resMap = CostCodeHelper.service.saveCostEtcInvest(reqMap);

			ioMap.put("Result", 0);
			ioMap.put("Reload", 1);
			resMap.put("IO", ioMap);

		} catch (Exception e) {
			ioMap.put("Result", -1);
			resMap.put("IO", ioMap);
			e.printStackTrace();
		}

		return resMap;
	}

	@ResponseBody
	@RequestMapping("/saveCostQty")
	public Map<String, Object> saveCostQty(@RequestParam Map<String, Object> reqMap) {

		Map<String, Object> resMap = new HashMap<String, Object>();
		Map<String, Object> ioMap = new HashMap<String, Object>();

		try {
			resMap = CostCodeHelper.service.saveCostQty(reqMap);

			ioMap.put("Result", 0);
			ioMap.put("Reload", 1);
			resMap.put("IO", ioMap);

		} catch (Exception e) {
			ioMap.put("Result", -1);
			resMap.put("IO", ioMap);
			e.printStackTrace();
		}

		return resMap;
	}

	@RequestMapping("/costQtyCaseCreate")
	public void costQtyCaseCreate(@RequestParam Map<String, Object> reqMap, HttpServletResponse response) throws Exception {

		String oid = (String) reqMap.get("productOid");

		reqMap.put("oid", oid);
		String msg = KETMessageService.getMessageService().getString("e3ps.message.ket_message", "02454");/* "저장되었습니다." */

		try {
			reqMap = CostCodeHelper.service.costQtyCaseCreate(reqMap);
		} catch (Exception e) {
			msg = "오류가 발생했습니다.관리자에게 문의바랍니다.";
		}

		response.setContentType("text/html;charset=KSC5601");
		PrintWriter pwriter = response.getWriter();
		String str = "\n <script language=\"javascript\">\n alert(\"" + msg + "\");";
		str += "\n parent.location.href='/plm/ext/cost/code/CostQuantityPopup.do?oid=" + oid + "&version=" + (String) reqMap.get("version") + "';" + "\n </script>";

		pwriter.println(str);
		pwriter.close();
		return;
	}

	@RequestMapping("/costQtyCaseDelete")
	public void costQtyCaseDelete(@RequestParam Map<String, Object> reqMap, HttpServletResponse response) throws Exception {

		String oid = (String) reqMap.get("productOid");

		reqMap.put("oid", oid);
		String msg = "삭제되었습니다.";
		String version = "";
		try {
			reqMap = CostCodeHelper.service.costQtyCaseDelete(reqMap);
			version = CostCodeHelper.service.QtyMaxVersion(oid);
		} catch (Exception e) {
			msg = "오류가 발생했습니다.관리자에게 문의바랍니다.";
		}

		response.setContentType("text/html;charset=KSC5601");
		PrintWriter pwriter = response.getWriter();
		String str = "\n <script language=\"javascript\">\n alert(\"" + msg + "\");";
		str += "\n parent.location.href='/plm/ext/cost/code/CostQuantityPopup.do?oid=" + oid + "&version=" + version + "';" + "\n </script>";

		pwriter.println(str);
		pwriter.close();
		return;
	}

	@RequestMapping("/syncPartByCodeMaster")
	@ResponseBody
	public Map<String, Object> syncPartByCodeMaster(String partListOid, String version, String subVersion, String partOid) {

		Map<String, Object> resMap = new HashMap<String, Object>();

		try {
			CostCodeHelper.service.syncPartByCodeMaster(partListOid, version, subVersion, partOid);

			// CostCodeHelper.service.syncCost(partListOid, version);

			resMap.put("message", "동기화가 완료되었습니다.");
			resMap.put("result", true);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
			resMap.put("message", e.getLocalizedMessage());
			resMap.put("result", false);
		}

		return resMap;
	}

	@RequestMapping("/costAddFomulaForm")
	public Model costAddFomulaForm(Model model) throws Exception {

		model.addAttribute("isOpen", "");
		model.addAttribute("partOid", "");

		return model;
	}

	@RequestMapping("/getCostFomulaTreeList")
	@ResponseBody
	public Map<String, Object> getCostFomulaTreeList(Model model) throws Exception {

		Map<String, Object> resMap = new HashMap<String, Object>();
		try {
			ArrayList<Map<String, Object>> attrData = CostCodeHelper.service.getCostFomulaTreeList();

			resMap.put("treeData", attrData);
			resMap.put("result", true);
		} catch (Exception e) {
			resMap.put("message", e.getLocalizedMessage());
			resMap.put("result", false);
		}

		return resMap;
	}

	@SuppressWarnings({ "unchecked", "deprecation", "static-access" })
	@RequestMapping("/costWorkTimePopup")
	public Model costWorkTimePopup(Model model) throws Exception {

		LOGGER.info("######## CALL costWorkTimePopup ########");

		List<NumberCode> codeList = NumberCodeHelper.manager.getNumberCodeList("MFTFACTORY");

		JSONArray mftFactoryList = new JSONArray();

		for (NumberCode code : codeList) {

			JSONObject obj = new JSONObject();

			obj.put("code", CommonUtil.getOIDLongValue(code));
			obj.put("name", code.getName());

			if (code.getParent() != null) {
				obj.put("pCode", CommonUtil.getOIDLongValue(code.getParent()));
				obj.put("pName", code.getParent().getName());
			}

			mftFactoryList.put(obj);
		}

		model.addAttribute("mftFactoryList", mftFactoryList.toString());

		return model;
	}

	@ResponseBody
	@RequestMapping("/costWorkTimeList")
	public Map<String, Object> costWorkTimeList(@RequestParam Map<String, Object> reqMap) {

		Map<String, Object> result = new HashMap<String, Object>();
		try {

			List<Object> body = new ArrayList<Object>();

			List<Map<String, Object>> data = CostCodeHelper.service.getCostWorkTimeList();
			body.add(data);

			result.put("Body", body);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	@ResponseBody
	@RequestMapping("/saveCostWorkTime")
	public Map<String, Object> saveCostWorkTime(@RequestParam Map<String, Object> reqMap) {

		Map<String, Object> resMap = new HashMap<String, Object>();
		Map<String, Object> ioMap = new HashMap<String, Object>();

		try {
			resMap = CostCodeHelper.service.saveCostWorkTime(reqMap);

			ioMap.put("Result", 0);
			ioMap.put("Reload", 1);
			resMap.put("IO", ioMap);

		} catch (Exception e) {
			ioMap.put("Result", -1);
			resMap.put("IO", ioMap);
			e.printStackTrace();
		}

		return resMap;
	}

}