package ext.ket.edm.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import e3ps.common.util.StringUtil;
import e3ps.common.web.PageControl;
import ext.ket.edm.entity.dto.KETDrawingDistReqDTO;
import ext.ket.shared.dao.CommonDao;

@Service
public class DrawingDistFindService {
    @Inject
    private CommonDao dao;

    @SuppressWarnings("unchecked")
    public PageControl findPagingSql(KETDrawingDistReqDTO ketDrawingDistReqDTO) throws Exception {
	// TODO Auto-generated method stub
	String getDrawingDistDeptArray = StringUtil.checkNull(ketDrawingDistReqDTO.getDrawingDistDeptArray());
	if (!getDrawingDistDeptArray.equals(null) && !getDrawingDistDeptArray.equals("")) {
	    ketDrawingDistReqDTO.setDrawingDistDeptCodeArray(ketDrawingDistReqDTO.getDrawingDistDeptArray().split(","));
	}

	if (ketDrawingDistReqDTO.getSortName() != null && ketDrawingDistReqDTO.getSortName() != "") {
	    if (ketDrawingDistReqDTO.getSortName().startsWith("-")) {
		ketDrawingDistReqDTO.setSortName(ketDrawingDistReqDTO.getSortName().substring(1));
		ketDrawingDistReqDTO.setSortType("DESC");
	    } else {
		ketDrawingDistReqDTO.setSortType("ASC");
	    }
	}

	ketDrawingDistReqDTO.searchCondition();

	List<KETDrawingDistReqDTO> rsltList = dao.findPaging("drawingDistRequest.pagingList", ketDrawingDistReqDTO,
	        ketDrawingDistReqDTO.getPage() + 1, ketDrawingDistReqDTO.getFormPage());

	int totalCount = dao.getTotalRecordCount("drawingDistRequest.totalPageCount", ketDrawingDistReqDTO);
	PageControl pageControl = new PageControl(ketDrawingDistReqDTO.getPage() + 1, rsltList, ketDrawingDistReqDTO.getFormPage(),
	        ketDrawingDistReqDTO.getFormPage(), totalCount);

	return pageControl;
    }
}
