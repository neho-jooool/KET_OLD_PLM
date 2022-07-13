package e3ps.dms.service;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wt.method.RemoteMethodServer;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import e3ps.common.dao.DaoFactory;
import e3ps.common.dao.DaoManager;
import e3ps.common.dao.RowSetStrategy;
import e3ps.dms.service.internal.TechdocUpload;
import ext.ket.shared.log.Kogger;

public class TechDesignDocumentReupload implements wt.method.RemoteAccess, java.io.Serializable {

	static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;

	public static void executeUpload() throws Exception {
		SessionContext sessioncontext = SessionContext.newContext();

		List<Map<String, Object>> list = searchTechDocumentList();
		for (Map<String, Object> techDoc : list) {
			if ("Y".equals(techDoc.get("ISDESIGN"))) {
				HashMap<String, String> paramMap = new HashMap<String, String>();
				try {
					String ip = "192.168.1.110";
					paramMap.put("oid", (String) techDoc.get("oid"));
					paramMap.put("userOid", "10");
					paramMap.put("ip", ip);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					SessionHelper.manager.setAdministrator();
					TechdocUpload techUp = new TechdocUpload();
					techUp.disignFileUpload(paramMap);
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				} finally {
					SessionContext.setContext(sessioncontext);
				}
			}
		}

	}

