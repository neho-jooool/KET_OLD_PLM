package ext.ket.project.gate.entity;

import e3ps.common.code.NumberCode;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.cost.util.StringUtil;
import ext.ket.shared.dto.BaseDTO;

public class TemplateGateCheckSheetDTO extends BaseDTO {
    private String pjtOid = "";
    private String targetType = "";
    private String checkSheetName = "";
    private String achieveBase = "";
    private String unit = "";
    private String criteria = "";
    private int orderNo = 0;
    private String createStamp;
    private String modifyStamp;
    private String gateName1;
    private String gateName2;
    private String gateName3;
    private String gateName4;
    private String gateName5;
    private String gateName6;
    private String gateName7;
    private String gateName8;
    private String gateName9;
    private String gateName10;
    private String gateName11;
    private String gateName12;
    private String gateName13;
    private String gateName14;
    private String gateName15;
    private String gateName16;
    private String gateName17;
    private String gateName18;
    private String gateName19;
    private String gateName20;
    private String select1;
    private String select2;
    private String select3;
    private String select4;
    private String select5;
    private String select6;
    private String select7;
    private String select8;
    private String select9;
    private String select10;
    private String select11;
    private String select12;
    private String select13;
    private String select14;
    private String select15;
    private String select16;
    private String select17;
    private String select18;
    private String select19;
    private String select20;
    private String target1;
    private String target2;
    private String target3;
    private String target4;
    private String target5;
    private String target6;
    private String target7;
    private String target8;
    private String target9;
    private String target10;
    private String target11;
    private String target12;
    private String target13;
    private String target14;
    private String target15;
    private String target16;
    private String target17;
    private String target18;
    private String target19;
    private String target20;
    private String result1;
    private String result2;
    private String result3;
    private String result4;
    private String result5;
    private String result6;
    private String result7;
    private String result8;
    private String result9;
    private String result10;
    private String result11;
    private String result12;
    private String result13;
    private String result14;
    private String result15;
    private String result16;
    private String result17;
    private String result18;
    private String result19;
    private String result20;

    public TemplateGateCheckSheetDTO() {
    }

