package ext.ket.common.files.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import wt.content.ContentHolder;
import wt.fc.Persistable;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.StringUtil;
import ext.ket.common.files.util.DownloadView;
import ext.ket.shared.content.entity.ContentDTO;
import ext.ket.shared.content.service.KETContentHelper;
import ext.ket.shared.drm.DRMHelper;

@Controller
public class FileContentController implements ApplicationContextAware {

    @SuppressWarnings("unused")
    private WebApplicationContext context = null;

    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
	// TODO Auto-generated method stub

	this.context = (WebApplicationContext) arg0;
    }

    @RequestMapping("/pathDownLoad")
    public ModelAndView pathDownLoad(@RequestParam("path") String path) throws Exception {

	path = URLDecoder.decode(path, "utf-8");
	File file = new File(path);

	return new ModelAndView("download", "downloadFile", file);
    }

    @RequestMapping("/download")
    public ModelAndView download(@RequestParam("path") String path) throws Exception {
	String fullPath = DownloadView.CODEBASEPATH + File.separator + path;

	File file = new File(fullPath);

	return new ModelAndView("download", "downloadFile", file);
    }

    @RequestMapping("/copyDownload")
    public ModelAndView copyDownload(@RequestParam("path") String path, @RequestParam("name") String name) throws Exception {

	String zipDir = DownloadView.TEMPPATH + File.separator + "PPAPFILES_QMS_" + DateUtil.getCurrentTimestamp().getTime();
	File tempDir = new File(zipDir);
	tempDir.mkdir();

	File orgFile = new File(path);

	path = tempDir + File.separator + name;

	File copyFile = new File(path);

	FileOutputStream fos = new FileOutputStream(copyFile);

	// 암호화
	if (DRMHelper.useDrm) {
	    if (DRMHelper.isEncFile(orgFile)) {
		orgFile = DRMHelper.Decryptupload(orgFile, name);
	    }
	    orgFile = DRMHelper.encryptFile(orgFile, name);
	}

	FileInputStream is = new FileInputStream(orgFile);
	IOUtils.copy(is, fos);

	is.close();
	fos.flush();
	fos.close();

	return new ModelAndView("download", "downloadFile", copyFile);
    }

    @RequestMapping("/common/attachFilesForm")
    public ModelAndView attachFilesForm(ModelAndView model, @RequestParam Map<String, Object> reqMap) throws Exception {

	String oid = (String) reqMap.get("oid");
	String mode = (String) reqMap.get("mode");
	String tid = (String) reqMap.get("tid");
	model.addObject("mode", mode);
	model.addObject("tid", tid);

	if (StringUtil.checkString(oid)) {

	    model.addObject("oid", oid);

	    Persistable pbo = CommonUtil.getObject(oid);
	    ContentHolder holder = (ContentHolder) pbo;
	    ArrayList<ContentDTO> contentList = KETContentHelper.manager.getSecondaryContents(holder);
	    model.addObject("holderOid", CommonUtil.getOIDString(holder));

	    if (StringUtil.checkString(tid)) {
		for (Iterator<ContentDTO> iter = contentList.iterator(); iter.hasNext();) {
		    ContentDTO dto = iter.next();
		    if (tid.equals(dto.getDescription())) {
			continue;
		    }
		    iter.remove();
		}
	    }

	    model.addObject("contentList", contentList);
	}

	model.setViewName("noExtends:/common/attachFilesForm");

	return model;
    }
}