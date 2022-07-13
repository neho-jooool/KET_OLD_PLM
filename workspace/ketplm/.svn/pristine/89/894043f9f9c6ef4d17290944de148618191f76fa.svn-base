/*
 * LSISStringUtil.java
 *
 * Created on 2006년 8월 16일
 */

package e3ps.common.util;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.Vector;

import ext.ket.shared.log.Kogger;

/**
 * 자주 쓰이는 함수들을 공유하기 위하여 등록
 * 
 * @author LG CNS
 * @since 2006-08-16
 * @modify
 */
public final class KETStringUtil {

    /** Creates a new instance of LSISStringUtil */
    /** 화폐단위 표시형식 */
    public static final String MONEY_FORMAT    = "#,###.##";
    public static final String QUANTITY_FORMAT = "#,###.##";

    public KETStringUtil() {
    }

    /**
     * 문자열을 처리하는 중 발생할 수 있는 Null pointer exception을 방지하기 위해<BR>
     * null 문자열을 빈 문자열로 반환해 주는 Filter 작용
     * 
     * @return String 전달된 문자가 null일 경우 빈 문자열을 반납<BR>
     *         null이 아닐경우는 전달된 문자열을 반납
     * @param str
     */
    public static String nullFilter(String str) {
	if ((str == null) || (str.trim().equals("")) || (str.trim().equals("null"))) return "";
	else return str;
    }

    /**
     * <br>
     * 문자열이 null일 경우 특정 문자열로 전환하여 반납
     * 
     * @return String 전달된 문자열 혹은 null을 대체한 문자열
     * @param str
     *            원 문자열
     * @param NVLString
     *            원 문자열이 null일 경우 대체되어 반납되는 문자열
     */
    public static String nvl(String str, String NVLString) {
	if ((str == null) || (str.trim().equals("")) || (str.trim().equals("null"))) return NVLString;
	else return str.trim();
    }

    /**
     * 지정된 Format으로 전달 받은 문자열을 돌려준다. 입력받은 전달 받은 문자열이 숫자일때 해당된다. 숫자가 아닌 값이 들어오면 입력값을 그대로 돌려준다.<BR>
     * <BR>
     * 
     * 사용예) getFormattedNumber("200102042345","'$'####,####0")<BR>
     * 결 과 ) $20,01020,42345<BR>
     * <BR>
     * 
     * Format은 J2SE의 MessageFormat의 Documentation을 참고한다.
     * 
     * @return java.lang.String
     * @param pInstr
     *            String
     * @param pInformat
     *            String
     */
    public static String getFormattedNumber(String pInstr, String pInformat) {

	String rStr = pInstr;

	try {
	    Object[] testArgs = { new Long(pInstr) };
	    MessageFormat form = new MessageFormat("{0,number," + pInformat + "}");
	    rStr = form.format(testArgs);
	} catch (Exception e) {
	}

	return rStr;
    }

    /**
     * 지정된 Format으로 전달 받은 문자열을 돌려준다. <BR>
     * <BR>
     * 
     * 사용예) getFormattedText( "7104041055727", "######-########")<BR>
     * 결 과 ) 710404-1055727<BR>
     * <BR>
     * 
     * 그대로 사용할 문자는 '#'으로 표현된다. 그 외의 문자열은 아무기호나 상관없다. 그러므로 '#'자체는 사용이 불가능하다. 입력받은 문자가 Format보다 길다면 Format길이 이후의 문자열은 잘리게 된다. Format이 입력받은
     * 문자열보다 길다면 문자열만큼만 출력된다.
     * 
     * @return java.lang.String
     * @param pInstr
     *            String
     * @param pInformat
     *            String
     */
    public static String getFormattedText(String pInstr, String pInformat) {

	StringBuffer rStr = new StringBuffer();

	try {
	    for (int i = 0, j = 0; i < pInformat.length(); i++) {
		if (pInformat.charAt(i) == '#') {
		    rStr.append(pInstr.charAt(j));
		    j++;
		}
		else {
		    rStr.append(pInformat.charAt(i));
		}
	    }
	} catch (Exception e) {
	}
	;

	return rStr.toString().trim();
    }

