package e3ps.sap;

import java.util.HashMap;
import java.util.Vector;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.IRepository;
import com.sap.mw.jco.JCO;
import com.sap.mw.jco.JCO.Client;
import com.sap.mw.jco.JCO.Function;
import com.sap.mw.jco.JCO.Table;

import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.code.NumberCodeType;
import e3ps.common.jdf.config.Config;
import e3ps.common.jdf.config.ConfigImpl;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import ext.ket.shared.log.Kogger;

public class StdInfoInterface
{
	public static Config conf = ConfigImpl.getInstance();
	public static boolean isERPCheck = conf.getBoolean( "ERPCHECK" );

	// KET Test Function
	public static void testBOM()
	{
		Client client = null;
		IRepository repository = null;

		try
		{
Kogger.debug(StdInfoInterface.class,  "======> StdInfoInterface :: testBOM() 01" );

			client = RFCConnect.getConnection();
Kogger.debug(StdInfoInterface.class,  "======> StdInfoInterface :: testBOM() 02" );

	        client.connect();
Kogger.debug(StdInfoInterface.class,  "======> StdInfoInterface :: testBOM() 03" );

	        repository = JCO.createRepository( "BFREPOSITORY", client );
Kogger.debug(StdInfoInterface.class,  "======> StdInfoInterface :: testBOM() 04" );

	        IFunctionTemplate tmpl = repository.getFunctionTemplate( "ZKIS_FUNC_TEST01" );
Kogger.debug(StdInfoInterface.class,  "======> StdInfoInterface :: testBOM() 05" );
Kogger.debug(StdInfoInterface.class,  "======> StdInfoInterface :: testBOM() :: Function Name : " + tmpl.getName() );

	        Function function = tmpl.getFunction();
Kogger.debug(StdInfoInterface.class,  "======> StdInfoInterface :: testBOM() 06" );

	        function.getImportParameterList().setValue( "110001", "I_MATERIAL" );
	        function.getImportParameterList().setValue( "2000", "I_PLANT" );
	        function.getImportParameterList().setValue( "2", "I_BOM_USAGE" );
	        function.getImportParameterList().setValue( "20101022", "I_VALID_FROM" );

Kogger.debug(StdInfoInterface.class,  "======> StdInfoInterface :: testBOM() 07" );
	        JCO.Structure inputStructure = function.getImportParameterList().getStructure("I_STKO");
Kogger.debug(StdInfoInterface.class,  "======> StdInfoInterface :: testBOM() 08" );

	        inputStructure.setValue( "10000", "BASE_QUAN" );
	        inputStructure.setValue( "EA", "BASE_UNIT" );
	        inputStructure.setValue( "Test Text", "ALT_TEXT" );
	        inputStructure.setValue( "", "LABORATORY" );
	        inputStructure.setValue( "", "DELETE_IND" );
	        inputStructure.setValue( "", "BOM_TEXT" );
	        inputStructure.setValue( "", "BOM_GROUP" );
	        inputStructure.setValue( "", "AUTH_GROUP" );
	        inputStructure.setValue( "", "CAD_IND" );
	        function.getImportParameterList().setValue( inputStructure, "I_STKO" );

Kogger.debug(StdInfoInterface.class,  "======> StdInfoInterface :: testBOM() 09" );

	        JCO.Table inputTable = function.getTableParameterList().getTable( "T_STPO" );
Kogger.debug(StdInfoInterface.class,  "======> StdInfoInterface :: testBOM() 10" );

			inputTable.appendRow();
			inputTable.setValue( "L", "ITEM_CATEG" );
			inputTable.setValue( "0010", "ITEM_NO" );
			inputTable.setValue( "H110001", "COMPONENT" );
			inputTable.setValue( "10100", "COMP_QTY" );
			inputTable.setValue( "EA", "COMP_UNIT" );
			inputTable.setValue( "10", "SORTSTRING" );
			inputTable.setValue( "X", "REL_COST" );
			inputTable.setValue( "X", "REL_PROD" );
			inputTable.setValue( "2000", "ISSUE_LOC" );
			inputTable.setValue( "X", "PM_ASSMBLY" );
			inputTable.setValue( "20101022", "VALID_FROM" );
Kogger.debug(StdInfoInterface.class,  "======> StdInfoInterface :: testBOM() 11" );

			inputTable.appendRow();
			inputTable.setValue( "L", "ITEM_CATEG" );
			inputTable.setValue( "0020", "ITEM_NO" );
			inputTable.setValue( "P100006", "COMPONENT" );
			inputTable.setValue( "1", "COMP_QTY" );
			inputTable.setValue( "EA", "COMP_UNIT" );
			inputTable.setValue( "20", "SORTSTRING" );
			inputTable.setValue( "X", "REL_PROD" );
			inputTable.setValue( "X", "BULK_MAT" );
			inputTable.setValue( "20101022", "VALID_FROM" );
Kogger.debug(StdInfoInterface.class,  "======> StdInfoInterface :: testBOM() 12" );

	        try
	        {
	        	client.execute(function);
Kogger.debug(StdInfoInterface.class,  "======> StdInfoInterface :: testBOM() 13" );

	        }
	        catch( Exception e )
	        {
	        	Kogger.debug(StdInfoInterface.class,  "StdInfoInterface[testBOM]>>> "+e.toString() );
	        }

	        String r = (String)function.getExportParameterList().getValue( "E_RETURN" );
	        int c = function.getExportParameterList().getInt( "E_CNT" );
	        String fl = function.getExportParameterList().getString( "E_FL_WARNING" );
	        String bn = function.getExportParameterList().getString( "E_BOM_NO" );

	        Kogger.debug(StdInfoInterface.class, "E_RETURN<<<<<< "+ r );
	        Kogger.debug(StdInfoInterface.class, "E_CNT<<<<<< "+ c );
	        Kogger.debug(StdInfoInterface.class,  "E_FL_WARNING<<<<<< "+ fl );
	        Kogger.debug(StdInfoInterface.class,  "E_BOM_NO<<<<<< "+ bn );

			JCO.Table outputTable = function.getTableParameterList().getTable( "T_STPO" );
			String changeNo = "";
			for( int i = 0; i < outputTable.getNumRows(); i++ )
			{
				outputTable.setRow( i );
				changeNo = outputTable.getValue( "CHANGE_NO" ).toString();

Kogger.debug(StdInfoInterface.class,  "Result Change No. " + (i+1) + " <<<<<< "+ changeNo );
			}
		}
		catch( Exception e )
		{
			Kogger.error(StdInfoInterface.class, e);
		}
		finally
		{
			client.disconnect();
			repository = null;
		}
	}
	/////////////////////////////////////////////////////////////////////////

