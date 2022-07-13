package ext.ket.edm.service.base;

import java.io.File;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.web.multipart.MultipartFile;

import wt.clients.iba.container.NewValueCreator;
import wt.epm.EPMDocument;
import wt.iba.definition.litedefinition.AttributeDefDefaultView;
import wt.iba.value.DefaultAttributeContainer;
import wt.iba.value.IBAHolder;
import wt.iba.value.litevalue.AbstractValueView;
import wt.iba.value.service.IBAValueHelper;
import wt.org.WTUser;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.common.message.KETMessageService;
import e3ps.common.util.DateUtil;
import e3ps.edm.CADAppType;
import e3ps.edm.beans.EDMHelper;
import e3ps.edm.clients.batch.EPMLoadData;
import e3ps.edm.util.EDMAttributeUtil;
import e3ps.edm.util.EDMAttributes;
import e3ps.load.remote.edm.EPMLoadHelper;
import ext.ket.edm.cad2bom.service.internal.EpmInfoUpadator;
import ext.ket.edm.service.internal.EpmEcoValidator;
import ext.ket.shared.drm.DRMHelper;
import ext.ket.shared.log.Kogger;

public class StandardEpmBaseService extends StandardManager implements EpmBaseService {
    private static final long serialVersionUID = 1L;

    public static StandardEpmBaseService newStandardEpmBaseService() throws WTException {
	StandardEpmBaseService instance = new StandardEpmBaseService();
	instance.initialize();
	return instance;
    }

    @Override
    public String validCheckoutInfoEpm(EPMDocument epmDoc) {
	return new EpmEcoValidator().validCheckoutInfoEpm(epmDoc);
    }

