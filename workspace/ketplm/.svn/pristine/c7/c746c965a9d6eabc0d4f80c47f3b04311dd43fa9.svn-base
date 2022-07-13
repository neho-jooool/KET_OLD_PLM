/*
 * @(#) LoadPeople.java Create on 2005. 7. 7.
 * Copyright (c) e3ps. All rights reserved
 *
 * @version 1.00
 * @since jdk 1.4.02
 * @createdate 2005. 7. 7.
 * @author Choi Kang Hun, khchoi@e3ps.com
 */
package e3ps.load.groupware;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import wt.fc.PersistenceHelper;
import wt.util.WTException;
import wt.util.WTProperties;
import wt.util.WTPropertyVetoException;
import e3ps.common.jdf.log.Logger;
import e3ps.common.util.JExcelUtil;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.DepartmentHelper;
import e3ps.groupware.company.People;
import e3ps.groupware.company.PeopleHelper;
import ext.ket.shared.log.Kogger;

public class LoadPeople
{
    private static String dir;
    private HashMap dutyMap;

    public static void main(String[] args)
    {
    	if( args.length != 3 )
        {
            // 사용법 설명
            Kogger.debug(LoadPeople.class, "Usage  : windchill e3ps.load.groupware.LoadPeople userID passwd 로더파일_전체경로");
            Kogger.debug(LoadPeople.class, "로더파일 : D:\\ptc\\Windchill\\loadFiles\\ket\\groupware\\People.xls");
            return;
        }

		wt.method.RemoteMethodServer.getDefault().setUserName(args[0]);
		wt.method.RemoteMethodServer.getDefault().setPassword(args[1]);

        LoadPeople loader = new LoadPeople();
        String file = args[2].trim();
        File newfile = null;

        try
        {
            newfile = new File(file);
            if( !newfile.getName().endsWith(".xls") )
            {
            	Kogger.debug(LoadPeople.class,  "File 체크 flase" );
            	return;
            }
        }
        catch( Exception e )
        {
            return;
        }

        dir = newfile.getParent();
        Workbook wb = JExcelUtil.getWorkbook(newfile);
        loader.load(wb);
        System.exit(0);
    }

    private void load(Workbook wb)
    {
        printLog("#####################################");
        printLog("# People Loader Start");

        //직급 ADD
        dutyMap = new HashMap();
        /*
        dutyMap.put( "회장", "TA10" );
        dutyMap.put( "부회장", "TA20" );
        dutyMap.put( "대표이사", "TA30" );
        dutyMap.put( "사장", "TA31" );
        dutyMap.put( "부사장", "TA40" );
        dutyMap.put( "전무", "TC10" );
        dutyMap.put( "상무", "TC20" );
        dutyMap.put( "상무보", "TD10" );
        dutyMap.put( "이사대우", "TD20" );
        dutyMap.put( "부장", "TF10" );
        dutyMap.put( "총괄연구원", "TF20" );
        dutyMap.put( "차장", "TH10" );
        dutyMap.put( "수석연구원", "TH20" );
        dutyMap.put( "과장", "TJ10" );
        dutyMap.put( "책임연구원", "TJ20" );
        dutyMap.put( "기장", "TJ30" );
        dutyMap.put( "대리", "TL10" );
        dutyMap.put( "선임연구원", "TL20" );
        dutyMap.put( "주임", "TP10" );
        dutyMap.put( "주임연구원", "TP20" );
        dutyMap.put( "선임반장", "TP30" );
        dutyMap.put( "반장", "TP40" );
        dutyMap.put( "사원", "TR10" );
        dutyMap.put( "연구원", "TR20" );
        dutyMap.put( "기타", "TZZZ" );
        dutyMap.put( "사외이사", "TB30" );
        dutyMap.put( "감사", "TB20" );
        dutyMap.put( "경영고문", "TB10" );
        dutyMap.put( "기술고문", "TB40" );
		*/

        dutyMap.put( "회장", "1_A10" );
        dutyMap.put( "사장", "4_A30" );
        dutyMap.put( "부사장	", "7_A40" );
        dutyMap.put( "전무", "A_C10" );
        dutyMap.put( "상무", "D_C20" );
        dutyMap.put( "상무보", "	G_D10" );
        dutyMap.put( "이사대우", "J_D20" );
        dutyMap.put( "부장", "M_F10" );
        dutyMap.put( "차장", "P_H10" );
        dutyMap.put( "수석연구원", "P_H20" );
        dutyMap.put( "과장", "S_J10" );
        dutyMap.put( "책임연구원", "S_J20" );
        dutyMap.put( "기장", "V_J30" );
        dutyMap.put( "대리", "Y_L10" );
        dutyMap.put( "선임연구원", "Y_L20" );
        dutyMap.put( "선임반장", "b_P30" );
        dutyMap.put( "주임", "e_P10" );
        dutyMap.put( "주임연구원", "e_P20" );
        dutyMap.put( "반장", "h_P40" );
        dutyMap.put( "사원", "k_R10" );
        dutyMap.put( "연구원", "	k_R20" );
        dutyMap.put( "고문", "k_B60" );
        dutyMap.put( "감사", "k_B20" );

        Sheet[] sheets = wb.getSheets();
        int rows = sheets[0].getRows();
        for (int i = 1; i < rows; i++)
        {
            CreatePeople(sheets[0].getRow(i), i+1);
        }
        printLog("# People Loader End");
        printLog("#####################################");
    }

