/* 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : DocuCateServlet.java
 * 작성자 : 김경종
 * 작성일자 : 2010. 9
 */
package e3ps.dms.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;
import e3ps.common.code.NumberCodeDao;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.web.CommonServlet;
import e3ps.common.web.WebUtil;
import e3ps.dms.beans.DMSUtil;
import e3ps.dms.entity.KETDocumentCategory;
import e3ps.dms.entity.KETDocumentCategoryLink;
import e3ps.dms.entity.KETTechnicalCategoryLink;
import e3ps.dms.service.KETDmsHelper;
import ext.ket.shared.log.Kogger;
/* 클래스명 : DocuCateServlet
 * 설명 : 분류체계 생성수정삭제
 * 작성자 : 김경종
 * 작성일자 : 2010. 9
 */
public class DocuCateServlet extends CommonServlet
{
    protected void doService(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException
    {
        String cmd = req.getParameter("cmd");
        String oid = req.getParameter("oid");

        String msg = null;
        String param="";
        if (cmd.equals("create")){
            msg = create(req,res);

            if (msg.substring(0,1).equals("f")){
                msg=msg.substring(1);
                param="?"+msg.substring(msg.indexOf("oid"));
                msg = msg.substring(0,msg.indexOf(".")+1);
                alertNgo(res, msg, "/plm/jsp/dms/CreateDocuCate.jsp"+param);
            }else{
                param="?"+msg.substring(msg.indexOf("oid"));
                msg = msg.substring(0,msg.indexOf(".")+1);
                alertNgo(res, msg, "/plm/jsp/dms/ViewDocuCate.jsp"+param);
            }
        }
        else if (cmd.equals("update"))
        {
            msg = modify(req,res);
            if (msg.substring(0,1).equals("f")){
                msg=msg.substring(1);
                param= "?oid="+oid;
                msg = msg.substring(0,msg.indexOf(".")+1);
                alertNgo(res, msg, "/plm/jsp/dms/UpdateDocuCate.jsp"+param);
            }else{
                param= "?oid="+oid;
                alertNgo(res, msg, "/plm/jsp/dms/ViewDocuCate.jsp"+param);
            }
        }
        else if (cmd.equals("delete"))
        {
            msg = delete(req, res);
            if (msg=="T"){
                msg="삭제되었습니다.";
                String poid = req.getParameter("poid");
                param= "?oid="+poid;
                alertNgo(res, msg, "/plm/jsp/dms/ViewDocuCate.jsp"+param);
            }else{
                oid = req.getParameter("oid");
                param= "?oid="+oid;
                alertNgo(res, msg, "/plm/jsp/dms/ViewDocuCate.jsp"+param);
            }
        }

    }
    /* 함수명 : create
     * 설명 : 분류체계생성
     * 작성자 : 김경종
     * 작성일자 : 2010. 9
     */
    private String create(HttpServletRequest req, HttpServletResponse res)
    {
        KETDocumentCategory docuCate = null;
        String returnTemp = null;
        String categoryCode = null;
        String categoryName = null;
        String parentCategoryCode = null;
        String categoryLevel = null;
        String docTypeCode = null;
        String docCatePath = null;
        Integer categoryLvl = 0;
        Hashtable tempHash = WebUtil.getHttpParams(req);
        try{
            parentCategoryCode = ((String)tempHash.get("oid")).trim();
            docTypeCode = ((String)tempHash.get("docTypeCode")).trim();
            docCatePath = ((String)tempHash.get("docCatePath")).trim();
            docTypeCode = docTypeCode.toUpperCase();
            if(parentCategoryCode.equals("ROOT")){
                QuerySpec q1 = new QuerySpec(KETDocumentCategory.class);
                 q1.appendWhere(new SearchCondition(KETDocumentCategory.class, "docTypeCode", "=", docTypeCode), new int[] { 0 });
                 QueryResult qr1 = PersistenceHelper.manager.find(q1);

                 if(qr1.hasMoreElements()){
                     docuCate = (KETDocumentCategory) qr1.nextElement();
                     categoryCode = docuCate.getCategoryCode();
                     returnTemp = "f"+docTypeCode + "는 기 존재하는 분류코드입니다.oid=" + parentCategoryCode + "&path=" + docCatePath;
                     return returnTemp;
                 }
            }

             //기존재분류명  체크
            categoryName = ((String)tempHash.get("categoryName")).trim();

            if(parentCategoryCode.equals("ROOT")){
                categoryLevel="0";
            }else{
                categoryLevel=parentCategoryCode.substring(2, 3);
            }

            categoryLvl=Integer.parseInt(categoryLevel)+1;
            QuerySpec q = new QuerySpec(KETDocumentCategory.class);
             q.appendWhere(new SearchCondition(KETDocumentCategory.class, "categoryName", "=", categoryName), new int[] { 0 });
             q.appendAnd();
             q.appendWhere(new SearchCondition(KETDocumentCategory.class, "parentCategoryCode", "=", parentCategoryCode), new int[] { 0 });
            QueryResult qr = PersistenceHelper.manager.find(q);
            if(qr.hasMoreElements()){
                 docuCate = (KETDocumentCategory) qr.nextElement();
                 categoryCode = docuCate.getCategoryCode();
                 returnTemp = "f"+categoryName + "는 기 존재하는 분류명입니다.oid=" + parentCategoryCode + "&path=" + docCatePath;
                 return returnTemp;
             }
             //분류체계입력 Helper.service호출하여 화면정보를 넘긴다.
             docuCate = KETDmsHelper.service.insertDocCategory(tempHash);
             if (docuCate != null){
                //categoryPath = KETDmsHelper.service.selectCategoryPath(categoryCode);
                categoryCode = docuCate.getCategoryCode();

                try {
                    // [Start] NumberCodeValue //
                    Map<String, Object> parameter = new HashMap<String, Object>();
                    parameter.put("codeType", "LANGUAGE");
                    List<Map<String, Object>> list = NumberCodeHelper.manager.getNumberCodeList(parameter);

                    Connection conn = null;
                    try {
                        conn = PlmDBUtil.getConnection();
                        NumberCodeDao dao = new NumberCodeDao(conn);

                        Map<String, Object> param = null;
                        for ( Map<String, Object> item : list ) {

                            param = new HashMap<String, Object>();

                            if ( !item.get("code").equals("ko") ) {

                                param.put("codeType", "DOCUMENTCATEGORY");
                                param.put("code",     categoryCode);
                                param.put("lang",     item.get("code"));
                                param.put("value",    tempHash.get("categoryName_"+item.get("code")));
                                param.put("desc",     "");
                                dao.createNumberCodeValue(param);
                            }
                            else {
                                param.put("codeType", "DOCUMENTCATEGORY");
                                param.put("code",     categoryCode);
                                param.put("lang",     "ko");
                                param.put("value",    tempHash.get("categoryName"));
                                param.put("desc",     "");
                                dao.createNumberCodeValue(param);
                            }
                        }
                    }
                    finally {
                        PlmDBUtil.close(conn);
                    }
                    // [End] NumberCodeValue //
                }
                catch (Exception e) {
                    Kogger.error(getClass(), e);
                }

                returnTemp = "분류체계가 생성되었습니다.oid="+categoryCode;
            }else{
                returnTemp = "f분류체계생성에 실패하였습니다.oid=" + parentCategoryCode + "&path=" + docCatePath;
            }
        }
        catch (WTException e)
        {
            Kogger.error(getClass(), e);
            returnTemp = "f분류체계생성에 실패하였습니다.oid=" + parentCategoryCode + "&path=" + docCatePath;
        }
        return returnTemp;
    }

    /* 함수명 : modify
     * 설명 : 분류체계수정
     * 작성자 : 김경종
     * 작성일자 : 2010. 9
     */
    private String modify(HttpServletRequest req, HttpServletResponse res)
    {
        KETDocumentCategory docuCate = null;
        String returnTemp = null;
        String categoryCode = null;
        String categoryName = null;
        String categoryLevel = null;
        String parentCategoryCode = null;
        Hashtable tempHash = WebUtil.getHttpParams(req);
        try{
            //Kogger.debug(getClass(), "hash ===>" + tempHash);
            categoryName = ((String)tempHash.get("categoryName")).trim();
            categoryCode = ((String)tempHash.get("oid")).trim();
            categoryLevel= ((String)tempHash.get("lvl")).trim();
            parentCategoryCode= ((String)tempHash.get("parentCategoryCode")).trim();

            //기존재분류명  체크
            QuerySpec q = new QuerySpec(KETDocumentCategory.class);
             q.appendWhere(new SearchCondition(KETDocumentCategory.class, "categoryName", "=", categoryName), new int[] { 0 });
             q.appendAnd();
             q.appendWhere(new SearchCondition(KETDocumentCategory.class, "categoryCode", "<>", categoryCode), new int[] { 0 });
             q.appendAnd();
            //q.appendWhere(new SearchCondition(KETDocumentCategory.class, "categoryLevel", "=", Integer.parseInt(categoryLevel)), new int[] { 0 });
            //Integer.parseInt(categoryLevel))
             q.appendWhere(new SearchCondition(KETDocumentCategory.class, "parentCategoryCode", "=", parentCategoryCode), new int[] { 0 });
            Kogger.debug(getClass(), "Query==========>"+q.toString());

             QueryResult qr1 = PersistenceHelper.manager.find(q);
             if(qr1.hasMoreElements()){
                 docuCate = (KETDocumentCategory) qr1.nextElement();
                 categoryCode = docuCate.getCategoryCode();
                 returnTemp = "f"+categoryName+ "는 기 존재하는 분류명입니다.oid="+categoryCode;
                 return returnTemp;
             }

             //분류체계수정 Helper.service호출하여 화면정보를 넘긴다.
             Kogger.debug(getClass(), "hash ===>");
            docuCate = KETDmsHelper.service.updateDocCategory(tempHash);

            if (docuCate != null){
                categoryCode = docuCate.getCategoryCode();

                try {
                    // [Start] NumberCodeValue //
                    Map<String, Object> parameter = new HashMap<String, Object>();
                    parameter.put("codeType", "LANGUAGE");
                    List<Map<String, Object>> list = NumberCodeHelper.manager.getNumberCodeList(parameter);

                    Connection conn = null;
                    try {
                        conn = PlmDBUtil.getConnection();
                        NumberCodeDao dao = new NumberCodeDao(conn);

                        Map<String, Object> param = null;
                        for ( Map<String, Object> item : list ) {

                            param = new HashMap<String, Object>();

                            if ( !item.get("code").equals("ko") ) {

                                param.put("codeType", "DOCUMENTCATEGORY");
                                param.put("code",     categoryCode);
                                param.put("lang",     item.get("code"));
                                param.put("value",    tempHash.get("categoryName_"+item.get("code")));
                                param.put("desc",     "");
                                dao.createNumberCodeValue(param);
                            }
                            else {
                                param.put("codeType", "DOCUMENTCATEGORY");
                                param.put("code",     categoryCode);
                                param.put("lang",     "ko");
                                param.put("value",    tempHash.get("categoryName"));
                                param.put("desc",     "");
                                dao.createNumberCodeValue(param);
                            }
                        }
                    }
                    finally {
                        PlmDBUtil.close(conn);
                    }
                    // [End] NumberCodeValue //
                }
                catch (Exception e) {
                    Kogger.error(getClass(), e);
                }

                returnTemp = "분류체계가 수정되었습니다.";
            }else{
                returnTemp = "f분류체계수정에 실패하였습니다.oid="+categoryCode;
            }
        }
        catch (WTException e)
        {
            Kogger.error(getClass(), e);
            returnTemp = "f분류체계수정에 실패하였습니다.oid="+categoryCode;
        }
        return returnTemp;
    }

    /* 함수명 : delete
     * 설명 : 분류체계삭제
     * 작성자 : 김경종
     * 작성일자 : 2010. 9
     */
    private String delete(HttpServletRequest req, HttpServletResponse res)
    {

        KETDocumentCategory docuCate = null;
        String returnTemp = null;
        String categoryCode = req.getParameter("oid");

        try{
            //Kogger.debug(getClass(), "del3 categoryCode ===>" + categoryCode);

            QuerySpec q = new QuerySpec(KETDocumentCategory.class);
            q.appendWhere(new SearchCondition(KETDocumentCategory.class, "parentCategoryCode", "=", categoryCode), new int[] { 0 });

            QueryResult qr = PersistenceHelper.manager.find(q);
            if(qr.hasMoreElements()){
                returnTemp = "해당 분류체계에 하위가 존재합니다. 삭제 하실 수 없습니다.";
                return returnTemp;
            }

            q = new QuerySpec(KETDocumentCategory.class);
            q.appendWhere(new SearchCondition(KETDocumentCategory.class, "categoryCode", "=", categoryCode), new int[] { 0 });

            //삭제전 하위등록문서 존재여부 체크
            qr = PersistenceHelper.manager.find(q);
            if(qr.hasMoreElements()){
                docuCate = (KETDocumentCategory) qr.nextElement();

                QueryResult r = PersistenceHelper.manager.navigate(docuCate, "document", KETDocumentCategoryLink.class);
                if (r.hasMoreElements()){
                    returnTemp = "해당 분류체계에 문서가 존재합니다. 삭제 하실 수 없습니다.";
                    return returnTemp;
                }

                r = PersistenceHelper.manager.navigate(docuCate, "technical", KETTechnicalCategoryLink.class);
                if (r.hasMoreElements()){
                    returnTemp = "해당 분류체계에 문서가 존재합니다. 삭제 하실 수 없습니다.";
                    return returnTemp;
                }

                int cateUseCnt = DMSUtil.useDocCategoryCnt(categoryCode);
                if(cateUseCnt>0){
                    returnTemp = "해당 분류체계에 Task가 존재합니다. 삭제 하실 수 없습니다.";
                    return returnTemp;
                }
                //useDocCategoryCnt
            }
            //분류체계삭제 Helper.service호출하여 화면정보를 넘긴다.
            returnTemp = KETDmsHelper.service.deleteDocCategory(categoryCode);

            if (returnTemp != null){
                returnTemp = "T";
            }else{
                returnTemp = "삭제에 실패하였습니다.";
            }
        }
        catch (WTException e)
        {
            Kogger.error(getClass(), e);
            returnTemp = "삭제에 실패하였습니다.";
        }
        return returnTemp;
    }
}
