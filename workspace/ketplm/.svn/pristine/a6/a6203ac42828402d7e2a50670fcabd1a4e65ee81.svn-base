package ext.ket.dms.util;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import e3ps.common.query.SearchUtil;
import e3ps.common.util.DateUtil;
import ext.ket.dms.entity.KETSGDocument;
import ext.ket.dms.entity.KETSGDocumentDTO;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.method.MethodContext;
import wt.pom.DBProperties;
import wt.pom.WTConnection;
import wt.query.QuerySpec;
import wt.query.SearchCondition;

/*********************************************************
 * @description 
 * @author dhkim
 * @date 2018. 10. 4. 오전 9:44:30
 * @Pakage ext.ket.dms.util
 * @class KETDocumentUtil
 *********************************************************/
public class KETDocumentUtil {
    
    public static final KETDocumentUtil manager = new KETDocumentUtil();
    
    public static Map<String, String> moduleMap = new HashMap<String, String>();
    
    static {
        moduleMap.put("MODULE001",  "Workspace");
        moduleMap.put("MODULE002",  "Project");
        moduleMap.put("MODULE003",  "금형/구매품");
        moduleMap.put("MODULE004",  "Document");
        moduleMap.put("MODULE005",  "Drawing");
        moduleMap.put("MODULE006",  "Part");
        moduleMap.put("MODULE007",  "ECM");
        moduleMap.put("MODULE008",  "EWS");
        moduleMap.put("MODULE009",  "HelpDesk");
        moduleMap.put("MODULE0010", "관리메뉴");
        moduleMap.put("MODULE0011", "DashBoard");
        moduleMap.put("MODULE0012", "원가관리");
    }
    
    /**
     * <pre>
     * @description 시스템 가이드 문서번호 채번
     * @author dhkim
     * @date 2018. 10. 4. 오전 9:44:32
     * @method getGenrateSGDocNo
     * @return String
     * @throws Exception 
     * </pre>
     */
    public String getGenrateSGDocNo() throws Exception {
        
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumIntegerDigits(3);

        String year = DateUtil.getThisYear();
        year = year.substring(year.length() - 2);
        
        String newNo = "SG-" + year + "-";
        Statement stat = null;
        ResultSet rs = null;
        MethodContext mContext = MethodContext.getContext();
        WTConnection conn = null;

        try {
            conn = (WTConnection) mContext.getConnection();
            StringBuffer sql = new StringBuffer();
            
            sql.append("SELECT MAX(TO_NUMBER(SUBSTR(A0.DOCNO,-3), '999')) + 1 AS NEWNO FROM KETSGDOCUMENT A0 WHERE SUBSTR(A0.DOCNO,4,2)='" + year + "'");
            stat = conn.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            rs = stat.executeQuery(sql.toString());

            if (rs.next()) {
                int no = rs.getInt("NEWNO");
                if(no == 0) no = 1;
                newNo += nf.format(no);
            }

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stat != null) {
                stat.close();
            }
            if (DBProperties.FREE_CONNECTION_IMMEDIATE && !conn.isTransactionActive()) {
                MethodContext.getContext().freeConnection();
            }
        }

        return newNo;
    }
    
    /**
     * <pre>
     * @description 시스템 가이드 전체 버전 조회
     * @author dhkim
     * @date 2018. 10. 4. 오전 10:45:38
     * @method getBranchSGDocumnent
     * @param sgDoc
     * @return List<KETSGDocumentDTO>
     * @throws Exception
     * </pre>
     */
    @SuppressWarnings("deprecation")
    public List<KETSGDocumentDTO> getBranchSGDocumnent(KETSGDocument sgDoc) throws Exception {
        
        List<KETSGDocumentDTO> list = new ArrayList<KETSGDocumentDTO>();
        
        QuerySpec qs = new QuerySpec(KETSGDocument.class);
        qs.setAdvancedQueryEnabled(true);
        
        if (qs.getConditionCount() > 0) qs.appendAnd();
        qs.appendWhere(new SearchCondition(KETSGDocument.class, KETSGDocument.BRANCH_ID, SearchCondition.EQUAL, sgDoc.getBranchId()), new int[] { 0 });
        
        SearchUtil.setOrderBy(qs, KETSGDocument.class, 0, KETSGDocument.VERSION, false);
        
        QueryResult qr = PersistenceHelper.manager.find(qs);
        
        while(qr.hasMoreElements()) {
            
            KETSGDocument doc = (KETSGDocument) qr.nextElement();
            list.add(new KETSGDocumentDTO(doc));
            
        }
        
        return list;
    }
    
    /**
     * <pre>
     * @description 최신버전 시스템 가이드 조회
     * @author dhkim
     * @date 2018. 10. 4. 오전 11:38:24
     * @method getLastestSGDocumnent
     * @param branchId
     * @return KETSGDocumentDTO
     * @throws Exception 
     * </pre>
     */
    @SuppressWarnings("deprecation")
    public KETSGDocumentDTO getLastestSGDocumnent(long branchId) throws Exception {
        
        QuerySpec qs = new QuerySpec(KETSGDocument.class);
        qs.setAdvancedQueryEnabled(true);
        
        if (qs.getConditionCount() > 0) qs.appendAnd();
        qs.appendWhere(new SearchCondition(KETSGDocument.class, KETSGDocument.BRANCH_ID, SearchCondition.EQUAL, branchId), new int[] { 0 });
        
        if (qs.getConditionCount() > 0) qs.appendAnd();
        qs.appendWhere(new SearchCondition(KETSGDocument.class, KETSGDocument.LASTEST, SearchCondition.IS_TRUE, true), new int[] { 0 });
        
        QueryResult qr = PersistenceHelper.manager.find(qs);
        
        if(qr.hasMoreElements()) {
            KETSGDocument doc = (KETSGDocument) qr.nextElement();
            return new KETSGDocumentDTO(doc);
        }
        
        return null;
    }
    
    /**
     * <pre>
     * @description 최신버전 시스템 가이드 조회
     * @author dhkim
     * @date 2018. 10. 10. 오후 12:02:40
     * @method getLastestSGDocumnent
     * @param branchId
     * @return KETSGDocument
     * @throws Exception
     * </pre>
     */
    @SuppressWarnings("deprecation")
    public KETSGDocument getLastestSGDocumnent(String docNo) throws Exception {
        
        QuerySpec qs = new QuerySpec(KETSGDocument.class);
        qs.setAdvancedQueryEnabled(true);
        
        if (qs.getConditionCount() > 0) qs.appendAnd();
        qs.appendWhere(new SearchCondition(KETSGDocument.class, KETSGDocument.DOC_NO, SearchCondition.EQUAL, docNo), new int[] { 0 });
        
        if (qs.getConditionCount() > 0) qs.appendAnd();
        qs.appendWhere(new SearchCondition(KETSGDocument.class, KETSGDocument.LASTEST, SearchCondition.IS_TRUE, true), new int[] { 0 });
        
        QueryResult qr = PersistenceHelper.manager.find(qs);
        
        if(qr.hasMoreElements()) {
            KETSGDocument doc = (KETSGDocument) qr.nextElement();
            return doc;
        }
        
        return null;
    }
}
