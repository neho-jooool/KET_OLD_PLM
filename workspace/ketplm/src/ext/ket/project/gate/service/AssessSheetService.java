package ext.ket.project.gate.service;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import wt.method.RemoteInterface;
import wt.util.WTException;
import e3ps.project.E3PSProject;
import ext.ket.project.gate.entity.AssessSheet;
import ext.ket.project.gate.entity.AssessSheetDTO;
import ext.ket.project.gate.entity.GateCheckSheet;
import ext.ket.project.gate.entity.GateCheckSheetDTO;
import ext.ket.project.gate.entity.TemplateAssessSheet;
import ext.ket.shared.service.CommonServiceInterface;

@RemoteInterface
public interface AssessSheetService extends CommonServiceInterface<AssessSheetDTO, AssessSheet> {

    public List<GateCheckSheet> findGateCheckSheet(String pjtOid, GateCheckSheetDTO paramObject) throws Exception;

    public AssessSheet findLinkedAssessSheet(String pjtOid) throws Exception;

    public List<GateCheckSheet> registTemplateAssessSheet(E3PSProject proj, TemplateAssessSheet tmplAssess) throws Exception;

    public HashMap<String, String> getProjectAssessHeadInfo(String pjtOid, GateCheckSheetDTO paramObject) throws Exception;

    public AssessSheet getAssessSheetFromPjtOid(String pjtOid) throws Exception;

    public AssessSheet reviseAssessSheetVersion(Hashtable hash, AssessSheetDTO dto) throws WTException;

    public List<AssessSheet> findRevisedProjectAssess(AssessSheetDTO paramObject) throws Exception;

    public List<GateCheckSheet> findViewVerGateCheckSheet(String oid, String versionNo, GateCheckSheetDTO paramObject) throws Exception;
}
