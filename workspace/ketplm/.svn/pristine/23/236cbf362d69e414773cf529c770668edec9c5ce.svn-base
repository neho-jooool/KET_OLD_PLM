package ext.ket.edm.migration.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import wt.epm.EPMDocument;
import wt.epm.EPMDocumentMaster;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.iba.definition.StringDefinition;
import wt.iba.value.IBAHolder;
import wt.iba.value.StringValue;
import wt.lifecycle.State;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.services.StandardManager;
import wt.util.WTException;
import e3ps.bom.common.iba.IBAUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import e3ps.ecm.entity.KETMoldChangeOrder;
import e3ps.ecm.entity.KETProdChangeOrder;
import e3ps.edm.EPMLink;
import e3ps.edm.beans.EDMHelper;
import ext.ket.edm.approval.KetEpmApprovalHelper;
import ext.ket.edm.approval.internal.EpmApprovalHandler;
import ext.ket.edm.approval.internal.EpmApprovalHistoryHandler;
import ext.ket.edm.entity.KETEpmApprovalHis;
import ext.ket.edm.entity.KETEpmApprovalHisLink;
import ext.ket.edm.migration.revision.EpmRevisionDTO;
import ext.ket.part.base.service.internal.VieawbleGenerator;
import ext.ket.part.base.service.internal.associate.PartEpmRelation;
import ext.ket.part.entity.KETPartClassification;
import ext.ket.part.migration.dao.StampDaoFactory;
import ext.ket.part.migration.dao.StampDaoManager;
import ext.ket.part.migration.dao.StampRowSetStrategy;
import ext.ket.part.migration.erp.service.MigLogUtil;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.query.QueryFactory;
import ext.ket.shared.query.SimpleQuerySpec;

public class StandardKetMigEpmService extends StandardManager implements KetMigEpmService {
    
    private static final long serialVersionUID = 1L;
    private MigLogUtil migLogUtil = new MigLogUtil();
    private SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
    private Map<String, KETPartClassification> clazMap = new HashMap<String, KETPartClassification>();

    public static StandardKetMigEpmService newStandardKetMigEpmService() throws WTException {
	StandardKetMigEpmService instance = new StandardKetMigEpmService();
	instance.initialize();
	return instance;
    }
    
    //////////////////////////////////////////////////////////////////////
    //
    // 도면의 양산 리비전 처리
    // 
    //////////////////////////////////////////////////////////////////////

    public void migEpmRevision(String prefix, String arg2) throws Exception{
	
	try {
	    
	    // String[] seperEpmNos = new String[]{ "EM420151"
	    // "0"
	    // ,"10","11","12","13","14","15","16","17","18","19"
	    // ,"20","21","22","23","24","25","26","27","28","29"
	    // ,"3","4","5","6","7","8","9"
	    // ,"A","B","C","D","E","F","G","H","I","J","K","L","M","N"
	    // ,"O","P","Q","R","S","T","U","V","W","X","Y","Z"
	    // };

	    StringDefinition stringDefinition = getStringDefinition(EDMHelper.IBA_MANUFACTURING_VERSION); // "ManufacturingVersion"
	    StringDefinition stringDevDefinition = getStringDefinition(EDMHelper.IBA_DEV_STAGE);

	    // 마스터 리스트
	    // TODO TKLEE 차후 수정 필요
	    boolean include = false;
	    if("Y".equals(arg2)){
		include = true;
	    }
	    List<EpmRevisionDTO> prefixPartList = getPartAllInfo(prefix, include);
//	    List<EpmRevisionDTO> prefixPartList = getSpecailPartAllInfo(prefix);

	    for (EpmRevisionDTO oneMast : prefixPartList) {

		// 전체 Iteration 리스트
		List<EpmRevisionDTO> detailMastList = getPartDetailInfo(oneMast.getMastId());

		// 도면 하나당 양산 리비전 처리
		if (detailMastList != null) {
		    updateEpmRevision(detailMastList, stringDefinition, stringDevDefinition);
		}
	    }
	
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}
	
    }
    