    private void CreatePeople(Cell[] cell, int line)
    {
        //기존 사용자 뽑기
        People tempPeo = null;
        Department tempDept = null;
        String tempDutyCode = null;
        String tempChief = null;
        boolean tempisdisable = true;

        Kogger.debug(getClass(), "부서 코드 : "+ (String)JExcelUtil.getContent(cell, 3).trim());

        tempPeo = (People)PeopleHelper.manager.getPeople((String)JExcelUtil.getContent(cell, 1).trim());
        tempDept = (Department)DepartmentHelper.manager.getDepartment((String)JExcelUtil.getContent(cell, 3).trim());

        if( tempPeo == null )
        {
        	return;
        }

        tempDutyCode = JExcelUtil.getContent(cell, 2).trim();
        tempChief = JExcelUtil.getContent(cell, 4).trim()==null?"":JExcelUtil.getContent(cell, 4).trim();

        if( tempChief.length() > 0 )
        {
        	tempChief = tempDept.getName();
        }

        if( JExcelUtil.getContent(cell, 5).trim().equals("1") )
        {
        	tempisdisable = true;
        }
        else
        {
        	tempisdisable = false;
        }

        Kogger.debug(getClass(), "dutyCode<<<<"+tempDutyCode);

        try
        {
        	String dutycode = (String)dutyMap.get( (String)JExcelUtil.getContent(cell, 2).trim() );

        	tempPeo.setDuty( (String)JExcelUtil.getContent(cell, 2).trim() );			// 직급
            tempPeo.setDutyCode( dutycode );											// 직급 코드
            tempPeo.setDepartment( tempDept );										// 부서
            tempPeo.setPerNo( (String)JExcelUtil.getContent(cell, 6).trim() );			// 사번
            tempPeo.setPassword( (String)JExcelUtil.getContent(cell, 7).trim() );		// 패스워드

            if( tempChief.length() > 0 )
            {
            	 tempPeo.setChief( tempChief );											// 부서장
            }

            tempPeo.setIsDisable( tempisdisable );										// 퇴사자

            PersistenceHelper.manager.modify( tempPeo );
            printLog( "기존 사용자 " + JExcelUtil.getContent(cell, 1) + " 에 정보 변경완료" );
        }
        catch( WTPropertyVetoException e )
        {
            Kogger.error(getClass(), e);
        }
        catch( WTException e )
        {
            Kogger.error(getClass(), e);
        }
    }

    private boolean checkList(Cell[] cell, int line)
    {
    	//ID Check
        if( JExcelUtil.getContent(cell, 1).trim() == null )
        {
            printLog( "LINE<< " + line + " 사용자 ID가 없습니다." );
            return true;
        }
        else
        {
            //ID DB Check
            if( PeopleHelper.manager.getPeople((String)JExcelUtil.getContent(cell, 1).trim()) == null )
            {
                printLog( "LINE<< " + line + " 사용자 ID가 DataBase에 없습니다." );
                return true;
            }
        }

        //직위 Check
        if( JExcelUtil.getContent(cell, 2).trim() == null )
        {
            printLog("LINE<< " + line + " 직위가 없습니다.");
            return true;
        }
        else
        {
            //직위 List 와 비교
        	Kogger.debug(getClass(),  "직위 코드: " +JExcelUtil.getContent(cell, 2).trim() );
            if( dutyMap.get(JExcelUtil.getContent(cell, 2).trim()) == null )
            {
                printLog("LINE<< " + line + " 직위가 목록 정의에 없습니다.");
                return true;
            }
        }

        //부서 Check
        if( JExcelUtil.getContent(cell, 3).trim() == null )
        {
            printLog( "LINE<< " + line + " 부서가 없습니다." );
            return true;
        }
        else
        {
            //부서가 DB 에 있나 Check
            if( DepartmentHelper.manager.getDepartment((String)JExcelUtil.getContent(cell, 3).trim()) == null )
            {
                printLog("LINE<< " + line + " 부서가 DB에 없습니다.");
                return true;
            }
        }

        return true;
    }

    private void printLog( String msg )
    {
        Logger.info.println( msg );
        Kogger.debug(getClass(),  msg );
    }

    private String getHome() throws IOException
    {
        WTProperties prop = WTProperties.getLocalProperties();
        return prop.getProperty( "wt.home" );
    }
}
