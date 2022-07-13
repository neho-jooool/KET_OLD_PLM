package ext.ket.part.migration.spec;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.part.WTPartMaster;
import wt.pom.Transaction;
import wt.query.QuerySpec;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.common.code.NumberCodeType;
import ext.ket.part.entity.KETPartAttribute;
import ext.ket.part.migration.erp.service.MigLogUtil;
import ext.ket.part.spec.service.PartSpecHelper;
import ext.ket.part.spec.service.internal.PartSpecBuilder;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.shared.log.Kogger;

public class PartSpecAttrWcLoader implements RemoteAccess, Serializable {

    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    public static PartSpecAttrWcLoader manager = new PartSpecAttrWcLoader();

    public PartSpecAttrWcLoader() {

    }

    // windchill ext.ket.part.migration.spec.PartSpecAttrWcLoader
    public static void main(String[] args) {

	try {

	    Kogger.debug(PartSpecAttrWcLoader.class, "@start");
	    PartSpecAttrWcLoader.manager.saveFromExcel();
	    Kogger.debug(PartSpecAttrWcLoader.class, "@end");

	} catch (Exception e) {
	    Kogger.debug(PartSpecAttrWcLoader.class, e);
	}
    }

    public void saveFromExcel() throws Exception {

	if (!SERVER) {
	    try {

		Class aclass[] = {};
		Object aobj[] = {};

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
	    executeMigration();
	}
    }

    public void executeMigration() throws WTException {

	SessionContext sessioncontext = SessionContext.newContext();

	ReferenceFactory rf = new ReferenceFactory();
	Transaction trx = null;
	try {

	    trx = new Transaction();
	    trx.start();

	    Kogger.debug(getClass(), "**************** DRAWING  Excel Extract Start **************************");

	    SessionHelper.manager.setAdministrator();
	    
	    PartSpecEnum[] specEnumArry = PartSpecEnum.values();
	    
	    printRbinfo();
	    
	    // 모두 삭제 - 유지보수를 위해 제거함
	    // deleteKetPartAttr();
	    
	    for (PartSpecEnum partSpecEnum : specEnumArry) {

		String attrCode = partSpecEnum.getAttrCode();
		String attrName = partSpecEnum.getAttrName();
		String columnName = partSpecEnum.getColumnName();
		String attrInputType = partSpecEnum.getAttrInputType();
		String attrCodeType = partSpecEnum.getAttrCodeType();
		boolean attrMultiSelect = partSpecEnum.getAttrMultiSelect();
		String attrUnit = partSpecEnum.getAttrUnit();
		boolean sendErp = partSpecEnum.getSendReceiveErp();
		String erpDesc = partSpecEnum.getErpDesc();
		boolean useNumbering = partSpecEnum.getUseNumbering();

		KETPartAttribute partAttr = new PartSpecBuilder.Builder().setAttrCode(attrCode).setAttrName(attrName).setAttrOotbName(attrName).setColumnName(columnName)
		        .setAttrInputType(attrInputType).setAttrDesc(attrUnit).setSendErp(sendErp).setErpDesc(erpDesc).setUseNumbering(useNumbering)
		        .setAttrCodeType(attrCodeType).setAttrMultiSelect(attrMultiSelect).build();

		partAttr = PartSpecHelper.service.insert(partAttr, true /* exist Not Insert */);
	    }

	    Kogger.debug(getClass(), "**************** DRAWING  Excel Extract End **************************");
	    
	    trx.commit();

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    trx.rollback();
	} finally {
	    SessionContext.setContext(sessioncontext);
	}
    }
   
    public void printRbinfo() throws Exception {
	
	MigLogUtil migLogUtil = new MigLogUtil();
	
	Map<String, NumberCodeType> codeTypeMap = new HashMap<String, NumberCodeType>();

	NumberCodeType [] ncs = NumberCodeType.getNumberCodeTypeSet();
	for( NumberCodeType nc : ncs){
	    Kogger.debug(getClass(), nc.getStringValue() + "=" + nc.toString());
	    codeTypeMap.put(nc.toString(), nc);
	}
	
	PartSpecEnum[] specEnumArry = PartSpecEnum.values();
	
	int index = 2280;
	for (PartSpecEnum item : specEnumArry) {
	    if (StringUtils.isNotEmpty(item.getAttrCodeType())) {
		String attrCodeType = item.getAttrCodeType();
		if(codeTypeMap.containsKey(attrCodeType)){
		    continue;
		}else{
		    StringBuffer buffer = new StringBuffer();
		    Kogger.debug(getClass(), "# 부품 속성 - " + item.getAttrName());
		    Kogger.debug(getClass(), attrCodeType +".value=" + item.getAttrName());
		    index = index + 10;
		    Kogger.debug(getClass(), attrCodeType +".order=" +index);
		    Kogger.debug(getClass(), "");
		    migLogUtil.log("PART_SPEC", (WTPartMaster)null, "PartSpec Add", attrCodeType +".value=" + item.getAttrName() , attrCodeType +".order=" +index, "# 부품 속성 - " + item.getAttrName());
		}
	    }
	}
	
	for (NumberCodeType nc : ncs) {
	    if (StringUtils.isNotEmpty(nc.toString())) {
		String attrCodeType = nc.toString();
		boolean hasCode = false;
		for (PartSpecEnum item : specEnumArry) {
		    if (attrCodeType.equals(item.getAttrCodeType())) {
			hasCode = true;
			break;
		    } 
		}
		if(!hasCode){
		    Kogger.debug(getClass(), "## 사라진 코드 찾기:" + attrCodeType);
		    migLogUtil.log("PART_SPEC", (WTPartMaster)null, "PartSpec Delete", "## 사라진 코드 찾기:" + attrCodeType, "## 사라진 코드 찾기:" + attrCodeType, "## 사라진 코드 찾기:" + attrCodeType);
		}
	    }
	}
    }
    
    public void deleteKetPartAttr() throws Exception {
	
	QuerySpec query = new QuerySpec(KETPartAttribute.class);
	QueryResult queryResult = PersistenceHelper.manager.find(query);
	while(queryResult.hasMoreElements()){
	    KETPartAttribute obj = (KETPartAttribute)queryResult.nextElement();
	    PersistenceHelper.manager.delete(obj);
	}
    }
    

}
