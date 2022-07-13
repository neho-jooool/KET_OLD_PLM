package ext.ket.dms.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.org.WTUser;
import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.common.util.AuthUtil;
import e3ps.common.util.CommonUtil;
import e3ps.dms.entity.KETDocumentCategory;
import e3ps.dms.entity.KETDocumentCategoryLink;
import e3ps.dms.entity.KETProjectDocument;
import e3ps.wfm.util.WorkflowSearchHelper;
import ext.ket.dms.service.KETProjectDocumentHelper;

@Controller
@RequestMapping("/dms/*")
public class DocumentSearchController {
    @RequestMapping("/listTotalDocumentPopup")
    public void listTotalDocumentPopup() throws Exception {
    }
    
    @RequestMapping("/getCategoryIsQulity")
    @ResponseBody
    public String getCategoryIsQulity(String CagegoryCode) throws Exception {
	/*
	 * 표준품질문서인지 판단
	 */
	KETDocumentCategory category = null;
	String qulityYn = "";
	try{
	    category = KETProjectDocumentHelper.manager.getDocumentCategory(CagegoryCode);
	    
	    if (StringUtils.isNotEmpty(category.getAttribute2()) && StringUtils.isEmpty(category.getAttribute3())) {
		qulityYn = "Y";
	    } else {
		qulityYn = "N";
	    }
	}catch(Exception e){
	    
	}
	return qulityYn;
    }
    
    @RequestMapping("/getCategoryIsSecurity")
    @ResponseBody
    public String getCategoryIsSecurity(String CagegoryCode) throws Exception {
	/*
	 * 대내비 대상인지 판단
	 */
	KETDocumentCategory category = null;
	String securityYn = "";
	try{
	    category = KETProjectDocumentHelper.manager.getDocumentCategory(CagegoryCode);
	    
	    if (StringUtils.isNotEmpty(category.getAttribute4())) {
		securityYn = "Y";
	    } else {
		securityYn = "N";
	    }
	}catch(Exception e){
	    
	}
	return securityYn;
    }
    
    @RequestMapping("/isgAuthUserDOC")
    @ResponseBody
    public String isgAuthUserDOC(String oid) throws Exception {
	/*
	 * 대내비 대상인지 판단
	 */
	
	WTUser wtuser = (WTUser) SessionHelper.manager.getPrincipal();
	
	KETProjectDocument pdoc = (KETProjectDocument)CommonUtil.getObject(oid);
	
	String securityYn = "N";
	
	if(isDocCategoryS2(pdoc)){
	    securityYn = isDocAuthCheckS2(pdoc,wtuser);
	}else if(AuthUtil.isAuthUserDOC(pdoc,wtuser)){
	    securityYn = "Y";
	}

	return securityYn;
    }
    
    public String isDocAuthCheckS2(KETProjectDocument pdoc, WTUser wtuser) throws Exception{
	String securityYn = "N";
	
        //분류체계에서 대내비설정된 개발산출문서의 경우 관리자/작성자/결재자 로 열람권한을 제한한다
        if(CommonUtil.isMember("Administrators", wtuser) || WorkflowSearchHelper.manager.userInApproval(pdoc, wtuser.getName()) || (wtuser.getName()).equals(pdoc.getCreatorName())){
            securityYn = "Y";
        }
        
        return securityYn;
    }
    
    public boolean isDocCategoryS2(KETProjectDocument pdoc) throws Exception{
	
	boolean isS2 = false;
	
	KETDocumentCategory docCate = null;
        QueryResult r = PersistenceHelper.manager.navigate(pdoc, "documentCategory", KETDocumentCategoryLink.class);
        if ( r.hasMoreElements() ) {
            docCate = (KETDocumentCategory) r.nextElement();
        }
        
        if (StringUtils.isNotEmpty(docCate.getAttribute4()) && "S2".equals(pdoc.getSecurity())) {
            isS2 = true;
        }
        
        return isS2;
    }
    
}


