package ext.ket.shared.tld;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import e3ps.common.util.StringUtil;
import ext.ket.shared.code.service.CodeHelper;
import ext.ket.shared.log.Kogger;

public class CodeValueByCodeTag extends SimpleTagSupport {
    private String codeType;
    private String code;

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

    @Override
    public void doTag() throws JspException, IOException {
	String codeNm = "";

	if (!StringUtil.isEmpty(code) && !StringUtil.isEmpty(codeType)) {
	    try {
		if (code.indexOf(",") != -1) {
		    String codeArr[] = code.split(",");
		    for (int i = 0; i < codeArr.length; i++) {
			if (codeNm != "") {
			    codeNm += ", ";
			}
			codeNm += CodeHelper.manager.getCodeValue(codeType, codeArr[i].trim());
		    }
		}
		else {
		    codeNm = CodeHelper.manager.getCodeValue(codeType, code);
		}
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
	    }
	}
	getJspContext().getOut().print(codeNm);
    }
}
