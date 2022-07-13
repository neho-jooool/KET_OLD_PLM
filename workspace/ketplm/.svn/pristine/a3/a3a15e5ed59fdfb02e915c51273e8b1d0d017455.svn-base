package e3ps.project.machine.load;

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
import wt.query.ClassAttribute;
import wt.query.ColumnExpression;
import wt.query.ConstantExpression;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.util.WTException;
import wt.util.WTProperties;
import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.util.CommonUtil;
import e3ps.project.machine.MoldMachine;
import ext.ket.shared.log.Kogger;



public class MachineLoad implements wt.method.RemoteAccess, java.io.Serializable
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
						MachineLoad.class.getName(),
						null,
						argTypes,
						args);
			}
			catch( RemoteException e )
			{
				Kogger.error(MachineLoad.class, e);
				throw new WTException(e);
			}
			catch( InvocationTargetException e )
			{
				Kogger.error(MachineLoad.class, e);
				throw new WTException(e);
			}

			return ((Boolean)obj).booleanValue();
		}

		Transaction trx = new Transaction();
		try
		{
			if( fileName == null || fileName.length() == 0 )
			{
				fileName = "MachineLoad.xls";
			}

            String wt_home = WTProperties.getServerProperties().getProperty("wt.home");
            String loadBase_home = wt_home + File.separator +
            						"loadFiles" + File.separator +
            						"ket" + File.separator +
            						"code";

			File file = new File( loadBase_home + File.separator + fileName );
			
			if( file == null )
			{
				Kogger.debug(MachineLoad.class, "File not found!!!");
				return false;
			}

			//trx.start();
			
			Hashtable machinType_mold = new Hashtable();
			Hashtable machinType_press = new Hashtable();
			
			
			Hashtable machinTon = new Hashtable();
			
			Hashtable machinMaker_mold = new Hashtable();
			Hashtable machinMaker_press = new Hashtable();
//			Hashtable type비철 = new Hashtable();
			
//			Vector vec = NumberCodeHelper.manager.getNumberCodeLevelType("MATERIALMAKER", "수지");
			
			machinType_mold = getHashNumberCode("MACHINETYPE", "Mold");
			machinType_press = getHashNumberCode("MACHINETYPE", "Press");
			
			machinTon = getHashNumberCode("MACHINETON");
			
			machinMaker_mold = getHashNumberCode("MACHINEMAKER", "Mold");
			machinMaker_press = getHashNumberCode("MACHINEMAKER", "Press");

			
			
			Workbook workbook = Workbook.getWorkbook(file);
			Sheet sheets[] = workbook.getSheets();

			if( sheets.length > 0 )
			{
				Sheet sheet = null;

				for( int i = 0; i < sheets.length; i++ )
				{
					sheet = sheets[i];
					Kogger.debug(MachineLoad.class,  "######### Sheet [" + sheet.getName() + "] Loading start ########" );


					int rows = sheet.getRows();

					for( int j = 1; j < rows; j++ )
					{
						System.out.print( "@>>>>>> Row[" + j + "]" + " begin >>>>> " );

						
						/*
						 * 0:원재료 구분
						 * 1:Maker
						 * 2:type
						 * 3:grade
						 */

						Cell a1 = sheet.getCell(0,j);
						String a1Str = a1.getContents();
						if(a1Str == null) a1Str = "";

						if( a1Str.startsWith("#") )
						{
							continue;
						}

						Cell b1 = sheet.getCell(1, j);
						Cell c1 = sheet.getCell(2, j);
						Cell d1 = sheet.getCell(3, j);
						Cell e1 = sheet.getCell(4, j);
						

						String b1Str = b1.getContents();
						String c1Str = c1.getContents();
						String d1Str = d1.getContents();
						String e1Str = e1.getContents();
						

						if( b1Str == null ) b1Str = "";
						if( c1Str == null ) c1Str = "";
						if( d1Str == null ) d1Str = "";
						if( e1Str == null ) e1Str = "";
						

						
						String gubun = a1Str.trim(); //원재료 구분
						String type = b1Str.trim(); //type
						String ton = c1Str.trim(); //ton
						String maker = d1Str.trim(); //maker
						String model = e1Str.trim(); //model;
						
						NumberCode typeCode = null;
						NumberCode tonCode = null;
						NumberCode makerCode = null;
						
						tonCode = (NumberCode)machinTon.get(ton);
						
						if(gubun.equals("Mold")){
							
							typeCode = (NumberCode)machinType_mold.get(type);
							makerCode = (NumberCode)machinMaker_mold.get(maker);					
						}else if(gubun.equals("Press")){
							typeCode = (NumberCode)machinType_press.get(type);
							makerCode = (NumberCode)machinMaker_press.get(maker);	
							
						}else{
							throw new WTException( (j + 1) + " 라인 구분이 적합하지 않습니다." );
						}
						
						if(typeCode == null){
							throw new WTException( (j + 1) + " 라인 Type이 코드체계에 존재하지 않습니다." );
						}
						
						if(tonCode == null){
							throw new WTException( (j + 1) + " Ton 이 코드체계에 존재하지 않습니다." );
						}
						
						if(makerCode == null){
							throw new WTException( (j + 1) + " 라인 Maker가 코드체계에 존재하지 않습니다." );
						}
						
						if(model.length() == 0){
							throw new WTException( (j + 1) + " 라인 model이 적합하지 않습니다." );
						}
						
						
						MoldMachine data = checkCodeData(gubun, typeCode, tonCode, makerCode, model);
						
						if(data != null){
							continue;
						}
						
						data = MoldMachine.newMoldMachine();
						data.setModel(model);
						data.setMoldType(gubun);
						data.setMachineMaker(makerCode);
						data.setMachineType(typeCode);
						data.setTon(tonCode);
						
						PersistenceHelper.manager.save(data);
						

						
					}

					Kogger.debug(MachineLoad.class,  "######### Sheet [" + sheet.getName() + "] end ########" );
				}
			}

			workbook.close();
			trx.commit();
			trx = null;
		}
		catch( Exception e )
		{
			Kogger.error(MachineLoad.class, e);
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

	public static MoldMachine checkCodeData(String gubun, NumberCode type, NumberCode ton, NumberCode maker, String model) throws WTException
	{
		if( !SERVER )
		{
			Class argTypes[] = new Class[]{String.class, NumberCode.class, NumberCode.class, NumberCode.class, String.class};
			Object args[] = new Object[]{gubun, type, ton, maker, model};
			Object obj = null;

			try
			{
				obj = wt.method.RemoteMethodServer.getDefault().invoke(
						"checkCodeData",
						MachineLoad.class.getName(),
						null,
						argTypes,
						args);
			}
			catch( RemoteException e )
			{
				Kogger.error(MachineLoad.class, e);
				throw new WTException(e);
			}
			catch( InvocationTargetException e )
			{
				Kogger.error(MachineLoad.class, e);
				throw new WTException(e);
			}

			return (MoldMachine)obj;
		}

		try
		{
			QuerySpec qs = new QuerySpec();
			int index1 = qs.addClassList(MoldMachine.class, true);
			
			long makerId = CommonUtil.getOIDLongValue(maker);
			long typeId = CommonUtil.getOIDLongValue(type);
			long tonId = CommonUtil.getOIDLongValue(ton);
			
			
			qs.appendWhere(new SearchCondition(MoldMachine.class, MoldMachine.MOLD_TYPE, "=", gubun) , new int[]{0});
			qs.appendAnd();
			qs.appendWhere(new SearchCondition(MoldMachine.class, MoldMachine.TON_REFERENCE + ".key.id", "=", tonId) , new int[]{0});
			qs.appendAnd();
			qs.appendWhere(new SearchCondition(MoldMachine.class, MoldMachine.MACHINE_TYPE_REFERENCE + ".key.id", "=", typeId) , new int[]{0});
			qs.appendAnd();
			qs.appendWhere(new SearchCondition(MoldMachine.class, MoldMachine.MACHINE_MAKER_REFERENCE + ".key.id", "=", makerId) , new int[]{0});
			qs.appendAnd();
			
			
			ClassAttribute ca0 = new ClassAttribute(MoldMachine.class, MoldMachine.MODEL);
        	SQLFunction upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca0);
   
        	model = model.toUpperCase();
        	ColumnExpression ce = ConstantExpression.newExpression(model);
        	qs.appendWhere(new SearchCondition(upper, SearchCondition.EQUAL, ce) , new int[]{index1});
			
			QueryResult qr = PersistenceHelper.manager.find(qs);

			if( qr.hasMoreElements() )
			{
				Object obj[] = (Object[])qr.nextElement();
				return (MoldMachine)obj[0];
			}
		}
		catch(Exception e)
		{
			Kogger.error(MachineLoad.class, e);
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
	//	Kogger.debug(MachineLoad.class, "hash=== "  + hash.size());
		String fileName = "MachineLoad.xls";
		boolean flag = loadData(fileName);
		Kogger.debug(MachineLoad.class, "Load : " + flag);
		System.exit(0);
	}


}
