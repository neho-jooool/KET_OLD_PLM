package ext.ket.part.classify.service;

import java.util.List;

import wt.method.RemoteInterface;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import ext.ket.part.entity.KETPartClassification;
import ext.ket.part.entity.dto.PartClassificationDTO;
import ext.ket.part.util.PartSpecEnum;

@RemoteInterface
public interface PartClazService {// extends CommonServiceInterface<PartClassificationDTO, KETPartClassification> {

    // 전체 쿼리
    public List<PartClassificationDTO> searchFullList(boolean withUseOnly) throws Exception;

    // client XML로 전달
    public String searchFullListXml(boolean withUseOnly) throws Exception;

    // insert for Migration
    public KETPartClassification insertMigration(KETPartClassification child, KETPartClassification parent) throws Exception;

    // save Xml Data
    public boolean saveXmlTreeData(String xmlStr) throws Exception;

    // get enum key value
    public String[] getEnumKeyValue(String codeType, java.util.Locale loc) throws Exception;

    // get Naming enum key value
    public String[] getNamingEnumKeyValue(java.util.Locale loc) throws Exception;

    // 분류 코드로 분류체계 가져오기 - migration 등에 사용
    public KETPartClassification getPartClassificationByClazCode(String clazCode) throws Exception;

    // 분류 코드로 분류체계 가져오기 - erp if 사용
    public KETPartClassification getPartClassification(WTPart wtpart) throws Exception;

    // 분류 코드로 분류체계 가져오기 - project 사용
    public KETPartClassification getPartClassification(WTPartMaster wtpartMast) throws Exception;

    // 부품에 연결된 분류체계에 연결된 '재질' 속성을 가져온다. - BOM에서 사용 : 재질은 수지, 비철, 금형, 구매품으로 4가지 종류가 있음
    public List<PartSpecEnum> getMaterialPartSpecEnum(WTPart wtpart) throws Exception;

    // 수지인지 비철인지 여부를 판단함.
    public boolean isSuji(String clazOid) throws Exception;

    // 수지인지 비철인지 여부를 판단함.
    public boolean isSuji(KETPartClassification claz) throws Exception;

    public String searchClazRoute(String clazOid) throws Exception;

    // public KETPartClassification findSameClassification(PartClassificationDTO clazDTO) throws Exception;

    // public KETPartClassification insertWithParent(PartClassificationDTO clazDTO) throws Exception;
    //
    // public KETPartClassification insertRoot(PartClassificationDTO clazDTO) throws Exception;

    // public KETPartClassification modify(PartClassificationDTO clazDTO) throws Exception;
    //
    // public KETPartClassification delete(PartClassificationDTO clazDTO) throws Exception;

    // public KETPartClassification insertMigration(KETPartClassification child, KETPartClassTreeLink link) throws Exception;

}