    /**
     * <br>
     * KSC5601 형식으로 converting<BR>
     * 입력받은 문자열이 KSC5601 형식일 경우 입력받은 문자열을 그대로 반환한다.<BR>
     * 
     * @return String KSC5601 형식으로 변환된 문자열
     * @param ko
     *            KDS5601 형식으로 변환할 문자열
     */
    public static String toKSC5601(String ko) {
	if (ko != null && ko != "") {
	    String new_str = null;

	    try {
		if (ko.equals(new String(ko.getBytes("KSC5601"), "KSC5601"))) new_str = ko;
		else new_str = new String(ko.getBytes("8859_1"), "KSC5601");
	    } catch (java.io.UnsupportedEncodingException ue) {
		new_str = "";
	    }
	    return new_str.trim();

	}
	else return "";
    }

    /**
     * <br>
     * 입력받은 문자열을 US8859 형식으로 converting 해 준다.
     * 
     * @return String US8859 형식으로 변환된 문자열
     * @param en
     *            US8859 형식으로 변환하기 위한 원문자열
     */
    public static String toUS8859(String en) {
	if (en != null && en != "") {
	    String new_str = null;

	    try {
		if (en.equals(new String(en.getBytes("KSC5601"), "KSC5601"))) new_str = new String(en.getBytes("KSC5601"), "8859_1");
		else new_str = en;
	    } catch (java.io.UnsupportedEncodingException ue) {
		new_str = "";
	    }
	    return new_str;

	}
	else return "";
    }

    /**
     * <br>
     * 원문자열에 포함되어 있는 HTML Tag들을 token으로 변환시켜 준다.<BR>
     * 변환되는 문자들은 다음과 같다.<BR>
     * <BR>
     * <CODE>p_str = replace(p_str, "&", "&amp");</CODE><BR>
     * <CODE> p_str = replace(p_str, "<", "&lt");</CODE><BR>
     * <CODE>p_str = replace(p_str, ">", "&gt");</CODE><BR>
     * <CODE>p_str = replace(p_str, "'", "&quot");</CODE><BR>
     * <CODE>p_str = replace(p_str, "\"", "");</CODE><BR>
     * 
     * @return String HTML tag가 제거된 문자열
     * @param p_str
     *            HTML Tag를 포함하고 있는 문자열
     */
    public static String replaceNoHTML(String p_str) {
	if (p_str == null) {
	    p_str = "";
	}
	else {
	    p_str = p_str.trim();
	    p_str = replace(p_str, "&", "&amp");
	    p_str = replace(p_str, "<", "&lt");
	    p_str = replace(p_str, ">", "&gt");
	    p_str = replace(p_str, "'", "&quot");
	    p_str = replace(p_str, "\"", "");
	}

	return p_str;
    }

    //  특수문자 DB입력 처리
    /**
     * <br>
     * 문자열에 포함되어 있는 문자들 중 DB에 바로 저장할 수 없는 특수문자들을<BR>
     * 저장할 수 있는 형태로 변환시켜준다.<BR>
     * 변환되는 문자들은 아래와 같다.<BR>
     * <BR>
     * 
     * <CODE> p_str = p_str.trim();</CODE><BR>
     * <CODE> p_str = replace(p_str, "&", "&&");</CODE><BR>
     * <CODE> p_str = replace(p_str, "'", "''");</CODE><BR>
     * 
     * @return String DB에 직접 저장되지 않는 문자들이 변환된 문자열
     * @param p_str
     *            원문자열
     */
    public static String replaceSpecialTag(String p_str) {
	if (p_str == null) {
	    p_str = "";
	}
	else {
	    p_str = p_str.trim();
	    p_str = replace(p_str, "&", "&&");
	    p_str = replace(p_str, "'", "''");
	}

	return p_str;
    }

    /**
     * <br>
     * Carrige Return을 HTML에 표시하기 위해 BR tag로 전환시켜 준다.
     * 
     * @return String Carrige Return이 BR tag로 변환된 문자열
     * @param p_str
     *            Carrige Return을 포함하고 있는 문자열
     */
    public static String replaceBR(String p_str) {
	if (p_str == null) {
	    p_str = "";
	}
	else {
	    p_str = p_str.trim();
	    p_str = replace(p_str, "\n", "<br>");
	}

	return p_str;
    }

