package e3ps.project.material.load;

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
import e3ps.project.material.MoldMaterial;
import ext.ket.shared.log.Kogger;


public class MaterialLoad implements wt.method.RemoteAccess, java.io.Serializable
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
						MaterialLoad.class.getName(),
						null,
						argTypes,
						args);
			}
			catch( RemoteException e )
			{
				Kogger.error(MaterialLoad.class, e);
				throw new WTException(e);
			}
			catch( InvocationTargetException e )
			{
				Kogger.error(MaterialLoad.class, e);
				throw new WTException(e);
			}

			return ((Boolean)obj).booleanValue();
		}

		Transaction trx = new Transaction();
		try
		{
			if( fileName == null || fileName.length() == 0 )
			{
				fileName = "MaterialLoad.xls";
			}

            String wt_home = WTProperties.getServerProperties().getProperty("wt.home");
            String loadBase_home = wt_home + File.separator +
            						"loadFiles" + File.separator +
            						"ket" + File.separator +
            						"code";

			File file = new File( loadBase_home + File.separator + fileName );
			
			if( file == null )
			{
				Kogger.debug(MaterialLoad.class, "File not found!!!");
				return false;
			}

			//trx.start();
			
			Hashtable maker수지 = new Hashtable();
			Hashtable maker비철 = new Hashtable();
			
			Hashtable type수지 = new Hashtable();
			Hashtable type비철 = new Hashtable();
			
//			Vector vec = NumberCodeHelper.manager.getNumberCodeLevelType("MATERIALMAKER", "수지");
			
			maker수지 = getMaterialNumberCode("MATERIALMAKER", "수지");
			maker비철 = getMaterialNumberCode("MATERIALMAKER", "비철");
			
			type수지 = getMaterialNumberCode("MATERIALTYPE", "수지");
			type비철 = getMaterialNumberCode("MATERIALTYPE", "비철");
			
			
			Workbook workbook = Workbook.getWorkbook(file);
			Sheet sheets[] = workbook.getSheets();

			if( sheets.length > 0 )
			{
				Sheet sheet = null;

				for( int i = 0; i < sheets.length; i++ )
				{
					sheet = sheets[i];
					Kogger.debug(MaterialLoad.class,  "######### Sheet [" + sheet.getName() + "] Loading start ########" );


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

						String b1Str = b1.getContents();
						String c1Str = c1.getContents();
						String d1Str = d1.getContents();


						if( b1Str == null ) b1Str = "";
						if( c1Str == null ) c1Str = "";
						if( d1Str == null ) d1Str = "";

						
						String materialGubun = a1Str.trim(); //원재료 구분
						String maker = b1Str.trim(); //Maker
						String materialType = c1Str.trim(); //type
						String grade = d1Str.trim(); //grade
						
						NumberCode makerCode = null;
						NumberCode typeCode = null;
						
						if(materialGubun.equals("수지")){
							makerCode = (NumberCode)maker수지.get(maker);
							typeCode = (NumberCode)type수지.get(materialType);							
						}else if(materialGubun.equals("비철")){
							makerCode = (NumberCode)maker비철.get(maker);
							typeCode = (NumberCode)type비철.get(materialType);
						}else{
							throw new WTException( (j + 1) + " 라인 구분이 적합하지 않습니다." );
						}
						
						if(makerCode == null){
							throw new WTException( (j + 1) + " 라인 Maker가 코드체계에 존재하지 않습니다." );
						}
						
						if(typeCode == null){
							throw new WTException( (j + 1) + " 라인 Type이 코드체계에 존재하지 않습니다." );
						}
						
						MoldMaterial data = checkCodeData(materialGubun, makerCode, typeCode, grade);
						
						if(data != null){
							continue;
						}
						
						data = MoldMaterial.newMoldMaterial();
						
						data.setMaterial(materialGubun);
						data.setGrade(grade);
						
						data.setMaterialMaker(makerCode);
						data.setMaterialType(typeCode);
						PersistenceHelper.manager.save(data);
						

						Kogger.debug(MaterialLoad.class, materialGubun + " " + " " + maker + " " + materialType + " " + grade);
					}

					Kogger.debug(MaterialLoad.class,  "######### Sheet [" + sheet.getName() + "] end ########" );
				}
			}

			workbook.close();
			trx.commit();
			trx = null;
		}
		catch( Exception e )
		{
			Kogger.error(MaterialLoad.class, e);
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
	
	public static Hashtable getMaterialNumberCode(String type, String gubun){
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

	public static MoldMaterial checkCodeData(String material, NumberCode maker, NumberCode type, String grade) throws WTException
	{
		if( !SERVER )
		{
			Class argTypes[] = new Class[]{NumberCode.class, NumberCode.class, NumberCode.class, String.class};
			Object args[] = new Object[]{material, maker, type, grade};
			Object obj = null;

			try
			{
				obj = wt.method.RemoteMethodServer.getDefault().invoke(
						"checkCodeData",
						MaterialLoad.class.getName(),
						null,
						argTypes,
						args);
			}
			catch( RemoteException e )
			{
				Kogger.error(MaterialLoad.class, e);
				throw new WTException(e);
			}
			catch( InvocationTargetException e )
			{
				Kogger.error(MaterialLoad.class, e);
				throw new WTException(e);
			}

			return (MoldMaterial)obj;
		}

		try
		{
			QuerySpec qs = new QuerySpec();
			int index1 = qs.addClassList(MoldMaterial.class, true);
			long makerId = CommonUtil.getOIDLongValue(maker);
			long typeId = CommonUtil.getOIDLongValue(type);
			qs.appendWhere(new SearchCondition(MoldMaterial.class, MoldMaterial.MATERIAL, "=", material) , new int[]{0});
			qs.appendAnd();
			qs.appendWhere(new SearchCondition(MoldMaterial.class, MoldMaterial.MATERIAL_MAKER_REFERENCE + ".key.id", "=", makerId) , new int[]{0});
			qs.appendAnd();
			qs.appendWhere(new SearchCondition(MoldMaterial.class, MoldMaterial.MATERIAL_TYPE_REFERENCE + ".key.id", "=", typeId) , new int[]{0});
			
			qs.appendAnd();
			
			ClassAttribute ca0 = new ClassAttribute(MoldMaterial.class, MoldMaterial.GRADE);
        	SQLFunction upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca0);
        	
        	grade = grade.toUpperCase();
        	ColumnExpression ce = ConstantExpression.newExpression(grade);
        	qs.appendWhere(new SearchCondition(upper, SearchCondition.EQUAL, ce) , new int[]{index1});
			
			QueryResult qr = PersistenceHelper.manager.find(qs);

			if( qr.hasMoreElements() )
			{
				Object obj[] = (Object[])qr.nextElement();
				return (MoldMaterial)obj[0];
			}
		}
		catch(Exception e)
		{
			Kogger.error(MaterialLoad.class, e);
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

		String fileName = "MaterialLoad.xls";
		boolean flag = loadData(fileName);
		Kogger.debug(MaterialLoad.class, "Load : " + flag);
		System.exit(0);
	}


}
