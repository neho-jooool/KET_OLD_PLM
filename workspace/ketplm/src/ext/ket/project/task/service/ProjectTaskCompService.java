package ext.ket.project.task.service;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import wt.change2.WTChangeOrder2;
import wt.fc.QueryResult;
import wt.method.RemoteInterface;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSTask;
import e3ps.project.ProjectOutput;
import e3ps.project.TaskAssessResult;
import ext.ket.dqm.entity.KETDqmDTO;
import ext.ket.dqm.entity.KETDqmRaise;
import ext.ket.project.gate.entity.GateAssessResult;
import ext.ket.project.task.entity.AssessTaskResultDTO;
import ext.ket.project.task.entity.GateTaskOutputDTO;
import ext.ket.project.task.entity.ProjectTaskDTO;
import ext.ket.project.task.entity.TrustTaskResultDTO;
import ext.ket.shared.service.CommonServiceInterface;

@RemoteInterface
public interface ProjectTaskCompService extends CommonServiceInterface<ProjectTaskDTO, E3PSTask> {

    public E3PSTask completeProjectTaskSchedule(ProjectTaskDTO paramObject) throws Exception;

    public E3PSTask saveCompleteProjectTaskSchedule(ProjectTaskDTO paramObject) throws Exception;

    public List<GateTaskOutputDTO> getProjectOutputList(String taskOid, GateAssessResult ass) throws Exception;

    public void saveTaskOutputResult(String oid, ProjectTaskDTO dto) throws Exception;

    public void saveTaskGateCheckResult(String oid, ProjectTaskDTO dto) throws Exception;

    public void saveTaskQualityResult(String oid, ProjectTaskDTO dto) throws Exception;

    public void saveTaskTrustResult(String oid, ProjectTaskDTO dto) throws Exception;

    // public List<KETDqmDTO> getQualityProblemList(String taskOid, GateAssessResult ass) throws Exception;

    public List<TrustTaskResultDTO> getTaskTrustResultList(String taskOid) throws Exception;

    public List<GateTaskOutputDTO> getProjectGateCheckSheetList(String pjtOid) throws Exception;

    public List<GateTaskOutputDTO> getProjectTaskTotalList(String pjtOid) throws Exception;

    // public List<GateCheckSheetDTO> getProjectTaskGateCheckLinkList(String taskOid, GateAssessResult ass) throws Exception;

    public Hashtable getGateAssessResultInfoList(String taskOid, boolean isList, int gateDegree) throws Exception;

    public GateAssessResult getGateAssessResultObj(String taskOid) throws Exception;

    public String getProjectTryTaskLevel(String taskOid) throws Exception;

    public List<String> getProjectWorkTimeSumList(String projectOid) throws Exception;

    public List<TrustTaskResultDTO> getTaskTrustLevelList(String taskOid) throws Exception;

    public void deleteTaskTrustResult(String oid, ProjectTaskDTO dto) throws Exception;

    public List<E3PSTask> getTaskThreeBefore() throws Exception;

    public List<E3PSTask> getTaskSevenBefore() throws Exception;

    public List<E3PSTask> getTaskPlanEndBefore() throws Exception;

    public List<E3PSTask> getTaskPlanEndDelay() throws Exception;

    public void updateEcoProjectOutputLink(WTChangeOrder2 obj, ProjectOutput output, String type) throws Exception;

    public void updateDqmProjectOutputLink(KETDqmRaise dqm, ProjectOutput out) throws Exception;

    public void completeDqmProjectOutput(KETDqmRaise dqm) throws Exception;

    public KETDqmDTO getOutputQLP(ProjectOutput output) throws Exception;

    public List<GateAssessResult> getProjectLinkAssesResult(E3PSProject pjtObj) throws Exception;

    public GateAssessResult getAssesResultByTask(E3PSTask task) throws Exception;

    public E3PSTask getTask(GateAssessResult assessResult) throws Exception;

    public void saveTaskRelatedAllResult(String oid, ProjectTaskDTO dto) throws Exception;

    public void initiateGateResultLink(String outputLinkDiv, String gateLinkDiv, String dqmLinkDiv) throws Exception;

    public int createDegreeTaskRelatedAllResult(String oid) throws Exception;

    public int getMaxGateDegree(String oid) throws Exception;

    public List<AssessTaskResultDTO> getTaskAssessResultList(String taskOid) throws Exception;

    public E3PSTask getAssessTask(TaskAssessResult assessResult) throws Exception;

    public TaskAssessResult saveTaskAssessResult(String taskOid) throws Exception;

    public void deleteAssessByTask(E3PSTask task) throws Exception;

    public QueryResult getOutPutByTask(E3PSTask task) throws Exception;

    public List<AssessTaskResultDTO> getOnlyTaskAssessResultByTask(String taskOid) throws Exception;

    public E3PSTask syncMainSchedule(E3PSTask task) throws Exception;

    public void makeCostInfoAuto() throws Exception;

    public void saveKqisTrust(Map<String, Object> reqMap) throws Exception;

    public Map<String, Object> addKqisSearchList(Map<String, Object> reqMap) throws Exception;
}
