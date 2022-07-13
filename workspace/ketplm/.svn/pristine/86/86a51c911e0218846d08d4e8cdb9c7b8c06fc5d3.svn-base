package ext.ket.shared.tld;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang.StringUtils;

import e3ps.cost.util.StringUtil;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.shared.log.Kogger;

public class PartNoMultiTag extends SimpleTagSupport {
    
    private String code;

    @Override
    public void doTag() throws JspException, IOException {
	getJspContext().getOut().print(makeValue());
    }
    
    private String makeValue() {
	
	if(StringUtils.isEmpty(code)){
	    return "";
	}
	
	String partNoAlink = null;
	
	try {
	    partNoAlink = PartBaseHelper.service.getALinkByMultiPartNo(code);
	    return StringUtil.checkNull(partNoAlink);
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
