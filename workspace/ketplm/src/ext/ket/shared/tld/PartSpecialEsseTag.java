package ext.ket.shared.tld;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import e3ps.common.util.StringUtil;
import ext.ket.part.spec.service.PartSpecHelper;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.shared.log.Kogger;

public class PartSpecialEsseTag extends SimpleTagSupport {
    
    private String partClazCode;
    private String attrCode;
    private String isEsse;

    @Override
    public void doTag() throws JspException, IOException {
	
	String esseRedHtml = "<span class=\"red-star\">*</span>";
	String esseBlueHtml = "<span class=\"blue-star\">*</span>";
	String resultHtml = "";

	boolean isFromEsseTag = "true".equals(isEsse);
	
	if (!StringUtil.isEmpty(partClazCode)) {
	    
	    
	    	    
	    try {
		
		boolean[] projectWeightCheck = PartSpecHelper.service.checkProjectWeightEss(partClazCode);
		
		boolean isEsseProject = projectWeightCheck[0];
		boolean isCheckedProject = projectWeightCheck[1];
		boolean isEsseWeight = projectWeightCheck[2];
		boolean isCheckedWeight = projectWeightCheck[3];
		
		
		if(PartSpecEnum.SpProjectNo.getAttrCode().equals(attrCode)){
		    
		    if (isEsseProject) {
			resultHtml = isFromEsseTag?"true":esseRedHtml;
		    } else if (isCheckedProject) {
			resultHtml = isFromEsseTag?"false":esseBlueHtml;
		    }else{
			resultHtml = isFromEsseTag?"false":resultHtml;
		    }
		    
		}else if(PartSpecEnum.SpNetWeight.getAttrCode().equals(attrCode)){
		    
		    if (isEsseWeight) {
			resultHtml = isFromEsseTag?"true":esseRedHtml;
		    } else if (isCheckedWeight) {
			resultHtml = isFromEsseTag?"false":esseBlueHtml;
		    } else{
			resultHtml = isFromEsseTag?"false":resultHtml;
		    }
		}
		
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
	    }
	}
	
	if(isFromEsseTag){
	    if("".equals(resultHtml))
		resultHtml = "false";
	}
	
	getJspContext().getOut().print(resultHtml);
    }

    public String getPartClazCode() {
        return partClazCode;
    }

    public void setPartClazCode(String partClazCode) {
        this.partClazCode = partClazCode;
    }

    public String getAttrCode() {
        return attrCode;
    }

    public void setAttrCode(String attrCode) {
        this.attrCode = attrCode;
    }

    public String getIsEsse() {
        return isEsse;
    }

    public void setIsEsse(String isEsse) {
        this.isEsse = isEsse;
    }

    
}
