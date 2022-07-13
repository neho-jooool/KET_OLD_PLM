package ext.ket.edm.stamping.service;

import wt.method.RemoteInterface;
import ext.ket.edm.entity.KETDrawingDistRequest;

@RemoteInterface
public interface StampingService {

    // Stamping 실행이 완료된 것인지 아닌지 판단
    public boolean isReceivedAlready(String reqOid) throws Exception;

    // Stamping CAD 파일 내려주고, XML 생성하고, xml full path return
    public String makeXmlDownCadDataByOid(String reqOid) throws Exception;

    // Stamping 결과를 저장한다.
    public boolean saveDrawingDeployRequest(String reqOid, String xmlFileFullPath) throws Exception;

    // Queue 입력시에 Samping 정보 저장 및 상태 update
    public void createKETStampingWhenQueueInput(KETDrawingDistRequest drawingDistReq) throws Exception;

    // Fail이 났을 경우에 에러 정보 저장
    public boolean saveErrorLog(String reqOid, String errorWhere, String errorType, String errorLog, String xmlFilePath) throws Exception;

    // stamping server someTime call, 만일에 shutdown 대비
    public boolean inputQueueDrawingDistReq() throws Exception;

}
