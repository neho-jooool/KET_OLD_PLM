package e3ps.common.treegrid;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * 
 * @클래스명 : TgStringBuffer
 * @작성자 : tklee
 * @작성일 : 2014. 6. 20.
 * @설명 : TreeGridStringBuffer를 개선 - StringEscapeUtils.escapeXml로 처리 : Server Paging
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
public class TgStringBuffer {

    private StringBuilder sb = new StringBuilder();

    public TgStringBuffer append(String s) {
	sb.append(s);
	return this;
    }

    public TgStringBuffer append(Object o) {
	sb.append(o);
	return this;
    }

    public TgStringBuffer appendRepl(String s) {
	sb.append(StringEscapeUtils.escapeXml(s));
	return this;
    }

    public String toString() {
	return sb.toString();
    }

}
