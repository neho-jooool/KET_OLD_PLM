package e3ps.common.treegrid;

/*
 * [PLM System 1차개선]
 * 파일명 : TreeGridStringBuffer.java
 * 설명 : Tree Grid용 변환기능을 포함한 StringBuffer
 * 작성일자 : 2013. 06. 13
 * 작성자 : 김무준
 */

public class TreeGridStringBuffer {

    private StringBuilder sb = new StringBuilder();

    public TreeGridStringBuffer append(String s) {
        sb.append(s);
        return this;
    }
    public TreeGridStringBuffer append(Object o) {
        sb.append(o);
        return this;
    }
    /**
     * TreeGrid 위해 특수문자 변환 후 append
     * @param s - 원본 문자열
     * @return 특수문자가 변환된 문자열 (TreeGridUtil.replaceForI() 참고)
     */
    public TreeGridStringBuffer appendRepl(String s) {
        sb.append(TreeGridUtil.replaceForI(s));
        return this;
    }

    /**
     * TreeGrid 위해 데이터 내의 기호(쌍따옴표) 변환 후 append
     */
    public TreeGridStringBuffer appendContent(String s) {
        s = TreeGridUtil.replaceContentForI(s);
        s = TreeGridUtil.replaceForI(s); // 확인사삽
        sb.append(s);
        return this;
    }

    public String toString() {
        return sb.toString();
    }

}
