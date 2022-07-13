package ext.ket.part.base.service.internal;

import wt.epm.EPMDocument;
import wt.epm.workspaces.EPMAsStoredConfigSpec;
import wt.fc.ObjectReference;
import wt.fc.QueryResult;
import wt.representation.Representation;
import wt.representation.RepresentationHelper;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.config.ConfigSpec;
import wt.vc.config.LatestConfigSpec;

import com.ptc.wvs.common.ui.Publisher;

import ext.ket.shared.log.Kogger;

public class VieawbleGenerator {

    public boolean reGenerator(EPMDocument epmDoc) throws Exception {

	try {
	    
	    if(epmDoc == null)
		return false;
	    
	    String docNum = epmDoc.getNumber();
	    String docVer = epmDoc.getVersionIdentifier().getValue();
	    String docIter = epmDoc.getIterationIdentifier().getValue();
	    // 대상 확인
	    Kogger.debug(getClass(), "Target:" + docNum + "|" + docVer + "|" + docIter);

	    // default가 아니면 삭제
	    QueryResult result = RepresentationHelper.service.getRepresentations(epmDoc);
	    if (result != null && result.size() > 0) {
		
		while (result.hasMoreElements()) {

		    Representation rep = (Representation) result.nextElement();
		    RepresentationHelper.service.deleteRepresentation(rep);
		}
		
		return publishEpm(epmDoc);
		
	    } else {
		
		return publishEpm(epmDoc);
	    }

	} catch (Exception e) {
	    
	    Kogger.error(getClass(), e);

	    return false;
	    
	} finally {

	}
    }
    
    public boolean newGenerator(EPMDocument epmDoc) throws Exception {

	try {
	    
	    if(epmDoc == null)
		return false;
	    
	    String docNum = epmDoc.getNumber();
	    String docVer = epmDoc.getVersionIdentifier().getValue();
	    String docIter = epmDoc.getIterationIdentifier().getValue();
	    // 대상 확인
	    Kogger.debug(getClass(), "Target:" + docNum + "|" + docVer + "|" + docIter);

	    QueryResult result = RepresentationHelper.service.getRepresentations(epmDoc);
	    if (result != null && result.size() > 0) {
		return false;
	    } else {
		
		return publishEpm(epmDoc);
	    }

	} catch (Exception e) {
	    
	    Kogger.error(getClass(), e);

	    return false;
	    
	} finally {

	}
    }
    
    private boolean publishEpm(EPMDocument epmdoc) throws WTException, WTPropertyVetoException{
	
	if(epmdoc == null) return false;
	
	ConfigSpec ketCongig = null;
	
	boolean latestFirst = true;
	String cadname = epmdoc.getCADName();
	if(cadname != null && cadname.toLowerCase().endsWith(".drw")){
	    latestFirst = false;
	}
	
	if(latestFirst){
	    ketCongig = new LatestConfigSpec();
	}else{
	    ketCongig = EPMAsStoredConfigSpec.newEPMAsStoredConfigSpec((EPMDocument) epmdoc);
	}
	
	   publishOneItem(epmdoc, ketCongig);
	   return true;
    }
	
    private boolean publishOneItem(EPMDocument epmdoc, ConfigSpec asStoredConfigSpec) throws WTException, WTPropertyVetoException{   
	
	boolean viewableLink = true;
	boolean forceRepublish = false;
	String objectReference = ObjectReference.newObjectReference(epmdoc).toString();
	Kogger.debug("Representable Ref: " + objectReference);
	ConfigSpec partConfigSpec = null;
	boolean defaultRep = true;
	String repName = "ketDefault";
	String repDescription = "ketScheduler";
	int structureType = Publisher.EPM;
	String actionString = null;
	int jobSource = 0;

	Publisher publisher = new Publisher();
	boolean publishState = publisher.doPublish(viewableLink, forceRepublish, objectReference, asStoredConfigSpec, partConfigSpec, defaultRep, repName, repDescription, structureType, actionString,
	        jobSource);
	
	if (publishState) {
	    Kogger.debug(getClass(), "Representation Link: " + publisher.getViewableLink() + " OR: " + publisher.getViewableObjRef());
	}
	
	return publishState;
    }

}
