package ext.ket.shared.log.service;

import java.util.List;
import java.util.Locale;

import wt.method.RemoteInterface;
import e3ps.common.web.PageControl;
import ext.ket.shared.log.entity.dto.EcmLogDTO;
import ext.ket.shared.log.entity.dto.EcmLogSearchDTO;

@RemoteInterface
public interface KetLogService {

    // 로그 검색
    public PageControl searchEcoLogList(EcmLogSearchDTO ecmLogSearchDTO, String pageTotalSize) throws Exception;

    // 로그 상세 조회
    public List<EcmLogDTO> viewDetailEcoLog(EcmLogDTO dto, Locale locale) throws Exception;

    // 에러 복구 실행
    public boolean recoverEcoError(EcmLogDTO dto) throws Exception;

    // 로그 삭제
    public void deleteLog(EcmLogDTO dto, Locale locale) throws Exception;

    // 로그 저장
    public void insertLog(EcmLogDTO dto) throws Exception;
    
    // Test
    public void testLog() throws Exception;

}
