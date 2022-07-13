package ext.ket.sysif.cost.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import ext.ket.project.cost.entity.KETCostDTO;
import ext.ket.shared.dao.CommonDao;

@Service
public class CostService {
    @Inject
    private CommonDao commonDao;

    /**
     * 원가 시스템에서
     * 
     * @param dto
     * @return
     * @throws Exception
     * @메소드명 : findByProjectNo
     * @작성자 : sw775.park
     * @작성일 : 2014. 10. 29.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @SuppressWarnings("unchecked")
    public List<KETCostDTO> findByProjectNo(KETCostDTO dto) throws Exception {
	return (List<KETCostDTO>) commonDao.find("cost.findByProjectNo", dto);
    }
}
