package e3ps.load.groupware;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Hashtable;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import wt.fc.PersistenceHelper;
import wt.introspection.ClassInfo;
import wt.introspection.WTIntrospector;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.pds.DatabaseInfoUtilities;
import wt.util.WTException;
import wt.util.WTProperties;
import wt.util.WTPropertyVetoException;
//import e3ps.common.db.DBCPManager;
import e3ps.common.jdf.log.Logger;
import e3ps.common.util.JExcelUtil;
import e3ps.common.util.StringUtil;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.DepartmentHelper;
import ext.ket.shared.log.Kogger;

public class LoadDepartment implements RemoteAccess
{
	private static String dir;

	public static void main( String[] args )
	{
//    	interfaceTableSync();

        if( args.length != 3 )
        {
            // 사용법 설명
            Kogger.debug(LoadDepartment.class,  "Usage  : java e3ps.load.groupware.LoadDepartment userId psswd 로더파일_전체경로" );
            Kogger.debug(LoadDepartment.class,  "로더파일 : D:\\ptc\\Windchill\\loadFiles\\ket\\groupware\\Department.xls" );
            return;
        }

		wt.method.RemoteMethodServer.getDefault().setUserName(args[0]);
		wt.method.RemoteMethodServer.getDefault().setPassword(args[1]);

        LoadDepartment loader = new LoadDepartment();
        String file = args[2].trim();
        File newfile = null;

        try
        {
            newfile = new File(file);
            if( !newfile.getName().endsWith(".xls") )
            {
                return;
            }
        }
        catch( Exception e )
        {
            return;
        }

        dir = newfile.getParent();
        Workbook wb = JExcelUtil.getWorkbook( newfile );
        loader.load( wb );
        System.exit( 0 );
    }

	private void load( Workbook wb )
	{
        printLog( "#####################################" );
        printLog( "# 부서 Loader Start" );

        Sheet[] sheets = wb.getSheets();
        int rows = sheets[0].getRows();

        for( int i = 1; i < rows; i++ )
        {
            CreateDepartment( sheets[0].getRow(i), i + 1 );
        }

        printLog( "# 부서 Loader End" );
        printLog( "#####################################" );
    }

