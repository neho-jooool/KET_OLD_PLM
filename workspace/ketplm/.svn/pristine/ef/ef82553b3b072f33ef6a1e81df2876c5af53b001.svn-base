package ext.ket.part.migration.base;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

import wt.fc.PersistenceHelper;
import wt.fc.ReferenceFactory;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.pom.Transaction;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;
import ext.ket.part.entity.KETPartAttribute;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.shared.log.Kogger;

public class MigAddPartAttribute implements RemoteAccess, Serializable {

    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    public static MigAddPartAttribute manager = new MigAddPartAttribute();

    public MigAddPartAttribute() {

    }

    // windchill ext.ket.part.migration.base.MigAddPartAttribute H793280A-3 H793280-3
    public static void main(String[] args) {

	try {

	    String oldPartNo = null;
	    String newPartNo = null;

	    if (args == null || args.length < 2)
		throw new Exception("@@ args need !");
	    else{
		oldPartNo = args[0];
		newPartNo = args[1];
	    }
	    
	    Kogger.debug(MigAddPartAttribute.class, "@start");
	    MigAddPartAttribute.manager.saveFromExcel(oldPartNo, newPartNo);
	    Kogger.debug(MigAddPartAttribute.class, "@end");

	} catch (Exception e) {
	    Kogger.debug(MigAddPartAttribute.class, e);
	}
    }

    public void saveFromExcel(String oldPartNo, String newPartNo) throws Exception {

	if (!SERVER) {
	    try {

		Class aclass[] = {String.class, String.class};
		Object aobj[] = {oldPartNo, newPartNo};

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
	    executeMigration(oldPartNo, newPartNo);
	}
    }

    public void executeMigration(String oldPartNo, String newPartNo) throws WTException {

	SessionContext sessioncontext = SessionContext.newContext();

	ReferenceFactory rf = new ReferenceFactory();
	Transaction trx = null;
	try {

	    Kogger.debug(getClass(), "**************** DRAWING  Excel Extract Start **************************");

	    SessionHelper.manager.setAdministrator();

	    trx = new Transaction();
	    trx.start();
	    
	    KETPartAttribute partAttr = KETPartAttribute.newKETPartAttribute();

	    partAttr.setAttrCode(PartSpecEnum.SpThickWH.getAttrCode());
	    partAttr.setAttrInputType(PartSpecEnum.SpThickWH.getAttrInputType());
	    partAttr.setAttrCodeType(PartSpecEnum.SpThickWH.getAttrCodeType());
	    partAttr.setAttrMultiSelect(PartSpecEnum.SpThickWH.getAttrMultiSelect());
	    partAttr.setAttrName(PartSpecEnum.SpThickWH.getAttrName());
	    partAttr.setAttrOotbName(PartSpecEnum.SpThickWH.getAttrCode());
	    partAttr.setAttrDesc("");
	    partAttr.setColumnName(PartSpecEnum.SpThickWH.getColumnName());
	    partAttr.setErpDesc(PartSpecEnum.SpThickWH.getErpDesc());
	    partAttr.setReceiveErp(PartSpecEnum.SpThickWH.getSendReceiveErp());
	    partAttr.setSendErp(PartSpecEnum.SpThickWH.getSendReceiveErp());
	    partAttr.setUseNumbering(PartSpecEnum.SpThickWH.getUseNumbering());

	    PersistenceHelper.manager.save(partAttr);
	    
	    trx.commit();

	    Kogger.debug(getClass(), "**************** DRAWING  Excel Extract End **************************");


	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    trx.rollback();
	} finally {
	    SessionContext.setContext(sessioncontext);
	}
    }

}
