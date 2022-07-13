package ext.ket.edm.util;

public enum DrawingSheetTypeEnum {

    Active("NX의 경우만 선택가능"), All("도면 변환되는 CREO는 필수, NX의 경우 선택"), None("비대상 도면 - AUTOCAD");

    private String desc;

    private DrawingSheetTypeEnum(String desc) {
	this.desc = desc;
    }

    public String getDesc() {
	return desc;
    }
}
