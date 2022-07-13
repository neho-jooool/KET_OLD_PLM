package ext.ket.yesone.service;

import ext.ket.yesone.entity.KETYesoneDTO;
import wt.method.RemoteInterface;


@RemoteInterface
public interface YesoneService {
    
    //연말정산간소화 서비스에서 내려받은 PDF 검증
    public KETYesoneDTO validityCheckPDF(KETYesoneDTO ketYesonDTO) throws Exception;
    
    public KETYesoneDTO xmlConvert2rdb(KETYesoneDTO ketYesonDTO) throws Exception;
}
