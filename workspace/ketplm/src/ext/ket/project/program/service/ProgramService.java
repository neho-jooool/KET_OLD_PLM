package ext.ket.project.program.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wt.method.RemoteInterface;
import e3ps.project.ProductProject;
import e3ps.project.outputtype.ModelPlan;
import ext.ket.project.program.entity.KETProgramProjectLink;
import ext.ket.project.program.entity.KETProgramSchedule;
import ext.ket.project.program.entity.ProgramDTO;
import ext.ket.project.program.entity.ProgramEventDTO;
import ext.ket.project.program.entity.ProgramNotifyDTO;
import ext.ket.project.program.entity.ProgramProjectDTO;
import ext.ket.shared.service.CommonServiceInterface;

@RemoteInterface
public interface ProgramService extends CommonServiceInterface<ProgramDTO, KETProgramSchedule> {
    /**
     * 프로그램 이력 비교
     * 
     * @param paramObjetDTO
     * @return
     * @throws Exception
     * @메소드명 : compareNotifyHistory
     * @작성자 : sw775.park
     * @작성일 : 2014. 8. 1.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public List<Map<String, String>> compareNotifyHistory(ProgramNotifyDTO paramObjet) throws Exception;

    /**
     * 프로그램 공지
     * 
     * @param paramObject
     * @return
     * @throws Exception
     * @메소드명 : createNotice
     * @작성자 : sw775.park
     * @작성일 : 2014. 8. 2.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public KETProgramSchedule createNotice(ProgramDTO paramObject) throws Exception;

    /**
     * 차종별 event 일정
     * 
     * @param dto
     * @return
     * @throws Exception
     * @메소드명 : findEventByCarType
     * @작성자 : sw775.park
     * @작성일 : 2014. 7. 24.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public List<ProgramEventDTO> findEventByCarType(String carTypeOid) throws Exception;

    /**
     * 프로그램에 등록된 event 일정 리스트
     * 
     * @param programOid
     * @return
     * @throws Exception
     * @메소드명 : findProgramEventByCarType
     * @작성자 : sw775.park
     * @작성일 : 2014. 8. 4.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public List<ProgramEventDTO> findEventByProgram(String programOid) throws Exception;

    /**
     * 프로그램 공지 이력 정보
     * 
     * @param paramObject
     * @return
     * @throws Exception
     * @메소드명 : findNotifyHistoryList
     * @작성자 : sw775.park
     * @작성일 : 2014. 8. 1.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public List<ProgramNotifyDTO> findNotifyHistoryList(ProgramDTO paramObject) throws Exception;

    /**
     * 프로젝트의 프로그램 조회
     * 
     * @param projectOid
     * @return
     * @throws Exception
     * @메소드명 : findProgramByProject
     * @작성자 : sw775.park
     * @작성일 : 2014. 9. 4.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public List<ProgramDTO> findProgramByProject(String projectOid) throws Exception;

    /**
     * 프로그램에 등록된 프로젝트 정보 리스트
     * 
     * @param programOid
     * @return
     * @throws Exception
     * @메소드명 : findProjectByProgram
     * @작성자 : sw775.park
     * @작성일 : 2014. 8. 4.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public List<ProgramProjectDTO> findProjectByProgram(String programOid) throws Exception;

    /**
     * 프로젝트의 맵핑된 프로그램의 관련 프로젝트 조회
     * 
     * @param projectOid
     * @return
     * @throws Exception
     * @메소드명 : findProjectByProject
     * @작성자 : sw775.park
     * @작성일 : 2014. 9. 4.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public List<ProgramProjectDTO> findProjectByProject(String projectOid) throws Exception;

    /**
     * 프로그램 Event 일정 변경 체크
     * 
     * @param programSchedule
     * @param paramObject
     * @return
     * @throws Exception
     * @메소드명 : hasChangedEvent
     * @작성자 : sw775.park
     * @작성일 : 2014. 8. 25.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public boolean hasChangedEvent(KETProgramSchedule programSchedule, ProgramDTO paramObject) throws Exception;

    /**
     * 변경된 프로그램 일정이 있는지 체크하는 method
     * 
     * @param projectOid
     * @return
     * @throws Exception
     * @메소드명 : hasChangedProgramEvent
     * @작성자 : sw775.park
     * @작성일 : 2014. 9. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public boolean hasChangedProgramEvent(String projectOid) throws Exception;

    /**
     * 기준 프로그램 등록
     * 
     * @param programOid
     * @param projectLinkOid
     * @return
     * @throws Exception
     * @메소드명 : modifyBaseProgram
     * @작성자 : sw775.park
     * @작성일 : 2014. 9. 4.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    KETProgramProjectLink modifyBaseProgram(String programOid, String projectLinkOid) throws Exception;

    /**
     * 프로그램 일정 확인
     * 
     * @param projectLinkOid
     * @return
     * @throws Exception
     * @메소드명 : modifyCheckedEvent
     * @작성자 : sw775.park
     * @작성일 : 2014. 9. 4.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public KETProgramProjectLink modifyCheckedEvent(String projectLinkOid) throws Exception;

    /**
     * 프로그램의 프로젝트 link 정보 저장
     * 
     * @param paramObject
     * @param programSchedule
     * @throws Exception
     * @메소드명 : saveProjectLink
     * @작성자 : sw775.park
     * @작성일 : 2014. 8. 4
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public void saveProjectLink(ProgramDTO paramObject, KETProgramSchedule programSchedule) throws Exception;

    /**
     * 프로그램의 프로젝트 link 정보 저장
     * 
     * @param paramObject
     * @throws Exception
     * @메소드명 : saveProjectLinkByProject
     * @작성자 : sw775.park
     * @작성일 : 2014. 9. 4.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public void saveProjectLinkByProject(ProgramDTO paramObject) throws Exception;

    /**
     * 기준 프로그램
     * 
     * @param programDTO
     * @return
     * @throws Exception
     * @메소드명 : updateProgramProjectLinkOfBaseProgram
     * @작성자 : sw775.park
     * @작성일 : 2014. 10. 20.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public void updateProgramProjectLinkOfBaseProgram(ProgramDTO programDTO) throws Exception;

    /**
     * 일정 확인
     * 
     * @param programDTO
     * @return
     * @throws Exception
     * @메소드명 : updateProgramProjectLinkOfChangedEvent
     * @작성자 : sw775.park
     * @작성일 : 2014. 10. 20.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public void updateProgramProjectLinkOfChangedEvent(ProgramDTO programDTO) throws Exception;

    /**
     * 프로그램의 상태 업데이트
     * 
     * @param project
     * @throws Exception
     * @메소드명 : updateProgramState
     * @작성자 : sw775.park
     * @작성일 : 2014. 11. 4.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public void updateProgramState(ProductProject project) throws Exception;

    /**
     * 차종의 ModelPlan 객체 반환
     * 
     * @param carTypeOid
     * @return
     * @throws Exception
     * @메소드명 : getModelPlanByCarType
     * @작성자 : sw775.park
     * @작성일 : 2014. 11. 14.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public ModelPlan getModelPlanByCarType(String carTypeOid) throws Exception;
    
    /**
     * 프로그램의 접점고객 P1,P2,SOP 날짜 반환
     * 
     * @param projectOid
     * @return
     * @throws Exception
     * @메소드명 : findContactEventByproject
     * @작성자 : 황정태
     * @작성일 : 2017. 5. 23.
     * @설명 : 
     * @수정이력 - 수정일,수정자,수정내용  					   
     *
     */
    public List<HashMap<String, String>> findContactEventByproject(String projectOid) throws Exception;
}