    // 도면 정보 업데이트 - 도면 하나당 양산 리비전 처리
    public void updateEpmRevision(List<EpmRevisionDTO> detailMastList, StringDefinition stringDefinition
	    , StringDefinition stringDevDefinition) throws Exception {
	
	List<List<EpmRevisionDTO>> allRevList = new ArrayList<List<EpmRevisionDTO>>();
	
	List<EpmRevisionDTO> oneRevList = null;
	
	long oldRevId = 0;
	
	// 1. 도면을 리비전별로 나눈다.
	for( int k = 0; k < detailMastList.size(); k++ ){
	    
	    EpmRevisionDTO dto = detailMastList.get(k);
	    
	    long revId = dto.getRevId();
	    
	    if(k==0){
		oldRevId = revId;
		oneRevList = new ArrayList<EpmRevisionDTO>();
	    }
	    
	    if(revId == oldRevId){
		oneRevList.add(dto);
	    }else{
		allRevList.add(oneRevList); // new 
		oneRevList = new ArrayList<EpmRevisionDTO>();
		oneRevList.add(dto);
	    }
	    
	    if(k == detailMastList.size() -1 ){
		allRevList.add(oneRevList); // new end
	    }
	    
	    // oldRev Change
	    oldRevId = revId;
	}
	
	// 각 Revision의 최신Iteration에서 개발단계가 있는지 체크한다.
	boolean hasDevStage = false;
	List<String> devStageList = new ArrayList<String>();
	EPMDocument logEpmDoc = null;
	String logDevStage = null;
	for( List<EpmRevisionDTO> revOneList : allRevList){
	    for(int k = 0; k < 1; k++ ){
		EpmRevisionDTO dto = revOneList.get(k);
		String iterationOid = dto.getIterOid();
		EPMDocument epmDoc = (EPMDocument)CommonUtil.getObject(iterationOid);
		logEpmDoc = epmDoc;
		String stringValueOid = getExistStringValue(epmDoc, EDMHelper.IBA_DEV_STAGE);
		if (stringValueOid != null) {
		    StringValue sv = (StringValue) CommonUtil.getObject(stringValueOid);
		    String devStageName = StringUtil.checkNull(sv.getValue());
		    logDevStage = devStageName;
		    if(devStageName.indexOf("단계")!= -1){
			hasDevStage = true;
		    }
		    devStageList.add(devStageName);
		}else{
		    String devStageName = "";
		    logDevStage = devStageName;
		    devStageList.add(devStageName);
		}
	    }
	}
	
	// 금형도면인지 아닌지 체크한다.
	String isMoldSide = "";
	for( List<EpmRevisionDTO> revOneList : allRevList){
	    for(int k = 0; k < 1; k++ ){
		EpmRevisionDTO dto = revOneList.get(k);
		String iterationOid = dto.getIterOid();
		EPMDocument epmDoc = (EPMDocument)CommonUtil.getObject(iterationOid);
		String stringValueOid = getExistStringValue(epmDoc, EDMHelper.IBA_CAD_MANAGE_TYPE);
		if (stringValueOid != null) {
		    StringValue sv = (StringValue) CommonUtil.getObject(stringValueOid);
		    String cadManagedTypeName = StringUtil.checkNull(sv.getValue());
		    if(cadManagedTypeName == null){
		    }else if(cadManagedTypeName.indexOf("금형") != -1){
			isMoldSide = "Y";
			break;
		    }else if(cadManagedTypeName.indexOf("제품") != -1){
			isMoldSide = "N";
			break;
		    }
		}else{

		}
	    }
	}
	
	if("".equals(isMoldSide)){
	    for( List<EpmRevisionDTO> revOneList : allRevList){
		for (EpmRevisionDTO dto : revOneList) {
		    String iterationOid = dto.getIterOid();
		    EPMDocument epmDoc = (EPMDocument) CommonUtil.getObject(iterationOid);
		    String number = epmDoc.getNumber();
		    if(number.startsWith("1") || number.startsWith("2")|| number.startsWith("3")|| number.startsWith("4")){
			isMoldSide = "Y";
			break;
		    }else if(number.startsWith("CK") || number.startsWith("CP") || number.startsWith("WH") ||
			    number.startsWith("EM") || number.startsWith("JB") || number.startsWith("MG") ||
			    number.startsWith("ST") || number.startsWith("PG") || number.startsWith("K") ||
			    number.startsWith("R") || number.startsWith("0") || number.startsWith("A") 
			    ){
			isMoldSide = "N";
			break;
		    }
		}
	    }
	}
	
	if("Y".equals(isMoldSide)){
	    // 금형은 EPM과 동일한 Rev을 넣어준다.
	    for( List<EpmRevisionDTO> revOneList : allRevList){
		for (EpmRevisionDTO dto : revOneList) {
		    String iterationOid = dto.getIterOid();
		    EPMDocument epmDoc = (EPMDocument) CommonUtil.getObject(iterationOid);
		    updateIBAValue(epmDoc, EDMHelper.IBA_MANUFACTURING_VERSION, epmDoc.getVersionIdentifier().getValue(), stringDefinition); // "ManufacturingVersion"
		}
	    }
	    
	    return;
	    
	}else{
	    
	    if(!hasDevStage){
		
		String cadFileName = "";
		String cadType = "";
		if(logEpmDoc == null){
		    
		}else{
		    cadFileName = logEpmDoc.getCADName();
		    cadType = logEpmDoc.getAuthoringApplication().toString();
		}
		
		if("".equals(isMoldSide)){
		    
		    migLogUtil.log("EPM_Revision", logEpmDoc, "DevStageNMold", "개발단계 및 금형 제품 구분 부재", logDevStage + "[" + cadType +"][" + cadFileName + "]", (logEpmDoc==null?null:logEpmDoc.getNumber()));
		    return;
		    
		}else{
		    
		    String logShort = StringUtil.checkNull(logDevStage) + " : " + StringUtil.checkNull(isMoldSide);
		    migLogUtil.log("EPM_Revision", logEpmDoc, "DevStage", "개발단계 부재", logShort + "[" + cadType +"][" + cadFileName + "]", (logEpmDoc==null?null:logEpmDoc.getNumber()));
		    return;
		}
		
	    }else if("".equals(isMoldSide)){
		migLogUtil.log("EPM_Revision", logEpmDoc, "IS_PROD_OR_MOLD", "금형 제품 구분 부재",  isMoldSide, (logEpmDoc==null?null:logEpmDoc.getNumber()));
		return;
	    }
	}
	
	// 도면을 리비전 별로 처리한다.
	int developLevel = -1;
	int manufLevel = -1;
	String revision = "";
	boolean startedManuf = false;
	for( int i=0; i < allRevList.size(); i++){
	    
	    List<EpmRevisionDTO> revOneList = allRevList.get(i);
	    
	    // 개발단계인지 양산단계인지 체크
	    int checkRet = 0;
	    for(int k = 0; k < revOneList.size(); k++ ){
		
		EpmRevisionDTO dto = revOneList.get(k);
		
		if(k == 0){
		    // 현 리비전의 최신 이터레이션의 '개발단계' 확인
		    if(startedManuf){
			manufLevel = manufLevel + 1;
			revision = String.valueOf(manufLevel);
		    }else{
			
			String devStageName = devStageList.get(i);
			checkRet = checkDevelopLevel(devStageName);
			if (checkRet > 0) { // 양산단계
			    startedManuf = true;
			    manufLevel = manufLevel + 1;
			    revision = String.valueOf(manufLevel);
			} else { // 개발단계
			    developLevel = developLevel + 1;
			    revision = String.valueOf(developLevel);
			    if (revision.length() == 1) {
				revision = "D0" + String.valueOf(developLevel);
			    } else if (revision.length() == 2) {
				revision = "D" + String.valueOf(developLevel);
			    } else {
				throw new Exception("리비전 3자리 이상입니다.");
			    }
			}
		    }
		}
		
		// 해당하는 것 처리
		String iterationOid = dto.getIterOid();
		EPMDocument epmDoc = (EPMDocument)CommonUtil.getObject(iterationOid);
		updateIBAValue(epmDoc, EDMHelper.IBA_MANUFACTURING_VERSION, revision, stringDefinition); // "ManufacturingVersion"
		
	    }
	}
    }
    
    private void updateIBAValue(EPMDocument epmDocument, String ibaDefType, String value, StringDefinition stringDefinition) throws Exception {

	if(StringUtils.isEmpty(value)) return;
	
	String stringValueOid = getExistStringValue(epmDocument, ibaDefType);

	if (stringValueOid != null) {

	    StringValue sv = (StringValue) CommonUtil.getObject(stringValueOid);
	    if (value.equals(sv.getValue())) {

	    } else {
	        sv.setValue(value);
		PersistenceServerHelper.manager.update(sv);
	    }

	} else {

	    StringValue sv = StringValue.newStringValue(stringDefinition, (IBAHolder) epmDocument, value);
	    PersistenceServerHelper.manager.insert(sv);
	}
    }

    private StringDefinition getStringDefinition(String attrName) throws WTException {
    
	QuerySpec select = new QuerySpec();
	int idx = select.addClassList(StringDefinition.class, true);
	select.appendWhere(new SearchCondition(StringDefinition.class, "name", "=", attrName), new int[] { idx });
	QueryResult re = PersistenceHelper.manager.find(select);
	while (re.hasMoreElements()) {
	    Object[] obj = (Object[]) re.nextElement();
	    StringDefinition sv = (StringDefinition) obj[0];
	    return sv;
	}
	
	return null;
    }

    private String getExistStringValue(EPMDocument epmDocument, String ibaDefType) throws Exception {

	StampDaoManager oDaoManager = StampDaoFactory.getInstance().getDaoManager();
	List aBindList = new ArrayList();

	StringBuffer buffer = new StringBuffer();
	buffer.append(" SELECT CLASSNAMEA2A2||':'||IDA2A2  OID \n");
	buffer.append(" FROM STRINGVALUE \n");
	buffer.append(" WHERE 1 = 1 \n");
	buffer.append(" AND IDA3A4 = ? \n");
	buffer.append(" AND IDA3A6 IN (SELECT IDA2A2 FROM StringDefinition WHERE NAME = ? ) \n");
	// NAME = 'DevStage' OR NAME = 'CADCategory' OR NAME = ''

	aBindList.add(CommonUtil.getOIDLongValue(epmDocument));
	aBindList.add(ibaDefType);

	List<String> stringValueList = oDaoManager.queryForList(buffer.toString(), aBindList, new StampRowSetStrategy<String>() {

	    @Override
	    public String mapRow(ResultSet rs) throws SQLException {

		return rs.getString("OID");
	    }
	});

	if (stringValueList.size() == 0) {
	    return null;
	} else if (stringValueList.size() == 1) {
	    return stringValueList.get(0);
	} else {
	    
	    Kogger.debug(getClass(), "IBA Value 2개 이상");
	    
	    for(int k=stringValueList.size()-1; k>0; k--){
		StringValue sv = (StringValue) CommonUtil.getObject(stringValueList.get(k));
		PersistenceHelper.manager.delete(sv);
	    }
	    
	    return stringValueList.get(0);
	}
    }
    
