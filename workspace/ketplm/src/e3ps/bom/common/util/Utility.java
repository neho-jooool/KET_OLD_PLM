// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Utility.java

package e3ps.bom.common.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFrame;

import e3ps.bom.framework.util.MessageBox;
import ext.ket.shared.log.Kogger;

public class Utility {

	public Utility() {}

	public static boolean isDouble(String s) {
		char charArr[] = s.toCharArray();
		int commaCount = 0;

		for (int i = 0; i < charArr.length; i++)
			if (charArr[i] == '.')
				commaCount++;
			else if (Character.getNumericValue(charArr[i]) < 0 || Character.getNumericValue(charArr[i]) > 9)
				return false;

		return commaCount <= 1;
	}

	public static boolean isNumeric(String s) {
		char charArr[] = s.toCharArray();

		for (int i = 0; i < charArr.length; i++)
			if (Character.getNumericValue(charArr[i]) < 0 || Character.getNumericValue(charArr[i]) > 9)
				return false;

		return true;
	}

	public static Date stringToDate(String currentDate) {
		String year = "";
		String month = "";
		String day = "";

		if (currentDate == null || currentDate.trim().length() == 0) {
			return null;
		} else if (currentDate.length() < 11) {
			year = currentDate.substring(0, 4);
			month = currentDate.substring(5, 7);
			day = currentDate.substring(8);
		} else {
			year = currentDate.substring(0, 4);
			month = currentDate.substring(5, 7);
			day = currentDate.substring(8, 10);
		}

		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day));
		dateToString(cal.getTime());
		return cal.getTime();
	}

	public static String dateToString(java.util.Date date) {
		if (date == null) {
			return "";
		}

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date);
	}

	public static String dateToString(java.util.Date date, String format) {
		if (date == null)
			return "";

		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}

	public static String currentDate() {
		Date date = new Date();

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = df.format(date);

		return strDate;
	}

	public static String checkQuote(String s) throws Exception {
		return replace(s, "'", "''");
	}

	public static String replace(String s, String from, String to) throws Exception {
		if (s == null)
			return null;

		String result = s;

		try {
			if (!from.equals(to)) {
				int index = result.indexOf(from);
				int length = to.length();

				for (; index >= 0; index = result.indexOf(from, index + length))
					if (index == 0)
						result = to + result.substring(from.length());
					else
						result = result.substring(0, index) + to + result.substring(index + from.length());
			}

		} catch (Exception ex) {
			throw new Exception("StringUtil.replace(\"" + s + "\",\"" + from + "\",\"" + to + "\")\r\n" + ex.getMessage());
		}

		return result;
	}

	public static String asc2ksc(String en) {
		String new_str = null;

		try {
			if (en == null)
				new_str = "";
			else
				new_str = new String(en.getBytes("8859_1"), "KSC5601");

		} catch (Exception ex) {
			Kogger.debug(Utility.class, "asc2ksc : " + ex.getMessage());
		}

		return new_str;
	}

	public static String ksc2asc(String ko) {
		String new_str = null;

		try {
			if (ko == null)
				new_str = "";
			else
				new_str = new String(ko.getBytes("KSC5601"), "8859_1");

		} catch (Exception ex) {
			Kogger.debug(Utility.class, "asc2ksc : " + ex.getMessage());
		}

		return new_str;
	}

	public static String checkNVL(String str) {
		if (str == null || str.equals("null"))
			return "";
		else
			return str.trim();
	}

	public static String checkNVL(Object obj) {
		if (obj == null || ((String)obj).equals("null")) {
			return "";
		} else {
			return ((String)obj).trim();
		}
	}

	public static String checkNVL(String str, String res) {
		if (str == null || str.equals("null"))
			return res;
		else
			return str;
	}

	public static void massageBox(JFrame desktop, String msg, String title) {
		MessageBox mbox = new MessageBox(desktop, msg, title, 0);
		mbox.setModal(true);
		mbox.setVisible(true);
	}

	public static void massageBox(Throwable log) {
		String llog = log.toString() + "\n";
		StackTraceElement st[] = log.getStackTrace();

		for (int i = 0; i < st.length; i++)
			llog = llog + "\tat " + st[i] + "\n";

		MessageBox mbox = new MessageBox(llog, "error_message", 0);
		mbox.setModal(true);
		mbox.setVisible(true);
	}

	public static void massageBox(String msg, String title) {
		MessageBox mbox = new MessageBox(msg, title, 0);
		mbox.setModal(true);
		mbox.setVisible(true);
	}

	/**
	 * 지정된 Format으로 전달 받은 문자열을 돌려준다. 입력받은 전달 받은 문자열이 숫자일때 해당된다.
	 * 숫자가 아닌 값이 들어오면 입력값을 그대로 돌려준다.<BR><BR>
	 *
	 * 사용예) getFormattedNumber("2001020.42345","'$'#,###.####")<BR>
	 * 결 과 ) $2,001,020.4234<BR><BR>
	 *
	 * Format은 J2SE의 MessageFormat의 Documentation을 참고한다.
	 *
	 * @return java.lang.String
	 * @param pInstr String
	 * @param pInformat String
	 */
	public static String getFormattedNumber(String pInstr, String pInformat) {

		String rStr = "";

		try {
			Object[] testArgs = { new Long(pInstr) };
			MessageFormat form = new MessageFormat("{0,number," + pInformat + "}");
			rStr = form.format(testArgs);
		} catch (Exception e) {
			Kogger.error(Utility.class, e);
		}

		return rStr;
	}

	public static String getFormattedNumber(String pInstr) {

		String rStr = "";

		try {
			Object[] testArgs = { (new BigDecimal(pInstr)).setScale(4, BigDecimal.ROUND_HALF_UP) };
			MessageFormat form = new MessageFormat("{0,number,#,###.####}");
			rStr = form.format(testArgs);
		} catch (Exception e) {
			Kogger.error(Utility.class, e);
		}

		return rStr;
	}

	public static String getFormattedNumber(BigDecimal instr) {

		String rStr = "";

		try {
			Object[] testArgs = { instr.setScale(4, BigDecimal.ROUND_HALF_UP) };
			MessageFormat form = new MessageFormat("{0,number,#,###.####}");
			rStr = form.format(testArgs);
		} catch (Exception e) {
			Kogger.error(Utility.class, e);
		}

		return rStr;
	}
}