    /**
     * <br>
     * 문자열 단위로 교체
     * 
     * @return String 교체된 문자열
     * @param line
     *            원 문자열
     * @param oldString
     *            원 문자열에 포함되어 있는 교체될 문자열
     * @param newString
     *            oldString과 교체되어 반환될 문자열
     */
    public static String replace(String line, String oldString, String newString) {
	for (int index = 0; (index = line.indexOf(oldString, index)) >= 0; index += newString.length())
	    line = line.substring(0, index) + newString + line.substring(index + oldString.length());

	return line;
    }

    /**
     * <br>
     * 원 문자열의 정해진 길이마다 구분자를 넣어 준다.<BR>
     * Carriage Return은 공백으로 대체된다.<BR>
     * Web에서 한 줄로 된 정보를 여러줄로 전환할 때 사용<BR>
     * 
     * @return String 일정한 길이마다 구분자가 삽입된 문자열
     * @param str
     *            원문자열
     * @param cutSize
     *            원 문자열을 자를 길이
     * @param lineBreaker
     *            일정한 길이마다 삽입할 구분자
     */
    public static String getMultiline(String str, int cutSize, String lineBreaker) {
	StringBuffer tempStrBuffer = new StringBuffer("");
	StringBuffer strBuffer = new StringBuffer("");
	StringTokenizer strToken = new StringTokenizer(str, "\n");

	while (strToken.hasMoreElements()) {
	    tempStrBuffer.append(strToken.nextToken() + " ");
	}

	int length = tempStrBuffer.length();
	int startIndex = 0;

	try {
	    while (length > startIndex) {
		strBuffer.append(tempStrBuffer.substring(startIndex, startIndex + cutSize) + lineBreaker);
		startIndex += cutSize;
	    }

	} catch (Exception e) {
	    strBuffer.append(tempStrBuffer.substring(startIndex, length));
	}

	return strBuffer.toString();
    }


    /**
     * <br>
     * 일정 길이 다음의 문자열을 ... 로 표시하는 메서드
     * 
     * @return String 일정한 길이로 잘리고 인식 문자열이 추가된 문자열
     * @param limit
     *            반환될 문자열의 길이
     * @param pStr
     *            String
     */
    public static String getTruncatedText(String pStr, int limit) {
	if (pStr.length() < limit) {
	    return pStr;
	}
	else {
	    return pStr.substring(0, limit) + "...";
	}

    }

    /**
     * <br>
     * 특정 구분자로 연결되어 있는 문자열을 받아 문자열만을 추출한 후에<BR>
     * 그것을 ArrayList로 만들어 Return한다.<BR>
     * 입력되는 문자열은 자유로운 Delimiter로 사용가능하다.<BR>
     * 아래의 모든 문자열은 같은 결과를 나타낸다.<BR>
     * <BR>
     * 
     * 수정 by 오명운<BR>
     * delimiter 앞에 아무문자열이 없어도 빈문자열하나가 있는 것으로 간주하며,<BR>
     * delimiter 뒤에 아무문자열이 없는 것은 아무것도 없는 것으로 간주한다.<BR>
     * 또한 delimiter 의 길이가 2이상일 때도 오류없도록 수정하였다.<BR>
     * <BR>
     * 
     * <CODE>getToken( ":b:", ":" ).size() : 2</CODE><BR>
     * <CODE>getToken( "a:b:c", ":" ).size() : 3</CODE><BR>
     * <CODE>getToken( "a:b:c:", ":" ).size() : 3</CODE><BR>
     * <CODE>getToken( "a:b:c:d", ":" ).size() : 4</CODE><BR>
     * <CODE>getToken( ":::", ":" ).size() : 3</CODE><BR>
     * <CODE>getToken( " : : : ", ":" ).size() : 4</CODE><BR>
     * <CODE>getToken( "", ":" ).size() : 0</CODE><BR>
     * 
     * @return java.util.ArrayList
     * @param pStr
     *            String
     * @param pDelimiter
     *            String
     */
    public static ArrayList getToken(String pStr, String pDelimiter) {

	ArrayList al = new ArrayList();

	if (pStr == null || pStr.equals("")) return al;
	if (pDelimiter == null || pDelimiter.equals("")) return al;

	if (pStr.length() < pDelimiter.length()) {
	    al.add(pStr);
	    return al;
	}

	int vStart = 0;
	int vEnd = pStr.length();
	boolean vFirst = false;

	if (pStr.substring(0, pDelimiter.length()).equals(pDelimiter)) vFirst = true;

	for (int i = 0; vEnd > -1; i++) {

	    if (i > 0) vStart = vEnd + pDelimiter.length();

	    vEnd = pStr.indexOf(pDelimiter, vStart);

	    if (vEnd > -1) {
		al.add(pStr.substring(vStart, vEnd));
	    }
	}

	if (vStart < pStr.length()) {
	    al.add(pStr.substring(vStart));
	}

	return al;
    }

