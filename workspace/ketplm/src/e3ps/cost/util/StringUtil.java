package e3ps.cost.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

public final class StringUtil {
    /**
     * 객체 생성을 방지하기 위해서 디폴트 생성자를 Private로 선언
     */
    private StringUtil() {
    }

    /**
     * 인자값이 null 인경우 공백을 리턴한다.
     * 
     * @param str
     *            입력스트링
     */
    public static String checkNull(String str) {
	if (str == null)
	    return "";
	return str;
    }

    public static String checkNullZero(String str) {
	if (str == null || "".equals(str))
	    return "0";
	return str;
    }

    /**
     * 인자값이 null 이거나 공백인경우 Double 0을 리턴한다.
     * 
     * @param str
     *            입력스트링
     */
    public static Double nullDouble(String str) {
	if (str == null || "".equals(str) || "NaN".equals(str) || "Infinity".equals(str)) {
	    return Double.parseDouble("0");
	}
	str = str.replaceAll(",", "");
	return Double.parseDouble(str);
    }

    /* 정수이면 반올림 없이 그대로 리턴,실수면 반올림하여 리턴 */
    public static String Roundformat(String str, int de) {// 값, 반올림할 자리수

	if (str == null || "".equals(str) || "NaN".equals(str) || "Infinity".equals(str)) {
	    return "0";
	}
	str = str.replaceAll(",", "");
	if (str.indexOf(".") == -1) {
	    return str;
	} else {
	    if (de == 0) {
		if (str.equals("0.0")) {
		    return str.substring(0, 1);
		} else {
		    return str;
		}
	    } else {
		return Double.toString(new java.math.BigDecimal(str).setScale(de, BigDecimal.ROUND_HALF_UP).doubleValue());
	    }
	}
    }

    public static String DoubleFormat(String sdb, String won, int per, int de) { // 값, 금액, %의 *100, 소숫점뒷자리
	String result = "";
	Double db = 0.0;
	if (per == 100) {
	    db = nullDouble(sdb) * 100;
	} else {
	    db = nullDouble(sdb);
	}
	if (!Double.toString(db).equals("0.0") && !Double.toString(db).equals("0")) {
	    result = String.format("%,." + de + "f", db) + " " + won;
	} else {
	    result = "";
	}

	return result;
    }

    // public static String DoubleFormat(String sdb, String won){
    // String shap = "#,###";
    // String result = "";
    // Double db = nullDouble(sdb);
    // if(db > 0){
    // NumberFormat nf = new DecimalFormat(shap);
    // result = nf.format(db)+won;
    // }else{
    // result = "";
    // }
    // return result;
    // }
    /**
     * @see getParameter(HttpServletRequest request, String name, String defaultVal = "")
     */
    public static String getParameter(HttpServletRequest request, String name) {
	return getParameter(request, name, "");
    }

    /**
     * @param request
     *            HttpServletRequest name 얻고자 하는 값의 키값 defaultVal
     * @return 디폴트 값이 지정이 안되었고 <code>null</code>값이라면 <code>defaultVal</code>값 리턴, 그외의 경우는 해당 value값 리턴
     */
    public static String getParameter(HttpServletRequest request, String name, String defaultVal) {
	String temp = request.getParameter(name);
	if (temp != null) {
	    return temp.trim();
	} else {
	    return defaultVal;
	}
    }

    /**
     * 해당 년 리턴
     * 
     * @param
     */
    public static String thisYear() {
	Date dt = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
	String year = sdf.format(dt);
	return year;
    }

    /**
     * BigDecimal 사용하여 연산(나누기) 결과값 리턴 소수점 10자리까지 표현한다
     * 
     */
    public static String Doubledivide(BigDecimal calc_1, BigDecimal calc_2) {
	String returnValue = "";
	try {
	    returnValue = Double.toString(calc_1.divide(calc_2, 10, BigDecimal.ROUND_CEILING).doubleValue());
	} catch (ArithmeticException e) {
	    returnValue = "0.00";
	}

	return returnValue;
    }

