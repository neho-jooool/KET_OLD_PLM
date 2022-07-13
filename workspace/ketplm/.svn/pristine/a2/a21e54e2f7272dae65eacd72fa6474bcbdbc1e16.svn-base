package ext.ket.project.trycondition.service;

import java.util.List;
import java.util.Map;

import wt.method.RemoteInterface;
import e3ps.project.E3PSProject;
import e3ps.project.MoldProject;
import ext.ket.project.trycondition.entity.KETTryCondition;
import ext.ket.project.trycondition.entity.TryConditionDTO;
import ext.ket.shared.service.CommonServiceInterface;

@RemoteInterface
public interface TryConditionService extends CommonServiceInterface<TryConditionDTO, KETTryCondition> {

    /**
     * 프로젝트에 등록된 cavity를 반환한다.(Mold만 해당)
     * 
     * @param project
     * @return
     * @throws Exception
     * @메소드명 : getCavityByProject
     * @작성자 : sw775.park
     * @작성일 : 2014. 10. 15.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public String getCavityByProject(MoldProject project) throws Exception;

    /**
     * Max Sub ver을 반환 한다.
     * 
     * @param paramObject
     * @return
     * @throws Exception
     * @메소드명 : getMaxSubVer
     * @작성자 : sw775.park
     * @작성일 : 2014. 9. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public int getMaxSubVer(TryConditionDTO paramObject) throws Exception;

    /**
     * 프로젝트에 등록된 Try 조건표 조회(Try조건표 복사 팝업에서 사용)
     * 
     * @param projectOid
     * @return
     * @throws Exception
     * @메소드명 : getTryConditionByProject
     * @작성자 : sw775.park
     * @작성일 : 2014. 9. 27.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public List<TryConditionDTO> getTryConditionByProject(String projectOid) throws Exception;

    /**
     * 금형Rank 수정(S일 경우만)
     * 
     * @param project
     * @throws Exception
     * @메소드명 : changeMoldRankByProject
     * @작성자 : sw775.park
     * @작성일 : 2014. 10. 30.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public void changeMoldRankByProject(E3PSProject project) throws Exception;

    /**
     * 
     * 
     * @param oid
     * @return
     * @throws Exception
     * @메소드명 : getTryPartList
     * @작성자 : admin
     * @작성일 : 2020. 3. 4.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public List<Map<String, Object>> getTryPartList(String oid) throws Exception;
}
