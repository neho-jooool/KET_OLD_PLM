package ext.ket.project.issue.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ext.ket.common.files.util.DownloadView;
import ext.ket.project.issue.service.KETIssueHelper;

@Controller
@RequestMapping("/project/issue/*")
public class KETIssueController {

    @ResponseBody
    @RequestMapping("/issueExcelUpload")
    public Map<String, Object> issueExcelUpload(@RequestParam Map<String, Object> reqMap, @RequestParam("uploadFile") MultipartFile file) {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {

	    Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss_SS");
	    String tmpFileName = sdf.format(cal.getTime());

	    File uploadFile = new File(DownloadView.TEMPPATH + File.separator + tmpFileName + "_" + file.getOriginalFilename());
	    file.transferTo(uploadFile);

	    KETIssueHelper.service.issueExcelUpload(reqMap, uploadFile);

	    resMap.put("message", "저장되었습니다.");
	    resMap.put("result", true);

	} catch (Exception e) {
	    e.printStackTrace();
	    resMap.put("result", false);
	    resMap.put("message", e.getLocalizedMessage());
	}

	return resMap;
    }

}