    public static int DoubleMultiply(String str, String str1, String str2) {
	return (int) Math.round(nullDouble(str)) * (int) Math.round(nullDouble(str1)) * (int) Math.round(nullDouble(str2));
    }

    /**
     * 인자값이 null 인경우 공백을 리턴한다.
     * 
     * @param str
     *            입력스트링
     */
    public static String ChangeDeptNoPC(String dept_no) {
	String team = "";
	if ("11732".equals(dept_no)) {// 커넥터설계1팀
	    team = "1";
	} else if ("11728".equals(dept_no)) {// 커넥터개발팀
	    team = "11";
	} else if ("11729".equals(dept_no)) {// 커넥터양산개선팀
	    team = "12";
	} else if ("11724".equals(dept_no)) {// 전장모듈연구개발1팀
	    team = "22";
	} else if ("11725".equals(dept_no)) {// 전장모듈연구개발2팀
	    team = "23";
	} else if ("11726".equals(dept_no)) {// 전장모듈연구개발3팀
	    team = "24";
	} else if ("11737".equals(dept_no)) {// 연구개발3팀
	    team = "3";
	} else if ("11738".equals(dept_no)) {// 연구개발4팀
	    team = "4";
	} else if ("11743".equals(dept_no)) {// 연구개발5팀
	    team = "5";
	} else if ("11740".equals(dept_no)) {// 연구개발6팀
	    team = "6";
	} else if ("11745".equals(dept_no)) {// 시작개발팀
	    team = "21";
	} else if ("11908".equals(dept_no)) {// 전자기획팀
	    team = "90";
	} else if ("11734".equals(dept_no)) {// 전장부품개발팀
	    team = "7";
	}
	return team;
    }

    /**
     * 인자값이 null 인경우 공백을 리턴한다.
     * 
     * @param str
     *            입력스트링
     */
    public static String ChangeDeptNoCP(String team) {
	String dept_no = "";
	if ("1".equals(team)) {
	    dept_no = "11732";// 커넥터설계1팀
	} else if ("11".equals(team)) {
	    dept_no = "11728";// 커넥터개발팀
	} else if ("12".equals(team)) {
	    dept_no = "11729";//  커넥터양산개선팀
	} else if ("22".equals(team)) {
	    dept_no = "11724";// 전장모듈연구개발1팀
	} else if ("23".equals(team)) {
	    dept_no = "11725";// 전장모듈연구개발2팀
	} else if ("24".equals(team)) {
	    dept_no = "11726";// 전장모듈연구개발3팀
	} else if ("3".equals(team)) {
	    dept_no = "11737";// 연구개발3팀
	} else if ("4".equals(team)) {
	    dept_no = "11738";// 연구개발4팀
	} else if ("5".equals(team)) {
	    dept_no = "11743";// 연구개발5팀
	} else if ("6".equals(team)) {
	    dept_no = "11740";// 연구개발6팀
	} else if ("21".equals(team)) {
	    dept_no = "11745";// 시작개발1팀
	} else if ("7".equals(team)) {
	    dept_no = "11734";// 전장부품개발팀
	}
	return dept_no;
    }

    /**
     * 인자값이 null 인경우 공백을 리턴한다.
     * 
     * @param str
     *            입력스트링
     */
    public static String etcOk(String dept_no) {
	String etc_auth = "";	
	if(dept_no.equals("11652") || dept_no.equals("11651") || dept_no.equals("11721") || dept_no.equals("11722") || dept_no.equals("11723") || 
    		dept_no.equals("11724") || dept_no.equals("11725") || dept_no.equals("11726") || dept_no.equals("11737") || dept_no.equals("11738") 
    		|| dept_no.equals("11739") || dept_no.equals("11740") || dept_no.equals("11745") || dept_no.equals("11681") || dept_no.equals("11728") || dept_no.equals("11729") || dept_no.equals("11732") || dept_no.equals("11743") || dept_no.equals("11734") ){
        etc_auth = "ok";
    }
	return etc_auth;
    }

