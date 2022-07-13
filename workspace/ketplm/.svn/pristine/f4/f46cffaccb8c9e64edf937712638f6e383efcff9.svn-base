package ext.ket.project.gate.service;

import java.util.List;

import wt.method.RemoteInterface;
import ext.ket.project.gate.entity.TemplateGateCheckSheet;
import ext.ket.project.gate.entity.TemplateGateCheckSheetDTO;
import ext.ket.shared.service.CommonServiceInterface;

@RemoteInterface
public interface TemplateGateCheckSheetService extends CommonServiceInterface<TemplateGateCheckSheetDTO, TemplateGateCheckSheet> {

    public List<TemplateGateCheckSheet> findGateCheckSheet(String pjtOid, TemplateGateCheckSheetDTO paramObject) throws Exception;

    public TemplateGateCheckSheet saveProjectGate(String pjtObjOid, int ordNo, TemplateGateCheckSheetDTO paramObject) throws Exception;

    public TemplateGateCheckSheet updateProjectGate(String pjtObjOid, int ordNo, TemplateGateCheckSheetDTO paramObject) throws Exception;

    public void deleteGateCheckSheet(TemplateGateCheckSheet gateCheck) throws Exception;
}
