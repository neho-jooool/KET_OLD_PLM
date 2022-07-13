package e3ps.common.util;

import java.beans.PropertyVetoException;
import java.net.URL;
import java.util.Vector;

import wt.content.ApplicationData;
import wt.content.ContentHelper;
import wt.content.ContentHolder;
import wt.content.ContentItem;
import wt.content.FormatContentHolder;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.inf.container.ExchangeContainer;
import wt.inf.container.WTContainerRef;
import wt.lifecycle.LifeCycleException;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleTemplate;
import wt.org.OrganizationServicesHelper;
import wt.org.WTPrincipalReference;
import wt.pdmlink.PDMLinkProduct;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;
import wt.vc.VersionControlHelper;
import wt.vc.Versioned;
import wt.workflow.engine.WfEngineHelper;
import wt.workflow.engine.WfProcess;
import wt.workflow.engine.WfState;
import wt.workflow.work.WfAssignedActivity;
import wt.workflow.work.WorkItem;
import ext.ket.shared.log.Kogger;

public class WCUtil
{
    private static ReferenceFactory rf;

    /**
     * @since 2007/05/27
     * @author kang ho chul
     * @see
     * @return Vector - description: Lifecycle state 를 반환한다
     * 
     */
    public static Vector getState(String lifeCycle) throws WTException
    {
    
        Vector stateVec = null;

        WTContainerRef wtContainerRef = null;
        try
        {
            wtContainerRef = getWTContainerRef();
        }
        catch (Exception e)
        {

            Kogger.error(WCUtil.class, e);
            throw new WTException(e);
        }

        if (wtContainerRef != null)
        {
            try
            {
                LifeCycleTemplate lct = LifeCycleHelper.service.getLifeCycleTemplate(lifeCycle, wtContainerRef);
                stateVec = LifeCycleHelper.service.findStates(lct);

            }
            catch (LifeCycleException e)
            {
                Kogger.error(WCUtil.class, e);
                throw new WTException(e);
            }
        }

        return stateVec;
    }

    /**
     * @since 2007/05/27
     * @author kang ho chul
     * @see
     * @return WTPrincipalReference - description: WTPrincipalReference 를 반환한다
     * 
     */
    public static WTPrincipalReference getWTPrincipalReference(String id)
    {

        WTPrincipalReference wtpr = null;
        try
        {
            wtpr = OrganizationServicesHelper.manager.getPrincipalReference(id, OrganizationServicesHelper.manager.getDefaultDirectoryService());
        }
        catch (Exception e)
        {
            Kogger.error(WCUtil.class, e);
        }
        return wtpr;
    }

    /**
     * @since 2007/05/27
     * @author kang ho chul
     * @see
     * @return WTContainerRef - description: WTContainerRef 를 반환한다
     * 
     */
    public static WTContainerRef getWTContainerRef() throws Exception
    {

        PDMLinkProduct wtProduct = getPDMLinkProduct();
        WTContainerRef wtContainerRef = null;
        if (wtProduct != null)
        {
            wtContainerRef = WTContainerRef.newWTContainerRef(wtProduct);
        }

        return wtContainerRef;
    }

    /**
     * @since 2007/05/27
     * @author kang ho chul
     * @see
     * @return PDMLinkProduct - description: PDMLinkProduct 를 반환한다
     * 
     */
   
    public static PDMLinkProduct getPDMLinkProduct() throws Exception
    {
        QuerySpec qs = new QuerySpec(PDMLinkProduct.class);
        SearchCondition sc1 = new SearchCondition(PDMLinkProduct.class, PDMLinkProduct.NAME, SearchCondition.EQUAL, "KET");
        qs.appendSearchCondition(sc1);
        QueryResult results = (QueryResult) PersistenceHelper.manager.find(qs);
        PDMLinkProduct wtProduct = null;
        if (results.hasMoreElements())
        {
            wtProduct = (PDMLinkProduct) results.nextElement();
        }

        return wtProduct;
    }
    
