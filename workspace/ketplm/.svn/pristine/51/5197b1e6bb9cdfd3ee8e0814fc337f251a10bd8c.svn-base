package ext.ket.dqm.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.lifecycle.LifeCycleManaged;
import wt.org.WTUser;
import wt.part.WTPart;
import wt.pds.StatementSpec;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionHelper;
import wt.workflow.engine.WfProcess;
import wt.workflow.work.WfAssignmentState;
import wt.workflow.work.WorkItem;
import e3ps.common.code.NumberCode;
import e3ps.common.message.KETMessageService;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.NumberCodeUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.PageControl;
import e3ps.ecm.entity.KETEcrDqmLink;
import e3ps.ecm.entity.KETProdChangeRequest;
import e3ps.groupware.company.PeopleData;
import e3ps.project.E3PSProject;
import e3ps.project.ProductInfo;
import e3ps.project.ProductProject;
import e3ps.project.ProjectProductInfoLink;
import e3ps.project.beans.E3PSProjectData;
import e3ps.project.outputtype.OEMProjectType;
import ext.ket.bom.query.KETBOMQueryBean;
import ext.ket.dqm.entity.KETDqmAction;
import ext.ket.dqm.entity.KETDqmClose;
import ext.ket.dqm.entity.KETDqmDTO;
import ext.ket.dqm.entity.KETDqmHeader;
import ext.ket.dqm.entity.KETDqmRaise;
import ext.ket.dqm.entity.KETDqmTodoAtivity;
import ext.ket.dqm.service.DqmHelper;
import ext.ket.part.entity.KETPartClassification;
import ext.ket.part.util.PartUtil;
import ext.ket.shared.code.service.CodeHelper;
import ext.ket.shared.content.entity.ContentDTO;
import ext.ket.shared.content.service.KETContentHelper;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.util.EjsConverUtil;
import ext.ket.shared.util.SearchUtil;
import ext.ket.shared.util.SessionUtil;

/**
 * @클래스명 : DqmController
 * @작성자 : KKW
 * @작성일 : 2014. 7. 21.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 */
@Controller
@RequestMapping("/dqm/*")
public class DqmController {
    @RequestMapping("/dqmList")
    public void dqmList() throws Exception {
    }

    @RequestMapping("/searchDqmPopup")
    public void searchDqmPopup() throws Exception {
    }

