package e3ps.common.util;

import java.util.Calendar;
import java.util.Date;

import ext.ket.shared.log.Kogger;

/**
 * 
 * Simple달력
 */
public class MySimpleCalendar
{

    public static void main(String[] args)
    {
        Calendar gCal = Calendar.getInstance();
        gCal.setTime(new Date());
        int year = gCal.get(Calendar.YEAR);
        int month = gCal.get(Calendar.MONTH)+1;
        
        // 년월 출력
        Kogger.debug(MySimpleCalendar.class,  "DAY: " + year + " 년 " + month + " 월");
        
        // 일,월,~,토 출력
        for( int j=0; j<7;j++)
        {
            System.out.print( MySimpleCalendar.getDayOfWeek(j) + "\t" );
        }
        Kogger.debug(MySimpleCalendar.class, "");
        
        // 달력출력
        Object [][]cal = MySimpleCalendar.getTable(year,month);
        for( int i=0; i<cal.length;i++)
        {
            for( int j=0; j<cal[i].length;j++)
            {
                System.out.print( cal[i][j] + "\t" );
            }
            Kogger.debug(MySimpleCalendar.class, "");
        }
    }

    /**
     * 
     * @param i
     * @return
     */
    public static Object getDayOfWeek(int i)
    {
        return new Object[]{ "일", "월", "화", "수", "목", "금", "토" }[i];
    }
    /**
     * 년월에 해당하는 달력을 출력 
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static Object[][] getTable(int year, int month)
    {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, 1);

        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int firstDay = cal.get(Calendar.DAY_OF_WEEK);
        Object temp[][] = new Object[6][7];
        int daycount = 1;
        for (int i = 0; i < 6; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                if (firstDay - 1 > 0 || daycount > lastDay)
                {
                    temp[i][j] = "";
                    firstDay--;
                    continue;
                }
                else
                {
                    temp[i][j] = String.valueOf( daycount );
                }
                daycount++;
            }
        }
        
        return temp;
    }
}
