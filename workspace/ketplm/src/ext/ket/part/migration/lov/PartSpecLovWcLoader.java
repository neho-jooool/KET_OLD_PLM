package ext.ket.part.migration.lov;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.part.WTPartMaster;
import wt.pom.Transaction;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.common.code.NCodeNCodeLink;
import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeDao;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.code.NumberCodeType;
import e3ps.common.util.KETStringUtil;
import e3ps.cost.util.DBUtil;
import ext.ket.part.migration.erp.service.MigLogUtil;
import ext.ket.shared.log.Kogger;

/**

SELECT *
FROM NUMBERCODE
WHERE CODETYPE LIKE 'SP%'

SELECT *
FROM NUMBERCODEVALUE
WHERE CODETYPE LIKE 'SP%'
  					   
 */
public class PartSpecLovWcLoader implements RemoteAccess, Serializable {

    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    public static PartSpecLovWcLoader manager = new PartSpecLovWcLoader();

    public PartSpecLovWcLoader() {

    }
    //windchill ext.ket.part.migration.lov.PartSpecLovWcLoader Z:\\90.개인폴더\\이응진\\부품사양관리\\업로드포멧\\업로드작업\\제품분류_V1.1_MigrationData.xlsx
    //windchill ext.ket.part.migration.lov.PartSpecLovWcLoader D:\MigrationData.xlsx
    public static void main(String[] args) {

	try {

	    String filePath = null;

	    if (args == null || args.length == 0)
		throw new Exception("@@ args need !");
	    else
		filePath = args[0];

	    Kogger.debug(PartSpecLovWcLoader.class, "@start");
	    PartSpecLovWcLoader.manager.saveFromExcel(filePath);
	    Kogger.debug(PartSpecLovWcLoader.class, "@end");

	} catch (Exception e) {
	    Kogger.debug(PartSpecLovWcLoader.class, e);
	}
    }

    public void saveFromExcel(String filePath) throws Exception {

	if (!SERVER) {
	    try {

		Class aclass[] = { String.class };
		Object aobj[] = { filePath };

		Kogger.debug(getClass(), "@		start");
		RemoteMethodServer.getDefault().invoke("saveFromExcel", null, this, aclass, aobj);
		Kogger.debug(getClass(), "@		end");

		return;

	    } catch (RemoteException e) {
		Kogger.debug(getClass(), e);
	    } catch (InvocationTargetException e) {
		Kogger.debug(getClass(), e);
	    } catch (Exception e) {
		Kogger.debug(getClass(), e);
	    }
	} else {
	    executeMigration(filePath);
	}
    }

    public void executeMigration(String filePath) throws WTException {

	SessionContext sessioncontext = SessionContext.newContext();

	ReferenceFactory rf = new ReferenceFactory();
	Transaction trx = null;
	try {

	    trx = new Transaction();
	    trx.start();

	    Kogger.debug(getClass(), "**************** DRAWING  Excel Extract Start **************************");

	    SessionHelper.manager.setAdministrator();

	    loadPartSpecLov(filePath);

	    Kogger.debug(getClass(), "**************** DRAWING  Excel Extract End **************************");

	    trx.commit();

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    trx.rollback();
	} finally {
	    SessionContext.setContext(sessioncontext);
	}
    }

    private void loadPartSpecLov(String filePath) throws Exception {

	PartSpecLovLoader loader = new PartSpecLovLoader();
	loader.load(filePath);
	List<MigPartLovDTO> list = loader.getList();
	Map<String, MigPartLovDTO> map = loader.getMap();
	Map<String, NumberCodeType> codeTypeMap = new HashMap<String, NumberCodeType>();

	NumberCodeType [] ncs = NumberCodeType.getNumberCodeTypeSet();
	for( NumberCodeType nc : ncs){
	    Kogger.debug(getClass(), nc.getStringValue() + "=" + nc.toString());
	    codeTypeMap.put(nc.toString(), nc);
	}
	
	// 공통 코드 등록
	for (MigPartLovDTO dto : list) {

	    NumberCode oldNumberCode = NumberCodeHelper.manager.getNumberCode(dto.getCodeTypeCode(), dto.getCodeCode());
	    savaCode( dto, oldNumberCode, codeTypeMap);
	}
    }