    public TemplateGateCheckSheetDTO(TemplateGateCheckSheet chk) {
	this.setOid(CommonUtil.getOIDString(chk));
	NumberCode tgTpCode = chk.getTargetType();
	if (tgTpCode != null)
	    this.targetType = tgTpCode.getCode();
	this.checkSheetName = StringUtil.checkNull(chk.getCheckSheetName());
	this.achieveBase = StringUtil.checkNull(chk.getAchieveBase());
	this.unit = StringUtil.checkNull(chk.getUnit());
	this.criteria = StringUtil.checkNull(chk.getCriteria());
	this.orderNo = chk.getOrderNo();
	this.gateName1 = StringUtil.checkNull(chk.getAttr().getGateName1());
	this.gateName2 = StringUtil.checkNull(chk.getAttr().getGateName2());
	this.gateName3 = StringUtil.checkNull(chk.getAttr().getGateName3());
	this.gateName4 = StringUtil.checkNull(chk.getAttr().getGateName4());
	this.gateName5 = StringUtil.checkNull(chk.getAttr().getGateName5());
	this.gateName6 = StringUtil.checkNull(chk.getAttr().getGateName6());
	this.gateName7 = StringUtil.checkNull(chk.getAttr().getGateName7());
	this.gateName8 = StringUtil.checkNull(chk.getAttr().getGateName8());
	this.gateName9 = StringUtil.checkNull(chk.getAttr().getGateName9());
	this.gateName10 = StringUtil.checkNull(chk.getAttr().getGateName10());
	this.gateName11 = StringUtil.checkNull(chk.getAttr().getGateName11());
	this.gateName12 = StringUtil.checkNull(chk.getAttr().getGateName12());
	this.gateName13 = StringUtil.checkNull(chk.getAttr().getGateName13());
	this.gateName14 = StringUtil.checkNull(chk.getAttr().getGateName14());
	this.gateName15 = StringUtil.checkNull(chk.getAttr().getGateName15());
	this.gateName16 = StringUtil.checkNull(chk.getAttr().getGateName16());
	this.gateName17 = StringUtil.checkNull(chk.getAttr().getGateName17());
	this.gateName18 = StringUtil.checkNull(chk.getAttr().getGateName18());
	this.gateName19 = StringUtil.checkNull(chk.getAttr().getGateName19());
	this.gateName20 = StringUtil.checkNull(chk.getAttr().getGateName20());
	this.target1 = StringUtil.checkNull(chk.getAttr().getTarget1());
	this.target2 = StringUtil.checkNull(chk.getAttr().getTarget2());
	this.target3 = StringUtil.checkNull(chk.getAttr().getTarget3());
	this.target4 = StringUtil.checkNull(chk.getAttr().getTarget4());
	this.target5 = StringUtil.checkNull(chk.getAttr().getTarget5());
	this.target6 = StringUtil.checkNull(chk.getAttr().getTarget6());
	this.target7 = StringUtil.checkNull(chk.getAttr().getTarget7());
	this.target8 = StringUtil.checkNull(chk.getAttr().getTarget8());
	this.target9 = StringUtil.checkNull(chk.getAttr().getTarget9());
	this.target10 = StringUtil.checkNull(chk.getAttr().getTarget10());
	this.target11 = StringUtil.checkNull(chk.getAttr().getTarget11());
	this.target12 = StringUtil.checkNull(chk.getAttr().getTarget12());
	this.target13 = StringUtil.checkNull(chk.getAttr().getTarget13());
	this.target14 = StringUtil.checkNull(chk.getAttr().getTarget14());
	this.target15 = StringUtil.checkNull(chk.getAttr().getTarget15());
	this.target16 = StringUtil.checkNull(chk.getAttr().getTarget16());
	this.target17 = StringUtil.checkNull(chk.getAttr().getTarget17());
	this.target18 = StringUtil.checkNull(chk.getAttr().getTarget18());
	this.target19 = StringUtil.checkNull(chk.getAttr().getTarget19());
	this.target20 = StringUtil.checkNull(chk.getAttr().getTarget20());
	this.result1 = StringUtil.checkNull(chk.getAttr().getResult1());
	this.result2 = StringUtil.checkNull(chk.getAttr().getResult2());
	this.result3 = StringUtil.checkNull(chk.getAttr().getResult3());
	this.result4 = StringUtil.checkNull(chk.getAttr().getResult4());
	this.result5 = StringUtil.checkNull(chk.getAttr().getResult5());
	this.result6 = StringUtil.checkNull(chk.getAttr().getResult6());
	this.result7 = StringUtil.checkNull(chk.getAttr().getResult7());
	this.result8 = StringUtil.checkNull(chk.getAttr().getResult8());
	this.result9 = StringUtil.checkNull(chk.getAttr().getResult9());
	this.result10 = StringUtil.checkNull(chk.getAttr().getResult10());
	this.result11 = StringUtil.checkNull(chk.getAttr().getResult11());
	this.result12 = StringUtil.checkNull(chk.getAttr().getResult12());
	this.result13 = StringUtil.checkNull(chk.getAttr().getResult13());
	this.result14 = StringUtil.checkNull(chk.getAttr().getResult14());
	this.result15 = StringUtil.checkNull(chk.getAttr().getResult15());
	this.result16 = StringUtil.checkNull(chk.getAttr().getResult16());
	this.result17 = StringUtil.checkNull(chk.getAttr().getResult17());
	this.result18 = StringUtil.checkNull(chk.getAttr().getResult18());
	this.result19 = StringUtil.checkNull(chk.getAttr().getResult19());
	this.result20 = StringUtil.checkNull(chk.getAttr().getResult20());
	this.select1 = (chk.getAttr().isSelect1()) ? "1" : "0";
	this.select2 = (chk.getAttr().isSelect2()) ? "1" : "0";
	this.select3 = (chk.getAttr().isSelect3()) ? "1" : "0";
	this.select4 = (chk.getAttr().isSelect4()) ? "1" : "0";
	this.select5 = (chk.getAttr().isSelect5()) ? "1" : "0";
	this.select6 = (chk.getAttr().isSelect6()) ? "1" : "0";
	this.select7 = (chk.getAttr().isSelect7()) ? "1" : "0";
	this.select8 = (chk.getAttr().isSelect8()) ? "1" : "0";
	this.select9 = (chk.getAttr().isSelect9()) ? "1" : "0";
	this.select10 = (chk.getAttr().isSelect10()) ? "1" : "0";
	this.select11 = (chk.getAttr().isSelect11()) ? "1" : "0";
	this.select12 = (chk.getAttr().isSelect12()) ? "1" : "0";
	this.select13 = (chk.getAttr().isSelect13()) ? "1" : "0";
	this.select14 = (chk.getAttr().isSelect14()) ? "1" : "0";
	this.select15 = (chk.getAttr().isSelect15()) ? "1" : "0";
	this.select16 = (chk.getAttr().isSelect16()) ? "1" : "0";
	this.select17 = (chk.getAttr().isSelect17()) ? "1" : "0";
	this.select18 = (chk.getAttr().isSelect18()) ? "1" : "0";
	this.select19 = (chk.getAttr().isSelect19()) ? "1" : "0";
	this.select20 = (chk.getAttr().isSelect20()) ? "1" : "0";
	this.createStamp = DateUtil.getDateString(chk.getPersistInfo().getCreateStamp(), "date");
	this.modifyStamp = DateUtil.getDateString(chk.getPersistInfo().getModifyStamp(), "date");
    }

