package ext.ket.part.migration.claz;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.pds.StatementSpec;
import wt.pom.Transaction;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.common.util.CommonUtil;
import ext.ket.part.classify.service.PartClazHelper;
import ext.ket.part.entity.KETPartClassAttrLink;
import ext.ket.part.entity.KETPartClassification;
import ext.ket.part.entity.dto.PartClassificationDTO;
import ext.ket.shared.log.Kogger;

public class PartClazCopy implements RemoteAccess, Serializable {

    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    public static PartClazCopy manager = new PartClazCopy();

    public PartClazCopy() {

    }
    
    // windchill ext.ket.part.migration.claz.PartClazCopy A10 B
    // arg[0] => 복사 원본
    // arg[1] => 복사한 후 어느 분류체계의 child로 붙일 것인지를 결정한다
    // 해당 프로그램의 목적 : 현재 신규 분류체계 생성시 기존의 분류쳬계 복사가 안되는 등의 이유로 작업효율성이 떨어저 3레벨의 분류쳬계 한 세트를 자동 복사 2017.02.15 by 황정태 
    public static void main(String[] args) {

	try {

	    String sourceClassCode = "";
	    String targetParentClassCode = "";

	    if (args == null || args.length == 0)
		throw new Exception("@@ args need !");
	    else
		sourceClassCode = args[0];
	    targetParentClassCode = args[1];

	    Kogger.debug(PartClazCopy.class, "@start");
	    PartClazCopy.manager.saveFromExcel(sourceClassCode, targetParentClassCode);
	    Kogger.debug(PartClazCopy.class, "@end");

	} catch (Exception e) {
	    Kogger.debug(PartClazCopy.class, e);
	}
    }

