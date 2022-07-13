package ext.ket.cost.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * 
 * @클래스명 : CostCalculatorUtil
 * @작성자 : 황정태
 * @작성일 : 2018. 1. 19.
 * @설명 : 원가 수식 파싱
 * @수정이력 - 수정일,수정자,수정내용
 *
 */
public class CostCalculatorUtil {

    private static CostCalculatorUtil instance;

    private static final String ROUND = "round";
    private static final String CEIL = "ceil";
    private static final String FLOOR = "floor";

    private static String defaultOption = ROUND;
    private static String defaultDecimal = "3";

    public static CostCalculatorUtil getInstance() throws Exception {

        if (instance == null) {
            synchronized (CostCalculatorUtil.class) {
                if (instance == null) {
                    instance = new CostCalculatorUtil();
                }
            }
        }
        return instance;
    }

    private CostCalculatorUtil() {

    }

    public static String calculate(String expression, Map<String, String> values, Map formula) throws Exception {

        String convertExpr = convert(expression, values, formula);

        Object result = eval(convertExpr);

        return result.toString();

    }

    private static String convert(String expression, Map<String, String> values, Map formula) {

        List<String> operandList = new ArrayList<String>();

        Pattern p = Pattern.compile("\\[([^\\]]+)");

        Matcher m = p.matcher(expression);

        while (m.find()) {

            operandList.add(m.group(1));

        }

        for (String operand : operandList) {

            expression = expression.replaceAll("\\[" + operand + "\\]", (String) values.get(operand));

        }

        formula.put("CALCFORMULA", expression);

        return expression;

    }

    @SuppressWarnings("static-access")
    public static Object eval(String expression) throws Exception {

        ScriptEngineManager mgr = new ScriptEngineManager();

        ScriptEngine engine = mgr.getEngineByName("javascript");

        Object result = null;
        Object number = "";

        expression = expression.replaceAll("--", "+");

        if (StringUtils.isEmpty(expression) || "0".equals(expression)) {
            number = "0";
        } else {

            if (expression.indexOf("^") != -1) {// 거듭제곱 처리
                String expr[] = expression.split("\\^");

                String tempStr = "Math.pow(";
                String tempExpr = "";

                for (int i = 0; i < expr.length - 1; i++) {
                    tempExpr += tempStr;
                }

                for (int i = 0; i < expr.length; i++) {
                    if (i == 0) {
                        tempExpr += expr[i] + ",";
                    } else {
                        tempExpr += expr[i] + "),";
                    }
                }
                tempExpr = StringUtils.removeEnd(tempExpr, ",");
                expression = tempExpr;

            }

            result = engine.eval(expression);

            if (String.valueOf(result).equals("null") || String.valueOf(result).equals("NaN")
                    || String.valueOf(result).equals("Infinity") || String.valueOf(result).equals("-Infinity")) {
                result = "0";
            }

            BigDecimal converter = new BigDecimal(String.valueOf(result));// 자바스크립트의 숫자범위한계때문에 BigDeciaml 활용

            converter = converter.setScale(4, BigDecimal.ROUND_UP); // 올림

            result = converter.toPlainString();

            number = result.toString();

            // number = numberTrans(result.toString());

        }

        return number;
    }

    private static String numberTrans(String number) throws Exception {

        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("javascript");

        String expression = "Math.pow(10," + defaultDecimal + ")";
        String count = engine.eval(expression).toString();

        expression = "Math." + defaultOption + "(" + number + "*" + count + ")/" + count;

        number = engine.eval(expression).toString();

        return number;
    }

    public static void main(String[] args) {
        // try {
        //
        // CostCalculatorUtil.eval("(240)*(1+0.04)^((2019-2011)/2)");
        //
        // } catch (Exception e1) {
        // // TODO Auto-generated catch block
        // e1.printStackTrace();
        // }

        Map<String, String> values = new HashMap<String, String>();

        values.put("총중량", "11.170");

        values.put("원재료단가", "1.3");

        values.put("Loss", "-5.32");

        values.put("스크랩판매비", "1.324");

        values.put("포장비", "13.42");

        values.put("재생단가", "52.1");
        Map formula = new HashMap();
        String result = null;
        String exp = "([총중량]*[원재료단가])+([총중량]*[원재료단가]*[Loss])-[스크랩판매비]+[포장비]";
        try {
            result = CostCalculatorUtil.getInstance().calculate(exp, values, formula);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println(result);

    }
}
