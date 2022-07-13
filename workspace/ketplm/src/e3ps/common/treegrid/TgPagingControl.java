package e3ps.common.treegrid;

import javax.servlet.http.HttpServletRequest;

import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.PropertiesUtil;
import ext.ket.shared.log.Kogger;

/**
 * 
 * @클래스명 : TgPagingControl
 * @작성자 : tklee
 * @작성일 : 2014. 6. 20.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
public final class TgPagingControl {

    public final static String DEFAULT_ROW_NUM = "";
    private String XML_INPUT_STR;
    private int PAGE_SIZE;

    private final static String ASC = "ASC";
    private final static String DESC = "DESC";

    private org.w3c.dom.Document X = null;

    // Constructor 1
    public TgPagingControl(boolean isPage, KETParamMapUtil paramMap) {

	String xmlParamStr = paramMap.getString("TGData", null);
	String perPage = paramMap.getString("perPage", null);

	initialize(isPage, xmlParamStr, perPage);
    }

    // Constructor 2
    public TgPagingControl(boolean isPage, HttpServletRequest req) {

	String xmlParamStr = req.getParameter("TGData");
	String perPage = req.getParameter("perPage");

	initialize(isPage, xmlParamStr, perPage);
    }

    // Constructor 3
    public TgPagingControl(boolean isPage, String xmlParamStr, String perPage) {

	initialize(isPage, xmlParamStr, perPage);
    }

    private void initialize(boolean isPage, String xmlParamStr, String perPage) {

	if (xmlParamStr != null)
	    this.XML_INPUT_STR = xmlParamStr;

	if (xmlParamStr == null) {
	    X = parseXML(isPage ? "<Grid><Cfg /><Body><B Pos='0'/></Body></Grid>" : "<Grid><Cfg /></Grid>");
	} else {
	    X = parseXML(xmlParamStr);
	}

	if (perPage == null || perPage.trim().equals("")) {
	    PAGE_SIZE = Integer.parseInt(PropertiesUtil.getSearchPagingSizeDefault());
	} else {
	    PAGE_SIZE = Integer.parseInt(perPage);
	}

    }

    // /////////////////////////////
    // for paginging
    // ////////////////////////////
    public int getCurrentPageNo() {
	return getPagePos(X);
    }

    public int getPagingStartNo() {
	return getCurrentPageNo() * PAGE_SIZE;
    }

    // totalcount - pageSize * ( pos - 1) // pos가 0부터 시작
    public int getRowNumStartNo(int totalCount) {
	return totalCount - PAGE_SIZE * (getCurrentPageNo());
    }

    public String getSortCol() {

	String[] temp = getSortCols(X);

	if (temp == null || temp.length == 0)
	    return null;
	else
	    return temp[0];
    }

    public String getSortType() {

	String[] temp = getSortTypes(X);
	String replaceStr = DESC; // if( data nothing), then default == DESC;

	if (temp == null || temp.length == 0) {
	    String tempSortCol = getSortCol();
	    if (tempSortCol != null && !"".equals(tempSortCol.trim()))
		return ASC;
	    else
		return replaceStr;
	} else {
	    if (temp[0] != null) {
		return temp[0];
	    } else {
		String tempSortCol = getSortCol();
		if (tempSortCol != null && !"".equals(tempSortCol.trim()))
		    return ASC;
		else
		    return replaceStr;
	    }
	}
    }

    public boolean getSortTypeForWcQuery() {

	String sortType = getSortType();
	if (sortType == null)
	    return true;

	if (sortType == DESC)
	    return true;
	else if (sortType == ASC)
	    return false;
	else
	    return true;

    }

    // multi sorting - 현재 사용하지 않음
    // public List<String> getSortColList() {
    // List<String> sortColsList = Arrays.asList(getSortCols(X));
    // return sortColsList;
    // }
    //
    // public List<String> getSortTypeList() {
    // List<String> sortTypesList = Arrays.asList(getSortTypes(X));
    // return sortTypesList;
    // }

    // /////////////////////////////
    // for grouping
    // ////////////////////////////
    public String[] getgetGroupCols() {
	return getGroupCols(X);
    }

    public int getLevels() {
	return getLevels(getgetGroupCols());
    }

    public int getLevels(String[] GroupCols) {
	if (GroupCols == null)
	    return 0;

	return GroupCols.length;
    }

    public int getPageSize() {
	return PAGE_SIZE;
    }

    public int getPagingLength(int totalCount) {

	if (totalCount == 0)
	    return 0;

	int tempPage = totalCount / PAGE_SIZE;
	int tempRemain = totalCount % PAGE_SIZE;

	if (tempRemain != 0)
	    tempPage = tempPage + 1;

	return tempPage;
    }

    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ------------------------------------------------------------------------------------------------------------------
    // From TreeGrid JSP framework
    // ------------------------------------------------------------------------------------------------------------------
    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private org.w3c.dom.Document parseXML(String XML) {
	if (XML == null)
	    XML = "";
	if (XML.equals(""))
	    return null;
	if (XML.charAt(0) == '&')
	    XML = XML.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&amp;", "&").replaceAll("&quot;", "\"")
		    .replaceAll("&apos;", "'");
	try {
	    Kogger.debug(getClass(), XML);
	    return javax.xml.parsers.DocumentBuilderFactory.newInstance().newDocumentBuilder()
		    .parse(new org.xml.sax.InputSource(new java.io.StringReader(XML)));
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    return null;
	}
    }

    // ------------------------------------------------------------------------------------------------------------------
    // Returns array of columns according to is grid sorted
    // Returns null if there are no columns
    private String[] getGroupCols(String XML) {
	return getGroupCols(parseXML(XML));
    }

    private String[] getGroupCols(org.w3c.dom.Document XML) {
	if (XML == null)
	    return null;
	org.w3c.dom.Element Cfg = (org.w3c.dom.Element) XML.getElementsByTagName("Cfg").item(0);
	String[] s = Cfg.getAttribute("GroupCols").split("\\,");
	return s.length == 0 || s[0].length() == 0 ? null : s;
    }

    // ------------------------------------------------------------------------------------------------------------------
    // Returns page number from input XML
    // private int getPagePos(String XML) {
    // return getPagePos(parseXML(XML));
    // }

    private int getPagePos(org.w3c.dom.Document XML) {
	if (XML == null)

	    return -1;

	String posTemp = "0";

	try {

	    posTemp = ((org.w3c.dom.Element) (XML.getElementsByTagName("B").item(0))).getAttribute("Pos");

	} catch (Exception e) {
	}

	return posTemp == null ? 0 : Integer.valueOf(posTemp).intValue();
    }

    // ------------------------------------------------------------------------------------------------------------------
    // Returns array of columns according to is grid sorted
    // Returns null if there are no columns
    // private String[] getSortCols(String XML) {
    // return getSortCols(parseXML(XML));
    // }

    private String[] getSortCols(org.w3c.dom.Document XML) {
	if (XML == null)
	    return null;
	org.w3c.dom.Element Cfg = (org.w3c.dom.Element) XML.getElementsByTagName("Cfg").item(0);
	String[] s = Cfg.getAttribute("SortCols").split("\\,");
	return s.length == 0 || s[0].length() == 0 ? null : s;
    }

    
    public void replaceSortCols(String regex, String replacement) {
	if (this.X != null) {
            org.w3c.dom.Element Cfg = (org.w3c.dom.Element) this.X.getElementsByTagName("Cfg").item(0);
            String s = Cfg.getAttribute("SortCols");
            if (s != null) {
                s = s.replaceAll(regex, replacement);
        	    
                Cfg.setAttribute("SortCols", s);
            }
        	
	}
        	
    }
    
    // ------------------------------------------------------------------------------------------------------------------
    // Returns array of sorting types for columns according to is grid sorted
    // Returns null if there are no columns
    // private int[] getSortTypes(String XML) {
    // return getSortTypes(parseXML(XML));
    // }

    // private int[] getSortTypes(org.w3c.dom.Document XML) {
    // if (XML == null)
    // return null;
    // org.w3c.dom.Element Cfg = (org.w3c.dom.Element) XML.getElementsByTagName("Cfg").item(0);
    // String[] tt = Cfg.getAttribute("SortTypes").split("\\,");
    // if (tt.length == 0 || tt[0].length() == 0)
    // return null;
    // int[] t = new int[tt.length];
    // for (int i = 0; i < t.length; i++)
    // t[i] = Integer.valueOf(tt[i]).intValue();
    // return t;
    // }

    private String[] getSortTypes(org.w3c.dom.Document XML) {
	if (XML == null)
	    return null;
	org.w3c.dom.Element Cfg = (org.w3c.dom.Element) XML.getElementsByTagName("Cfg").item(0);
	String[] tt = Cfg.getAttribute("SortTypes").split("\\,");
	if (tt.length == 0 || tt[0].length() == 0)
	    return null;
	String[] t = new String[tt.length];
	for (int i = 0; i < t.length; i++) {
	    if (Integer.parseInt(tt[i]) >= 1) {
		t[i] = DESC;
	    } else {
		t[i] = ASC;
	    }
	}
	return t;
    }

    public static void main(String[] args) {
	int totalCount = 23;
	int pageSize = 21;
	int rest = totalCount / pageSize;
	int rest_2 = totalCount % pageSize;

	Kogger.debug(TgPagingControl.class, "rest:" + rest + " rest_2:" + rest_2);
    }

}