    public String getGateName11() {
	return gateName11;
    }

    public void setGateName11(String gateName11) {
	this.gateName11 = gateName11;
    }

    public String getGateName12() {
	return gateName12;
    }

    public void setGateName12(String gateName12) {
	this.gateName12 = gateName12;
    }

    public String getGateName13() {
	return gateName13;
    }

    public void setGateName13(String gateName13) {
	this.gateName13 = gateName13;
    }

    public String getGateName14() {
	return gateName14;
    }

    public void setGateName14(String gateName14) {
	this.gateName14 = gateName14;
    }

    public String getGateName15() {
	return gateName15;
    }

    public void setGateName15(String gateName15) {
	this.gateName15 = gateName15;
    }

    public String getGateName16() {
	return gateName16;
    }

    public void setGateName16(String gateName16) {
	this.gateName16 = gateName16;
    }

    public String getGateName17() {
	return gateName17;
    }

    public void setGateName17(String gateName17) {
	this.gateName17 = gateName17;
    }

    public String getGateName18() {
	return gateName18;
    }

    public void setGateName18(String gateName18) {
	this.gateName18 = gateName18;
    }

    public String getGateName19() {
	return gateName19;
    }

    public void setGateName19(String gateName19) {
	this.gateName19 = gateName19;
    }

    public String getGateName20() {
	return gateName20;
    }

    public void setGateName20(String gateName20) {
	this.gateName20 = gateName20;
    }

    public String getSelect11() {
	return select11;
    }

    public void setSelect11(String select11) {
	this.select11 = select11;
    }

    public String getSelect12() {
	return select12;
    }

    public void setSelect12(String select12) {
	this.select12 = select12;
    }

    public String getSelect13() {
	return select13;
    }

    public void setSelect13(String select13) {
	this.select13 = select13;
    }

    public String getSelect14() {
	return select14;
    }

    public void setSelect14(String select14) {
	this.select14 = select14;
    }

    public String getSelect15() {
	return select15;
    }

    public void setSelect15(String select15) {
	this.select15 = select15;
    }

    public String getSelect16() {
	return select16;
    }

    public void setSelect16(String select16) {
	this.select16 = select16;
    }

    public String getSelect17() {
	return select17;
    }

    public void setSelect17(String select17) {
	this.select17 = select17;
    }

    public String getSelect18() {
	return select18;
    }

    public void setSelect18(String select18) {
	this.select18 = select18;
    }

    public String getSelect19() {
	return select19;
    }

    public void setSelect19(String select19) {
	this.select19 = select19;
    }

    public String getSelect20() {
	return select20;
    }

    public void setSelect20(String select20) {
	this.select20 = select20;
    }

    public String getTarget11() {
	return target11;
    }

    public void setTarget11(String target11) {
	this.target11 = target11;
    }

    public String getTarget12() {
	return target12;
    }

    public void setTarget12(String target12) {
	this.target12 = target12;
    }

    public String getTarget13() {
	return target13;
    }

    public void setTarget13(String target13) {
	this.target13 = target13;
    }