	// KET Test Function
	public static Vector getVendorInfo()
	{
        Vector returnVec = new Vector();

		Client client = null;
		IRepository repository = null;

		try
		{
Kogger.debug(StdInfoInterface.class,  "======> StdInfoInterface :: getVendorInfo() 01" );

			client = RFCConnect.getConnection();
Kogger.debug(StdInfoInterface.class,  "======> StdInfoInterface :: getVendorInfo() 02" );

	        client.connect();
Kogger.debug(StdInfoInterface.class,  "======> StdInfoInterface :: getVendorInfo() 03" );

	        repository = JCO.createRepository( "BFREPOSITORY", client );
Kogger.debug(StdInfoInterface.class,  "======> StdInfoInterface :: getVendorInfo() 04" );

	        IFunctionTemplate tmpl = repository.getFunctionTemplate( "ZKIS_FUNC_TEST00" );
Kogger.debug(StdInfoInterface.class,  "======> StdInfoInterface :: getVendorInfo() 05" );
Kogger.debug(StdInfoInterface.class,  "======> StdInfoInterface :: getVendorInfo() :: Function Name : " + tmpl.getName() );

	        Function function = tmpl.getFunction();
Kogger.debug(StdInfoInterface.class,  "======> StdInfoInterface :: getVendorInfo() 06" );

//	        function.getImportParameterList().setValue( "100143", "I_LIFNR" );
Kogger.debug(StdInfoInterface.class,  "======> StdInfoInterface :: getVendorInfo() 07" );

	        Table tables = function.getTableParameterList().getTable( "T_LIFNR" );
Kogger.debug(StdInfoInterface.class,  "======> StdInfoInterface :: getVendorInfo() 08" );
Kogger.debug(StdInfoInterface.class,  "======> StdInfoInterface :: getVendorInfo() table rows 1 : " + tables.getNumRows() );

	        try
	        {
	        	client.execute(function);
Kogger.debug(StdInfoInterface.class,  "======> StdInfoInterface :: getVendorInfo() 09" );
Kogger.debug(StdInfoInterface.class,  "======> StdInfoInterface :: getVendorInfo() table rows 2 : " + tables.getNumRows() );

	        }
	        catch( Exception e )
	        {
	        	Kogger.debug(StdInfoInterface.class,  "StdInfoInterface[getVendorInfo]>>> "+e.toString() );
	        }

	        String r = (String)function.getExportParameterList().getValue( "E_RETURN" );
	        int c = function.getExportParameterList().getInt( "E_CNT" );

	        Kogger.debug(StdInfoInterface.class,  "E_RETURN<<<<<< "+ r );
	        Kogger.debug(StdInfoInterface.class,  "E_CNT<<<<<< "+ c );

	        for( int j = 0; j < tables.getNumRows(); j++ )
	        {
	        	tables.setRow(j);
	        	returnVec.addElement( tables.getString("LIFNR") + "===" + tables.getString("NAME1") );
	        }
		}
		catch( Exception e )
		{
			Kogger.error(StdInfoInterface.class, e);
		}
		finally
		{
			client.disconnect();
			repository = null;
		}

		return returnVec;
	}
	/////////////////////////////////////////////////////////////////////////

