package ext.ket.shared.tld;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang.StringUtils;

import wt.part.WTPart;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.message.KETMessageService;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.classify.service.PartClazHelper;
import ext.ket.part.entity.dto.CommonCodeDTO;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.shared.log.Kogger;

/**
 * 원재료 정보 처리하는 Tag
 * 
 * @클래스명 : SelectMaterialTag
 * @작성자 : yjlee
 * @작성일 : 2014. 10. 29.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
public class SelectMaterialTag extends SimpleTagSupport {

    private String type;
    private String clazOid;
    private String partOid;

    private String className;
    private String id;
    private String name;
    private String style;

    // private String makerCode;
    // private String typeCode;
    // private String gradeCode;
    private String value;

    private String numberCodeTypeUse;
    private String numberCodeType;
    private String ketMultiSelect;
    private String multiple;
    private String otherHtml;

    private String esse;
    private String esseLabel;

    @Override
    public void doTag() throws JspException, IOException {
	StringBuffer sb = new StringBuffer();
	sb.append("<select ");
	if (!StringUtil.isEmpty(className)) {
	    sb.append("class=\"" + className + "\" ");
	}
	if (!StringUtil.isEmpty(id)) {
	    sb.append("id=\"" + id + "\" ");
	}
	if (!StringUtil.isEmpty(name)) {
	    sb.append("name=\"" + name + "\" ");
	}
	if (!StringUtil.isEmpty(style)) {
	    sb.append("style=\"" + style + "\" ");
	}
	if (!StringUtil.isEmpty(numberCodeTypeUse)) {
	    sb.append("numberCodeTypeUse=\"" + numberCodeTypeUse + "\" ");
	}
	if (!StringUtil.isEmpty(numberCodeType)) {
	    sb.append("numberCodeType=\"" + numberCodeType + "\" ");
	}
	if (!StringUtil.isEmpty(ketMultiSelect)) {
	    sb.append("ketMultiSelect=\"" + ketMultiSelect + "\" ");
	}
	if (!StringUtil.isEmpty(multiple)) {
	    sb.append("multiple=\"" + multiple + "\" ");
	}
	if (!StringUtil.isEmpty(otherHtml)) {
	    sb.append(" " + otherHtml + " ");
	}
	if (!StringUtil.isEmpty(esse)) {
	    sb.append("esse=\"" + esse + "\" ");
	}
	if (!StringUtil.isEmpty(esseLabel)) {
	    sb.append("esseLabel=\"" + esseLabel + "\" ");
	}
	sb.append(">");
	if ("MAKER".equals(type)) {
	    sb.append(this.getOptionMaterialMakerTag());
	} else if ("TYPE".equals(type)) {
	    sb.append(this.getOptionMaterialTypeTag());
	} else if ("GRADE".equals(type)) {
	    sb.append(this.getOptionMaterialGradeTag());
	}

	sb.append("</select>");
	getJspContext().getOut().print(sb.toString());
    }

    @SuppressWarnings("static-access")
    private KETMessageService getMessageService() throws Exception {
	PageContext pageContext = (PageContext) getJspContext().getAttribute("javax.servlet.jsp.jspPageContext");
	return KETMessageService.service.getMessageService(((HttpServletRequest) pageContext.getRequest()));
    }
    
    private StringBuffer getOptionMaterialGradeTag() {
	
	try {
	    
	    WTPart wtPart = (WTPart)CommonUtil.getObject(partOid);
	    
	    String spMaterMaker = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpMaterMaker);
	    String spMaterType = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpMaterType);
	    
	    if(StringUtils.isNotEmpty(spMaterMaker) && StringUtils.isNotEmpty(spMaterType)){
		
		Map<String, Object> map = PartBaseHelper.service.getMaterialInfoList(clazOid, spMaterMaker, spMaterType);
		List<CommonCodeDTO> codeList = (List<CommonCodeDTO>)map.get("options");
		StringBuffer sb = new StringBuffer();
		
		for (CommonCodeDTO codeDTO : codeList) {
		    String selected = "";
		    if (value != null && codeDTO.getValue().equals(value)) {
			selected = " selected";
		    }
		    sb.append("<option value=\"" + codeDTO.getValue() + "\"" + selected + ">" + codeDTO.getText() + "</option>");
		}
		return sb;
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return new StringBuffer();
    }
    
    private StringBuffer getOptionMaterialTypeTag() {
	
	try {
	    
	    WTPart wtPart = (WTPart)CommonUtil.getObject(partOid);
	    String spMaterMaker = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpMaterMaker);
	    if(StringUtils.isNotEmpty(spMaterMaker)){
		Map<String, Object> map = PartBaseHelper.service.getMaterialTypeList(clazOid, spMaterMaker);
		List<CommonCodeDTO> codeList = (List<CommonCodeDTO>)map.get("options");
		StringBuffer sb = new StringBuffer();
		for (CommonCodeDTO codeDTO : codeList) {
		    String selected = "";
		    if (value != null && codeDTO.getValue().equals(value)) {
			selected = " selected";
		    }
		    sb.append("<option value=\"" + codeDTO.getValue() + "\"" + selected + ">" + codeDTO.getText() + "</option>");
		}
		return sb;
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return new StringBuffer();
    }

    private StringBuffer getOptionMaterialMakerTag() {

	try {
	    
	    boolean isSuji = false;
	    
	    if("MAT1000".equals(clazOid)){
		isSuji = true;
	    }else if("MAT2000".equals(clazOid)){
		isSuji = false;
	    }else{
		isSuji = PartClazHelper.service.isSuji(clazOid);
	    }
	    
	    if (isSuji) {
		return getOptionTagByCodeType("MATERIALMAKER", "MAT1000", value);
	    } else {
		return getOptionTagByCodeType("MATERIALMAKER", "MAT2000", value);
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return new StringBuffer();
    }

    private StringBuffer getOptionTagByCodeType(String codeType, String code, String value) {

	StringBuffer sb = new StringBuffer();
	Map<String, Object> parameter = new HashMap<String, Object>();
	try {
	    parameter.put("locale", getMessageService().getLocale());
	    parameter.put("codeType", codeType);
	    if (!StringUtil.isEmpty(code)) {
		parameter.put("code", code);
	    }
	    List<Map<String, Object>> codeList = NumberCodeHelper.manager.getNumberCodeList(parameter);
	    for (Map<String, Object> map : codeList) {
		String selected = "";
		if (value != null && map.get("code").equals(value)) {
		    selected = " selected";
		}
		sb.append("<option value=\"" + map.get("code") + "\"" + selected + ">" + map.get("value") + "</option>");
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	return sb;
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getClassName() {
	return className;
    }

    public void setClassName(String className) {
	this.className = className;
    }

    public String getStyle() {
	return style;
    }

    public void setStyle(String style) {
	this.style = style;
    }

    public String getValue() {
	return value;
    }

    public void setValue(String value) {
	this.value = value;
    }

    public String getNumberCodeTypeUse() {
	return numberCodeTypeUse;
    }

    public void setNumberCodeTypeUse(String numberCodeTypeUse) {
	this.numberCodeTypeUse = numberCodeTypeUse;
    }

    public String getNumberCodeType() {
        return numberCodeType;
    }

    public void setNumberCodeType(String numberCodeType) {
        this.numberCodeType = numberCodeType;
    }

    public String getKetMultiSelect() {
	return ketMultiSelect;
    }

    public void setKetMultiSelect(String ketMultiSelect) {
	this.ketMultiSelect = ketMultiSelect;
    }

    public String getOtherHtml() {
	return otherHtml;
    }

    public void setOtherHtml(String otherHtml) {
	this.otherHtml = otherHtml;
    }

    public String getEsse() {
	return esse;
    }

    public void setEsse(String esse) {
	this.esse = esse;
    }

    public String getEsseLabel() {
	return esseLabel;
    }

    public void setEsseLabel(String esseLabel) {
	this.esseLabel = esseLabel;
    }

    public String getClazOid() {
	return clazOid;
    }

    public void setClazOid(String clazOid) {
	this.clazOid = clazOid;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    public String getPartOid() {
	return partOid;
    }

    public void setPartOid(String partOid) {
	this.partOid = partOid;
    }

    public String getMultiple() {
        return multiple;
    }

    public void setMultiple(String multiple) {
        this.multiple = multiple;
    }

}