    /**
     * 토큰의 배열을 delimiter를 중간에 넣어서 합친뒤 스트링으로 반환하는 메소드.
     * 
     * @param array
     *            합쳐야할 토큰들.
     * @param delimiter
     *            토큰이 합쳐질때 사이에 들어갈 delimiter.
     * @return 합쳐진 스트링.
     * @see #split
     */
    public static String join(ArrayList array, String delimiter) {
	StringBuffer sb = new StringBuffer();
	for (int i = 0; i < array.size(); i++) {
	    if (i > 0) sb.append(delimiter);
	    sb.append(array.get(i));
	}

	return (sb.toString());
    }
    
    /**
     * 토큰의 배열을 delimiter를 중간에 넣어서 합친뒤 스트링으로 반환하는 메소드.
     * 
     * @param array
     *            합쳐야할 토큰들.
     * @param delimiter
     *            토큰이 합쳐질때 사이에 들어갈 delimiter.
     * @return 합쳐진 스트링.
     * @see #split
     */
    public String getArrayjoin(String array[], String delimiter) {
	StringBuffer sb = new StringBuffer();
	String temp = "";
	for (int i = 0; i < array.length; i++) {
	    temp = array[i];
	    if (i > 0) sb.append(delimiter);
	    sb.append(temp);
	}

	return (sb.toString());
    }

    /**
     * <br>
     * 검색 Keyword를 받아 wild card로 쓰이는 '*'를 '%'로 바꾸는 함수<BR>
     * 주로 검색어를 받아 Like 검색으로 전환할 때 사용<BR>
     * 검색어가 공백(null, "null", " ", "")일 때는 '%'로 전환되고,<BR>
     * 검색어에 wild card가 포함되어있지 않은 경우 앞 뒤로 '%'를 추가해 준다.
     * 
     * @return String(%str%)
     * @param str
     *            원 문자열
     */
    public static String getLikeString(String str) {
	if (isNull(str)) {
	    str = "%";
	}
	else if (str.indexOf("*") == -1 && str.indexOf("%") == -1) {
	    str = "%" + str + "%";
	}
	else {
	    str = str.replace('*', '%');
	}

	str = replace(str, " ", "");

	return str.toUpperCase();
    }

    /**
     * <br>
     * String 전체를 읽어서 특수기호를 코드로 바꾸어서 저장하기 위한 Method<br>
     * 
     * @param orgstr
     *            변환하고자 하는 전체 String<br>
     * @return 코드로 변환된 String값<br>
     */
    public static String replaceCode(String orgstr) {
	//   int pos = 0;
	int len = orgstr.length();
	String rplStr = "";
	String currStr = "";
	String replaceStr = "";
	int i = 0;

	for (i = 0; i < len; i++) {
	    // if (i != len-1)

	    currStr = orgstr.substring(i, i + 1);
	    if (currStr.equals("\"")) {
		rplStr = "##34";
	    }
	    else if (currStr.equals("\'")) {
		rplStr = "##39";
	    }
	    else if (currStr.equals(">")) {
		rplStr = "##60";
	    }
	    else if (currStr.equals("<")) {
		rplStr = "##62";
	    }
	    else if (currStr.equals("/")) {
		rplStr = "##47";
	    }
	    else if (currStr.equals("\\")) {
		rplStr = "##92";
	    }
	    else if (currStr.equals("\n")) {
		rplStr = "##10";
	    }
	    else if (currStr.equals(" ")) {
		rplStr = "##40";
	    }
	    else {
		rplStr = currStr;
	    }
	    replaceStr += rplStr;
	}

	return replaceStr;
    }

