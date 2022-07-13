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



public class CarLoad implements wt.method.RemoteAccess, java.io.Serializable
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
						CarLoad.class.getName(),
						null,
						argTypes,
						args);
			}
			catch( RemoteException e )
			{
				Kogger.error(CarLoad.class, e);
				throw new WTException(e);
			}
			catch( InvocationTargetException e )
			{
				Kogger.error(CarLoad.class, e);
				throw new WTException(e);
			}

			return ((Boolean)obj).booleanValue();
		}

		Transaction trx = new Transaction();
		try
		{
			if( fileName == null || fileName.length() == 0 )
			{
				fileName = "CarLoad.xls";
			}

            String wt_home = WTProperties.getServerProperties().getProperty("wt.home");
            String loadBase_home = wt_home + File.separator +
            						"loadFiles" + File.separator +
            						"ket" + File.separator +
            						"code";

			File file = new File( loadBase_home + File.separator + fileName );
			
			if( file == null )
			{
				Kogger.debug(CarLoad.class, "File not found!!!");
				return false;
			}
			Hashtable car_in = new Hashtable();
			Hashtable car_out = new Hashtable();
			car_in = getHashNumberCode("CUSTOMEREVENT", "국내");
			car_out = getHashNumberCode("CUSTOMEREVENT", "국외");
			Workbook workbook = Workbook.getWorkbook(file);
			Sheet sheets[] = workbook.getSheets();
			
			if( sheets.length > 0 )
			{
				Sheet sheet = null;

				for( int i = 0; i < sheets.length; i++ )
				{
					sheet = sheets[i];
					Kogger.debug(CarLoad.class,  "######### Sheet [" + sheet.getName() + "] Loading start ########\n" );


					int rows = sheet.getRows();

					for( int j = 1; j < rows; j++ )
					{
						System.out.print( "@>>>>>> Row[" + j + "]" + " begin >>>>> " );

						
						/*
						 * 0:분류
						 * 1:이름
						 * 2:차종
						
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
						String aStr1 = a1.getContents();
						String aStr2 = a2.getContents();
						String aStr3 = a3.getContents();
						
						if( aStr1 == null || aStr1.equals("")) aStr1 = "";
						if( aStr2 == null || aStr2.equals("")) { aStr2 = "";	}
						if( aStr3 == null || aStr3.equals("")) { aStr3 = "";	}
						String type = a1Str.trim(); 
			            String name = aStr1.trim();
			            String carName = aStr2.trim();
			            String code = aStr3.trim();
						
						NumberCode typeCode = null;
						
						if(type.equals("국내")){
							typeCode = (NumberCode)car_in.get(name);
							
						}else if(type.equals("국외")){
							typeCode = (NumberCode)car_out.get(name);
							
						}else{
							throw new WTException( (j + 1) + " 라인 구분이 적합하지 않습니다." );
						}
						
				           
						if(typeCode == null){
							throw new WTException( (j + 1) + " 라인 Name이 코드체계에 존재하지 않습니다." );
						}
						
						
						if(aStr2.equals("")){
							throw new WTException( (j + 1) + " 라인 차종명이 존재하지 않습니다." );
						}
						
						if(aStr3.equals("")){
							throw new WTException( (j + 1) + " 라인 Code가 존재하지 않습니다." );
						}
						
						//int checkInt = getHashOEMProjectType("CARTYPE", typeCode);
						OEMProjectType data = checkCodeData(typeCode, "CARTYPE", carName);
						
						Kogger.debug(CarLoad.class, "carName :" + carName+ "  code:" + code + " Data:" + data);
						if(data == null){
							data = OEMProjectType.newOEMProjectType();
						}
						data.setMaker(typeCode);
			            data.setCode(code);
			            data.setName(carName);
			            data.setIsSavePlan(false);
			            data.setOemType(OEMType.toOEMType("CARTYPE"));
			            data = (OEMProjectType) PersistenceHelper.manager.save(data);
						
					}

					Kogger.debug(CarLoad.class,  "######### Sheet [" + sheet.getName() + "] end ########" );
				}
			}

			workbook.close();
			trx.commit();
			trx = null;
		}
		catch( Exception e )
		{
			Kogger.error(CarLoad.class, e);
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
		Vector vec = NumberCodeHelper.manager.getNumberCodeLevelType(type, gubun);
		Hashtable hash = new Hashtable();
		
		for(int i = 0; i < vec.size(); i++){
			NumberCode code = (NumberCode)vec.get(i);
			String key = code.getName();
			hash.put(key, code);
		}
		
		return hash;
	}
	public static int getHashOEMProjectType(String type, NumberCode nccode) throws Exception{
		QuerySpec qs = new QuerySpec();
		int index = qs.addClassList(OEMProjectType.class, true);
		long id = CommonUtil.getOIDLongValue(nccode);
		qs.appendWhere(new SearchCondition(OEMProjectType.class, "oemType", "=", type), new int[]{index});
		qs.appendAnd();
		qs.appendWhere(new SearchCondition(OEMProjectType.class, "makerReference.key.id", "=", id), new int[]{index});

		QueryResult qr = PersistenceHelper.manager.find(qs);
		return qr.size();
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
						CarLoad.class.getName(),
						null,
						argTypes,
						args);
			}
			catch( RemoteException e )
			{
				Kogger.error(CarLoad.class, e);
				throw new WTException(e);
			}
			catch( InvocationTargetException e )
			{
				Kogger.error(CarLoad.class, e);
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
			Kogger.error(CarLoad.class, e);
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
	//	Kogger.debug(CarLoad.class, "hash=== "  + hash.size());
		String fileName = "CarLoad.xls";
		boolean flag = loadData(fileName);
		Kogger.debug(CarLoad.class, "Load : " + flag);
		System.exit(0);
	}


}