    /**
     * 인자값이 null 인경우 공백을 리턴한다.
     * 
     * @param str
     *            입력스트링
     */
    public static String ChangeDeptNoCPString(String team, String f_name) {
	String deptName = "";
	if ("1".equals(team)) {
	    deptName = "커넥터설계팀" + f_name;
	}else if ("11".equals(team)) {
	    deptName = "커넥터개발팀" + f_name;
	}else if ("12".equals(team)) {
	    deptName = "커넥터양산개선팀" + f_name;
	}else if ("22".equals(team)) {
	    deptName = "전장모듈연구개발1팀" + f_name;
	} else if ("23".equals(team)) {
	    deptName = "전장모듈연구개발2팀" + f_name;
	} else if ("24".equals(team)) {
	    deptName = "전장모듈연구개발3팀" + f_name;
	} else if ("3".equals(team)) {
	    deptName = "연구개발3팀" + f_name;
	} else if ("4".equals(team)) {
	    deptName = "연구개발4팀" + f_name;
	} else if ("5".equals(team)) {
	    deptName = "연구개발5팀" + f_name;
	} else if ("6".equals(team)) {
	    deptName = "연구개발6팀" + f_name;
	} else if ("21".equals(team)) {
	    deptName = "시작개발팀" + f_name;
	} else if ("7".equals(team)) {
	    deptName = "전장부품개발팀" + f_name;
	}

	return deptName;
    }

    /**
     * 인자값이 null 인경우 공백을 리턴한다.
     * 
     * @param str
     *            입력스트링
     */
    public static String SelectDeptString(String team) {
	String deptString = "";
	StringBuffer sb = new StringBuffer();
	sb.append("<option value=''>선택</option>");
	if ("1".equals(team)) {
	    sb.append("<option value='1' selected>커넥터설계팀</option>");
	} else {
	    sb.append("<option value='1'>커넥터설계팀</option>");
	}
	if ("11".equals(team)) {
	    sb.append("<option value='11' selected>커넥터개발팀</option>");
	} else {
	    sb.append("<option value='11'>커넥터개발팀</option>");
	}
	if ("12".equals(team)) {
	    sb.append("<option value='12' selected>커넥터양산개선팀</option>");
	} else {
	    sb.append("<option value='12'>커넥터양산개선팀</option>");
	}
	if ("22".equals(team)) {
	    sb.append("<option value='22' selected>전장모듈연구개발1팀</option>");
	} else {
	    sb.append("<option value='22'>전장모듈연구개발1팀</option>");
	}
	if ("23".equals(team)) {
	    sb.append("<option value='23' selected>전장모듈연구개발2팀</option>");
	} else {
	    sb.append("<option value='23'>전장모듈연구개발2팀</option>");
	}
	if ("24".equals(team)) {
	    sb.append("<option value='24' selected>전장모듈연구개발3팀</option>");
	} else {
	    sb.append("<option value='24'>전장모듈연구개발3팀</option>");
	}
	if ("3".equals(team)) {
	    sb.append("<option value='3' selected>연구개발3팀</option>");
	} else {
	    sb.append("<option value='3'>연구개발3팀</option>");
	}
	if ("4".equals(team)) {
	    sb.append("<option value='4' selected>연구개발4팀</option>");
	} else {
	    sb.append("<option value='4'>연구개발4팀</option>");
	}
	if ("5".equals(team)) {
	    sb.append("<option value='5' selected>연구개발5팀</option>");
	} else {
	    sb.append("<option value='5'>연구개발5팀</option>");
	}
	if ("6".equals(team)) {
	    sb.append("<option value='6' selected>연구개발6팀</option>");
	} else {
	    sb.append("<option value='6'>연구개발6팀</option>");
	}
	if ("7".equals(team)) {
	    sb.append("<option value='7' selected>전장부품개발팀</option>");
	} else {
	    sb.append("<option value='7'>전장부품개발팀</option>");
	}
	deptString = sb.toString();
	return deptString;
    }
}