    public void saveFromExcel(String sourceClassCode, String targetParentClassCode) throws Exception {

	if (!SERVER) {
	    try {

		Class aclass[] = { String.class, String.class };
		Object aobj[] = { sourceClassCode, targetParentClassCode };

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
	    executeMigration(sourceClassCode, targetParentClassCode);
	}
    }

    public void attrCopy(KETPartClassification source, KETPartClassification target) throws Exception {//attribute 복사
	QuerySpec query = new QuerySpec();
	int idx = query.appendClassList(KETPartClassAttrLink.class, true);

	query.appendWhere(
	        new SearchCondition(KETPartClassAttrLink.class, "roleBObjectRef.key.id", "=", CommonUtil.getOIDLongValue(source)),
	        new int[] { idx });

	StatementSpec Lastquery = query;

	QueryResult qr = PersistenceHelper.manager.find(Lastquery);

	KETPartClassAttrLink sourceAttrLink = null;
	KETPartClassAttrLink targetAttrLink = null;
	while (qr.hasMoreElements()) {
	    Object[] obj = (Object[]) qr.nextElement();
	    sourceAttrLink = (KETPartClassAttrLink) obj[0];

	    targetAttrLink = KETPartClassAttrLink.newKETPartClassAttrLink(sourceAttrLink.getAttr(), target);
	    targetAttrLink.setChecked(sourceAttrLink.isChecked());
	    targetAttrLink.setEssential(sourceAttrLink.isEssential());
	    targetAttrLink.setIndexCol(sourceAttrLink.getIndexCol());
	    targetAttrLink.setHpYn(sourceAttrLink.isHpYn());
	    targetAttrLink.setIndexRow(sourceAttrLink.getIndexRow());
	    targetAttrLink.setIndexSort(sourceAttrLink.getIndexSort());
	    PersistenceHelper.manager.save(targetAttrLink);
	}
    }

    public KETPartClassification clazCopy(KETPartClassification source, String targetParentClassCode) throws Exception {//레벨 1 복사
	KETPartClassification target = KETPartClassification.newKETPartClassification();
	
	KETPartClassification parent = PartClazHelper.service.getPartClassificationByClazCode(targetParentClassCode);
	
	target.setCatalogueCode(source.getCatalogueCode());
	target.setClassCode(source.getClassCode());
	target.setClassEnName(source.getClassEnName());
	target.setClassKrName(source.getClassKrName());
	target.setClassPartType(source.getClassPartType());
	target.setClassZhName(source.getClassZhName());
	target.setDivisionFlag(source.getDivisionFlag());
	target.setEpmCode(source.getEpmCode());
	target.setErpClassNo(source.getErpClassNo());
	target.setErpProdCode(source.getErpProdCode());
	target.setErpProdGroupCode(source.getErpProdGroupCode());
	target.setIsEcoCar(source.getIsEcoCar());
	target.setNumberingCharics(source.getNumberingCharics());
	target.setNumberingCode(source.getNumberingCode());
	target.setNumberingMinNo(source.getNumberingMinNo());
	target.setPartClassificType(source.getPartClassificType());
	target.setSortNo(source.getSortNo());
	target.setUseClaz(source.isUseClaz());
	
	target.setParent(parent);

	target = (KETPartClassification) PersistenceHelper.manager.save(target);
	this.attrCopy(source, target);

	return target;
    }
    
    public KETPartClassification clazCopy(KETPartClassification source, KETPartClassification parent) throws Exception {//레벨2 복사
	KETPartClassification target = KETPartClassification.newKETPartClassification();
	
	target.setCatalogueCode(source.getCatalogueCode());
	target.setClassCode(source.getClassCode());
	target.setClassEnName(source.getClassEnName());
	target.setClassKrName(source.getClassKrName());
	target.setClassPartType(source.getClassPartType());
	target.setClassZhName(source.getClassZhName());
	target.setDivisionFlag(source.getDivisionFlag());
	target.setEpmCode(source.getEpmCode());
	target.setErpClassNo(source.getErpClassNo());
	target.setErpProdCode(source.getErpProdCode());
	target.setErpProdGroupCode(source.getErpProdGroupCode());
	target.setIsEcoCar(source.getIsEcoCar());
	target.setNumberingCharics(source.getNumberingCharics());
	target.setNumberingCode(source.getNumberingCode());
	target.setNumberingMinNo(source.getNumberingMinNo());
	target.setPartClassificType(source.getPartClassificType());
	target.setSortNo(source.getSortNo());
	target.setUseClaz(source.isUseClaz());
	
	target.setParent(parent);

	target = (KETPartClassification) PersistenceHelper.manager.save(target);
	this.attrCopy(source, target);

	return target;
    }

    public void executeMigration(String sourceClassCode, String targetParentClassCode) throws WTException {

	SessionContext sessioncontext = SessionContext.newContext();

	Transaction trx = null;
	try {

	    trx = new Transaction();
	    trx.start();

	    Kogger.debug(getClass(), "**************** DRAWING  Excel Extract Start **************************");

	    SessionHelper.manager.setAdministrator();

	    KETPartClassification sourceClazParent = null;

	    KETPartClassification targetClazParent = null;
	    KETPartClassification sourceClaz = null;

	    List<PartClassificationDTO> fullRecursiveList = PartClazHelper.service.searchFullList(false);
	    String sourceOid = "";

	    for (PartClassificationDTO dto : fullRecursiveList) {

		if (StringUtils.isNotEmpty(dto.getClassCode()) && dto.getClassCode().equals(sourceClassCode)) {
		    sourceOid = dto.getOid();
		    sourceClazParent = (KETPartClassification) CommonUtil.getObject(sourceOid);//복사할 원본을 찾는다
		    targetClazParent = this.clazCopy(sourceClazParent, targetParentClassCode);

		    break;
		}

	    }

	    for (PartClassificationDTO dto : fullRecursiveList) {
		if (StringUtils.isNotEmpty(dto.getParentOid()) && dto.getParentOid().equals(sourceOid)) {
		    sourceClaz = (KETPartClassification) CommonUtil.getObject(dto.getOid());
		    this.clazCopy(sourceClaz, targetClazParent);

		}
	    }

	    Kogger.debug(getClass(), "**************** DRAWING  Excel Extract End **************************");

	    trx.commit();

	} catch (Exception e) {
	    e.printStackTrace();
	    trx.rollback();
	} finally {
	    SessionContext.setContext(sessioncontext);
	}
    }

}
