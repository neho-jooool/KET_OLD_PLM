package ext.ket.project.trycondition.entity;

public class TryPressDTO extends TryConditionDTO {
    private static final long serialVersionUID = 1L;
    private String bolsterHeight;
    private String bolsterWidth;
    private String dieHeight;
    private String feederMachine;
    private String feederMachineEtc;
    private String material; // Grade
    private String moldSizeHeight;
    private String moldSizeLength;
    private String moldSizeWidth;
    private String moldStruc;
    private String moldStrucEtc;
    private String moldWeightLower;
    private String moldWeightTop;
    private String pitch;
    private String plating;
    private String press;
    private String productMethod;
    private String productMethodEtc;
    private String productOutputCol;
    private String productOutputPitch;
    private String punchingOil;
    private String punchingSpeed;
    private String punchingCount;
    private String saftySensor;
    private String scrapProcess;
    private String spm;
    private String stroke;
    private String testResult;
    private String thickness;
    private String tone;
    private String jsonData;

    public TryPressDTO() {
    }

    public TryPressDTO(KETTryCondition tryCondition) throws Exception {
	super(tryCondition);
    }

    public String getPunchingCount() {
	return punchingCount;
    }

    public void setPunchingCount(String punchingCount) {
	this.punchingCount = punchingCount;
    }

    public String getBolsterHeight() {
	return bolsterHeight;
    }

    public String getSaftySensor() {
	return saftySensor;
    }

    public void setSaftySensor(String saftySensor) {
	this.saftySensor = saftySensor;
    }

    public String getBolsterWidth() {
	return bolsterWidth;
    }

    public String getDieHeight() {
	return dieHeight;
    }

    public String getFeederMachine() {
	return feederMachine;
    }

    public String getFeederMachineEtc() {
	return feederMachineEtc;
    }

    public String getMaterial() {
	return material;
    }

    public String getMoldSizeHeight() {
	return moldSizeHeight;
    }

    public String getMoldSizeLength() {
	return moldSizeLength;
    }

    public String getMoldSizeWidth() {
	return moldSizeWidth;
    }

    public String getMoldStruc() {
	return moldStruc;
    }

    public String getMoldStrucEtc() {
	return moldStrucEtc;
    }

    public String getMoldWeightLower() {
	return moldWeightLower;
    }

    public String getMoldWeightTop() {
	return moldWeightTop;
    }

    public String getPitch() {
	return pitch;
    }

    public String getPlating() {
	return plating;
    }

    public String getPress() {
	return press;
    }

    public String getProductMethod() {
	return productMethod;
    }

    public String getProductMethodEtc() {
	return productMethodEtc;
    }

    public String getProductOutputCol() {
	return productOutputCol;
    }

    public String getProductOutputPitch() {
	return productOutputPitch;
    }

    public String getPunchingOil() {
	return punchingOil;
    }

    public String getPunchingSpeed() {
	return punchingSpeed;
    }

    public String getScrapProcess() {
	return scrapProcess;
    }

    public String getSpm() {
	return spm;
    }

    public String getStroke() {
	return stroke;
    }

    public String getTestResult() {
	return testResult;
    }

    public String getThickness() {
	return thickness;
    }

    public String getTone() {
	return tone;
    }

    public void setBolsterHeight(String bolsterHeight) {
	this.bolsterHeight = bolsterHeight;
    }

    public void setBolsterWidth(String bolsterWidth) {
	this.bolsterWidth = bolsterWidth;
    }

    public void setDieHeight(String dieHeight) {
	this.dieHeight = dieHeight;
    }

    public void setFeederMachine(String feederMachine) {
	this.feederMachine = feederMachine;
    }

    public void setFeederMachineEtc(String feederMachineEtc) {
	this.feederMachineEtc = feederMachineEtc;
    }

    public void setMaterial(String material) {
	this.material = material;
    }

    public void setMoldSizeHeight(String moldSizeHeight) {
	this.moldSizeHeight = moldSizeHeight;
    }

    public void setMoldSizeLength(String moldSizeLength) {
	this.moldSizeLength = moldSizeLength;
    }

    public void setMoldSizeWidth(String moldSizeWidth) {
	this.moldSizeWidth = moldSizeWidth;
    }

    public void setMoldStruc(String moldStruc) {
	this.moldStruc = moldStruc;
    }

    public void setMoldStrucEtc(String moldStrucEtc) {
	this.moldStrucEtc = moldStrucEtc;
    }

    public void setMoldWeightLower(String moldWeightLower) {
	this.moldWeightLower = moldWeightLower;
    }

    public void setMoldWeightTop(String moldWeightTop) {
	this.moldWeightTop = moldWeightTop;
    }

    public void setPitch(String pitch) {
	this.pitch = pitch;
    }

    public void setPlating(String plating) {
	this.plating = plating;
    }

    public void setPress(String press) {
	this.press = press;
    }

    public void setProductMethod(String productMethod) {
	this.productMethod = productMethod;
    }

    public void setProductMethodEtc(String productMethodEtc) {
	this.productMethodEtc = productMethodEtc;
    }

    public void setProductOutputCol(String productOutputCol) {
	this.productOutputCol = productOutputCol;
    }

    public void setProductOutputPitch(String productOutputPitch) {
	this.productOutputPitch = productOutputPitch;
    }

    public void setPunchingOil(String punchingOil) {
	this.punchingOil = punchingOil;
    }

    public void setPunchingSpeed(String punchingSpeed) {
	this.punchingSpeed = punchingSpeed;
    }

    public void setScrapProcess(String scrapProcess) {
	this.scrapProcess = scrapProcess;
    }

    public void setSpm(String spm) {
	this.spm = spm;
    }

    public void setStroke(String stroke) {
	this.stroke = stroke;
    }

    public void setTestResult(String testResult) {
	this.testResult = testResult;
    }

    public void setThickness(String thickness) {
	this.thickness = thickness;
    }

    public void setTone(String tone) {
	this.tone = tone;
    }

    public String getJsonData() {
	return jsonData;
    }

    public void setJsonData(String jsonData) {
	this.jsonData = jsonData;
    }
}