    private int checkDevelopLevel(String devStageName) throws Exception {
	
	 if ("양산단계".equals(devStageName)) {
	     return 1;
	
	 }else if(devStageName != null && devStageName.indexOf("단계") != -1){
	     return 0;
	 }else{
	     return -1;
	 }
    }
    
    private int checkDevelopLevel(long iterId) throws Exception {
	
	int ret = 0; // 개발단계 old
	List<String> list = getDevLevel(iterId, EDMHelper.IBA_DEV_STAGE); // "DevStage"
	
	if(list == null || list.size() == 0){
	    ret = -1; // 개발단계 new
	}else{
	    for (String devLevel : list) {
		if("양산단계".equals(devLevel)){
		    ret = 1;
		    break;
		}
	    }
	}
	
	return ret;
    }
    
    //setStateCheckState
    private List<String> getDevLevel(long iterId, String ibaName) throws Exception {
	
	List<String> list = null;
	StampDaoManager oDaoManager = StampDaoFactory.getInstance().getDaoManager();
	
	try {
	    
	    List aBind = new ArrayList();
	    
	    StringBuffer buffer = new StringBuffer();
	    buffer.append(" SELECT VALUE \n");
	    buffer.append(" FROM STRINGVALUE \n");
	    buffer.append(" WHERE IDA3A4 = ? \n");
	    buffer.append(" AND IDA3A6 IN (SELECT IDA2A2 FROM STRINGDEFINITION WHERE NAME = ? ) \n");
	    
	    aBind.add(iterId);
	    aBind.add(ibaName);
	    
	    String sSql = buffer.toString();
	    list = oDaoManager.queryForList(sSql, aBind, new StampRowSetStrategy<String>() {
		
		@Override
		public String mapRow(ResultSet rs) throws SQLException {
		    
		    String ret = rs.getString("VALUE");
		    return ret;
		}
		
	    });
	    
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}
	
	return list;
	
    }
    
    // 마스터의 상세 도면 리스트
    private List<EpmRevisionDTO> getPartDetailInfo(long mid) throws Exception {

	List<EpmRevisionDTO> list = null;
	StampDaoManager oDaoManager = StampDaoFactory.getInstance().getDaoManager();

	try {

	    List aBind = new ArrayList();

	    StringBuffer buffer = new StringBuffer();
	    buffer.append(" SELECT DOCUMENTNUMBER DOC_NO, M.NAME \n");
	    buffer.append("      , E.VERSIONIDA2VERSIONINFO VER_NO \n");
	    buffer.append("      , E.VERSIONSORTIDA2VERSIONINFO VER_SORT_NO \n");
	    buffer.append("      , E.ITERATIONIDA2ITERATIONINFO ITER_NO \n");
	    buffer.append("      , M.IDA2A2 MID \n");
	    buffer.append("      , E.STATECHECKOUTINFO STATECHECKOUTINFO \n");
	    buffer.append("      , M.CLASSNAMEA2A2||':'||M.IDA2A2 MOID \n");
	    buffer.append("      , E.BRANCHIDITERATIONINFO VID \n");
	    buffer.append("      , 'VR:'||E.CLASSNAMEA2A2||':'||E.BRANCHIDITERATIONINFO VOID \n");
	    buffer.append("      , E.IDA2A2 EID \n");
	    buffer.append("      , E.CLASSNAMEA2A2||':'||E.IDA2A2 EOID \n");
	    buffer.append(" FROM EPMDOCUMENTMASTER M, EPMDOCUMENT E \n");
	    buffer.append(" WHERE 1 = 1 \n");
	    buffer.append(" AND M.IDA2A2 = ? \n");
	    buffer.append(" AND M.IDA2A2 = E.IDA3MASTERREFERENCE \n");
	    buffer.append(" ORDER BY M.IDA2A2, E.VERSIONSORTIDA2VERSIONINFO , TO_NUMBER(E.ITERATIONIDA2ITERATIONINFO) DESC \n");

	    aBind.add(mid);
	    
	    String sSql = buffer.toString();
	    list = oDaoManager.queryForList(sSql, aBind, new StampRowSetStrategy<EpmRevisionDTO>() {
		
		@Override
                public EpmRevisionDTO mapRow(ResultSet rs) throws SQLException {
		    
	            EpmRevisionDTO dto = new EpmRevisionDTO();
	            
	            dto.setEpmNo(rs.getString("DOC_NO"));
	            dto.setEpmName(rs.getString("NAME"));
	            
	            dto.setVerNo(rs.getString("VER_NO"));
	            dto.setVerSortNo(rs.getString("VER_SORT_NO"));
	            
	            dto.setIterNo(rs.getString("ITER_NO"));
	            
	            dto.setStateCheckState(rs.getString("STATECHECKOUTINFO"));
	            
	            dto.setMastOid(rs.getString("MOID"));
	            dto.setIterOid(rs.getString("EOID"));
	            dto.setRevOid(rs.getString("VOID"));
	            
	            dto.setMastId(rs.getLong("MID"));
	            dto.setRevId(rs.getLong("VID"));
	            dto.setIterId(rs.getLong("EID"));
	            
	            return dto;
                }
		
	    });

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}
	
	return list;
    }
    
    // prefix로 시작되는 모든 도면 리스트
    private List<EpmRevisionDTO> getPartAllInfo(String prefix, boolean include) throws Exception {
	
	List<EpmRevisionDTO> list = null;
	StampDaoManager oDaoManager = StampDaoFactory.getInstance().getDaoManager();
	
	try {
	    
	    StringBuffer buffer = new StringBuffer();
	    buffer.append(" SELECT M.IDA2A2 MID \n");
	    buffer.append(" FROM EPMDOCUMENTMASTER M \n");
	    buffer.append(" WHERE 1 = 1 \n");
	    buffer.append(" AND M.DOCUMENTNUMBER LIKE '" + prefix + "%' \n");
	    if(include){
		buffer.append(" AND M.DOCUMENTNUMBER IN \n");
		buffer.append(" ( \n");
		buffer.append("  select DISTINCT MIG_SRC_NO \n");
		buffer.append("  from MIG_LOG2 \n");
		buffer.append("  WHERE MIG_TYPE = 'EPM_Approval' \n");
		buffer.append(" ) \n");
	    }
	    buffer.append(" ORDER BY M.DOCUMENTNUMBER \n");
	    
	    String sSql = buffer.toString();
	    list = oDaoManager.queryForList(sSql, new StampRowSetStrategy<EpmRevisionDTO>() {
		
		@Override
		public EpmRevisionDTO mapRow(ResultSet rs) throws SQLException {
		    
		    EpmRevisionDTO dto = new EpmRevisionDTO();
		    
		    dto.setMastId(rs.getLong("MID"));
		    
		    return dto;
		}
		
	    });
	    
	    
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}
	
	return list;
    }
    