	private void CreateDepartment( Cell[] cell, int line )
	{
        //Excel Data Check
        if( checkList(cell, line) )
        {
            return;
        }

        String mode = JExcelUtil.getContent(cell, 1);
        Kogger.debug(getClass(),  " 작업모드 = " + mode );

        if( "C".equalsIgnoreCase(mode) )
        {
        	//새 부서 생성
            printLog( ">>>># 새 부서 생성 Start" );

            try
            {
                Department createDept = Department.newDepartment();
                createDept.setName( JExcelUtil.getContent(cell, 5).trim() );
                createDept.setCode( JExcelUtil.getContent(cell, 4).trim() );

                if( StringUtil.checkString(JExcelUtil.getContent(cell, 3).trim()) && !"root".equals(JExcelUtil.getContent(cell, 3).trim()) )
                {
                    createDept.setParent( DepartmentHelper.manager.getDepartment((String) JExcelUtil.getContent(cell, 3).trim()) );
                }

                createDept.setEnabled( true );
                createDept.setSort(Integer.parseInt( JExcelUtil.getContent(cell, 6).trim()) );
                PersistenceHelper.manager.save( createDept );
                printLog( "새 부서 " + JExcelUtil.getContent(cell, 5).trim() + " 를 생성했습니다." );
            }
            catch( WTException e )
            {
                Kogger.error(getClass(), e);
            }
            catch( WTPropertyVetoException e )
            {
                Kogger.error(getClass(), e);
            }

            printLog( ">>>># 새 부서 생성 End" );
        }
        else if( "M".equalsIgnoreCase(mode) )
        {
        	//기존부서수정
            printLog( ">>>># 기존 부서 수정 Start" );

            Department modifyDept = null;
            modifyDept = (Department) DepartmentHelper.manager.getDepartment( JExcelUtil.getContent(cell, 2) );

            try
            {
                modifyDept.setName( JExcelUtil.getContent(cell, 5) );
                modifyDept.setCode( JExcelUtil.getContent(cell, 4) );

                if( !JExcelUtil.getContent(cell, 3).equalsIgnoreCase("DAEKI") )
                {
                    modifyDept.setParent(DepartmentHelper.manager.getDepartment( JExcelUtil.getContent(cell, 3)) );
                }
                else
                {
                    modifyDept.setParent( null );
                }

                PersistenceHelper.manager.modify( modifyDept );
                printLog( "기존 부서인 " + DepartmentHelper.manager.getDepartment( (String) JExcelUtil.getContent(cell, 4).trim() ).getName() + " 이 수정되었습니다." );
            }
            catch( WTPropertyVetoException e )
            {
                Kogger.error(getClass(), e);
            }
            catch( WTException e )
            {
                Kogger.error(getClass(), e);
            }

            printLog( ">>>># 기존 부서 수정 End" );
        }
        else if( "D".equalsIgnoreCase(mode) )
        {
        	//기존부서 삭제
            printLog( ">>>># 기존 부서 삭제 Start" );

            Department delDept = null;
            String delName = null;
            delDept = (Department) DepartmentHelper.manager.getDepartment( (String) JExcelUtil.getContent(cell, 2).trim() );
            delName = delDept.getName();

            try
            {
                PersistenceHelper.manager.delete(delDept);
                printLog( "기존 부서인 " + delName + " 이 삭제되었습니다." );
            }
            catch( WTException e )
            {
                Kogger.error(getClass(), e);
            }

            printLog( ">>>># 기존 부서 삭제 End" );
        }

        //기존 부서 Object 뽑기 & 수정
        /*
         * Department tempDept = null; Department parentDept = null; tempDept =
         * (Department)DepartmentHelper.getDepartObject("NAME",
         * JExcelUtil.getContent(cell, 1).trim()); parentDept =
         * (Department)DepartmentHelper.getDepartObject("NAME",
         * JExcelUtil.getContent(cell, 2).trim()); try {
         * tempDept.setName((String)JExcelUtil.getContent(cell, 4).trim());
         * tempDept.setCode((String)JExcelUtil.getContent(cell, 3).trim());
         * if(!JExcelUtil.getContent(cell, 2).trim().equalsIgnoreCase("DAEKI"))
         * tempDept.setParent(DepartmentHelper.manager.getDepartment((String)parentDept.getCode()));
         *
         * PersistenceHelper.manager.modify(tempDept); printLog("기존 부서명 " +
         * JExcelUtil.getContent(cell, 1) + " 를 새 부서명 " +
         * JExcelUtil.getContent(cell, 4) + " 로 변경완료"); } catch (Exception e) {
         * Kogger.error(getClass(), e); }
         */
    }

