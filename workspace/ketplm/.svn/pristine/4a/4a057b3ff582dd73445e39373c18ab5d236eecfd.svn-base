package e3ps.bom.dao;

import java.io.UnsupportedEncodingException;
import java.util.Vector;

import wt.inf.container.WTContainerHelper;
import wt.inf.container.WTContainerRef;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleTemplate;
import wt.org.OrganizationServicesHelper;
import wt.org.WTOrganization;
import wt.session.SessionHelper;
import ext.ket.shared.log.Kogger;

public class BOMAppletDao 
{
	public BOMAppletDao(){}
	
	public Vector getBOMState() throws UnsupportedEncodingException
	{
		Vector stateVec = new Vector();
		try
		{
			WTOrganization wtOrg = OrganizationServicesHelper.manager.getOrganization(SessionHelper.manager.getPrincipal());
			WTContainerRef containerRef = WTContainerHelper.service.getByPath("/wt.inf.container.OrgContainer=" + wtOrg.getName() + "/wt.inf.library.WTLibrary=BOM");
			// 신규  BOM LifeCycle 상태
			LifeCycleTemplate lifeCycle = LifeCycleHelper.service.getLifeCycleTemplate("KET_COMMON_LC", containerRef);
			stateVec = LifeCycleHelper.service.findStates(lifeCycle);
		}
		catch(Exception ex)
		{
			Kogger.error(getClass(), ex);
		}
        
        return stateVec;	
	}

	public Vector getBOMECOState() throws UnsupportedEncodingException
	{
		Vector bomEcoStateVec = new Vector();
		try
		{
			WTOrganization wtOrg = OrganizationServicesHelper.manager.getOrganization(SessionHelper.manager.getPrincipal());
			WTContainerRef containerRef = WTContainerHelper.service.getByPath("/wt.inf.container.OrgContainer=" + wtOrg.getName() + "/wt.inf.library.WTLibrary=BOM");
	
			// BOM ECO LifeCycle 상태
			LifeCycleTemplate bomEcoLifeCycle = LifeCycleHelper.service.getLifeCycleTemplate("KET_ECO_LC", containerRef);
			bomEcoStateVec = LifeCycleHelper.service.findStates(bomEcoLifeCycle);
		}
		catch(Exception ex)
		{
			Kogger.error(getClass(), ex);
		}		
        
        return bomEcoStateVec;	
	}

}