	public static String getCustomer()
	{
		String returnValue = "";

		Client client = null;
		IRepository repository = null;

		try
		{
			client = RFCConnect.getConnection();
	        client.connect();
	        repository = JCO.createRepository( "BFREPOSITORY", client );

	        IFunctionTemplate tmpl = repository.getFunctionTemplate("ZPS_PLM_IF_PJTCODE");
	        Function function = tmpl.getFunction();
	        function.getImportParameterList().setValue("1", "I_GUBUN");
	        Table tables = function.getTableParameterList().getTable("T_KUNNR");

	        try
	        {
	        	client.execute(function);
	        }
	        catch( Exception e )
	        {
	        	Kogger.debug(StdInfoInterface.class, "StdInfoInterface[getCustomer]>>> "+e.toString());
	        }

	        String t = (String) function.getExportParameterList().getValue("E_TYPE");
	        String m = (String) function.getExportParameterList().getValue("E_MESG");
	        int c = function.getExportParameterList().getInt("E_COUNT");

	        Kogger.debug(StdInfoInterface.class, "E_TYPE<<<<<< "+t);
	        Kogger.debug(StdInfoInterface.class, "E_MESG<<<<<< "+m);
	        Kogger.debug(StdInfoInterface.class, "E_COUNT<<<<< "+c);

	        String code = "";
	        String name = "";

	        for( int j = 0; j < tables.getNumRows(); j++ )
	        {
	        	tables.setRow(j);

	        	code = tables.getString("KUNNR");
	        	name = tables.getString("NAME1");

	        	NumberCode num = null;

	        	if( code.startsWith("1") )
	        	{	//국내
	        		num = getCustomerCODE(1, code);
	        	}
	        	else if( code.startsWith("2") )
	        	{	//해외
	        		num = getCustomerCODE(2, code);
	        	}

	        	if( num != null )
	        	{	//관리코드로 이미 존재함
	        		//Kogger.debug(StdInfoInterface.class, "NUMBERCODE[존재함]>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+CommonUtil.getOIDString(num));
	        		num.setName(name);
	        		PersistenceHelper.manager.modify(num);
	        	}
	        	else
	        	{	//관리코드로 존재하지 않음
	        		//Kogger.debug(StdInfoInterface.class, "NUMBERCODE[존재하지 않음]");
	        		num = NumberCode.newNumberCode();

	        		num.setCode(code);
	        		num.setName(name);
	        		num.setDescription("");
	        		num.setCodeType(NumberCodeType.toNumberCodeType("CUSTOMERCODE"));

	        		if( code.startsWith("1") )
	        		{
	        			num.setParent(getCustomerLevelOne(1));
	        		}
	        		else if( code.startsWith("2") )
	        		{
	        			num.setParent(getCustomerLevelOne(2));
	        		}

	        		num = (NumberCode) PersistenceHelper.manager.save(num);
	        	}

//	        	code += "^"+tables.getString("KUNNR");
//	        	name += "^"+tables.getString("NAME1");

//	        	Kogger.debug(StdInfoInterface.class, "CODE>>>>>>>>> "+code);
//	        	Kogger.debug(StdInfoInterface.class, "NAME>>>>>>>>> "+name);
	        }

	        returnValue = m;
		}
		catch( WTException e )
		{
			Kogger.error(StdInfoInterface.class, e);
		}
		catch( WTPropertyVetoException e )
		{
			Kogger.error(StdInfoInterface.class, e);
		}
		finally
		{
			client.disconnect();
			repository = null;
		}

		return returnValue;
	}

