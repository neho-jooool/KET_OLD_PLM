package ext.ket.yesone.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.web.multipart.MultipartFile;

import ext.ket.yesone.entity.KETYesoneDTO;

public class CreateXml {
    
    public KETYesoneDTO String2xml(KETYesoneDTO ketYesoneDTO){
	
	String empNo = ketYesoneDTO.getEmpNo();
	String strXml = ketYesoneDTO.getStrXml();
	
	String filePath = "D:\\ptc\\yesone\\"+empNo;
	
	File dir = new File(filePath);
	
	if (!dir.isDirectory()) {
	    dir.mkdirs();
	}
	
	String xmlfilePath = filePath+"\\"+empNo+".xml";
	 
	try {
	    MultipartFile file = ketYesoneDTO.getPrimaryFile();
	    
	    String pdfFilePath = filePath+"\\"+file.getOriginalFilename();
	    
	    File f = new File(pdfFilePath);
	    
	    file.transferTo(f);
	    
	    FileOutputStream fos = new FileOutputStream(xmlfilePath);
	    OutputStreamWriter osw = new OutputStreamWriter(fos, "utf-8");

	    BufferedWriter fw = new BufferedWriter(osw);

	    fw.write(strXml);
	    fw.flush();
	    fw.close();
	    ketYesoneDTO.setFilePath(xmlfilePath);
	} catch (Exception e) {
	    e.printStackTrace();
	    ketYesoneDTO.setValidityMsg("xml 생성 중 오류가 발생했습니다.");
	}
	
	return ketYesoneDTO;

    }

    
}