	public void saveFile() throws Exception {
		if (!SERVER) {
			try {

				Class aclass[] = {};
				Object aobj[] = {};

				Kogger.debug(getClass(), "@		start");
				RemoteMethodServer.getDefault().invoke("saveFile", null, this, aclass, aobj);
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
			executeUpload();
		}
	}

	/**
	 * 함수명 : searchTechDocumentList 설명 : 기술문서 검색
	 * 
	 * @param conditionList
	 * @return List<Map<String, Object>> 검색된 기술문서List
	 * @throws Exception
	 */
	public static List<Map<String, Object>> searchTechDocumentList() throws Exception {

		StringBuffer sb = null;
		DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
		List<Map<String, Object>> techDocumentList = null;

		try {

			sb = new StringBuffer();
			sb.append(" SELECT A.OID                                 AS OID          \n");
			sb.append("       ,G.WTDOCUMENTNUMBER                    AS DOCUMENTNO   \n");
			sb.append("       ,A.TITLE                               AS TITLE        \n");
			sb.append("       ,NCV.VALUE                             AS CATEGORYNAME \n");
			sb.append("       ,A.STATESTATE                          AS STATESTATE   \n");
			sb.append("       ,E.NAME                                AS STATE        \n");
			sb.append("       ,A.VERSIONIDA2VERSIONINFO              AS VER          \n");
			sb.append("       ,F.NAME                                AS CREATORNAME  \n");
			sb.append("       ,A.DEPTNAME                            AS DEPTNAME     \n");
			sb.append("       ,A.CREATESTAMPA2                       AS CREATEDATE   \n");
			sb.append("       ,A.HOLDEROID                           AS HOLDEROID    \n");
			sb.append("       ,A.APPDATAOID                          AS APPDATAOID   \n");
			sb.append("       ,A.EXTENSION                           AS EXTENSION    \n");
			sb.append("       ,A.ATTRIBUTE1                          AS ISDESIGN     \n");
			sb.append("       ,A.ATTRIBUTE3                          AS DUTY     \n");
			sb.append("   FROM (                                                     \n");
			sb.append("         SELECT DOC.CLASSNAMEA2A2||':'||DOC.IDA2A2 OID        \n");
			sb.append("               ,DOC.VERSIONIDA2VERSIONINFO                    \n");
			sb.append("               ,DOC.IDA2A2                                    \n");
			sb.append("               ,DOC.IDA3A2STATE                               \n");
			sb.append("               ,DOC.IDA3B2ITERATIONINFO                       \n");
			sb.append("               ,DOC.IDA3MASTERREFERENCE                       \n");
			sb.append("               ,DOC.STATESTATE                                \n");
			sb.append("               ,DOC.LATESTITERATIONINFO                       \n");
			sb.append("               ,DOC.DIVISIONCODE                              \n");
			sb.append("               ,DOC.TITLE                                     \n");
			sb.append("               ,DOC.DEPTNAME                                  \n");
			sb.append("               ,DOC.ATTRIBUTE1                                \n");
			sb.append("               ,DOC.ATTRIBUTE3                                \n");
			sb.append("               ,DOC.ATTRIBUTE2                                \n");
			sb.append("               ,TO_CHAR(DOC.CREATESTAMPA2, 'YYYY-MM-DD')                   AS CREATESTAMPA2      \n");
			sb.append("               ,holder.CLASSNAMEKEYROLEAOBJECTREF || ':' || holder.IDA3A5  AS HOLDEROID          \n");
			sb.append("               ,holder.CLASSNAMEKEYROLEBOBJECTREF || ':' || holder.IDA3B5  AS APPDATAOID         \n");
			sb.append("               ,REVERSE(SUBSTR(REVERSE(app.filename), 0, INSTR(REVERSE(app.filename), '.', 1, 1)-1)) AS EXTENSION   \n");
			sb.append("           FROM KETTechnicalDOCUMENT DOC                      \n");
			sb.append("               ,HOLDERTOCONTENT    holder                     \n");
			sb.append("               ,APPLICATIONDATA    app                        \n");
			sb.append("          WHERE LATESTITERATIONINFO = 1                       \n");
			sb.append("            AND DOC.IDA2A2 = holder.IDA3A5                    \n");
			sb.append("            AND holder.IDA3B5 = app.IDA2A2                    \n");
			sb.append("            AND app.ROLE = 'PRIMARY'                          \n");
			sb.append("            AND DOC.ATTRIBUTE1 = 'Y'                          \n");
			sb.append("        ) A,                                                  \n");
			sb.append("        KETTechnicalCategoryLink B,                           \n");
			sb.append("        KETDOCUMENTCATEGORY      C,                           \n");
			sb.append("        PHASELINK                D,                           \n");
			sb.append("        PHASETEMPLATE            E,                           \n");
			sb.append("        PEOPLE                   F,                           \n");
			sb.append("        WTDocumentMaster         G,                           \n");
			sb.append("        NUMBERCODEVALUE          NCV                          \n");
			sb.append("  WHERE 1=1                                                   \n");
			sb.append("    AND A.IDA2A2              = B.IDA3B5                      \n");
			sb.append("    AND B.IDA3A5              = C.IDA2A2                      \n");
			sb.append("    AND A.IDA3A2STATE         = D.IDA3A5                      \n");
			sb.append("    AND D.IDA3B5              = E.IDA2A2                      \n");
			sb.append("    AND E.PHASESTATE          = A.STATESTATE                  \n");
			sb.append("    AND A.IDA3B2ITERATIONINFO = F.IDA3B4                      \n");
			sb.append("    AND A.IDA3MASTERREFERENCE = G.IDA2A2                      \n");
			sb.append("    AND C.CATEGORYCODE        =  NCV.CODE           	         \n");
			sb.append("    AND NCV.LANG = 'ko'           				 \n");

			techDocumentList = oDaoManager.queryForList(sb.toString(), new RowSetStrategy<Map<String, Object>>() {

				@Override
				public Map<String, Object> mapRow(ResultSet rs) throws SQLException {

					Map<String, Object> techDocument = new HashMap<String, Object>();
					techDocument.put("oid", (String) rs.getString("OID"));
					techDocument.put("documentNo", (String) rs.getString("DOCUMENTNO"));
					techDocument.put("title", (String) rs.getString("TITLE"));
					techDocument.put("categoryName", (String) rs.getString("CATEGORYNAME"));
					techDocument.put("stateState", (String) rs.getString("STATESTATE"));
					techDocument.put("state", (String) rs.getString("STATE"));
					techDocument.put("ver", (String) rs.getString("VER"));
					techDocument.put("creatorName", (String) rs.getString("CREATORNAME"));
					techDocument.put("deptName", (String) rs.getString("DEPTNAME"));
					techDocument.put("createDate", (String) rs.getString("CREATEDATE"));
					techDocument.put("holderOid", (String) rs.getString("HOLDEROID"));
					techDocument.put("appDataOid", (String) rs.getString("APPDATAOID"));
					techDocument.put("extension", (String) rs.getString("EXTENSION"));
					techDocument.put("ISDESIGN", (String) rs.getString("ISDESIGN"));
					techDocument.put("DUTY", (String) rs.getString("DUTY"));

					return techDocument;
				}
			});

		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return techDocumentList == null ? new ArrayList<Map<String, Object>>() : techDocumentList;
	}

	public static void main(String args[]) throws Exception {

		long startTimeMillis = System.currentTimeMillis();
		Kogger.debug(TechDesignDocumentReupload.class, "Load Start...");

		TechDesignDocumentReupload tech = new TechDesignDocumentReupload();
		tech.saveFile();

		Kogger.debug(TechDesignDocumentReupload.class, "Load End...");

		long endTimeMillis = System.currentTimeMillis();
		Kogger.debug(TechDesignDocumentReupload.class, "Load Time : " + (endTimeMillis - startTimeMillis));
		System.exit(0);
	}

}
