package ext.ket.sample.entity;

import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.PropertiesUtil;
import ext.ket.shared.dto.BaseDTO;

/**
 * Sample DTO 객체
 * 
 * @클래스명 : SampleDTO
 * @작성자 : sw775.park
 * @작성일 : 2014. 5. 8.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
public class SampleDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;
    private String ida2a2;
    private int num;
    private String name;
    private String title;
    private String email;
    private String createStamp;
    private String modifyStamp;
    private String webEditor;
    private String webEditorText;

    public SampleDTO() {
    }

    public SampleDTO(Sample sample) {
	this.setOid(CommonUtil.getOIDString(sample));
	this.num = sample.getNum();
	this.name = sample.getName();
	this.title = sample.getTitle();
	this.webEditor = (String) sample.getWebEditor();
	this.webEditorText = (String) sample.getWebEditorText();
	this.createStamp = DateUtil.getDateString(sample.getPersistInfo().getCreateStamp(), "date");
	this.modifyStamp = DateUtil.getDateString(sample.getPersistInfo().getModifyStamp(), "date");
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

    public String getWebEditor() {
	return webEditor;
    }

    public void setWebEditor(String webEditor) {
	this.webEditor = webEditor;
    }

    public String getWebEditorText() {
	return webEditorText;
    }

    public void setWebEditorText(String webEditorText) {
	this.webEditorText = webEditorText;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getIda2a2() {
	return ida2a2;
    }

    public void setIda2a2(String ida2a2) {
	this.ida2a2 = ida2a2;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    /**
     * @return the title
     */
    public String getTitle() {
	return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
	this.title = title;
    }

    /**
     * @return the num
     */
    public int getNum() {
	return num;
    }

    /**
     * @param num
     *            the num to set
     */
    public void setNum(int num) {
	this.num = num;
    }

    public String getNameHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getNameHtmlPostfix() {
	return "</font>";
    }
}
