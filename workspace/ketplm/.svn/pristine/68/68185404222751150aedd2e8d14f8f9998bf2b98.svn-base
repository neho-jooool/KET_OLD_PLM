package ext.ket.bom.query;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import wt.part.WTPart;
import e3ps.common.dao.DaoFactory;
import e3ps.common.dao.DaoManager;
import ext.ket.edm.cad2bom.service.Cad2BomHelper;
import ext.ket.shared.log.Kogger;

public class KETCad2BomQueryBean {

    public KETCad2BomQueryBean() {

    }

    public List<Map<String, Object>> getCad2BomViewData(Hashtable params) throws Exception {

	String modelOid = (String) params.get("modelOid");
	List<Map<String, Object>> trreList = Cad2BomHelper.service.makeCadStructure(modelOid);

	return trreList;

    }

    public boolean existBOMHead(WTPart part) throws Exception {

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

	try {

	    List aBind = new ArrayList();
	    String partNumber = part.getNumber();
	    String partRev = wt.vc.VersionControlHelper.getVersionIdentifier(part).getValue();

	    StringBuffer sql = new StringBuffer();
	    sql.append("   SELECT IDA2A2 FROM KETBOMHEADER WHERE STATESTATE = 'INWORK' AND NEWBOMCODE = ? AND BOMVERSION = ? ");

	    aBind.add(partNumber);
	    aBind.add(partRev);

	    String sSql = sql.toString();
	    int count = oDaoManager.queryForCount(sSql, aBind);

	    return count != 0;

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}
    }

    public boolean hasBOMComp(WTPart part) throws Exception {

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

	try {

	    String partNumber = part.getNumber();
	    List aBind = new ArrayList();
	    aBind.add(partNumber);

	    StringBuffer sql = new StringBuffer();

	    sql.append("   SELECT * FROM KETBOMCOMPONENT WHERE NEWBOMCODE = ? ");

	    String sSql = sql.toString();
	    int count = oDaoManager.queryForCount(sSql, aBind);

	    return count != 0;

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}
    }

    public boolean isExistBOMComp(WTPart parentPart, WTPart childPart) throws Exception {

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

	try {

	    StringBuffer sql = new StringBuffer();
	    sql.append(" SELECT IDA2A2 FROM  (            						        \n");
	    sql.append(" SELECT BH.STATESTATE, BH.ECOHEADERNUMBER, BH.BOMVERSION AS PRODVER,    		\n");
	    sql.append("        BH.BOXQUANTITY, BC.PARENTITEMCODE, BC.CHILDITEMCODE, BC.BOMVERSION, 		\n");
	    sql.append("        BC.NEWFLAG, BC.SECONDREMARK, BH.IDA2A2 						\n");
	    sql.append("   FROM KETBOMHEADER BH, KETBOMCOMPONENT BC 						\n");
	    sql.append("  WHERE BH.NEWBOMCODE=BC.NEWBOMCODE AND BC.NEWFLAG='NEW' AND BH.STATESTATE='INWORK' 	\n");
	    sql.append("    AND BC.PARENTITEMCODE = ? AND BC.CHILDITEMCODE = ? AND BC.BOMVERSION = ? 		\n");
	    sql.append(" )											\n");

	    String parentNumber = parentPart.getNumber();
	    String childNumber = childPart.getNumber();
	    String childRev = wt.vc.VersionControlHelper.getVersionIdentifier(childPart).getValue();

	    List aBind = new ArrayList();
	    aBind.add(parentNumber);
	    aBind.add(childNumber);
	    aBind.add(childRev);

	    String sSql = sql.toString();
	    int count = oDaoManager.queryForCount(sSql, aBind);

	    return count != 0;

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}
    }

    /**
     * 0리비전 INWORK 부품 삭제시에 해당 부품이 BOM에 사용중인지 체크함. 
     * 
     * @param partNumber
     * @return
     * @throws Exception
     * @메소드명 : isUsedBOMComp
     * @작성자 : yjlee
     * @작성일 : 2014. 10. 7.
     * @설명 : 
     * @수정이력 - 수정일,수정자,수정내용  					   
     *
     */
    public boolean isUsedBOMComp(String partNumber) throws Exception {

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

	try {

	    StringBuffer sql = new StringBuffer();
	    sql.append(" SELECT IDA2A2 FROM  (            						        \n");
	    sql.append(" SELECT BH.STATESTATE, BH.ECOHEADERNUMBER, BH.BOMVERSION AS PRODVER,    		\n");
	    sql.append("        BH.BOXQUANTITY, BC.PARENTITEMCODE, BC.CHILDITEMCODE, BC.BOMVERSION, 		\n");
	    sql.append("        BC.NEWFLAG, BC.SECONDREMARK, BH.IDA2A2 						\n");
	    sql.append("   FROM KETBOMHEADER BH, KETBOMCOMPONENT BC 						\n");
	    sql.append("  WHERE BH.NEWBOMCODE=BC.NEWBOMCODE AND BC.NEWFLAG='NEW' AND BH.STATESTATE='INWORK' 	\n");
	    sql.append("    AND BC.CHILDITEMCODE = ? 								\n");
	    sql.append(" )											\n");

	    List aBind = new ArrayList();
	    aBind.add(partNumber);

	    String sSql = sql.toString();
	    int count = oDaoManager.queryForCount(sSql, aBind);

	    return count != 0;

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}
    }

}