    @Override
    public void updateIbaCadApptype(EPMDocument epm) throws Exception {
	// TODO Auto-generated method stub

	IBAHolder ibaHolder = null;
	DefaultAttributeContainer container = null;
	CADAppType cadApp = null;

	try {
	    ibaHolder = IBAValueHelper.service.refreshAttributeContainer(epm, null, null, null);
	} catch (RemoteException e) {
	    Kogger.error(getClass(), e);
	    throw new WTException(e);
	}
	EDMAttributes edmAttributes = EDMAttributes.getInstance();
	container = (DefaultAttributeContainer) ibaHolder.getAttributeContainer();

	cadApp = CADAppType.toCADAppType(epm.getAuthoringApplication().toString());

	AttributeDefDefaultView dv = (AttributeDefDefaultView) edmAttributes.getEPMAttribute(EDMHelper.IBA_CAD_APP_TYPE);
	AbstractValueView avv = (AbstractValueView) NewValueCreator.createNewValueObject(dv);
	try {
	    String value = (String) EDMAttributeUtil.getConvertedValue(cadApp.getDisplay(Locale.KOREAN),
		    avv.getClass().getDeclaredField("value").getType());

	    EpmInfoUpadator updator = new EpmInfoUpadator();
	    updator.callupdateIBAValue(epm, EDMHelper.IBA_CAD_APP_TYPE, value);

	} catch (SecurityException e) {
	    Kogger.error(getClass(), e);
	} catch (NoSuchFieldException e) {
	    Kogger.error(getClass(), e);
	}

	// if (!WorkInProgressHelper.isCheckedOut(epm)) {
	// wt.vc.wip.Workable workable = (wt.vc.wip.Workable) epm;
	// wt.vc.wip.CheckoutLink checkOutLink = null;
	// wt.vc.wip.Workable workingCopy = null;
	// wt.vc.wip.Workable orgCopy = null;
	//
	// try {
	// wt.folder.Folder folder = wt.vc.wip.WorkInProgressHelper.service.getCheckoutFolder();
	// // Folder folder = FolderHelper.service.getFolder("/Default");
	// Kogger.debug(getClass(), "Folder is " + folder);
	// Kogger.debug(getClass(), "++++++++++++++++++++++Document Check-out Started...");
	// Kogger.debug(getClass(), "checkOutLink Is: " + workable + " and Folder is : " + folder);
	// try {
	// checkOutLink = (wt.vc.wip.CheckoutLink) wt.vc.wip.WorkInProgressHelper.service.checkout(workable, folder, "");
	// } catch (wt.util.WTPropertyVetoException wtpve) {
	// Kogger.debug(getClass(), "++++++++++++++++++++++Document Check-out wtpve error :" + wtpve.getLocalizedMessage());
	// Kogger.error(getClass(), wtpve);
	// }
	// // get Original copy
	// orgCopy = checkOutLink.getOriginalCopy();
	//
	// Kogger.debug(getClass(), "orgCopy is " + orgCopy);
	// // get working copy
	// workingCopy = checkOutLink.getWorkingCopy();
	//
	// Kogger.debug(getClass(), "workingCopy is " + workingCopy);
	//
	// epm = (EPMDocument) workingCopy;
	// } catch (WTException wte) {
	// Kogger.error(getClass(), wte);
	//
	// throw new WTException(wte.getMessage());
	// }
	// }
	//
	// IBAHolder ibaHolder = null;
	// DefaultAttributeContainer container = null;
	// CADAppType cadApp = null;
	//
	// try {
	// ibaHolder = IBAValueHelper.service.refreshAttributeContainer(epm, null, null, null);
	// } catch (RemoteException e) {
	// Kogger.error(getClass(), e);
	// throw new WTException(e);
	// }
	// EDMAttributes edmAttributes = EDMAttributes.getInstance();
	// container = (DefaultAttributeContainer) ibaHolder.getAttributeContainer();
	//
	// cadApp = CADAppType.toCADAppType(epm.getAuthoringApplication().toString());
	//
	// AttributeDefDefaultView dv = (AttributeDefDefaultView) edmAttributes.getEPMAttribute(EDMHelper.IBA_CAD_APP_TYPE);
	// AbstractValueView avv = (AbstractValueView) NewValueCreator.createNewValueObject(dv);
	// try {
	// EDMAttributeUtil.setValue(avv, EDMAttributeUtil.getConvertedValue(cadApp.getDisplay(Locale.KOREAN),
	// avv.getClass().getDeclaredField("value").getType()));
	// } catch (SecurityException e) {
	// Kogger.error(getClass(), e);
	// } catch (NoSuchFieldException e) {
	// Kogger.error(getClass(), e);
	// }
	//
	// container.addAttributeValue(avv);
	// ibaHolder.setAttributeContainer(container);
	// try {
	// ibaHolder = IBAValueHelper.service.updateIBAHolder(ibaHolder, null, null, null);
	// } catch (RemoteException e) {
	// Kogger.error(getClass(), e);
	// }
	// // EDMAttributeUtil.updateIBAHolder(ibaHolder);
	//
	// WorkInProgressHelper.service.checkin(epm, "");
	//
	// PersistenceHelper.manager.refresh(epm);

    }

