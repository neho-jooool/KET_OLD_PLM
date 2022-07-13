package e3ps.edm.beans;

import java.util.ArrayList;
import java.util.Vector;

import wt.epm.EPMApplicationType;
import wt.epm.EPMAuthoringAppType;
import wt.epm.EPMDocument;
import wt.epm.EPMDocumentType;
import e3ps.edm.util.EDMProperties;
import ext.ket.shared.log.Kogger;

public class EDMPBOHelper {

	
	public static EPMDocument getPrimaryBusinessObject(EPMDocument epm) {
		
		try {
			EDMProperties edmProperties = EDMProperties.getInstance();
			
			EPMApplicationType appType = epm.getOwnerApplication();
			EPMAuthoringAppType authType = epm.getAuthoringApplication();
			EPMDocumentType docType = epm.getDocType();
			
			//OOTB인 경우
			//if(edmProperties.isAppTypeByPLM(epm.getOwnerApplication().toString())) {
			//	return null;
			//}
			
			//WGM인 경우만
			if(!"EPM".equals(appType.toString())) {
				return null;
			}
			
			if( !("CADDRAWING".equals(docType.toString()) ||
					"CADASSEMBLY".equals(docType.toString()) ||
					"CADCOMPONENT".equals(docType.toString())) ) {
				return null;
			}
			
			String category = EDMHelper.getCategory(epm);
			if(category.length()==0) {
				return null;
			}
			
			
			EPMDocument m = null;
			if("CADDRAWING".equals(docType.toString())) {
				Vector vv = EDMHelper.getReferences(epm, true);
				for(int i = 0; i < vv.size(); i++) {
					m = (EPMDocument)vv.get(i);
				}
				
			} else {
				m = epm;
			}
			
			if(m == null) {
				return null;
			}
			
			
			//제품도면/사출금형/프레스금형
			
			/*
			 * 1. 개발검토도면-관련3D모델인 경우
			 * 2. 제품도면-대표/관련 3D 모델인 경우
			 * 3. 사출금형-대표/관련 3D 모델인 경우
			 * 4. 사출금형SET도면-대표/관련 3D 모델인 경우
			 * ...
			 */
			
			ArrayList refedDrawings = EDMHelper.getReferenceEPMs(m, -1);
			EPMDocument drawing = null;
			for(int i = 0; i < refedDrawings.size(); i++) {
				drawing = (EPMDocument)((Object[])refedDrawings.get(i))[1];
			}
			
			return drawing;
		}
		catch(Exception e) {
			Kogger.error(EDMPBOHelper.class, e);
		}
		return null;
	}
	
}
