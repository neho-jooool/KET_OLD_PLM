package ext.ket.sample.service;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import e3ps.common.web.PageControl;
import ext.ket.sample.entity.SampleDTO;
import ext.ket.sample.entity.SampleRequestDTO;
import ext.ket.shared.dao.CommonDao;

@Service
public class SampleDBService {
    @Inject
    private CommonDao commonDao;

    @SuppressWarnings("unchecked")
    public List<SampleDTO> findPanging(SampleDTO sampleDTO) throws Exception {
	return commonDao.findPaging("sample.pagingList", sampleDTO, sampleDTO.getPage(), sampleDTO.getFormPage());
    }

    @SuppressWarnings("unchecked")
    public PageControl sampleRequsetfindPagingSql(SampleRequestDTO sampleRequestDTO) throws Exception {
	// TODO Auto-generated method stub
	String checkPartOid = "";
	String checkDevelCode = "";
	checkPartOid = Arrays.toString(sampleRequestDTO.getRequstPartOidArr());
	checkDevelCode = Arrays.toString(sampleRequestDTO.getDevelopeStageCodeArr());

	sampleRequestDTO.setCheckDevelCode(checkDevelCode);
	sampleRequestDTO.setCheckPartOid(checkPartOid);

	if (sampleRequestDTO.getSortName() != null && sampleRequestDTO.getSortName() != "") {
	    if (sampleRequestDTO.getSortName().startsWith("-")) {
		sampleRequestDTO.setSortName(sampleRequestDTO.getSortName().substring(1));
		sampleRequestDTO.setSortType("DESC");
	    } else {
		sampleRequestDTO.setSortType("ASC");
	    }
	}

	sampleRequestDTO.searchCondition();

	List<SampleRequestDTO> rsltList = commonDao.findPaging("sample.sampleRequsetPagingList", sampleRequestDTO,
	        sampleRequestDTO.getPage() + 1, sampleRequestDTO.getFormPage());

	int totalCount = commonDao.getTotalRecordCount("sample.sampleRequsetTotalPageCount", sampleRequestDTO);
	PageControl pageControl = new PageControl(sampleRequestDTO.getPage() + 1, rsltList, sampleRequestDTO.getFormPage(),
	        sampleRequestDTO.getFormPage(), totalCount);

	return pageControl;
    }
}