    @RequestMapping("/closeDelete")
    public void closeDelete(KETDqmDTO ketDqmDTO, HttpServletResponse response, HttpServletRequest request) throws Exception {
	DqmHelper.service.deleteClose(ketDqmDTO);

	String str = "";
	response.setContentType("text/html;charset=KSC5601");
	PrintWriter pwriter = response.getWriter();
	str = "\n <script language=\"javascript\">\n alert(\""
	        + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "01699")/* 삭제되었습니다. */+ "\");"
	        + "\n try{ parent.parent.opener.dqm.search();  } catch(error){ }  \n"
	        + "\n parent.location.href='/plm/ext/dqm/closeCreateForm.do?oid=" + ketDqmDTO.getOid() + "';" + "\n </script>";
	pwriter.println(str);
	pwriter.close();
	return;
	// return "redirect:/ext/dqm/actionCreateForm.do?oid=" + ketDqmDTO.getOid();
    }

    @RequestMapping("/closeUpdate")
    public void closeUpdate(KETDqmDTO ketDqmDTO, HttpServletResponse response, HttpServletRequest request) throws Exception {
	ketDqmDTO = DqmHelper.service.modifyClose(ketDqmDTO);

	String str = "";
	response.setContentType("text/html;charset=KSC5601");
	PrintWriter pwriter = response.getWriter();
	str = "\n <script language=\"javascript\">\n alert(\""
	        + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "02454")/* "저장되었습니다." */+ "\");"
	        + "\n try{ parent.parent.opener.dqm.search();  } catch(error){ }  \n"
	        + "\n parent.location.href='/plm/ext/dqm/closeViewForm.do?oid=" + ketDqmDTO.getOid() + "';" + "\n </script>";
	pwriter.println(str);
	pwriter.close();
	return;
	// return "redirect:/ext/dqm/actionViewForm.do?oid=" + ketDqmDTO.getOid() + "&raiseOid=" + ketDqmDTO.getRaiseOid() + "&actionOid=" +
	// ketDqmDTO.getActionOid();
    }

    @RequestMapping("/closeCreate")
    public void closeCreate(KETDqmDTO ketDqmDTO, HttpServletResponse response, HttpServletRequest request) throws Exception {
	ketDqmDTO = DqmHelper.service.saveClose(ketDqmDTO);

	String str = "";
	response.setContentType("text/html;charset=KSC5601");
	PrintWriter pwriter = response.getWriter();
	str = "\n <script language=\"javascript\">\n alert(\""
	        + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "02454")/* "저장되었습니다." */+ "\");"
	        + "\n try{ parent.parent.opener.dqm.search();  } catch(error){ }  \n"
	        + "\n parent.location.href='/plm/ext/dqm/closeViewForm.do?oid=" + ketDqmDTO.getOid() + "';" + "\n </script>";
	pwriter.println(str);
	pwriter.close();
	return;
	// return "redirect:/ext/dqm/actionViewForm.do?oid=" + ketDqmDTO.getOid() + "&raiseOid=" + ketDqmDTO.getRaiseOid() + "&actionOid=" +
	// ketDqmDTO.getActionOid();
    }

    @RequestMapping("/closeUpdateForm")
    public void closeUpdateForm(String oid, Model model) throws Exception {
	KETDqmHeader ketDqmHeader = (KETDqmHeader) CommonUtil.getObject(oid);
	KETDqmRaise ketDqmRaise = ketDqmHeader.getRaise();
	KETDqmClose ketDqmClose = ketDqmHeader.getClose();

	KETDqmDTO ketDqmDTO = new KETDqmDTO();
	ketDqmDTO = ketDqmDTO.KETDqmDTO(ketDqmHeader, ketDqmDTO);
	ketDqmDTO = ketDqmDTO.KETDqmDTO(ketDqmClose, ketDqmRaise, ketDqmDTO);

	if (ketDqmClose != null && !ketDqmClose.equals(null)) {
	    model.addAttribute("primaryFile", KETContentHelper.manager.getPrimaryContent(ketDqmClose));
	    model.addAttribute("secondaryFiles", KETContentHelper.manager.getSecondaryContents(ketDqmClose));
	} else {
	    model.addAttribute("primaryFile", null);
	    model.addAttribute("secondaryFiles", null);
	}

	model.addAttribute("curState", ketDqmHeader.getDqmStateCode());
	model.addAttribute("dqm", ketDqmDTO);
    }

    @RequestMapping("/closeViewForm")
    public void closeViewForm(String oid, Model model) throws Exception {
	KETDqmHeader ketDqmHeader = (KETDqmHeader) CommonUtil.getObject(oid);
	KETDqmRaise ketDqmRaise = ketDqmHeader.getRaise();
	KETDqmClose ketDqmClose = ketDqmHeader.getClose();

	KETDqmDTO ketDqmDTO = new KETDqmDTO();
	ketDqmDTO = ketDqmDTO.KETDqmDTO(ketDqmHeader, ketDqmDTO);
	ketDqmDTO = ketDqmDTO.KETDqmDTO(ketDqmClose, ketDqmRaise, ketDqmDTO);

	boolean todoUserFlag = false;

	QuerySpec query = new QuerySpec();
	SearchCondition sc = null;
	int idxTodoActivity = query.appendClassList(KETDqmTodoAtivity.class, true);

	sc = new SearchCondition(KETDqmTodoAtivity.class, "headerReference.key.id", SearchCondition.EQUAL,
	        CommonUtil.getOIDLongValue(ketDqmHeader));
	query.appendWhere(sc, new int[] { idxTodoActivity });
	query.appendAnd();
	sc = new SearchCondition(KETDqmTodoAtivity.class, KETDqmTodoAtivity.TASK_CODE, SearchCondition.EQUAL, "DQM_CLOSE");

	query.appendWhere(sc, new int[] { idxTodoActivity });

	QueryResult qr = PersistenceHelper.manager.find(query);
	while (qr.hasMoreElements()) {
	    Object[] tempObj = (Object[]) qr.nextElement();
	    KETDqmTodoAtivity ketDqmTodoAtivity = (KETDqmTodoAtivity) tempObj[0];

	    WTUser workItemWTUser = null;
	    WfProcess process = null;
	    QuerySpec spec = new QuerySpec(WorkItem.class);
	    SearchUtil.appendEQUAL(spec, WorkItem.class, "primaryBusinessObject.key.classname",
		    CommonUtil.getRefOID((LifeCycleManaged) ketDqmTodoAtivity), 0);
	    QueryResult inQr = PersistenceHelper.manager.find((StatementSpec) spec);
	    if (inQr.hasMoreElements()) {
		WorkItem item = (WorkItem) inQr.nextElement();
		if (item.getStatus().equals(WfAssignmentState.POTENTIAL)) {
		    workItemWTUser = (WTUser) item.getOwnership().getOwner().getPrincipal();

		    if (workItemWTUser.equals((WTUser) SessionHelper.manager.getPrincipalReference().getPrincipal())) {
			todoUserFlag = true;
		    }
		}
	    }
	}

	if (ketDqmClose != null && !ketDqmClose.equals(null)) {
	    model.addAttribute("primaryFile", KETContentHelper.manager.getPrimaryContent(ketDqmClose));
	    model.addAttribute("secondaryFiles", KETContentHelper.manager.getSecondaryContents(ketDqmClose));
	} else {
	    model.addAttribute("primaryFile", null);
	    model.addAttribute("secondaryFiles", null);
	}

	model.addAttribute("todoUserFlag", todoUserFlag);
	model.addAttribute("curState", ketDqmHeader.getDqmStateCode());
	model.addAttribute("dqm", ketDqmDTO);
    }

    @RequestMapping("/closeCreateForm")
    public void closeCreateForm(String oid, Model model) throws Exception {
	KETDqmHeader ketDqmHeader = (KETDqmHeader) CommonUtil.getObject(oid);

	KETDqmDTO ketDqmDTO = new KETDqmDTO();
	ketDqmDTO = ketDqmDTO.KETDqmDTO(ketDqmHeader, ketDqmDTO);

	model.addAttribute("curState", ketDqmHeader.getDqmStateCode());
	model.addAttribute("dqm", ketDqmDTO);
    }

    @RequestMapping("/create")
    public void create(KETDqmDTO ketDqmDTO, HttpServletResponse response, HttpServletRequest request) throws Exception {
	ketDqmDTO = DqmHelper.service.saveRaise(ketDqmDTO);
	String str = "";
	response.setContentType("text/html;charset=KSC5601");
	PrintWriter pwriter = response.getWriter();
	str = "\n <script language=\"javascript\">\n alert(\""
	        + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "02454")/* "저장되었습니다." */+ "\");"
	        + "\n try{ parent.parent.opener.dqm.search();  } catch(error){ }  \n";
	if (!(ketDqmDTO.getOutputOid().equals("") || ketDqmDTO.getOutputOid().equals(null))) {
	    str += "\n try{ parent.parent.opener.goTaskPage();  } catch(error){ }  \n";
	}

	if ("OK".equals(ketDqmDTO.getByProjectDqm())) {
	    str += "\n  try { parent.parent.opener.location.reload(); } catch(error) {}  \n";
	}

	str += "\n parent.parent.location.href='/plm/ext/dqm/dqmMainForm.do?type=view&oid=" + ketDqmDTO.getOid() + "';" + "\n </script>";

	pwriter.println(str);
	pwriter.close();
	return;
	// return "redirect:/ext/dqm/viewForm.do?oid=" + ketDqmDTO.getOid() + "&raiseOid=" + ketDqmDTO.getRaiseOid();
    }

    @RequestMapping("/actionDelete")
    public void actionDelete(KETDqmDTO ketDqmDTO, HttpServletResponse response, HttpServletRequest request) throws Exception {
	DqmHelper.service.deleteAction(ketDqmDTO);

	String str = "";
	response.setContentType("text/html;charset=KSC5601");
	PrintWriter pwriter = response.getWriter();
	str = "\n <script language=\"javascript\">\n alert(\""
	        + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "01699")/* 삭제되었습니다. */+ "\");"
	        + "\n try{ parent.parent.opener.dqm.search();  } catch(error){ }  \n"
	        + "\n parent.location.href='/plm/ext/dqm/actionCreateForm.do?oid=" + ketDqmDTO.getOid() + "';" + "\n </script>";
	pwriter.println(str);
	pwriter.close();
	return;
	// return "redirect:/ext/dqm/actionCreateForm.do?oid=" + ketDqmDTO.getOid();
    }

    @RequestMapping("/actionUpdate")
    public void actionUpdate(KETDqmDTO ketDqmDTO, HttpServletResponse response, HttpServletRequest request) throws Exception {
	ketDqmDTO = DqmHelper.service.modifyAction(ketDqmDTO);

	String str = "";
	response.setContentType("text/html;charset=KSC5601");
	PrintWriter pwriter = response.getWriter();
	str = "\n <script language=\"javascript\">\n alert(\""
	        + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "02454")/* "저장되었습니다." */+ "\");"
	        + "\n try{ parent.parent.opener.dqm.search();  } catch(error){ }  \n"
	        + "\n  try { parent.parent.opener.location.reload(); } catch(error) {}  \n"
	        + "\n parent.location.href='/plm/ext/dqm/actionViewForm.do?oid=" + ketDqmDTO.getOid() + "';" + "\n </script>";
	pwriter.println(str);
	pwriter.close();
	return;
	// return "redirect:/ext/dqm/actionViewForm.do?oid=" + ketDqmDTO.getOid() + "&raiseOid=" + ketDqmDTO.getRaiseOid() + "&actionOid=" +
	// ketDqmDTO.getActionOid();
    }

    @RequestMapping("/actionCreate")
    public void actionCreate(KETDqmDTO ketDqmDTO, HttpServletResponse response, HttpServletRequest request) throws Exception {
	ketDqmDTO = DqmHelper.service.saveAction(ketDqmDTO);

	String str = "";
	response.setContentType("text/html;charset=KSC5601");
	PrintWriter pwriter = response.getWriter();
	str = "\n <script language=\"javascript\">\n alert(\""
	        + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "02454")/* "저장되었습니다." */+ "\");"
	        + "\n try{ parent.parent.opener.dqm.search();  } catch(error){ }  \n"
	        + "\n  try { parent.parent.opener.location.reload(); } catch(error) {}  \n"
	        + "\n parent.location.href='/plm/ext/dqm/actionViewForm.do?oid=" + ketDqmDTO.getOid() + "';" + "\n </script>";
	pwriter.println(str);
	pwriter.close();
	return;
	// return "redirect:/ext/dqm/actionViewForm.do?oid=" + ketDqmDTO.getOid() + "&raiseOid=" + ketDqmDTO.getRaiseOid() + "&actionOid=" +
	// ketDqmDTO.getActionOid();
    }

    @RequestMapping("/actionComplete")
    public void actionComplete(KETDqmDTO ketDqmDTO, HttpServletResponse response, HttpServletRequest request) throws Exception {
	DqmHelper.service.actionComplete(ketDqmDTO);

	String str = "";
	response.setContentType("text/html;charset=KSC5601");
	PrintWriter pwriter = response.getWriter();
	str = "\n <script language=\"javascript\">\n alert(\""
	        + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "02454")/* "저장되었습니다." */+ "\");"
	        + "\n try{ parent.parent.opener.dqm.search();  } catch(error){ }  \n"
	        + "\n  try { parent.parent.opener.location.reload(); } catch(error) {}  \n"
	        + "\n parent.location.href='/plm/ext/dqm/dqmMainForm.do?type=DQM_ACTION&oid=" + ketDqmDTO.getOid() + "';" + "\n </script>";
	pwriter.println(str);
	pwriter.close();
	return;
	// return "redirect:/ext/dqm/actionViewForm.do?oid=" + ketDqmDTO.getOid() + "&raiseOid=" + ketDqmDTO.getRaiseOid() + "&actionOid=" +
	// ketDqmDTO.getActionOid();
    }

    @RequestMapping("/dqmMainForm")
    public void dqmMainForm(String type, String oid, String isIframe, String outputOid, Model model) throws Exception {
	String copyOid = StringUtil.checkNull(oid);
	if ("copy".equals(type)) {
	    oid = "";
	}
	String checkOid = StringUtil.checkNull(oid);
	boolean authCheckFlag = true;
	// if (checkOid.equals("")) {
	// authCheckFlag = true;
	// } else {
	// if (CommonUtil.isAdmin()) {
	// authCheckFlag = true;
	// } else {
	// authCheckFlag = DqmHelper.service.authCheck(checkOid, type);
	// }
	// }

	if (type.equals("raise")) {
	    KETDqmRaise ketDqmRaise = (KETDqmRaise) CommonUtil.getObject(oid);
	    KETDqmHeader ketDqmHeader = null;

	    QuerySpec query = new QuerySpec();
	    SearchCondition sc = null;
	    int idxHeaer = query.appendClassList(KETDqmHeader.class, true);

	    sc = new SearchCondition(KETDqmHeader.class, "raiseReference.key.id", SearchCondition.EQUAL,
		    CommonUtil.getOIDLongValue(ketDqmRaise));
	    query.appendWhere(sc, new int[] { idxHeaer });

	    QueryResult qr = PersistenceHelper.manager.find(query);

	    while (qr.hasMoreElements()) {
		Object[] tempObj = (Object[]) qr.nextElement();
		ketDqmHeader = (KETDqmHeader) tempObj[0];
	    }

	    KETDqmClose ketDqmClose = ketDqmHeader.getClose();
	    KETDqmAction ketDqmAction = ketDqmHeader.getAction();

	    KETDqmDTO ketDqmDTO = new KETDqmDTO();
	    ketDqmDTO = ketDqmDTO.KETDqmDTOOidSetting(ketDqmHeader, ketDqmRaise, ketDqmAction, ketDqmClose, ketDqmDTO);

	    model.addAttribute("curState", ketDqmHeader.getDqmStateCode());
	    model.addAttribute("dqm", ketDqmDTO);
	} else if (type.equals("action")) {
	    KETDqmAction ketDqmAction = (KETDqmAction) CommonUtil.getObject(oid);
	    KETDqmHeader ketDqmHeader = null;

	    QuerySpec query = new QuerySpec();
	    SearchCondition sc = null;
	    int idxHeaer = query.appendClassList(KETDqmHeader.class, true);

	    sc = new SearchCondition(KETDqmHeader.class, "actionReference.key.id", SearchCondition.EQUAL,
		    CommonUtil.getOIDLongValue(ketDqmAction));
	    query.appendWhere(sc, new int[] { idxHeaer });

	    QueryResult qr = PersistenceHelper.manager.find(query);

	    while (qr.hasMoreElements()) {
		Object[] tempObj = (Object[]) qr.nextElement();
		ketDqmHeader = (KETDqmHeader) tempObj[0];
		CommonUtil.getOIDString(ketDqmHeader);
	    }

	    KETDqmClose ketDqmClose = ketDqmHeader.getClose();
	    KETDqmRaise ketDqmRaise = ketDqmHeader.getRaise();

	    KETDqmDTO ketDqmDTO = new KETDqmDTO();
	    ketDqmDTO = ketDqmDTO.KETDqmDTOOidSetting(ketDqmHeader, ketDqmRaise, ketDqmAction, ketDqmClose, ketDqmDTO);

	    model.addAttribute("curState", ketDqmHeader.getDqmStateCode());
	    model.addAttribute("dqm", ketDqmDTO);
	} else if (!checkOid.equals("") && !checkOid.equals(null)) {

	    KETDqmHeader ketDqmHeader = (KETDqmHeader) CommonUtil.getObject(oid);
	    KETDqmRaise ketDqmRaise = ketDqmHeader.getRaise();
	    KETDqmAction ketDqmAction = ketDqmHeader.getAction();
	    KETDqmClose ketDqmClose = ketDqmHeader.getClose();

	    KETDqmDTO ketDqmDTO = new KETDqmDTO();
	    ketDqmDTO = ketDqmDTO.KETDqmDTOOidSetting(ketDqmHeader, ketDqmRaise, ketDqmAction, ketDqmClose, ketDqmDTO);

	    model.addAttribute("curState", ketDqmHeader.getDqmStateCode());
	    model.addAttribute("dqm", ketDqmDTO);
	}
	model.addAttribute("authCheckFlag", authCheckFlag);
	model.addAttribute("type", type);
	model.addAttribute("isIframe", isIframe);
	model.addAttribute("outputOid", outputOid);
	model.addAttribute("copyOid", copyOid);
    }

    @RequestMapping("/createForm")
    public void createForm(String outputOid, String copyOid, Model model) throws Exception {
	WTUser sessionUser = (WTUser) SessionHelper.manager.getPrincipal();
	String closerName = sessionUser.getFullName();
	model.addAttribute("outputOid", outputOid);
	KETDqmDTO ketDqmDTO = new KETDqmDTO();
	if (StringUtils.isNotEmpty(copyOid)) {// 기본정보 복사
	    KETDqmHeader ketDqmHeader = (KETDqmHeader) CommonUtil.getObject(copyOid);
	    if (ketDqmHeader != null) {
		KETDqmRaise ketDqmRaise = ketDqmHeader.getRaise();

		ketDqmDTO = ketDqmDTO.KETDqmDTO(ketDqmHeader, ketDqmDTO);
		ketDqmDTO = ketDqmDTO.KETDqmDTO(ketDqmRaise, ketDqmDTO);
		PeopleData peopleData = new PeopleData(ketDqmRaise.getActionUser());
		if (peopleData.isDiable) {
		    ketDqmDTO.setActionUserName("");
		    ketDqmDTO.setActionUserOid("");
		}
	    }
	}
	ketDqmDTO.setCloserName(closerName);
	ketDqmDTO.setCloserOid(CommonUtil.getOIDString(sessionUser));
	model.addAttribute("dqm", ketDqmDTO);
	// model.addAttribute("closerName", closerName);
	// model.addAttribute("closerOid", CommonUtil.getOIDString(sessionUser));
    }

    @RequestMapping("/actionCreateForm")
    public void actionCreateForm(String popup_yn, String oid, String raiseOid, Model model) throws Exception {
	KETDqmHeader ketDqmHeader = (KETDqmHeader) CommonUtil.getObject(oid);

	KETDqmDTO ketDqmDTO = new KETDqmDTO();
	ketDqmDTO = ketDqmDTO.KETDqmDTO(ketDqmHeader, ketDqmDTO);
	ketDqmDTO = ketDqmDTO.KETDqmDTO(ketDqmHeader.getRaise(), ketDqmDTO);

	model.addAttribute("popup_yn", popup_yn);
	model.addAttribute("curState", ketDqmHeader.getDqmStateCode());
	model.addAttribute("dqm", ketDqmDTO);
    }

    @RequestMapping("/actionViewForm")
    public void actionViewForm(String popup_yn, String oid, String raiseOid, String actionOid, Model model) throws Exception {

	KETDqmHeader ketDqmHeader = (KETDqmHeader) CommonUtil.getObject(oid);
	KETDqmRaise ketDqmRaise = ketDqmHeader.getRaise();
	KETDqmAction ketDqmAction = ketDqmHeader.getAction();

	KETDqmDTO ketDqmDTO = new KETDqmDTO();
	ketDqmDTO = ketDqmDTO.KETDqmDTO(ketDqmHeader, ketDqmDTO);
	ketDqmDTO = ketDqmDTO.KETDqmDTO(ketDqmAction, ketDqmRaise, ketDqmDTO);

	KETDqmClose ketDqmClose = ketDqmHeader.getClose();
	if (ketDqmClose != null) {
	    ketDqmDTO.setProblemReflectYn(ketDqmClose.getProblemReflectYn());
	}

	boolean todoUserFlag = false;

	QuerySpec query = new QuerySpec();
	SearchCondition sc = null;
	int idxTodoActivity = query.appendClassList(KETDqmTodoAtivity.class, true);

	sc = new SearchCondition(KETDqmTodoAtivity.class, "headerReference.key.id", SearchCondition.EQUAL,
	        CommonUtil.getOIDLongValue(ketDqmHeader));
	query.appendWhere(sc, new int[] { idxTodoActivity });
	query.appendAnd();
	sc = new SearchCondition(KETDqmTodoAtivity.class, KETDqmTodoAtivity.TASK_CODE, SearchCondition.EQUAL, "DQM_ACTION");

	query.appendWhere(sc, new int[] { idxTodoActivity });

	QueryResult qr = PersistenceHelper.manager.find(query);
	while (qr.hasMoreElements()) {
	    Object[] tempObj = (Object[]) qr.nextElement();
	    KETDqmTodoAtivity ketDqmTodoAtivity = (KETDqmTodoAtivity) tempObj[0];

	    WTUser workItemWTUser = null;
	    WfProcess process = null;
	    QuerySpec spec = new QuerySpec(WorkItem.class);
	    SearchUtil.appendEQUAL(spec, WorkItem.class, "primaryBusinessObject.key.classname",
		    CommonUtil.getRefOID((LifeCycleManaged) ketDqmTodoAtivity), 0);
	    QueryResult inQr = PersistenceHelper.manager.find((StatementSpec) spec);
	    if (inQr.hasMoreElements()) {
		WorkItem item = (WorkItem) inQr.nextElement();
		if (item.getStatus().equals(WfAssignmentState.POTENTIAL)) {
		    workItemWTUser = (WTUser) item.getOwnership().getOwner().getPrincipal();

		    if (workItemWTUser.equals((WTUser) SessionHelper.manager.getPrincipalReference().getPrincipal())) {
			todoUserFlag = true;
		    }
		}
	    }
	}

	if (ketDqmAction != null && !ketDqmAction.equals(null)) {
	    model.addAttribute("primaryFile", KETContentHelper.manager.getPrimaryContent(ketDqmAction));
	    model.addAttribute("secondaryFiles", KETContentHelper.manager.getSecondaryContents(ketDqmAction));
	} else {
	    model.addAttribute("primaryFile", null);
	    model.addAttribute("secondaryFiles", null);
	}

	model.addAttribute("todoUserFlag", todoUserFlag);
	model.addAttribute("popup_yn", popup_yn);
	model.addAttribute("curState", ketDqmHeader.getDqmStateCode());
	model.addAttribute("dqm", ketDqmDTO);
    }

    @RequestMapping("/printDqmViewForm")
    public void printDqmViewForm(String oid, Model model) throws Exception {
	KETDqmHeader ketDqmHeader = (KETDqmHeader) CommonUtil.getObject(oid);
	KETDqmRaise ketDqmRaise = ketDqmHeader.getRaise();
	KETDqmAction ketDqmAction = ketDqmHeader.getAction();

	KETDqmDTO ketDqmDTO = new KETDqmDTO();
	ketDqmDTO = ketDqmDTO.KETDqmDTOPrint(ketDqmHeader, ketDqmAction, ketDqmRaise, ketDqmDTO);

	ArrayList<ContentDTO> contentDTOList = new ArrayList<ContentDTO>();

	if (ketDqmRaise != null && !ketDqmRaise.equals(null)) {
	    contentDTOList = KETContentHelper.manager.getSecondaryContents(ketDqmRaise);
	}

	if (ketDqmAction != null && !ketDqmAction.equals(null)) {

	    ArrayList<ContentDTO> contentDTOSubList = KETContentHelper.manager.getSecondaryContents(ketDqmAction);

	    if (contentDTOSubList != null) {
		for (int i = 0; i < contentDTOSubList.size(); i++) {
		    contentDTOList.add(contentDTOSubList.get(i));
		}
	    }
	}

	model.addAttribute("secondaryFiles", contentDTOList);
	model.addAttribute("curState", ketDqmHeader.getDqmStateCode());
	model.addAttribute("dqm", ketDqmDTO);
    }

    @RequestMapping("/printActionViewForm")
    public void printActionViewForm(String popup_yn, String oid, String raiseOid, String actionOid, Model model) throws Exception {

	KETDqmHeader ketDqmHeader = (KETDqmHeader) CommonUtil.getObject(oid);
	KETDqmRaise ketDqmRaise = ketDqmHeader.getRaise();
	KETDqmAction ketDqmAction = ketDqmHeader.getAction();

	KETDqmDTO ketDqmDTO = new KETDqmDTO();
	ketDqmDTO = ketDqmDTO.KETDqmDTO(ketDqmHeader, ketDqmDTO);
	ketDqmDTO = ketDqmDTO.KETDqmDTO(ketDqmAction, ketDqmRaise, ketDqmDTO);

	KETDqmClose ketDqmClose = ketDqmHeader.getClose();
	if (ketDqmClose != null) {
	    ketDqmDTO.setProblemReflectYn(ketDqmClose.getProblemReflectYn());
	}

	boolean todoUserFlag = false;

	QuerySpec query = new QuerySpec();
	SearchCondition sc = null;
	int idxTodoActivity = query.appendClassList(KETDqmTodoAtivity.class, true);

	sc = new SearchCondition(KETDqmTodoAtivity.class, "headerReference.key.id", SearchCondition.EQUAL,
	        CommonUtil.getOIDLongValue(ketDqmHeader));
	query.appendWhere(sc, new int[] { idxTodoActivity });
	query.appendAnd();
	sc = new SearchCondition(KETDqmTodoAtivity.class, KETDqmTodoAtivity.TASK_CODE, SearchCondition.EQUAL, "DQM_ACTION");

	query.appendWhere(sc, new int[] { idxTodoActivity });

	QueryResult qr = PersistenceHelper.manager.find(query);
	while (qr.hasMoreElements()) {
	    Object[] tempObj = (Object[]) qr.nextElement();
	    KETDqmTodoAtivity ketDqmTodoAtivity = (KETDqmTodoAtivity) tempObj[0];

	    WTUser workItemWTUser = null;
	    WfProcess process = null;
	    QuerySpec spec = new QuerySpec(WorkItem.class);
	    SearchUtil.appendEQUAL(spec, WorkItem.class, "primaryBusinessObject.key.classname",
		    CommonUtil.getRefOID((LifeCycleManaged) ketDqmTodoAtivity), 0);
	    QueryResult inQr = PersistenceHelper.manager.find((StatementSpec) spec);
	    if (inQr.hasMoreElements()) {
		WorkItem item = (WorkItem) inQr.nextElement();
		if (item.getStatus().equals(WfAssignmentState.POTENTIAL)) {
		    workItemWTUser = (WTUser) item.getOwnership().getOwner().getPrincipal();

		    if (workItemWTUser.equals((WTUser) SessionHelper.manager.getPrincipalReference().getPrincipal())) {
			todoUserFlag = true;
		    }
		}
	    }
	}

	if (ketDqmAction != null && !ketDqmAction.equals(null)) {
	    model.addAttribute("primaryFile", KETContentHelper.manager.getPrimaryContent(ketDqmAction));
	    model.addAttribute("secondaryFiles", KETContentHelper.manager.getSecondaryContents(ketDqmAction));
	} else {
	    model.addAttribute("primaryFile", null);
	    model.addAttribute("secondaryFiles", null);
	}

	model.addAttribute("todoUserFlag", todoUserFlag);
	model.addAttribute("popup_yn", popup_yn);
	model.addAttribute("curState", ketDqmHeader.getDqmStateCode());
	model.addAttribute("dqm", ketDqmDTO);
    }

    @RequestMapping("/rejectReasonForm")
    public void rejectReasonForm(String oid, Model model) throws Exception {
	model.addAttribute("oid", oid);
    }

    @RequestMapping("/viewForm")
    public void viewForm(String popup_yn, String oid, String raiseOid, Model model) throws Exception {

	KETDqmHeader ketDqmHeader = (KETDqmHeader) CommonUtil.getObject(oid);
	KETDqmRaise ketDqmRaise = ketDqmHeader.getRaise();

	KETDqmDTO ketDqmDTO = new KETDqmDTO();
	ketDqmDTO = ketDqmDTO.KETDqmDTO(ketDqmHeader, ketDqmDTO);
	ketDqmDTO = ketDqmDTO.KETDqmDTO(ketDqmRaise, ketDqmDTO);

	if (ketDqmRaise != null && !ketDqmRaise.equals(null)) {
	    model.addAttribute("primaryFile", KETContentHelper.manager.getPrimaryContent(ketDqmRaise));
	    model.addAttribute("secondaryFiles", KETContentHelper.manager.getSecondaryContents(ketDqmRaise));
	} else {
	    model.addAttribute("primaryFile", null);
	    model.addAttribute("secondaryFiles", null);
	}
	model.addAttribute("popup_yn", popup_yn);
	model.addAttribute("curState", ketDqmHeader.getDqmStateCode());
	model.addAttribute("dqm", ketDqmDTO);
    }

    @RequestMapping("/printViewForm")
    public void printViewForm(String popup_yn, String oid, String raiseOid, Model model) throws Exception {

	KETDqmHeader ketDqmHeader = (KETDqmHeader) CommonUtil.getObject(oid);
	KETDqmRaise ketDqmRaise = ketDqmHeader.getRaise();

	KETDqmDTO ketDqmDTO = new KETDqmDTO();
	ketDqmDTO = ketDqmDTO.KETDqmDTO(ketDqmHeader, ketDqmDTO);
	ketDqmDTO = ketDqmDTO.KETDqmDTO(ketDqmRaise, ketDqmDTO);

	if (ketDqmRaise != null && !ketDqmRaise.equals(null)) {
	    model.addAttribute("primaryFile", KETContentHelper.manager.getPrimaryContent(ketDqmRaise));
	    model.addAttribute("secondaryFiles", KETContentHelper.manager.getSecondaryContents(ketDqmRaise));
	} else {
	    model.addAttribute("primaryFile", null);
	    model.addAttribute("secondaryFiles", null);
	}
	model.addAttribute("popup_yn", popup_yn);
	model.addAttribute("curState", ketDqmHeader.getDqmStateCode());
	model.addAttribute("dqm", ketDqmDTO);
    }

    @RequestMapping("/delete")
    public void delete(KETDqmDTO ketDqmDTO, HttpServletResponse response, HttpServletRequest request) throws Exception {
	DqmHelper.service.deleteRaise(ketDqmDTO);
	// return "redirect:/ext/dqm/createForm.do"; 작성화면으로 리턴?
	String str = "";
	response.setContentType("text/html;charset=KSC5601");
	PrintWriter pwriter = response.getWriter();
	str = "\n <script language=\"javascript\">\n alert(\""
	        + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "01699")/* 삭제되었습니다. */+ "\");"
	        + "\n try{ parent.parent.opener.dqm.search();  } catch(error){ }  \n"
	        + "\n  try { parent.parent.opener.location.reload(); } catch(error) {}  \n"
	        + "\n try{ parent.parent.opener.feedbackAfterPopup();  } catch(error){ }  \n" + "\n parent.parent.window.close();"
	        + "\n </script>";
	pwriter.println(str);
	pwriter.close();
	return;
    }

    @RequestMapping("/updateForm")
    public void updateForm(String popup_yn, String oid, String raiseOid, Model model) throws Exception {
	KETDqmHeader ketDqmHeader = (KETDqmHeader) CommonUtil.getObject(oid);
	KETDqmRaise ketDqmRaise = ketDqmHeader.getRaise();

	KETDqmDTO ketDqmDTO = new KETDqmDTO();
	ketDqmDTO = ketDqmDTO.KETDqmDTO(ketDqmHeader, ketDqmDTO);
	ketDqmDTO = ketDqmDTO.KETDqmDTO(ketDqmRaise, ketDqmDTO);

	if (ketDqmRaise != null && !ketDqmRaise.equals(null)) {
	    model.addAttribute("primaryFile", KETContentHelper.manager.getPrimaryContent(ketDqmRaise));
	    model.addAttribute("secondaryFiles", KETContentHelper.manager.getSecondaryContents(ketDqmRaise));
	} else {
	    model.addAttribute("primaryFile", null);
	    model.addAttribute("secondaryFiles", null);
	}
	model.addAttribute("popup_yn", popup_yn);
	model.addAttribute("curState", ketDqmHeader.getDqmStateCode());
	model.addAttribute("dqm", ketDqmDTO);
    }

    @RequestMapping("/actionUpdateForm")
    public void actionUpdateForm(String popup_yn, String oid, String raiseOid, Model model) throws Exception {
	KETDqmHeader ketDqmHeader = (KETDqmHeader) CommonUtil.getObject(oid);
	KETDqmRaise ketDqmRaise = ketDqmHeader.getRaise();
	KETDqmAction ketDqmAction = ketDqmHeader.getAction();

	KETDqmDTO ketDqmDTO = new KETDqmDTO();
	ketDqmDTO = ketDqmDTO.KETDqmDTO(ketDqmHeader, ketDqmDTO);
	ketDqmDTO = ketDqmDTO.KETDqmDTO(ketDqmAction, ketDqmRaise, ketDqmDTO);

	if (ketDqmAction != null && !ketDqmAction.equals(null)) {
	    model.addAttribute("primaryFile", KETContentHelper.manager.getPrimaryContent(ketDqmAction));
	    model.addAttribute("secondaryFiles", KETContentHelper.manager.getSecondaryContents(ketDqmAction));
	} else {
	    model.addAttribute("primaryFile", null);
	    model.addAttribute("secondaryFiles", null);
	}
	model.addAttribute("popup_yn", popup_yn);
	model.addAttribute("curState", ketDqmHeader.getDqmStateCode());
	model.addAttribute("dqm", ketDqmDTO);
    }

    @RequestMapping("/update")
    public void update(KETDqmDTO ketDqmDTO, HttpServletResponse response, HttpServletRequest request) throws Exception {
	ketDqmDTO = DqmHelper.service.modifyRaise(ketDqmDTO);

	String str = "";
	response.setContentType("text/html;charset=KSC5601");
	PrintWriter pwriter = response.getWriter();
	str = "\n <script language=\"javascript\">\n alert(\""
	        + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "02454")/* "저장되었습니다." */+ "\");"
	        + "\n try{ parent.parent.opener.dqm.search();  } catch(error){ }  \n"
	        + "\n parent.parent.location.href='/plm/ext/dqm/dqmMainForm.do?type=view&oid=" + ketDqmDTO.getOid() + "';" + "\n </script>";
	pwriter.println(str);
	pwriter.close();
	return;
	// return ketDqmDTO.getOid();
	// return "redirect:/ext/dqm/viewForm.do?oid=" + ketDqmDTO.getOid() + "&raiseOid=" + ketDqmDTO.getRaiseOid();
    }

    @RequestMapping("/findPagingList")
    @ResponseBody
    public Map<String, Object> findPagingList(KETDqmDTO ketDqmDTO) throws Exception {
	if (ketDqmDTO.getPage() == 0) {
	    SessionUtil.removeAttribute("pageSessionId");
	}
	// methodserver로 부터 데이터를 조회한다.
	PageControl pageControl = DqmHelper.service.findPaging(ketDqmDTO);
	SessionUtil.setAttribute("pageSessionId", pageControl.getSessionId() + "");

	// DTO 객체로 변환 한다
	QueryResult queryResult = pageControl.getResult();

	List<KETDqmDTO> ketDqmDTOList = new ArrayList<KETDqmDTO>();

	Date current = DateUtil.getCurrentTimestamp();
	Date requestDate = null;
	int day = 0;
	long oneDayMillis = 24*60*60*1000;
	while (queryResult.hasMoreElements()) {
	    Object[] objArr = (Object[]) queryResult.nextElement();
	    KETDqmDTO rsltKETDqmDTO = new KETDqmDTO();
	    KETDqmHeader ketDqmHeader = (KETDqmHeader) objArr[0];

	    String stateFlag = ketDqmHeader.getDqmStateCode();

	    if (ketDqmHeader.getRaise() != null) {
		requestDate = ketDqmHeader.getRaise().getRequestDate();
	    }

	    if (requestDate != null) {
		day = (int)((ketDqmHeader.getRaise().getRequestDate().getTime() - current.getTime())/oneDayMillis);
	    }

	    if (day < 0 && !(stateFlag.equals("RAISEINWORK") || stateFlag.equals("ACTIONAPPROVED") || stateFlag.equals("END") || stateFlag.equals("RAISEREJECTED"))) {
		rsltKETDqmDTO.setDqmStateFlag("지연");
	    }
	    day = 0;
	    requestDate = null;

	    rsltKETDqmDTO = rsltKETDqmDTO.KETDqmDTOGrid(ketDqmHeader, rsltKETDqmDTO);
	    ketDqmDTOList.add(rsltKETDqmDTO);
	}

	return EjsConverUtil.convertToDto(ketDqmDTOList, pageControl);
    }

    @RequestMapping("/dqmTodoComplete")
    public void dqmTodoComplete(KETDqmDTO ketDqmDTO, HttpServletResponse response, HttpServletRequest request) throws Exception {
	DqmHelper.service.dqmTodoComplete();
    }

    @RequestMapping("/excelDown")
    public void excelDown(KETDqmDTO ketDqmDTO, HttpServletResponse response, HttpServletRequest request) throws Exception {
	// Excel File 위치 설정
	String userAgent = request.getHeader("User-Agent");
	boolean ie = userAgent.indexOf("MSIE") > -1;

	// file path
	String sWtHome = wt.util.WTProperties.getLocalProperties().getProperty("wt.home", "");
	String sFilePath = sWtHome + "/temp/download";

	File downPath = new File(sFilePath);
	if (!downPath.isDirectory()) {
	    downPath.mkdir();
	}

	// file name
	SimpleDateFormat ff = new SimpleDateFormat("yyyyMMddHHmmss");
	String sFileName = "SearchDqmList_" + ff.format(new Date()) + ".xls";

	// 파일명 한글 깨짐 방지
	if (ie) {
	    sFileName = URLEncoder.encode(sFileName, "utf-8");
	} else {
	    sFileName = new String(sFileName.getBytes("utf-8"), "iso-8859-1");
	}

	// Excel File Object
	File excelFileObj = new File(sFilePath + "/" + sFileName);

	try {

	    WritableWorkbook writebook = Workbook.createWorkbook(new File(sFilePath + "/" + sFileName));
	    WritableSheet sheet = writebook.createSheet("개발품질문제 목록", 0);

	    WritableCellFormat titleCellFormat = new WritableCellFormat();
	    titleCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN); // 셀의 스타일을 지정
	    titleCellFormat.setAlignment(Alignment.CENTRE);
	    titleCellFormat.setBackground(jxl.format.Colour.GREY_25_PERCENT);

	    WritableCellFormat cellFormat = new WritableCellFormat();
	    cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN); // 셀의 스타일을 지정

	    int row = 1;

	    sheet.mergeCells(1, row, 1, row + 2);// (시작열,시작행,종료열,종료행)
	    Label lable = new Label(1, row, "No", titleCellFormat);
	    sheet.addCell(lable);

	    sheet.mergeCells(2, row, 28, row);
	    lable = new Label(2, row, "제기", titleCellFormat);
	    sheet.addCell(lable);

	    sheet.mergeCells(2, row + 1, 2, row + 2);
	    lable = new Label(2, row + 1, "Project No", titleCellFormat);
	    sheet.addCell(lable);

	    sheet.mergeCells(3, row + 1, 3, row + 2);
	    lable = new Label(3, row + 1, "Project Name", titleCellFormat);
	    sheet.addCell(lable);

	    sheet.mergeCells(4, row + 1, 4, row + 2);
	    lable = new Label(4, row + 1, "고객사", titleCellFormat);
	    sheet.addCell(lable);

	    sheet.mergeCells(5, row + 1, 5, row + 2);
	    lable = new Label(5, row + 1, "차종", titleCellFormat);
	    sheet.addCell(lable);

	    sheet.mergeCells(6, row + 1, 6, row + 2);
	    lable = new Label(6, row + 1, "문제점구분", titleCellFormat);
	    sheet.addCell(lable);

	    sheet.mergeCells(7, row + 1, 7, row + 2);
	    lable = new Label(7, row + 1, "발생시점", titleCellFormat);
	    sheet.addCell(lable);

	    sheet.mergeCells(8, row + 1, 8, row + 2);
	    lable = new Label(8, row + 1, "불량구분", titleCellFormat);
	    sheet.addCell(lable);

	    sheet.mergeCells(9, row + 1, 9, row + 2);
	    lable = new Label(9, row + 1, "불량유형", titleCellFormat);
	    sheet.addCell(lable);

	    sheet.mergeCells(10, row + 1, 10, row + 2);
	    lable = new Label(10, row + 1, "긴급도", titleCellFormat);
	    sheet.addCell(lable);

	    sheet.mergeCells(11, row + 1, 11, row + 2);
	    lable = new Label(11, row + 1, "중요도", titleCellFormat);
	    sheet.addCell(lable);

	    sheet.mergeCells(12, row + 1, 12, row + 2);
	    lable = new Label(12, row + 1, "적용부위1", titleCellFormat);
	    sheet.addCell(lable);

	    sheet.mergeCells(13, row + 1, 13, row + 2);
	    lable = new Label(13, row + 1, "적용부위2", titleCellFormat);
	    sheet.addCell(lable);

	    sheet.mergeCells(14, row + 1, 14, row + 2);
	    lable = new Label(14, row + 1, "적용부위3", titleCellFormat);
	    sheet.addCell(lable);

	    sheet.mergeCells(15, row + 1, 17, row + 1); // +3
	    lable = new Label(15, row + 1, "부품분류", titleCellFormat);
	    sheet.addCell(lable);

	    lable = new Label(15, row + 2, "1 Level", titleCellFormat);
	    sheet.addCell(lable);

	    lable = new Label(16, row + 2, "2 Level", titleCellFormat);
	    sheet.addCell(lable);

	    lable = new Label(17, row + 2, "3 Level", titleCellFormat);
	    sheet.addCell(lable);

	    sheet.mergeCells(18, row + 1, 18, row + 2);
	    lable = new Label(18, row + 1, "관련부품", titleCellFormat);
	    sheet.addCell(lable);

	    sheet.mergeCells(19, row + 1, 19, row + 2);
	    lable = new Label(19, row + 1, "발생구분", titleCellFormat);
	    sheet.addCell(lable);

	    sheet.mergeCells(20, row + 1, 20, row + 2);
	    lable = new Label(20, row + 1, "발생단계", titleCellFormat);
	    sheet.addCell(lable);

	    sheet.mergeCells(21, row + 1, 21, row + 2);
	    lable = new Label(21, row + 1, "발생처", titleCellFormat);
	    sheet.addCell(lable);

	    sheet.mergeCells(22, row + 1, 22, row + 2);
	    lable = new Label(22, row + 1, "발생일", titleCellFormat);
	    sheet.addCell(lable);

	    sheet.mergeCells(23, row + 1, 23, row + 2);
	    lable = new Label(23, row + 1, "발생장소", titleCellFormat);
	    sheet.addCell(lable);

	    sheet.mergeCells(24, row + 1, 24, row + 2);
	    lable = new Label(24, row + 1, "제기자", titleCellFormat);
	    sheet.addCell(lable);

	    sheet.mergeCells(25, row + 1, 25, row + 2);
	    lable = new Label(25, row + 1, "작성자", titleCellFormat);
	    sheet.addCell(lable);

	    sheet.mergeCells(26, row + 1, 26, row + 2);
	    lable = new Label(26, row + 1, "작성부서", titleCellFormat);
	    sheet.addCell(lable);

	    sheet.mergeCells(27, row + 1, 27, row + 2);
	    lable = new Label(27, row + 1, "완료목표일", titleCellFormat);
	    sheet.addCell(lable);

	    sheet.mergeCells(28, row + 1, 28, row + 2);
	    lable = new Label(28, row + 1, "상세내용", titleCellFormat);
	    sheet.addCell(lable);

	    sheet.mergeCells(29, row, 43, row);
	    lable = new Label(29, row, "검토", titleCellFormat);
	    sheet.addCell(lable);

	    sheet.mergeCells(29, row + 1, 30, row); // (시작열,시작행,종료열,종료행)
	    lable = new Label(29, row + 1, "원인", titleCellFormat);
	    sheet.addCell(lable);

	    lable = new Label(29, row + 2, "구분", titleCellFormat);
	    sheet.addCell(lable);

	    lable = new Label(30, row + 2, "상세내용", titleCellFormat);
	    sheet.addCell(lable);

	    sheet.mergeCells(31, row + 1, 31, row + 2);
	    lable = new Label(31, row + 1, "개선대책", titleCellFormat);
	    sheet.addCell(lable);

	    sheet.mergeCells(32, row + 1, 32, row + 2);
	    lable = new Label(32, row + 1, "중복", titleCellFormat);
	    sheet.addCell(lable);

	    sheet.mergeCells(33, row + 1, 33, row + 2);
	    lable = new Label(33, row + 1, "중복문제보고", titleCellFormat);
	    sheet.addCell(lable);

	    sheet.mergeCells(34, row + 1, 34, row + 2);
	    lable = new Label(34, row + 1, "도면출도", titleCellFormat);
	    sheet.addCell(lable);

	    sheet.mergeCells(35, row + 1, 35, row + 2);
	    lable = new Label(35, row + 1, "금형수정", titleCellFormat);
	    sheet.addCell(lable);

	    sheet.mergeCells(36, row + 1, 36, row + 2);
	    lable = new Label(36, row + 1, "T/O", titleCellFormat);
	    sheet.addCell(lable);

	    sheet.mergeCells(37, row + 1, 37, row + 2);
	    lable = new Label(37, row + 1, "신뢰성시험", titleCellFormat);
	    sheet.addCell(lable);

	    sheet.mergeCells(38, row + 1, 38, row + 2);
	    lable = new Label(38, row + 1, "유효성검증예정", titleCellFormat);
	    sheet.addCell(lable);

	    sheet.mergeCells(39, row + 1, 39, row + 2);
	    lable = new Label(39, row + 1, "실제완료일", titleCellFormat);
	    sheet.addCell(lable);

	    sheet.mergeCells(40, row + 1, 40, row + 2);
	    lable = new Label(40, row + 1, "관련ECR", titleCellFormat);
	    sheet.addCell(lable);

	    sheet.mergeCells(41, row + 1, 41, row + 2);
	    lable = new Label(41, row + 1, "과거차 문제점 반영", titleCellFormat);
	    sheet.addCell(lable);

	    sheet.mergeCells(42, row + 1, 42, row + 2);
	    lable = new Label(42, row + 1, "검토자", titleCellFormat);
	    sheet.addCell(lable);

	    sheet.mergeCells(43, row + 1, 43, row + 2);
	    lable = new Label(43, row + 1, "검토부서", titleCellFormat);
	    sheet.addCell(lable);

	    sheet.mergeCells(44, row, 49, row); // (시작열,시작행,종료열,종료행)
	    lable = new Label(44, row, "종결", titleCellFormat);
	    sheet.addCell(lable);

	    sheet.mergeCells(44, row + 1, 44, row + 2);
	    lable = new Label(44, row + 1, "완료일", titleCellFormat);
	    sheet.addCell(lable);

	    sheet.mergeCells(45, row + 1, 45, row + 2);
	    lable = new Label(45, row + 1, "검토결과", titleCellFormat);
	    sheet.addCell(lable);

	    sheet.mergeCells(46, row + 1, 46, row + 2);
	    lable = new Label(46, row + 1, "적용예정일", titleCellFormat);
	    sheet.addCell(lable);

	    sheet.mergeCells(47, row + 1, 47, row + 2);
	    lable = new Label(47, row + 1, "유효성검증확인", titleCellFormat);
	    sheet.addCell(lable);

	    sheet.mergeCells(48, row + 1, 48, row + 2);
	    lable = new Label(48, row + 1, "과거차 문제점 반영", titleCellFormat);
	    sheet.addCell(lable);

	    sheet.mergeCells(49, row + 1, 49, row + 2);
	    lable = new Label(49, row + 1, "검토의견", titleCellFormat);
	    sheet.addCell(lable);
	    /*
	     * sheet.mergeCells(2, row+1,2,row+2); lable = new Label(1, row+1, "제기"); sheet.addCell(lable);
	     * 
	     * sheet.mergeCells(2, row+1,2,row+2); lable = new Label(1, row+1, "제기"); sheet.addCell(lable);
	     * 
	     * sheet.mergeCells(2, row+1,2,row+2); lable = new Label(1, row+1, "제기"); sheet.addCell(lable);
	     */

	    QueryResult qr = DqmHelper.service.getSearchList(ketDqmDTO);

	    row = 3;
	    int cnt = 0;

	    while (qr.hasMoreElements()) {
		row++;
		cnt++;
		// int idxHeaer = query.appendClassList(KETDqmHeader.class, true);
		// int idxRaise = query.appendClassList(KETDqmRaise.class, true);
		// int idxClose = query.appendClassList(KETDqmClose.class, true);
		// int idxUser = query.appendClassList(WTUser.class, false);
		// int idxPart = query.appendClassList(WTPart.class, false);

		Object[] objArr = (Object[]) qr.nextElement();
		KETDqmHeader ketDqmHeader = (KETDqmHeader) objArr[0];
		KETDqmRaise ketDqmRasie = (KETDqmRaise) objArr[1];
		KETDqmAction ketDqmAction = ketDqmHeader.getAction();
		KETDqmClose ketDqmClose = (KETDqmClose) objArr[2];

		// NO
		lable = new Label(1, row, String.valueOf(cnt), cellFormat);
		sheet.addCell(lable);

		// Project No
		lable = new Label(2, row, ketDqmRasie.getPjtno(), cellFormat);
		sheet.addCell(lable);

		// Project Name
		lable = new Label(3, row, ketDqmRasie.getPjtname(), cellFormat);
		sheet.addCell(lable);

		// 고객사
		lable = new Label(4, row, ketDqmRasie.getCustomerName(), cellFormat);
		sheet.addCell(lable);

		// 차종
		lable = new Label(5, row, ketDqmRasie.getCartypeName(), cellFormat);
		sheet.addCell(lable);

		// 문제점구분
		lable = new Label(6, row, ketDqmRasie.getIssueName(), cellFormat);
		sheet.addCell(lable);

		// 발생시점
		lable = new Label(7, row, ketDqmRasie.getOccurPointName(), cellFormat);
		sheet.addCell(lable);

		// 불량구분
		lable = new Label(8, row, ketDqmRasie.getDefectDivName(), cellFormat);
		sheet.addCell(lable);

		// 불량유형
		lable = new Label(9, row, ketDqmRasie.getDefectTypeName(), cellFormat);
		sheet.addCell(lable);

		// 긴급도
		lable = new Label(10, row, ketDqmRasie.getUrgencyName(), cellFormat);
		sheet.addCell(lable);

		// 중요도
		lable = new Label(11, row, ketDqmRasie.getImportanceName(), cellFormat);
		sheet.addCell(lable);

		// 적용부위
		lable = new Label(12, row, ketDqmRasie.getApplyArea1(), cellFormat);
		sheet.addCell(lable);

		// 적용부위2
		lable = new Label(13, row, ketDqmRasie.getApplyArea2(), cellFormat);
		sheet.addCell(lable);

		// 적용부위3
		lable = new Label(14, row, ketDqmRasie.getApplyArea3(), cellFormat);
		sheet.addCell(lable);

		// 부품분류 1
		lable = new Label(15, row, ketDqmRasie.getPartCategoryName(), cellFormat);
		sheet.addCell(lable);

		// 부품분류 2
		lable = new Label(16, row, ketDqmRasie.getPartCategoryName(), cellFormat);
		sheet.addCell(lable);

		// 부붚분류 3
		lable = new Label(17, row, ketDqmRasie.getPartCategoryName(), cellFormat);
		sheet.addCell(lable);

		// 관련부품
		lable = new Label(18, row, ketDqmRasie.getPart().getNumber(), cellFormat);
		sheet.addCell(lable);

		// 발생구분
		lable = new Label(19, row, ketDqmRasie.getOccurDivName(), cellFormat);
		sheet.addCell(lable);

		// 발생단계
		lable = new Label(20, row, ketDqmRasie.getOccurStepName(), cellFormat);
		sheet.addCell(lable);

		// 발생처
		lable = new Label(21, row, ketDqmRasie.getOccurPlaceName(), cellFormat);
		sheet.addCell(lable);

		// 발생일
		lable = new Label(22, row, DateUtil.getDateString(ketDqmRasie.getOccurDate(), "date"), cellFormat);
		sheet.addCell(lable);

		// 발생장소
		lable = new Label(23, row, ketDqmRasie.getOccurPlaceName(), cellFormat);
		sheet.addCell(lable);

		// 제기자
		lable = new Label(24, row, ketDqmRasie.getActionDeptName(), cellFormat);
		sheet.addCell(lable);

		// 작성자
		lable = new Label(25, row, ketDqmRasie.getCreatorUserName(), cellFormat);
		sheet.addCell(lable);

		// 작성부서
		lable = new Label(26, row, ketDqmRasie.getCreatorDeptName(), cellFormat);
		sheet.addCell(lable);

		// 완료목표일
		lable = new Label(27, row, DateUtil.getDateString(ketDqmRasie.getRequestDate(), "date"), cellFormat);
		sheet.addCell(lable);

		// 상세내용
		lable = new Label(28, row, (String) ketDqmRasie.getWebEditorText(), cellFormat);
		sheet.addCell(lable);

		if (ketDqmAction != null) {
		    String causeCode = "";
		    if (!StringUtil.checkNull(ketDqmAction.getCauseCode()).equals("")) {
			String nameArr[] = ketDqmAction.getCauseCode().split(",");
			for (int j = 0; j < nameArr.length; j++) {
			    if (causeCode != "") {
				causeCode += ", ";
			    }
			    causeCode += CodeHelper.manager.getCodeValue("PROBLEMTEAM", nameArr[j].trim());
			}
		    }

		    // 구분
		    lable = new Label(29, row, causeCode, cellFormat);
		    sheet.addCell(lable);

		    // 상세내용
		    lable = new Label(30, row, (String) ketDqmAction.getCauseWebEditorText(), cellFormat);
		    sheet.addCell(lable);

		    // 개선대책
		    lable = new Label(31, row, (String) ketDqmAction.getImproveWebEditorText(), cellFormat);
		    sheet.addCell(lable);

		    // 중복
		    lable = new Label(32, row, ketDqmAction.getDuplicateYn(), cellFormat);
		    sheet.addCell(lable);

		    // 중복문제보고
		    lable = new Label(33, row, ketDqmAction.getDuplicateReportName(), cellFormat);
		    sheet.addCell(lable);

		    // 도면출도
		    lable = new Label(34, row, DateUtil.getDateString(ketDqmAction.getDrawingOutDate(), "date"), cellFormat);
		    sheet.addCell(lable);

		    // 금형수정
		    lable = new Label(35, row, DateUtil.getDateString(ketDqmAction.getMoldModifyDate(), "date"), cellFormat);
		    sheet.addCell(lable);

		    // T/O
		    lable = new Label(36, row, DateUtil.getDateString(ketDqmAction.getToDate(), "date"), cellFormat);
		    sheet.addCell(lable);

		    // 신뢰성시험
		    lable = new Label(37, row, DateUtil.getDateString(ketDqmAction.getTrustTestDate(), "date"), cellFormat);
		    sheet.addCell(lable);

		    // 유효성검증예정
		    lable = new Label(38, row, DateUtil.getDateString(ketDqmAction.getValidationDate(), "date"), cellFormat);
		    sheet.addCell(lable);

		    // 실제완료일
		    lable = new Label(39, row, DateUtil.getDateString(ketDqmAction.getExecEndDate(), "date"), cellFormat);
		    sheet.addCell(lable);

		    KETEcrDqmLink ketEcrDqmLink = null;
		    KETProdChangeRequest ecr = null;

		    String relatedEcrNo = "";
		    QueryResult inQr = PersistenceHelper.manager.navigate(ketDqmAction, "ecr", KETEcrDqmLink.class, false);
		    while (inQr.hasMoreElements()) {
			ketEcrDqmLink = (KETEcrDqmLink) inQr.nextElement();
			ecr = (KETProdChangeRequest) ketEcrDqmLink.getEcr();

			if (!relatedEcrNo.equals("")) {
			    relatedEcrNo += ", ";
			}

			relatedEcrNo += ecr.getEcrId().replace("ECR-", "");
		    }

		    // 관련ECR
		    lable = new Label(40, row, relatedEcrNo, cellFormat);
		    sheet.addCell(lable);

		    if (ketDqmClose != null) {
			// 과거차 문제점 반영
			lable = new Label(41, row, ketDqmClose.getProblemReflectYn(), cellFormat);
			sheet.addCell(lable);
		    } else {
			lable = new Label(41, row, "", cellFormat);
			sheet.addCell(lable);
		    }

		} else {

		    lable = new Label(29, row, "", cellFormat);
		    sheet.addCell(lable);

		    lable = new Label(30, row, "", cellFormat);
		    sheet.addCell(lable);

		    lable = new Label(31, row, "", cellFormat);
		    sheet.addCell(lable);

		    lable = new Label(32, row, "", cellFormat);
		    sheet.addCell(lable);

		    lable = new Label(33, row, "", cellFormat);
		    sheet.addCell(lable);

		    lable = new Label(34, row, "", cellFormat);
		    sheet.addCell(lable);

		    lable = new Label(35, row, "", cellFormat);
		    sheet.addCell(lable);

		    lable = new Label(36, row, "", cellFormat);
		    sheet.addCell(lable);

		    lable = new Label(37, row, "", cellFormat);
		    sheet.addCell(lable);

		    lable = new Label(38, row, "", cellFormat);
		    sheet.addCell(lable);

		    lable = new Label(39, row, "", cellFormat);
		    sheet.addCell(lable);

		    lable = new Label(40, row, "", cellFormat);
		    sheet.addCell(lable);

		    lable = new Label(41, row, "", cellFormat);
		    sheet.addCell(lable);

		}

		// 검토자
		lable = new Label(42, row, ketDqmRasie.getActionUserName(), cellFormat);
		sheet.addCell(lable);

		// 검토부서
		lable = new Label(43, row, ketDqmRasie.getActionUserDeptName(), cellFormat);
		sheet.addCell(lable);

		if (ketDqmClose != null) {
		    lable = new Label(44, row, DateUtil.getDateString(ketDqmClose.getReviewDate(), "date"), cellFormat);
		    sheet.addCell(lable);

		    lable = new Label(45, row, CodeHelper.manager.getCodeValue("DQMREVIEWRESULT", ketDqmClose.getReviewRsltCode()),
			    cellFormat);
		    sheet.addCell(lable);

		    lable = new Label(46, row, DateUtil.getDateString(ketDqmClose.getApplyExpectDate(), "date"), cellFormat);
		    sheet.addCell(lable);

		    lable = new Label(47, row, ketDqmClose.getValidationCheck(), cellFormat);
		    sheet.addCell(lable);

		    lable = new Label(48, row, ketDqmClose.getProblemReflectYn(), cellFormat);
		    sheet.addCell(lable);

		    lable = new Label(49, row, (String) ketDqmClose.getWebEditorText(), cellFormat);
		    sheet.addCell(lable);

		} else {

		    lable = new Label(44, row, "", cellFormat);
		    sheet.addCell(lable);

		    lable = new Label(45, row, "", cellFormat);
		    sheet.addCell(lable);

		    lable = new Label(46, row, "", cellFormat);
		    sheet.addCell(lable);

		    lable = new Label(47, row, "", cellFormat);
		    sheet.addCell(lable);

		    lable = new Label(48, row, "", cellFormat);
		    sheet.addCell(lable);

		    lable = new Label(49, row, "", cellFormat);
		    sheet.addCell(lable);
		}
	    }

	    writebook.write(); // 쓰고

	    writebook.close(); // 닫자

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}

	try {
	    // //////////////////////////////////////////////////////////////////////////////////////////////////////////
	    // Added by MJOH, 2011-04-18
	    // 엑셀 다운로드 파일 DRM 암호화 적용
	    // String contentID = sFileName.substring( 0, sFileName.lastIndexOf(".") );
	    // excelFileObj = E3PSDRMHelper.downloadExcel( excelFileObj, sFileName, contentID, req.getRemoteAddr() );
	    // //////////////////////////////////////////////////////////////////////////////////////////////////////////

	    FileInputStream fin = new FileInputStream(excelFileObj);
	    int ifilesize = (int) excelFileObj.length();
	    byte b[] = new byte[ifilesize];

	    response.setContentLength(ifilesize);
	    response.setContentType("application/vnd.ms-excel;charset=UTF-8");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + sFileName + "\";");
	    response.setHeader("Content-Transfer-Encoding", "binary");

	    ServletOutputStream oout = response.getOutputStream();
	    fin.read(b);
	    oout.write(b, 0, ifilesize);
	    oout.close();
	    fin.close();
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	} finally {
	    if (excelFileObj.isFile()) {
		excelFileObj.delete();
	    }
	}

    }

    @RequestMapping("/dqmMigration")
    public void dqmMigration(KETDqmDTO ketDqmDTO, HttpServletResponse response, HttpServletRequest request) throws Exception {
	List<HashMap<String, String>> paramsList = new ArrayList<HashMap<String, String>>();

	try {
	    MultipartFile multiFile = ketDqmDTO.getSecondaryFiles().get(0);
	    XSSFWorkbook workbook = new XSSFWorkbook(multiFile.getInputStream());

	    int sheetNum = workbook.getNumberOfSheets();

	    // 처음 시트만 기준
	    XSSFSheet sheet = workbook.getSheetAt(0);

	    int rows = sheet.getPhysicalNumberOfRows();

	    Kogger.debug(getClass(), "\n# sheet rows num : " + rows);

	    // 데이터 시작줄이 5번째 줄부터 시작 함 (5-1)
	    for (int rownum = 4; rownum < rows; rownum++) {

		XSSFRow row = sheet.getRow(rownum);

		if (row != null) {

		    int cells = row.getPhysicalNumberOfCells();

		    Kogger.debug(getClass(), "\n# row = " + row.getRowNum() + " / cells = " + cells);

		    HashMap<String, String> params = new HashMap<String, String>();

		    for (int cellnum = 0; cellnum < cells; cellnum++) {

			XSSFCell cell = row.getCell(cellnum);

			if (cell != null) {

			    switch (cell.getCellType()) {

			    case XSSFCell.CELL_TYPE_FORMULA:

				params.put("INDEX" + cellnum, cell.getCellFormula());

				break;

			    case XSSFCell.CELL_TYPE_NUMERIC:

				params.put("INDEX" + cellnum, String.valueOf(cell.getNumericCellValue()));

				break;

			    case XSSFCell.CELL_TYPE_STRING:

				params.put("INDEX" + cellnum, cell.getStringCellValue());

				break;

			    case HSSFCell.CELL_TYPE_BLANK:

				params.put("INDEX" + cellnum, "");

				break;

			    case XSSFCell.CELL_TYPE_ERROR:

				params.put("INDEX" + cellnum, "ERROR");

				break;

			    default:

				break;

			    }
			}
		    }
		    paramsList.add(params);
		}
	    }

	} catch (Exception e) {

	    Kogger.error(getClass(), e);

	}

	String rslt = DqmHelper.service.migration(paramsList);

	response.setContentType("text/html;charset=KSC5601");
	PrintWriter pwriter = response.getWriter();
	pwriter.println(rslt);
	pwriter.close();
	return;
    }

    @RequestMapping("/getPartClassification")
    @ResponseBody
    public List<Map<String, String>> getPartClassification(String oid, Model model) throws Exception {
	KETMessageService messageService = KETMessageService.getMessageService();

	WTPart wtPart = (WTPart) CommonUtil.getObject(oid);

	List<Map<String, String>> rsltList = new ArrayList<Map<String, String>>();

	Map<String, String> rslt = new HashMap<String, String>();

	KETPartClassification ketPartClassification = PartUtil.getPartClassification(wtPart);

	String locale = messageService.getLocale().toString();

	String name = "";
	String partClassificationOid = "";

	if (ketPartClassification != null) {
	    partClassificationOid = CommonUtil.getOIDString(ketPartClassification);
	    if (locale.equals("zh_CN")) {
		name = ketPartClassification.getClassZhName();
	    } else if (locale.equals("en")) {
		name = ketPartClassification.getClassEnName();
	    } else {
		name = ketPartClassification.getClassKrName();
	    }
	}

	rslt.put("oid", partClassificationOid);
	rslt.put("name", name);

	rsltList.add(rslt);

	return rsltList;
    }

    @RequestMapping("/searchPjtInfo")
    @ResponseBody
    public List<Map<String, String>> searchPjtInfo(String oid, Model model) throws Exception {
	KETMessageService messageService = KETMessageService.getMessageService();
	E3PSProject project = (E3PSProject) CommonUtil.getObject(oid);
	E3PSProjectData projectData = new E3PSProjectData(project);

	String carTypeRequiredFlag = "false";

	if ("자동차 사업부".equals(projectData.teamType)) {
	    carTypeRequiredFlag = "true";
	}

	// 대표 차종
	OEMProjectType oemType = project.getOemType();
	String carTypeOid = (oemType != null) ? CommonUtil.getOIDString(oemType) : "";
	String carTypeName = (oemType != null) ? oemType.getName() : "";
	String carTypeCode = (oemType != null) ? oemType.getCode() : "";

	String partNumber = "";
	String partOid = "";
	// 제품정보
	KETBOMQueryBean ketBOMQueryBean = new KETBOMQueryBean();
	// 제품 프로젝트일 경우
	if (project instanceof ProductProject) {
	    QueryResult qr = PersistenceHelper.manager.navigate(project, ProjectProductInfoLink.PRODUCT_INFO_ROLE,
		    ProjectProductInfoLink.class, false);
	    while (qr.hasMoreElements()) {
		ProjectProductInfoLink projectProductInfoLink = (ProjectProductInfoLink) qr.nextElement();
		ProductInfo productInfo = projectProductInfoLink.getProductInfo();
		partNumber = productInfo.getPNum();
		@SuppressWarnings("rawtypes")
		Hashtable partInfo = ketBOMQueryBean.getPartInfo(partNumber);
		partOid = (partInfo != null) ? (String) partInfo.get("oid") : "";
		if (partOid == "" || partOid == null) {
		    partNumber = "";
		    partOid = "";
		}
	    }
	}

	String subcontractorCode = "";
	String subcontractorValue = "";
	String subcontractorOid = "";

	if (projectData.subcontractorVec != null && projectData.subcontractorVec.size() > 0) {
	    for (int i = 0; i < projectData.subcontractorVec.size(); i++) {
		NumberCode nc = (NumberCode) projectData.subcontractorVec.get(i);
		String value = NumberCodeUtil.getNumberCodeValue("SUBCONTRACTOR", nc.getCode(), messageService.getLocale().toString());
		String ncOid = CommonUtil.getOIDString(nc);
		String code = nc.getCode();

		if (i != 0) {
		    subcontractorCode += ",";
		    subcontractorValue += ",";
		    subcontractorOid += ",";
		}
		subcontractorCode += code;
		subcontractorValue += value;
		subcontractorOid += ncOid;
	    }
	}

	List<Map<String, String>> rsltList = new ArrayList<Map<String, String>>();

	Map<String, String> rslt = new HashMap<String, String>();

	String occurStepCode = "";

	if (project.getProcess() != null) {
	    if (project.getProcess().getCode().equals("PC001")) {
		occurStepCode = "PROTO";
	    } else if (project.getProcess().getCode().equals("PC002")) {
		occurStepCode = "PILOT";
	    } else if (project.getProcess().getCode().equals("PC003")) {
		occurStepCode = "TCAR";
	    }
	}

	String state = project.getState().toString();
	if (state.equals("COMPLETED")) {
	    occurStepCode = "PRODUCTION";
	}

	rslt.put("carTypeOid", carTypeOid);
	rslt.put("carTypeName", carTypeName);
	rslt.put("carTypeCode", carTypeCode);
	rslt.put("partNumber", partNumber);
	rslt.put("partOid", partOid);

	rslt.put("subcontractorOid", subcontractorOid);
	rslt.put("subcontractorCode", subcontractorCode);
	rslt.put("subcontractorValue", subcontractorValue);
	rslt.put("occurStepCode", occurStepCode);
	rslt.put("carTypeRequiredFlag", carTypeRequiredFlag);

	rsltList.add(rslt);

	return rsltList;
    }

    @RequestMapping("/setCloser")
    @ResponseBody
    public List<Map<String, String>> setCloser(String oid, Model model) throws Exception {

	KETDqmHeader ketDqmHeader = (KETDqmHeader) CommonUtil.getObject(oid);
	KETDqmRaise ketDqmRaise = ketDqmHeader.getRaise();

	List<Map<String, String>> rsltList = new ArrayList<Map<String, String>>();

	if ((ketDqmRaise.getProductProject()) != null) {
	    ProductProject productProject = (ProductProject) ketDqmRaise.getProductProject();

	    E3PSProjectData projectData = new E3PSProjectData(productProject);
	    WTUser wtCloseUser = projectData.pjtQA;
	    if (wtCloseUser == null && "전자 사업부".equals(productProject.getTeamType())) {
		wtCloseUser = projectData.pjtQC;
	    }
	    if (wtCloseUser != null) {
		ketDqmRaise.setCloseUser(wtCloseUser);
		ketDqmRaise = (KETDqmRaise) PersistenceHelper.manager.modify(ketDqmRaise);
		PeopleData pdata = new PeopleData(wtCloseUser);

		Map<String, String> rslt = new HashMap<String, String>();

		String closerName = pdata.name;
		rslt.put("closerName", closerName);
		rsltList.add(rslt);
	    }
	}

	return rsltList;
    }

    @RequestMapping("/deleteAll")
    @ResponseBody
    public String deleteAll(Model model) throws Exception {
	DqmHelper.service.deleteAll();
	return "OK";
    }

    @RequestMapping("/saveReason")
    @ResponseBody
    public String saveReason(KETDqmDTO ketDqmDTO, Model model) throws Exception {
	DqmHelper.service.saveReason(ketDqmDTO);
	return "OK";
    }

    @RequestMapping("/reReview")
    @ResponseBody
    public String reReview(KETDqmDTO ketDqmDTO, Model model) throws Exception {
	DqmHelper.service.reReview(ketDqmDTO);
	return "OK";
    }

    @RequestMapping("/close")
    @ResponseBody
    public String close(KETDqmDTO ketDqmDTO, Model model) throws Exception {
	DqmHelper.service.close(ketDqmDTO);
	return "OK";
    }

    @RequestMapping("/findDqmByProject")
    @ResponseBody
    public Map<String, Object> findDqmByProject(String pjtNo) throws Exception {
	return EjsConverUtil.convertToDto(DqmHelper.service.findDqmByProject(pjtNo));

    }
}