    // prefix로 시작되는 모든 도면 리스트
    private List<EpmRevisionDTO> getSpecailPartAllInfo(String prefix) throws Exception {
	
	List<EpmRevisionDTO> list = null;
	StampDaoManager oDaoManager = StampDaoFactory.getInstance().getDaoManager();
	
	try {
	    
	    StringBuffer buffer = new StringBuffer();
	    buffer.append(" SELECT M.IDA2A2 MID \n");
	    buffer.append(" FROM EPMDOCUMENTMASTER M \n");
	    buffer.append(" WHERE 1 = 1 \n");
	    buffer.append(" AND M.DOCUMENTNUMBER IN \n");
	    buffer.append(" (select distinct MIG_SRC_NO as PartNo \n");
	    buffer.append(" from( \n");
	    buffer.append(" select MIG_SRC_NO \n");
	    buffer.append(" from mig_log \n");
	    buffer.append(" where mig_type = 'EPM_Revision' \n");
	    buffer.append(" and mig_src_log_code = 'DevStage' \n");
	    buffer.append(" union \n");
	    buffer.append(" select MIG_SRC_NO \n");
	    buffer.append(" from mig_log \n");
	    buffer.append(" where mig_type = 'EPM_Revision' \n");
	    buffer.append(" and mig_src_log_code = 'DevStageNMold' \n");
	    buffer.append(" )) \n");
	    buffer.append(" ORDER BY M.DOCUMENTNUMBER \n");
	    
	    String sSql = buffer.toString();
	    list = oDaoManager.queryForList(sSql, new StampRowSetStrategy<EpmRevisionDTO>() {
		
		@Override
		public EpmRevisionDTO mapRow(ResultSet rs) throws SQLException {
		    
		    EpmRevisionDTO dto = new EpmRevisionDTO();
		    
		    dto.setMastId(rs.getLong("MID"));
		    
		    return dto;
		}
		
	    });
	    
	    
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}
	
	return list;
    }
    
    //##################################################################################
    ////////////////////////////////////////////////////////////////////////////////////
    // 도면 결재 정보 마이그레이션
    ////////////////////////////////////////////////////////////////////////////////////
    //##################################################################################
    @Override
    public void migEpmApproval(String prefix, String arg2) throws Exception{
	
	try {
	    
	    // String[] seperEpmNos = new String[]{ "EM420151"
	    // "0"
	    // ,"10","11","12","13","14","15","16","17","18","19"
	    // ,"20","21","22","23","24","25","26","27","28","29"
	    // ,"3","4","5","6","7","8","9"
	    // ,"A","B","C","D","E","F","G","H","I","J","K","L","M","N"
	    // ,"O","P","Q","R","S","T","U","V","W","X","Y","Z"
	    // };

	    boolean include = "Y".equals(arg2);
	    
	    List<EpmRevisionDTO> prefixPartList = getPartAllInfo(prefix, include);

	    for (EpmRevisionDTO oneMast : prefixPartList) {

		// 리비전 리스트
		List<EpmRevisionDTO> detailMastList = getPartDetailRevInfo(oneMast.getMastId());

		// 도면 결재 처리
		if (detailMastList != null) {
		    if(include){
			updateEpmApprovalInfoInclude(detailMastList);
		    }else{
			updateEpmApprovalInfo(detailMastList);
		    }
		}
	    }
	
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}
    }
    
    // 도면 정보 업데이트 - 도면 결재 처리
    public void updateEpmApprovalInfoInclude(List<EpmRevisionDTO> detailMastList) throws Exception {
	
	// 도면을 리비전 별로 결재를 처리한다.
	for( int i=0; i < detailMastList.size(); i++){
	    
	    // 최초 리비전
	    EpmRevisionDTO dto = detailMastList.get(i);
	    String iterationOid = dto.getIterOid();
	    EPMDocument epmDoc = (EPMDocument)CommonUtil.getObject(iterationOid);
	    
	    // 승인된 것만 처리한다.
	    if(epmDoc.getLifeCycleState() != State.toState("APPROVED")){
		continue;
	    }
	    
	    Persistable approvalPers = KetEpmApprovalHelper.service.getSavedPbo(epmDoc);
	    if(approvalPers != null) continue;
	    
	    // 승인된 것의 결재정보를 가져온다.
	    // 1. PBO를 찾는다. : pbo object type, pbo object OID, ECO NO
//	    WTObject pbo = KETCommonHelper.service.getPBO( epmDoc );
	    EpmApprovalHandler handler = new EpmApprovalHandler();
	    handler.getApprovalPBO(epmDoc, true);
	    
	    String approvalType = handler.getType();
	    Persistable pbo = handler.getPbo();
	    
	    String pboOid = "";
	    String ecoNo = "";
	    Timestamp requestDate = null;
	    Timestamp approvalDate = null;
	    String approverId = "";
	    String approverName = "";
	    String delegater = "";
	    String approvalMastOid = "";
	    if(pbo == null) {
		
		approvalType = "Admin";
		requestDate = epmDoc.getCreateTimestamp();
		approvalDate = epmDoc.getCreateTimestamp();
		approverId = "wcadmin";
		approverName = "관리자";
		
	    }else{
	    
		pboOid = CommonUtil.getOIDString(pbo);
		
		if (pbo instanceof KETProdChangeOrder) {
		    ecoNo = ((KETProdChangeOrder) pbo).getEcoId();
		} else if (pbo instanceof KETMoldChangeOrder) {
		    ecoNo = ((KETMoldChangeOrder) pbo).getEcoId();
		}

		// 2. 결재정보를 가져온다. : 결재 승인자ID, 승인자명, 대리자, 요청일, 완료일, 결재Master Oid
		EpmApprovalHistoryHandler history = new EpmApprovalHistoryHandler();
		history.getApprovalHistory(pbo);

		if (history.getApprovalDate() == null || history.getApproverName() == null) {
		    
		    approvalType = "Admin";
		    requestDate = epmDoc.getCreateTimestamp();
		    approvalDate = epmDoc.getCreateTimestamp();
		    approverId = "wcadmin";
		    approverName = "관리자";
		}else{
		    requestDate = history.getRequestDate();
		    approvalDate = history.getApprovalDate();
		    approverId = history.getApproverId();
		    approverName = history.getApproverName();
		    delegater = history.getApproverDelegate();
		    approvalMastOid = history.getApprovalMastOid();
		}
		
	    }

	    // 3. KETEpmApprovalHis 에 있으면 업데이트 없으면, INSERT -- Unique Key 생성한다.
	    QueryResult qr = KetEpmApprovalHelper.service.getEpmApprovalLink(epmDoc, null);
	    
	    if(qr != null && qr.size() > 0){
		
		Object[] objs = (Object[]) qr.nextElement();

		KETEpmApprovalHisLink historyLink = (KETEpmApprovalHisLink) objs[0];

		KETEpmApprovalHis epmApprovalHis = historyLink.getApprovalHis();

		epmApprovalHis.setApprovalType(approvalType);
		epmApprovalHis.setPboOid(pboOid);
		epmApprovalHis.setEcoNo(ecoNo);

		epmApprovalHis.setRequestDate(requestDate);
		epmApprovalHis.setApprovalDate(approvalDate);
		epmApprovalHis.setApproverId(approverId);
		epmApprovalHis.setApproverName(approverName);
		epmApprovalHis.setApproverDelegate(delegater);
		epmApprovalHis.setApprovalMastOid(approvalMastOid);

		PersistenceServerHelper.manager.update(epmApprovalHis);
		
	    }else{
		
		KETEpmApprovalHis epmApprovalHis = KETEpmApprovalHis.newKETEpmApprovalHis();

		epmApprovalHis.setApprovalType(approvalType);
		epmApprovalHis.setPboOid(pboOid);
		epmApprovalHis.setEcoNo(ecoNo);

		epmApprovalHis.setRequestDate(requestDate);
		epmApprovalHis.setApprovalDate(approvalDate);
		epmApprovalHis.setApproverId(approverId);
		epmApprovalHis.setApproverName(approverName);
		epmApprovalHis.setApproverDelegate(delegater);
		epmApprovalHis.setApprovalMastOid(approvalMastOid);

		PersistenceHelper.manager.save(epmApprovalHis);

		KETEpmApprovalHisLink historyLink = KETEpmApprovalHisLink.newKETEpmApprovalHisLink(epmApprovalHis, epmDoc);

		PersistenceHelper.manager.save(historyLink);
	    }
	}
    }
    
