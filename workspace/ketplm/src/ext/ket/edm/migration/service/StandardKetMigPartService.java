package ext.ket.edm.migration.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import wt.content.ContentHolder;
import wt.content.ContentItem;
import wt.content.ContentRoleType;
import wt.fc.PersistenceServerHelper;
import wt.fc.collections.WTArrayList;
import wt.fc.collections.WTCollection;
import wt.method.MethodContext;
import wt.part.WTPart;
import wt.part.WTPartTypeInfo;
import wt.part.WTPartTypeInterface;
import wt.pom.WTConnection;
import wt.services.StandardManager;
import wt.util.WTException;
import e3ps.common.dao.DaoFactory;
import e3ps.common.dao.DaoManager;
import e3ps.common.dao.RowSetStrategy;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import ext.ket.edm.service.PlmHpIfCtl;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.entity.dto.PartBaseDTO;
import ext.ket.part.migration.erp.service.MigLogUtil;
import ext.ket.shared.content.entity.ContentDTO;
import ext.ket.shared.content.entity.UploadedFile;
import ext.ket.shared.content.service.KETContentHelper;
import ext.ket.shared.dto.BaseDTO;
import ext.ket.shared.log.Kogger;

public class StandardKetMigPartService extends StandardManager implements KetMigPartService {

    private static final long serialVersionUID = 1L;
    private MigLogUtil migLogUtil = new MigLogUtil();

    final int FETCH_SIZE = 1000;

    public static StandardKetMigPartService newStandardKetMigPartService() throws WTException {
	StandardKetMigPartService instance = new StandardKetMigPartService();
	instance.initialize();
	return instance;
    }

    private StringBuilder getMigPartSql() {
	StringBuilder sSql = new StringBuilder();

	sSql.append("SELECT * FROM ( ");
	sSql.append("SELECT C.*, ROWNUM RN FROM ( ");
	sSql.append("SELECT * FROM PART_MIG  ) C ) ");
	sSql.append("WHERE RN >= ? AND RN < ? ");

	return sSql;
    }

    public boolean updateMigPart() throws Exception {
	boolean result = true;

	Connection oConnection = null;
	PreparedStatement oPstmt = null;
	ResultSet rs = null;

	// Transaction trx = null;

	try {
	    MethodContext oMethodContext = MethodContext.getContext();
	    WTConnection wtConnection = (WTConnection) oMethodContext.getConnection();
	    oConnection = wtConnection.getConnection();

	    StringBuilder sSql = getMigPartSql();

	    oPstmt = oConnection.prepareStatement(sSql.toString());

	    WTCollection wtCollection = null;

	    for (int idx = 0; idx < 10000; idx += FETCH_SIZE) {
		// trx = new Transaction();
		// trx.start();

		wtCollection = new WTArrayList();
		oPstmt.setInt(1, idx);
		oPstmt.setInt(2, idx + FETCH_SIZE);

		rs = oPstmt.executeQuery();

		if (rs.next()) {
		    // migLogUtil.log("EPM_Revision", logEpmDoc, "DevStageNMold", "개발단계 및 금형 제품 구분 부재", logDevStage + "[" + cadType +"][" +
		    // cadFileName + "]", (logEpmDoc==null?null:logEpmDoc.getNumber()));
		    wtCollection = updatePart(rs, wtCollection);
		} else {
		    // migLogUtil.log("EPM_Revision", logEpmDoc, "DevStageNMold", "개발단계 및 금형 제품 구분 부재", logDevStage + "[" + cadType +"][" +
		    // cadFileName + "]", (logEpmDoc==null?null:logEpmDoc.getNumber()));
		    result = false;
		    break;
		}

		while (rs.next()) {
		    wtCollection = updatePart(rs, wtCollection);
		}
		PersistenceServerHelper.manager.update(wtCollection);
		// PersistenceHelper.manager.save(wtCollection);

		// trx.commit();
		// trx = null;
	    }

	} catch (Exception e) {
	    // migLogUtil.log("EPM_Revision", logEpmDoc, "DevStageNMold", "개발단계 및 금형 제품 구분 부재", logDevStage + "[" + cadType +"][" +
	    // cadFileName + "]", (logEpmDoc==null?null:logEpmDoc.getNumber()));
	    throw e;
	} finally {
	    // if (trx != null)
	    // trx.rollback();
	    try {
		if (oPstmt != null)
		    oPstmt.close();
	    } catch (Exception e) {
	    }
	    try {
		if (rs != null)
		    rs.close();
	    } catch (Exception e) {
	    }
	}

	return result;
    }

    public boolean updateMigPart2() throws Exception {
	boolean result = true;

	Connection oConnection = null;
	PreparedStatement oPstmt = null;
	ResultSet rs = null;

	// Transaction trx = null;

	try {
	    MethodContext oMethodContext = MethodContext.getContext();
	    WTConnection wtConnection = (WTConnection) oMethodContext.getConnection();
	    oConnection = wtConnection.getConnection();

	    StringBuilder sSql = getMigPartSql();

	    oPstmt = oConnection.prepareStatement(sSql.toString());

	    WTCollection wtCollection = null;

	    for (int idx = 0; idx < 10000; idx += FETCH_SIZE) {
		// trx = new Transaction();
		// trx.start();

		wtCollection = new WTArrayList();
		oPstmt.setInt(1, idx);
		oPstmt.setInt(2, idx + FETCH_SIZE);

		rs = oPstmt.executeQuery();

		while (rs.next()) {
		    updatePartImg(rs);
		}
	    }

	} catch (Exception e) {
	    // migLogUtil.log("EPM_Revision", logEpmDoc, "DevStageNMold", "개발단계 및 금형 제품 구분 부재", logDevStage + "[" + cadType +"][" +
	    // cadFileName + "]", (logEpmDoc==null?null:logEpmDoc.getNumber()));
	    throw e;
	} finally {
	    // if (trx != null)
	    // trx.rollback();
	    try {
		if (oPstmt != null)
		    oPstmt.close();
	    } catch (Exception e) {
	    }
	    try {
		if (rs != null)
		    rs.close();
	    } catch (Exception e) {
	    }
	}

	return result;
    }

    private void updatePartImg(ResultSet rs) throws Exception {

	String wtpartnumber = rs.getString("PARTNUMBER");

	WTPart part = PartBaseHelper.service.getLatestPart(wtpartnumber);

	if (part != null) {
	    PartBaseDTO dto = new PartBaseDTO();

	    String filePath = StringUtil.checkNull(rs.getString("PARTIMG"));

	    if (!"".equals(filePath)) {
		File file = new File(filePath);
		Path path = Paths.get(filePath);
		String name = file.getName();
		String originalFileName = file.getName();
		String contentType = "text/plain";
		byte[] content = null;
		try {
		    content = Files.readAllBytes(path);
		} catch (final IOException e) {
		}
		MultipartFile multipartFile = new MockMultipartFile(name, originalFileName, contentType, content);

		dto.setPrimaryFile(multipartFile);

		this.updateContent(part, dto);
	    }

	}
    }

    public void updatePartImg2() throws Exception {

	String wtpartnumber = "520308-5";

	WTPart part = PartBaseHelper.service.getLatestPart(wtpartnumber);

	if (part != null) {
	    PartBaseDTO dto = new PartBaseDTO();

	    String filePath = "C:/450007.jpg";

	    File file = new File(filePath);
	    Path path = Paths.get(filePath);
	    String name = file.getName();
	    String originalFileName = file.getName();
	    String contentType = "text/plain";
	    byte[] content = null;
	    try {
		content = Files.readAllBytes(path);
	    } catch (final IOException e) {
	    }
	    MultipartFile multipartFile = new MockMultipartFile(name, originalFileName, contentType, content);

	    dto.setPrimaryFile(multipartFile);

	    this.updateContent(part, dto);

	}
    }

    public void updateContent(ContentHolder holder, BaseDTO dto) throws Exception {

	List<ContentItem> contentItems = new ArrayList<ContentItem>(); // not remove contentitem list
	List<UploadedFile> files = new ArrayList<UploadedFile>(); // uploaded new file list

	// getting uploaded new file list
	MultipartFile primaryFile = dto.getPrimaryFile();
	UploadedFile uploadedFile = UploadedFile.newUploadedFile(primaryFile, true);
	files.add(uploadedFile);

	this.updateContent(holder, files, contentItems);
    }

    public void updateContent(ContentHolder holder, List<UploadedFile> uploadedFiles, List<ContentItem> contentItems) throws Exception {

	if (uploadedFiles != null && uploadedFiles.size() > 0) {
	    for (UploadedFile file : uploadedFiles) {
		if (file != null && file.getFile() != null && file.getSize() > 0) {
		    ContentRoleType role = ContentRoleType.toContentRoleType("PRIMARY");
		    ContentDTO primaryDto = KETContentHelper.manager.getPrimaryContent(holder);
		    if (primaryDto != null)
			holder = KETContentHelper.service.delete(holder, (ContentItem) CommonUtil.getObject(primaryDto.getContentoid()));
		    Kogger.debug(getClass(), "ADD ContentRoleType : " + role);
		    KETContentHelper.service.attache(holder, file, role);
		}
	    }
	}
    }

