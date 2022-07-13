package ext.ket.shared.tld;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang.StringUtils;

import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.shared.log.Kogger;

public class ManufacturePlaceTag extends SimpleTagSupport {
    
    private String code;

    @Override
    public void doTag() throws JspException, IOException {
	getJspContext().getOut().print(makeValue());
    }
    
    private String makeValue() {
	
	if(StringUtils.isEmpty(code)){
	    return "";
	}
	
	String manufacPlaceName = null;
	
	try {
	    manufacPlaceName = PartBaseHelper.service.getManufacPlaceName(code);
	    return manufacPlaceName;
        } catch (Exception e) {
	    Kogger.error(getClass(), e);
	    return "";
        }
	
    }
    
    public String getCode() {
	return code;
    }

    public void setCode(String code) {
	this.code = code;
    }

}
