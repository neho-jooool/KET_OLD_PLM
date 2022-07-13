package ext.ket.edm.cad2bom.tree.dto;

import java.util.ArrayList;
import java.util.List;

import wt.epm.EPMDocumentMaster;
import wt.part.WTPartMaster;
import ext.ket.edm.cad2bom.tree.TreeDTO;

final public class Cad2BomDTO extends TreeDTO {
    
    private List<WTPartMaster> anotherWTPartMasterList = new ArrayList<WTPartMaster>();
    private List<EPMDocumentMaster> anotherEPMDocumentMasterList = new ArrayList<EPMDocumentMaster>();

    public void addAnotherWTPartMaster(WTPartMaster anotherWTPartMaster) {
	anotherWTPartMasterList.add(anotherWTPartMaster);
    }

    public void addAnotherEPMDocumentMaster(EPMDocumentMaster anotherEPMDocumentMaster) {
	anotherEPMDocumentMasterList.add(anotherEPMDocumentMaster);
    }

    public List<WTPartMaster> getAnotherWTPartMasterList() {
	return anotherWTPartMasterList;
    }

    public void setAnotherWTPartMasterList(List<WTPartMaster> anotherWTPartMasterList) {
	this.anotherWTPartMasterList = anotherWTPartMasterList;
    }

    public List<EPMDocumentMaster> getAnotherEPMDocumentMasterList() {
	return anotherEPMDocumentMasterList;
    }

    public void setAnotherEPMDocumentMasterList(List<EPMDocumentMaster> anotherEPMDocumentMasterList) {
	this.anotherEPMDocumentMasterList = anotherEPMDocumentMasterList;
    }

}
