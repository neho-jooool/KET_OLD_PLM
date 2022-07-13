package ext.ket.shared.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import e3ps.common.web.PageControl;
import ext.ket.shared.dto.BaseDTO;

/**
 * EJS Treegrid를 위한 Data 변환을 위한 Util
 * 
 * @클래스명 : EjsConverUtil
 * @작성자 : sw775.park
 * @작성일 : 2014. 6. 5.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
public class EjsConverUtil {

    /**
     * EJS Treegrid를 위한 Data 변환
     * 
     * @param dtoList
     * @return
     * @throws Exception
     * @메소드명 : convertToDto
     * @작성자 : sw775.park
     * @작성일 : 2014. 6. 5.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Map<String, Object> convertToDto(List<?> dtoList) throws Exception {
	List items = new ArrayList();
	if (dtoList != null && dtoList.size() > 0) {
	    items.add(dtoList);
	} else {
	    items.add("");
	}

	Map<String, Object> grid = new HashMap<String, Object>();
	grid.put("Body", items);
	return grid;
    }

    /**
     * EJS Treegrid를 위한 Paging Data 변환
     * 
     * @param dtoList
     * @param pageControl
     * @return
     * @throws Exception
     * @메소드명 : convertToDto
     * @작성자 : sw775.park
     * @작성일 : 2014. 6. 5.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Map<String, Object> convertToDto(List<?> dtoList, PageControl pageControl) throws Exception {
	Map<String, Object> pos = new HashMap<String, Object>();
	// 현재 Page번호
	pos.put("Pos", pageControl.getCurrentPage() - 1);
	// 페이지별 rows count
	pos.put("Count", (pageControl.getTotalCount() == 0) ? 0 : pageControl.getInitPerPage());
	// 실제 표시될 item list
	pos.put("Items", setRowNum(dtoList, pageControl.getTopListCount()));

	// B tag object를 위한 배열 생성
	List bList = new ArrayList();
	bList.add(pos);
	for (int pageNo = 2; pageNo <= pageControl.getTotalPage(); pageNo++) {
	    Map<String, Object> pagePos = new HashMap<String, Object>();
	    // 마지막 페이지의 개수
	    if (pageNo == pageControl.getTotalPage()) {
		PageControl nextPage = new PageControl(pageNo, pageControl.getInitFormPage(), pageControl.getInitPerPage(), pageControl.getTotalCount());
		pagePos.put("Count", nextPage.getTopListCount());
	    } else {
		pagePos.put("Count", pageControl.getInitPerPage());
	    }
	    bList.add(pagePos);
	}

	// 전체 개수를 위한 Cfg Object
	Map<String, Object> cfg = new HashMap<String, Object>();
	cfg.put("RootCount", pageControl.getTotalCount());
	// Body Object
	Map<String, Object> body = new HashMap<String, Object>();
	body.put("Body", bList);
	body.put("Cfg", cfg);

	return body;
    }

    /**
     * EJS Treegrid를 위한 Paging Data 변환
     * 
     * @param pageControl
     * @return
     * @throws Exception
     * @메소드명 : convertToDto
     * @작성자 : sw775.park
     * @작성일 : 2014. 11. 19.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public static Map<String, Object> convertToDto(PageControl pageControl) throws Exception {
	return EjsConverUtil.convertToDto(pageControl.getResltList(), pageControl);
    }

    /**
     * EJS Upload 후 return 값
     * 
     * @param 0:success, -99:fail
     * @return
     * @throws Exception
     * @메소드명 : sendResult
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 25.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public static Map<String, Object> sendResult(String result) throws Exception {
	Map<String, Object> grid = new HashMap<String, Object>();
	Map<String, Object> io = new HashMap<String, Object>();
	Map<String, Object> resultMap = new HashMap<String, Object>();
	resultMap.put("Result", result);
	io.put("IO", resultMap);
	grid.put("Grid", io);
	return io;
    }

    /**
     * 
     * @param dtoList
     * @param startIndex
     * @return
     * @throws Exception
     * @메소드명 : setRowNum
     * @작성자 : sw775.park
     * @작성일 : 2014. 6. 30.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @SuppressWarnings("unchecked")
    private static List<?> setRowNum(List<?> dtoList, int startIndex) throws Exception {
	if (dtoList != null && dtoList.size() > 0) {
	    for (Object dto : dtoList) {
	        if(dto instanceof BaseDTO) {
	            BaseDTO baseDTO = (BaseDTO) dto;
	            baseDTO.setRowNum(startIndex--);
	        }else if(dto instanceof Map){
	            Map<String, Object> map = (Map<String, Object>) dto;
	            map.put("rowNum", startIndex--);
	        }
	    }
	}
	return dtoList;
    }
}
