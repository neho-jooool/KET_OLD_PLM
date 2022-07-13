package e3ps.project.outputtype.load;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Vector;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.pom.Transaction;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;
import wt.util.WTProperties;
import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.util.CommonUtil;
import e3ps.project.outputtype.OEMProjectType;
import e3ps.project.outputtype.OEMType;
import ext.ket.shared.log.Kogger;



public class EventLoad implements wt.method.RemoteAccess, java.io.Serializable
{
	private static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;

	public static boolean loadData(String fileName) throws WTException
	{
		if( !SERVER )
		{
			Class argTypes[] = new Class[]{String.class};
			Object args[] = new Object[]{fileName};
			Object obj = null;
			try
			{
				obj = wt.method.RemoteMethodServer.getDefault().invoke(
						"loadData",
						EventLoad.class.getName(),
						null,
						argTypes,
						args);
			}
			catch( RemoteException e )
			{
				Kogger.error(EventLoad.class, e);
				throw new WTException(e);
			}
			catch( InvocationTargetException e )
			{
				Kogger.error(EventLoad.class, e);
				throw new WTException(e);
			}

			return ((Boolean)obj).booleanValue();
		}

		Transaction trx = new Transaction();
		try
		{
			if( fileName == null || fileName.length() == 0 )
			{
				fileName = "EventLoad.xls";
			}

            String wt_home = WTProperties.getServerProperties().getProperty("wt.home");
            String loadBase_home = wt_home + File.separator +
            						"loadFiles" + File.separator +
            						"ket" + File.separator +
            						"code";

			File file = new File( loadBase_home + File.separator + fileName );
			
			if( file == null )
			{
				Kogger.debug(EventLoad.class, "File not found!!!");
				return false;
			}
			Hashtable event_in = new Hashtable();
			Hashtable event_out = new Hashtable();
			event_in = getHashNumberCode("CUSTOMEREVENT", "국내");
			event_out = getHashNumberCode("CUSTOMEREVENT", "국외");
			Workbook workbook = Workbook.getWorkbook(file);
			Sheet sheets[] = workbook.getSheets();
			
			if( sheets.length > 0 )
			{
				Sheet sheet = null;

				for( int i = 0; i < sheets.length; i++ )
				{
					sheet = sheets[i];
					Kogger.debug(EventLoad.class,  "######### Sheet [" + sheet.getName() + "] Loading start ########\n" );


					int rows = sheet.getRows();

					for( int j = 1; j < rows; j++ )
					{
						System.out.print( "@>>>>>> Row[" + j + "]" + " begin >>>>> " );

						
						/*
						 * 0:분류
						 * 1:이름
						 * 2:event1
						 * 3:e2
						 * 4:e3
						 * 5:e4
						 * 6:e5
						 * 7:e6
						 * 8:e7
						 * 9:e8
						 * 10:e9
						 * 11:e10
						 */

						Cell a0 = sheet.getCell(0,j);
						String a1Str = a0.getContents();
						if(a1Str == null) a1Str = "";

						if( a1Str.startsWith("#") )
						{
							continue;
						}

						Cell a1 = sheet.getCell(1, j);
						Cell a2 = sheet.getCell(2, j);
						Cell a3 = sheet.getCell(3, j);
						Cell a4 = sheet.getCell(4, j);
						Cell a5 = sheet.getCell(5, j);
						Cell a6 = sheet.getCell(6, j);
						Cell a7 = sheet.getCell(7, j);
						Cell a8 = sheet.getCell(8, j);
						Cell a9 = sheet.getCell(9, j);
						Cell a10 = sheet.getCell(10, j);
						Cell a11 = sheet.getCell(11, j);
						
						String aStr1 = a1.getContents();
						String aStr2 = a2.getContents();
						String aStr3 = a3.getContents();
						String aStr4 = a4.getContents();
						String aStr5 = a5.getContents();
						String aStr6 = a6.getContents();
						String aStr7 = a7.getContents();
						String aStr8 = a8.getContents();
						String aStr9 = a9.getContents();
						String aStr10 = a10.getContents();
						String aStr11 = a11.getContents();
						
						if( aStr1 == null ) aStr1 = "";
						
						Vector vt = new Vector();
						int checkInt = 0;
						if( aStr2 == null || aStr2.equals("")) { aStr2 = "";	}else{vt.add(checkInt,aStr2);	checkInt = checkInt+1;}
						if( aStr3 == null || aStr3.equals("")) { aStr3 = "";	}else{vt.add(checkInt,aStr3);	checkInt = checkInt+1;}
						if( aStr4 == null || aStr4.equals("")) {  aStr4 = "";	}else{vt.add(checkInt,aStr4);	checkInt = checkInt+1;}
						if( aStr5 == null || aStr5.equals("")) {  aStr5 = "";	}else{vt.add(checkInt,aStr5);	checkInt = checkInt+1;}
						if( aStr6 == null || aStr6.equals("")) {  aStr6 = "";	}else{vt.add(checkInt,aStr6);	checkInt = checkInt+1;}
						if( aStr7 == null || aStr7.equals("")) {  aStr7 = "";	}else{vt.add(checkInt,aStr7);	checkInt = checkInt+1;}
						if( aStr8 == null || aStr8.equals("")) {  aStr8 = "";	}else{vt.add(checkInt,aStr8);	checkInt = checkInt+1;}
						if( aStr9 == null || aStr9.equals("")) {  aStr9 = "";	}else{vt.add(checkInt,aStr9);	checkInt = checkInt+1;}
						if( aStr10 == null || aStr10.equals("")) {  aStr10 = "";	}else{vt.add(checkInt,aStr10);	checkInt = checkInt+1;}
						if( aStr11 == null || aStr11.equals("")) {  aStr11 = "";	}else{vt.add(checkInt,aStr11);	checkInt = checkInt+1;}
						
						
						String type = a1Str.trim(); 
			            String name = aStr1.trim();
			            
						
						NumberCode typeCode = null;
						
						if(type.equals("국내")){
							typeCode = (NumberCode)event_in.get(name);
							
						}else if(type.equals("국외")){
							typeCode = (NumberCode)event_out.get(name);
							
						}else{
							throw new WTException( (j + 1) + " 라인 구분이 적합하지 않습니다." );
						}
						   
						if(typeCode == null){
							throw new WTException( (j + 1) + " 라인 Name이 코드체계에 존재하지 않습니다." );
						}
						
						Kogger.debug(EventLoad.class, "checkInt=====>" + checkInt);
						for(int savei = 0 ; savei < checkInt; savei++){
							int countCode = savei+1000;
							String checkName =(String) vt.get(savei);
							Kogger.debug(EventLoad.class, "eventName=====>" + checkName + " name:" +typeCode.getName()+ " OemType:" +OEMType.toOEMType("CUSTOMEREVENT"));
							
							OEMProjectType data = checkCodeData(typeCode, "CUSTOMEREVENT", checkName);
							if(data == null){
								data = OEMProjectType.newOEMProjectType();
							}
							data.setMaker(typeCode); 
				            data.setCode(""+countCode);
				            data.setName(checkName);
				            data.setIsSavePlan(false);
				            data.setOemType(OEMType.toOEMType("CUSTOMEREVENT"));
				            data = (OEMProjectType) PersistenceHelper.manager.save(data);
						}
					}

					Kogger.debug(EventLoad.class,  "######### Sheet [" + sheet.getName() + "] end ########" );
				}
			}

			workbook.close();
			trx.commit();
			trx = null;
		}
		catch( Exception e )
		{
			Kogger.error(EventLoad.class, e);
			trx.rollback();

			throw new WTException(e.getMessage());
		}
		finally
		{
			if( trx != null )
			{
				trx = null;
			}
		}

		return true;
	}
	
