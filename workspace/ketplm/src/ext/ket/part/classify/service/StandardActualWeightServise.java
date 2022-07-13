package ext.ket.part.classify.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.part.WTPart;
import wt.services.StandardManager;
import wt.util.WTException;

import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.IRepository;
import com.sap.mw.jco.JCO;
import com.sap.mw.jco.JCO.Client;
import com.sap.mw.jco.JCO.Function;

import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.framework.util.Registry;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import e3ps.ecm.service.KETEcmHelper;
import e3ps.sap.RFCConnect;
import ext.ket.bom.query.KETBOMQueryBean;
import ext.ket.part.entity.KETPartClassLink;
import ext.ket.part.entity.KETPartClassification;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.part.util.PartSpecSetter;
import ext.ket.shared.log.Kogger;

/**
 * 
 * 
 * @클래스명 : StandardActualWeightServise
 * @작성자 : KKW
 * @작성일 : 2014. 9. 26.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
public class StandardActualWeightServise extends StandardManager implements ActualWeightServise {

    private static final long serialVersionUID = 1L;

    Registry                  registry         = Registry.getRegistry("e3ps.bom.bom");

    public static StandardActualWeightServise newStandardActualWeightServise() throws WTException {
	StandardActualWeightServise instance = new StandardActualWeightServise();
	instance.initialize();
	return instance;
    }

    @Override
    public List<HashMap<String, String>> getPartList(String part_oid, Locale locale) throws Exception {
	List<HashMap<String, String>> partHashMaps = new ArrayList<HashMap<String, String>>();
	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer sql = new StringBuffer();

	String partInfo[] = part_oid.split(":");

	// ArrayList revList = new ArrayList();
	try {
	    sql.append(" SELECT *																																								");
	    sql.append(" FROM (SELECT 0 AS NUM,                                                                 ");
	    sql.append("              0 AS LVL,                                                                 ");
	    sql.append("              C0.WTPARTNUMBER AS CHILDITEMCODE,                                         ");
	    sql.append("              D0.VERSIONIDA2VERSIONINFO AS VER                                          ");
	    sql.append("        FROM WTPARTMASTER C0, WTPART D0                                                 ");
	    sql.append("       WHERE     C0.IDA2A2 = D0.IDA3MASTERREFERENCE                                     ");
	    sql.append("             AND D0.LATESTITERATIONINFO = 1                                             ");
	    sql.append("             AND D0.IDA2A2 = " + partInfo[1] + "                                           ");
	    sql.append("      UNION                                                                             ");
	    sql.append("      SELECT *                                                                          ");
	    sql.append("        FROM (           SELECT ROWNUM AS NUM,                                          ");
	    sql.append("                                LEVEL AS LVL,                                           ");
	    sql.append("                                A0.CHILDITEMCODE,                                       ");
	    sql.append("                                A0.BOMVERSION AS VER                                    ");
	    sql.append("                           FROM KETPARTUSAGELINK A0, WTPART B0                          ");
	    sql.append("                          WHERE A0.IDA3A5 = B0.IDA2A2                                   ");
	    sql.append("                     START WITH A0.IDA3A5 = " + partInfo[1] + "                            ");
	    sql.append("                     CONNECT BY PRIOR (SELECT MAX (B.IDA3A5)                            ");
	    sql.append("                                         FROM KETPARTUSAGELINK B                        ");
	    sql.append("                                        WHERE B.PARENTITEMCODE = A0.CHILDITEMCODE) =    ");
	    sql.append("                                   A0.IDA3A5                                            ");
	    sql.append("              ORDER SIBLINGS BY A0.ITEMSEQ))                                            ");
	    sql.append(" ORDER BY NUM                                                                           ");

	    // Kogger.debug(getClass(), "SQL:" + sql.toString());
	    pstmt = conn.prepareStatement(sql.toString());
	    // pstmt.setString(1, partNumber);
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		String QpartNo = rs.getString("CHILDITEMCODE");
		String QpartVer = rs.getString("VER");
		String QpartLvl = rs.getString("LVL");
		if (QpartVer.contains(".")) {
		    String tempArr[] = QpartVer.split("\\.");
		    QpartVer = tempArr[0];
		}

		KETBOMQueryBean ketbomQueryBean = new KETBOMQueryBean();
		String wtPartOid = ketbomQueryBean.getPartOid(QpartNo, QpartVer);

		WTPart wtPart = (WTPart) CommonUtil.getObject(wtPartOid);

		// C PARTCLAZPARTGUBUN 0 구매품
		// P PARTCLAZPARTGUBUN 0 단품
		// A PARTCLAZPARTGUBUN 0 ASSY

		boolean partAddCheck = false;
		// 단품 구매품 assy 부품인지 체크 하여 맞다면 중량 조정할수있도록 추가
		QueryResult qr = PersistenceHelper.manager.navigate(wtPart.getMaster(), "classific", KETPartClassLink.class);
		if (qr.hasMoreElements()) {
		    while (qr.hasMoreElements()) {
			KETPartClassification ketPartClassification = (KETPartClassification) qr.nextElement();
			String partClassificType = StringUtil.checkNull(ketPartClassification.getPartClassificType());
			if (partClassificType.equals("C") || partClassificType.equals("P") || partClassificType.equals("A")) {
			    partAddCheck = true;
			}
		    }
		}

		// 테스트위해 강제로 true 처리함
		// partAddCheck = true;

		if (partAddCheck) {
		    HashMap<String, String> partMap = new HashMap<String, String>();
		    partMap.put("part_oid", CommonUtil.getOIDString(wtPart));
		    partMap.put("part_no", wtPart.getNumber());
		    partMap.put("part_ver", QpartVer);
		    partMap.put("part_lvl", QpartLvl);

		    partMap.put("part_name", wtPart.getName());
		    partMap.put("part_sp_net_weight", PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpNetWeight));
		    partMap.put("part_sp_total_weight", PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpTotalWeight));
		    partMap.put("part_sp_weight_unit", PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpWeightUnit));
		    partMap.put("part_sp_scrab_weight", PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpScrabWeight));

		    partHashMaps.add(partMap);

		}

	    }

	} catch (Exception e) {
	    try {

		conn.rollback();
		Kogger.error(getClass(), e);
	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }
	    throw e;
	} finally {

	    try {
		if (rs != null) {
		    rs.close();
		}

		if (pstmt != null) {
		    pstmt.close();
		}
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
		throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);
		}
	    }

	}
	return partHashMaps;
    }

    @Override
    public boolean actualWeightBomSave(String ecoOid, String[] part_no, String[] part_sp_net_weight, String[] part_sp_total_weight, String[] part_oid, String part_sp_scrab_weight[]) throws Exception {
	// TODO Auto-generated method stub
	boolean result = false;
	Client client = null;
	IRepository repository = null;

	client = RFCConnect.getConnection();
	client.connect();
	repository = JCO.createRepository("BFREPOSITORY", client);
	IFunctionTemplate tmpl = repository.getFunctionTemplate("Z_ST_CHANGE_WEIGHT_INFO");

	Function function = tmpl.getFunction();
	JCO.Table inputTable = function.getTableParameterList().getTable("IT_DATA");

	for (int i = 0; i < part_no.length; i++) {
	    inputTable.appendRow();

	    // MATNR CHAR 18 자재 번호
	    // BRGEW CHAR 20 총중량
	    // NTGEW CHAR 20 순중량
	    // GEWEI UNIT 3 중량 단위

	    inputTable.setValue(part_no[i], "MATNR");
	    inputTable.setValue(part_sp_total_weight[i], "BRGEW");
	    inputTable.setValue(part_sp_net_weight[i], "NTGEW");
	    inputTable.setValue("G", "GEWEI");
	}

	try {
	    client.execute(function);
	} catch (Exception e) {
	    result = false;
	    Kogger.debug(getClass(), "BomEcoInfoInterface [ getGenInfo ] >>> " + e.toString());
	}

	String r = (String) function.getExportParameterList().getValue("E_RETURN");
	int c = function.getExportParameterList().getInt("E_CNT");
	String r_msg = (String) function.getExportParameterList().getValue("E_MSG");

	Kogger.debug(getClass(), "E_RETURN<<<<<< " + r);
	Kogger.debug(getClass(), "E_CNT<<<<<< " + c);
	Kogger.debug(getClass(), "E_MSG<<<<<< " + r_msg);

	if ((r.toUpperCase()).equals("S")) {
	    result = true;
	}
	else {
	    result = false;
	    return result;
	}

	for (int i = 0; i < part_oid.length; i++) {
	    WTPart wtPart = (WTPart) CommonUtil.getObject(part_oid[i]);

	    PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpNetWeight, part_sp_net_weight[i]);
	    PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpTotalWeight, part_sp_total_weight[i]);
	    // PartSpecSetter.setPartSpec(wtPart, PartSpecEnum.SpScrabWeight, part_sp_scrab_weight[i]);

	    PersistenceServerHelper.manager.update(wtPart);
	}

	// History
	KETEcmHelper.service.saveKETEcnWeightHistory(ecoOid, part_no, part_sp_net_weight, part_sp_total_weight, part_oid, part_sp_scrab_weight);

	return result;
    }
}
