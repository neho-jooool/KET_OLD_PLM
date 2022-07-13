package e3ps.common.util;

import wt.util.WTProperties;
import e3ps.common.jdf.config.ConfigImpl;
import ext.ket.shared.log.Kogger;

/* [PLM System 1차개선]
 * 수정내용 : 검색 관련 속성 정보를 가져오는 유틸리티
 * 수정일자 : 2013. 5. 30
 * 수정자 : 오명재
 */
public final class PropertiesUtil {

    /**
     * 객체 생성을 방지하기 위해서 디폴트 생성자를 Private로 선언
     */
    private PropertiesUtil() {
    }

    /**
     * 검색 목록 페이징 사이즈 ComboList 에 표시한 값 (예) All, 10, 20, 50, 100
     */
    public static String getSearchPagingSizeList() {

	String size = "";
	try {
	    WTProperties properties = WTProperties.getLocalProperties();
	    String hostName = properties.getProperty("java.rmi.server.hostname");

	    // 개발서버 hostName.equals("ketplmdev.ket.com")
	    // 운영서버 hostName.equals("plm.ket.com")
	    if (!hostName.equals("plm.ket.com") && !hostName.equals("ketplmdev.ket.com")) {
		size = ConfigImpl.getInstance().getString("search.pagingLocal.size.list");
	    } else {
		size = ConfigImpl.getInstance().getString("search.paging.size.list");
	    }
	} catch (Exception e) {
	    Kogger.error(PropertiesUtil.class, e);
	}

	return size;
    }

    /**
     * 검색 목록 페이징 사이즈 ComboList 에 표시한 값 (예) All, 10, 20, 50, 100
     */
    public static String getSearchPagingSizeNameList() {

	String size = "";
	try {
	    WTProperties properties = WTProperties.getLocalProperties();
	    String hostName = properties.getProperty("java.rmi.server.hostname");

	    // 개발서버 hostName.equals("ketplmdev.ket.com")
	    // 운영서버 hostName.equals("plm.ket.com")
	    if (!hostName.equals("plm.ket.com") && !hostName.equals("ketplmdev.ket.com")) {
		size = ConfigImpl.getInstance().getString("search.pagingLocal.size.list.name");
	    } else {
		size = ConfigImpl.getInstance().getString("search.paging.size.list.name");
	    }
	} catch (Exception e) {
	    Kogger.error(PropertiesUtil.class, e);
	}

	return size;
    }

    /**
     * 검색 목록 그리드의 기본 스타일 지정 (예) ExtJs
     */
    public static String getSearchGridStyle() {

	return ConfigImpl.getInstance().getString("search.grid.style.default");
    }

    /**
     * 검색 목록 기본 페이징 사이즈 (예) 50
     */
    public static String getSearchPagingSizeDefault() {

	String defaultSize = "";
	try {
	    WTProperties properties = WTProperties.getLocalProperties();
	    String hostName = properties.getProperty("java.rmi.server.hostname");

	    // 개발서버 hostName.equals("ketplmdev.ket.com")
	    // 운영서버 hostName.equals("plm.ket.com")
	    if (!hostName.equals("plm.ket.com") && !hostName.equals("ketplmdev.ket.com")) {
		defaultSize = ConfigImpl.getInstance().getString("search.pagingLocal.size.default");
	    } else {
		defaultSize = ConfigImpl.getInstance().getString("search.paging.size.default");
	    }
	} catch (Exception e) {
	    Kogger.error(PropertiesUtil.class, e);
	}

	return defaultSize;
    }

    /**
     * 간트챠트 그리드의 기본 스타일 지정 (예) ExtJs
     */
    public static String getGanttGridStyle() {

	return ConfigImpl.getInstance().getString("gantt.grid.style.default");
    }

    /**
     * 검색 목록 그리드의 쿠키 스타일 지정 (예) 0 - 저장 및 로딩 사용
     */
    public static String getSearchGridCookiesType() {

	return ConfigImpl.getInstance().getString("search.grid.cookies.type");
    }

    /**
     * 검색 목록 그리드의 Row Color 표시 방식 지정 (예) 2 (홀수-짝수 행을 다른 색상으로 표시)
     */
    public static String getSearchGridAlternateType() {

	return ConfigImpl.getInstance().getString("search.grid.alternate.type");
    }

    /**
     * 검색 목록 그리드의 동시 정렬 키 갯수 지정 (예) 1
     */
    public static String getSearchGridMaxSort() {

	return ConfigImpl.getInstance().getString("search.grid.maxsort");
    }

    /**
     * 검색 목록 그리드의 정렬 아이콘 표시 방식 지정 (예) 1
     */
    public static String getSearchGridSortIcons() {

	return ConfigImpl.getInstance().getString("search.grid.sorticons");
    }

    /**
     * 검색 목록 컬럼의 최소 사이즈 지정 (예) 30
     */
    public static String getSearchColMinWidth() {

	return ConfigImpl.getInstance().getString("search.grid.col.minwidth");
    }

    /**
     * 검색 목록 그리드의 링크 색상 지정 (예) #4398BC
     */
    public static String getSearchGridLinkColor() {

	return ConfigImpl.getInstance().getString("search.grid.link.color");
    }

    /**
     * 그리드 검색 결과 제한 건수 설정 (예) 500
     */
    public static int getSearchGridCountLimit() {

	return ConfigImpl.getInstance().getInt("search.grid.count.limit");
    }

    /**
     * 그리드 검색 결과 건수 제한 여부 설정 (예) false
     */
    public static boolean getSearchGridCountLimitFlag() {

	return ConfigImpl.getInstance().getBoolean("search.grid.count.limit.flag");
    }
}
