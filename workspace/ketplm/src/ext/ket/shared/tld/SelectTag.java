package ext.ket.shared.tld;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang.StringUtils;

import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleTemplate;
import wt.lifecycle.PhaseTemplate;
import wt.lifecycle.State;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.message.KETMessageService;
import e3ps.common.util.StringUtil;
import e3ps.common.util.WCUtil;
import ext.ket.shared.log.Kogger;

/**
 * SelectTag lib
 * 
 * @클래스명 : SelectTag
 * @작성자 : sw775.park
 * @작성일 : 2014. 7. 9.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
public class SelectTag extends SimpleTagSupport {
    private String codeType;
    private String code;
    private String id;
    private String name;
    private String style;
    private String value;
    private String onClick;
    private String onFocus;
    private String onBlur;
    private String onChange;
    private String className;
    private String disabled;
    private String multiple;
    private String depthLevel;
    private String lifeCycleState;
    private String otherHtml;
    private String esse;
    private String esseLabel;
    private boolean useNullValue = false;
    private boolean useCodeValue = false;
    private boolean useOidValue = false;
    private boolean useNamingAbbr = false; // 부품명 자동생성에서 약어를 사용함

    public boolean isUseNullValue() {
	return useNullValue;
    }

    public void setUseNullValue(boolean useNullValue) {
	this.useNullValue = useNullValue;
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

    public boolean isUseCodeValue() {
	return useCodeValue;
    }

    public void setUseCodeValue(boolean useCodeValue) {
	this.useCodeValue = useCodeValue;
    }

    public boolean isUseNamingAbbr() {
	return useNamingAbbr;
    }

    public void setUseNamingAbbr(boolean useNamingAbbr) {
	this.useNamingAbbr = useNamingAbbr;
    }

    public boolean isUseOidValue() {
	return useOidValue;
    }

    public void setUseOidValue(boolean useOidValue) {
	this.useOidValue = useOidValue;
    }

    public String getCodeType() {
	return codeType;
    }

    public void setCodeType(String codeType) {
	this.codeType = codeType;
    }

    public String getCode() {
	return code;
    }

    public void setCode(String code) {
	this.code = code;
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

    public String getOnClick() {
	return onClick;
    }

    public void setOnClick(String onClick) {
	this.onClick = onClick;
    }

    public String getOnFocus() {
	return onFocus;
    }

    public void setOnFocus(String onFocus) {
	this.onFocus = onFocus;
    }

    public String getOnBlur() {
	return onBlur;
    }

    public void setOnBlur(String onBlur) {
	this.onBlur = onBlur;
    }

    public String getOnChange() {
	return onChange;
    }

    public void setOnChange(String onChange) {
	this.onChange = onChange;
    }

    public String getClassName() {
	return className;
    }

    public void setClassName(String className) {
	this.className = className;
    }

    public String getDisabled() {
	return disabled;
    }

    public void setDisabled(String disabled) {
	this.disabled = disabled;
    }

    public String getMultiple() {
	return multiple;
    }

    public void setMultiple(String multiple) {
	this.multiple = multiple;
    }

    public String getDepthLevel() {
	return depthLevel;
    }

    public void setDepthLevel(String depthLevel) {
	this.depthLevel = depthLevel;
    }

    public String getOtherHtml() {
	return otherHtml;
    }

    public void setOtherHtml(String otherHtml) {
	this.otherHtml = otherHtml;
    }

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
	if (!StringUtil.isEmpty(onClick)) {
	    sb.append("onClick=\"" + onClick + "\" ");
	}
	if (!StringUtil.isEmpty(onFocus)) {
	    sb.append("onFocus=\"" + onFocus + "\" ");
	}
	if (!StringUtil.isEmpty(onBlur)) {
	    sb.append("onBlur=\"" + onBlur + "\" ");
	}
	if (!StringUtil.isEmpty(onChange)) {
	    sb.append("onChange=\"" + onChange + "\" ");
	}
	if (!StringUtil.isEmpty(multiple)) {
	    sb.append("multiple=\"" + multiple + "\" ");
	}
	if (!StringUtil.isEmpty(otherHtml)) {
	    sb.append(" " + otherHtml + " ");
	}
	if (!StringUtil.isEmpty(disabled)) {
	    sb.append("disabled ");
	}
	if (!StringUtil.isEmpty(esse)) {
	    sb.append("esse=\"" + esse + "\" ");
	}
	if (!StringUtil.isEmpty(esseLabel)) {
	    sb.append("esseLabel=\"" + esseLabel + "\" ");
	}
	sb.append(">");
	if (!StringUtil.isEmpty(lifeCycleState)) {
	    sb.append(this.getOptionTagByLifeCycle());
	} else {
	    sb.append(this.getOptionTagByCodeType());
	}
	sb.append("</select>");
	getJspContext().getOut().print(sb.toString());
    }

    @SuppressWarnings("static-access")
    private KETMessageService getMessageService() throws Exception {
	PageContext pageContext = (PageContext) getJspContext().getAttribute("javax.servlet.jsp.jspPageContext");
	return KETMessageService.service.getMessageService(((HttpServletRequest) pageContext.getRequest()));
    }

    /**
     * NumberCode를 위한 option Tag
     * 
     * @return
     * @메소드명 : getOptionTagByCodeType
     * @작성자 : sw775.park
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    private StringBuffer getOptionTagByCodeType() {
	StringBuffer sb = new StringBuffer();
	Map<String, Object> parameter = new HashMap<String, Object>();
	try {
	    parameter.put("locale", getMessageService().getLocale());
	    parameter.put("codeType", codeType);
	    if (!StringUtil.isEmpty(code)) {
		parameter.put("code", code);
	    }
	    if (!StringUtil.isEmpty(depthLevel)) {
		parameter.put("depthLevel", depthLevel);
	    }
	    List<Map<String, Object>> codeList = NumberCodeHelper.manager.getNumberCodeList(parameter);

	    String[] values = new String[0];
	    if (!StringUtil.isEmpty(value)) {
		values = value.trim().split(",");
	    }
	    if (isUseNullValue()) {
		sb.append("<option value=\"\">" + getMessageService().getString("e3ps.message.ket_message", "01802") + "</option>");
	    }
	    for (Map<String, Object> map : codeList) {
		String selected = "";
		for (String valueStr : values) {
		    if (useCodeValue && map.get("code").equals(valueStr)) {
			selected = " selected";
		    } else if (useOidValue && map.get("oid").equals(valueStr)) {
			selected = " selected";
		    } else {
			if (map.get("ida2a2").equals(valueStr)) {
			    selected = " selected";
			}
		    }
		}
		if (useCodeValue) {
		    sb.append("<option value=\"" + map.get("code") + "\"" + selected + ">" + map.get("value") + "</option>");
		} else if (useOidValue) {
		    sb.append("<option value=\"" + map.get("oid") + "\"" + selected + ">" + map.get("value") + "</option>");
		} else if (useNamingAbbr) {
		    String abbr = (String) map.get("abbr");
		    abbr = StringUtils.isEmpty(abbr) ? "↕" + map.get("code") : abbr + "↕" + map.get("code");

		    sb.append("<option value=\"" + abbr + "\"" + selected + ">" + (String) map.get("abbr") + "(" + map.get("value") + ")" + "</option>");
		} else {
		    sb.append("<option value=\"" + map.get("ida2a2") + "\"" + selected + ">" + map.get("value") + "</option>");
		}
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	return sb;
    }

    @SuppressWarnings("rawtypes")
    private StringBuffer getOptionTagByLifeCycle() {
	StringBuffer sb = new StringBuffer();
	try {
	    LifeCycleTemplate lifecycletemplate = LifeCycleHelper.service.getLifeCycleTemplate(lifeCycleState, WCUtil.getWTContainerRef());
	    Vector states = LifeCycleHelper.service.getPhaseTemplates(lifecycletemplate);

	    String[] values = new String[0];
	    if (!StringUtil.isEmpty(value)) {
		values = value.trim().split(",");
	    }
	    for (int i = 0; i < states.size(); i++) {
		PhaseTemplate pt = (PhaseTemplate) states.get(i);
		State lcState = pt.getPhaseState();
		String stateName = pt.getPhaseState().getDisplay(this.getMessageService().getLocale());
		String selected = "";
		for (String valueStr : values) {
		    if (lcState.toString().equals(valueStr)) {
			selected = " selected";
		    }
		}
		sb.append("<option value=\"" + lcState + "\"" + selected + ">" + stateName + "</option>");
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	return sb;
    }

    /**
     * @return the lifeCycleState
     */
    public String getLifeCycleState() {
	return lifeCycleState;
    }

    /**
     * @param lifeCycleState
     *            the lifeCycleState to set
     */
    public void setLifeCycleState(String lifeCycleState) {
	this.lifeCycleState = lifeCycleState;
    }

}