    private void savaCode(MigPartLovDTO dto, NumberCode numberCode, Map<String, NumberCodeType> codeTypeMap) throws Exception {

	NumberCodeType codeType = null;
	Kogger.debug(getClass(), "codeType=>" + dto.getCodeTypeCode() + "<=, code =>" + dto.getCodeCode() + "<=" );
	
	if(numberCode == null){
	    numberCode = NumberCode.newNumberCode();
	    
	    if(codeTypeMap.containsKey(dto.getCodeTypeCode())){
		codeType = codeTypeMap.get(dto.getCodeTypeCode());
	    }else{
		codeType = NumberCodeType.toNumberCodeType(dto.getCodeTypeCode());
		codeTypeMap.put(dto.getCodeTypeCode(), codeType);
	    }
	    numberCode.setCodeType(codeType);
	}
	
	numberCode.setCode(dto.getCodeCode());
	// numberCode.setCostCode();
	numberCode.setDescription(dto.getCodeDesc());
	numberCode.setDisabled(false);
	// numberCode.setDsCode();
	numberCode.setName(dto.getCodeName());
	// numberCode.setParentReference();
	// numberCode.setPersistInfo();
	numberCode.setSorting(dto.getCodeSorting());
	// numberCode.setVenderCode();
	// numberCode.setWcType();

	numberCode = (NumberCode) PersistenceHelper.manager.save(numberCode);

	if (StringUtils.isNotEmpty(dto.getCodeParentCode())) {

	    if (numberCode.getParent() != null) {

		QueryResult qr = PersistenceHelper.manager.navigate(numberCode, "parent", NCodeNCodeLink.class, false);
		while (qr.hasMoreElements()) {
		    NCodeNCodeLink link = (NCodeNCodeLink) qr.nextElement();
		    PersistenceHelper.manager.delete(link);
		}
	    }
	    
	    numberCode = (NumberCode) PersistenceHelper.manager.refresh(numberCode);

	    NumberCode parent = null;

	    parent = NumberCodeHelper.manager.getNumberCode(dto.getCodeTypeCode(), dto.getCodeParentCode());
	    if (parent == null){
		MigLogUtil migLogUtil = new MigLogUtil();
		String logStr = dto.getCodeTypeCode() + ":" + numberCode.getCode() + ":" + dto.getCodeParentCode();
		String logStr2 = numberCode.getCodeType() + ":" + numberCode.getCode() + ":" + dto.getCodeParentCode();
		String logStr3 = numberCode.getName() + ":" + numberCode.getDescription();
		migLogUtil.log("PART_SPEC_LOV", (WTPartMaster)null, "PartSpec_Lov_Parent", logStr, logStr2, logStr3);
		throw new Exception("## parent can't find!!");
	    }
	    numberCode.setParent(parent);
	    numberCode = (NumberCode) PersistenceHelper.manager.save(numberCode);
	}
	
	Map<String, Object> parameter = new HashMap<String, Object>();
        parameter.put("codeType", "LANGUAGE");
        List<Map<String, Object>> list = NumberCodeHelper.manager.getNumberCodeList(parameter);
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            NumberCodeDao dao = new NumberCodeDao(conn);

            Map<String, Object> param = null;
            for ( Map<String, Object> item : list ) {

                param = new HashMap<String, Object>();

                if ( !item.get("code").equals("ko") ) {

                    param.put("codeType", dto.getCodeTypeCode());
                    param.put("code",     dto.getCodeCode());
                    param.put("lang",     item.get("code"));
                    String value = KETStringUtil.replaceSpecialTag(dto.getCodeNameKr());
                    param.put("value",    value);
                    String desc = KETStringUtil.replaceSpecialTag(dto.getCodeDesc());
                    param.put("desc",     desc);
                    param.put("abbr",     KETStringUtil.replaceSpecialTag(dto.getCodeShortName()));
                    dao.createNumberCodeValue(param);

                }else if ( !item.get("code").equals("en") ) {

                    param.put("codeType", dto.getCodeTypeCode());
                    param.put("code",     dto.getCodeCode());
                    param.put("lang",     item.get("code"));
                    String value = KETStringUtil.replaceSpecialTag(dto.getCodeNameEn());
                    if(StringUtils.isEmpty(value)) value = KETStringUtil.replaceSpecialTag(dto.getCodeNameKr());
                    param.put("value",    value);
                    String desc = KETStringUtil.replaceSpecialTag(dto.getCodeDesc());
                    param.put("desc",     desc);
                    param.put("abbr",     KETStringUtil.replaceSpecialTag(dto.getCodeShortName()));
                    dao.createNumberCodeValue(param);

                }else {
                    param.put("codeType", dto.getCodeTypeCode());
                    param.put("code",     dto.getCodeCode());
                    param.put("lang",     item.get("code"));
                    String value = KETStringUtil.replaceSpecialTag(dto.getCodeNameCn());
                    if(StringUtils.isEmpty(value)) value = KETStringUtil.replaceSpecialTag(dto.getCodeNameKr());
                    param.put("value",    value);
                    String desc = KETStringUtil.replaceSpecialTag(dto.getCodeDesc());
                    param.put("desc",     desc);
                    param.put("abbr",     KETStringUtil.replaceSpecialTag(dto.getCodeShortName()));
                    dao.createNumberCodeValue(param);
                }
            }
        }
        finally {
            DBUtil.close(conn);
        }
    }
}