    // 도면 정보 업데이트 - 도면 결재 처리
    public void updateEpmApprovalInfo(List<EpmRevisionDTO> detailMastList) throws Exception {
	
	// 도면을 리비전 별로 결재를 처리한다.
	for( int i=0; i < detailMastList.size(); i++){
	    
	    // 최초 리비전
	    EpmRevisionDTO dto = detailMastList.get(i);
	    String iterationOid = dto.getIterOid();
	    EPMDocument epmDoc = (EPMDocument)CommonUtil.getObject(iterationOid);
	    
	    // 승인된 것만 처리한다.
	    if(epmDoc.getLifeCycleState() != State.toState("APPROVED")){
		continue;
	    }
	    
	    Persistable approvalPers = KetEpmApprovalHelper.service.getSavedPbo(epmDoc);
	    if(approvalPers != null) continue;
	    
	    // 승인된 것의 결재정보를 가져온다.
	    // 1. PBO를 찾는다. : pbo object type, pbo object OID, ECO NO
//	    WTObject pbo = KETCommonHelper.service.getPBO( epmDoc );
	    EpmApprovalHandler handler = new EpmApprovalHandler();
	    handler.getApprovalPBO(epmDoc, true);
	    
	    String approvalType = handler.getType();
	    Persistable pbo = handler.getPbo();
	    
	    if(pbo == null) {
		migLogUtil.log("EPM_Approval", epmDoc, "HAS_NOT_PBO", "결재 정보 부재", epmDoc.getVersionIdentifier().getValue() + "[" + StringUtil.checkNull(IBAUtil.getAttrValue(epmDoc, EDMHelper.IBA_MANUFACTURING_VERSION)) +  "]" , epmDoc.getNumber());
		return;
	    }
	    
	    String pboOid = CommonUtil.getOIDString(pbo);
	    
	    String ecoNo = "";
	    if(pbo instanceof KETProdChangeOrder){
		ecoNo = ((KETProdChangeOrder)pbo).getEcoId();
	    }else if(pbo instanceof KETMoldChangeOrder){
		ecoNo = ((KETMoldChangeOrder)pbo).getEcoId();
	    }
	    
	    // 2. 결재정보를 가져온다. : 결재 승인자ID, 승인자명, 대리자, 요청일, 완료일, 결재Master Oid 
	    EpmApprovalHistoryHandler history = new EpmApprovalHistoryHandler();
	    history.getApprovalHistory(pbo);
	    
	    if(history.getApprovalDate() == null ) {
		if(history.getApproverName() == null ) {
		    migLogUtil.log("EPM_Approval", epmDoc, "HAS_NOT_COMPELETE_DATENUSER", epmDoc.getVersionIdentifier().getValue() + "[" + StringUtil.checkNull(IBAUtil.getAttrValue(epmDoc, EDMHelper.IBA_MANUFACTURING_VERSION)) +  "]", pboOid, epmDoc.getNumber());
		    return;
		}else{
		    migLogUtil.log("EPM_Approval", epmDoc, "HAS_NOT_COMPELETE_DATE", epmDoc.getVersionIdentifier().getValue() + "[" + StringUtil.checkNull(IBAUtil.getAttrValue(epmDoc, EDMHelper.IBA_MANUFACTURING_VERSION)) +  "]", pboOid, epmDoc.getNumber());
		    return;
		}
	    }else if(history.getApproverName() == null ) {
		migLogUtil.log("EPM_Approval", epmDoc, "HAS_NOT_COMPELETE_USER", epmDoc.getVersionIdentifier().getValue() + "[" + StringUtil.checkNull(IBAUtil.getAttrValue(epmDoc, EDMHelper.IBA_MANUFACTURING_VERSION)) +  "]", pboOid, epmDoc.getNumber());
		return;
	    }
	    
	    // 3. KETEpmApprovalHis 에 있으면 업데이트 없으면, INSERT -- Unique Key 생성한다.
	    QueryResult qr = KetEpmApprovalHelper.service.getEpmApprovalLink(epmDoc, null);
	    
	    if(qr != null && qr.size() > 0){
		
		Object[] objs = (Object[]) qr.nextElement();
		
		KETEpmApprovalHisLink historyLink = (KETEpmApprovalHisLink) objs[0];
		
		KETEpmApprovalHis epmApprovalHis = historyLink.getApprovalHis();
		
		epmApprovalHis.setApprovalType(approvalType);
		epmApprovalHis.setPboOid(pboOid);
		epmApprovalHis.setEcoNo(ecoNo);
		
		epmApprovalHis.setRequestDate(history.getRequestDate());
		epmApprovalHis.setApprovalDate(history.getApprovalDate());
		epmApprovalHis.setApproverId(history.getApproverId());
		epmApprovalHis.setApproverName(history.getApproverName());
		epmApprovalHis.setApproverDelegate(history.getApproverDelegate());
		epmApprovalHis.setApprovalMastOid(history.getApprovalMastOid());
		
		PersistenceServerHelper.manager.update(epmApprovalHis);
		
	    }else{
		
		KETEpmApprovalHis epmApprovalHis = KETEpmApprovalHis.newKETEpmApprovalHis();
		
		epmApprovalHis.setApprovalType(approvalType);
		epmApprovalHis.setPboOid(pboOid);
		epmApprovalHis.setEcoNo(ecoNo);
		
		epmApprovalHis.setRequestDate(history.getRequestDate());
		epmApprovalHis.setApprovalDate(history.getApprovalDate());
		epmApprovalHis.setApproverId(history.getApproverId());
		epmApprovalHis.setApproverName(history.getApproverName());
		epmApprovalHis.setApproverDelegate(history.getApproverDelegate());
		epmApprovalHis.setApprovalMastOid(history.getApprovalMastOid());
		
		PersistenceHelper.manager.save(epmApprovalHis);
		
		KETEpmApprovalHisLink historyLink = KETEpmApprovalHisLink.newKETEpmApprovalHisLink(epmApprovalHis, epmDoc);
		
		PersistenceHelper.manager.save(historyLink);
	    }
	}
    }