    /**
     * @since 2007/05/27
     * @author kang ho chul
     * @see
     * @return PDMLinkProduct - description: PDMLinkProduct 를 반환한다
     * 
     */
    
    public static ExchangeContainer getSiteContainer() throws Exception
    {
        QuerySpec qs = new QuerySpec(ExchangeContainer.class);
        //SearchCondition sc1 = new SearchCondition(ExchangeContainer.class, PDMLinkProduct.NAME, SearchCondition.EQUAL, "e3ps");
        //qs.appendSearchCondition(sc1);
        QueryResult results = (QueryResult) PersistenceHelper.manager.find(qs);
        ExchangeContainer ec = null;
        if (results.hasMoreElements())
        {
            ec = (ExchangeContainer) results.nextElement();
        }

        return ec;
    }
    /**
     * @since 2007/05/27
     * @author kang ho chul
     * @see
     * @return String - description: OID 를 반환한다
     * 
     */
    public static String getOIDString(Persistable per)
    {
        try
        {
            /*
             * ReferenceFactory rf = new ReferenceFactory(); return rf.getQueryString(rf.getReference(per));
             */
            if (per == null) { return ""; }
            return PersistenceHelper.getObjectIdentifier(per).getStringValue();
        }
        catch (Exception e)
        {
            Kogger.error(WCUtil.class, e);
            return "";
        }
    }
    
    /**
     * 
     * @author Choi Seunghwan, swchoi@e3ps.com
     * @createDate 2007. 08. 06
     * @param per
     * @param isFullOid
     * @return
     */
    public static String getOIDString(Persistable persistable, boolean isFullOid)
    {
        if (persistable == null) return null;
        try
        {
            if (rf == null) rf = new ReferenceFactory();
            
            if(isFullOid)
                return rf.getReferenceString(persistable);
            else
                return PersistenceHelper.getObjectIdentifier(persistable).getStringValue();
        }
        catch (Exception e)
        {
            Kogger.error(WCUtil.class, e);
        }
        return null;
    }

    /**
     * @since 2007/05/27
     * @author kang ho chul
     * @see
     * @return String - description: WorkProcess OID 를 반환한다
     * 
     */
    public static String getWfProcessOIDString(Persistable per)
    {
        try
        {
            String workProcessOid = "";
            ReferenceFactory rf = new ReferenceFactory();
            WorkItem item = (WorkItem) per;
            WfAssignedActivity source = (WfAssignedActivity) item.getSource().getObject();
            WfProcess process = source.getParentProcess();
            workProcessOid = rf.getReferenceString(process);
            return workProcessOid;
        }
        catch (Exception e)
        {
            Kogger.error(WCUtil.class, e);
            return "";
        }
    }

    /**
     * @since 2007/05/27
     * @author kang ho chul oid에 해당하는 Persistable객체를 반환한다.
     * @param oid
     * @return Persistable
     */
    public static Persistable getPersistable(String oid) throws Exception
    {
        return getObject(oid);
    }

    /**
     * @since 2007/05/27
     * @author kang ho chul 객체에 첨부되어 있는 파일 정보를 얻어온다.
     * @param fch
     * @return
     */
    public static Vector getAttacheFiles(FormatContentHolder fch)
    {
        Vector secVec = null;
        try
        {
            ContentHolder ch = (ContentHolder) fch;
            FormatContentHolder formatCHolder = (FormatContentHolder) ContentHelper.service.getContents(ch);

            Vector sfv = ContentHelper.getContentList(formatCHolder);

            if (sfv != null && sfv.size() > 0)
            {
                secVec = new Vector();
                for (int ii = 0; ii < sfv.size(); ii++)
                {
                    ContentItem sci = (ContentItem) sfv.get(ii);
                    if (sci instanceof ApplicationData)
                    {
                        ApplicationData secAppData = (ApplicationData) sci;
                        URL secUrl = ContentHelper.getDownloadURL(ch, secAppData);
                        String secFileName = secAppData.getFileName();
                        String secSize = (new Long(secAppData.getFileSize() / 1024L)).toString() + " KB";
                        String secFileOid = secAppData.getPersistInfo().getObjectIdentifier().getStringValue();
                        String[] secStr = new String[] { secUrl.toString(), secFileName, secSize, secFileOid };
                        secVec.add(secStr);
                    }
                }
            }
        }
        catch (WTException e)
        {
            Kogger.error(WCUtil.class, e);
        }
        catch (PropertyVetoException e)
        {
            Kogger.error(WCUtil.class, e);
        }
        return secVec;
    }

