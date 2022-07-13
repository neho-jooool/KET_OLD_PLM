package ext.ket.project.gate.service;

import wt.method.RemoteInterface;
import ext.ket.project.gate.entity.GateCheckSheet;
import ext.ket.project.gate.entity.GateCheckSheetDTO;
import ext.ket.shared.service.CommonServiceInterface;

@RemoteInterface
public interface GateCheckSheetService extends CommonServiceInterface<GateCheckSheetDTO, GateCheckSheet> {

    public GateCheckSheet saveProjectGate(String pjtObjOid, int ordNo, GateCheckSheetDTO paramObject) throws Exception;

    public GateCheckSheet updateProjectGate(String pjtObjOid, int ordNo, GateCheckSheetDTO paramObject) throws Exception;

    public void deleteGateCheckSheet(GateCheckSheet gateCheck) throws Exception;
}