    // 마스터의 상세 도면 Rev 리스트
    private List<EpmRevisionDTO> getPartDetailRevInfo(long mid) throws Exception {

	List<EpmRevisionDTO> list = null;
	StampDaoManager oDaoManager = StampDaoFactory.getInstance().getDaoManager();

	try {

	    List aBind = new ArrayList();

	    StringBuffer buffer = new StringBuffer();
	    buffer.append(" SELECT DOCUMENTNUMBER DOC_NO, M.NAME \n");
	    buffer.append("      , E.VERSIONIDA2VERSIONINFO VER_NO \n");
	    buffer.append("      , E.VERSIONSORTIDA2VERSIONINFO VER_SORT_NO \n");
	    buffer.append("      , E.ITERATIONIDA2ITERATIONINFO ITER_NO \n");
	    buffer.append("      , M.IDA2A2 MID \n");
	    buffer.append("      , E.STATECHECKOUTINFO STATECHECKOUTINFO \n");
	    buffer.append("      , M.CLASSNAMEA2A2||':'||M.IDA2A2 MOID \n");
	    buffer.append("      , E.BRANCHIDITERATIONINFO VID \n");
	    buffer.append("      , 'VR:'||E.CLASSNAMEA2A2||':'||E.BRANCHIDITERATIONINFO VOID \n");
	    buffer.append("      , E.IDA2A2 EID \n");
	    buffer.append("      , E.CLASSNAMEA2A2||':'||E.IDA2A2 EOID \n");
	    buffer.append(" FROM EPMDOCUMENTMASTER M, EPMDOCUMENT E \n");
	    buffer.append(" WHERE 1 = 1 \n");
	    buffer.append(" AND M.IDA2A2 = ? \n");
	    buffer.append(" AND M.IDA2A2 = E.IDA3MASTERREFERENCE \n");
	    // 추가된 부분 시작
	    buffer.append(" AND E.LATESTITERATIONINFO = 1 \n");
	    buffer.append(" AND E.STATECHECKOUTINFO != 'wrk' \n");
	    // 추가된 부품 끝
	    buffer.append(" ORDER BY M.IDA2A2, E.VERSIONSORTIDA2VERSIONINFO , TO_NUMBER(E.ITERATIONIDA2ITERATIONINFO) DESC \n");

	    aBind.add(mid);
	    
	    String sSql = buffer.toString();
	    list = oDaoManager.queryForList(sSql, aBind, new StampRowSetStrategy<EpmRevisionDTO>() {
		
		@Override
                public EpmRevisionDTO mapRow(ResultSet rs) throws SQLException {
		    
	            EpmRevisionDTO dto = new EpmRevisionDTO();
	            
	            dto.setEpmNo(rs.getString("DOC_NO"));
	            dto.setEpmName(rs.getString("NAME"));
	            
	            dto.setVerNo(rs.getString("VER_NO"));
	            dto.setVerSortNo(rs.getString("VER_SORT_NO"));
	            
	            dto.setIterNo(rs.getString("ITER_NO"));
	            
	            dto.setStateCheckState(rs.getString("STATECHECKOUTINFO"));
	            
	            dto.setMastOid(rs.getString("MOID"));
	            dto.setIterOid(rs.getString("EOID"));
	            dto.setRevOid(rs.getString("VOID"));
	            
	            dto.setMastId(rs.getLong("MID"));
	            dto.setRevId(rs.getLong("VID"));
	            dto.setIterId(rs.getLong("EID"));
	            
	            return dto;
                }
		
	    });

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}
	
	return list;
    }
    
    //##################################################################################
    ////////////////////////////////////////////////////////////////////////////////////
    // 도면 마스터 릴레이션 마이그레이션
    ////////////////////////////////////////////////////////////////////////////////////
    //##################################################################################
    