    /**
     * @since 2007/05/28
     * @author kang ho chul
     * @param persist
     * @return
     * @throws Exception
     */
    public static long getLongPersistable(Persistable persist) throws Exception
    {
        return PersistenceHelper.getObjectIdentifier(persist).getId();
    }

    /**
     * @since 2006/07/25
     * @author sunny
     * @see
     * @return String - description: VR OID 를 반환한다
     * 
     */
    public static String getVROID(Persistable persistable)
    {
        if (persistable == null) return null;
        try
        {
            if (rf == null) rf = new ReferenceFactory();
            return rf.getReferenceString(persistable);
        }
        catch (Exception e)
        {
            Kogger.error(WCUtil.class, e);
        }
        return null;
    }

    /**
     * @since 2006/07/25
     * @author sunny
     * @see
     * @return String - description: VR OID 를 반환한다
     * 
     */
    public static String getVROID(String oid) throws WTException
    {
        try
        {
            Persistable obj = (Persistable) getPersistable(oid);
            if (obj == null)
                return null;
            else
                return getVROID(obj);
        }
        catch (Exception e)
        {
            Kogger.error(WCUtil.class, e);
            throw new WTException(e);
        }
    }

    /**
     * oid에서 객체로 리턴한다.
     * 
     * @author Choi Seunghwan, swchoi@e3ps.com
     * @createDate 2007. 07. 24
     * @param oid
     * @return
     */
    public static Persistable getObject(String oid)
    {
        if (oid == null || "null".equals(oid)) return null;
        try
        {
            if (rf == null) rf = new ReferenceFactory();
            return rf.getReference(oid).getObject();
        }
        catch (WTException e)
        {
            Kogger.debug(WCUtil.class, "Error Message = "+ e.getMessage());
        }
        return null;
    }
    

    /** 
     * Argument로 Pass된 Versioned Object에 대한 최신 Version.Iteration의 Value(예, V1.1)를 Return
     *
     * @param versioned : Versioned 객체
     * @return 최신 Version의 Version.Iteration Value <P>
     * @throws wt.util.WTException
     */
    public static String getVersionIterationValue(Versioned versioned) throws WTException  {
        if(versioned != null) {
            String version = VersionControlHelper.getVersionIdentifier(versioned).getValue();
            String iteration = VersionControlHelper.getIterationIdentifier(versioned).getValue();
            return (version + "." + iteration);
        }
        return "";
    }
    

    /** 
     * Argument로 Pass된 Oid에 해당하는 WTObject의 WfProcess를 찾아 그 프로세스명을 Return함.
     * 
     * @param oid : WTObject의 OID
     * @return String : 프로세스명 <P>
     * @throws WTException
     */
    public static String getProcessName( String oid ) throws WTException {
        String processName = "";
        QueryResult qr = null;
        Persistable persistable = null;
        WfProcess wfProcess = null;
        
        persistable = WCUtil.getObject( oid );
        if ( persistable != null )
            qr = WfEngineHelper.service.getAssociatedProcesses( persistable, null, null );
        while ( qr.hasMoreElements() ) {
            wfProcess = (WfProcess)qr.nextElement();
            if ( ( wfProcess.getState() ).equals( WfState.OPEN_RUNNING ) ) {
                processName = wfProcess.getName();
                break;
            }
            else if ( ( wfProcess.getState() ).equals( WfState.CLOSED_COMPLETED_EXECUTED ) ) {
                processName = wfProcess.getName();
                break;
            }
        }
        
        return processName;
    }
    
}
