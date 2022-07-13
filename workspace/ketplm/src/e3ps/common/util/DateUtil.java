/**
 * @(#) DateUtil.java Copyright (c) jerred. All rights reserverd
 * @version 1.00
 * @since jdk 1.4.02
 * @createdate 2004. 3. 3.
 * @author Cho Sung Ok, jerred@bcline.com
 * @desc
 */

package e3ps.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import e3ps.common.jdf.config.Config;
import e3ps.common.jdf.config.ConfigImpl;
import ext.ket.shared.log.Kogger;

/**
 *
 * 	시간에 관련해서 원하는 약식에 맞추어 출력해 준다.
 *
 *	[참고 1] 그레고리식 달력은 1582 년도 10월 달력이 비정상적이다.
 *  		즉, 10월 4일 다음 날이 10월 15일이었다. 그러므로,
 *          그 달은 열흘이 부족한 21일이 한달이었다.
 *
 *	[참고 2] 영국식 달력은 1752 년도 9월 달력이 비정상적이다.
 *  		즉, 9월 2일 다음 날이 9월 14일이었다. 그러므로,
 *			그 달은 열하루가 부족한 19일이 한달이었다.
 */

public final class DateUtil {
    /*
     * 수정내용 : 'java.text.~Format' 객체들은 thread-safe하지 않으므로 static으로 사용하지 않는다.
     * 수정일자 : 2013. 5. 31
     * 수정자 : 김무준
     */
//    private static SimpleDateFormat defaultDateFormat = null;
//    private static SimpleDateFormat defaultTimeFormat = null;
//    private static SimpleDateFormat defaultMonthFormat = null;

    private static Config config = ConfigImpl.getInstance ();
    private static final String[] monthNames = { "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC" };
    static {
//        config = ConfigImpl.getInstance ();
//        defaultDateFormat = new SimpleDateFormat ( config.getString ( "date.format" ) , Locale.KOREA );
//        defaultTimeFormat = new SimpleDateFormat ( config.getString ( "time.format" ) , Locale.KOREA );
//        defaultMonthFormat = new SimpleDateFormat ( config.getString ( "month.format" ) , Locale.KOREA );

    }

    /**
     * 객체 생성을 방지하기 위해서 디폴트 생성자를 Private로 선언
     */
    private DateUtil() {}

    public static String getDateString(Date date, String type) {
        if (date == null) return "";

        if (type.equalsIgnoreCase ( "all" ) || type.equalsIgnoreCase ( "a" )) {
            return /*defaultDateFormat*/new SimpleDateFormat ( config.getString ( "date.format" ) , Locale.KOREA ).format ( date ) + " " + /*defaultTimeFormat*/new SimpleDateFormat ( config.getString ( "time.format" ) , Locale.KOREA ).format ( date );
        } else if (type.equalsIgnoreCase ( "date" ) || type.equalsIgnoreCase ( "d" )) {
            return /*defaultDateFormat*/new SimpleDateFormat ( config.getString ( "date.format" ) , Locale.KOREA ).format ( date );
        } else if (type.equalsIgnoreCase ( "time" ) || type.equalsIgnoreCase ( "t" )) {
            return /*defaultTimeFormat*/new SimpleDateFormat ( config.getString ( "time.format" ) , Locale.KOREA ).format ( date );
        } else if (type.equalsIgnoreCase ( "moth" ) || type.equalsIgnoreCase ( "m" )) {
            return /*defaultMonthFormat*/new SimpleDateFormat ( config.getString ( "month.format" ) , Locale.KOREA ).format ( date );
        } else {
            return date.toString ();
        }
    }