    public void migEpmMastRel(String arg1, String arg2) throws Exception{
	
	List<String> list = null;
	StampDaoManager oDaoManager = StampDaoFactory.getInstance().getDaoManager();

	try {

	    StringBuffer buffer = new StringBuffer();
	    buffer.append(" SELECT PARTMASTER, EPMMASTER, REFERENCETYPE, REQUIRED, ECAD \n");
	    buffer.append(" FROM \n");
	    buffer.append(" ( \n");
	    buffer.append("  SELECT DISTINCT P.IDA3MASTERREFERENCE PARTMASTER, E.IDA3MASTERREFERENCE EPMMASTER, L.REFERENCETYPE, L.REQUIRED, L.ECAD \n");
	    buffer.append("  FROM PartToEPMLink L, WTPART P, EPMDOCUMENT E  \n");
	    buffer.append("  WHERE L.BRANCHIDA3B5 = P.BRANCHIDITERATIONINFO  \n");
	    buffer.append("  AND L.BRANCHIDA3A5 = E.BRANCHIDITERATIONINFO  \n");
	    buffer.append("  MINUS   \n");
	    buffer.append("  SELECT IDA3B5 PARTMASTER, IDA3A5 EPMMASTER, L.REFERENCETYPE, L.REQUIRED, L.ECAD \n");
	    buffer.append("  FROM EPMLINK L \n");
	    buffer.append(" ) A \n");
	    buffer.append(" WHERE (A.PARTMASTER, A.EPMMASTER) NOT IN (SELECT IDA3B5 PARTMASTER, IDA3A5 EPMMASTER FROM EPMLINK ) \n");
	    buffer.append(" ORDER BY PARTMASTER, EPMMASTER, REFERENCETYPE, REQUIRED, ECAD \n");
	    
	    String sSql = buffer.toString();
	    list = oDaoManager.queryForList(sSql, new StampRowSetStrategy<String>() {
		
		@Override
                public String mapRow(ResultSet rs) throws SQLException {
	            
	            return rs.getString("PARTMASTER") + "," + rs.getString("EPMMASTER")  + "," + rs.getString("REQUIRED") + "," + rs.getString("ECAD")+ "," + rs.getString("REFERENCETYPE");
                }
		
	    });
	    
	    if( list == null || list.size() == 0 ){
		return;
	    }
	    
	    for(String str : list){
		
		String[] array = str.split(",");
		String partMasterOid  = "wt.part.WTPartMaster:" + array[0];
		String epmMasterOid  = "wt.epm.EPMDocumentMaster:" + array[1];
		String required  = array[2];
		String ecad  = array[3];
		String referenceType  = array[4];
		
		WTPartMaster partMaster = (WTPartMaster)CommonUtil.getObject(partMasterOid);
		EPMDocumentMaster epmMaster = (EPMDocumentMaster)CommonUtil.getObject(epmMasterOid);
		
		Kogger.debug(getClass(), "PART MASTER :" + partMaster.getNumber() );
		Kogger.debug(getClass(), "EPM MASTER :" + epmMaster.getNumber() );
		Kogger.debug(getClass(), "referenceType :" + referenceType);
		Kogger.debug(getClass(), "required :" + required);
		Kogger.debug(getClass(), "ecad :" + ecad);
		
		EPMLink epmLink = EPMLink.newEPMLink(epmMaster, partMaster);
		epmLink.setReferenceType(referenceType);
		epmLink.setRequired(Integer.parseInt(required));
		epmLink.setEcad("1".equals(ecad));
		PersistenceHelper.manager.save(epmLink);
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}
	
    }
    
    //##################################################################################
    ////////////////////////////////////////////////////////////////////////////////////
    // 도면 리비전 릴레이션 마이그레이션
    ////////////////////////////////////////////////////////////////////////////////////
    //##################################################################################
    
    public void migEpmRevisionRel(String[] args) throws Exception{
	
	List<String> list = null;
	StampDaoManager oDaoManager = StampDaoFactory.getInstance().getDaoManager();

	try {

	    StringBuffer buffer = new StringBuffer();
	    /**
	    // 아래 쿼리 돌려서 ECO NO 가 나오면,
	    // ECO 결재후의 도면의 결재이력이 빠진 경우 생겼음.
            SELECT E.ECOID
                 FROM
                 (
                     SELECT ECOID, E.IDA2A2
                     FROM KETPRODCHANGEORDER E
                     WHERE STATESTATE = 'APPROVED'
                     AND CREATESTAMPA2 > TO_DATE('2014-12-01', 'yyyy-MM-dd')
                     UNION
                     SELECT ECOID, E.IDA2A2
                     FROM KETMOLDCHANGEORDER E
                     WHERE STATESTATE = 'APPROVED'
                     AND CREATESTAMPA2 > TO_DATE('2014-12-01', 'yyyy-MM-dd')
                 ) E, 
                  ( 
                    SELECT DISTINCT IDA3A8
                    FROM (
                      select A.IDA3A8 
                      from KETProdChangeActivity A, KETProdECAEpmDocLink L, EPMDOCUMENT OE, EPMDOCUMENT NE 
                      WHERE 1 = 1 
                      AND L.IDA3A5 = OE.IDA2A2 
                      AND L.IDA3B5 = A.IDA2A2 
                      AND AFTERVERSION IS NULL 
                      AND OE.STATESTATE = 'APPROVED' 
                      AND OE.IDA3MASTERREFERENCE = NE.IDA3MASTERREFERENCE 
                      AND NE.VERSIONIDA2VERSIONINFO = L.PREVERSION 
                      UNION 
                      select A.IDA3A8 
                      from KETMoldChangeActivity A, KETMoldECAEpmDocLink L, EPMDOCUMENT OE, EPMDOCUMENT NE 
                      WHERE 1 = 1 
                      AND L.IDA3A5 = OE.IDA2A2 
                      AND L.IDA3B5 = A.IDA2A2 
                      AND AFTERVERSION IS NULL 
                      AND OE.STATESTATE = 'APPROVED' 
                      AND OE.IDA3MASTERREFERENCE = NE.IDA3MASTERREFERENCE 
                      AND NE.VERSIONIDA2VERSIONINFO = L.PREVERSION 
                      UNION
                      select A.IDA3A8 
                      from KETProdChangeActivity A, KETProdECAEpmDocLink L, EPMDOCUMENT OE, EPMDOCUMENT NE 
                      WHERE 1 = 1 
                      AND L.IDA3A5 = OE.IDA2A2 
                      AND L.IDA3B5 = A.IDA2A2 
                      AND AFTERVERSION IS NOT NULL 
                      AND OE.STATESTATE = 'APPROVED' 
                      AND OE.IDA3MASTERREFERENCE = NE.IDA3MASTERREFERENCE 
                      AND NE.VERSIONIDA2VERSIONINFO = L.AFTERVERSION 
                      UNION 
                      select A.IDA3A8 
                      from KETMoldChangeActivity A, KETMoldECAEpmDocLink L, EPMDOCUMENT OE, EPMDOCUMENT NE 
                      WHERE 1 = 1 
                      AND L.IDA3A5 = OE.IDA2A2 
                      AND L.IDA3B5 = A.IDA2A2 
                      AND AFTERVERSION IS NOT NULL 
                      AND OE.STATESTATE = 'APPROVED' 
                      AND OE.IDA3MASTERREFERENCE = NE.IDA3MASTERREFERENCE 
                      AND NE.VERSIONIDA2VERSIONINFO = L.AFTERVERSION
                    ) 
                  ) A
                 WHERE 1 = 1
                 AND E.IDA2A2 = A.IDA3A8
                 MINUS
                 SELECT H.ECONO ECOID
                 FROM KETEpmApprovalHis H
                 WHERE H.ECONO IS NOT NULL
	     */
	    buffer.append(" SELECT DISTINCT L.IDA3B5 PARTMASTER, L.IDA3A5 EPMMASTER, PM.WTPARTNUMBER, P.IDA2A2 PART , EM.DOCUMENTNUMBER, E.IDA2A2 EPM \n");
	    buffer.append("   , L.REFERENCETYPE, L.REQUIRED,  L.ECAD \n");
	    buffer.append("  FROM EPMLINK L, WTPARTMASTER PM, EPMDOCUMENTMASTER EM, WTPART P, EPMDOCUMENT E, PartToEPMLink L2 \n");
	    // 도면 결재이력의 리비전별, ECO NO 정보
	    buffer.append("  ,( \n");
	    buffer.append("    SELECT distinct HL.BRANCHIDA3B5, h.ECONO \n");
	    buffer.append("    FROM KETEpmApprovalHis H, KETEpmApprovalHisLink hl \n");
	    buffer.append("    WHERE H.IDA2a2 = HL.IDA3A5 \n");
	    buffer.append("    AND H.ECONO IS NOT NULL \n");
	    buffer.append("  ) H \n");
	    buffer.append("  WHERE 1=1 \n");
	    buffer.append("  AND L.IDA3B5 = PM.IDA2A2 \n"); 
	    buffer.append("  AND L.IDA3A5 = EM.IDA2A2 \n");
	    buffer.append("  AND P.IDA3MASTERREFERENCE = PM.IDA2A2 \n"); 
	    buffer.append("  AND E.IDA3MASTERREFERENCE = EM.IDA2A2 \n"); 
	    // 최신 이터레이션 대상
	    buffer.append("  AND P.LATESTITERATIONINFO = 1 \n");
	    buffer.append("  AND E.LATESTITERATIONINFO = 1 \n");
	    // 승인난 부품 및 도면들 대상
	    buffer.append("  AND E.STATESTATE = 'APPROVED' \n");
	    buffer.append("  AND P.STATESTATE = 'APPROVED' \n");
	    // 체크인된 부품 도면 대상
	    buffer.append("  AND E.STATECHECKOUTINFO = 'c/i' \n");
	    buffer.append("  AND P.STATECHECKOUTINFO = 'c/i' \n");
	    // 개정 안된 최신 리비전의 부품 도면들 대상
	    buffer.append("  AND E.BRANCHIDITERATIONINFO = ( SELECT MAX(BRANCHIDITERATIONINFO) FROM EPMDOCUMENT WHERE IDA3MASTERREFERENCE = EM.IDA2A2) \n"); 
	    buffer.append("  AND P.BRANCHIDITERATIONINFO = ( SELECT MAX(BRANCHIDITERATIONINFO) FROM WTPART WHERE IDA3MASTERREFERENCE = PM.IDA2A2) \n");
	    // 부품과 도면이 EPMLink로 연결되어 있지만 PartToEpm에는 연결되어 있지 않은 경우
	    buffer.append("  AND E.BRANCHIDITERATIONINFO = L2.BRANCHIDA3A5(+) \n");
	    buffer.append("  AND L2.IDA2A2 IS NULL \n");
	    // 1차고도화 이후의 결재승인나면 부품 속성에 ECONO가 들어오는 데 그것의 ECO NO가 있고
	    buffer.append("  AND P." + PartSpecEnum.SpEoNo.getColumnName() + " IS NOT NULL \n");
	    // 부품의 ECO NO와 도면의 ECO NO가 동일한 경우
	    buffer.append("  AND P." + PartSpecEnum.SpEoNo.getColumnName() + " = h.ECONO \n");
	    buffer.append("  AND E.BRANCHIDITERATIONINFO = H.BRANCHIDA3B5 \n");
	    // EPMLINK의 데이터 두개 이상일 경우, REFERNCE_TYPE이 RELATED_MODEL, RELATED_DRAWING중 RELATED_DRAWING만 적용함.
	    buffer.append("  ORDER BY PARTMASTER, EPMMASTER, REFERENCETYPE, REQUIRED DESC, ECAD DESC \n");
	    	    
	    String sSql = buffer.toString();
	    list = oDaoManager.queryForList(sSql, new StampRowSetStrategy<String>() {
		
		@Override
                public String mapRow(ResultSet rs) throws SQLException {
	            
	            return rs.getString("PARTMASTER") + "," + rs.getString("EPMMASTER")  + "," + 
	        	    rs.getString("PART") + "," + rs.getString("EPM")  + "," + 
	        	    rs.getString("REQUIRED") + "," + rs.getString("ECAD")+ "," + rs.getString("REFERENCETYPE");
                }
		
	    });
	    
	    if( list == null || list.size() == 0 ){
		return;
	    }
	    
	    Map<String, String> map = new HashMap<String, String>();
	    for(String str : list){
		
		String[] array = str.split(",");
		String partMasterOid  = "wt.part.WTPartMaster:" + array[0];
		String epmMasterOid  = "wt.epm.EPMDocumentMaster:" + array[1];
		String partOid  = "wt.part.WTPart:" + array[2];
		String epmOid  = "wt.epm.EPMDocument:" + array[3];
		String required  = array[4];
		String ecad  = array[5];
		String referenceType  = array[6];
		
		WTPartMaster partMaster = (WTPartMaster)CommonUtil.getObject(partMasterOid);
		EPMDocumentMaster epmMaster = (EPMDocumentMaster)CommonUtil.getObject(epmMasterOid);
		
		if(map.containsKey(partMaster.getNumber()  + "|" + epmMaster.getNumber())){
		    continue;
		}else{
		    map.put(partMaster.getNumber()  + "|" + epmMaster.getNumber() , null);
		}
		
		WTPart part = (WTPart)CommonUtil.getObject(partOid);
		EPMDocument epmDocument = (EPMDocument)CommonUtil.getObject(epmOid);
		
		Kogger.debug(getClass(), "PART MASTER :" + partMaster.getNumber() );
		Kogger.debug(getClass(), "EPM MASTER :" + epmMaster.getNumber() );
		Kogger.debug(getClass(), "referenceType :" + referenceType);
		Kogger.debug(getClass(), "required :" + required);
		Kogger.debug(getClass(), "ecad :" + ecad);
		
		PartEpmRelation rel = new PartEpmRelation();
		rel.createNewVerRelation(partMaster, epmMaster, part, epmDocument
			    , "1".equals(ecad),Integer.parseInt(required), referenceType, true, false);
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}
	
    }
    
  //##################################################################################
    ////////////////////////////////////////////////////////////////////////////////////
    // 도면 viewable파일 생성 (autocad, proe 만)
    ////////////////////////////////////////////////////////////////////////////////////
    //##################################################################################
    
    public void migEpmRePublish(String[] args) throws Exception{
	
	List<String> list = null;
	StampDaoManager oDaoManager = StampDaoFactory.getInstance().getDaoManager();

	try {
	    VieawbleGenerator gen = new VieawbleGenerator();

	    StringBuffer buffer = new StringBuffer();
	    
	    buffer.append(" SELECT 'wt.epm.EPMDocument:'||EPM.IDA2A2 AS DRAWINGOID                                                                   \n");
	    buffer.append("   FROM EPMDOCUMENTMASTER EM                                                                                              \n");
	    buffer.append("       ,EPMDOCUMENT EPM                                                                                                   \n");
	    buffer.append("       ,(SELECT IDA3A4 FROM STRINGVALUE WHERE IDA3A6 = '16909' AND VALUE = '제품도면') STV                                \n");
	    buffer.append("  WHERE 1=1                                                                                                               \n");
	    buffer.append("    AND EM.IDA2A2 = EPM.IDA3MASTERREFERENCE                                                                               \n");
	    buffer.append("    AND EPM.IDA2A2 = STV.IDA3A4                                                                              \n");
	    buffer.append("    AND EPM.LATESTITERATIONINFO = 1                                                                                       \n");
	    buffer.append("    AND EPM.STATECHECKOUTINFO IN ('c/i','c/o')                                                                            \n");
	    buffer.append("    AND EPM.VERSIONIDA2VERSIONINFO = ( SELECT TO_CHAR(MAX(TO_NUMBER(E.VERSIONIDA2VERSIONINFO)))                           \n");
	    buffer.append("                                         FROM EPMDOCUMENT E                                                               \n");
	    buffer.append("                                         WHERE E.IDA3MASTERREFERENCE = EPM.IDA3MASTERREFERENCE                            \n");
	    buffer.append("                                         AND E.STATECHECKOUTINFO NOT IN ('term','wrk','to del','to wrk')                  \n");
	    buffer.append("                                         AND E.LATESTITERATIONINFO = 1 AND AUTHORINGAPPLICATION IN ('PROE','ACAD' ))      \n");
	    buffer.append("    AND TO_CHAR(EPM.CREATESTAMPA2,'YYYY') = "+args[0]+"                                                                        \n");
	    buffer.append("    AND AUTHORINGAPPLICATION IN ('PROE','ACAD' )                                                                          \n");
	    	    
	    String sSql = buffer.toString();
	    list = oDaoManager.queryForList(sSql, new StampRowSetStrategy<String>() {
		
		@Override
                public String mapRow(ResultSet rs) throws SQLException {
	            
	            return rs.getString("DRAWINGOID");
                }
		
	    });
	    
	    if( list == null || list.size() == 0 ){
		return;
	    }
	    
	    int targetSize = 0;
	    
	    boolean VIEWABLE_ALREADY_EXIST = false;
	    Map<String, String> map = new HashMap<String, String>();
	    for(String str : list){

		String epmOid  = str;

		EPMDocument epm = (EPMDocument)CommonUtil.getObject(epmOid);
		VIEWABLE_ALREADY_EXIST = gen.newGenerator(epm);
		if(VIEWABLE_ALREADY_EXIST){
		    System.out.println("publish 대상 [ "+epm.getNumber() + " ]  VIEWABLE_ALREADY_EXIST => "+VIEWABLE_ALREADY_EXIST);
		    targetSize++;
		}
		
	    }
	    
	    System.out.println("총 publish 대상 건 수 : "+targetSize + " 건");

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}
	
    }
    
}