/**
 * 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : EWSUtil.java
 * 작성자 : 김경희
 * 작성일자 : 2010. 12. 09.
 */
package e3ps.ews.beans;

import java.util.ArrayList;
import java.util.StringTokenizer;

import wt.doc.WTDocument;
import wt.doc.WTDocumentMaster;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.org.WTUser;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.common.code.NumberCode;
import e3ps.common.obj.ObjectData;
import e3ps.common.obj.ObjectUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.KETObjectUtil;
import e3ps.common.util.StringUtil;
import e3ps.ews.dao.PartnerDao;
import e3ps.ews.entity.KETEarlyWarning;
import e3ps.ews.entity.KETEarlyWarningPlan;
import e3ps.ews.entity.KETEarlyWarningPlanLink;
import e3ps.project.E3PSProject;
import e3ps.project.ProductProject;
import e3ps.project.TemplateProject;
import e3ps.project.beans.ProjectUserHelper;
import ext.ket.shared.log.Kogger;


public class EWSUtil {

	/**
	 * 함수명 : ViewPjtName
	 * 설명 :
	 * @param pjtNo
	 * @return String
	 * @throws
	 * 작성자 : 김경희
	 * 작성일자 : 2010. 12. 09.
	 */
	public static String ViewPjtName( String pjtNo ){
		String pjtName = null;
		ProductProject productProject = null;

		try {
			QuerySpec qs = new QuerySpec();
			SearchCondition sc = null;
			int idx = qs.appendClassList(ProductProject.class, true);

			SearchCondition sc0 = new SearchCondition(ProductProject.class, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true") );
			qs.appendWhere(sc0, new int[] {idx});

			if (pjtNo!=null) {
				qs.appendAnd();

				sc = new SearchCondition(ProductProject.class, "master>pjtNo", SearchCondition.EQUAL, pjtNo);
				qs.appendWhere(sc, new int[] {idx});
			}

			QueryResult qr = PersistenceHelper.manager.find( qs );

			while(qr.hasMoreElements()){
				Object[] po = (Object[])qr.nextElement();
				productProject = (ProductProject)po[0];

				if (pjtNo!=null && !pjtNo.equals("")) {
					pjtName = productProject.getPjtName();
				}
			}
		} catch (QueryException e) {
			Kogger.error(EWSUtil.class, e);
		} catch (WTException e) {
			Kogger.error(EWSUtil.class, e);
		}

		return StringUtil.checkNull(pjtName);
	}

	/**
	 * 함수명 : ViewPjtRank
	 * 설명 :
	 * @param pjtNo
	 * @return String
	 * @throws
	 * 작성자 : 김경희
	 * 작성일자 : 2011. 03. 11.
	 */
	public static String ViewPjtRank( String pjtNo ){
		String pjtRank = null;
		E3PSProject productProject = null;

		try {
			QuerySpec qs = new QuerySpec();
			SearchCondition sc = null;
			int idx = qs.appendClassList(ProductProject.class, true);

			SearchCondition sc0 = new SearchCondition(ProductProject.class, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true") );
			qs.appendWhere(sc0, new int[] {idx});

			if (pjtNo!=null) {
				qs.appendAnd();

				sc = new SearchCondition(ProductProject.class, "master>pjtNo", SearchCondition.EQUAL, pjtNo);
				qs.appendWhere(sc, new int[] {idx});
			}

			QueryResult qr = PersistenceHelper.manager.find( qs );

			while(qr.hasMoreElements()){
				Object[] po = (Object[])qr.nextElement();
				productProject = (E3PSProject)po[0];

				if (pjtNo!=null && !pjtNo.equals("")) {
					pjtRank = productProject.getRank().getName();
				}
			}
		} catch (QueryException e) {
			Kogger.error(EWSUtil.class, e);
		} catch (WTException e) {
			Kogger.error(EWSUtil.class, e);
		}

		return StringUtil.checkNull(pjtRank);
	}

	/**
	 * 함수명 : ViewPjt
	 * 설명 :
	 * @param pjtNo
	 * @return String
	 * @throws
	 * 작성자 : 김경희
	 * 작성일자 : 2011. 03. 11.
	 */
	public static E3PSProject ViewPjt( String pjtNo ){
		E3PSProject productProject = null;

		try {
			QuerySpec qs = new QuerySpec();
			SearchCondition sc = null;
			int idx = qs.appendClassList(ProductProject.class, true);

			SearchCondition sc0 = new SearchCondition(ProductProject.class, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true") );
			qs.appendWhere(sc0, new int[] {idx});

			if (pjtNo!=null) {
				qs.appendAnd();

				sc = new SearchCondition(ProductProject.class, "master>pjtNo", SearchCondition.EQUAL, pjtNo);
				qs.appendWhere(sc, new int[] {idx});
			}

			QueryResult qr = PersistenceHelper.manager.find( qs );

			while(qr.hasMoreElements()){
				Object[] po = (Object[])qr.nextElement();
				productProject = (E3PSProject)po[0];
			}
		} catch (QueryException e) {
			Kogger.error(EWSUtil.class, e);
		} catch (WTException e) {
			Kogger.error(EWSUtil.class, e);
		}

		return productProject;
	}

	/**
	 * 함수명 : ViewPjtOid
	 * 설명 :
	 * @param pjtNo
	 * @return String
	 * @throws
	 * 작성자 : 김경희
	 * 작성일자 : 2010. 12. 29.
	 */
	public static String ViewPjtOid( String pjtNo ){
		String pjtOid = null;
		ProductProject productProject = null;

		try {
			QuerySpec qs = new QuerySpec();
			SearchCondition sc = null;
			int idx = qs.appendClassList(ProductProject.class, true);

			SearchCondition sc0 = new SearchCondition(ProductProject.class, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true") );
			qs.appendWhere(sc0, new int[] {idx});

			if (pjtNo!=null) {
				qs.appendAnd();

				sc = new SearchCondition(ProductProject.class, "master>pjtNo", SearchCondition.EQUAL, pjtNo);
				qs.appendWhere(sc, new int[] {idx});
			}

			QueryResult qr = PersistenceHelper.manager.find( qs );

			while(qr.hasMoreElements()){
				Object[] po = (Object[])qr.nextElement();
				productProject = (ProductProject)po[0];

				if (pjtNo!=null && !pjtNo.equals("")) {
					pjtOid = CommonUtil.getOIDString(productProject);
				}
			}
		} catch (QueryException e) {
			Kogger.error(EWSUtil.class, e);
		} catch (WTException e) {
			Kogger.error(EWSUtil.class, e);
		}

		return StringUtil.checkNull(pjtOid);
	}

	/**
     * 함수명 : isQuality
     * 설명 : 품질그룹 소속 여부
     * @return
     * @throws WTException
     * 작성자 :
     * 작성일자 : 2011. 01. 19.
     */
    public static boolean isQuality() throws WTException {
    	boolean flag = false;

    	WTUser user = (WTUser)SessionHelper.manager.getPrincipal();

        if( KETObjectUtil.isMember( user.getName() , "자동차사업부_품질" ) ) {
        	flag = true;
        }

    	return flag;
    }

    /**
     * 함수명 : isProduction
     * 설명 : 생산그룹 소속 여부
     * @return
     * @throws WTException
     * 작성자 :
     * 작성일자 : 2011. 01. 19.
     */
    public static boolean isProduction() throws WTException {
    	boolean flag = false;

    	WTUser user = (WTUser)SessionHelper.manager.getPrincipal();

        if( KETObjectUtil.isMember( user.getName() , "자동차사업부_생산" ) ) {
        	flag = true;
        }

    	return flag;
    }

    /**
     * 함수명 : isPurchase
     * 설명 : 구매그룹 소속 여부
     * @return
     * @throws WTException
     * 작성자 :
     * 작성일자 : 2011. 01. 19.
     */
    public static boolean isPurchase() throws WTException {
    	boolean flag = false;

    	WTUser user = (WTUser)SessionHelper.manager.getPrincipal();

        if( KETObjectUtil.isMember( user.getName() , "자동차사업부_구매" ) ) {
        	flag = true;
        }

    	return flag;
    }

    /**
     * 함수명 : isMember
     * 설명 : 초기유동관리 지정자, 담당자, 활동멤버 포함여부
     * @return
     * @throws WTException
     * 작성자 :
     * 작성일자 : 2011. 02. 16.
     */
    public static boolean isMember(KETEarlyWarning ketEarlyWarning) throws WTException {
    	boolean flag = false;
    	ArrayList<String> list = new ArrayList<String>();

    	QueryResult isPlan = PersistenceHelper.navigate((WTDocumentMaster)ketEarlyWarning.getMaster(), KETEarlyWarningPlanLink.ROLE_BOBJECT_ROLE, KETEarlyWarningPlanLink.class, false);
      	KETEarlyWarningPlanLink ketEarlyWarningPlanLink = null;
        Object planMaster = null;
        ObjectData planMasterData = null;
        KETEarlyWarningPlan ketEarlyWarningPlan = null;
        String planOid = null;

		try {
			if (isPlan != null && isPlan.size() != 0){
				while(isPlan.hasMoreElements()){
					ketEarlyWarningPlanLink = (KETEarlyWarningPlanLink)isPlan.nextElement();
					planMaster = (Object)ObjectUtil.getLatestObject((WTDocumentMaster)ketEarlyWarningPlanLink.getRoleBObject());
					planMasterData = new ObjectData((WTDocument)planMaster);
					planOid = planMasterData.getOid();
					ketEarlyWarningPlan = (KETEarlyWarningPlan)CommonUtil.getObject(planOid);
				}
			}
		} catch (Exception e) {
			Kogger.error(EWSUtil.class, e);
		}

    	String userName = ((WTUser)SessionHelper.manager.getPrincipal()).getName();

    	list.add(((WTUser)ketEarlyWarning.getCreator().getPrincipal()).getName());
    	list.add(((WTUser)CommonUtil.getObject(ketEarlyWarning.getFstCharge())).getName());
    	if (ketEarlyWarning.getSndCharge() != null) {
    		list.add(((WTUser)CommonUtil.getObject(StringUtil.checkNull(ketEarlyWarning.getSndCharge()))).getName());
    	}
    	if (ketEarlyWarningPlan.getCftMember() != null){
    		StringTokenizer st = new StringTokenizer(ketEarlyWarningPlan.getCftMember(), "/");
    		while (st.hasMoreTokens()){
    			list.add(((WTUser)CommonUtil.getObject(st.nextToken())).getName());
    		}
    	}

    	for(int inx = 0 ; inx < list.size(); inx++){
    		if ( userName.equals( list.get(inx)) ){
    			flag = true;
    		}
    	}

    	return flag;
    }

    /**
     * 함수명 : isMainMember
     * 설명 : 초기유동관리 지정자, 담당자 포함여부
     * @return
     * @throws WTException
     * 작성자 :
     * 작성일자 : 2011. 03. 11.
     */
    public static boolean isMainMember(KETEarlyWarning ketEarlyWarning) throws WTException {
    	boolean flag = false;
    	ArrayList<String> list = new ArrayList<String>();

    	String userName = ((WTUser)SessionHelper.manager.getPrincipal()).getName();

    	list.add(((WTUser)ketEarlyWarning.getCreator().getPrincipal()).getName());
    	list.add(((WTUser)CommonUtil.getObject(ketEarlyWarning.getFstCharge())).getName());
    	if (ketEarlyWarning.getSndCharge() != null) {
    		list.add(((WTUser)CommonUtil.getObject(StringUtil.checkNull(ketEarlyWarning.getSndCharge()))).getName());
    	}

    	for(int inx = 0 ; inx < list.size(); inx++){
    		if ( userName.equals( list.get(inx)) ){
    			flag = true;
    		}
    	}

    	return flag;
    }

    /**
     * 함수명 : isSPjtMember
     * 설명 : 초기유동관리 지정자, 담당자 포함여부
     * @return
     * @throws WTException
     * 작성자 :
     * 작성일자 : 2011. 03. 11.
     */
    public static boolean isSPjtMember(String oid) throws WTException {
    	boolean flag = false;
    	ArrayList<String> list = new ArrayList<String>();

    	KETEarlyWarning ketEarlyWarning = (KETEarlyWarning)CommonUtil.getObject(oid);
    	QueryResult isPlan = PersistenceHelper.navigate((WTDocumentMaster)ketEarlyWarning.getMaster(), KETEarlyWarningPlanLink.ROLE_BOBJECT_ROLE, KETEarlyWarningPlanLink.class, false);
      	KETEarlyWarningPlanLink ketEarlyWarningPlanLink = null;
        Object planMaster = null;
        ObjectData planMasterData = null;
        KETEarlyWarningPlan ketEarlyWarningPlan = null;
        String planOid = null;

		try {
			if (isPlan != null && isPlan.size() != 0){
				while(isPlan.hasMoreElements()){
					ketEarlyWarningPlanLink = (KETEarlyWarningPlanLink)isPlan.nextElement();
					planMaster = (Object)ObjectUtil.getLatestObject((WTDocumentMaster)ketEarlyWarningPlanLink.getRoleBObject());
					planMasterData = new ObjectData((WTDocument)planMaster);
					planOid = planMasterData.getOid();
					ketEarlyWarningPlan = (KETEarlyWarningPlan)CommonUtil.getObject(planOid);
				}
			}
		} catch (Exception e) {
			Kogger.error(EWSUtil.class, e);
		}

		WTUser wtuser = (WTUser)SessionHelper.manager.getPrincipal();
    	String userName =wtuser.getName();

    	list.add(((WTUser)ketEarlyWarning.getCreator().getPrincipal()).getName());
    	list.add(((WTUser)CommonUtil.getObject(ketEarlyWarning.getFstCharge())).getName());

    	if (ketEarlyWarning.getSndCharge() != null) {
    		list.add(((WTUser)CommonUtil.getObject(StringUtil.checkNull(ketEarlyWarning.getSndCharge()))).getName());
    	}

    	try{
	    	if (ketEarlyWarningPlan.getCftMember() != null){
	    		StringTokenizer st = new StringTokenizer(ketEarlyWarningPlan.getCftMember(), "/");
	    		while (st.hasMoreTokens()){
	    			list.add(((WTUser)CommonUtil.getObject(st.nextToken())).getName());
	    		}
	    	}
    	}catch(Exception e){
    		Kogger.error(EWSUtil.class, e);
    	}

    	for(int inx = 0 ; inx < list.size(); inx++){
    		if ( userName.equals( list.get(inx)) ){
    			flag = true;
    		}
    	}

    	E3PSProject project = ViewPjt(ketEarlyWarning.getPjtNo());
    	Boolean isProjectMember = ProjectUserHelper.manager.isProjectUserAll( (TemplateProject)project, wtuser);
    	if ( isProjectMember ){
			flag = true;
		}

    	return flag;
    }

    /**
	 * 함수명 : ViewInout
	 * 설명 :
	 * @param ketEarlyWarning
	 * @return String
	 * @throws
	 * 작성자 : 김경희
	 * 작성일자 : 2011. 01. 26.
	 */
	public static String ViewInout( KETEarlyWarning ketEarlyWarning ){
		String inout = ketEarlyWarning.getInOut();
	   	String inoutName = null;
	   	PartnerDao partnerDao = null;

   		if (inout != null && inout.equals("i")){
   		   inout = "사내 - ";
   		   inoutName = inout + StringUtil.checkNull(((NumberCode)CommonUtil.getObject(ketEarlyWarning.getProteamNo())).getName());
   		}else if (inout != null && inout.equals("o")){
   		   inout = "외주 - ";
		   partnerDao = new PartnerDao();
		   inoutName = inout  + StringUtil.checkNull(partnerDao.ViewPartnerName(ketEarlyWarning.getPartnerNo()));
   		}

		return StringUtil.checkNull(inoutName);
	}

}
