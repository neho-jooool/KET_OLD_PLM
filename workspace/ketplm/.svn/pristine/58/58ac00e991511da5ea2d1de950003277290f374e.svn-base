package ext.ket.project.issue.service;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import wt.fc.EnumeratedTypeUtil;
import wt.org.WTPrincipalReference;
import wt.org.WTUser;
import wt.pom.Transaction;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.groupware.company.People;
import e3ps.groupware.company.PeopleHelper;
import e3ps.project.E3PSProject;
import e3ps.project.IssueType;
import e3ps.project.ProjectIssue;
import e3ps.project.beans.ProjectIssueHelper;
import ext.ket.shared.drm.DRMHelper;
import ext.ket.shared.mail.service.KETMailHelper;

public class StandardKETIssueService extends StandardManager implements KETIssueService {

    private static final long serialVersionUID = 1L;

    public static StandardKETIssueService newStandardKETIssueService() throws WTException {
	StandardKETIssueService instance = new StandardKETIssueService();
	instance.initialize();
	return instance;
    }

    @Override
    public void issueExcelUpload(Map<String, Object> reqMap, File uploadFile) throws Exception {

	Transaction trx = new Transaction();

	try {

	    trx.start();
	    ArrayList<ProjectIssue> issueList = null;
	    if (DRMHelper.useDrm) {
		uploadFile = DRMHelper.Decryptupload(uploadFile, uploadFile.getName());
	    }

	    String filePathInfo = "sFilePath = " + uploadFile.getAbsolutePath();
	    System.out.println(filePathInfo);

	    String newFileInfo = "newFile = " + uploadFile;
	    System.out.println(newFileInfo);

	    System.out.println(uploadFile.getName());
	    String fileName = uploadFile.getName();

	    String ext = "";
	    if (fileName.indexOf(".") >= 0) {
		ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
	    }

	    if (ext.length() == 0) {
		throw new Exception("정확하지 않은 확장자의 파일입니다");
	    } else {

		System.out.println(ext);

		FileInputStream fis = new FileInputStream(uploadFile);

		if ("xlsx".equals(ext)) {
		    issueList = xssfExcelLoad(reqMap, fis);
		} else {
		    throw new Exception("xlsx 확장자의 파일만 업로드 가능 합니다.");
		}
	    }

	    trx.commit();
	    trx = null;

	    try {
		// 이슈 등록 메일 공지 발송
		for (ProjectIssue issue : issueList) {

		    WTUser from = (WTUser) issue.getOwner().getPrincipal();
		    // List<WTUser> to = ProjectIssueHelper.manager.getIssueMailingList(issue);
		    List<WTUser> to = new ArrayList<WTUser>();
		    /*
	             * 기존 메일발송처럼 관련 인원전체에게 보내는 것이 아니라 이슈 담당자에게만 알림메일을 보낸다. 기존처럼 메일을 보내기위해서는 위의 주석을 풀어서 적용해야함
	             */
		    to.add((WTUser) issue.getManager().getPrincipal());
		    KETMailHelper.service.sendFormMail("08017", "NoticeMailLine3.html", issue, from, to);
		}
	    } catch (Exception e) {
		e.printStackTrace();// 메일 발송시 오류가 발생해도 리턴안하고 진행한다
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	    throw new Exception(e.getLocalizedMessage());
	} finally {
	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }
	}

    }

