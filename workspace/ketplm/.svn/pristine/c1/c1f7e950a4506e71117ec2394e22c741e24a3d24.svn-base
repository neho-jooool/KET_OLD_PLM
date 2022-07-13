/*
 * @(#) ManageSequence.java
 * Copyright (c) e3ps. All rights reserverd
 */
package e3ps.common.util;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.pds.oracle81.OraclePds81;
import wt.query.ClassAttribute;
import wt.query.KeywordExpression;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.query.SubSelectExpression;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import ext.ket.shared.log.Kogger;

/**
 * 데이터베이스의 시퀀스를 가져온다.
 * 
 * @version 1.00
 * @since jdk 1.4.2
 * @author Seung-hwan Choi, skyprda@e3ps.com
 */

public class ManageSequence
{
    /**
     * Sequence 를 가져온다
     * 
     * @param seqName
     *            가져올 시퀀스 이름
     * @param format
     *            필요한 자릿수 ex) "000"
     */
    public static String getSeqNo(String seqName, String format)
    {
        String seqNo = format;
        String temp = seqName;

        try
        {
            temp = temp.replace('-', '_');
            temp = temp.replace(' ', '_');
            seqNo = PersistenceHelper.manager.getNextSequence(temp);
        }
        catch (Exception exception)
        {
            createNewSequence(temp, 1, 1);
            try
            {
                seqNo = PersistenceHelper.manager.getNextSequence(temp);
            }
            catch (Exception exception1)
            {
                Kogger.debug(ManageSequence.class, "Error!! 시퀀스를 가져오는중에 에러가 발생했습니다.");
            }
        }
        DecimalFormat decimalformat = new DecimalFormat(format);
        return decimalformat.format(Long.parseLong(seqNo));
    }
    
    public static String generateNumberSpec(String seqName, String format, Class target, String colName) 
    throws WTException, WTPropertyVetoException{

        Date date = Calendar.getInstance().getTime();
        String yyyy = new SimpleDateFormat("yyyy").format(date);
        String key = seqName+"-"+yyyy.substring(2)+"-";
        
        QuerySpec qs = new QuerySpec();		
        int idx = qs.appendClassList(target, false);
 		
        ClassAttribute ca = new ClassAttribute(target, colName);
        SQLFunction countfunction = SQLFunction.newSQLFunction(SQLFunction.MAXIMUM, ca);
        countfunction.setColumnAlias("indexbydate");
        qs.appendSelect(countfunction, new int[]{idx}, false);
        qs.appendWhere(new SearchCondition(target, colName, SearchCondition.LIKE, key+"%"), new int[]{idx});

        SubSelectExpression subfrom =  new SubSelectExpression(qs);
        subfrom.setFromAlias(new String[]{"SUB0"}, 0);
    
        QuerySpec main = new QuerySpec();
        main.setAdvancedQueryEnabled(true);
        int main_idx = main.appendFrom(subfrom);
 	
        KeywordExpression ke = new KeywordExpression("indexbydate");
        main.appendSelect(ke, new int[]{main_idx}, false);
 	
        QueryResult qr = PersistenceHelper.manager.find(main);
        
        String rstr = "";
        Object idxObj[] = null;
        if(qr.hasMoreElements()) {
     	   idxObj = (Object[])qr.nextElement();
     	   if(idxObj[0] instanceof BigDecimal) {
     		   BigDecimal bd = (BigDecimal)idxObj[0];
     		   rstr = String.valueOf(bd);
     	   }
     	   else if(idxObj[0] instanceof String) {
     		   rstr = (String)idxObj[0];
     	   }
     	   else {
     		   //Kogger.debug(getClass(), "XXXXXXXXXXXXx : " + idxObj[0].toString());
     	   }
        }
        
        if(rstr.length() == 0) {
     	   rstr= "0";
        }
        else {
     	   rstr = rstr.substring(key.length());
        }
        
        int index = Integer.parseInt(rstr);
        
        DecimalFormatSymbols symbol = null;
        DecimalFormat df = null;
 		
        symbol = new DecimalFormatSymbols();
        df = new DecimalFormat(format, symbol);
        String idxStr = df.format(++index);
        return key+idxStr;
    }
    
    /**
     * 현재 sequence중 가장 큰 sequence의 다음값을 반환한다.
     * 
     * @param seqName
     *            sequence name
     * @param format
     *            sequence format
     * @param tabName
     *            DB table name
     * @param colName
     *            DB column name
     * @return
     */
    public static String getSeqNo(String seqName, String format, String tabName, String colName)
    {
        String result = "";
        
        OraclePds81 dds = new OraclePds81();
        Connection con = null;
        ResultSet rs = null;
        try
        {
            String sql = "SELECT NVL(MAX(" + colName + "),'1') no FROM " + tabName + " WHERE " + colName + " LIKE '" + seqName
                    + "%'";
            //Kogger.debug(getClass(), "sql = " + sql);
            
            
            con = dds.getDataSource().getConnection();
            Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(sql);
            
            while (rs.next())
            {
                if (rs.getString("no").equals("1")) result = "1";
                else 
                {
                	String no = rs.getString("no");
                	//Kogger.debug(getClass(), "ManageSequence::getSeqNo:no.substring() = "+no.substring(seqName.length()));
            		result = "" + (Integer.parseInt(no.substring(seqName.length())) + 1);
                }
            }
        }
        catch (SQLException e)
        {
            Kogger.error(ManageSequence.class, e);
        }
        finally
        {
            try
            {
                if (rs != null) rs.close();
                if (con != null) con.close();
            }
            catch (SQLException e)
            {
                Kogger.error(ManageSequence.class, e);
            }
        }
        DecimalFormat decimalformat = new DecimalFormat(format);
        return decimalformat.format(Long.parseLong(result));
    }