    public String getTarget14() {
	return target14;
    }

    public void setTarget14(String target14) {
	this.target14 = target14;
    }

    public String getTarget15() {
	return target15;
    }

    public void setTarget15(String target15) {
	this.target15 = target15;
    }

    public String getTarget16() {
	return target16;
    }

    public void setTarget16(String target16) {
	this.target16 = target16;
    }

    public String getTarget17() {
	return target17;
    }

    public void setTarget17(String target17) {
	this.target17 = target17;
    }

    public String getTarget18() {
	return target18;
    }

    public void setTarget18(String target18) {
	this.target18 = target18;
    }

    public String getTarget19() {
	return target19;
    }

    public void setTarget19(String target19) {
	this.target19 = target19;
    }

    public String getTarget20() {
	return target20;
    }

    public void setTarget20(String target20) {
	this.target20 = target20;
    }

    public String getResult11() {
	return result11;
    }

    public void setResult11(String result11) {
	this.result11 = result11;
    }

    public String getResult12() {
	return result12;
    }

    public void setResult12(String result12) {
	this.result12 = result12;
    }

    public String getResult13() {
	return result13;
    }

    public void setResult13(String result13) {
	this.result13 = result13;
    }

    public String getResult14() {
	return result14;
    }

    public void setResult14(String result14) {
	this.result14 = result14;
    }

    public String getResult15() {
	return result15;
    }

    public void setResult15(String result15) {
	this.result15 = result15;
    }

    public String getResult16() {
	return result16;
    }

    public void setResult16(String result16) {
	this.result16 = result16;
    }

    public String getResult17() {
	return result17;
    }

    public void setResult17(String result17) {
	this.result17 = result17;
    }

    public String getResult18() {
	return result18;
    }

    public void setResult18(String result18) {
	this.result18 = result18;
    }

    public String getResult19() {
	return result19;
    }

    public void setResult19(String result19) {
	this.result19 = result19;
    }

    public String getResult20() {
	return result20;
    }

    public void setResult20(String result20) {
	this.result20 = result20;
    }

    public String getTargetType() {
	return targetType;
    }

    public void setTargetType(String targetType) {
	this.targetType = targetType;
    }

    public String getPjtOid() {
	return pjtOid;
    }

    public void setPjtOid(String pjtOid) {
	this.pjtOid = pjtOid;
    }

    public String getCreateStamp() {
	return createStamp;
    }

    public void setCreateStamp(String createStamp) {
	this.createStamp = createStamp;
    }

    public String getModifyStamp() {
	return modifyStamp;
    }

    public void setModifyStamp(String modifyStamp) {
	this.modifyStamp = modifyStamp;
    }

    public String getCheckSheetName() {
	return checkSheetName;
    }

    public void setCheckSheetName(String checkSheetName) {
	this.checkSheetName = checkSheetName;
    }

    public String getAchieveBase() {
	return achieveBase;
    }

    public void setAchieveBase(String achieveBase) {
	this.achieveBase = achieveBase;
    }

    public String getUnit() {
	return unit;
    }

    public void setUnit(String unit) {
	this.unit = unit;
    }

    public String getCriteria() {
	return criteria;
    }

    public void setCriteria(String criteria) {
	this.criteria = criteria;
    }

    public int getOrderNo() {
	return orderNo;
    }

    public void setOrderNo(int orderNo) {
	this.orderNo = orderNo;
    }

    public String getGateName1() {
	return gateName1;
    }

    public void setGateName1(String gateName1) {
	this.gateName1 = gateName1;
    }

    public String getGateName2() {
	return gateName2;
    }

    public void setGateName2(String gateName2) {
	this.gateName2 = gateName2;
    }

    public String getGateName3() {
	return gateName3;
    }

    public void setGateName3(String gateName3) {
	this.gateName3 = gateName3;
    }

    public String getGateName4() {
	return gateName4;
    }

    public void setGateName4(String gateName4) {
	this.gateName4 = gateName4;
    }

    public String getGateName5() {
	return gateName5;
    }

    public void setGateName5(String gateName5) {
	this.gateName5 = gateName5;
    }

    public String getGateName6() {
	return gateName6;
    }

    public void setGateName6(String gateName6) {
	this.gateName6 = gateName6;
    }