	public static NumberCode getCustomerCODE( int parentCode, String currentCode )
	{
		NumberCode num = null;
		QuerySpec mainSpec = null;
		QueryResult mainRs = null;

		//select * from numbercode where codetype = 'CUSTOMERCODE' and (select ida2a2 from numbercode where codetype = 'CUSTOMERCODE' and name = '국내') = ida3a3

		try
		{
			mainSpec = new QuerySpec();

			Class target = NumberCode.class;
			int idx_target = mainSpec.addClassList(target, true);

			mainSpec.appendWhere(new SearchCondition(NumberCode.class, "codeType", SearchCondition.EQUAL, NumberCodeType.toNumberCodeType("CUSTOMERCODE")), idx_target);
			SearchUtil.appendEQUAL(mainSpec, target, "parentReference.key.id", CommonUtil.getOIDLongValue(getCustomerLevelOne(parentCode)), idx_target);
			SearchUtil.appendEQUAL(mainSpec, target, "code", currentCode, idx_target);

			mainRs = PersistenceHelper.manager.find(mainSpec);

			while( mainRs.hasMoreElements() )
			{
				Object[] obj = (Object[]) mainRs.nextElement();
				num = (NumberCode) obj[0];
			}

		}
		catch( QueryException e )
		{
			Kogger.error(StdInfoInterface.class, e);
		}
		catch( WTException e )
		{
			Kogger.error(StdInfoInterface.class, e);
		}

		return num;
	}

	public static NumberCode getCustomerLevelOne( int parentCode )
	{
		NumberCode num = null;
		QuerySpec spec = null;
		QueryResult result = null;

		try
		{
			spec = new QuerySpec();

			Class target = NumberCode.class;
			int idx_target = spec.addClassList(target, true);

			spec.appendWhere(new SearchCondition(NumberCode.class, "codeType", SearchCondition.EQUAL, NumberCodeType.toNumberCodeType("CUSTOMERCODE")), idx_target);

			if( parentCode == 1 )
			{
				SearchUtil.appendEQUAL(spec, target, "name", "국내", idx_target);
			}
			else if( parentCode == 2 )
			{
				SearchUtil.appendEQUAL(spec, target, "name", "해외", idx_target);
			}

			result = PersistenceHelper.manager.find(spec);
//			Kogger.debug(StdInfoInterface.class, "resule["+result.size()+"]!!!!!!!!!!!!!!");

			while( result.hasMoreElements() )
			{
				Object[] obj = (Object[]) result.nextElement();
				num = (NumberCode) obj[0];
//				Kogger.debug(StdInfoInterface.class, "NumberCode<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< "+CommonUtil.getOIDString(num));
			}
		}
		catch( QueryException e )
		{
			Kogger.error(StdInfoInterface.class, e);
		}
		catch( WTException e )
		{
			Kogger.error(StdInfoInterface.class, e);
		}

		return num;
	}

	public static String setAllModel() throws WTException
	{
		String returnValue = "";

		Client client = null;
		IRepository repository = null;

		try
		{
			client = RFCConnect.getConnection();
	        client.connect();
	        repository = JCO.createRepository("BFREPOSITORY", client);

	        IFunctionTemplate tmpl = repository.getFunctionTemplate("ZPS_SAP_IF_PJTCODE");
	        Function function = tmpl.getFunction();
	        function.getImportParameterList().setValue("1", "I_GUBUN");
	        Table tables = function.getTableParameterList().getTable("T_MODEL");

	        //OLD
	        HashMap map = new HashMap();
			map.put("type", "MODELCODE");
			map.put("isParent", "true");

			QuerySpec qs = new QuerySpec();
			QueryResult qr = null;
			NumberCode mainNC = null;

			qs = NumberCodeHelper.getCodeQuerySpec(map);
			qr = PersistenceHelper.manager.find(qs);

			Object[] obj = null;

			for(int i = 0; i < qr.size(); i++)
			{
				obj = (Object[])qr.nextElement();
				mainNC = (NumberCode) obj[0];

				Kogger.debug(StdInfoInterface.class, "NumberCode[MODEL]>>>>> "+mainNC.getName()+"["+mainNC.getCode()+"]!!!!!");
			}

	        //NEW
	        Vector tMap = NumberCodeHelper.manager.getNumberCodeLevel("MODELCODE", "child");
			for(int i = 0; i < tMap.size(); i++)
			{
				mainNC = (NumberCode)tMap.get(i);
				Kogger.debug(StdInfoInterface.class, "mainNC>>>> "+mainNC.getCode());
				tables.appendRow();

				tables.setValue(mainNC.getCode(), "ZMODEL");
				tables.setValue(mainNC.getName(), "ZTEXT");
			}

	        try
	        {
	        	client.execute(function);
	        }
	        catch (Exception e)
	        {
	        	Kogger.debug(StdInfoInterface.class, "StdInfoInterface[setAllModel]>>> "+e.toString());
	        }

	        String t = (String) function.getExportParameterList().getValue("E_TYPE");
	        String m = (String) function.getExportParameterList().getValue("E_MESG");
	        int c = function.getExportParameterList().getInt("E_COUNT");

	        returnValue = m;
		}
		catch(Exception e)
		{
			Kogger.error(StdInfoInterface.class, e);
		}

		return returnValue;
	}
}