    /**
     * <br>
     * 특수기호로 바꾼 전체 String을 다시 특수문자로 변환하는 Method<br>
     * 
     * @param orgstr
     *            코드로 변환된 String<br>
     * @return 특수문자로 다시 변환된 String<br>
     */
    public static String replaceSign(String orgstr) {
	// pos = 0;
	int len = orgstr.length();
	String rplStr = "";
	String currChrs = "";
	String currStr = "";
	String replaceStr = "";
	int j = 0, i = 0;
	boolean flag = true;

	for (i = 0; i < len; i++) {
	    currChrs = orgstr.substring(i, i + 1);
	    flag = true;

	    j = i;
	    if (len - j >= 4) currStr = orgstr.substring(j, j + 4);
	    else currStr = orgstr.substring(j, j + 1);
	    if (currStr.equals("##34")) {
		rplStr = "\"";
	    }
	    else if (currStr.equals("##39")) {
		rplStr = "\'";
	    }
	    else if (currStr.equals("##60")) {
		rplStr = ">";
	    }
	    else if (currStr.equals("##62")) {
		rplStr = "<";
	    }
	    else if (currStr.equals("##47")) {
		rplStr = "/";
	    }
	    else if (currStr.equals("##92")) {
		rplStr = "\\";
	    }
	    else if (currStr.equals("##10")) {
		rplStr = "\n";
	    }
	    else if (currStr.equals("##40")) {
		rplStr = " ";
	    }
	    else {
		rplStr = currChrs;
		flag = false;
	    }

	    replaceStr += rplStr;
	    if (flag == true) {
		i = i + 3;
	    }
	}
	return replaceStr;
    }

    /**
     * <br>
     * length크기에 맞추어 앞자리에 "0"을 추가하여 Return하는 Method<BR>
     * Creation date: (01-10-25)
     * 
     * @param nums
     *            실제 Number
     * @param length
     *            실제 Number+0의 갯수
     * @return num "0"을 추가한 문자열(만약 num이 한자리일 경우에)
     */
    public static String toLen(int nums, int length) {
	String num = String.valueOf(nums).toString();
	int space = length - num.length();
	int i = 0;
	String buf = "";

	for (i = 0; i < space; i++) {
	    buf += "0";
	}
	num = buf + num;
	return num;
    }

    /**
     * 문자열이 null인지 검사해서 결과를 반환
     * 
     * @return boolean true - null
     * @param str
     */
    public static boolean isNull(String str) {
	if ((str == null) || (str.trim().equals("")) || (str.trim().equals("null"))) return true;
	else return false;
    }


    /**
     * String을 length만큼 return
     * 
     * @param string
     * @param length
     * @return
     */
    public static String cutStringMax(String string, int length) {
	if (KETStringUtil.isNull(string)) return "";
	else if (string.length() > length) return string.substring(0, length - 3) + "...";
	else return string;
    }

    /**
     * <br>
     * 한글로 Encoding하는 Method<br>
     * 
     * @param str
     *            한글로 변환하고자 하는 문자열<br>
     * @return 한글로 변환된 문자열<br>
     */
    public static String toKorean(String str) {
	String rtn = null;
	try {
	    if (str != null) rtn = new String(str.getBytes("8859_1"), "EUC_KR");
	} catch (java.io.UnsupportedEncodingException e) {
	}
	return rtn;
    }


    /**
     * <br>
     * 로그인 한 사용자의 관리자 여부를 확인
     * 
     * @param userId
     *            로그인 한 사용자의 ID
     * @throws LSysException
     * @return true - 로그인 한 사용자가 관리자이다.<br>
     *         false - 로그인 한 사용자가 관리자가 아니다.
     */
    public static boolean isAdmin(String userId) throws Exception {
	boolean isAdmin = false;
	try {
	    wt.util.WTProperties aProperties = wt.util.WTProperties.getLocalProperties();
	    String adminNames = (String) aProperties.get("wt.sysadm.administrators");

	    if (adminNames != null && adminNames.lastIndexOf(userId) > 0) isAdmin = true;
	} catch (Exception e) {
	    Kogger.error(KETStringUtil.class, e);
	}

	return isAdmin;
    }