    @Override
    public Map<String, Object> uploadBatchMold(Map<String, Object> reqMap) throws Exception {
	Map<String, Object> resMap = new HashMap<String, Object>();
	ObjectMapper mapper = new ObjectMapper();
	String path = "";
	WTUser sessionUser = (WTUser) SessionHelper.manager.getPrincipal();
	try {
	    String formDataStr = (String) reqMap.get("jsonData");
	    List<MultipartFile> moldFileList = (List<MultipartFile>) reqMap.get("fileList");
	    MultipartFile templateFile = (MultipartFile) reqMap.get("templateFile");

	    Map<String, Object> formData = mapper.readValue(formDataStr, new TypeReference<Map<String, Object>>() {
	    });
	    Workbook wb = null;
	    Vector<EPMLoadData> paramV = new Vector<EPMLoadData>();

	    path = "D:\\ptc\\Windchill_10.2\\Windchill\\temp\\edm\\" + DateUtil.getCurrentTimestamp().getTime();

	    createDir(path);// 파일 transfer 대상 폴더 생성

	    File uploadFile = new File(path + File.separator + templateFile.getOriginalFilename());
	    templateFile.transferTo(uploadFile);// 템플릿 엑셀 transter

	    for (MultipartFile file : moldFileList) {// 업로드 대상 금형도면 파일 transfer
		File moldFile = new File(path + File.separator + file.getOriginalFilename());
		file.transferTo(moldFile);
	    }

	    System.out.println("DRMHelper.useDrm : " + DRMHelper.useDrm);

	    // if (DRMHelper.useDrm) {// 템플릿 엑셀 복호화
	    //
	    // }
	    uploadFile = DRMHelper.Decryptupload(uploadFile, uploadFile.getName());

	    try {
		wb = Workbook.getWorkbook(uploadFile);
	    } catch (Exception ex) {
		ex.printStackTrace();
		throw new Exception("템플릿 엑셀 양식이 적합하지 않습니다.\r\n.xls파일만 등록가능합니다.");
	    }

	    String checkMessage = moldUploadTargetExtract(path, wb, formData, paramV);// 데이터 검증 및 업로드대상 추출

	    if (StringUtils.isNotEmpty(checkMessage)) {// validation 검증 실패시 메세지 리턴
		resMap.put("result", false);
		resMap.put("message", checkMessage);
	    } else if (paramV.size() > 0) {// validaton 검증 성공 후 업로드 대상이 있다면 도면 등록
		EPMLoadHelper.load(sessionUser.getName(), paramV, path);

		resMap.put("message", "작업이 완료되었습니다.");
		resMap.put("result", true);
	    } else {
		resMap.put("message", "업로드 대상도면이 없습니다.\r\n금형도면 템플릿의 CAD_Number(도면유/무) 항목값이 전부 NA입니다.");
		resMap.put("result", true);
	    }

	} catch (Exception e) {
	    throw e;
	} finally {
	    EPMLoadHelper.deleteLoadFile(path, sessionUser.getName());
	}

	return resMap;
    }

    private void createDir(String path) {
	File tempDir = new File(path);
	tempDir.mkdir();
    }

    private String moldUploadTargetExtract(String path, Workbook wb, Map<String, Object> formData, Vector<EPMLoadData> v) throws Exception {
	String businessType = "e3ps.common.code.NumberCode:" + (String) formData.get("businessType");
	String security = (String) formData.get("security");
	String devStage = (String) formData.get("devStage");
	String category = (String) formData.get("category");
	String cadAppType = (String) formData.get("cadAppType");
	String project = (String) formData.get("project");

	Sheet[] sheets = wb.getSheets();
	Sheet sheet = sheets[0];

	int rows = sheet.getRows();
	EPMLoadData data = null;

	Map<String, String> dupMap = new HashMap<String, String>();

	String checkMessage = "";
	for (int i = 1; i < rows; i++) {
	    Cell cell[] = sheet.getRow(i);
	    data = new EPMLoadData(cell, String.valueOf(i));
	    if (data.m_moldPartNumber.trim().length() == 0) {
		checkMessage = i + " 행의 금형번호가 누락되었습니다.";
		break;
	    }
	    data.manageType = "MOLD_DRAWING";
	    data.bizOid = businessType;
	    data.devStage = devStage;
	    data.category = category;
	    data.cadAppType = cadAppType;
	    data.projectOid = project;
	    data.number = EPMLoadHelper.convNumber(data.m_moldPartNumber, category, cadAppType);
	    data.security = security;

	    data.name = data.m_moldPartDesc;

	    if (StringUtils.isNotEmpty(dupMap.get(data.number))) {// 중복체크
		continue;
	    }

	    dupMap.put(data.number, data.number);

	    if (data.isSkipRow) {
		data.isValidate = true;
	    } else {
		if (data.isEmptyField) {
		    data.isValidate = false;
		} else {
		    data.fileName = getFileName(data.m_moldPartNumber, cadAppType);
		    data.cadName = data.fileName;

		    data.iswgm = isWGM(data.manageType, data.cadAppType);

		    if (!(data.iswgm)) {
			File chkFile = new File(path + "\\" + data.fileName);

			if (!chkFile.exists()) {
			    data.isFileNotFound = true;
			}
		    }

		    data = (EPMLoadData) EPMLoadHelper.validate(data);

		    if (data.iswgm) {
			if (!(data.isExist)) {
			    data.isValidate = false;
			}
		    } else {
			if ((data.isExist)) {
			    data.isValidate = false;
			}

			if ((data.isFileNotFound)) {
			    data.isValidate = false;
			}

			if ((data.isCADName)) {
			    data.isValidate = false;
			}
		    }

		    if ((data.isAppTypeDiff)) {
			data.isValidate = false;
		    }
		}
	    }

	    if ((data.isSkipRow) || !(data.isValidate)) {
		if (!(data.isValidate)) {
		    checkMessage += getCheckMessage(data);
		    if (StringUtils.isNotEmpty(checkMessage)) {
			break;
		    }
		}

		continue;
	    }

	    v.add(data);

	}
	return checkMessage;
    }