	public static Hashtable getHashNumberCode(String codeType){
		QueryResult rs = NumberCodeHelper.manager.getQueryResult(codeType, null);
		Hashtable hash = new Hashtable();
		while(rs.hasMoreElements()){
			NumberCode code = (NumberCode)rs.nextElement();
			String key = code.getName();
			hash.put(key, code);
		}
		return hash;
	}
	
	public static Hashtable getHashNumberCode(String type, String gubun){
		//Vector vec = NumberCodeHelper.manager.getNumberCodeLevelType("MATERIALMAKER", "수지");
		Vector vec = NumberCodeHelper.manager.getNumberCodeLevelType(type, gubun);
		Hashtable hash = new Hashtable();
		
		for(int i = 0; i < vec.size(); i++){
			NumberCode code = (NumberCode)vec.get(i);
			String key = code.getName();
			hash.put(key, code);
		}
		
		return hash;
	}

	public static OEMProjectType checkCodeData(NumberCode nccode, String type, String name) throws WTException
	{
		if( !SERVER )
		{
			Class argTypes[] = new Class[]{NumberCode.class, String.class, String.class};
			Object args[] = new Object[]{nccode, type, name};
			Object obj = null;

			try
			{
				obj = wt.method.RemoteMethodServer.getDefault().invoke(
						"checkCodeData",
						EventLoad.class.getName(),
						null,
						argTypes,
						args);
			}
			catch( RemoteException e )
			{
				Kogger.error(EventLoad.class, e);
				throw new WTException(e);
			}
			catch( InvocationTargetException e )
			{
				Kogger.error(EventLoad.class, e);
				throw new WTException(e);
			}
			return (OEMProjectType)obj;
		}
		try
		{
			QuerySpec qs = new QuerySpec();
			long id = CommonUtil.getOIDLongValue(nccode);
			int index = qs.addClassList(OEMProjectType.class, true);
			qs.appendWhere(new SearchCondition(OEMProjectType.class, "name", "=", name), new int[]{index});
			qs.appendAnd();
			qs.appendWhere(new SearchCondition(OEMProjectType.class, "oemType", "=", type), new int[]{index});
			qs.appendAnd();
			qs.appendWhere(new SearchCondition(OEMProjectType.class, "makerReference.key.id", "=", id), new int[]{index});
			
			QueryResult qr = PersistenceHelper.manager.find(qs);

			if( qr.hasMoreElements() )
			{
				Object obj[] = (Object[])qr.nextElement();
				return (OEMProjectType)obj[0];
			}
		}
		catch(Exception e)
		{
			Kogger.error(EventLoad.class, e);
			throw new WTException(e);
		}

		return null;
	}

	public static void main( String args[] ) throws Exception
	{
//		wt.method.RemoteMethodServer remotemethod = wt.method.RemoteMethodServer.getDefault();
//		if(remotemethod.getUserName() == null) {
//			remotemethod.setUserName("wcadmin");
//			remotemethod.setPassword("wcadmin");
//		}
		
		
		
		wt.method.RemoteMethodServer.getDefault().setUserName(args[0]);
		wt.method.RemoteMethodServer.getDefault().setPassword(args[1]);
	//	Hashtable hash = getHashNumberCode("MACHINETON");
	//	Kogger.debug(EventLoad.class, "hash=== "  + hash.size());
		String fileName = "EventLoad.xls";
		boolean flag = loadData(fileName);
		Kogger.debug(EventLoad.class, "Load : " + flag);
		System.exit(0);
	}


}
