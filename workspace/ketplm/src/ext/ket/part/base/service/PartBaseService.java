package ext.ket.part.base.service;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import wt.epm.EPMDocument;
import wt.epm.EPMDocumentMaster;
import wt.method.RemoteInterface;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.util.WTException;
import e3ps.common.web.PageControl;
import e3ps.project.E3PSProject;
import ext.ket.part.entity.KETPartClassification;
import ext.ket.part.entity.dto.KETMassPartDTO;
import ext.ket.part.entity.dto.PartBaseDTO;
import ext.ket.part.entity.dto.PartCatalogueDTO;
import ext.ket.part.entity.dto.PartClassAttrLinkDTO;
import ext.ket.part.entity.dto.PartDieProjectHelpDTO;
import ext.ket.part.entity.dto.PartListDTO;
import ext.ket.part.entity.dto.PartMatchingDTO;
import ext.ket.part.entity.dto.PartSearchMainDTO;
import ext.ket.part.entity.dto.PartValidationDTO;

@RemoteInterface
public interface PartBaseService {

    // 부품 검색
    public PageControl searchMainPartList(PartSearchMainDTO partSearchMainDTO, String pageTotalSize) throws Exception;

    // 부품 검색시 화면에 분류체계의 결합된 속성 조회
    public List<PartClassAttrLinkDTO> getPartPropsMix(String clazOidArray, Locale locale) throws Exception;

    // 부품 등록
    public WTPart createWTPart(PartBaseDTO dto) throws Exception;

    // 부품 반제품 등록
    public WTPart createWTPartByHalb(PartBaseDTO dto) throws Exception;

    // 부품 복사 등록
    public WTPart createWTPartByCopy(PartBaseDTO dto) throws Exception;

    // 부품[ERP] 등록
    public WTPart createWTPartByErp(PartBaseDTO dto) throws Exception;

    // 부품 No 체크
    public boolean existWTPartNumber(String partNumber, String partType) throws Exception;

    // 부품 No ERP 체크
    public boolean existErpPartNumber(String partNumber) throws Exception;

    // 부품 연관 DieHalbLink
    public boolean existPartDieHalbLink(String partNumber) throws Exception;

    // 부품 등록시 분류체계별 속성 조회 - 수정, 조회화면에서도 그대로 사용
    public List<PartClassAttrLinkDTO> getPartProps(String clazOid, Locale locale) throws Exception;

    // 부품 등록시 채번 코드, 제품/반제품 코드 확인
    public KETPartClassification getPartClassification(String clazOid) throws Exception;

    // 부품 등록시 품명 자동완성 관련 naming 가져옴
    public String getPartNamingOid(String clazOid) throws Exception;

    // 부품 수정
    public WTPart updateWTPart(PartBaseDTO dto) throws Exception;

    // 부품 결재완료 후 수정
    public WTPart updateWTPartAfterApp(PartBaseDTO dto) throws Exception;

    // 부품 삭제
    public void deletePart(PartBaseDTO dto, Locale locale) throws Exception;

    // 부품 사용않함
    public String notUsePart(String partOid) throws Exception;

    // 부품 조회 - 수정시, 복사생성 시도 조회함
    public PartBaseDTO viewDetailPart(PartBaseDTO dto, Locale locale) throws Exception;
    
    // RK30용도 부품 조회 - 수정시, 복사생성 시도 조회함
    public PartBaseDTO RK30viewDetailPart(PartBaseDTO dto, Locale locale) throws Exception;

    // 부품 Part List 조회
    public PartListDTO viewPartList(PartListDTO dto, Locale locale) throws Exception;

    // 부품 개정 - Version label 제공 for eco
    public String reviseWTPartNGetVersion(WTPart sourcePart, String developLevel) throws Exception;

    // 부품 개정 취소
    public void cancelReviseWTPart(WTPart sourcePart) throws Exception;

    // ERP 부품 가져오기
    public PartBaseDTO migPartFromErpForm(String partNumber, Locale locale) throws Exception;

    // 최신 부품 가져오기
    public WTPart getLatestPart(String partNumber) throws Exception;

    // 최신 부품 가져오기
    public WTPart getLatestPart(WTPartMaster wtPartMast) throws Exception;

    // 부품 마스터 가져오기
    public WTPartMaster getMaster(String partNo) throws WTException;

    // 최신 EPM 가져오기
    public EPMDocument getLatestEPM(String epmNumber) throws Exception;

    // 최신 EPM 가져오기
    public EPMDocument getLatestEPM(EPMDocumentMaster epmMast) throws Exception;

    // EPM 마스터 가져오기
    public EPMDocumentMaster getEpmMaster(String epmNumber) throws WTException;

    // 도면 개정 - 일반제품일 경우 개발단계 변경 by eco
    public String reviseEpmDocNGetVersion(EPMDocument sourceEpm, String developLevel) throws Exception;

    // 부품의 생산처명 정보 가져오기
    public String getManufacPlaceName(String manufacPlaceCode) throws Exception;

    // 연계도면 가져오기
    public List<EPMDocument> getRelatedEPMDocument(List<WTPart> partList) throws Exception;

    // ECO WTPart의 연관된 EPM 가져오기
    public Map<String, List<EPMDocument>> getRelatedEPMDocByEcoPart(WTPart wtPart) throws Exception;

    public Map<String, List<EPMDocument>> getRelatedEPMDocByEcoPart(WTPart wtPart, boolean isOnlyApproved) throws Exception;

    // 프로젝트 개발단계 정보 가져오기
    public String getProjectDevLevel(String projectNumber) throws Exception;

    // 프로젝트 개발단계 정보 가져오기
    public String getProjectDevLevel(E3PSProject e3psProject) throws Exception;