	private boolean checkList( Cell[] cell, int line )
	{
        //변수들
        String tempType = JExcelUtil.getContent( cell, 1 ).trim(); //생성타입
        String tempBeforeDept = JExcelUtil.getContent( cell, 2 ).trim(); //기존 부서 코드
        String tempTopDept = JExcelUtil.getContent( cell, 3 ).trim(); //변경 부서 상위 코드
        String tempAfterDept = JExcelUtil.getContent( cell, 4 ).trim(); //변경 부서 코드
        String tempDeptName = JExcelUtil.getContent( cell, 5 ).trim(); //변경 부서명

        //생성타입 체크
        if( tempType == null )
        {
            printLog( "LINE<< " + line + " 생성 타입이 없습니다." );
            return true;
        }
        else if( tempType.equalsIgnoreCase("C") )
        {
        	//새부서 생성
            //변경 부서 상위 코드
            if( tempTopDept != null )
            {
                //DB Check
                if( !"root".equals(tempTopDept) && DepartmentHelper.manager.getDepartment(tempTopDept) == null )
                {
                    printLog( "LINE<< " + line + " DB 에 변경 부서 상위 코드가 없습니다." );
                    return true;
                }
            }

            //변경 부서 코드
            if( tempAfterDept == null )
            {
                printLog( "LINE<< " + line + " 변경 부서 코드가 없습니다." );
                return true;
            }
            else
            {
                //DB Check
                if( DepartmentHelper.manager.getDepartment(tempAfterDept) != null )
                {
                    printLog( "LINE<< " + line + " DB 에서 변경 부서코드가  중복 되었습니다." );
                    return true;
                }
            }

            //변경 부서명
            if( tempDeptName == null )
            {
                printLog( "LINE<< " + line + " 변경 부서명이 없습니다." );
                return true;
            }
        }
        else if( tempType.equalsIgnoreCase("M") )
        {
        	//기존부서 수정
            //기존 부서 코드
            if( tempBeforeDept == null )
            {
                printLog( "LINE<< " + line + " 기존 부서 코드가 없습니다." );
                return true;
            }

            //변경 부서 상위 코드
            if( tempTopDept == null )
            {
                printLog( "LINE<< " + line + " 변경 부서 상위 코드가 없습니다." );
                return true;
            }

            //변경 부서 코드
            if( tempAfterDept == null )
            {
                printLog( "LINE<< " + line + " 변경 부서 코드가 없습니다." );
                return true;
            }

            //변경 부서명
            if( tempDeptName == null )
            {
                printLog( "LINE<< " + line + " 변경 부서명이 없습니다." );
                return true;
            }
        }
        else if( tempType.equalsIgnoreCase("D") )
        {
        	//기존부서 삭제
            //기존 부서 코드
            if( tempBeforeDept == null )
            {
                printLog( "LINE<< " + line + " 기존 부서 코드가 없습니다." );
                return true;
            }
        }

        /*
         * //기존 부서명 체크 if(JExcelUtil.getContent(cell, 1).trim() == null) {
         * printLog("LINE < < " + line + " 변경 해야 할 기존 부서명이 없습니다."); return true; }
         * else { //기존 부서명 DB 존재 여부 체크
         * if(DepartmentHelper.getDepartObject("NAME",
         * JExcelUtil.getContent(cell, 1).trim()) == null) { printLog("LINE < < " +
         * line + " 변경 해야 할 기존 부서명이 DataBase에 없습니다."); return true; } }
         *
         * //상위 부서명 if(JExcelUtil.getContent(cell, 2).trim() == null) {
         * printLog("LINE < < " + line + " 변경할 부서에 상위 부서명이 없습니다."); return true; }
         * else { //상위 부서명 DB 존재 여부 체크 if(!JExcelUtil.getContent(cell,
         * 2).trim().equalsIgnoreCase("DAEKI")) {
         * if(DepartmentHelper.getDepartObject("NAME",
         * JExcelUtil.getContent(cell, 2).trim()) == null) { printLog("LINE < < " +
         * line + " 변경할 부서에 상위 부서명이 DataBase에 없습니다."); return true; } } }
         *
         * //변경 부서 코드 체크 if(JExcelUtil.getContent(cell, 3).trim() == null) {
         * printLog("LINE < < " + line + " 변경할 부서 코드가 없습니다."); return true; }
         *
         * //변경 부서 이름 체크 if(JExcelUtil.getContent(cell, 4).trim() == null) {
         * printLog("LINE < < " + line + " 변경할 부서 이름이 없습니다."); return true; }
         */

        return false;
    }

	public static void allDisabled() throws Exception
	{
    	if( !wt.method.RemoteMethodServer.ServerFlag )
    	{
    		Class argTypes[] = null;
			Object args[] = null;

			try
			{
				Object obj =RemoteMethodServer.getDefault().invoke( "allDisabled", LoadDepartment.class.getName() , null, argTypes, args );
				return;
			}
			catch( RemoteException e )
			{
				Kogger.error(LoadDepartment.class, e);
				throw new WTException(e);
			}
			catch( InvocationTargetException e )
			{
				Kogger.error(LoadDepartment.class, e);
				throw new WTException( e );
			}
		}

    	String tableName = "";
    	Class taskClass = Department.class;

    	ClassInfo classinfo = WTIntrospector.getClassInfo( taskClass );

		if( DatabaseInfoUtilities.isAutoNavigate(classinfo) )
		{
			tableName = DatabaseInfoUtilities.getBaseTableName( classinfo );
		}
		else
		{
			tableName = DatabaseInfoUtilities.getValidTableName( classinfo );
		}

//		String enabledColumnName = DatabaseInfoUtilities.getValidColumnName(classinfo, Department.ENABLED);

		//MethodContext methodcontext = null;
		//WTConnection wtconnection = null;

		Connection con = null;
		int childLength = 0;
//		try {
//			//methodcontext = MethodContext.getContext();
//			//wtconnection = (WTConnection) methodcontext.getConnection();
////			con = DBCPManager.getConnection("plm");
//
////			String sql = "update  " + tableName + " set " + enabledColumnName + " = ? ";
////			PreparedStatement st = con.prepareStatement(sql);
//
//			st.setInt(1, 0);
//		    Kogger.debug(getClass(), "department enabled update = " + st.execute());
//		} catch (Exception e) {
//			Kogger.error(getClass(), e);
//			throw e;
//		} finally {
//			try {
//                if (con != null) con.close();
//            } catch (SQLException e) {
//                Kogger.error(getClass(), e);
//            }
//		}
    }

