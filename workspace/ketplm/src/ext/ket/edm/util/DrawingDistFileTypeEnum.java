package ext.ket.edm.util;

public enum DrawingDistFileTypeEnum {

    PDF("PDF", "PDF가 Stamping 결과에 포함됩니다."), 
    DWG("DWG", "DWG가 Stamping 결과에 포함됩니다."), 
    //ONEDO("3D", "3D가 Stamping 결과에 포함됩니다."), 
    STEP("STEP", "STEP 파일이 Stamping 결과에 포함됩니다."),
    IGS("IGS", "IGS 파일이 Stamping 결과에 포함됩니다."),
    JPG("JPG", "JPG 파일이 Stamping 결과에 포함됩니다."),
    CAT("CAT", "CATIA 파일이 Stamping 결과에 포함됩니다."),
    THREEPDF("3D_PDF", "3D_PDF 파일이 Stamping 결과에 포함됩니다.");

    private String name;
    private String desc;

    private DrawingDistFileTypeEnum(String name, String desc) {
	this.name = name;
	this.desc = desc;
    }

    public String getName() {
	return name;
    }

    public String getDesc() {
	return desc;
    }
}