    /**
     * 현재 날을 설정된 형식으로 출력한다.
     * @param timestamp
     * @param type all[a] | date[d] | time[t]
     * @return java.lang.String
     */
    public static String getDateString(Timestamp timestamp, String type)
    {
        if(timestamp == null) return "";
        return getDateString(new Date(timestamp.getTime()), type);
    }
    /**
     *   주어진 Timestamp의 시간을 입력된 fomat형태로 RETURN
     *   @param  Timestamp time
     *   @param String format(ex:yyyy-MM-dd HH:mm:ss:SS)
     *   @return String str
     *   @since 2005.01
     */
     public static String getTimeFormat(Timestamp time, String format) {

         if( time == null ) return "";
         SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.KOREA );
         String str = formatter.format(time);
         return str;
     }
     /**
    *   주어진 시간String을 주어진 FormatString형태로 인식하는 Timestamp로 변환한다.
    *   @param  timeString
    *   @param  formatString
    *   @return Timestamp
    *   @since 2005.01
     */
     public static Timestamp getTimestampFormat(String timeString, String formatString) throws Exception{
         SimpleDateFormat format = new SimpleDateFormat(formatString);
         Date date = format.parse(timeString);
         return new Timestamp(date.getTime());
     }

    /**
     * 현재 날을 설정된 형식으로 출력한다.
     * @param type all[a] | date[d] | time[t]
     * @return
     */
    public static String getCurrentDateString(String type) {
        Date currentDate = new Date ();

        if (type.equalsIgnoreCase ( "all" ) || type.equalsIgnoreCase ( "a" )) {
            return /*defaultDateFormat*/new SimpleDateFormat ( config.getString ( "date.format" ) , Locale.KOREA ).format ( currentDate ) + " " + /*defaultTimeFormat*/new SimpleDateFormat ( config.getString ( "time.format" ) , Locale.KOREA ).format ( currentDate );
        } else if (type.equalsIgnoreCase ( "date" ) || type.equalsIgnoreCase ( "d" )) {
            return /*defaultDateFormat*/new SimpleDateFormat ( config.getString ( "date.format" ) , Locale.KOREA ).format ( currentDate );
        } else if (type.equalsIgnoreCase ( "time" ) || type.equalsIgnoreCase ( "t" )) {
            return /*defaultTimeFormat*/new SimpleDateFormat ( config.getString ( "time.format" ) , Locale.KOREA ).format ( currentDate );
        } else if (type.equalsIgnoreCase ( "month" ) || type.equalsIgnoreCase ( "m" )) {
            return /*defaultMonthFormat*/new SimpleDateFormat ( config.getString ( "month.format" ) , Locale.KOREA ).format ( currentDate );

        } else {
            return currentDate.toString ();
        }
    }

    public static String getCurrentDateString(SimpleDateFormat dateFormat) {
        Date currentDate = new Date ();
        return dateFormat.format ( currentDate );
    }

    public static Timestamp getCurrentTimestamp(){
        Date currentDate = new Date ();
        return new Timestamp(currentDate.getTime());
    }

    /**
     * 웹페이지 리스트에 날짜를 표시하기 위해서 적당한 형태로 변경시켜주는 Method <br>
     * 오늘과 같은 날짜일 경우에는 시간만 표시하고 <br>
     * 오늘과 다른 날일 경우에는 날짜만 표시한다. <br>
     *
     * @param date <code>java.lang.String</code> 변환할 날짜 정보 String
     * @return <code>java.lang.String</code> 변환된 날짜 정보 String
     */
    public static String getDateToWeb(Date date) {
        if (date == null) return "";
        String currentDate = /*defaultDateFormat*/new SimpleDateFormat ( config.getString ( "date.format" ) , Locale.KOREA ).format ( new Date () );
        String paramDate = /*defaultDateFormat*/new SimpleDateFormat ( config.getString ( "date.format" ) , Locale.KOREA ).format ( date );
        String paramTime = /*defaultTimeFormat*/new SimpleDateFormat ( config.getString ( "time.format" ) , Locale.KOREA ).format ( date );

        if (currentDate.equals ( paramDate )) {
            return paramTime;
        }
        else {
            return paramDate;
        }


    }

    public static boolean isToday(Date date) {
        String currentDate = /*defaultDateFormat*/new SimpleDateFormat ( config.getString ( "date.format" ) , Locale.KOREA ).format ( new Date () );
        String paramDate = /*defaultDateFormat*/new SimpleDateFormat ( config.getString ( "date.format" ) , Locale.KOREA ).format ( date );

        if (currentDate.equals ( paramDate )) return true;
        else return false;
    }

    public static String getDateString(Date date, SimpleDateFormat format) {
        return format.format ( date );
    }

    public static Date parseDateStr(String dateStr) throws ParseException {
        return /*defaultDateFormat*/new SimpleDateFormat ( config.getString ( "date.format" ) , Locale.KOREA ).parse ( dateStr );
    }

    public static Date parseDateStr(SimpleDateFormat format, String dateStr)
        throws ParseException {
        return format.parse ( dateStr );
    }

    public static String getToDay() {
        String toDay = "";
        try {
            SimpleDateFormat sdFormat = new SimpleDateFormat ( "yyyy/MM/dd" );
            toDay = sdFormat.format ( Calendar.getInstance ().getTime () );
        } catch (Exception e) {
            Kogger.error ( "Exception : " + e.getMessage () );
        }
        return toDay;
    }

    public static String getToDay(String format) {
        String toDay = "";
        try {
            SimpleDateFormat sdFormat = new SimpleDateFormat ( format );
            toDay = sdFormat.format ( Calendar.getInstance ().getTime () );
        } catch (Exception e) {
            Kogger.error ( "Exception : " + e.getMessage () );
        }
        return toDay;
    }

    public static Timestamp convertDate(String str) {
        if(str == null || str.length() == 0) return null;
        Timestamp convertDate = ( new Timestamp ( new SimpleDateFormat ("yyyy/MM/dd:HH-mm-ss" ).parse (str + ":12-59-59" ,	new ParsePosition ( 0 ) ).getTime () ) );
        return convertDate;
    }

    public static Timestamp convertStartDate(String str) {
        if(str == null || str.length() == 0) return null;
        Timestamp convertDate = ( new Timestamp ( new SimpleDateFormat ("yyyy/MM/dd" ).parse ( str , new ParsePosition ( 0 ) ).getTime () ) );
        return convertDate;
    }

    public static Timestamp convertEndDate(String str) {
        if(str == null || str.length() == 0) return null;
        Timestamp convertDate = ( new Timestamp ( new SimpleDateFormat ("yyyy/MM/dd:HH-mm-ss" ).parse (str + ":23-59-59" ,	new ParsePosition ( 0 ) ).getTime () ) );
        return convertDate;
    }

    //converDate 임시 수정 -by syoh
    public static Timestamp convertDate2(String str) {
        if(str == null || str.length() == 0) return null;
        Timestamp convertDate = ( new Timestamp ( new SimpleDateFormat ("yyyy-MM-dd:HH-mm-ss" ).parse (str + ":12-59-59" ,	new ParsePosition ( 0 ) ).getTime () ) );
        return convertDate;
    }

    public static Timestamp convertStartDate2(String str) {
        if(str == null || str.length() == 0) return null;
        Timestamp convertDate = ( new Timestamp ( new SimpleDateFormat ("yyyy-MM-dd" ).parse ( str , new ParsePosition ( 0 ) ).getTime () ) );
        return convertDate;
    }

    public static Timestamp convertEndDate2(String str) {
        if(str == null || str.length() == 0) return null;
        Timestamp convertDate = ( new Timestamp ( new SimpleDateFormat ("yyyy-MM-dd:HH-mm-ss" ).parse (str + ":23-59-59" ,	new ParsePosition ( 0 ) ).getTime () ) );
        return convertDate;
    }

    /**
     * 일년 간 달력의 월별 날짜수 배열을 구한다.
     * @param year
     * @return
     */
    public static int[] getMonthDaysArray(int year) {
        int[] monthDayCaseOne = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        int[] monthDayCaseTwo = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

        if (year <= 1582) {
            if ((year % 4) == 0) {
                if (year == 4) {
                    return monthDayCaseOne;
                }
                return monthDayCaseTwo;
            }
        } else {
            if (((year % 4) == 0) && ((year % 100) != 0)) {
                return monthDayCaseTwo;
            } else if ((year % 400) == 0) {
                return monthDayCaseTwo;
            }
        }
        return monthDayCaseOne;
    }

    /**
     * 지정된 년도와 달에 따른 요일 편차를 구한다.
     * @param year
     * @param month
     * @return
     */
    public static int addWeekDays(int year, int month) {
        int[] b1 = { 3, 0, 3, 2, 3, 2, 3, 3, 2, 3, 2, 3 };
        int[] b2 = { 3, 1, 3, 2, 3, 2, 3, 3, 2, 3, 2, 3 };
        int i = 0;
        int rval = 0;

        if (year <= 1582) {
            if ((year % 4) == 0) {
                if (year == 4) {
                    for (i = 0; i < month - 1; i++) rval += b1[i];
                } else {
                    for (i = 0; i < month - 1; i++) rval += b2[i];
                }
            } else {
                for (i = 0; i < month - 1; i++) rval += b1[i];
            }
        } else {
            if (((year % 4) == 0) && ((year % 100) != 0)) {
                for (i = 0; i < month - 1; i++) rval += b2[i];
            } else if ((year % 400) == 0) {
                for (i = 0; i < month - 1; i++) rval += b2[i];
            } else {
                for (i = 0; i < month - 1; i++) rval += b1[i];
            }
        }

        return rval;
    }

    /**
     * 지정한 년도의 총 날짜 수를 구한다.
     * @param year
     * @return
     */
    public static int getDaysInYear(int year) {
        if (year > 1582) {
            if (year % 400 == 0) return 366;
            else if (year % 100 == 0) return 365;
            else if (year % 4 == 0) return 366;
            else return 365;
        } else if (year == 1582) {
            return 355;
        } else if (year > 4) {
            if (year % 4 == 0) return 366;
            else return 365;
        } else if (year > 0) {
            return 365;
        } else {
            return 0;
        }
    }

    /**
     * 지정한 년도, 지정한 월의 총 날짜 수를 구한다.
     * @param month
     * @param year
     * @return
     */
    public static int getDaysInMonth(int year, int month) {
        if (month < 1 || month > 12) throw new RuntimeException("Invalid month: " + month);

        int[] dayInMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        if (month != 2 && month >= 1 && month <= 12 && year != 1582) return dayInMonth[month - 1];
        if (month != 2 && month >= 1 && month <= 12 && year == 1582) {
            if (month != 10) return dayInMonth[month - 1];
            else  return dayInMonth[month - 1] - 10;
        }
        if (month != 2) return 0;

        // m == 2 (즉 2월)
        if (year > 1582) {
            if (year % 400 == 0) return 29;
            else if (year % 100 == 0) return 28;
            else if (year % 4 == 0) return 29;
            else return 28;
        } else if (year == 1582) {
            return 28;
        } else if (year > 4) {
            if (year % 4 == 0) return 29;
            else return 28;
        } else if (year > 0) {
            return 28;
        } else {
            throw new RuntimeException("Invalid year: " + year);
        }
    }

    /**
     * 지정한 년도의 지정한 월의 첫날 부터 지정한 날 까지의 날짜 수를 구한다.
     * @param day
     * @param month
     * @param year
     * @return
     */
    public static int getDaysFromMonthFirst(int year, int month, int day) {
        if (month < 1 || month > 12) throw new RuntimeException("Invalid month " + month + " in " + day + "/" + month + "/" + year);
        int max = getDaysInMonth(year, month);
        if (day >= 1 && day <= max) return day;
        else throw new RuntimeException("Invalid date " + day + " in " + day + "/" + month + "/" + year);
    }

    /**
     * 지정한 년도의 첫날 부터 지정한 월의 지정한 날 까지의 날짜 수를 구한다.
     * @param day
     * @param month
     * @param year
     * @return
     */
    public static int getDaysFromYearFirst(int year, int month, int day) {
        if (month < 1 || month > 12) throw new RuntimeException("Invalid month " + month + " in " + day + "/" + month + "/" + year);

        int max = getDaysInMonth(year, month);
        if (day >= 1 && day <= max) {
            int sum = day;
            for (int j = 1; j < month; j++) sum += getDaysInMonth(year, j);
            return sum;
        } else {
            throw new RuntimeException("Invalid date " + day + " in " + day + "/" + month + "/" + year);
        }
    }

    /**
     * 2000년 1월 1일 부터 지정한 년, 월, 일 까지의 날짜 수를 구한다.
     * 2000년 1월 1일 이전의 경우에는 음수를 리턴한다.
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static int getDaysFrom21Century(int year, int month, int day) {
        if (year >= 2000) {
            int sum = getDaysFromYearFirst(year, month, day);
            for (int j = year - 1; j >= 2000; j--) sum += getDaysInYear(j);
            return sum - 1;
        } else if (year > 0 && year < 2000) {
            int sum = getDaysFromYearFirst(year, month, day);
            for (int j = 1999; j >= year; j--) sum -= getDaysInYear(year);
            return sum - 1;
        } else {
            throw new RuntimeException("Invalid year " + year + " in " + day + "/" + month + "/" + year);
        }
    }

    /**
     * 지정한 년도의 지정한 월의 첫날 부터 지정한 날 까지의 날짜 수를 구한다.
     * @param s
     * @return
     */
    public static int getDaysFromMonthFirst(String s) {
        int d, m, y;
        if (s.length() == 8) {
            y = Integer.parseInt(s.substring(0, 4));
            m = Integer.parseInt(s.substring(4, 6));
            d = Integer.parseInt(s.substring(6));
            return getDaysFromMonthFirst(y, m, d);
        } else if (s.length() == 10) {
            y = Integer.parseInt(s.substring(0, 4));
            m = Integer.parseInt(s.substring(5, 7));
            d = Integer.parseInt(s.substring(8));
            return getDaysFromMonthFirst(y, m, d);
        } else if (s.length() == 11) {
            d = Integer.parseInt(s.substring(0, 2));
            String strM = s.substring(3, 6).toUpperCase();
            m = 0;
            for (int j = 1; j <= 12; j++) {
                if (strM.equals(monthNames[j-1])) {
                    m = j;
                    break;
                }
            }
            if (m < 1 || m > 12) throw new RuntimeException("Invalid month name: " + strM + " in " + s);
            y = Integer.parseInt(s.substring(7));
            return getDaysFromMonthFirst(y, m, d);
        } else {
            throw new RuntimeException("Invalid date format: " + s);
        }
    }

    /**
     * 지정한 년도의 첫날 부터 지정한 월의 지정한 날 까지의 날짜 수를 구한다.
     * @param s
     * @return
     */
    public static int getDaysFromYearFirst(String s) {
        int d, m, y;
        if (s.length() == 8) {
            y = Integer.parseInt(s.substring(0, 4));
            m = Integer.parseInt(s.substring(4, 6));
            d = Integer.parseInt(s.substring(6));
            return getDaysFromYearFirst(y, m, d);
        } else if (s.length() == 10) {
            y = Integer.parseInt(s.substring(0, 4));
            m = Integer.parseInt(s.substring(5, 7));
            d = Integer.parseInt(s.substring(8));
            return getDaysFromYearFirst(y, m, d);
        } else if (s.length() == 11) {
            d = Integer.parseInt(s.substring(0, 2));
            String strM = s.substring(3, 6).toUpperCase();
            m = 0;
            for (int j = 1; j <= 12; j++) {
                if (strM.equals(monthNames[j-1])) {
                    m = j;
                    break;
                }
            }
            if (m < 1 || m > 12) throw new RuntimeException("Invalid month name: " + strM + " in " + s);
            y = Integer.parseInt(s.substring(7));
            return getDaysFromYearFirst(y, m, d);
        } else {
            throw new RuntimeException("Invalid date format: " + s);
        }
    }

    /**
     * 2000년 1월 1일 부터 지정한 년, 월, 일 까지의 날짜 수를 구한다.
     * 2000년 1월 1일 이전의 경우에는 음수를 리턴한다.
     * @param s
     * @return
     */
    public static int getDaysFrom21Century(String s) {
        int d, m, y;
        if (s.length() == 8) {
            y = Integer.parseInt(s.substring(0, 4));
            m = Integer.parseInt(s.substring(4, 6));
            d = Integer.parseInt(s.substring(6));
            return getDaysFrom21Century(y, m, d);
        } else if (s.length() == 10) {
            y = Integer.parseInt(s.substring(0, 4));
            m = Integer.parseInt(s.substring(5, 7));
            d = Integer.parseInt(s.substring(8));
            return getDaysFrom21Century(y, m, d);
        } else if (s.length() == 11) {
            d = Integer.parseInt(s.substring(0, 2));
            String strM = s.substring(3, 6).toUpperCase();
            m = 0;
            for (int j = 1; j <= 12; j++) {
                if (strM.equals(monthNames[j-1])) {
                    m = j;
                    break;
                }
            }
            if (m < 1 || m > 12) throw new RuntimeException("Invalid month name: " + strM + " in " + s);
            y = Integer.parseInt(s.substring(7));
            return getDaysFrom21Century(y, m, d);
        } else {
            throw new RuntimeException("Invalid date format: " + s);
        }
    }

    /**
     * (양 끝 제외) 날짜 차이 구하기
     * @param s1
     * @param s2
     * @return
     */
    public static int getDaysBetween(String s1, String s2) {
        int y1 = getDaysFrom21Century(s1);
        int y2 = getDaysFrom21Century(s2);
        return y1 - y2 - 1;
    }

    /**
     * 날짜 차이 구하기
     * @param s1
     * @param s2
     * @return
     */
    public static int getDaysDiff(String s1, String s2) {
        int y1 = getDaysFrom21Century(s1);
        int y2 = getDaysFrom21Century(s2);
        return y1 - y2;
    }

    /**
     * (양 끝 포함) 날짜 차이 구하기
     * @param s1
     * @param s2
     * @return
     */
    public static int getDaysFromTo(String s1, String s2) {
        int y1 = getDaysFrom21Century(s1);
        int y2 = getDaysFrom21Century(s2);
        return y1 - y2 + 1;
    }

    /**
     * 지정한 년도, 지정한 월에 지정한 요일이 들어 있는 회수를 구한다.
     * @param year
     * @param month
     * @param weekDay
     * @return
     */
    public static int getWeekdaysInMonth(int year, int month, int weekDay ) {
        int week = ((weekDay - 1) % 7);
        int days = getDaysInMonth(year, month);
        int sum = 6;   // 2000년 1월 1일은 토요일
        if (year >= 2000) {
            for (int i = 2000; i < year; i++) sum += getDaysInYear(i);
        } else {
            for (int i = year; i < 2000; i++) sum -= getDaysInYear(i);
        }
        for (int i = 1; i < month; i++) sum += getDaysInMonth(year, i);


        int firstWeekDay = sum % 7;
        if (firstWeekDay < 0) firstWeekDay += 7;

        int n = firstWeekDay + days;
        int counter = (n / 7) + (((n % 7) > week) ? 1 : 0);
        if (firstWeekDay > week) counter--;

        return counter;
    }

    /**
     * 지정한 년도의 지정한 달에 지정한 요일이 들어 있는 회수를 구한다.
     * @param year
     * @param month
     * @param weekName
     * @return
     */
    public static int getWeekdaysInMonth(int year, int month, String weekName) {
        StringBuffer weekNames1 = new StringBuffer("일월화수목금토");
        StringBuffer weekNames2 = new StringBuffer("日月火水木金土");
        String[] weekNames3 = { "sun", "mon", "tue", "wed", "thu", "fri", "sat" };

        int n = weekNames1.indexOf(weekName);
        if (n < 0) n = weekNames2.indexOf(weekName);
        if (n < 0) {
            String str = weekName.toLowerCase();
            for (int i = 0; i < weekNames3.length; i++) {
                if (str.equals(weekNames3[i])) {
                    n = i;
                    break;
                }
            }
        }

        if (n < 0) throw new RuntimeException("Invalid week name: " + weekName);

        return getWeekdaysInMonth(year, month, n + 1);
    }

    /**
     * 2000년 1월 1일 기준을 n일 경과한 날짜 구하기
     * @param elapsed
     * @return	yyyy-mm-dd 양식의 String 타입
     */
    public static String getDateStringFrom21Century(int elapsed) {
        int y = 2000;
        int m = 1;
        int d = 1;

        int n = elapsed + 1;
        int j = 2000;
        if (n > 0) {
            while (n > getDaysInYear(j)) {
                n -= getDaysInYear(j);
                j++;
            }
            y = j;

            int i = 1;
            while (n > getDaysInMonth(y, i)) {
                n -= getDaysInMonth(y, i);
                i++;
            }
            m = i;
            d = n;
        } else if (n < 0) {
            while (n < 0) {
                n += getDaysInYear(j - 1);
                j--;
            }
            y = j;

            int i = 1;
            while (n > getDaysInMonth(y, i)) {
                n -= getDaysInMonth(y, i);
                i++;
            }
            m = i;
            d = n;
        }

        String strY = "" + y;
        String strM = "";
        String strD = "";

        if (m < 10) strM = "0" + m;
        else strM = "" + m;

        if (d < 10) strD = "0" + d;
        else strD = "" + d;

        return strY + "/" + strM + "/" + strD;
    }

    /**
     * 지정한 날짜를 offset일 경과한 날짜 구하기
     * @param year
     * @param month
     * @param day
     * @param offset
     * @return	yyyy-mm-dd 양식의 String 타입
     */
    public static String addDate(int year, int month, int day, int offset) {
        int z = getDaysFrom21Century(year, month, day);
        int n = z + offset;
        return getDateStringFrom21Century(n);
    }

    public static Timestamp getOneMonthBefore(int offset){
        Timestamp stamp = null;
        String today = getDateString(getCurrentTimestamp(), new SimpleDateFormat("yyyy-MM-dd"));
//        Kogger.debug(getClass(), "today : " + today);
        int z = getDaysFrom21Century(today);
        int n = z - offset;
        String oneMonthBefore = getDateStringFrom21Century(n);
//        Kogger.debug(getClass(), "oneMonthBefore : " + oneMonthBefore);
        stamp = convertDate(oneMonthBefore);
        return stamp;
    }

    public static int getDuration(Date date1, Date date2){
//    	Date pre = null;
//    	Date after = null;
//    	if(date1.before(date2)){
//    		pre = date1;
//    		after = date2;
//    	}else{
//    		after =date1;
//    		pre =date2;
//    	}
        long oneDayMillis = 24*60*60*1000;
        int day = (int)((date1.getTime() - date2.getTime())/oneDayMillis);

        if(day < 0){
            day = day * -1;
        }

        return day;
    }

    public static Timestamp getDelayTime(Timestamp time, int delayDay){
        long oneDay = 24*60*60*1000;

        long delayTimelong = time.getTime() + (delayDay * oneDay);

        Timestamp delayTime = new Timestamp(delayTimelong);

        return delayTime;

    }


    /**
     * 지정한 날짜를 offset일 경과한 날짜 구하기
     * @param date
     * @param offset
     * @return  yyyy-mm-dd 양식의 String 타입
     */
    public static String addDate(String date, int offset) {
        int z = getDaysFrom21Century(date);
        int n = z + offset;
        return getDateStringFrom21Century(n);
    }


    /**
     *
     * @author
     * @return 현재의 달의 첫번째 날짜의 값을 가지는 Default Pattern(yyyy/MM/dd)의 형태로 return함
     */

    public static String getFirstDayOfMonth(){
        Calendar calendar = Calendar.getInstance(Locale.KOREA);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat sdate = new SimpleDateFormat("yyyy/MM/dd");
        return sdate.format(calendar.getTime());
    }


    /**
     *  현재 날짜에 대한 현재 년도를 반환하는 함수
     *
     * @return 현재 년도 값
     */
    public static String getThisYear() {

        String currentDate = DateUtil.getToDay();
        if(!currentDate.equals("-")) {
            return currentDate.substring(0, 4);
        }
        return "-";
    }


    /**
     *  현재 날짜에 대한 현재 달을 반환하는 함수
     *
     * @return 현재 달 값 ( 2자리 )
     */
    public static String getThisMonth() {

        String currentDate = DateUtil.getToDay();
        if(!currentDate.equals("-")) {
            return currentDate.substring(5, 7);
        }
        return "-";
    }
    
    /**
     * 
     * 
     * @param date
     * @param day
     * @return
     * @throws Exception
     * @메소드명 : isDaysBeetween
     * @작성자 : 황정태
     * @작성일 : 2017. 4. 14.
     * @설명 :  주어진 날짜가 주어진 기간안에 포함되어있는지 판단하는 함수
     * @수정이력 - 수정일,수정자,수정내용  					   
     *
     */
    public static boolean isDaysBeetween(Timestamp date, int day) throws Exception{
	
	boolean IsInclusion = false;
	
	String createDate = DateUtil.getDateString(date, "date");
	createDate = DateUtil.addDate(createDate, day);
        
        Date date1 = DateUtil.getTimestampFormat(createDate, "yyyy/MM/dd");
        Date date2 = DateUtil.getCurrentTimestamp();
        
        long oneDayMillis = 24*60*60*1000;
        int duration = (int)((date1.getTime() - date2.getTime())/oneDayMillis);
        
        if(duration > 0){
            IsInclusion = true;
        }
        
	return IsInclusion;
    }

    public static void main(String[] args) {
//		Kogger.debug(getClass(), "----------------------------------------------");
//		Kogger.debug(getClass(), "지정한 년도에 포함된 날수 구하기:");
//		int x = getDaysInYear(2002);
//		Kogger.debug(getClass(), x);
//		Kogger.debug(getClass(), "지정한 년도의 지정한 달에 포함된 날수 구하기:");
//		int y = getDaysInMonth( 2002,1);
//		Kogger.debug(getClass(), y);
//		Kogger.debug(getClass(), "2000년 1월 1일 이후 경과한 날수 구하기:");
//		int z = getDaysFromYearFirst( 2002, 9,11);
//		Kogger.debug(getClass(), z);
//		int z1 = getDaysFromYearFirst("20020911");
//		Kogger.debug(getClass(), z1);
//		int z2 = getDaysFromYearFirst("2002/09/11");
//		Kogger.debug(getClass(), z2);
//		int z3 = getDaysFromYearFirst("2002-09-11");
//		Kogger.debug(getClass(), z3);
//		int z4 = getDaysFromYearFirst("11-Sep-2002");
//		Kogger.debug(getClass(), z4);
//		int z5 = getDaysFromMonthFirst("11-Sep-2002");
//		Kogger.debug(getClass(), z5);
//		int z6 = getDaysFrom21Century(2002, 9, 11);
//		Kogger.debug(getClass(), z6);
//		int z7 = getDaysFrom21Century("2002-09-11");
//		Kogger.debug(getClass(), z7);
//		int z8 = getDaysFrom21Century("11-Sep-2002");
//		Kogger.debug(getClass(), z8);
//		int u = getDaysDiff("11-Sep-2002", "01-Jan-2002");
//		Kogger.debug(getClass(), u);
//		int u2 = getDaysDiff("01-Jan-2002", "11-Sep-2002");
//		Kogger.debug(getClass(), u2);
//		int u3 = getDaysDiff("11-Sep-2002", "31-Dec-2000");
//		Kogger.debug(getClass(), u3);
//		int u4 = getDaysDiff("2002-09-10", "2002-09-01");
//		Kogger.debug(getClass(), u4);
//		int u5 = getDaysFrom21Century("31-Dec-2000");
//		Kogger.debug(getClass(), u5);
//		int u6 = getDaysFrom21Century("2000-12-31");
//		Kogger.debug(getClass(), u6);
//		int u7 = getDaysFrom21Century("1999-12-31");
//		Kogger.debug(getClass(), u7);
//		int u8 = getDaysFrom21Century("2000-01-01");
//		Kogger.debug(getClass(), u8);
//
//		Kogger.debug(getClass(), "-------------------------------------------------------------");
//		Kogger.debug(getClass(), "지정한 년도의 지정한 달에 포함된 지정한 요일의 개수 구하기:");
//		Kogger.debug(getClass(), "2002년 11월에 금요일 개수는 " + getWeekdaysInMonth(2002, 11, 6));
//		Kogger.debug(getClass(), "2002년 11월에 목요일 개수는 " + getWeekdaysInMonth(2002, 11, "목"));
//		Kogger.debug(getClass(), "2002년 12월에 금요일 개수는 " + getWeekdaysInMonth(2002, 12, 6));
//		Kogger.debug(getClass(), "2002년 12월에 金요일 개수는 " + getWeekdaysInMonth(2002, 12, "金"));
//		Kogger.debug(getClass(), "2002년 12월에 Tuesday 개수는 " + getWeekdaysInMonth(2002, 12, "tue"));
//		Kogger.debug(getClass(), "2002년 12월에 Saturday 개수는 " + getWeekdaysInMonth(2001, 12, "sat"));
//		Kogger.debug(getClass(), "1998년 12월에 Saturday 개수는 " + getWeekdaysInMonth(1998, 12, "sat"));
//
//		Kogger.debug(getClass(), "-------------------------------------------------------------");
//		Kogger.debug(getClass(), "1990년 12월에 Sunday 개수는 " + getWeekdaysInMonth(1990, 12, "sun"));
//		Kogger.debug(getClass(), "            Monday 개수는 " + getWeekdaysInMonth(1990, 12, "mon"));
//		Kogger.debug(getClass(), "            Tuesday 개수는 " + getWeekdaysInMonth(1990, 12, "tue"));
//		Kogger.debug(getClass(), "            Wednsday 개수는 " + getWeekdaysInMonth(1990, 12, "wed"));
//		Kogger.debug(getClass(), "            Thursday 개수는 " + getWeekdaysInMonth(1990, 12, "thu"));
//		Kogger.debug(getClass(), "            Friday 개수는 " + getWeekdaysInMonth(1990, 12, "fri"));
//		Kogger.debug(getClass(), "            Saturday 개수는 " + getWeekdaysInMonth(1990, 12, "sat"));
//		Kogger.debug(getClass(), "-------------------------------------------------------------");
//
//		Kogger.debug(getClass(), "2002년 1월 1일 기준으로 지정한 날수 만큼 경과한 날짜 구하기:");
//		Kogger.debug(getClass(), getDateStringFrom21Century(366));
//		Kogger.debug(getClass(), getDateStringFrom21Century(-365));
//		Kogger.debug(getClass(), addDate(2002, 1, 1, 364));
//		Kogger.debug(getClass(), addDate("2002/01/01", 365));
    }

    public static Timestamp getADDHour(Timestamp time, int hour){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time.getTime());
        c.add(Calendar.HOUR, hour);
        Timestamp timestamp = new Timestamp(c.getTimeInMillis());
        return timestamp;
    }
    
    public static String trancKST(String kst){
        String strDate = null;
        try {
            SimpleDateFormat recvSimpleFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
            
            /** 여기에 원하는 포맷을 넣어주면 된다 */
            SimpleDateFormat tranSimpleFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);   

            Date data = recvSimpleFormat.parse(kst);
            strDate = tranSimpleFormat.format(data);
        } catch (ParseException e) {
            Kogger.error(DateUtil.class, e);
        }
        return strDate;
    }
}