    public String getGateName7() {
	return gateName7;
    }

    public void setGateName7(String gateName7) {
	this.gateName7 = gateName7;
    }

    public String getGateName8() {
	return gateName8;
    }

    public void setGateName8(String gateName8) {
	this.gateName8 = gateName8;
    }

    public String getGateName9() {
	return gateName9;
    }

    public void setGateName9(String gateName9) {
	this.gateName9 = gateName9;
    }

    public String getGateName10() {
	return gateName10;
    }

    public void setGateName10(String gateName10) {
	this.gateName10 = gateName10;
    }

    public String getSelect1() {
	return select1;
    }

    public void setSelect1(String select1) {
	this.select1 = select1;
    }

    public String getSelect2() {
	return select2;
    }

    public void setSelect2(String select2) {
	this.select2 = select2;
    }

    public String getSelect3() {
	return select3;
    }

    public void setSelect3(String select3) {
	this.select3 = select3;
    }

    public String getSelect4() {
	return select4;
    }

    public void setSelect4(String select4) {
	this.select4 = select4;
    }

    public String getSelect5() {
	return select5;
    }

    public void setSelect5(String select5) {
	this.select5 = select5;
    }

    public String getSelect6() {
	return select6;
    }

    public void setSelect6(String select6) {
	this.select6 = select6;
    }

    public String getSelect7() {
	return select7;
    }

    public void setSelect7(String select7) {
	this.select7 = select7;
    }

    public String getSelect8() {
	return select8;
    }

    public void setSelect8(String select8) {
	this.select8 = select8;
    }

    public String getSelect9() {
	return select9;
    }

    public void setSelect9(String select9) {
	this.select9 = select9;
    }

    public String getSelect10() {
	return select10;
    }

    public void setSelect10(String select10) {
	this.select10 = select10;
    }

    public String getTarget1() {
	return target1;
    }

    public void setTarget1(String target1) {
	this.target1 = target1;
    }

    public String getTarget2() {
	return target2;
    }

    public void setTarget2(String target2) {
	this.target2 = target2;
    }

    public String getTarget3() {
	return target3;
    }

    public void setTarget3(String target3) {
	this.target3 = target3;
    }

    public String getTarget4() {
	return target4;
    }

    public void setTarget4(String target4) {
	this.target4 = target4;
    }

    public String getTarget5() {
	return target5;
    }

    public void setTarget5(String target5) {
	this.target5 = target5;
    }

    public String getTarget6() {
	return target6;
    }

    public void setTarget6(String target6) {
	this.target6 = target6;
    }

    public String getTarget7() {
	return target7;
    }

    public void setTarget7(String target7) {
	this.target7 = target7;
    }

    public String getTarget8() {
	return target8;
    }

    public void setTarget8(String target8) {
	this.target8 = target8;
    }

    public String getTarget9() {
	return target9;
    }

    public void setTarget9(String target9) {
	this.target9 = target9;
    }

    public String getTarget10() {
	return target10;
    }

    public void setTarget10(String target10) {
	this.target10 = target10;
    }

    public String getResult1() {
	return result1;
    }

    public void setResult1(String result1) {
	this.result1 = result1;
    }

    public String getResult2() {
	return result2;
    }

    public void setResult2(String result2) {
	this.result2 = result2;
    }

    public String getResult3() {
	return result3;
    }

    public void setResult3(String result3) {
	this.result3 = result3;
    }

    public String getResult4() {
	return result4;
    }

    public void setResult4(String result4) {
	this.result4 = result4;
    }

    public String getResult5() {
	return result5;
    }

    public void setResult5(String result5) {
	this.result5 = result5;
    }

    public String getResult6() {
	return result6;
    }

    public void setResult6(String result6) {
	this.result6 = result6;
    }

    public String getResult7() {
	return result7;
    }

    public void setResult7(String result7) {
	this.result7 = result7;
    }

    public String getResult8() {
	return result8;
    }

    public void setResult8(String result8) {
	this.result8 = result8;
    }

    public String getResult9() {
	return result9;
    }

    public void setResult9(String result9) {
	this.result9 = result9;
    }

    public String getResult10() {
	return result10;
    }

    public void setResult10(String result10) {
	this.result10 = result10;
    }

}
