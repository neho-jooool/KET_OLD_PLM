package ext.ket.shared.tld;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang.StringUtils;

import wt.part.QuantityUnit;

/**
 * 
 * 
 * @클래스명 : SelectPartUnitTag
 * @작성자 : yjlee
 * @작성일 : 2014. 8. 26.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
public class SelectPartUnitTag extends SimpleTagSupport {

    private String htmlAttribute;
    private String selectCode;

    @Override
    public void doTag() throws JspException, IOException {
	getJspContext().getOut().print(makeSelectOption());
    }

    private String makeSelectOption() {

	StringBuffer buffer = new StringBuffer();
	buffer.append("<select " + (StringUtils.isNotEmpty(htmlAttribute) ? htmlAttribute : "") + " >");

	QuantityUnit[] unitArry = QuantityUnit.getQuantityUnitSet();

	for (QuantityUnit unit : unitArry) {

	    String unitCode = unit.toString();

	    if (!unitCode.startsWith("KET"))
		continue;

	    if (unitCode.toUpperCase().equals("KET_M") || unitCode.toUpperCase().equals("KET_EA") || unitCode.toUpperCase().equals("KET_KG")) {// 2020.04.09 차주원 차장 요청으로 선택 제한, 추후 예외 사항 로직이 정해질때까지는 무조건 M,EA,KG 가능하도록

	    } else {
		continue;
	    }

	    String selected = "";
	    if (unitCode.equals(selectCode)) {
		selected = " selected";
	    }

	    String unitText = unit.getDisplay();
	    buffer.append("<option value=\"" + unitCode + "\" " + selected + " >");
	    buffer.append(unitText);
	    buffer.append("</option>");
	}

	buffer.append("</select> ");
	return buffer.toString();
    }

    public String getHtmlAttribute() {
	return htmlAttribute;
    }

    public void setHtmlAttribute(String htmlAttribute) {
	this.htmlAttribute = htmlAttribute;
    }

    public String getSelectCode() {
	return selectCode;
    }

    public void setSelectCode(String selectCode) {
	this.selectCode = selectCode;
    }

}