    /**
     * <br>
     * 숫자로만 이루어진 byte 단위의 file size를 크기에 따라 byte 혹은 KB 형식으로 변환하여 반환<br>
     * null String일 경우 0byte가 반환된다.
     * 
     * @param inputSize
     *            DB 로 부터 조회한 file의 크기. byte 단위로 들어가야 하며 숫자로 이루어져 있어야 한다.
     * @return byte, 혹은 KB 단위가 포함된 Size.
     */
    public static String getFileSizeString(String inputSize) {
	int divisor = 1024;
	long fileSize = 0L;
	char decimalPoint = ',';

	String displaySize = "";
	char[] sizeArray = null;

	inputSize = nullFilter(inputSize);

	if (inputSize.length() < 1) {
	    displaySize = "0byte";
	}
	else {
	    try {
		fileSize = Long.parseLong(inputSize);

		if (fileSize < 1024) {
		    if (inputSize.length() > 3) {
			displaySize = inputSize.substring(0, 1) + decimalPoint + inputSize.substring(1);
		    }
		    else displaySize = inputSize;

		    displaySize = displaySize + "byte";
		}
		else {
		    inputSize = String.valueOf(Long.parseLong(inputSize) / divisor);
		    sizeArray = inputSize.toCharArray();

		    for (int inx = 0; inx < sizeArray.length; inx++) {
			displaySize = sizeArray[sizeArray.length - inx - 1] + displaySize;

			if ((inx + 1) % 3 == 0) {
			    displaySize = decimalPoint + displaySize;
			}
		    }

		    if (displaySize.startsWith(",")) displaySize = displaySize.substring(1);

		    displaySize += "KB";
		}
	    } catch (NumberFormatException nfe) {
		Kogger.error(KETStringUtil.class, nfe);
	    }
	}

	return displaySize;
    }

    /**
     * String에서 substring을 찾아 바꾸고자하는 다른 string으로대치해서 넘겨주는메소드. 놀랍게도 java.lang.String에 이러한 메소드가 없답니다. 물론 차후에 생기게 될지도..^^
     * 
     * @param search
     *            찾으려고하는 단어
     * @param replace
     *            바꾸고자하는 단어
     * @param source
     *            전체 String
     * @return The source with all instances of <code>search</code> replaced by <code>replace</code>
     */
    public static String sReplace(String search, String replace, String source) {
	int spot;
	String returnString;
	String origSource = new String(source);

	spot = source.indexOf(search);

	if (spot > -1) returnString = "";
	else returnString = source;
	while (spot > -1) {
	    if (spot == source.length() + 1) {
		returnString = returnString.concat(source.substring(0, source.length() - 1).concat(replace));
		source = "";
	    }
	    else if (spot > 0) {
		returnString = returnString.concat(source.substring(0, spot).concat(replace));
		source = source.substring(spot + search.length(), source.length());
	    }
	    else {
		returnString = returnString.concat(replace);
		source = source.substring(spot + search.length(), source.length());
	    }
	    spot = source.indexOf(search);
	}
	if (!source.equals(origSource)) {
	    return returnString.concat(source);
	}
	else {
	    return returnString;
	}
    }

    /**
     * 데이타의 길이를 체크하여 원하는 길이만큼 리턴한다.
     * 
     * @param pm_sData
     *            , pm_iMaxLength
     * @return Sting
     */
    public static String dataSubstring(String pm_sData, int pm_iMaxLength) {
	String numstr = " ~!@#$%^&*()_+|=-<>?/.,`0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	String thischar = "";
	String lm_sNewData = "";
	int count = 0;

	for (int i = 0; i < pm_sData.length(); i++) {
	    thischar = pm_sData.substring(i, i + 1);
	    if (numstr.indexOf(thischar) != -1) {
		count++;
	    }
	    else {
		count = count + 2;
	    }

	    lm_sNewData = lm_sNewData + thischar;
	    if (count > pm_iMaxLength) {
		lm_sNewData = lm_sNewData + " ...";
		break;
	    }
	}
	return lm_sNewData;
    }

    /**
     * String 값 중에서 특수문자를 기준으로 마지막 Token 값을 구한다.
     * 
     * @param pm_sSource
     *            , pm_sDelim
     * @return String
     */
    public static String getLastToken(String pm_sSource, String pm_sDelim) {
	int lm_sLastIndex = 0;
	String lm_sLastToken = null;
	lm_sLastIndex = pm_sSource.lastIndexOf(pm_sDelim);
	lm_sLastToken = pm_sSource.substring(lm_sLastIndex + 1);
	return lm_sLastToken;
    }

