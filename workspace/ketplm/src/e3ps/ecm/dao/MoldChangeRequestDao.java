package e3ps.ecm.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.lang.StringUtils;

import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.treegrid.TreeGridUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.KETQueryUtil;
import e3ps.common.util.KETStringUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.PropertiesUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.util.WCUtil;
import e3ps.ecm.beans.ECMProperties;
import e3ps.ecm.beans.EcmUtil;
import e3ps.ecm.beans.ProdEcrBeans;
import e3ps.ecm.entity.KETChangeRequestExpansion;
import e3ps.ecm.entity.KETCompetentDepartment;
import e3ps.ecm.entity.KETMoldChangeLink;
import e3ps.ecm.entity.KETMoldChangeRequest;
import e3ps.ecm.entity.KETMoldChangeRequestVO;
import e3ps.ecm.entity.KETMoldECOPartLinkVO;
import e3ps.ecm.entity.KETMoldECRIssueLinkVO;
import e3ps.ecm.entity.KETProdChangeRequest;
import e3ps.ecm.entity.KETSearchEcoDetailVO;
import e3ps.groupware.company.Department;
import e3ps.project.E3PSProject;
import e3ps.project.beans.E3PSProjectData;
import ext.ket.shared.log.Kogger;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.lifecycle.State;
import wt.org.WTUser;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;

public class MoldChangeRequestDao {
    private static final long serialVersionUID = -1432672658288760111L;
    private static String dateFormatString = EcmUtil.getDateFormatString();