    private WTCollection updatePart(ResultSet rs, WTCollection wtCollection) throws Exception {

	String wtpartnumber = rs.getString("PARTNUMBER");

	WTPart part = PartBaseHelper.service.getLatestPart(wtpartnumber);

	if (part != null) {

	    WTPartTypeInterface wtPartTypeInterface = (WTPartTypeInterface) part;
	    WTPartTypeInfo wtPartTypeInfo = wtPartTypeInterface.getTypeInfoWTPart();

	    if (!"".equals(StringUtil.checkNull(rs.getString("spPartNameHis"))))
		wtPartTypeInfo.setPtc_str_1(rs.getString("spPartNameHis"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spPartRevision"))))
		wtPartTypeInfo.setPtc_str_2(rs.getString("spPartRevision"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spPartType"))))
		wtPartTypeInfo.setPtc_str_3(rs.getString("spPartType"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spPartDevLevel"))))
		wtPartTypeInfo.setPtc_str_4(rs.getString("spPartDevLevel"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spIsDeleted"))))
		wtPartTypeInfo.setPtc_str_5(rs.getString("spIsDeleted"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spBOMFlag"))))
		wtPartTypeInfo.setPtc_str_6(rs.getString("spBOMFlag"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spWeightUnit"))))
		wtPartTypeInfo.setPtc_str_7(rs.getString("spWeightUnit"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spNetWeight"))))
		wtPartTypeInfo.setPtc_str_8(rs.getString("spNetWeight"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spTotalWeight"))))
		wtPartTypeInfo.setPtc_str_9(rs.getString("spTotalWeight"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spScrabWeight"))))
		wtPartTypeInfo.setPtc_str_10(rs.getString("spScrabWeight"));

	    if (!"".equals(StringUtil.checkNull(rs.getString("spIsYazaki"))))
		wtPartTypeInfo.setPtc_str_11(rs.getString("spIsYazaki"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spYazakiNo"))))
		wtPartTypeInfo.setPtc_str_12(rs.getString("spYazakiNo"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spManufacPlace"))))
		wtPartTypeInfo.setPtc_str_13(rs.getString("spManufacPlace"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spCustomRev"))))
		wtPartTypeInfo.setPtc_str_14(rs.getString("spCustomRev"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spScrabCode"))))
		wtPartTypeInfo.setPtc_str_15(rs.getString("spScrabCode"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spWaterProof"))))
		wtPartTypeInfo.setPtc_str_16(rs.getString("spWaterProof"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spMaterialMark"))))
		wtPartTypeInfo.setPtc_str_17(rs.getString("spMaterialMark"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spColor"))))
		wtPartTypeInfo.setPtc_str_18(rs.getString("spColor"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spColorPurch"))))
		wtPartTypeInfo.setPtc_str_19(rs.getString("spColorPurch"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spColorElec"))))
		wtPartTypeInfo.setPtc_str_20(rs.getString("spColorElec"));

	    if (!"".equals(StringUtil.checkNull(rs.getString("spSeries"))))
		wtPartTypeInfo.setPtc_str_21(rs.getString("spSeries"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spSeriesLAMP"))))
		wtPartTypeInfo.setPtc_str_22(rs.getString("spSeriesLAMP"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spProdSize"))))
		wtPartTypeInfo.setPtc_str_23(rs.getString("spProdSize"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spEnvFriend"))))
		wtPartTypeInfo.setPtc_str_24(rs.getString("spEnvFriend"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spManufAt"))))
		wtPartTypeInfo.setPtc_str_25(rs.getString("spManufAt"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spTGenConn"))))
		wtPartTypeInfo.setPtc_str_26(rs.getString("spTGenConn"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spTPCBConn"))))
		wtPartTypeInfo.setPtc_str_27(rs.getString("spTPCBConn"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spTLAMPConn"))))
		wtPartTypeInfo.setPtc_str_28(rs.getString("spTLAMPConn"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spTHiVolt"))))
		wtPartTypeInfo.setPtc_str_29(rs.getString("spTHiVolt"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spTCharger"))))
		wtPartTypeInfo.setPtc_str_30(rs.getString("spTCharger"));

	    if (!"".equals(StringUtil.checkNull(rs.getString("spTHiVoltFuse"))))
		wtPartTypeInfo.setPtc_str_31(rs.getString("spTHiVoltFuse"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spTJnB"))))
		wtPartTypeInfo.setPtc_str_32(rs.getString("spTJnB"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spTPRA"))))
		wtPartTypeInfo.setPtc_str_33(rs.getString("spTPRA"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spTGNSS"))))
		wtPartTypeInfo.setPtc_str_34(rs.getString("spTGNSS"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spTComModule"))))
		wtPartTypeInfo.setPtc_str_35(rs.getString("spTComModule"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spTMultiMedUnit"))))
		wtPartTypeInfo.setPtc_str_36(rs.getString("spTMultiMedUnit"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spLanceType"))))
		wtPartTypeInfo.setPtc_str_37(rs.getString("spLanceType"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spNoOfPole"))))
		wtPartTypeInfo.setPtc_str_38(rs.getString("spNoOfPole"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spMTerminal"))))
		wtPartTypeInfo.setPtc_str_39(rs.getString("spMTerminal").replaceAll(" ", ""));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spMConnector"))))
		wtPartTypeInfo.setPtc_str_40(rs.getString("spMConnector").replaceAll(" ", ""));

	    if (!"".equals(StringUtil.checkNull(rs.getString("spMatchBulb"))))
		wtPartTypeInfo.setPtc_str_41(rs.getString("spMatchBulb").replaceAll(" ", ""));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spMClip"))))
		wtPartTypeInfo.setPtc_str_42(rs.getString("spMClip").replaceAll(" ", ""));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spMRH"))))
		wtPartTypeInfo.setPtc_str_43(rs.getString("spMRH").replaceAll(" ", ""));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spLockingType"))))
		wtPartTypeInfo.setPtc_str_44(rs.getString("spLockingType"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spLockTTML"))))
		wtPartTypeInfo.setPtc_str_45(rs.getString("spLockTTML"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spMWireSeal"))))
		wtPartTypeInfo.setPtc_str_47(rs.getString("spMWireSeal").replaceAll(" ", ""));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spRepresentM"))))
		wtPartTypeInfo.setPtc_str_48(rs.getString("spRepresentM"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spMaterialInfo"))))
		wtPartTypeInfo.setPtc_str_49(rs.getString("spMaterialInfo"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spMaterNotFe"))))
		wtPartTypeInfo.setPtc_str_50(rs.getString("spMaterNotFe"));

	    if (!"".equals(StringUtil.checkNull(rs.getString("spMaterDie"))))
		wtPartTypeInfo.setPtc_str_51(rs.getString("spMaterDie"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spMaterPurch"))))
		wtPartTypeInfo.setPtc_str_52(rs.getString("spMaterPurch"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spPlating"))))
		wtPartTypeInfo.setPtc_str_53(rs.getString("spPlating"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spPlatingPurch"))))
		wtPartTypeInfo.setPtc_str_54(rs.getString("spPlatingPurch"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spCustomPartNo"))))
		wtPartTypeInfo.setPtc_str_55(rs.getString("spCustomPartNo"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spPermitVolt"))))
		wtPartTypeInfo.setPtc_str_56(rs.getString("spPermitVolt"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spWireRangeAWG"))))
		wtPartTypeInfo.setPtc_str_57(rs.getString("spWireRangeAWG"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spWireRAWGElec"))))
		wtPartTypeInfo.setPtc_str_58(rs.getString("spWireRAWGElec"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spWireRangeMm"))))
		wtPartTypeInfo.setPtc_str_59(rs.getString("spWireRangeMm"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spTabThickness"))))
		wtPartTypeInfo.setPtc_str_60(rs.getString("spTabThickness"));

	    if (!"".equals(StringUtil.checkNull(rs.getString("spMaterThick"))))
		wtPartTypeInfo.setPtc_str_61(rs.getString("spMaterThick"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spBracketSizeD"))))
		wtPartTypeInfo.setPtc_str_62(rs.getString("spBracketSizeD"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spBracketSizeH"))))
		wtPartTypeInfo.setPtc_str_63(rs.getString("spBracketSizeH"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spBracketSizeT"))))
		wtPartTypeInfo.setPtc_str_64(rs.getString("spBracketSizeT"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spRelayCapa"))))
		wtPartTypeInfo.setPtc_str_65(rs.getString("spRelayCapa"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spEResistVal"))))
		wtPartTypeInfo.setPtc_str_66(rs.getString("spEResistVal"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spVoltSensCapa"))))
		wtPartTypeInfo.setPtc_str_67(rs.getString("spVoltSensCapa"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spManufPartNo"))))
		wtPartTypeInfo.setPtc_str_68(rs.getString("spManufPartNo"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spValueValue"))))
		wtPartTypeInfo.setPtc_str_69(rs.getString("spValueValue"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spTolerance"))))
		wtPartTypeInfo.setPtc_str_70(rs.getString("spTolerance"));

	    if (!"".equals(StringUtil.checkNull(rs.getString("spManufacturer"))))
		wtPartTypeInfo.setPtc_str_71(rs.getString("spManufacturer"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spRatedVoltage"))))
		wtPartTypeInfo.setPtc_str_72(rs.getString("spRatedVoltage"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spWatt"))))
		wtPartTypeInfo.setPtc_str_73(rs.getString("spWatt"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spOperTemp"))))
		wtPartTypeInfo.setPtc_str_74(rs.getString("spOperTemp"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spPackageType"))))
		wtPartTypeInfo.setPtc_str_75(rs.getString("spPackageType"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spMSL"))))
		wtPartTypeInfo.setPtc_str_76(rs.getString("spMSL"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spESD"))))
		wtPartTypeInfo.setPtc_str_77(rs.getString("spESD"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spAECQ"))))
		wtPartTypeInfo.setPtc_str_78(rs.getString("spAECQ"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spCharact1"))))
		wtPartTypeInfo.setPtc_str_79(rs.getString("spCharact1"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spCharact2"))))
		wtPartTypeInfo.setPtc_str_80(rs.getString("spCharact2"));

	    if (!"".equals(StringUtil.checkNull(rs.getString("spHoleSize"))))
		wtPartTypeInfo.setPtc_str_81(rs.getString("spHoleSize"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spWireRangeSQ"))))
		wtPartTypeInfo.setPtc_str_82(rs.getString("spWireRangeSQ"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spWATTW"))))
		wtPartTypeInfo.setPtc_str_83(rs.getString("spWATTW"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spSizemm"))))
		wtPartTypeInfo.setPtc_str_84(rs.getString("spSizemm"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spPermitTempC"))))
		wtPartTypeInfo.setPtc_str_85(rs.getString("spPermitTempC"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spIsResin"))))
		wtPartTypeInfo.setPtc_str_86(rs.getString("spIsResin"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spThickness"))))
		wtPartTypeInfo.setPtc_str_87(rs.getString("spThickness"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spHoleShape"))))
		wtPartTypeInfo.setPtc_str_88(rs.getString("spHoleShape"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spCapaEResist"))))
		wtPartTypeInfo.setPtc_str_89(rs.getString("spCapaEResist"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spClothesMater"))))
		wtPartTypeInfo.setPtc_str_90(rs.getString("spClothesMater"));

	    if (!"".equals(StringUtil.checkNull(rs.getString("spIsShield"))))
		wtPartTypeInfo.setPtc_str_91(rs.getString("spIsShield"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spESIRApproval"))))
		wtPartTypeInfo.setPtc_str_92(rs.getString("spESIRApproval"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spISIRApproval"))))
		wtPartTypeInfo.setPtc_str_93(rs.getString("spISIRApproval"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spConAppNoESIR"))))
		wtPartTypeInfo.setPtc_str_94(rs.getString("spConAppNoESIR"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spLayer"))))
		wtPartTypeInfo.setPtc_str_95(rs.getString("spLayer"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spOrgDskPartNo"))))
		wtPartTypeInfo.setPtc_str_96(rs.getString("spOrgDskPartNo"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spPCBMaterial"))))
		wtPartTypeInfo.setPtc_str_97(rs.getString("spPCBMaterial"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spTg"))))
		wtPartTypeInfo.setPtc_str_98(rs.getString("spTg"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spCharact"))))
		wtPartTypeInfo.setPtc_str_99(rs.getString("spCharact"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spSurfaceTreat"))))
		wtPartTypeInfo.setPtc_str_100(rs.getString("spSurfaceTreat"));

	    if (!"".equals(StringUtil.checkNull(rs.getString("spAppearanProc"))))
		wtPartTypeInfo.setPtc_str_101(rs.getString("spAppearanProc"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spSoldResColor"))))
		wtPartTypeInfo.setPtc_str_102(rs.getString("spSoldResColor"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spInstallPos"))))
		wtPartTypeInfo.setPtc_str_103(rs.getString("spInstallPos"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spSwVersion"))))
		wtPartTypeInfo.setPtc_str_104(rs.getString("spSwVersion"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spUnitUnit"))))
		wtPartTypeInfo.setPtc_str_105(rs.getString("spUnitUnit"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spResUnit"))))
		wtPartTypeInfo.setPtc_str_106(rs.getString("spResUnit"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spINDUnit"))))
		wtPartTypeInfo.setPtc_str_107(rs.getString("spINDUnit"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spCapUnit"))))
		wtPartTypeInfo.setPtc_str_108(rs.getString("spCapUnit"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spRLCPackType"))))
		wtPartTypeInfo.setPtc_str_109(rs.getString("spRLCPackType"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spRLCTolerance"))))
		wtPartTypeInfo.setPtc_str_110(rs.getString("spRLCTolerance"));

	    if (!"".equals(StringUtil.checkNull(rs.getString("spFestPrevent"))))
		wtPartTypeInfo.setPtc_str_111(rs.getString("spFestPrevent"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spConnCombDir"))))
		wtPartTypeInfo.setPtc_str_112(rs.getString("spConnCombDir"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spCertified"))))
		wtPartTypeInfo.setPtc_str_113(rs.getString("spCertified"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spPackageSpec"))))
		wtPartTypeInfo.setPtc_str_114(rs.getString("spPackageSpec"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spTabSize"))))
		wtPartTypeInfo.setPtc_str_115(rs.getString("spTabSize"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spSealType"))))
		wtPartTypeInfo.setPtc_str_116(rs.getString("spSealType"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spStudSize"))))
		wtPartTypeInfo.setPtc_str_117(rs.getString("spStudSize"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spPitch"))))
		wtPartTypeInfo.setPtc_str_118(rs.getString("spPitch"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spSoldering"))))
		wtPartTypeInfo.setPtc_str_119(rs.getString("spSoldering"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spConstrucMeth"))))
		wtPartTypeInfo.setPtc_str_120(rs.getString("spConstrucMeth"));

	    if (!"".equals(StringUtil.checkNull(rs.getString("spOptimumTemp"))))
		wtPartTypeInfo.setPtc_str_121(rs.getString("spOptimumTemp"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spContactRes"))))
		wtPartTypeInfo.setPtc_str_122(rs.getString("spContactRes"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spInsulatRes"))))
		wtPartTypeInfo.setPtc_str_123(rs.getString("spInsulatRes"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spConntHeight"))))
		wtPartTypeInfo.setPtc_str_124(rs.getString("spConntHeight"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spCableConMeth"))))
		wtPartTypeInfo.setPtc_str_125(rs.getString("spCableConMeth"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spTerminalType"))))
		wtPartTypeInfo.setPtc_str_126(rs.getString("spTerminalType"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spTermBarrelSz"))))
		wtPartTypeInfo.setPtc_str_127(rs.getString("spTermBarrelSz"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spTermPrezType"))))
		wtPartTypeInfo.setPtc_str_128(rs.getString("spTermPrezType"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spGWITMaterAt"))))
		wtPartTypeInfo.setPtc_str_129(rs.getString("spGWITMaterAt"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spInterface"))))
		wtPartTypeInfo.setPtc_str_130(rs.getString("spInterface"));

	    if (!"".equals(StringUtil.checkNull(rs.getString("spPinNShape"))))
		wtPartTypeInfo.setPtc_str_131(rs.getString("spPinNShape"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spSUBSuppliy"))))
		wtPartTypeInfo.setPtc_str_132(rs.getString("spSUBSuppliy"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spCodingNColor"))))
		wtPartTypeInfo.setPtc_str_133(rs.getString("spCodingNColor"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spPlant"))))
		wtPartTypeInfo.setPtc_str_134(rs.getString("spPlant"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spPuchaseGroup"))))
		wtPartTypeInfo.setPtc_str_135(rs.getString("spPuchaseGroup"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spDevManNm"))))
		wtPartTypeInfo.setPtc_str_136(rs.getString("spDevManNm"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spDesignManNm"))))
		wtPartTypeInfo.setPtc_str_137(rs.getString("spDesignManNm"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spManufacManNm"))))
		wtPartTypeInfo.setPtc_str_138(rs.getString("spManufacManNm"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spMContractSAt"))))
		wtPartTypeInfo.setPtc_str_139(rs.getString("spMContractSAt"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spMMoldAt"))))
		wtPartTypeInfo.setPtc_str_140(rs.getString("spMMoldAt"));

	    if (!"".equals(StringUtil.checkNull(rs.getString("spMMakingAt"))))
		wtPartTypeInfo.setPtc_str_141(rs.getString("spMMakingAt"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spMProdAt"))))
		wtPartTypeInfo.setPtc_str_142(rs.getString("spMProdAt"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spDieWhere"))))
		wtPartTypeInfo.setPtc_str_143(rs.getString("spDieWhere"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spObjectiveCT"))))
		wtPartTypeInfo.setPtc_str_144(rs.getString("spObjectiveCT"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spCavityStd"))))
		wtPartTypeInfo.setPtc_str_145(rs.getString("spCavityStd"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spMountWayApE"))))
		wtPartTypeInfo.setPtc_str_146(rs.getString("spMountWayApE"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spMaterMaker"))))
		wtPartTypeInfo.setPtc_str_147(rs.getString("spMaterMaker"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spMaterType"))))
		wtPartTypeInfo.setPtc_str_148(rs.getString("spMaterType"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spThickWH"))))
		wtPartTypeInfo.setPtc_str_149(rs.getString("spThickWH"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spEoNo"))))
		wtPartTypeInfo.setPtc_str_150(rs.getString("spEoNo"));

	    if (!"".equals(StringUtil.checkNull(rs.getString("spSeriesBulb"))))
		wtPartTypeInfo.setPtc_str_151(rs.getString("spSeriesBulb"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spTBulbConn"))))
		wtPartTypeInfo.setPtc_str_152(rs.getString("spTBulbConn"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spT2Charger"))))
		wtPartTypeInfo.setPtc_str_153(rs.getString("spT2Charger"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spT1HiVoltFuz"))))
		wtPartTypeInfo.setPtc_str_154(rs.getString("spT1HiVoltFuz"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spMCover"))))
		wtPartTypeInfo.setPtc_str_155(rs.getString("spMCover"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spStoreTemp"))))
		wtPartTypeInfo.setPtc_str_156(rs.getString("spStoreTemp"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spPropEtc"))))
		wtPartTypeInfo.setPtc_str_157(rs.getString("spPropEtc"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spHighVoltageSensingLimit"))))
		wtPartTypeInfo.setPtc_str_158(rs.getString("spHighVoltageSensingLimit"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spMovVoltage"))))
		wtPartTypeInfo.setPtc_str_159(rs.getString("spMovVoltage"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spToleranceSensing"))))
		wtPartTypeInfo.setPtc_str_160(rs.getString("spToleranceSensing"));

	    if (!"".equals(StringUtil.checkNull(rs.getString("spEsirApprove"))))
		wtPartTypeInfo.setPtc_str_161(rs.getString("spEsirApprove"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spMainChipset"))))
		wtPartTypeInfo.setPtc_str_162(rs.getString("spMainChipset"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spFrequency"))))
		wtPartTypeInfo.setPtc_str_163(rs.getString("spFrequency"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spDcpower"))))
		wtPartTypeInfo.setPtc_str_164(rs.getString("spDcpower"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spInterfaceIt"))))
		wtPartTypeInfo.setPtc_str_165(rs.getString("spInterfaceIt"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spFeatures"))))
		wtPartTypeInfo.setPtc_str_166(rs.getString("spFeatures"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spDevSpec"))))
		wtPartTypeInfo.setPtc_str_167(rs.getString("spDevSpec"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spFlameLevel"))))
		wtPartTypeInfo.setPtc_str_168(rs.getString("spFlameLevel"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spERatedVoltage"))))
		wtPartTypeInfo.setPtc_str_169(rs.getString("spERatedVoltage"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spQCNo"))))
		wtPartTypeInfo.setPtc_str_170(rs.getString("spQCNo"));

	    if (!"".equals(StringUtil.checkNull(rs.getString("spActuatorLctn"))))
		wtPartTypeInfo.setPtc_str_171(rs.getString("spActuatorLctn"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spMaxFrequency"))))
		wtPartTypeInfo.setPtc_str_172(rs.getString("spMaxFrequency"));
	    if (!"".equals(StringUtil.checkNull(rs.getString("spImpedance"))))
		wtPartTypeInfo.setPtc_str_173(rs.getString("spImpedance"));

	    if (!"".equals(StringUtil.checkNull(rs.getString("homepageIF"))))
		wtPartTypeInfo.setPtc_str_174(rs.getString("homepageIF"));

	    if (!"".equals(StringUtil.checkNull(rs.getString("homepage2DIF"))))
		wtPartTypeInfo.setPtc_str_175(rs.getString("homepage2DIF"));

	    if (!"".equals(StringUtil.checkNull(rs.getString("hompage3DIF"))))
		wtPartTypeInfo.setPtc_str_176(rs.getString("hompage3DIF"));

	    if (!"".equals(StringUtil.checkNull(rs.getString("hompageImgIF"))))
		wtPartTypeInfo.setPtc_str_177(rs.getString("hompageImgIF"));

	    if (!"".equals(StringUtil.checkNull(rs.getString("spPartStyle"))))
		wtPartTypeInfo.setPtc_str_179(rs.getString("spPartStyle"));

	    wtCollection.add(part);
	}
	return wtCollection;
    }

    public void sendHpMig() throws Exception {
	List<Map<String, Object>> partList = this.sendHpPartMigList();

	PlmHpIfCtl send = new PlmHpIfCtl();
	send.savePlmHpIfMig(partList);

    }

    public List<Map<String, Object>> sendHpPartMigList() throws Exception {
	List<Map<String, Object>> partAttrList = new ArrayList<Map<String, Object>>();
	String queryStr = getSendHpPartList();

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

	try {
	    partAttrList = oDaoManager.queryForList(queryStr, new RowSetStrategy<Map<String, Object>>() {
		@Override
		public Map<String, Object> mapRow(ResultSet rs) throws SQLException {

		    return getSendHpPartResultSet(rs);
		}
	    });

	} catch (SQLException se) {
	    Kogger.error(getClass(), se);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {

	}

	return partAttrList;
    }

    private String checkDocument(String partNumber) {
	String checkPartNumber = "";

	if (partNumber.trim().length() > 0) {
	    if ("7".equals(partNumber.trim().substring(0, 1))) {
		checkPartNumber = "ST" + partNumber.trim();
	    } else if ("6".equals(partNumber.trim().substring(0, 1))) {
		checkPartNumber = "MG" + partNumber.trim();
	    } else if ("5".equals(partNumber.trim().substring(0, 1))) {
		checkPartNumber = "JB" + partNumber.trim();
	    } else if ("4".equals(partNumber.trim().substring(0, 1))) {
		checkPartNumber = "EM" + partNumber.trim();
	    } else {
		checkPartNumber = partNumber.trim();
	    }
	} else {
	    checkPartNumber = partNumber.trim();
	}
	return checkPartNumber;
    }

    private String getSendHpPartList() throws Exception {
	StringBuilder sb = new StringBuilder();
	sb.append("\n        SELECT  																																																														       ");
	sb.append("\n             P.IDA2A2 PARTIDA2A2,                                                                                                                 ");
	sb.append("\n             C.CATALOGUECODE spCategoryCode, C.CLASSCODE spClassification,                          ");
	sb.append("\n             (SELECT WPM.NAME FROM WTPARTMASTER WPM WHERE WPM.WTPARTNUMBER = LTRIM(PM.WTPARTNUMBER, 'H')) AS PartName,                          ");
	sb.append("\n             PM.WTPARTNUMBER PartNumber ,                         ");
	sb.append("\n             PJT.PJTNO spProjectNo, PJT.PJTNAME,PTC_STR_150TYPEINFOWTPART spEoNo,                                                                 ");
	sb.append("\n             CASE WHEN C.DIVISIONFLAG = 'C' THEN '자동차사업부'                                                                                   ");
	sb.append("\n             ELSE CASE WHEN C.DIVISIONFLAG = 'E' THEN '전자사업부'                                                                                ");
	sb.append("\n             ELSE CASE WHEN C.DIVISIONFLAG = 'K' THEN 'KETS'                                                                                      ");
	sb.append("\n             ELSE CASE WHEN C.DIVISIONFLAG = 'N' THEN 'KETN'                                                                                      ");
	sb.append("\n             ELSE '' END END END END spDivision,                                                                                                  ");
	sb.append("\n             REPLACE(LOWER(P.PTC_STR_1TYPEINFOWTPART), 'n/a') AS spPartNameHis,                                                                                            ");
	sb.append("\n             REPLACE(LOWER(P.PTC_STR_2TYPEINFOWTPART), 'n/a') AS spPartRevision,                                                                                           ");
	sb.append("\n             CASE WHEN PTC_STR_3TYPEINFOWTPART = 'H' THEN '반제품'                                                                                ");
	sb.append("\n             ELSE CASE WHEN PTC_STR_3TYPEINFOWTPART = 'R' THEN '원자재'                                                                           ");
	sb.append("\n             ELSE CASE WHEN PTC_STR_3TYPEINFOWTPART = 'S' THEN '스크랩'                                                                           ");
	sb.append("\n             ELSE CASE WHEN PTC_STR_3TYPEINFOWTPART = 'K' THEN '포장재'                                                                           ");
	sb.append("\n             ELSE CASE WHEN PTC_STR_3TYPEINFOWTPART = 'D' THEN 'Die'                                                                              ");
	sb.append("\n             ELSE CASE WHEN PTC_STR_3TYPEINFOWTPART = 'M' THEN '금형부품'                                                                         ");
	sb.append("\n             ELSE CASE WHEN PTC_STR_3TYPEINFOWTPART = 'W' THEN '상품'                                                                             ");
	sb.append("\n             ELSE CASE WHEN PTC_STR_3TYPEINFOWTPART = 'O' THEN '기타'                                                                             ");
	sb.append("\n             ELSE CASE WHEN PTC_STR_3TYPEINFOWTPART = 'YES' THEN 'YAZAKI'                                                                         ");
	sb.append("\n             ELSE '' END END END END END END END END END spPartType,                                                                              ");
	sb.append("\n             CASE WHEN P.PTC_STR_4TYPEINFOWTPART = 'PC003' THEN '개발단계'                                                                       ");
	sb.append("\n             ELSE CASE WHEN P.PTC_STR_4TYPEINFOWTPART = 'PC004' THEN '양산단계'                                                                  ");
	sb.append("\n             ELSE '' END END spPartDevLevel,                                                                                                     ");
	sb.append("\n             REPLACE(LOWER(P.PTC_STR_5TYPEINFOWTPART), 'n/a') AS spIsDeleted,                                                                                              ");
	sb.append("\n             REPLACE(LOWER(P.PTC_STR_6TYPEINFOWTPART), 'n/a') AS spBOMFlag,                                                                                                ");
	sb.append("\n             REPLACE(LOWER(P.PTC_STR_7TYPEINFOWTPART), 'n/a') AS spWeightUnit,                                                                                             ");
	sb.append("\n             REPLACE(LOWER(P.PTC_STR_8TYPEINFOWTPART), 'n/a') AS spNetWeight,                                                                                              ");
	sb.append("\n             REPLACE(LOWER(P.PTC_STR_9TYPEINFOWTPART), 'n/a') AS spTotalWeight,                                                                                            ");
	sb.append("\n             REPLACE(LOWER(P.PTC_STR_10TYPEINFOWTPART), 'n/a') AS spScrabWeight,                                                                                           ");
	sb.append("\n             '' AS spIsYazaki,                                                                                              ");
	sb.append("\n             '' AS spYazakiNo,                                                                                              ");
	sb.append("\n             '' AS spManufAt,                                                                                               ");
	sb.append("\n             '' AS spManufacPlace,                                                                                          ");
	sb.append("\n             REPLACE(LOWER(P.PTC_STR_14TYPEINFOWTPART), 'n/a') AS spCustomRev,                                                                                             ");
	sb.append("\n             REPLACE(LOWER(P.PTC_STR_15TYPEINFOWTPART), 'n/a') AS spScrabCode,                                                                                             ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SEALED' AND CODE = P.PTC_STR_16TYPEINFOWTPART) spWaterProof, ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECMATERIALMARKING' AND CODE = P.PTC_STR_17TYPEINFOWTPART) spMaterialMark,          ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECCOLOR' AND CODE = P.PTC_STR_18TYPEINFOWTPART) spColor,                           ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPCOLORPURCH' AND CODE = P.PTC_STR_19TYPEINFOWTPART) spColorPurch,                   ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPCOLORELEC' AND CODE = P.PTC_STR_20TYPEINFOWTPART) spColorElec,                     ");
	sb.append("\n             (SELECT VALUE AS NAME FROM NUMBERCODEVALUE WHERE CODETYPE = 'SPECSERIES' AND lang='en' AND CODE = P.PTC_STR_21TYPEINFOWTPART) spSeries,  ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPSERIESLAMP' AND CODE = P.PTC_STR_22TYPEINFOWTPART) spSeriesLAMP,                   ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPSERIESBULB' AND CODE = P.PTC_STR_151TYPEINFOWTPART) spSeriesBulb,                  ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_23TYPEINFOWTPART), 'n/a') AS spProdSize,                                                                                               ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPENVFRIEND' AND CODE = P.PTC_STR_24TYPEINFOWTPART) spEnvFriend,                      ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPTGENCONN' AND CODE = P.PTC_STR_26TYPEINFOWTPART) spTGenConn,                        ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPTPCBCONN' AND CODE = P.PTC_STR_27TYPEINFOWTPART) spTPCBConn,                        ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPTLAMPCONN' AND CODE = P.PTC_STR_28TYPEINFOWTPART) spTLAMPConn,                      ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPTBULBCONN' AND CODE = P.PTC_STR_152TYPEINFOWTPART) spTBulbConn,                     ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPTHIVOLT' AND CODE = P.PTC_STR_29TYPEINFOWTPART) spTHiVolt,                          ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECTYPECHARGER' AND CODE = P.PTC_STR_30TYPEINFOWTPART) spTCharger,                   ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPT2CHARGER' AND CODE = P.PTC_STR_153TYPEINFOWTPART) spT2Charger,                   ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECTYPEHIGHVOLTAGEFUSE' AND CODE = P.PTC_STR_31TYPEINFOWTPART) spTHiVoltFuse,        ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPT1HIVOLTFUZ' AND CODE = P.PTC_STR_154TYPEINFOWTPART) spT1HiVoltFuz,        ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPTJNB' AND CODE = P.PTC_STR_32TYPEINFOWTPART) spTJnB,                                ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPTPRA' AND CODE = P.PTC_STR_33TYPEINFOWTPART) spTPRA,                                ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPTGNSS' AND CODE = P.PTC_STR_34TYPEINFOWTPART) spTGNSS,                              ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPTCOMMODULE' AND CODE = P.PTC_STR_35TYPEINFOWTPART) spTComModule,                    ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPTMULTIMEDUNIT' AND CODE = P.PTC_STR_36TYPEINFOWTPART) spTMultiMedUnit,              ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPLANCETYPE' AND CODE = P.PTC_STR_37TYPEINFOWTPART) spLanceType,                      ");
	sb.append("\n             REPLACE(LOWER(P.PTC_STR_38TYPEINFOWTPART), 'n/a') AS spNoOfPole,                                                                                               ");
	sb.append("\n             REPLACE(LOWER(P.PTC_STR_39TYPEINFOWTPART), 'n/a') AS spMTerminal,                                                                                              ");
	sb.append("\n             REPLACE(LOWER(P.PTC_STR_40TYPEINFOWTPART), 'n/a') AS spMConnector,                                                                                             ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_41TYPEINFOWTPART), 'n/a') AS spMatchBulb,                                                                                                ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_42TYPEINFOWTPART), 'n/a') AS spMClip,                                                                                                    ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_43TYPEINFOWTPART), 'n/a') AS spMRH,                                                                                                      ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_155TYPEINFOWTPART), 'n/a') AS spMCover,                                                                                                  ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECLOCKINGTYPE' AND CODE = P.PTC_STR_44TYPEINFOWTPART) spLockingType,                ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECLOCKTTML' AND CODE = P.PTC_STR_45TYPEINFOWTPART) spLockTTML,                      ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECLOCKTCONN' AND CODE = P.PTC_STR_46TYPEINFOWTPART) spLockTConn,                    ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_47TYPEINFOWTPART), 'n/a') AS spMWireSeal,                                                                                                ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_48TYPEINFOWTPART), 'n/a') AS spRepresentM,                                                                                               ");
	sb.append("\n             '' AS spMaterMaker,                  ");
	sb.append("\n             (SELECT VALUE AS NAME FROM NUMBERCODEVALUE WHERE CODETYPE = 'MATERIALTYPE' AND lang='en' AND CODE = P.PTC_STR_148TYPEINFOWTPART) spMaterType, ");
	sb.append("\n             '' AS spMaterialInfo,                                        ");
	sb.append("\n             '' AS spMaterNotFe,                                           ");
	sb.append("\n             '' AS spMaterDie,                  ");
	sb.append("\n             '' AS spMaterPurch,                                                                                               ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECPLATING' AND CODE = P.PTC_STR_53TYPEINFOWTPART) spPlating,                        ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPPLATINGPURCH' AND CODE = P.PTC_STR_54TYPEINFOWTPART) spPlatingPurch,                ");
	sb.append("\n             '' AS spCustomPartNo,                                                                                             ");
	sb.append("\n             REPLACE(LOWER(REPLACE(PTC_STR_56TYPEINFOWTPART, 'A', '')), 'n/a') AS spPermitVolt,                                                                                               ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_57TYPEINFOWTPART), 'n/a') AS spWireRangeAWG,                                                                                             ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPWIRERAWGELEC' AND CODE = P.PTC_STR_58TYPEINFOWTPART) spWireRAWGElec,                ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_59TYPEINFOWTPART), 'n/a') AS spWireRangeMm,                                                                                              ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_60TYPEINFOWTPART), 'n/a') AS spTabThickness,                                                                                             ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_61TYPEINFOWTPART), 'n/a') AS spMaterThick,                                                                                               ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_62TYPEINFOWTPART), 'n/a') AS spBracketSizeD,                                                                                             ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_63TYPEINFOWTPART), 'n/a') AS spBracketSizeH,                                                                                             ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_64TYPEINFOWTPART), 'n/a') AS spBracketSizeT,                                                                                             ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECRELAYCAPACITY' AND CODE = P.PTC_STR_65TYPEINFOWTPART) spRelayCapa,                ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECELECTRICRESISTANCEVALUE' AND CODE = P.PTC_STR_66TYPEINFOWTPART) spEResistVal,     ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECVOLTAGESENSECAPACITY' AND CODE = P.PTC_STR_67TYPEINFOWTPART) spVoltSensCapa,      ");
	sb.append("\n             '' AS spManufPartNo,                                                                                              ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_69TYPEINFOWTPART), 'n/a') AS spValueValue,                                                                                               ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECTOLERANCE' AND CODE = P.PTC_STR_69TYPEINFOWTPART) spTolerance,                    ");
	sb.append("\n             '' AS spManufacturer,                                                                                             ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_72TYPEINFOWTPART), 'n/a') AS spRatedVoltage,                                                                                             ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_73TYPEINFOWTPART), 'n/a') AS spWatt,                                                                                                     ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_74TYPEINFOWTPART), 'n/a') AS spOperTemp,                                                                                                 ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_156TYPEINFOWTPART), 'n/a') AS spStoreTemp,                                                                                               ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECPACKAGETYPE' AND CODE = P.PTC_STR_75TYPEINFOWTPART) spPackageType,                ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECMSL' AND CODE = P.PTC_STR_76TYPEINFOWTPART) spMSL,                                ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECESD' AND CODE = P.PTC_STR_77TYPEINFOWTPART) spESD,                                ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPAECQ' AND CODE = P.PTC_STR_78TYPEINFOWTPART) spAECQ,                                ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_79TYPEINFOWTPART), 'n/a') AS spCharact1,                                                                                                 ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_80TYPEINFOWTPART), 'n/a') AS spCharact2,                                                                                                 ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_81TYPEINFOWTPART), 'n/a') AS spHoleSize,                                                                                                 ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECWIRERANGE' AND CODE = P.PTC_STR_82TYPEINFOWTPART) spWireRangeSQ,                  ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECWATTW' AND CODE = P.PTC_STR_83TYPEINFOWTPART) spWATTW,                            ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_84TYPEINFOWTPART), 'n/a') AS spSizemm,                                                                                                   ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_85TYPEINFOWTPART), 'n/a') AS spPermitTempC,                                                                                              ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECRESIN' AND CODE = P.PTC_STR_86TYPEINFOWTPART) spIsResin,                          ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECTHICKNESS' AND CODE = P.PTC_STR_87TYPEINFOWTPART) spThickness,                    ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECHOLESHAPE' AND CODE = P.PTC_STR_88TYPEINFOWTPART) spHoleShape,                    ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_89TYPEINFOWTPART), 'n/a') AS spCapaEResist,                                                                                              ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECCLOTHESMATERIAL' AND CODE = P.PTC_STR_90TYPEINFOWTPART) spClothesMater,           ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECISSHIELD' AND CODE = P.PTC_STR_91TYPEINFOWTPART) spIsShield,                      ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECESIRAPPROVAL' AND CODE = P.PTC_STR_92TYPEINFOWTPART) spESIRApproval,              ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECISIRAPPROVAL' AND CODE = P.PTC_STR_93TYPEINFOWTPART) spISIRApproval,              ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_94TYPEINFOWTPART), 'n/a') AS spConAppNoESIR,                                                                                             ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECLAYER' AND CODE = P.PTC_STR_95TYPEINFOWTPART) spLayer,                            ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_96TYPEINFOWTPART), 'n/a') AS spOrgDskPartNo,                                                                                             ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECPCBMATERIAL' AND CODE = P.PTC_STR_97TYPEINFOWTPART) spPCBMaterial,                ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECTG' AND CODE = P.PTC_STR_98TYPEINFOWTPART) spTg,                                  ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_79TYPEINFOWTPART), 'n/a') AS spCharact,                                                                                                  ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECSURFACETREATMENT' AND CODE = P.PTC_STR_100TYPEINFOWTPART) spSurfaceTreat,         ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECAPPEARANCEPROCESS' AND CODE = P.PTC_STR_101TYPEINFOWTPART) spAppearanProc,        ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECSOLDERRESISTORCOLOR' AND CODE = P.PTC_STR_102TYPEINFOWTPART) spSoldResColor,      ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECINSTALLPOSITION' AND CODE = P.PTC_STR_103TYPEINFOWTPART) spInstallPos,            ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_104TYPEINFOWTPART), 'n/a') AS spSwVersion,                                                                                               ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECUNITUNIT' AND CODE = P.PTC_STR_105TYPEINFOWTPART) spUnitUnit,                     ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECRESUNIT' AND CODE = P.PTC_STR_106TYPEINFOWTPART) spResUnit,                       ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECINDUNIT' AND CODE = P.PTC_STR_107TYPEINFOWTPART) spINDUnit,                       ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECCAPUNIT' AND CODE = P.PTC_STR_108TYPEINFOWTPART) spCapUnit,                       ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECRLCPACKAGETYPE' AND CODE = P.PTC_STR_109TYPEINFOWTPART) spRLCPackType,            ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECRLCTOLERANCE' AND CODE = P.PTC_STR_110TYPEINFOWTPART) spRLCTolerance,             ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPFESTPREVENT' AND CODE = P.PTC_STR_111TYPEINFOWTPART) spFestPrevent,                 ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPCONNCOMBDIR' AND CODE = P.PTC_STR_112TYPEINFOWTPART) spConnCombDir,                 ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECCERTIFIED' AND CODE = P.PTC_STR_113TYPEINFOWTPART) spCertified,                   ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPPACKAGESPEC' AND CODE = P.PTC_STR_114TYPEINFOWTPART) spPackageSpec,                 ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_115TYPEINFOWTPART), 'n/a') AS spTabSize,                                                                                                 ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECSEALTYPE' AND CODE = P.PTC_STR_116TYPEINFOWTPART) spSealType,                     ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_117TYPEINFOWTPART), 'n/a') AS spStudSize,                                                                                                ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_118TYPEINFOWTPART), 'n/a') AS spPitch,                                                                                                   ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECSOLDERING' AND CODE = P.PTC_STR_119TYPEINFOWTPART) spSoldering,                   ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECCONSTRUCMETH' AND CODE = P.PTC_STR_120TYPEINFOWTPART) spConstrucMeth,             ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_121TYPEINFOWTPART), 'n/a') AS spOptimumTemp,                                                                                             ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_122TYPEINFOWTPART), 'n/a') AS spContactRes,                                                                                              ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_123TYPEINFOWTPART), 'n/a') AS spInsulatRes,                                                                                              ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_124TYPEINFOWTPART), 'n/a') AS spConntHeight,                                                                                             ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECCABLECONMETH' AND CODE = P.PTC_STR_125TYPEINFOWTPART) spCableConMeth,             ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECTERMINALTYPE' AND CODE = P.PTC_STR_126TYPEINFOWTPART) spTerminalType,             ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECTERMBARRELSZ' AND CODE = P.PTC_STR_127TYPEINFOWTPART) spTermBarrelSz,             ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECTERMPREZTYPE' AND CODE = P.PTC_STR_128TYPEINFOWTPART) spTermPrezType,             ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECGWITMATERAT' AND CODE = P.PTC_STR_129TYPEINFOWTPART) spGWITMaterAt,               ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECINTERFACE' AND CODE = P.PTC_STR_130TYPEINFOWTPART) spInterface,                   ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECPINNSHAPE' AND CODE = P.PTC_STR_131TYPEINFOWTPART) spPinNShape,                   ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECSUBSUPPLIY' AND CODE = P.PTC_STR_132TYPEINFOWTPART) spSUBSuppliy,                 ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECCODINGNCOLOR' AND CODE = P.PTC_STR_133TYPEINFOWTPART) spCodingNColor,             ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECPLANT' AND CODE = P.PTC_STR_134TYPEINFOWTPART) spPlant,                           ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECPURCHASEGROUP' AND CODE = P.PTC_STR_135TYPEINFOWTPART) spPuchaseGroup,            ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_136TYPEINFOWTPART), 'n/a') AS spDevManNm,                                                                                                ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_137TYPEINFOWTPART), 'n/a') AS spDesignManNm,                                                                                             ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_138TYPEINFOWTPART), 'n/a') AS spManufacManNm,                                                                                            ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECMSELFCONTRACTFLAG' AND CODE = P.PTC_STR_139TYPEINFOWTPART) spMContractSAt,        ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECMMOLDFLAG' AND CODE = P.PTC_STR_140TYPEINFOWTPART) spMMoldAt,                     ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECMMAKINGFLAG' AND CODE = P.PTC_STR_141TYPEINFOWTPART) spMMakingAt,                 ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECMPRODFLAG' AND CODE = P.PTC_STR_142TYPEINFOWTPART) spMProdAt,                     ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_143TYPEINFOWTPART), 'n/a') AS spDieWhere,                                                                                                ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_144TYPEINFOWTPART), 'n/a') AS spObjectiveCT,                                                                                             ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_145TYPEINFOWTPART), 'n/a') AS spCavityStd,                                                                                               ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPMOUNTWAYAPE' AND CODE = P.PTC_STR_146TYPEINFOWTPART) spMountWayApE,                 ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_149TYPEINFOWTPART), 'n/a') AS spThickWH,                                                                                                 ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_157TYPEINFOWTPART), 'n/a') AS spPropEtc,                                                                                                 ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_158TYPEINFOWTPART), 'n/a') AS spHighVoltageSensingLimit,                                                                                 ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_159TYPEINFOWTPART), 'n/a') AS spMovVoltage,                                                                                              ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_160TYPEINFOWTPART), 'n/a') AS spToleranceSensing,                                                                                        ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_161TYPEINFOWTPART), 'n/a') AS spEsirApprove,                                                                                             ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_162TYPEINFOWTPART), 'n/a') AS spMainChipset,                                                                                             ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_163TYPEINFOWTPART), 'n/a') AS spFrequency,                                                                                               ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_164TYPEINFOWTPART), 'n/a') AS spDcpower,                                                                                                 ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_165TYPEINFOWTPART), 'n/a') AS spInterfaceIt,                                                                                             ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_165TYPEINFOWTPART), 'n/a') AS spFeatures,                                                                                                ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_167TYPEINFOWTPART), 'n/a') AS spDevSpec,                                                                                                 ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECMFLAMELEVEL' AND CODE = P.PTC_STR_168TYPEINFOWTPART) spFlameLevel,                ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_169TYPEINFOWTPART), 'n/a') AS spERatedVoltage,                                                                                           ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_170TYPEINFOWTPART), 'n/a') AS spQCNo,                                                                                                    ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPECMACLCTNFLAG' AND CODE = P.PTC_STR_171TYPEINFOWTPART) spActuatorLctn,              ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_172TYPEINFOWTPART), 'n/a') AS spMaxFrequency,                                                                                            ");
	sb.append("\n             REPLACE(LOWER(PTC_STR_173TYPEINFOWTPART), 'n/a') AS spImpedance,                                                                                               ");
	sb.append("\n             (SELECT REPLACE(VALUE, 'N/A', '') AS NAME FROM NUMBERCODEVALUE WHERE lang='en' AND CODETYPE = 'SPCONNECTORSTYLE' AND CODE = P.PTC_STR_179TYPEINFOWTPART) spPartStyle,                 ");
	sb.append("\n             MAT.MATERIAL spAssemblyMaterial,                                                                                                      ");
	sb.append("\n             MIG.SPIMGFILE spImgFile,                                                                                                      ");
	sb.append("\n             MIG.SP2DPDFFILE sp2DpdfFile,                                                                                                      ");
	sb.append("\n             MIG.SP3DPDFFILE sp3DpdfFile,                                                                                                      ");
	sb.append("\n             MIG.SPSTEPFILE spStepFile,                                                                                                      ");
	sb.append("\n             MIG.SPIGSFILE spIgsFile,                                                                                                      ");
	sb.append("\n             MIG.SPINSTRUCTIONSFILE spInstructionsFile,                                                                                                      ");
	sb.append("\n             MIG.HPPARTNUMBER HPPARTNUMBER,                                                                                                      ");
	sb.append("\n             MIG.SPMASTERNAME ,MIG.SPMASTEREMAIL                                                                                                      ");
	sb.append("\n         FROM WTPARTMASTER PM,  WTPART P , PART_MIG MIG,KETPartClassLink CL, KETPartClassification C,                                              ");
	sb.append("\n           ( SELECT J.IDA2A2, MAX(BRANCHIDITERATIONINFO) BRANCHIDITERATIONINFO                                                                    ");
	sb.append("\n             FROM WTPART I, WTPARTMASTER J                                                                                                        ");
	sb.append("\n             WHERE I.IDA3MASTERREFERENCE = J.IDA2A2                                                                                               ");
	sb.append("\n                  AND I.LATESTITERATIONINFO = 1                                                                                                   ");
	sb.append("\n             GROUP BY J.IDA2A2                                                                                                                    ");
	sb.append("\n           ) MAXVERPART,                                                                                                                          ");
	sb.append("\n           (                                                                                                                                      ");
	sb.append("\n           SELECT PJTLINK.BRANCHIDA3B5, PJT.PJTNO, PJT.PJTNAME FROM E3PSProjectMaster PJT, KETPartProjectLink PJTLINK                             ");
	sb.append("\n           WHERE PJTLINK.IDA3A5 = PJT.IDA2A2                                                                                                      ");
	sb.append("\n           ) PJT ,                                                                                                                                ");
	sb.append("\n         (                                                                                                                                        ");
	sb.append("\n             SELECT WTPARTNUMBER,                                                                                                                 ");
	sb.append("\n                     LISTAGG(MATERIAL||'^'||MATERIAL_PROPERTIES, '|') WITHIN GROUP (ORDER BY MATERIAL||'^'||MATERIAL_PROPERTIES) AS MATERIAL      ");
	sb.append("\n               FROM KETMATERIAL_MIG                                                                                                               ");
	sb.append("\n               GROUP BY WTPARTNUMBER                                                                                                              ");
	sb.append("\n            ) MAT                                                                                                                                 ");
	sb.append("\n        WHERE PM.IDA2A2 = P.IDA3MASTERREFERENCE                                                                                                   ");
	sb.append("\n             AND CL.IDA3A5 = C.IDA2A2                                                                                                             ");
	sb.append("\n             AND PM.IDA2A2 = CL.IDA3B5                                                                                                            ");
	sb.append("\n             AND P.BRANCHIDITERATIONINFO = PJT.BRANCHIDA3B5(+)                                                                                    ");
	sb.append("\n             AND PM.WTPARTNUMBER = MIG.PARTNUMBER                                                                                                 ");
	sb.append("\n             AND PM.WTPARTNUMBER = MAT.WTPARTNUMBER(+)                                                                                            ");
	sb.append("\n             and P.LATESTITERATIONINFO = 1                                                                                                        ");
	sb.append("\n             AND P.BRANCHIDITERATIONINFO = MAXVERPART.BRANCHIDITERATIONINFO                                                                       ");
	sb.append("\n             and PM.ida2a2 = P.IDA3MASTERREFERENCE                                                                                                ");
	sb.append("\n             AND SUBSTR(PM.WTPARTNUMBER, 0, 1) != 'H' ");

	return sb.toString();
    }

    private HashMap<String, Object> getSendHpPartResultSet(ResultSet rs) throws SQLException {

	HashMap<String, Object> epmDocument = new HashMap<String, Object>();
	epmDocument.put("spMasterName", StringUtil.checkNull(rs.getString("SPMASTERNAME")));
	epmDocument.put("spMasterEmail", StringUtil.checkNull(rs.getString("SPMASTEREMAIL")));
	epmDocument.put("spCategoryCode", StringUtil.checkNull(rs.getString("spCategoryCode")));
	epmDocument.put("spClassification", StringUtil.checkNull(rs.getString("spClassification")));
	epmDocument.put("PartName", StringUtil.checkNull(rs.getString("PartName")));
	epmDocument.put("HpPartNumber", StringUtil.checkNull(rs.getString("HPPARTNUMBER")));
	epmDocument.put("PartNumber", StringUtil.checkNull(rs.getString("PartNumber")));
	epmDocument.put("spProjectNo", StringUtil.checkNull(rs.getString("spProjectNo")));
	epmDocument.put("spEoNo", StringUtil.checkNull(rs.getString("spEoNo")));
	epmDocument.put("spDivision", StringUtil.checkNull(rs.getString("spDivision")));
	epmDocument.put("spPartNameHis", StringUtil.checkNull(rs.getString("spPartNameHis")));
	epmDocument.put("spPartRevision", StringUtil.checkNull(rs.getString("spPartRevision")));
	epmDocument.put("spPartType", StringUtil.checkNull(rs.getString("spPartType")));
	epmDocument.put("spPartDevLevel", StringUtil.checkNull(rs.getString("spPartDevLevel")));
	epmDocument.put("spIsDeleted", StringUtil.checkNull(rs.getString("spIsDeleted")));
	epmDocument.put("spBOMFlag", StringUtil.checkNull(rs.getString("spBOMFlag")));
	epmDocument.put("spWeightUnit", StringUtil.checkNull(rs.getString("spWeightUnit")));
	epmDocument.put("spNetWeight", StringUtil.checkNull(rs.getString("spNetWeight")));
	epmDocument.put("spTotalWeight", StringUtil.checkNull(rs.getString("spTotalWeight")));
	epmDocument.put("spScrabWeight", StringUtil.checkNull(rs.getString("spScrabWeight")));
	epmDocument.put("spIsYazaki", StringUtil.checkNull(rs.getString("spIsYazaki")));
	epmDocument.put("spYazakiNo", StringUtil.checkNull(rs.getString("spYazakiNo")));
	epmDocument.put("spManufAt", StringUtil.checkNull(rs.getString("spManufAt")));
	epmDocument.put("spManufacPlace", StringUtil.checkNull(rs.getString("spManufacPlace")));
	epmDocument.put("spCustomRev", StringUtil.checkNull(rs.getString("spCustomRev")));
	epmDocument.put("spScrabCode", StringUtil.checkNull(rs.getString("spScrabCode")));
	epmDocument.put("spWaterProof", StringUtil.checkNull(rs.getString("spWaterProof")));
	epmDocument.put("spMaterialMark", StringUtil.checkNull(rs.getString("spMaterialMark")));
	epmDocument.put("spColor", StringUtil.checkNull(rs.getString("spColor")));
	epmDocument.put("spColorPurch", StringUtil.checkNull(rs.getString("spColorPurch")));
	epmDocument.put("spColorElec", StringUtil.checkNull(rs.getString("spColorElec")));
	epmDocument.put("spSeries", StringUtil.checkNull(rs.getString("spSeries")));
	epmDocument.put("spSeriesLAMP", StringUtil.checkNull(rs.getString("spSeriesLAMP")));
	epmDocument.put("spSeriesBulb", StringUtil.checkNull(rs.getString("spSeriesBulb")));
	epmDocument.put("spProdSize", StringUtil.checkNull(rs.getString("spProdSize")));
	epmDocument.put("spEnvFriend", StringUtil.checkNull(rs.getString("spEnvFriend")));
	epmDocument.put("spTGenConn", StringUtil.checkNull(rs.getString("spTGenConn")));
	epmDocument.put("spTPCBConn", StringUtil.checkNull(rs.getString("spTPCBConn")));
	epmDocument.put("spTLAMPConn", StringUtil.checkNull(rs.getString("spTLAMPConn")));
	epmDocument.put("spTBulbConn", StringUtil.checkNull(rs.getString("spTBulbConn")));
	epmDocument.put("spTHiVolt", StringUtil.checkNull(rs.getString("spTHiVolt")));
	epmDocument.put("spTCharger", StringUtil.checkNull(rs.getString("spTCharger")));
	epmDocument.put("spT2Charger", StringUtil.checkNull(rs.getString("spT2Charger")));
	epmDocument.put("spT1HiVoltFuz", StringUtil.checkNull(rs.getString("spT1HiVoltFuz")));
	epmDocument.put("spTHiVoltFuse", StringUtil.checkNull(rs.getString("spTHiVoltFuse")));
	epmDocument.put("spTJnB", StringUtil.checkNull(rs.getString("spTJnB")));
	epmDocument.put("spTPRA", StringUtil.checkNull(rs.getString("spTPRA")));
	epmDocument.put("spTGNSS", StringUtil.checkNull(rs.getString("spTGNSS")));
	epmDocument.put("spTComModule", StringUtil.checkNull(rs.getString("spTComModule")));
	epmDocument.put("spTMultiMedUnit", StringUtil.checkNull(rs.getString("spTMultiMedUnit")));
	epmDocument.put("spLanceType", StringUtil.checkNull(rs.getString("spLanceType")));
	epmDocument.put("spNoOfPole", StringUtil.checkNull(rs.getString("spNoOfPole")));
	// 매칭터미널
	if (StringUtil.checkNull(rs.getString("spMTerminal")).trim().length() > 0) {
	    String matchingArry[] = StringUtil.checkNull(rs.getString("spMTerminal")).trim().split(",");
	    String matchingString = "";
	    for (int i = 0; i < StringUtil.checkNull(rs.getString("spMTerminal")).trim().split(",").length; i++) {
		if (matchingArry[i].trim().length() > 0) {
		    if (i == 0) {
			matchingString = this.checkDocument(matchingArry[i].trim());
		    } else {
			matchingString = matchingString + ", " + this.checkDocument(matchingArry[i].trim());
		    }
		}
	    }
	    epmDocument.put("spMTerminal", matchingString);
	} else {
	    epmDocument.put("spMTerminal", "");
	}
	// 매칭커넥터
	if (StringUtil.checkNull(rs.getString("spMConnector")).trim().length() > 0) {
	    String matchingArry[] = StringUtil.checkNull(rs.getString("spMConnector")).trim().split(",");
	    String matchingString = "";
	    for (int i = 0; i < StringUtil.checkNull(rs.getString("spMConnector")).trim().split(",").length; i++) {
		if (matchingArry[i].trim().length() > 0) {
		    if (i == 0) {
			matchingString = this.checkDocument(matchingArry[i].trim());
		    } else {
			matchingString = matchingString + ", " + this.checkDocument(matchingArry[i].trim());
		    }
		}
	    }
	    epmDocument.put("spMConnector", matchingString);
	} else {
	    epmDocument.put("spMConnector", "");
	}
	// 매칭벌브
	if (StringUtil.checkNull(rs.getString("spMatchBulb")).trim().length() > 0) {
	    String matchingArry[] = StringUtil.checkNull(rs.getString("spMatchBulb")).trim().split(",");
	    String matchingString = "";
	    for (int i = 0; i < StringUtil.checkNull(rs.getString("spMatchBulb")).trim().split(",").length; i++) {
		if (matchingArry[i].trim().length() > 0) {
		    if (i == 0) {
			matchingString = this.checkDocument(matchingArry[i].trim());
		    } else {
			matchingString = matchingString + ", " + this.checkDocument(matchingArry[i].trim());
		    }
		}
	    }
	    epmDocument.put("spMatchBulb", matchingString);
	} else {
	    epmDocument.put("spMatchBulb", "");
	}
	// 매칭클립
	if (StringUtil.checkNull(rs.getString("spMClip")).trim().length() > 0) {
	    String matchingArry[] = StringUtil.checkNull(rs.getString("spMClip")).trim().split(",");
	    String matchingString = "";
	    for (int i = 0; i < StringUtil.checkNull(rs.getString("spMClip")).trim().split(",").length; i++) {
		if (matchingArry[i].trim().length() > 0) {
		    if (i == 0) {
			matchingString = this.checkDocument(matchingArry[i].trim());
		    } else {
			matchingString = matchingString + ", " + this.checkDocument(matchingArry[i].trim());
		    }
		}
	    }
	    epmDocument.put("spMClip", matchingString);
	} else {
	    epmDocument.put("spMClip", "");
	}
	// 매칭리어홀더
	if (StringUtil.checkNull(rs.getString("spMRH")).trim().length() > 0) {
	    String matchingArry[] = StringUtil.checkNull(rs.getString("spMRH")).trim().split(",");
	    String matchingString = "";
	    for (int i = 0; i < StringUtil.checkNull(rs.getString("spMRH")).trim().split(",").length; i++) {
		if (matchingArry[i].trim().length() > 0) {
		    if (i == 0) {
			matchingString = this.checkDocument(matchingArry[i].trim());
		    } else {
			matchingString = matchingString + ", " + this.checkDocument(matchingArry[i].trim());
		    }
		}
	    }
	    epmDocument.put("spMRH", matchingString);
	} else {
	    epmDocument.put("spMRH", "");
	}
	// 매칭커버
	if (StringUtil.checkNull(rs.getString("spMCover")).trim().length() > 0) {
	    String matchingArry[] = StringUtil.checkNull(rs.getString("spMCover")).trim().split(",");
	    String matchingString = "";
	    for (int i = 0; i < StringUtil.checkNull(rs.getString("spMCover")).trim().split(",").length; i++) {
		if (matchingArry[i].trim().length() > 0) {
		    if (i == 0) {
			matchingString = this.checkDocument(matchingArry[i].trim());
		    } else {
			matchingString = matchingString + ", " + this.checkDocument(matchingArry[i].trim());
		    }
		}
	    }
	    epmDocument.put("spMCover", matchingString);
	} else {
	    epmDocument.put("spMCover", "");
	}
	// 매칭와이어씰
	if (StringUtil.checkNull(rs.getString("spMWireSeal")).trim().length() > 0) {
	    String matchingArry[] = StringUtil.checkNull(rs.getString("spMWireSeal")).trim().split(",");
	    String matchingString = "";
	    for (int i = 0; i < StringUtil.checkNull(rs.getString("spMWireSeal")).trim().split(",").length; i++) {
		if (matchingArry[i].trim().length() > 0) {
		    if (i == 0) {
			matchingString = this.checkDocument(matchingArry[i].trim());
		    } else {
			matchingString = matchingString + ", " + this.checkDocument(matchingArry[i].trim());
		    }
		}
	    }
	    epmDocument.put("spMWireSeal", matchingString);
	} else {
	    epmDocument.put("spMWireSeal", "");
	}
	epmDocument.put("spLockingType", StringUtil.checkNull(rs.getString("spLockingType")));
	epmDocument.put("spLockTTML", StringUtil.checkNull(rs.getString("spLockTTML")));
	epmDocument.put("spLockTConn", StringUtil.checkNull(rs.getString("spLockTConn")));
	epmDocument.put("spRepresentM", StringUtil.checkNull(rs.getString("spRepresentM")));
	epmDocument.put("spMaterMaker", StringUtil.checkNull(rs.getString("spMaterMaker")));
	epmDocument.put("spMaterType", StringUtil.checkNull(rs.getString("spMaterType")));
	epmDocument.put("spMaterialInfo", StringUtil.checkNull(rs.getString("spMaterialInfo")));
	epmDocument.put("spMaterNotFe", StringUtil.checkNull(rs.getString("spMaterNotFe")));
	epmDocument.put("spMaterDie", StringUtil.checkNull(rs.getString("spMaterDie")));
	epmDocument.put("spMaterPurch", StringUtil.checkNull(rs.getString("spMaterPurch")));
	epmDocument.put("spPlating", StringUtil.checkNull(rs.getString("spPlating")));
	epmDocument.put("spPlatingPurch", StringUtil.checkNull(rs.getString("spPlatingPurch")));
	epmDocument.put("spCustomPartNo", StringUtil.checkNull(rs.getString("spCustomPartNo")));
	epmDocument.put("spPermitVolt", StringUtil.checkNull(rs.getString("spPermitVolt")));
	epmDocument.put("spWireRangeAWG", StringUtil.checkNull(rs.getString("spWireRangeAWG")));
	epmDocument.put("spWireRAWGElec", StringUtil.checkNull(rs.getString("spWireRAWGElec")));
	epmDocument.put("spWireRangeMm", StringUtil.checkNull(rs.getString("spWireRangeMm")));
	epmDocument.put("spTabThickness", StringUtil.checkNull(rs.getString("spTabThickness")));
	epmDocument.put("spMaterThick", StringUtil.checkNull(rs.getString("spMaterThick")));
	epmDocument.put("spBracketSizeD", StringUtil.checkNull(rs.getString("spBracketSizeD")));
	epmDocument.put("spBracketSizeH", StringUtil.checkNull(rs.getString("spBracketSizeH")));
	epmDocument.put("spBracketSizeT", StringUtil.checkNull(rs.getString("spBracketSizeT")));
	epmDocument.put("spRelayCapa", StringUtil.checkNull(rs.getString("spRelayCapa")));
	epmDocument.put("spEResistVal", StringUtil.checkNull(rs.getString("spEResistVal")));
	epmDocument.put("spVoltSensCapa", StringUtil.checkNull(rs.getString("spVoltSensCapa")));
	epmDocument.put("spManufPartNo", StringUtil.checkNull(rs.getString("spManufPartNo")));
	epmDocument.put("spValueValue", StringUtil.checkNull(rs.getString("spValueValue")));
	epmDocument.put("spTolerance", StringUtil.checkNull(rs.getString("spTolerance")));
	epmDocument.put("spManufacturer", StringUtil.checkNull(rs.getString("spManufacturer")));
	epmDocument.put("spRatedVoltage", StringUtil.checkNull(rs.getString("spRatedVoltage")));
	epmDocument.put("spWatt", StringUtil.checkNull(rs.getString("spWatt")));
	epmDocument.put("spOperTemp", StringUtil.checkNull(rs.getString("spOperTemp")));
	epmDocument.put("spStoreTemp", StringUtil.checkNull(rs.getString("spStoreTemp")));
	epmDocument.put("spPackageType", StringUtil.checkNull(rs.getString("spPackageType")));
	epmDocument.put("spMSL", StringUtil.checkNull(rs.getString("spMSL")));
	epmDocument.put("spESD", StringUtil.checkNull(rs.getString("spESD")));
	epmDocument.put("spAECQ", StringUtil.checkNull(rs.getString("spAECQ")));
	epmDocument.put("spCharact1", StringUtil.checkNull(rs.getString("spCharact1")));
	epmDocument.put("spCharact2", StringUtil.checkNull(rs.getString("spCharact2")));
	epmDocument.put("spHoleSize", StringUtil.checkNull(rs.getString("spHoleSize")));
	epmDocument.put("spWireRangeSQ", StringUtil.checkNull(rs.getString("spWireRangeSQ")));
	epmDocument.put("spWATTW", StringUtil.checkNull(rs.getString("spWATTW")));
	epmDocument.put("spSizemm", StringUtil.checkNull(rs.getString("spSizemm")));
	epmDocument.put("spPermitTempC", StringUtil.checkNull(rs.getString("spPermitTempC")));
	epmDocument.put("spIsResin", StringUtil.checkNull(rs.getString("spIsResin")));
	epmDocument.put("spThickness", StringUtil.checkNull(rs.getString("spThickness")));
	epmDocument.put("spHoleShape", StringUtil.checkNull(rs.getString("spHoleShape")));
	epmDocument.put("spCapaEResist", StringUtil.checkNull(rs.getString("spCapaEResist")));
	epmDocument.put("spClothesMater", StringUtil.checkNull(rs.getString("spClothesMater")));
	epmDocument.put("spIsShield", StringUtil.checkNull(rs.getString("spIsShield")));
	epmDocument.put("spESIRApproval", StringUtil.checkNull(rs.getString("spESIRApproval")));
	epmDocument.put("spISIRApproval", StringUtil.checkNull(rs.getString("spISIRApproval")));
	epmDocument.put("spConAppNoESIR", StringUtil.checkNull(rs.getString("spConAppNoESIR")));
	epmDocument.put("spLayer", StringUtil.checkNull(rs.getString("spLayer")));
	epmDocument.put("spOrgDskPartNo", StringUtil.checkNull(rs.getString("spOrgDskPartNo")));
	epmDocument.put("spPCBMaterial", StringUtil.checkNull(rs.getString("spPCBMaterial")));
	epmDocument.put("spTg", StringUtil.checkNull(rs.getString("spTg")));
	epmDocument.put("spCharact", StringUtil.checkNull(rs.getString("spCharact")));
	epmDocument.put("spSurfaceTreat", StringUtil.checkNull(rs.getString("spSurfaceTreat")));
	epmDocument.put("spAppearanProc", StringUtil.checkNull(rs.getString("spAppearanProc")));
	epmDocument.put("spSoldResColor", StringUtil.checkNull(rs.getString("spSoldResColor")));
	epmDocument.put("spInstallPos", StringUtil.checkNull(rs.getString("spInstallPos")));
	epmDocument.put("spSwVersion", StringUtil.checkNull(rs.getString("spSwVersion")));
	epmDocument.put("spUnitUnit", StringUtil.checkNull(rs.getString("spUnitUnit")));
	epmDocument.put("spResUnit", StringUtil.checkNull(rs.getString("spResUnit")));
	epmDocument.put("spINDUnit", StringUtil.checkNull(rs.getString("spINDUnit")));
	epmDocument.put("spCapUnit", StringUtil.checkNull(rs.getString("spCapUnit")));
	epmDocument.put("spRLCPackType", StringUtil.checkNull(rs.getString("spRLCPackType")));
	epmDocument.put("spRLCTolerance", StringUtil.checkNull(rs.getString("spRLCTolerance")));
	epmDocument.put("spFestPrevent", StringUtil.checkNull(rs.getString("spFestPrevent")));
	epmDocument.put("spConnCombDir", StringUtil.checkNull(rs.getString("spConnCombDir")));
	epmDocument.put("spCertified", StringUtil.checkNull(rs.getString("spCertified")));
	epmDocument.put("spPackageSpec", StringUtil.checkNull(rs.getString("spPackageSpec")));
	epmDocument.put("spTabSize", StringUtil.checkNull(rs.getString("spTabSize")));
	epmDocument.put("spSealType", StringUtil.checkNull(rs.getString("spSealType")));
	epmDocument.put("spStudSize", StringUtil.checkNull(rs.getString("spStudSize")));
	epmDocument.put("spPitch", StringUtil.checkNull(rs.getString("spPitch")));
	epmDocument.put("spSoldering", StringUtil.checkNull(rs.getString("spSoldering")));
	epmDocument.put("spConstrucMeth", StringUtil.checkNull(rs.getString("spConstrucMeth")));
	epmDocument.put("spOptimumTemp", StringUtil.checkNull(rs.getString("spOptimumTemp")));
	epmDocument.put("spContactRes", StringUtil.checkNull(rs.getString("spContactRes")));
	epmDocument.put("spInsulatRes", StringUtil.checkNull(rs.getString("spInsulatRes")));
	epmDocument.put("spConntHeight", StringUtil.checkNull(rs.getString("spConntHeight")));
	epmDocument.put("spCableConMeth", StringUtil.checkNull(rs.getString("spCableConMeth")));
	epmDocument.put("spTerminalType", StringUtil.checkNull(rs.getString("spTerminalType")));
	epmDocument.put("spTermBarrelSz", StringUtil.checkNull(rs.getString("spTermBarrelSz")));
	epmDocument.put("spTermPrezType", StringUtil.checkNull(rs.getString("spTermPrezType")));
	epmDocument.put("spGWITMaterAt", StringUtil.checkNull(rs.getString("spGWITMaterAt")));
	epmDocument.put("spInterface", StringUtil.checkNull(rs.getString("spInterface")));
	epmDocument.put("spPinNShape", StringUtil.checkNull(rs.getString("spPinNShape")));
	epmDocument.put("spSUBSuppliy", StringUtil.checkNull(rs.getString("spSUBSuppliy")));
	epmDocument.put("spCodingNColor", StringUtil.checkNull(rs.getString("spCodingNColor")));
	epmDocument.put("spPlant", StringUtil.checkNull(rs.getString("spPlant")));
	epmDocument.put("spPuchaseGroup", StringUtil.checkNull(rs.getString("spPuchaseGroup")));
	epmDocument.put("spDevManNm", StringUtil.checkNull(rs.getString("spDevManNm")));
	epmDocument.put("spDesignManNm", StringUtil.checkNull(rs.getString("spDesignManNm")));
	epmDocument.put("spManufacManNm", StringUtil.checkNull(rs.getString("spManufacManNm")));
	epmDocument.put("spMContractSAt", StringUtil.checkNull(rs.getString("spMContractSAt")));
	epmDocument.put("spMMoldAt", StringUtil.checkNull(rs.getString("spMMoldAt")));
	epmDocument.put("spMMakingAt", StringUtil.checkNull(rs.getString("spMMakingAt")));
	epmDocument.put("spMProdAt", StringUtil.checkNull(rs.getString("spMProdAt")));
	epmDocument.put("spDieWhere", StringUtil.checkNull(rs.getString("spDieWhere")));
	epmDocument.put("spObjectiveCT", StringUtil.checkNull(rs.getString("spObjectiveCT")));
	epmDocument.put("spCavityStd", StringUtil.checkNull(rs.getString("spCavityStd")));
	epmDocument.put("spMountWayApE", StringUtil.checkNull(rs.getString("spMountWayApE")));
	epmDocument.put("spThickWH", StringUtil.checkNull(rs.getString("spThickWH")));
	epmDocument.put("spPropEtc", StringUtil.checkNull(rs.getString("spPropEtc")));
	epmDocument.put("spHighVoltageSensingLimit", StringUtil.checkNull(rs.getString("spHighVoltageSensingLimit")));
	epmDocument.put("spMovVoltage", StringUtil.checkNull(rs.getString("spMovVoltage")));
	epmDocument.put("spToleranceSensing", StringUtil.checkNull(rs.getString("spToleranceSensing")));
	epmDocument.put("spEsirApprove", StringUtil.checkNull(rs.getString("spEsirApprove")));
	epmDocument.put("spMainChipset", StringUtil.checkNull(rs.getString("spMainChipset")));
	epmDocument.put("spFrequency", StringUtil.checkNull(rs.getString("spFrequency")));
	epmDocument.put("spDcpower", StringUtil.checkNull(rs.getString("spDcpower")));
	epmDocument.put("spInterfaceIt", StringUtil.checkNull(rs.getString("spInterfaceIt")));
	epmDocument.put("spFeatures", StringUtil.checkNull(rs.getString("spFeatures")));
	epmDocument.put("spDevSpec", StringUtil.checkNull(rs.getString("spDevSpec")));
	epmDocument.put("spFlameLevel", StringUtil.checkNull(rs.getString("spFlameLevel")));
	epmDocument.put("spERatedVoltage", StringUtil.checkNull(rs.getString("spERatedVoltage")));
	epmDocument.put("spQCNo", StringUtil.checkNull(rs.getString("spQCNo")));
	epmDocument.put("spActuatorLctn", StringUtil.checkNull(rs.getString("spActuatorLctn")));
	epmDocument.put("spMaxFrequency", StringUtil.checkNull(rs.getString("spMaxFrequency")));
	epmDocument.put("spImpedance", StringUtil.checkNull(rs.getString("spImpedance")));
	epmDocument.put("spAssemblyMaterial", StringUtil.checkNull(rs.getString("spAssemblyMaterial")));
	epmDocument.put("spDescription", "");
	epmDocument.put("spPartStyle", StringUtil.checkNull(rs.getString("spPartStyle")));
	/*
	 * String sPartStyle = StringUtil.checkNull(rs.getString("PartNumber")).substring(0, 2); if ("61".equals(sPartStyle))
	 * epmDocument.put("spPartStyle", "Female"); else if ("62".equals(sPartStyle)) epmDocument.put("spPartStyle", "Male"); else if
	 * ("73".equals(sPartStyle)) epmDocument.put("spPartStyle", "Female"); else if ("74".equals(sPartStyle))
	 * epmDocument.put("spPartStyle", "Male"); else epmDocument.put("spPartStyle", "");
	 */
	epmDocument.put("spImgFile", StringUtil.checkNull(rs.getString("spImgFile")));
	epmDocument.put("sp2DpdfFile", StringUtil.checkNull(rs.getString("sp2DpdfFile")));
	epmDocument.put("sp3DpdfFile", StringUtil.checkNull(rs.getString("sp3DpdfFile")));
	epmDocument.put("spStepFile", StringUtil.checkNull(rs.getString("spStepFile")));
	epmDocument.put("spIgsFile", StringUtil.checkNull(rs.getString("spIgsFile")));
	epmDocument.put("spInstructionsFile", StringUtil.checkNull(rs.getString("spInstructionsFile")));

	return epmDocument;

    }
}