    /**
     * 특정 문자열 제거
     * 
     * @param String
     *            in, char skip
     * @return String
     */
    public static String skipChar(String in, char skip) {
	StringBuffer out = new StringBuffer();
	for (int i = 0; in != null && i < in.length(); i++) {
	    char c = in.charAt(i);
	    if (c != skip) {
		out.append(c);
	    }
	}
	return out.toString();
    }

    /**
     * 짝수인지를 판단한다.
     * 
     * @param int no
     * 
     * @return boolean
     */
    public static boolean isEven(int no) {
	if (((no / 2) * 2) == no) return true;  // 짝수
	else return false;  // 홀수
    }

    /**
     * <pre>
     * 중복제거
     * </pre>
     * 
     * @param 중복된값이
     *            있는 배열
     * @return 제거된 배열
     */
    public static String[] distinctString(String[] str) {
	Vector vec = new Vector();
	Vector vec2 = new Vector();
	String[] returnStrings;
	int a = 0;

	//vector에 insert
	for (int i = 0; i < str.length; i++) {
	    vec.addElement(str[i]);
	}

	int tot = vec.size();


	for (int i = 0; i < tot; i++) {
	    int add = 0;
	    for (int j = i + 1; j < tot; j++) {
		if (vec.elementAt(i).equals(vec.elementAt(j))) {
		    //같은 것이 있으면 +
		    add += 1;
		}

	    }
	    //같은 주소가 없으면(add=0) 출력.
	    if (add == 0) {
		a += 1;
		//Kogger.debug(getClass(), "email:" + vec.elementAt(i).toString()); //기록
		vec2.addElement(vec.elementAt(i));
	    }
	}

	returnStrings = new String[vec2.size()];
	for (int i = 0; i < vec2.size(); i++) {
	    returnStrings[i] = vec2.elementAt(i).toString();
	}

	return returnStrings;
    }

    /**
     * 인자로 넘어온 문자열의 앞뒤의 공백을 제거한다. 단, String.trim()메소드와 다른점이라면 '\u3000' 문자도 제거한다. 서버의 호스트에서 넘어오는 데이타의 경우 0x3000값이 들어갈 여지가 있어 생성된 메소드
     * 
     * @param s
     *            앞뒤의 공백을 제거할 문자열
     * @return String 앞뒤의 공백과 '\3000' 값이 제거된 문자열
     */
    public static String trim(String s) {
	int i = 0;
	char ac[] = s.toCharArray();
	int j = ac.length;
	int k;
	for (k = j; i < k && (ac[i] <= ' ' || ac[i] == '\u3000'); i++)
	    ;
	for (; i < k && (ac[k - 1] <= ' ' || ac[k - 1] == '\u3000'); k--)
	    ;
	return i <= 0 && k >= j ? s : s.substring(i, k);
    }

    /**
     * 전체 String(fullStr)에 특정 문자(addStr)을 특정 위치(idx)마다 추가하는 Method
     * 
     * @param fullStr
     *            String
     * @param addStr
     *            String
     * @param idx
     *            int
     * @return String
     */
    public static String addStrPerLength(String fullStr, String addStr, int idx) {
	String returnStr = "";
	int length = fullStr.length();
	if (length > idx) {
	    for (int i = 0; i < length; i = i + idx) {
		if (length < i + idx) {
		    returnStr += fullStr.substring(i);
		}
		else {
		    returnStr += fullStr.substring(i, i + idx) + addStr;
		}
	    }
	}
	else {
	    returnStr = fullStr;
	}
	return returnStr;
    }

    /**
     * 인자로 넘어온 Timestamp를 문자열 형태로 변환한다. format을 지원하며 yyyy - 연도, MM - 달, dd - 일자, HH - 시간, mm-
     * 
     * @param Timestamp
     *            변환시킬 Timestamp
     * @return String Format이 적용된 문자
     */
    public static String timestampToFormatString(Timestamp timestamp, String format, TimeZone timeZone) {
	if (timeZone == null) timeZone = (TimeZone) wt.util.WTContext.getContext().get("timezone");
	if (timeZone == null) timeZone = wt.util.WTContext.getContext().getTimeZone();

	SimpleDateFormat sdf = new SimpleDateFormat(format);

	return sdf.format(timestamp);
    }