    /**
     * 
     * 함수명 : getEcrId 설명 : ECRID를 채번한다.(ex:ECR-YYMM-001)
     * 
     * @return ECRID
     * @throws Exception
     *             작성자 : 안수학 작성일자 : 2010. 12. 31.
     */
    public String getEcrId() throws Exception {
	StringBuffer sql = new StringBuffer();
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    con = PlmDBUtil.getConnection();
	    // sql.append("SELECT 'ECR-' || TO_CHAR(SYSDATE, 'YYMM') || '-'  ")
	    // .append("    || LPAD(TO_NUMBER( ")
	    // .append("        SUBSTR(NVL(MAX(ECRID), 'ECR-' || TO_CHAR(SYSDATE, 'YYMM') || '-000'), 10) ")
	    // .append("    ) + 1, 3, '0') ECRID ")
	    // .append("FROM KETMoldChangeRequest ")
	    // .append("WHERE ECRID LIKE 'ECR-' || TO_CHAR(SYSDATE, 'YYMM') || '%' ");

	    sql.append("SELECT FN_GET_ECM_NUMBERING(?) FROM DUAL \n");
	    pstmt = con.prepareStatement(sql.toString());
	    pstmt.setString(1, "ECR");

	    rs = pstmt.executeQuery();
	    String ecrId = "";
	    while (rs.next()) {
		ecrId = rs.getString(1);
	    }

	    return ecrId;
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw new Exception(e);
	} finally {
	    sql.delete(0, sql.length());
	    if (rs != null)
		rs.close();
	    if (pstmt != null)
		pstmt.close();
	    PlmDBUtil.close(con);
	}
    }

    /**
     * 
     * 함수명 : getProdVendorName 설명 : 사내 생산처명을 조회한다.
     * 
     * @param vendorFlag
     *            :생산처구분(i:사내 o:외주)
     * @param oid
     *            : 부서oid
     * @return String : 생산처명
     * @throws Exception
     *             작성자 : 안수학 작성일자 : 2011. 1. 4.
     */
    public String getProdVendorName(String vendorFlag, String oid) throws Exception {
	String prodVendorName = "";
	if (vendorFlag == null)
	    return prodVendorName;
	if (oid == null)
	    return prodVendorName;
	if ("i".equals(vendorFlag)) {// 사내
	    Department dept = (Department) WCUtil.getObject(oid);
	    if (dept != null) {
		prodVendorName = dept.getName();
	    }
	}
	return prodVendorName;
    }

    /**
     * 
     * 함수명 : searchEcrList 설명 : 제픔/금형 ECR 목록을 검색한다.
     * 
     * @param ketSearchEcoVO
     *            : 금형 ECO 검색 VO
     * @param isExcel
     *            : 엑셀처리여부(0:미처리 1:엑셀처리)
     * @return KETSearchEcoVO : 금형 ECO 검색 VO
     * @throws Exception
     *             작성자 : 남현승 작성일자 : 2013. 8. 5.
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> searchEcrList(boolean isExcel, KETParamMapUtil hash, List<Map<String, Object>> conditionList)
	    throws Exception {

	Object[] returnObjects = this.searchEcrList3rd(isExcel, hash, conditionList);
	List<Map<String, Object>> ecoMonthlyReportList = (List<Map<String, Object>>) returnObjects[0];

	return ecoMonthlyReportList;

    }

    public StringBuffer searchEcrList2nd(boolean isExcel, KETParamMapUtil hash, List<Map<String, Object>> conditionList) throws Exception {

	Object[] returnObjects = this.searchEcrList3rd(isExcel, hash, conditionList);
	StringBuffer strBuffer = (StringBuffer) returnObjects[1];

	return strBuffer;

    }

    private Object[] searchEcrList3rd(boolean isExcel, KETParamMapUtil hash, List<Map<String, Object>> conditionList) throws Exception {
	ArrayList<KETSearchEcoDetailVO> ketSearchEcoDetailVOList = new ArrayList<KETSearchEcoDetailVO>();// ECO 검색상세 List
	KETSearchEcoDetailVO ketSearchEcoDetailVO = null;
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = "";
	Map<String, Object> ecrList = null;

	Object[] returnObjects = new Object[2];
	List<Map<String, Object>> ecoMonthlyReportList = new ArrayList<Map<String, Object>>();
	StringBuffer strBuffer = new StringBuffer();
	String detailInfoUrl = null;
	int rowCount = 1;

	try {
	    con = PlmDBUtil.getConnection();

	    sql = getSearchEcrListSQL(isExcel, hash, conditionList);
	    Kogger.debug(getClass(), " ########################################  sql  ==  " + sql);

	    pstmt = con.prepareStatement(sql);
	    rs = pstmt.executeQuery();

	    EcmComDao ecmComDao = new EcmComDao(con);

	    while (rs.next()) {
		ecrList = new HashMap<String, Object>();

		ecrList.put("EcoId", StringUtil.checkNull(rs.getString("ecrId")));// 1.
		ecrList.put("EcoName", StringUtil.checkNull(rs.getString("ecrName")));// 2
		ecrList.put("CreatorName", rs.getString("creatorName"));// 5
		ecrList.put("CreateDate", rs.getString("createDate"));// 6

		// ecrList.put("ApproveDate", EcmUtil.getLastApproveDate((Persistable)KETObjectUtil.getObject(rs.getString("oid"))) );
		ecrList.put("ApproveDate", rs.getString("approveDate2"));

		ecrList.put("SancStateFlag", rs.getString("stateState"));// 9
		ecrList.put("CreateDeptName", rs.getString("orgName"));// 9
		ecrList.put("Oid", rs.getString("oid"));
		ecrList.put("ProdMoldClsName", rs.getString("prodMoldClsName"));
		ecrList.put("ProjectNo", StringUtil.checkNull(rs.getString("projectNo")));
		ecrList.put("ProjectName", StringUtil.checkNull(rs.getString("projectName")));
		ecrList.put("ProjectOid", StringUtil.checkNull(rs.getString("projectOid")));

		ecrList.put("DevFlag", StringUtil.checkNull(rs.getString("dev_yn")));

		// ecrList.put("PartNumber", EcmSearchHelper.manager.getPartNoStr(rs.getString("oid")) );
		// ecrList.put("PartName", EcmSearchHelper.manager.getPartNameStr(rs.getString("oid")));
		ecrList.put("PartNumber", rs.getString("partNumber2"));
		ecrList.put("PartName", rs.getString("partName2"));

		ecrList.put("ReqType", StringUtil.checkNull(rs.getString("req_type")));
		if (StringUtil.checkNull(rs.getString("req_type_desc")).length() > 0) {
		    ecrList.put("ReqTypeDesc", "(" + StringUtil.checkNull(rs.getString("req_type_desc")) + ")");
		} else {
		    ecrList.put("ReqTypeDesc", StringUtil.checkNull(rs.getString("req_type_desc")));
		}

		// ecrList.put("MoldReqChgType", getCodeNames("CHANGETYPE", StringUtil.checkNull(rs.getString("mold_chg_type")) ) );
		ecrList.put("MoldReqChgType", rs.getString("moldReqChgType2"));

		if (StringUtil.checkNull(rs.getString("mold_chg_type_desc")).length() > 0) {
		    ecrList.put("MoldReqChgTypeDesc", "(" + StringUtil.checkNull(rs.getString("mold_chg_type_desc")) + ")");
		} else {
		    ecrList.put("MoldReqChgTypeDesc", StringUtil.checkNull(rs.getString("mold_chg_type_desc")));
		}

		// ecrList.put("ProcType", ecmComDao.getCodeName("PROCESSTYPE", StringUtil.checkNull(rs.getString("proc_type")) ) );
		ecrList.put("ProcType", rs.getString("procType2"));

		if (StringUtil.checkNull(rs.getString("proc_type_desc")).length() > 0) {
		    ecrList.put("ProdTypeDesc", "(" + StringUtil.checkNull(rs.getString("proc_type_desc")) + ")");
		} else {
		    ecrList.put("ProdTypeDesc", StringUtil.checkNull(rs.getString("proc_type_desc")));
		}

		if (StringUtil.checkNull(rs.getString("vendor_flag")).length() > 0) {
		    if ("i".equals(rs.getString("vendor_flag"))) {
			ecrList.put("ProdVendor",
			        StringUtil.checkNull(ecmComDao.getCodeName("PRODUCTIONDEPT", rs.getString("prod_vendor"))));// 생산처명
		    } else if ("o".equals(rs.getString("vendor_flag"))) {
			ecrList.put("ProdVendor", StringUtil.checkNull(ecmComDao.getPartnerName(rs.getString("prod_vendor"))));
		    }
		} else {
		    ecrList.put("ProdVendor", StringUtil.checkNull(rs.getString("prod_vendor")));
		}

		ecrList.put("CompleteReqDate", StringUtil.checkNull(rs.getString("complete_req_date")));

		// ecrList.put("RelatedECOStr", EcmSearchHelper.manager.getRelatedECOLinkStr(rs.getString("oid")) );
		ecrList.put("RelatedECOStr", rs.getString("relatedECOStr2"));

		ecoMonthlyReportList.add(ecrList);

		if (ecrList.get("ProdMoldClsName").equals("제품")) {
		    detailInfoUrl = "/plm/servlet/e3ps/ProdEcrServlet?prePage=Search&cmd=View&oid=" + ecrList.get("Oid"); //

		} else {
		    detailInfoUrl = "/plm/servlet/e3ps/MoldEcrServlet?cmd=view&oid=" + ecrList.get("Oid"); // 금형

		}
		
		//검토승인 날짜 가져오는 로직
		ProdEcrBeans beans = new ProdEcrBeans();
		String ecrOid = (String) ecrList.get("Oid");
		KETCompetentDepartment competent = beans.getKETCompetentDepartment(ecrOid);
		String pboOid = StringUtils.stripToEmpty(CommonUtil.getOIDString( competent ));
		PreparedStatement pstmt1 = null;
		ResultSet rs1 = null;
		String reviewApproval = "";
		
		Persistable reqObj = CommonUtil.getObject(ecrOid);
		
		if(reqObj instanceof KETProdChangeRequest) {
		    KETProdChangeRequest prodReq = (KETProdChangeRequest) reqObj;
		    ecrList.put("changeReason", StringUtil.checkNull(prodReq.getChangeReason()));
		}
		
		if(competent != null) { 
		    Department subject = competent.getSubject();
		    if(subject != null){
		    ecrList.put("departManagement", subject.getName());
		    }
		    
		    WTUser charge = competent.getCharge();
		    if(charge != null){
		    ecrList.put("departPerson", charge.getFullName());
		    }
		}else{
		  //제품, 금형 ECR 확장팩
		    KETChangeRequestExpansion expansion = null;
		    // ECR 로 찾는다.
		    QuerySpec spec = new QuerySpec(KETChangeRequestExpansion.class);
		    spec.appendWhere(new SearchCondition(KETChangeRequestExpansion.class, "ecrReference.key.id", "=", CommonUtil.getOIDLongValue(ecrOid)), new int[] { 0 });
		    QueryResult result = PersistenceHelper.manager.find(spec);
		    if (result.hasMoreElements()) { // while (result.hasMoreElements()) {
		      expansion = (KETChangeRequestExpansion) result.nextElement();
		    }
		    
		    if(expansion != null){
        		    Department subject = expansion.getSubject();
        		    if(subject != null){
        		    ecrList.put("departManagement", subject.getName());
        		    }
        		    
        		    WTUser charge = expansion.getCharge();
        		    if(charge != null){
        		    ecrList.put("departPerson", charge.getFullName());
        		    }
		    }
		}
		
		try{
		    if (PersistenceHelper.isPersistent(competent)) {
        		sql =   " SELECT to_char(MODIFYSTAMPA2,'yyyy-mm-dd') reviewApproval"
                               +" FROM KETWFMAPPROVALHISTORY"
                               +" WHERE ida3a4 IN ("
                               +"                     SELECT ida2a2"
                               +"                     FROM KETWFMAPPROVALMASTER"
                               +"                     WHERE classnamekeyb4||':'|| ida3b4 = '"+pboOid
                               +"'                 )"
                               +" AND decision = '승인'"
                               +" AND last = 1"; 
        		 pstmt1 = con.prepareStatement(sql);
        		 rs1 = pstmt1.executeQuery();
        		 
        		 while(rs1.next()){
        			    reviewApproval =  rs1.getString("reviewApproval");
        			    ecrList.put("ReviewDate", reviewApproval);
        			}
		    }
		
		}catch(Exception e){
		    throw e; 
		} finally{
		    if (rs1 != null)
			rs1.close();
		    if (pstmt1 != null)
			pstmt1.close();
		}
		URLCodec urlCodec = new URLCodec();
		detailInfoUrl = "/plm/jsp/ecm/reform/ViewEcrForm.jsp?redirectURL=" + urlCodec.encode(detailInfoUrl); //

		strBuffer.append("<I NoColor=&quot;2&quot; CanDelete=&quot;0&quot;");
		strBuffer.append(" RowNum=&quot;" + rowCount++ + "&quot;");
		strBuffer.append(" Oid=&quot;" + ecrList.get("Oid") + "&quot;");
		strBuffer.append(" ProdMoldCls=&quot;" + ecrList.get("ProdMoldClsName") + "&quot;");
		strBuffer.append(" EcrNo=&quot;" + ecrList.get("EcoId") + "&quot;"
		        // + "EcrNoOnClick=&quot;location.href=&apos;" + detailInfoUrl + "&apos;&quot;"
		        + "EcrNoOnClick=&quot;javascript:doView(&apos;" + detailInfoUrl + "&apos;);&quot;"

		        + " EcrNoHtmlPrefix=&quot;&lt;font color=&apos;" + PropertiesUtil.getSearchGridLinkColor()
		        + "&apos;&gt;&quot; EcrNoHtmlPostfix=&quot;&lt;/font&gt;&quot;");
		strBuffer.append(" EcrName=&quot;" + TreeGridUtil.replaceContentForI(ecrList.get("EcoName").toString()) + "&quot;");
		strBuffer.append(" DeptName=&quot;" + ecrList.get("CreateDeptName") + "&quot;");
		strBuffer.append(" changeReason=&quot;" + ecrList.get("changeReason") + "&quot;");
		
		if (ecrList.get("ProjectOid") != null && !ecrList.get("ProjectOid").equals("")) {
		    strBuffer.append(" ProjectNo=&quot;" + ecrList.get("ProjectNo") + "&quot;"
			    + " ProjectNoOnClick=&quot;javascript:openProject(&apos;" + ecrList.get("ProjectNo") + "&apos;);&quot;"
			    + " ProjectNoHtmlPrefix=&quot;&lt;font color=&apos;" + PropertiesUtil.getSearchGridLinkColor()
			    + "&apos;&gt;&quot; ProjectNoHtmlPostfix=&quot;&lt;/font&gt;&quot;");
		} else {
		    strBuffer.append(" ProjectNo=&quot;" + ecrList.get("ProjectNo") + "&quot;");
		}

		strBuffer.append(" Creator=&quot;" + ecrList.get("CreatorName") + "&quot;");
		strBuffer.append(" CreateDate=&quot;" + ecrList.get("CreateDate") + "&quot;");

		State state = State.toState((String) ecrList.get("SancStateFlag"));
		strBuffer.append(" StateAppro=&quot;" + ((state != null) ? state.getDisplay() : "") + "&quot;");
		strBuffer.append(" CompDate=&quot;" + ecrList.get("CompleteReqDate") + "&quot;");
		strBuffer.append(" ApproveDate=&quot;" + (ecrList.get("ApproveDate") != null ? ecrList.get("ApproveDate") : "") + "&quot;");
		strBuffer.append(" ReviewDate=&quot;" + reviewApproval + "&quot;");
		strBuffer.append(" departManagement=&quot;" + (ecrList.get("departManagement") != null ? ecrList.get("departManagement") : "") + "&quot;");
		strBuffer.append(" departPerson=&quot;" + (ecrList.get("departPerson") != null ? ecrList.get("departPerson") : "") + "&quot;");
		strBuffer.append("/>");

	    }

	} catch (Exception e) {
	    throw e;
	} finally {
	    if (rs != null)
		rs.close();
	    if (pstmt != null)
		pstmt.close();
	    PlmDBUtil.close(con);
	}
	// ketSearchEcoVO.setKetSearchEcoDetailVOList(ketSearchEcoDetailVOList);

	returnObjects[0] = ecoMonthlyReportList;
	returnObjects[1] = strBuffer;

	return returnObjects;
    }

    /**
     * 
     * 함수명 : searchEcrDetail 설명 : 금형 ECR 상세내역을 조회한다.
     * 
     * @param ketMoldChangeRequestVO
     *            : 금형 ECR VO
     * @return KETMoldChangeRequestVO : 금형 ECR VO
     * @throws Exception
     *             작성자 : 안수학 작성일자 : 2011. 1. 4.
     */
    // ECR상세조회
    public KETMoldChangeRequestVO searchEcrDetail(KETMoldChangeRequestVO ketMoldChangeRequestVO) throws Exception {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = "";

	KETMoldChangeRequest ketMoldChangeRequest = ketMoldChangeRequestVO.getKetMoldChangeRequest();
	try {
	    con = PlmDBUtil.getConnection();
	    sql = getSearchMoldEcrDetailSQL(ketMoldChangeRequestVO);
	    pstmt = con.prepareStatement(sql);
	    rs = pstmt.executeQuery();

	    MoldChangeOrderDao moldChangeOrderDao = new MoldChangeOrderDao();

	    if (rs.next()) {
		Kogger.debug(getClass(), "searchEcrDetail --------> 1");
		// ketMoldChangeRequest.setEcrId(StringUtil.checkNull(rs.getString("ecrId")));//1
		ketMoldChangeRequest.setDevYn(StringUtil.checkNull(rs.getString("devYn")));// 2
		ketMoldChangeRequestVO.setDevYnName(StringUtil.checkNull(rs.getString("devYnName")));// 2
		// ketMoldChangeRequest.setEcrName(StringUtil.checkNull(rs.getString("ecrName")));//3
		// ketMoldChangeRequest.setDivisionFlag(StringUtil.checkNull(rs.getString("divisionFlag")));//4
		ketMoldChangeRequestVO.setDivisionFlagName(StringUtil.checkNull(rs.getString("divisionFlagName")));// 4
		ketMoldChangeRequestVO.setProgressState(StringUtil.checkNull(rs.getString("stateState")));// 5.진행상태
		String stateName = ECMProperties.getInstance().getString(ketMoldChangeRequestVO.getProgressState());
		ketMoldChangeRequestVO.setProgressStateName(stateName);// 5.진행상태명
		ketMoldChangeRequestVO.setOrgName(StringUtil.checkNull(rs.getString("orgName")));// 6.작성부서명
		ketMoldChangeRequestVO.setTeamName(StringUtil.checkNull(rs.getString("teamName")));// 6.작성팀명
		ketMoldChangeRequestVO.setCreatorName(StringUtil.checkNull(rs.getString("creatorName")));// 7.작성자명
		ketMoldChangeRequestVO.setCreateDate(StringUtil.checkNull(rs.getString("createDate")));// 8.작성일자
		ketMoldChangeRequestVO.setUpdateDate(StringUtil.checkNull(rs.getString("updateDate")));// 9.수정일자
		ketMoldChangeRequestVO.setApprovalName(StringUtil.checkNull(EcmUtil.getLastApproverName(ketMoldChangeRequest)));// 10.승인자명
		ketMoldChangeRequestVO.setApprovalDate(StringUtil.checkNull(EcmUtil.getLastApproveDate(ketMoldChangeRequest)));// 11.승인일자
		Kogger.debug(getClass(), "searchEcrDetail --------> 2");
		String oid = StringUtil.checkNull(rs.getString("projectOid"));
		// ketMoldChangeRequest.setProjectOid(oid);//5.프로젝트oid
		if (!"".equals(oid)) {
		    E3PSProject project = (E3PSProject) CommonUtil.getObject(oid);
		    if (project != null) {
			E3PSProjectData projectData = new E3PSProjectData(project);
			ketMoldChangeRequestVO.setProjectNo(projectData.ViewpjtNo);// 5.프로젝트번호
			ketMoldChangeRequestVO.setProjectName(projectData.pjtName);// 5.프로젝트명
		    } else {
			ketMoldChangeRequestVO.setProjectNo("");// 5.프로젝트번호
			ketMoldChangeRequestVO.setProjectName("");// 5.프로젝트명
		    }
		} else {
		    ketMoldChangeRequestVO.setProjectNo("");// 5.프로젝트번호
		    ketMoldChangeRequestVO.setProjectName("");// 5.프로젝트명
		}
		Kogger.debug(getClass(), "searchEcrDetail --------> 3");
		String vendorFlag = StringUtil.checkNull(ketMoldChangeRequest.getVendorFlag());
		String prodVendor = StringUtil.checkNull(rs.getString("prodVendor"));
		if (prodVendor.length() > 0) {
		    if (vendorFlag.equals("i")) {
			NumberCode code = NumberCodeHelper.manager.getNumberCode("PRODUCTIONDEPT", prodVendor);

			if (code == null) {
			    ketMoldChangeRequestVO.setProdVendorName("");// 생산처명
			} else {
			    ketMoldChangeRequestVO.setProdVendorName(StringUtil.checkNull(code.getName()));// 생산처명
			}
		    } else if (vendorFlag.equals("o")) {
			EcmComDao ecmComDao = new EcmComDao(con);
			ketMoldChangeRequestVO.setProdVendorName(StringUtil.checkNull(ecmComDao.getPartnerName(prodVendor)));// 생산처명
		    }
		} else {
		    ketMoldChangeRequestVO.setProdVendorName("");// 생산처명
		}
		Kogger.debug(getClass(), "searchEcrDetail --------> 4");
		ketMoldChangeRequestVO.setCompleteReqDateFormat(EcmUtil.getClientDateFormat(StringUtil.checkNull(rs
		        .getString("completeReqDate"))));// 14.완료요청일
		ketMoldChangeRequest.setProcessType(StringUtil.checkNull(rs.getString("processType")));// 15.처리구분
		ketMoldChangeRequest.setOtherProcessDesc(StringUtil.checkNull(rs.getString("otherprocessdesc")));
		ketMoldChangeRequestVO.setProcessTypeName(StringUtil.checkNull(rs.getString("processTypeName")));// 15.처리구분명
		ketMoldChangeRequestVO.setOtherProcessdesc(StringUtil.checkNull(rs.getString("otherprocessdesc")));
		ketMoldChangeRequest.setRequestType(StringUtil.checkNull(rs.getString("requestType")));// 16.의뢰구분
		ketMoldChangeRequest.setOtherReqType(StringUtil.checkNull(rs.getString("otherreqtype")));

		Kogger.debug(getClass(), "searchEcrDetail --------> 5");
		ketMoldChangeRequestVO.setRequestTypeName(StringUtil.checkNull(rs.getString("RequestTypeName")));// 16.의뢰구분명
		ketMoldChangeRequestVO.setOtherRequestType(StringUtil.checkNull(rs.getString("otherreqtype")));
		ketMoldChangeRequestVO.setChangeTypeName(moldChangeOrderDao.getCodeNames("CHANGETYPE",
		        StringUtil.checkNull(rs.getString("changeType"))));// 17.설계변경유형
		ketMoldChangeRequestVO.setOtherChangeType(StringUtil.checkNull(rs.getString("otherchangedesc")));
		Kogger.debug(getClass(), "searchEcrDetail --------> 6");
		ketMoldChangeRequestVO.setOid(rs.getString("oid"));
		ketMoldChangeRequestVO.setTotalCount(1);

		Kogger.debug(getClass(), "searchEcrDetail --------> 7");
		// ketMoldChangeRequestVO.setKetMoldChangeRequest(ketMoldChangeRequest);
		ketMoldChangeRequestVO.setExistRelMoldEcoLink(existKETMoldChangeLink(ketMoldChangeRequest));// 관련금형eco 존재여부 확인
		Kogger.debug(getClass(), "searchEcrDetail --------> 8");
		// 관련부품 조회
		String idA2A2 = rs.getString("idA2A2");
		ketMoldChangeRequestVO.setKetMoldECOPartLinkVOList(getKETMoldECOPartLink(idA2A2));// 관련부품 VO세팅
		ketMoldChangeRequestVO.setKetMoldECRIssueLinkVOList(getKETMoldECRIssueLink(idA2A2));// 관련이슈 VO세팅

	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	} finally {
	    if (rs != null)
		rs.close();
	    if (pstmt != null)
		pstmt.close();
	    PlmDBUtil.close(con);
	}
	return ketMoldChangeRequestVO;
    }

    /**
     * 
     * 함수명 : getKETMoldECOPartLink 설명 : 금형ECR 관련 부품 목록을 조회한다.
     * 
     * @param moldEcrOid
     *            : 금형ECR oid
     * @return ArrayList<KETMoldECOPartLinkVO> : 금형ECR 관련 부품 목록 VO
     * @throws IOException
     *             작성자 : 안수학 작성일자 : 2011. 1. 4.
     */
    private ArrayList<KETMoldECOPartLinkVO> getKETMoldECOPartLink(String moldEcrOid) throws IOException {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = "";

	ArrayList<KETMoldECOPartLinkVO> ketMoldECOPartLinkVOList = new ArrayList<KETMoldECOPartLinkVO>();// 금형ECO관련부품 List
	KETMoldECOPartLinkVO ketMoldECOPartLinkVO = null;
	try {
	    con = PlmDBUtil.getConnection();
	    sql = getSearchRelPartListSQL(moldEcrOid);
	    pstmt = con.prepareStatement(sql);
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		ketMoldECOPartLinkVO = new KETMoldECOPartLinkVO();
		ketMoldECOPartLinkVO.setRelPartOid(StringUtil.checkNull(rs.getString("partOid")));
		ketMoldECOPartLinkVO.setRelPartNumber(StringUtil.checkNull(rs.getString("partNumber")));// 관련부품번호
		ketMoldECOPartLinkVO.setRelPartName(StringUtil.checkNull(rs.getString("partName")));// 관련부품명
		ketMoldECOPartLinkVO.setRelPartRev(StringUtil.checkNull(rs.getString("partVersion")));// 관련부품버젼
		ketMoldECOPartLinkVO.setChangeReqComment(StringUtil.checkNull(rs.getString("changeReqComment")));// 요청내용
		ketMoldECOPartLinkVO.setRelPartLinkOid(StringUtil.checkNull(rs.getString("relPartLinkOid")));// 관련부품linkoid
		ketMoldECOPartLinkVOList.add(ketMoldECOPartLinkVO);
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    try {
		if (rs != null)
		    rs.close();
		if (pstmt != null)
		    pstmt.close();
	    } catch (SQLException e) {
	    }
	    PlmDBUtil.close(con);
	}
	return ketMoldECOPartLinkVOList;
    }// end-of-getKETMoldECOPartLink

    /**
     * 
     * 함수명 : getKETMoldECRIssueLink 설명 : 금형ECR 관련 이슈 목록을 조회한다.
     * 
     * @param moldEcrOid
     *            : 금형ECR oid
     * @return ArrayList<KETMoldECRIssueLinkVO> : 금형ECR 관련 이슈 목록 VO
     * @throws IOException
     *             작성자 : 안수학 작성일자 : 2011. 1. 4.
     */
    private ArrayList<KETMoldECRIssueLinkVO> getKETMoldECRIssueLink(String moldEcrOid) throws IOException {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = "";

	ArrayList<KETMoldECRIssueLinkVO> ketMoldECRIssueLinkVOList = new ArrayList<KETMoldECRIssueLinkVO>();// 금형ECO관련부품 List
	KETMoldECRIssueLinkVO ketMoldECRIssueLinkVO = null;
	try {
	    con = PlmDBUtil.getConnection();
	    sql = getSearchRelIssueListSQL(moldEcrOid);
	    pstmt = con.prepareStatement(sql);
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		ketMoldECRIssueLinkVO = new KETMoldECRIssueLinkVO();
		ketMoldECRIssueLinkVO.setRelIssueOid(StringUtil.checkNull(rs.getString("projectIssueOid")));// 관련Issueoid
		ketMoldECRIssueLinkVO.setRelIssueCreatorName(StringUtil.checkNull(rs.getString("creatorName")));// 관련Issue작성자명
		ketMoldECRIssueLinkVO.setRelIssueCreateDate(StringUtil.checkNull(rs.getString("createDate")));// 작성일자
		ketMoldECRIssueLinkVO.setRelIssueName(StringUtil.checkNull(rs.getString("subject")));// 관련Issue명
		ketMoldECRIssueLinkVO.setRelIssueLinkOid(StringUtil.checkNull(rs.getString("relIssueLinkOid")));// 관련Issuelinkoid
		ketMoldECRIssueLinkVOList.add(ketMoldECRIssueLinkVO);
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    try {
		if (rs != null)
		    rs.close();
		if (pstmt != null)
		    pstmt.close();
	    } catch (SQLException e) {
	    }
	    PlmDBUtil.close(con);
	}
	return ketMoldECRIssueLinkVOList;
    }// end-of-getKETMoldECRIssueLink

    /**
     * 
     * 함수명 : getSearchEcrListSQL 설명 : 제품/금형 ECR 목록을 조회하는 SQL을 작성한다.
     * 
     * @param ketSearchEcoVO
     *            : 금형 ECO 검색 VO
     * @param ingPage
     *            : page 처리여부(0:미처리 1:page처리)
     * @param isExcel
     *            : 엑셀처리여부(0:미처리 1:엑셀처리)
     * @return String : SQL
     * @throws Exception
     *             작성자 : 남현승 작성일자 : 2013. 8. 5.
     */
    private String getSearchEcrListSQL(boolean isExcel, KETParamMapUtil paramMap, List<Map<String, Object>> conditionList) throws Exception {
	StringBuffer sql = new StringBuffer();
	sql.append("SELECT *                                                                                                                                                                         \n");
	sql.append("  FROM (                                                                                                                                                                         \n");
	sql.append("        SELECT ROWNUM row_id                                                                             \n");
	sql.append("             , t.*                                                                                                                                                         \n");
	sql.append("          FROM (                                                                                                                                                         \n");
	if (isExcel) {
	    sql.append("               SELECT ecrId                                                        ecrId                                                \n");
	} else {
	    sql.append("               SELECT substr(ecrId, 5)                                             ecrId                                                \n");
	}
	sql.append("                     , ecrName                                                         ecrName                     \n");
	sql.append("                     , ''                                                              partNumber                  \n");
	sql.append("                     , ''                                                              partName                    \n");
	sql.append("                     , creatorName                                                     creatorName                 \n");
	sql.append("                     , createDate                                                      createDate                  \n");
	sql.append("                     , stateState                                                      stateState                  \n");
	sql.append("                     , partNumberList                                                  partNumberList              \n");
	sql.append("                     , oid                                                             oid                         \n");

	sql.append("                     , classname2, idvalue2 \n");
	// 인덱스 생성 : KETWFMAPPROVALMASTER$IDA3B4
	sql.append("                     , (SELECT TO_CHAR(MAX(wam.completeddate), 'yyyy-MM-dd') FROM KETWfmApprovalMaster wam WHERE wam.IDA3B4 = idvalue2) as approveDate2 \n");
	// FUNCTION 생성 : FN_GET_PART_NO_NAME_STR
	sql.append("                     , FN_GET_PART_NO_NAME_STR(classname2, idvalue2, 'NUMBER') AS partNumber2 \n");
	sql.append("                     , FN_GET_PART_NO_NAME_STR(classname2, idvalue2, 'NAME') AS partName2 \n");
	// FUNCTION 생성 : FN_GET_RELATED_ECRECOLINK_STR
	sql.append("                     , FN_GET_RELATED_ECRECOLINK_STR('ECO', classname2, idvalue2) AS relatedECOStr2 \n");

	sql.append("                     , (SELECT nc.name FROM NUMBERCODE nc WHERE nc.codetype = 'PROCESSTYPE' AND nc.code = proc_type) AS procType2 \n");
	// FUNCTION 생성 : FN_GET_CODE_NAME
	sql.append("                     , FN_GET_CODE_NAME_STR('CHANGETYPE', mold_chg_type) AS moldReqChgType2 \n");

	sql.append("                     , TO_NUMBER(SUBSTR(partInfo, INSTR(partInfo, '|', 1, 2) + 1))     partCount                   \n");
	sql.append("                     , orgName                                                         orgName                     \n");
	sql.append("                     , projectOid                                                      projectOid                  \n");
	sql.append("                     , projectNo                                                       projectNo                   \n");
	sql.append("                     , projectName                                                     projectName                 \n");
	sql.append("                     , prodMoldClsName                                                 prodMoldClsName             \n");
	sql.append("                     , dev_yn                                                          dev_yn                      \n");
	sql.append("                     , req_type                                                        req_type                    \n");
	sql.append("                     , req_type_desc                                                   req_type_desc               \n");
	sql.append("                     , mold_chg_type                                                   mold_chg_type               \n");
	sql.append("                     , mold_chg_type_desc                                              mold_chg_type_desc          \n");
	sql.append("                     , vendor_flag                                                     vendor_flag                 \n");
	sql.append("                     , prod_vendor                                                     prod_vendor                 \n");
	sql.append("                     , proc_type                                                       proc_type                   \n");
	sql.append("                     , proc_type_desc                                                  proc_type_desc              \n");
	sql.append("                     , complete_req_date                                               complete_req_date           \n");
	sql.append("                  FROM (                                                                                                                                             \n");

	if (paramMap.getString("prodMoldCls").equals("1") || paramMap.getString("prodMoldCls").equals("2")) {
	    sql.append(getSearchEcrListDetailSQL(isExcel, paramMap.getString("prodMoldCls"), paramMap, conditionList));
	} else {
	    // 제품+금형ECO
	    sql.append(getSearchEcrListDetailSQL(isExcel, "1", paramMap, conditionList));
	    sql.append("UNION ALL \n");
	    sql.append(getSearchEcrListDetailSQL(isExcel, "2", paramMap, conditionList));
	}
	sql.append("                                        )                                                                                                                                             \n");
	sql.append("                ) t      )                                                                                                                                                         \n");

	return sql.toString();
    }

    /**
     * 
     * 함수명 : getSearchEcrListDetailSQL 설명 : 금형 ECR 상세 목록을 조회하는 SQL을 작성한다.
     * 
     * @param ketSearchEcoVO
     *            : 금형 ECO 검색 VO
     * @param ingPage
     *            : page 처리여부(0:미처리 1:page처리)
     * @param isExcel
     *            : 엑셀처리여부(0:미처리 1:엑셀처리)
     * @param prodMoldCls
     *            : 제품/금형구분(1:제품 2:금형)
     * @return String : SQL
     * @throws Exception
     *             작성자 : 남현승 작성일자 : 2013. 8. 5.
     */
    private String getSearchEcrListDetailSQL(boolean isExcel, String prodMoldCls, KETParamMapUtil paramMap,
	    List<Map<String, Object>> conditionList) throws Exception {
	StringBuffer sql = new StringBuffer();
	sql.append("SELECT                                                                          \n");
	sql.append("      T1.ecrId                                               ecrId              \n");
	sql.append("    , T1.ecrName                                             ecrName            \n");
	sql.append("    , ''                                                     partInfo           \n");
	sql.append("    , DECODE(t1.devyn,'D','개발','P','양산')                 dev_yn             \n");
	sql.append("    , T1.deptName                                            orgName            \n");
	sql.append("    , ( SELECT ST2.name                                                         \n");
	sql.append("          FROM WTUser ST1                                                       \n");
	sql.append("             , people ST2                                                       \n");
	sql.append("         WHERE ST1.idA2A2 = T1.idA3B7                                           \n");
	sql.append("           AND ST2.idA3B4 = ST1.idA2A2 )                     creatorName        \n");
	sql.append("    , TO_CHAR(T1.createStampA2, '" + dateFormatString + "')  createDate         \n");
	sql.append("    , T1.classnameA2A2||':'|| T1.idA2A2                      oid                \n");

	sql.append("    , T1.classnameA2A2 AS classname2, T1.idA2A2 AS idvalue2 \n");

	sql.append("    , T1.ecrstateState                                       stateState         \n");
	sql.append("    , ''                                                     partNumberList     \n");
	sql.append("    , T1.projectOid                                          projectOid         \n");

	if (prodMoldCls.equals("1")) {
	    sql.append("    , ( SELECT e.pjtno                                                                                      \n");
	    sql.append("          FROM e3psprojectmaster e                                                                          \n");
	    sql.append("             , productProject p                                                                             \n");
	    sql.append("         WHERE p.ida2a2 = substr(t1.projectoid, instr( t1.projectoid, ':')+1, length(t1.projectoid) )       \n");
	    sql.append("           AND p.ida3b8 = e.ida2a2  )                                             projectNo                 \n");
	    sql.append("    , ( SELECT e.pjtname                                                                                    \n");
	    sql.append("          FROM e3psprojectmaster e                                                                          \n");
	    sql.append("             , productProject p                                                                             \n");
	    sql.append("         WHERE p.ida2a2 = substr(t1.projectoid, instr( t1.projectoid, ':')+1, length(t1.projectoid) )       \n");
	    sql.append("           AND p.ida3b8 = e.ida2a2  )                                             projectName               \n");
	    sql.append("    , ( SELECT c.name                                                                                       \n");
	    sql.append("          FROM numbercode c                                                                                 \n");
	    sql.append("         WHERE c.codetype='PRODCHAGEREASON'                                                                 \n");
	    sql.append("           AND c.code = t1.changereason )                                         req_type                  \n");
	    sql.append("    , t1.otherchangereasondesc                                                    req_type_desc             \n");
	    sql.append("    , ''                                                                          mold_chg_type             \n");
	    sql.append("    , ''                                                                          mold_chg_type_desc        \n");
	    sql.append("    , ''                                                                          vendor_flag               \n");
	    sql.append("    , ''                                                                          prod_vendor               \n");
	    sql.append("    , ''                                                                          proc_type                 \n");
	    sql.append("    , ''                                                                          proc_type_desc            \n");
	    sql.append("    , ''                                                                          complete_req_date         \n");

	    // 설계변경요청사유
	    // sql.append("    , t1.CHANGEREASON AS changereason \n");

	} else {
	    sql.append("    , ( SELECT e.pjtno                                                                                      \n");
	    sql.append("          FROM e3psprojectmaster e                                                                          \n");
	    sql.append("             , moldproject m                                                                                \n");
	    sql.append("         WHERE m.ida2a2 = substr(t1.projectoid, instr( t1.projectoid, ':')+1, length(t1.projectoid) )       \n");
	    sql.append("           AND m.ida3b8 = e.ida2a2  )                                             projectNo                 \n");
	    sql.append("    , ( SELECT e.pjtname                                                                                    \n");
	    sql.append("          FROM e3psprojectmaster e                                                                          \n");
	    sql.append("             , moldproject m                                                                                \n");
	    sql.append("         WHERE m.ida2a2 = substr(t1.projectoid, instr( t1.projectoid, ':')+1, length(t1.projectoid) )       \n");
	    sql.append("            AND m.ida3b8 = e.ida2a2  )                                            projectName               \n");
	    sql.append("    , ( SELECT c.name                                                                                       \n");
	    sql.append("          FROM numbercode c                                                                                 \n");
	    sql.append("         WHERE c.codetype='REQUESTTYPE'                                                                     \n");
	    sql.append("           AND c.code = t1.requesttype )                                          req_type                  \n");
	    sql.append("    , t1.otherreqtype                                                             req_type_desc             \n");
	    sql.append("    , t1.changetype                                                               mold_chg_type             \n");
	    sql.append("    , t1.otherchangedesc                                                          mold_chg_type_desc        \n");
	    sql.append("    , t1.vendorflag                                                               vendor_flag               \n");
	    sql.append("    , t1.prodvendor                                                               prod_vendor               \n");
	    sql.append("    , t1.processtype                                                              proc_type                 \n");
	    sql.append("    , t1.otherprocessdesc                                                         proc_type_desc            \n");
	    sql.append("    , to_char( to_date(t1.completereqdate, 'YYYY-MM-DD' ), 'YYYY-MM-DD' )         complete_req_date         \n");

	    // 설계변경요청사유
	    // sql.append("    , '' AS changereason \n");

	}

	// 금형변경, 원가변동
	if (StringUtil.checkNull(paramMap.getString("HmoldChange")).length() > 0
	        || StringUtil.checkNull(paramMap.getString("HcostChange")).length() > 0) {

	    if ("1".equals(prodMoldCls)) {// 제품ECO

		sql.append("    , '제품' AS prodMoldClsName \n ");
		sql.append(" FROM ( \n ");
		sql.append("       SELECT ecr.* \n ");
		sql.append("         FROM KETProdChangeRequest ecr, \n ");
		sql.append("              KETChangeRequestExpansion expansion \n ");
		sql.append("        WHERE ecr.ida2a2 = expansion.ida3a4 \n ");

		if (StringUtil.checkNull(paramMap.getString("HmoldChange")).length() > 0) {
		    sql.append("          AND ")
			    .append(KETQueryUtil.getSqlQueryForMultiSearch("expansion.MOLDCHANGECODE", paramMap.getString("HmoldChange"),
			            false)).append(" \n");
		}
		if (StringUtil.checkNull(paramMap.getString("HcostChange")).length() > 0) {
		    sql.append("          AND ")
			    .append(KETQueryUtil.getSqlQueryForMultiSearch("expansion.COSTCHANGECODE", paramMap.getString("HcostChange"),
			            false)).append(" \n");
		}

		sql.append("      ) T1 \n ");

	    } else {// 금형ECO
		sql.append("    , '금형' AS prodMoldClsName \n ");
		sql.append(" FROM ( \n ");
		sql.append("       SELECT ecr.* \n ");
		sql.append("         FROM KETMoldChangeRequest ecr, \n ");
		sql.append("              KETChangeRequestExpansion expansion \n ");
		sql.append("        WHERE ecr.ida2a2 = expansion.ida3a4 \n ");

		if (StringUtil.checkNull(paramMap.getString("HmoldChange")).length() > 0) {
		    sql.append("          AND ")
			    .append(KETQueryUtil.getSqlQueryForMultiSearch("expansion.MOLDCHANGECODE", paramMap.getString("HmoldChange"),
			            false)).append(" \n");
		}
		if (StringUtil.checkNull(paramMap.getString("HcostChange")).length() > 0) {
		    sql.append("          AND ")
			    .append(KETQueryUtil.getSqlQueryForMultiSearch("expansion.COSTCHANGECODE", paramMap.getString("HcostChange"),
			            false)).append(" \n");
		}

		sql.append("      ) T1 \n ");

	    }
	}
	//
	else {

	    if ("1".equals(prodMoldCls)) {// 제품ECO
		sql.append("    , '제품' AS prodMoldClsName \n ");
		sql.append(" FROM KETProdChangeRequest T1 \n ");
		/*
	         * sql.append(" FROM ( \n "); sql.append("       SELECT ecr.*, expansion.statestate AS expansion_statestate \n ");
	         * sql.append("         FROM KETProdChangeRequest ecr, \n ");
	         * sql.append("              KETChangeRequestExpansion expansion \n ");
	         * sql.append("        WHERE ecr.ida2a2 = expansion.ida3a4(+) \n "); sql.append("      ) T1 \n ");
	         */
	    } else {// 금형ECO
		sql.append("    , '금형' AS prodMoldClsName \n ");
		sql.append(" FROM KETMoldChangeRequest T1 \n ");
		/*
	         * sql.append(" FROM ( \n "); sql.append("       SELECT ecr.*, expansion.statestate AS expansion_statestate \n ");
	         * sql.append("         FROM KETMoldChangeRequest ecr, \n ");
	         * sql.append("              KETChangeRequestExpansion expansion \n ");
	         * sql.append("        WHERE ecr.ida2a2 = expansion.ida3a4(+) \n "); sql.append("      ) T1 \n ");
	         */
	    }

	}

	/*
	 * ECR 검색 조건중 '사업부구분'이 있는데 로그인한 사용자의 사업부만 보이게 하면 사용자들이 오해할 수 있다. 이에 아래 코드 제거함.
	 */
	/*
	 * WTUser sessionUser = (WTUser) SessionHelper.manager.getPrincipal(); PeopleData pd = new PeopleData(sessionUser); if
	 * ("전자".equals(pd.ParentdepartmentName.substring(0, 2))) { sql.append("WHERE T1.divisionFlag like 'E' \n"); } else {
	 * sql.append("WHERE T1.divisionFlag like 'C' \n"); }
	 */
	sql.append("WHERE 1=1 \n");

	if (StringUtil.checkNull(paramMap.getString("partNo")).length() > 0) {
	    if ("1".equals(prodMoldCls)) {
		sql.append("   AND T1.idA2A2 IN (                                                                 \n");
		sql.append("                     SELECT ST1.idA3B5                                                \n");
		sql.append("                       FROM KETProdECRPartLink ST1                                    \n");
		sql.append("                          , wtpart    p                                               \n");
		sql.append("                          , wtpartmaster    m                                         \n");
		sql.append("                      WHERE 1 = 1                                                     \n");
		sql.append("                        AND st1.ida3a5 = p.ida2a2                                     \n");
		sql.append("                        AND p.ida3masterreference = m.ida2a2                          \n");
		sql.append("                        AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("m.wtpartnumber", paramMap.getString("partNo"), false))
		        .append("        \n");
		sql.append("                     )                                                                \n");
	    } else {
		sql.append("   AND T1.idA2A2 IN (                                                                 \n");
		sql.append("                     SELECT ST1.idA3B5                                                \n");
		sql.append("                       FROM KETMoldECRPartLink ST1                                    \n");
		sql.append("                          , wtpart    p                                               \n");
		sql.append("                          , wtpartmaster    m                                         \n");
		sql.append("                      WHERE 1 = 1                                                     \n");
		sql.append("                        AND st1.ida3a5 = p.ida2a2                                     \n");
		sql.append("                        AND p.ida3masterreference = m.ida2a2                          \n");
		sql.append("                        AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("m.wtpartnumber", paramMap.getString("partNo"), false))
		        .append("        \n");
		sql.append("                     )                                                                \n");

	    }
	}

	if (StringUtil.checkNull(paramMap.getString("partName")).length() > 0) {
	    if ("1".equals(prodMoldCls)) {
		sql.append("   AND T1.idA2A2 IN (                                                                 \n");
		sql.append("                     SELECT ST1.idA3B5                                                \n");
		sql.append("                       FROM KETProdECRPartLink ST1                                    \n");
		sql.append("                          , wtpart    p                                               \n");
		sql.append("                          , wtpartmaster    m                                         \n");
		sql.append("                      WHERE 1 = 1                                                     \n");
		sql.append("                        AND st1.ida3a5 = p.ida2a2                                     \n");
		sql.append("                        AND p.ida3masterreference = m.ida2a2                          \n");
		sql.append("                        AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("m.name", paramMap.getString("partName"), true))
		        .append("        \n");
		sql.append("                     )                                                                \n");
	    } else {
		sql.append("   AND T1.idA2A2 IN (                                                                 \n");
		sql.append("                     SELECT ST1.idA3B5                                                \n");
		sql.append("                       FROM KETMoldECRPartLink ST1                                    \n");
		sql.append("                          , wtpart    p                                               \n");
		sql.append("                          , wtpartmaster    m                                         \n");
		sql.append("                      WHERE 1 = 1                                                     \n");
		sql.append("                        AND st1.ida3a5 = p.ida2a2                                     \n");
		sql.append("                        AND p.ida3masterreference = m.ida2a2                          \n");
		sql.append("                        AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("m.name", paramMap.getString("partName"), true))
		        .append("        \n");
		sql.append("                     )                                                                \n");

	    }
	}

	for (Map<String, Object> condistion : conditionList) {

	    KETParamMapUtil map = KETParamMapUtil.getMap(condistion);

	    // 프로젝트oid
	    if (StringUtil.checkNull(map.getString("projectOid")).length() > 0) {
		sql.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("T1.projectOid", map.getString("projectOid"), false))
		        .append("        \n");
	    }
	    // 부서명
	    if (StringUtil.checkNull(map.getString("orgName")).length() > 0) {
		sql.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("T1.deptName", map.getString("orgName"), true))
		        .append("        \n");
	    }
	    // 작성자OID
	    if (StringUtil.checkNull(map.getString("creatorOid")).length() > 0) {
		sql.append("   AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("T1.idA3B7",
		                KETParamMapUtil.OidToString(map.getString("creatorOid")), false)).append("        \n");
	    }
	    // ECR번호
	    if (StringUtil.checkNull(map.getString("ecrId")).length() > 0) {
		sql.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("T1.ecrId", "ECR-" + map.getString("ecrId"), true))
		        .append("        \n");
		// sql.append("   AND UPPER(T1.ecrId) like  UPPER('ECR-'||'" + KETStringUtil.getLikeString(map.getString("ecrId")) +
		// "')    \n");
	    }
	    // 사업자 구분
	    String division = (String) map.get("HdivisionFlag");
	    if (division != null && division.trim().length() > 0 && !division.equalsIgnoreCase("null")) {
		sql.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("T1.divisionFlag", division, false))
		        .append("        \n");
	    }
	    // 개발양산구분
	    String devYn = (String) map.get("HdevYn");
	    if (devYn != null && devYn.trim().length() > 0 && !devYn.equalsIgnoreCase("null")) {
		sql.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("T1.devYn", devYn, false)).append("        \n");
	    }
	    // ECR제목
	    if (StringUtil.checkNull(map.getString("ecrName")).length() > 0) {
		sql.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("T1.ecrName", map.getString("ecrName"), true))
		        .append("        \n");
	    }
	    // 진행상태
	    String state = (String) map.get("HsancStateFlag");
	    if (state != null && state.trim().length() > 0 && !state.equalsIgnoreCase("null")) {
		sql.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("T1.ecrStateState", state, false)).append("        \n");
		/*
	         * sql.append("   AND ( \n"); sql.append("        ").append(KETQueryUtil.getSqlQueryForMultiSearch("T1.stateState", state,
	         * false)).append("        \n"); sql.append("        OR \n");
	         * sql.append("        ").append(KETQueryUtil.getSqlQueryForMultiSearch("T1.expansion_statestate", state,
	         * false)).append("        \n"); sql.append("       ) \n");
	         */
	    }
	    // 작성일자
	    if (!map.getString("createStartDate").equals("")) {
		String predate = map.getString("createStartDate");
		predate = predate.substring(0, 4) + predate.substring(5, 7) + predate.substring(8, 10);
		sql.append("   AND T1.createStampA2 >= TO_DATE('" + predate + "' || '000000','YYYYMMDDHH24MISS')                 \n");
	    }
	    if (!map.getString("createEndDate").equals("")) {
		String postdate = map.getString("createEndDate");
		postdate = postdate.substring(0, 4) + postdate.substring(5, 7) + postdate.substring(8, 10);
		sql.append("   AND T1.createStampA2 < TO_DATE('" + postdate + "' || '235959','YYYYMMDDHH24MISS')                \n");
	    }

	    // 설계변경요청사유
	    String prodChangeReason = (String) map.get("HprodChangeReason");
	    if (prodChangeReason != null && prodChangeReason.trim().length() > 0 && !prodChangeReason.equalsIgnoreCase("null")) {
		if ("1".equals(prodMoldCls)) {
		    sql.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("T1.changeReason", prodChangeReason, true))
			    .append(" \n");
		} else {
		    // '설계변경요청사유'는 금형 ECR에는 존재하지 않는다. 따라서 검색 조건으로 선택하였다면 금형 ECR은 아무것도 나오지 않게 한다.
		    sql.append("   AND 1 != 1 \n");
		}
	    }

	}
	return sql.toString();
    }

    /**
     * 
     * 함수명 : getSearchMoldEcrDetailSQL 설명 : 금형ECR 상세내역을 조회하는 SQL을 생성한다.
     * 
     * @param ketMoldChangeRequestVO
     *            : 금형 ECR VO
     * @return String : SQL
     * @throws Exception
     *             작성자 : 안수학 작성일자 : 2011. 1. 4.
     */
    private String getSearchMoldEcrDetailSQL(KETMoldChangeRequestVO ketMoldChangeRequestVO) throws Exception {
	StringBuffer sql = new StringBuffer();
	sql.append("SELECT \n");
	sql.append("    T1.ecrId \n");
	sql.append("    , T1.devYn \n");
	sql.append("    , (SELECT ST1.name devYnName FROM NumberCode ST1 WHERE ST1.codeType = 'DEVYN' AND ST1.code = T1.devYn) devYnName \n");
	sql.append("    , T1.ecrName \n");
	sql.append("    , T1.divisionFlag \n");
	sql.append("    , (SELECT ST1.name divisionFlagName FROM NumberCode ST1 WHERE ST1.codeType = 'DIVISIONFLAG' AND ST1.code = T1.divisionFlag) divisionFlagName \n");
	sql.append("    , T1.projectOid \n");
	sql.append("    , T1.deptName orgName \n");
	sql.append("    , (SELECT name \n");
	sql.append("    FROM TeamTemplate ST1 \n");
	sql.append("    WHERE ST1.idA2A2 = T1.idA3teamTemplateId) teamName \n");
	sql.append("    , (SELECT ST2.name \n");
	sql.append("    FROM WTUser ST1 \n");
	sql.append("        , people ST2 \n");
	sql.append("    WHERE ST1.idA2A2 = T1.idA3B7 \n");
	sql.append("    AND ST2.idA3B4 = ST1.idA2A2) creatorName \n");
	sql.append("    , TO_CHAR(T1.createStampA2, '" + dateFormatString + "') createDate \n");
	sql.append("    , TO_CHAR(T1.updateStampA2, '" + dateFormatString + "') updateDate \n");
	sql.append("    , T1.stateState \n");// 14
	sql.append("    , T1.prodVendor \n");
	sql.append("    , T1.changeType \n");
	sql.append("    , T1.otherchangedesc \n");
	sql.append("    , T1.completeReqDate \n");
	sql.append("    , T1.processType \n");
	sql.append("    , (SELECT ST1.name processTypeName FROM NumberCode ST1 WHERE ST1.codeType = 'PROCESSTYPE' AND ST1.code = T1.processType) processTypeName \n");
	sql.append("    , T1.otherprocessdesc \n");
	sql.append("    , T1.requestType \n");
	sql.append("    , (SELECT ST1.name requestTypeName FROM NumberCode ST1 WHERE ST1.codeType = 'REQUESTTYPE' AND ST1.code = T1.requestType) requestTypeName \n");
	sql.append("    , T1.otherreqtype \n");
	sql.append("    , T1.classnameA2A2||':'|| T1.idA2A2 oid \n");
	sql.append("    , T1.idA2A2 \n");
	sql.append("FROM KETMoldChangeRequest T1 \n");
	sql.append("WHERE 1 = 1 \n");
	sql.append("AND T1.idA2A2 = " + ketMoldChangeRequestVO.getOid() + " \n");
	EcmUtil.logging("\nMoldChangeRequestDao.getSearchMoldEcrDetailSQL():\n" + sql.toString());
	return sql.toString();
    }

    /**
     * 
     * 함수명 : getSearchRelPartListSQL 설명 : 금형ECR 관련부품 목록을 조회하는 SQL을 생성한다.
     * 
     * @param moldEcrOid
     *            : 금형ECR OID
     * @return String : SQL
     * @throws Exception
     *             작성자 : 안수학 작성일자 : 2011. 1. 4.
     */
    private String getSearchRelPartListSQL(String moldEcrOid) throws Exception {
	StringBuffer sql = new StringBuffer();
	sql.append("SELECT T3.WTPartNumber partNumber \n");
	sql.append("    , T3.name partName \n");
	sql.append("    , T2.versionIdA2versionInfo partVersion \n");
	sql.append("    , T1.changeReqComment \n");
	sql.append("    , T2.classnameA2A2||':'||T2.ida2a2 partOid \n");
	sql.append("    , T1.classnameA2A2||':'||T1.ida2a2 relPartLinkOid \n");
	sql.append("FROM KETMoldECRPartLink T1 \n");
	sql.append("    , WTPart T2 \n");
	sql.append("    , WTPartMaster T3 \n");
	sql.append("WHERE 1 = 1 \n");
	sql.append("AND T1.idA3B5 = " + moldEcrOid + " \n");
	sql.append("AND T2.idA2A2 = T1.idA3A5 \n");
	sql.append("AND T3.idA2A2 = T2.idA3masterReference \n");
	EcmUtil.logging("\n\nMoldChangeRequestDao.getSearchRelPartListSQL():\n" + sql.toString());
	return sql.toString();
    }

    /**
     * 
     * 함수명 : getSearchRelIssueListSQL 설명 : 금형ECR 관련이슈 목록을 조회하는 SQL을 생성한다.
     * 
     * @param moldEcrOid
     *            : 금형ECR OID
     * @return String : SQL
     * @throws Exception
     *             작성자 : 안수학 작성일자 : 2011. 1. 4.
     */
    private String getSearchRelIssueListSQL(String moldEcrOid) throws Exception {
	StringBuffer sql = new StringBuffer();
	sql.append("SELECT T1.classnameA2A2||':'|| T1.idA2A2 relIssueLinkOid \n");
	sql.append("    , T2.classnameA2A2||':'|| T2.idA2A2 projectIssueOid \n");
	sql.append("    , T2.subject \n");
	sql.append("    , (SELECT ST2.name \n");
	sql.append("    FROM WTUser ST1 \n");
	sql.append("        , people ST2 \n");
	sql.append("    WHERE ST1.idA2A2 = T2.idA3owner \n");
	sql.append("    AND ST2.idA3B4 = ST1.idA2A2) creatorName \n");
	sql.append("    , TO_CHAR(T2.createStampA2, '" + dateFormatString + "') createDate \n");
	sql.append("FROM KETMoldECRIssueLink T1 \n");
	sql.append("    , ProjectIssue T2 \n");
	sql.append("WHERE 1 = 1 \n");
	sql.append("AND T1.idA3A5 = T2.idA2A2 \n");
	sql.append("AND T1.idA3B5 = " + moldEcrOid + " \n");
	EcmUtil.logging("\n\nMoldChangeRequestDao.getSearchRelIssueListSQL():\n" + sql.toString());
	return sql.toString();
    }

    /**
     * 함수명 : existKETMoldChangeLink 설명 : 금형ECO에 관련된 금형ECR 존재여부를 확인한다.
     * 
     * @param moldEcrObj
     *            - 금형ECR 객체
     * @return existYn - 금형ECO에 관련된 금형ECR 존재여부 작성자 : 안수학 작성일자 : 2011. 01. 03.
     */
    public boolean existKETMoldChangeLink(Persistable moldEcrObj) {
	QueryResult qr = null;
	boolean existYn = false;

	try {
	    qr = PersistenceHelper.manager.navigate(moldEcrObj, "moldECO", KETMoldChangeLink.class);
	    if (qr.hasMoreElements()) {
		existYn = true;
	    }
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}

	return existYn;
    }

    public String getCodeNames(String codeType, String codes) throws Exception {
	String codeName = "";
	if ("".equals(codes)) {
	    return codeName;
	}
	StringBuffer sql = new StringBuffer();
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    con = PlmDBUtil.getConnection();
	    sql.append("SELECT code, name \n");
	    sql.append("FROM NumberCode \n");
	    sql.append("WHERE 1 = 1 \n");
	    sql.append("AND codeType = ? \n");
	    sql.append("AND code IN (? \n");

	    int max = 20;
	    int i = 0;
	    for (i = 0; i < max; i++) {
		sql.append(", ?");
	    }
	    sql.append("\n) \n");
	    sql.append("ORDER BY code \n");

	    pstmt = con.prepareStatement(sql.toString());
	    int pstmtCnt = 1;
	    pstmt.setString(pstmtCnt++, codeType);
	    ArrayList arrCode = KETStringUtil.getToken(codes, "|");
	    int size = arrCode.size();
	    for (i = 0; i < size; i++) {
		pstmt.setString(pstmtCnt++, (String) arrCode.get(i));
	    }
	    for (int j = i; j <= max; j++) {
		pstmt.setString(pstmtCnt++, "0");
	    }

	    rs = pstmt.executeQuery();
	    if (rs.next()) {
		codeName = rs.getString("name");
	    }
	    while (rs.next()) {
		codeName += "/ " + rs.getString("name");
	    }
	} catch (Exception e) {
	    throw new Exception(e);
	} finally {
	    sql.delete(0, sql.length());
	    if (rs != null)
		rs.close();
	    if (pstmt != null)
		pstmt.close();
	    PlmDBUtil.close(con);
	}
	return codeName;
    }

    /**
     * ECN 코드를 채번한다.
     * 
     * @return
     * @throws Exception
     * @메소드명 : getEcnNumber
     * @작성자 : kimtaehyun
     * @작성일 : 2014. 9. 16.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public String getEcnNumber() throws Exception {
	String ecnNumber = "";

	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    con = PlmDBUtil.getConnection();

	    StringBuffer sql = new StringBuffer();
	    sql.append("SELECT FN_GET_ECM_NUMBERING(?) FROM DUAL \n");
	    pstmt = con.prepareStatement(sql.toString());
	    pstmt.setString(1, "ECN");

	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		ecnNumber = rs.getString(1);
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw new Exception(e);
	} finally {
	    if (rs != null)
		rs.close();
	    if (pstmt != null)
		pstmt.close();
	    PlmDBUtil.close(con);
	}

	return ecnNumber;
    }
}
