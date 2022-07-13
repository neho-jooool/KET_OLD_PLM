package ext.ket.shared.tld;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import e3ps.common.util.StringUtil;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.shared.log.Kogger;

public class MaterialInfoTag extends SimpleTagSupport {

    private String id;

    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void doTag() throws JspException, IOException {
	String codeNm = "";

	if (!StringUtil.isEmpty(id) ) {
	    try {
		codeNm = PartBaseHelper.service.getMaterialInfo(id);
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
	    }
	}
	getJspContext().getOut().print(codeNm);
    }
}