    private String getCheckMessage(EPMLoadData data) {
	String checkMessage = "";
	checkMessage += KETMessageService.service.getString("e3ps.message.ket_message", "01275")/* 도면번호 */+ " : " + data.number
	        + "\r\n\r\n";
	if (data.isEmptyField) {
	    checkMessage += KETMessageService.service.getString("e3ps.message.ket_message", "03415") + "\r\n";/* 도면등록에 필요한 입력값이 없습니다. */
	} else {
	    if (data.iswgm) {
		if (!(data.isExist)) {
		    checkMessage += KETMessageService.service.getString("e3ps.message.ket_message", "03416") + "\r\n";/* 모델 데이터를 등록하시기 바랍니다. */
		}
	    } else {
		if ((data.isExist)) {
		    checkMessage += KETMessageService.service.getString("e3ps.message.ket_message", "03417") + "\r\n";/*
							                                                               * 동일한 도면번호로 등록된 도면이
							                                                               * 있습니다.
							                                                               */
		}

		if ((data.isFileNotFound)) {
		    checkMessage += KETMessageService.service.getString("e3ps.message.ket_message", "03418") + "(" + data.fileName + ")"
			    + "\r\n";/*
		                      * CAD 파일이 존재하지 않습니다 .
		                      */
		}

		if ((data.isCADName)) {
		    checkMessage += KETMessageService.service.getString("e3ps.message.ket_message", "03419") + "(" + data.fileName + ")"
			    + "\r\n";/*
		                      * 동일한 CAD 파일 명으로 등록된 도면이 있습니다 .
		                      */
		}
	    }

	    if ((data.isAppTypeDiff)) {
		checkMessage += KETMessageService.service.getString("e3ps.message.ket_message", "03420") + "\r\n";/*
							                                                           * 이미 등록된 도면의 CAD 종류가
							                                                           * 다릅니다.
							                                                           */
	    }
	}

	return checkMessage;
    }

    private String getFileName(String number, String cadAppType) {
	String fileName = number;

	if ("UG".equals(cadAppType.toUpperCase())) {
	    fileName += ".prt";
	} else if ("ACAD".equals(cadAppType.toUpperCase())) {
	    fileName += ".dwg";
	} else if ("EXCESS".equals(cadAppType.toUpperCase())) {
	    fileName += ".pls";
	} else {
	    fileName += "";
	}

	return fileName;
    }

    private boolean isWGM(String manageType, String cadAppType) {
	if ("MOLD_DRAWING".equals(manageType)) {
	    if ("UG".equals(cadAppType)) {
		return true;
	    }
	}

	return false;
    }

}