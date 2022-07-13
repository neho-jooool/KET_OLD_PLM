package ext.ket.dms.officeDocConvert.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ext.ket.dms.officeDocConvert.service.OfficeDocConvertHelper;

/**
 * @클래스명 : OfficeDocConvertController
 * @작성자 : 황정태
 * @작성일 : 2018. 09. 30.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 */
@Controller
@RequestMapping("/dms/officeDocConvert/*")
public class OfficeDocConvertController {
    
    @RequestMapping("/updateConvertFiles2PLM")
    @ResponseBody
    public String updateConvertFiles2PLM(HttpServletRequest request) throws Exception {
	
	try{
	    String str;
	    StringBuffer paramData = new StringBuffer();
	    BufferedReader br = null;

	    br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
	    while ((str = br.readLine()) != null) {
		paramData.append(str);
	    }

	    JSONObject obj = (JSONObject) JSONValue.parse(paramData.toString());

	    OfficeDocConvertHelper.service.updateConvertFiles2PLM(obj);
	}catch(Exception e){
	    e.printStackTrace();
	    throw e;
	}
	return "Success";
    }

}