    public ArrayList<ProjectIssue> xssfExcelLoad(Map<String, Object> reqMap, FileInputStream fis) throws Exception {

	String projectOid = (String) reqMap.get("oid");

	E3PSProject project = (E3PSProject) CommonUtil.getObject(projectOid);

	XSSFWorkbook wb = new XSSFWorkbook(fis);

	XSSFSheet sheet = wb.getSheetAt(0);
	DataFormatter df = new DataFormatter();

	int rowNum = sheet.getPhysicalNumberOfRows();

	IssueType[] issueTypeList = IssueType.getIssueTypeSet();
	Map<String, Object> issueTypeMap = new HashMap<String, Object>();

	for (int i = 0; i < issueTypeList.length; i++) {
	    issueTypeList[i].getStringValue();
	    issueTypeMap.put(issueTypeList[i].getDisplay(Locale.KOREA), issueTypeList[i].getStringValue());
	}

	Map<String, Object> urgencyMap = new HashMap<String, Object>();
	urgencyMap.put("상", "상");
	urgencyMap.put("중", "중");
	urgencyMap.put("하", "하");

	People manager = null;
	People owner = null;

	Timestamp createTimeStamp = null;
	WTPrincipalReference sessionUser = SessionHelper.manager.getPrincipalReference();

	ArrayList<ProjectIssue> issueList = new ArrayList<ProjectIssue>();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	for (int i = 2; i < rowNum; i++) {

	    XSSFRow row = sheet.getRow(i);

	    String issueType = "";
	    String subject = "";
	    String question = "";
	    String urgency = "";
	    String importance = "";
	    String masterMember = "";
	    String ownerMember = "";

	    int cellCnt = 0;
	    XSSFCell cell = row.getCell(cellCnt);
	    if (cell != null) {
		issueType = df.formatCellValue(cell).trim();
		issueType = (String) issueTypeMap.get(issueType);
	    }

	    if (StringUtils.isEmpty(issueType)) {
		throw new Exception(i + 1 + " 번째 줄의 [구분] 값이 비어있거나 올바른 값이 아닙니다.");
	    }
	    cellCnt++;

	    cell = row.getCell(cellCnt);
	    if (cell != null) {
		subject = df.formatCellValue(cell).trim();
	    }
	    if (StringUtils.isEmpty(subject)) {
		throw new Exception(i + 1 + " 번째 줄의 [제목] 값이 비어있습니다.");
	    }
	    cellCnt++;

	    cell = row.getCell(cellCnt);
	    if (cell != null) {
		question = df.formatCellValue(cell).trim();
	    }
	    cellCnt++;
	    if (StringUtils.isEmpty(question)) {
		throw new Exception(i + 1 + " 번째 줄의 [이슈내용] 값이 비어있습니다.");
	    }

	    cell = row.getCell(cellCnt);
	    if (cell != null) {
		urgency = df.formatCellValue(cell).trim();
		urgency = (String) urgencyMap.get(urgency);
	    }
	    cellCnt++;
	    if (StringUtils.isEmpty(urgency)) {
		throw new Exception(i + 1 + " 번째 줄의 [긴급도] 값이 비어있거나 올바른 값이 아닙니다.");
	    }

	    cell = row.getCell(cellCnt);
	    if (cell != null) {
		importance = df.formatCellValue(cell).trim();
		urgency = (String) urgencyMap.get(importance);
	    }
	    cellCnt++;
	    if (StringUtils.isEmpty(importance)) {
		throw new Exception(i + 1 + " 번째 줄의 [중요도] 값이 비어있거나 올바른 값이 아닙니다.");
	    }

	    cell = row.getCell(cellCnt);
	    if (cell != null) {
		ownerMember = df.formatCellValue(cell).trim();
		owner = PeopleHelper.manager.getPeople(ownerMember);
	    }
	    cellCnt++;
	    if (owner == null) {
		throw new Exception(i + 1 + " 번째 줄에 입력된 제기자 [ " + ownerMember + " ] 아이디로 인원을 찾을 수 없습니다.");
	    }

	    cell = row.getCell(cellCnt);
	    if (cell != null) {
		try {
		    Timestamp ts = new Timestamp(cell.getDateCellValue().getTime());
		    createTimeStamp = DateUtil.getTimestampFormat(formatter.format(ts), "yyyy-MM-dd");

		} catch (Exception e) {
		    throw new Exception(i + 1 + " 번째 줄에 입력된 제기일자의 형식은 날짜형식이어야합니다 (yyyy-MM-dd)");
		}
	    }
	    cellCnt++;

	    cell = row.getCell(cellCnt);
	    if (cell != null) {
		masterMember = df.formatCellValue(cell).trim();
		manager = PeopleHelper.manager.getPeople(masterMember);
	    }
	    if (manager == null) {
		throw new Exception(i + 1 + " 번째 줄에 입력된 담당자 [ " + masterMember + " ] 아이디로 인원을 찾을 수 없습니다.");
	    }

	    IssueType type = (IssueType) EnumeratedTypeUtil.toEnumeratedType(issueType);

	    ProjectIssue issue = ProjectIssue.newProjectIssue();
	    issue.setSubject(subject);
	    issue.setAnswer(question);
	    issue.setIsAnswerDone(true);
	    issue.setOwner(WTPrincipalReference.newWTPrincipalReference(owner.getUser()));
	    issue.setManager(WTPrincipalReference.newWTPrincipalReference(manager.getUser()));
	    issue.setIssueType(type);
	    issue.setProject(project);
	    issue.setCreateDate(createTimeStamp);
	    issue.setUrgency(urgency);
	    issue.setImportance(importance);

	    issue = ProjectIssueHelper.manager.createProjectIssue(issue);
	    issueList.add(issue);

	}
	return issueList;
    }

}
