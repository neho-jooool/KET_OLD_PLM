package ext.ket.orther.service;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import ext.ket.orther.entity.dto.OrtherDto;
import wt.method.RemoteInterface;

@RemoteInterface
public interface OrtherService {
    public OrtherDto getDividendInfoByEmpNo(OrtherDto paramObject) throws Exception; 
    public void dividendSave(OrtherDto paramObject) throws Exception;
    public void updateConvertFiles2PLM (JSONObject paramObj) throws Exception;
    public String decryptMarkAnyDrmFile(List<MultipartFile> fileList) throws Exception;
}
