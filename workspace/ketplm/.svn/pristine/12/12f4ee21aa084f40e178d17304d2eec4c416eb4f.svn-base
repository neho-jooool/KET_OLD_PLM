package ext.ket.part.migration.erp.service;

import java.util.List;

import wt.method.RemoteInterface;
import ext.ket.part.migration.erp.ErpDiePartDTO;
import ext.ket.part.migration.erp.ErpMoldPartDTO;
import ext.ket.part.migration.erp.ErpProdPartDTO;

@RemoteInterface
public interface KetMigService {
    
    //##################################################################################
    ////////////////////////////////////////////////////////////////////////////////////
    // 부품 ERP 마이그레이션
    ////////////////////////////////////////////////////////////////////////////////////
    //##################################################################################
    
    ////////////////////////////////////////////////////////////////////////////////////
    // 부품의 제품, DIE, Mold 그룹으로 구분해서 처리
    ////////////////////////////////////////////////////////////////////////////////////
    public void migErpProd(String filePath) throws Exception;
    
    public void migErpDie(String filePath) throws Exception;
    
    public void migErpMold1(String filePath) throws Exception;
    
    public void migErpMold2(String filePath) throws Exception;
    
    ////////////////////////////////////////////////////////////////////////////////////
    // 부품의 Excel파일 하나에 하나로 구분해서 처리
    ////////////////////////////////////////////////////////////////////////////////////
    public void migErpOnePartType(String filePath, String partType) throws Exception;
    
    ////////////////////////////////////////////////////////////////////////////////////
    // 부품의 일부만 처리
    ////////////////////////////////////////////////////////////////////////////////////
    public void migErpProd(String filePath, String fromPartType, List<ErpProdPartDTO> paramList) throws Exception;
    
    public void migErpDie(String filePath, String fromPartType, List<ErpDiePartDTO> paramList) throws Exception;
    
    public void migErpMold(String filePath, String fromPartType, List<ErpMoldPartDTO> paramList) throws Exception;

    
    //##################################################################################
    ////////////////////////////////////////////////////////////////////////////////////
    // 부품 속성 마이그레이션
    ////////////////////////////////////////////////////////////////////////////////////
    //##################################################################################
    // 속성 1
    public void migPartProp(String filePath, String lastSheet) throws Exception;
    // 속성2
    public void migPartProp(String filePath, String lastSheet, String fileName) throws Exception;
    // 리비전
    public void migPartRevision(String arg1, String arg2) throws Exception;
    // 원재료 / 특성
    public void migPartMater(String filePath) throws Exception;
    
    
}