    public static void interfaceTableSync()
    {
    	Statement st = null;
        ResultSet rs = null;
		Connection con = null;

		try
		{
			allDisabled();

			if( con == null )
			{
//				con = DBCPManager.getConnection("ehr");
			}

			st = con.createStatement();
			StringBuffer sb = new StringBuffer();
			sb.append("select C_CD, ");
			sb.append("TLEVEL, ");
			sb.append("TRG_OBJ_ID, ");
			sb.append("OBJ_ID, ");
			sb.append("OBJ_NM, ");
			sb.append("CC_CD ");
			sb.append("from vorm_org_info ");

			Kogger.debug(LoadDepartment.class, "#################### " + sb.toString());
            rs = st.executeQuery(sb.toString());
            Hashtable hash = new Hashtable();

            while( rs.next() )
            {
            	String companyCode = rs.getString("C_CD");
            	String tlevel = rs.getString("TLEVEL");
            	String parentCode = rs.getString("TRG_OBJ_ID");
            	String code = rs.getString("OBJ_ID");
            	String name = rs.getString("OBJ_NM");
            	String ccCode = rs.getString("CC_CD");
//            	IterfaceDepartMementData data = new IterfaceDepartMementData(companyCode, tlevel, parentCode, code, name, ccCode);
            	Kogger.debug(LoadDepartment.class, "code = " + code + ", name=" + name);
//            	hash.put(code, data);
            }

            Enumeration e = hash.keys();

            while( e.hasMoreElements() )
            {
            	String key = (String)e.nextElement();
//            	IterfaceDepartMementData data = (IterfaceDepartMementData)hash.get(key);
//            	try {
//					Department department = (Department)data.getDepartment();
//					boolean isParent = false;
//					if(data.parentCode != null){
////						IterfaceDepartMementData pdata = (IterfaceDepartMementData)hash.get(data.parentCode);
////						if(pdata != null){
//							Department parentDepartment = pdata.getDepartment();
//							department.setParent(parentDepartment);
//							department.setEnabled(true);
//
//							department = (Department)PersistenceHelper.manager.save(department);
//
//							isParent = true;
//						}
//					}
//
//					if(!isParent){
//						department.setParent(Department.newDepartment());
//						department.setEnabled(true);
//						department = (Department)PersistenceHelper.manager.save(department);
//						Kogger.debug(LoadDepartment.class, "departm... =  " + department.getParent());
//
//					}
//					data.department = department;
//				} catch (Exception e1) {
//					Kogger.error(LoadDepartment.class, e1);
//				}
//				Kogger.debug(LoadDepartment.class, "save ok...");
            }


		}
		catch( Exception e )
		{
			Kogger.error(LoadDepartment.class, e);
		}
		finally
		{
            try
            {
                if( st != null )
                {
                	st.close();
                }

                if( con != null )
                {
                	con.close();
                }
            }
            catch( SQLException e )
            {
                Kogger.error(LoadDepartment.class, e);
            }
        }
    }

	private String getHome() throws IOException
	{
        WTProperties prop = WTProperties.getLocalProperties();
        return prop.getProperty( "wt.home" );
    }

	private void printLog( String msg )
	{
        Logger.info.println( msg );
        Kogger.debug(getClass(),  msg );
    }
}
