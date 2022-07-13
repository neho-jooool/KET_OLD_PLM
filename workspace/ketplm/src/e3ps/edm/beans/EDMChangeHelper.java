package e3ps.edm.beans;

import java.util.ArrayList;

import wt.epm.EPMDocument;
import wt.fc.PersistenceHelper;
import wt.part.WTPart;
import e3ps.edm.util.EDMProperties;
import e3ps.edm.util.VersionHelper;

public class EDMChangeHelper {

	/**
	 * 관련 ECAD 도면 조회.
	 * PCB,Schematic,AutoCAD
	 * @param epm
	 * @return ArrayList
	 * @throws Exception
	 */
	public static ArrayList getAssociatedDocsByECAD(EPMDocument epm) throws Exception {
		
		if(!VersionHelper.isLatestRevision(epm)) {
			throw new Exception("도면("+epm.getNumber()+")은 최신 버전이 아닙니다.");
		}
		
		String category = EDMHelper.getCategory(epm);
		if("ECAD_DRAWING".equals(category)) {
			return new ArrayList();
		}
		
		String referenceType = EDMProperties.getInstance().getReferenceType(category);
		ArrayList parts = EDMHelper.getReferencedByParts(epm,referenceType,EDMHelper.REQUIRED_STANDARD);
		
		WTPart part = null;
		if( (parts != null) && (parts.size() > 0) ) {
			part = (WTPart)((Object[])parts.get(0))[1];
		}
		
		if(part == null) {
			return new ArrayList();
		}
		
		
		ArrayList result = new ArrayList();
		
		ArrayList ecads = EDMHelper.getAssociatedDocsByECAD(epm, part);
		for(int i = 0; i < ecads.size(); i++) {
			EPMDocument ecad = (EPMDocument)ecads.get(i);
			
			if(ecad.getNumber().equals(epm.getNumber())) { //if(PersistenceHelper.isEquivalent(ecad, epm)) {
				continue;
			}
			if(!VersionHelper.isLatestRevision(ecad)) {
				continue;
			}
			
			result.add(ecad);
			
		}
		return result;
	}
}