    /**
     * interval에 지정된 간격만큼 늘어나는 시퀀스를 반환한다.
     * 
     * @param seqName
     *            시퀀스 이름
     * @param interval
     *            시퀀스 간격
     * @param format
     * @return
     */
    public static String getSeqNo(String seqName, int interval, String format)
    {
        String seqNo = format;
        String temp = seqName;

        try
        {
            temp = temp.replace('-', '_');
            temp = temp.replace(' ', '_');
            seqNo = PersistenceHelper.manager.getNextSequence(temp);
        }
        catch (Exception exception)
        {
            createNewSequence(temp, interval, interval);
            try
            {
                seqNo = PersistenceHelper.manager.getNextSequence(temp);
            }
            catch (Exception exception1)
            {
                Kogger.debug(ManageSequence.class, "Error!! 시퀀스를 가져오는중에 에러가 발생했습니다.");
            }
        }

        DecimalFormat decimalformat = new DecimalFormat(format);
        return decimalformat.format(Long.parseLong(seqNo));
    }

    /**
     * 시퀀스이름이 길 경우 시퀀스 길이를 제한한다.
     * 
     * @param seqName
     *            가져올 시퀀스 이름
     * @param format
     *            필요한 자릿수 ex) "000"
     * @param position
     *            시퀀스 이름의 길이
     * @return 주어진 format의 시퀀스를 반환
     */
    public static String getSeqNo(String seqName, String format, int position)
    {
        String temp = seqName.replace('-', '_');
        temp = temp.replace(' ', '_');

        byte[] arry = temp.getBytes();
        if (arry.length >= position) temp = new String(arry, 0, position);

        return getSeqNo(temp, format);
    }

    /**
     * Sequence 를 생성해주는 Method
     */
    private static void createNewSequence(String s, int i, int j)
    {
        Connection conn = null;
        try
        {
            OraclePds81 oraclepds81 = new OraclePds81();
            conn = oraclepds81.getDataSource().getConnection();
            Statement stmt = conn.createStatement();
            stmt.execute("create sequence " + s + " increment by " + j + " start with " + i);
        }
        catch (Exception exception)
        {
            Kogger.debug(ManageSequence.class, "Error!! 시퀀스 생성시 에러가 발생했습니다.");
        }
        finally
        {
            try
            {
                conn.close();
            }
            catch (SQLException sqlexception)
            {
        	Kogger.error(ManageSequence.class, sqlexception);
            }
        }
    }
    
    public static String getSeqNo2(String seqName, String format, String tabName, String colName , int row)
    {
        String result = "";
        
        OraclePds81 dds = new OraclePds81();
        Connection con = null;
        ResultSet rs = null;
        try
        {
            String sql = "SELECT NVL(MAX(" + colName + "),'1') no FROM " + tabName + " WHERE " + colName + " LIKE '" + seqName
                    + "%'";
            Kogger.debug(ManageSequence.class, "sql = " + sql);
            
            
            con = dds.getDataSource().getConnection();
            Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(sql);
            
            while (rs.next())
            {
                if (rs.getString("no").equals("1")) result = "1";
                else 
                {
                	String no = rs.getString("no");
                	Kogger.debug(ManageSequence.class, "ManageSequence::getSeqNo:no.substring() = "+no.substring(seqName.length()));
            		result = "" + (Integer.parseInt(no.substring(seqName.length())) + row);
            		Kogger.debug(ManageSequence.class, "result=seq2=="+result);
                }
            }
        }
        catch (SQLException e)
        {
            Kogger.error(ManageSequence.class, e);
        }
        finally
        {
            try
            {
                if (rs != null) rs.close();
                if (con != null) con.close();
            }
            catch (SQLException e)
            {
                Kogger.error(ManageSequence.class, e);
            }
        }
        DecimalFormat decimalformat = new DecimalFormat(format);
        return decimalformat.format(Long.parseLong(result));
    }
    
    
    /**
     * 현재 sequence중 가장 큰 sequence의 다음값을 반환한다.
     * 
     * @param seqName
     *            sequence name
     * @param format
     *            sequence format
     * @param tabName
     *            DB table name
     * @param colName
     *            DB column name
     * @return
     */
    public static String getSeqNoByWTCon(String seqName, String format, String tabName, String colName, Connection con)
    {
        String result = "";
        
        OraclePds81 dds = new OraclePds81();
        
        ResultSet rs = null;
        Statement stmt = null;
        try
        {
            String sql = "SELECT NVL(MAX(" + colName + "),'1') no FROM " + tabName + " WHERE " + colName + " LIKE '" + seqName
                    + "%'";
            //Kogger.debug(getClass(), "sql = " + sql);
            

            stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            
            rs = stmt.executeQuery(sql);
            
            while (rs.next())
            {
                if (rs.getString("no").equals("1")) result = "1";
                else 
                {
                	String no = rs.getString("no");
                	//Kogger.debug(getClass(), "ManageSequence::getSeqNo:no.substring() = "+no.substring(seqName.length()));
            		result = "" + (Integer.parseInt(no.substring(seqName.length())) + 1);
                }
            }
        }
        catch (SQLException e)
        {
            Kogger.error(ManageSequence.class, e);
        }
        finally
        {
            try
            {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            }
            catch (SQLException e)
            {
                Kogger.error(ManageSequence.class, e);
            }
        }
        DecimalFormat decimalformat = new DecimalFormat(format);
        return decimalformat.format(Long.parseLong(result));
    }
    
}
