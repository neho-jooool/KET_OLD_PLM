package ext.ket.part.classify.util;

/**
 * 분류체계 - 부품구분
 * 
 * @클래스명 : PartClassificType
 * @작성자 : yjlee
 * @작성일 : 2014. 7. 27.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
public enum PartClassificType {

    // Part List, ECN에서 사용
    ASSY("ASSY", "Assembly"), COMPONENT("단품", "단품"), PURCHASE("구매품", "구매품"), NONE("", "비대상");

    private String code;
    private String desc;

    private PartClassificType(String code, String desc) {
	this.code = code;
	this.desc = desc;
    }

    public String getDesc() {
	return desc;
    }

    public String getCode() {
	return code;
    }

    public PartClassificType toPartClassificType(String value) throws Exception {

	PartClassificType ret = null;

	PartClassificType[] types = PartClassificType.values();
	for (PartClassificType type : types) {
	    if (type.getCode().equals(value))
		return type;
	}

	if (ret == null)
	    ret = PartClassificType.NONE;
	// throw new Exception("PartClassificType setting 오류");

	return ret;
    }

}