    public static String timestampToFormatString(Timestamp timestamp) {
	return timestampToFormatString(timestamp, "yyyy-MM-dd", null);
    }

    /***************************************************************************
     * 메소드명 : toPlusZero 메소드설명 : 넘겨온 숫자 앞에 0 붙여 넣기(ex,001) 작성자 : 송재현 작성일 : 2004-05-24 5:44오후 (ex, SUtil.toPlusZero(만들자릿수, 값)
     ***************************************************************************/
    public static String toPlusZero(int count, int intValue, String temp) {
	String strValue = Integer.toString(intValue);
	int cc = strValue.length();
	int aa = 0;

	if (count > cc) {
	    aa = count - cc;
	    for (int i = 1; i <= aa; i++) {
		strValue = temp + strValue;
	    }
	}
	return strValue;
    }

    /**
     * 인자로 넘어온 strValeu를 tag 처리해준다 안했을 경우 페이지 리로드 되었을 경우 데이터 유실
     * 
     * @param strValeu
     *            변환시킬 Html 태그로 변환
     * @return String strValeu로 태그 변환후 리턴
     */
    public static String tagReplaceAll(String strValeu) {

	strValeu = strValeu.replaceAll("\'", "\\'");
	strValeu = strValeu.replaceAll("\"", "&quot;");
	strValeu = strValeu.replaceAll("[(]", "(");
	strValeu = strValeu.replaceAll("[)]", ")");

	return strValeu;
    }

    /**
    *
    */
    public static String tranDotString(String str, int num) {
	String returnStr = "";

	if (!isNull(str)) {
	    if (str.getBytes().length > num * 2) {
		returnStr = str.substring(0, num) + "...";
	    }
	    else {
		returnStr = str;
	    }
	}
	return returnStr;
    }

    /**
     * 특정 포맷으로 숫자를 표현한다.
     * 
     * @param fmt
     *            포맷방식
     * @param num
     *            java.lang.Number
     * @return 특정포맷의 숫자 문자열
     */
    public static String numberFormat(String fmt, Number num) {
	NumberFormat nf = new DecimalFormat(fmt);
	return nf.format(num);
    }

    public static String numberFormat(String fmt, double num) {
	NumberFormat nf = new DecimalFormat(fmt);
	return nf.format(num);
    }

    /**
     * 화폐단위 표시형식으로 포맷된 문자열을 반환한다.
     * 
     * @param num
     *            java.lang.Number
     * @return 화폐단위 표시형식의 포맷 문자열
     */
    public static String money(Number num) {
	return numberFormat(MONEY_FORMAT, num);
    }

    /*
     * [PLM System 1차개선]
     * 수정내용 : MultiCombo에서 선택한 항목을 검색조건으로 만들어서 리턴
     * 수정일자 : 2013. 5. 31
     * 수정자 : 오명재
     */
    public static String getMultiSearchCondition(ArrayList<String> condList) {

	String returnStr = "";
	int cnt = 1;

	for (String condStr : condList) {

	    if (cnt == 1) {
		returnStr = "'" + condStr + "'";
	    }
	    else {
		returnStr += ",'" + condStr + "'";
	    }

	    cnt++;
	}

	return returnStr;
    }

    public static String toDebugString(String[] sary) {
	if (sary == null) return "null";

	StringBuilder sb = new StringBuilder();
	sb.append("[");
	if (sary != null) {
	    for (int i = 0; i < sary.length; ++i) {
		if (i > 0) sb.append(", ");
		sb.append("{").append(sary[i]).append("}");
	    }
	}
	sb.append("]");
	return sb.toString();
    }

    public static String join(String[] sary, String delimiter) {
	StringBuilder sb = new StringBuilder();
	if (sary != null) {
	    for (int i = 0; i < sary.length; ++i) {
		if (i > 0) sb.append(delimiter);
		sb.append(sary[i]);
	    }
	}
	return sb.toString();
    }

    public static String join(Collection collection, String delimiter) {
	StringBuilder sb = new StringBuilder();
	if (collection != null) {
	    Iterator ite = collection.iterator();
	    int cnt = 0;
	    while (ite.hasNext()) {
		if (cnt++ > 0) sb.append(delimiter);
		String s = String.valueOf(ite.next());
		sb.append(s);
	    }
	}
	return sb.toString();
    }

}
