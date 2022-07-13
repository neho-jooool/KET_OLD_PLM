/*
 * @(#) JExcelUtil.java  Create on 2005. 2. 21.
 * Copyright (c) e3ps. All rights reserverd
 */
package e3ps.common.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import jxl.Cell;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableCellFormat;
import ext.ket.shared.log.Kogger;

/**
 * 
 * @author Choi Seunghwan, skyprda@e3ps.com
 * @version 1.00, 2005. 2. 21.
 * @since 1.4
 */
public class JExcelUtil
{
    public static Workbook getWorkbook(byte[] bytes)
    {
        Workbook wb = null;
        try
        {
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            wb = Workbook.getWorkbook(bais);
        }
        catch (BiffException e)
        {
            Kogger.error(JExcelUtil.class, e);
        }
        catch (IOException e)
        {
            Kogger.error(JExcelUtil.class, e);
        }

        return wb;
    }

    public static Workbook getWorkbook(File file)
    {
        if (file == null)
            return null;
        try
        {
            if (!file.getName().endsWith(".xls"))
                return null;
        }
        catch (Exception e)
        {
            return null;
        }

        Workbook wb = null;
        try
        {
            wb = Workbook.getWorkbook(file);
        }
        catch (BiffException e)
        {
            Kogger.error(JExcelUtil.class, e);
        }
        catch (IOException e)
        {
            Kogger.error(JExcelUtil.class, e);
        }

        return wb;
    }

    public static String getContent(Cell[] cell, int idx)
    {
        try
        {
            String val = cell[idx].getContents();
            if (val == null)
                return "";
            return val.trim();
        }
        catch (ArrayIndexOutOfBoundsException e)
        {}
        return "";
    }

    /**
     * Excel에 있는 날짜형태의 값을 Timestamp로 변환한다.
     * @param _cell
     * @param _idx
     * @return
     */
    public static Timestamp getTimestamp(Cell[] _cell, int _idx)
    {
        try
        {
            String val = _cell[_idx].getContents();
            val = val == null ? "" : val.trim();
            if(val == null || val.trim().length() == 0){
            	return null;
            }
            
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy:HH-mm-ss", Locale.KOREA);
            return new Timestamp(format.parse(val + ":00-00-00").getTime());
        }
        catch (ArrayIndexOutOfBoundsException e)
        {}
        catch (ParseException e)
        {
            Kogger.error(JExcelUtil.class, e);
        }
        return null;
    }

    public static Timestamp getTimestamp(Cell[] _cell, int _idx, String type)
    {
        try
        {
            String val = _cell[_idx].getContents();
            val = val == null ? "" : val.trim();
            val = val.replaceAll("\"", "");
            val = val.replaceAll(";", "");
            val = val.replaceAll("@", "");
            if(type == null && type.length() == 0) type = "MM/dd/yy:HH-mm-ss";
            SimpleDateFormat format = new SimpleDateFormat(type, Locale.KOREA);
            if(val.length() > 0)
            	return new Timestamp(format.parse(val + ":00-00-00").getTime());
        }
        catch (ArrayIndexOutOfBoundsException e)
        {}
        catch (ParseException e)
        {
            Kogger.error(JExcelUtil.class, e);
        }
        return null;
    }

    public static boolean checkLine(Cell[] cell)
    {
        String value = null;
        try
        {
            value = cell[0].getContents().trim();
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            e.getMessage();
            return false;
        }
        if (value == null || value.length() == 0)
            return false;
        return true;
    }
    
    public static WritableCellFormat getCellFormat(jxl.format.Alignment alignment, jxl.format.Colour color) {
    	WritableCellFormat format = null;
    	try {
	    	format = new WritableCellFormat();
	    	if(color != null)
	    		format.setBackground(color);
	    	
	    	format.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN );
	    	
	    	if(alignment != null)
	    		format.setAlignment(alignment);
    	}
    	catch(Exception e) {
    		Kogger.error(JExcelUtil.class, e);
    	}
    	return format;
    }
}
/* $Log: not supported by cvs2svn $
/* Revision 1.5  2010/12/28 10:09:22  hhkim
/* *** empty log message ***
/*
/* Revision 1.4  2010/12/10 02:46:29  kihkim
/* *** empty log message ***
/*
/* Revision 1.3  2010/12/10 02:10:07  hhkim
/* *** empty log message ***
/*
/* Revision 1.2  2010/12/06 02:00:09  kihkim
/* *** empty log message ***
/*
/* Revision 1.1  2010/09/10 04:40:54  syoh
/* 최초등록
/*
/* Revision 1.1  2009/08/11 04:16:21  administrator
/* Init Source
/* Committed on the Free edition of March Hare Software CVSNT Server.
/* Upgrade to CVS Suite for more features and support:
/* http://march-hare.com/cvsnt/
/*
/* Revision 1.1  2009/02/25 01:25:48  smkim
/* 최초 작성
/* Committed on the Free edition of March Hare Software CVSNT Server.
/* Upgrade to CVS Suite for more features and support:
/* http://march-hare.com/cvsnt/
/*
/* Revision 1.1  2008/01/29 06:25:02  sjhan
/* 주성 기본 패키지 정리작업 완료
/*
/* Revision 1.2  2007/10/17 04:54:55  sjhan
/* *** empty log message ***
/*
/* Revision 1.1.1.1  2007/04/17 01:21:30  administrator
/* no message
/*
/* Revision 1.1.1.1  2007/02/14 07:53:56  administrator
/* no message
/*
/* Revision 1.1  2006/05/09 02:35:06  shchoi
/* *** empty log message ***
/*
/* Revision 1.2  2006/03/10 04:53:22  shchoi
/* getTimestamp 추가
/**/