    // Die - Halb Link 생성
    public boolean addDieHalbLink(String dieOid, String halbOid) throws Exception;

    // Die - Halb Linkt 삭제
    public boolean deleteDieHalbLink(String dieOid, String halbOid) throws Exception;

    // Material Type List
    public Map<String, Object> getMaterialTypeList(String clazOid, String spMaterMaker) throws Exception;

    // Material Type List
    public Map<String, Object> getMaterialTypeList(boolean isSuji, String spMaterMaker) throws Exception;

    // Material Info List : 재질 (수지), 재질 (비철)
    public Map<String, Object> getMaterialInfoList(String clazOid, String spMaterMaker, String spMaterType) throws Exception;

    // Material Info List : 재질 (수지), 재질 (비철)
    public Map<String, Object> getMaterialInfoList(boolean isSuji, String spMaterMaker, String spMaterType) throws Exception;

    // Material Info 가져오기 : 재질(수지), 재질(비철)
    public String getMaterialInfo(String materialOid) throws Exception;

    // Material Info Update : 원재료 정보 업데이트 by BOM의 일반제품일 경우
    public void updateMaterialInfo(WTPart halbParentPart, WTPart rohSonPart) throws Exception;

    // Die List of Halb part related
    public Map<String, Object> getDieListOfHalb(String halbOid) throws Exception;

    // Die Detail of Halb part related
    public Map<String, Object> getDieDetailOfHalb(String dieOid, Locale locale) throws Exception;

    // 프로젝트와 원재료 등 정보 공유 및 Sync : 부품 수정 시점 등
    public PartDieProjectHelpDTO getPartDieInfoForSync(String partNo, String dieNo) throws Exception;

    // BOM - Mold Upload 시점에 부품과 연결된 도면 링크 처리 : 금형부품의 프로젝트를 Die의 프로젝트로 넣어줌
    public boolean makeMoldPartToEpmDocRelation(WTPart diePart, WTPart moldPart, String optionModel) throws Exception;

    // EO 결재상신 Validation - ERP 존재 여부 체크
    public List<PartValidationDTO> validErpExist(List<WTPart> targetPartList) throws Exception;

    // EO 결재상신 Validation - 체크 속성 누락 여부 체크
    public List<PartValidationDTO> validCheckedProps(List<WTPart> targetPartList,HashMap<String, String> EcoValidParam) throws Exception;

    // EO 결재상신 Validation - 반제품 ERP에 존재하는지 체크 (금형 ECO의 경우 관련제품이 아직 ERP에 생성되지 않았습니다. )
    public List<PartValidationDTO> validHalbErpExistByDie(List<WTPart> targetPartList) throws Exception;

    // 프로젝트 부품 저장 시점에 부품의 프로젝트 No가 없으면 넣어준다.
    public void makeProjectPartRelationByProject(String projectNo, String partNo) throws Exception;

    // Admin 체크
    public boolean isAdmin() throws Exception;

    // Biz Admin 체크
    public boolean isBizAdmin() throws Exception;

    // Member 체크
    public boolean isMember(String group) throws Exception;

    // Member List 체크
    public boolean[] isMember(String... groupList) throws Exception;

    // Multi Part No A Link
    public String getALinkByMultiPartNo(String partNos) throws Exception;

    // 썸네일용 EPM 가져오기
    public EPMDocument getThumbViewEPMDocByPart(String wtPartOid) throws Exception;

    public EPMDocument getThumbViewEPMDocByPart(WTPart wtPart) throws Exception;

    // ECM에서 연관된 부품을 볼 때 사용함.
    public int getMatchingPartCount(String partOid) throws Exception;

    // ECM에서 연관된 부품을 볼 때 사용함.
    public List<PartMatchingDTO> getMatchingPartList(String partOid) throws Exception;

    // ECO Validation : 개정 버튼 누르면, 연계 부품이 먼저 개정되지 않으면 Validation Message 전달
    public String validRelatedPartRevised(EPMDocument epmDoc, String ecoOid);

    // ECO Validation : 개정 버튼 누르면, 연계 도면이 먼저 개정취소되지 않으면 Validation Message 전달
    public String validRelatedEpmCancelRevised(WTPart wtPart, String ecoOid);

    // ECO 부품상태 Released 되는 시점에 - 고객 Rev 올려줌. 고객 요청 속성 체크일 때만
    public WTPart checkReviseCustomerVersion(WTPart sourcePart) throws Exception;

    // Catalogue Export
    public List<PartCatalogueDTO> getCatalogueList(PartSearchMainDTO partSearchMainDTO) throws Exception;
    
    // 부품 도면 연계
    public boolean connEpm2Part(String epmOid, String partOid, String referenceType, String requied, String ecad) throws Exception;
    
    // 도면 (부품화면) viewable 재생성
    public boolean regenerateViewable(String oid) throws Exception;
    
    // 부품 도금 사양 추가 생성
    public void addGeneratePart(PartBaseDTO dto, String partNumber) throws Exception;

    // Test
    public void testPart(Object... args) throws Exception;
    
    //관리자 스크랩 생성
    public boolean scrapAutoGen(PartBaseDTO dto) throws Exception;
    
    //프로젝트 생산처 정보 동기화
    public void updateManufacPlaceByProject(Map<String, Object> reqMap) throws Exception;
    
    //자재마스터 양산시작일 관리
    public PageControl findPartMassPagingList(KETMassPartDTO dto) throws Exception;
    
    public void migPartMass() throws Exception;
    
    public void partMassSync() throws Exception;
    
    public Map<String, Object> saveMassPart(Map<String, Object> reqMap) throws Exception;

}
