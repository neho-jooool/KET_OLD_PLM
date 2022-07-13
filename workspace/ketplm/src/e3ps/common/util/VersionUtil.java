/*
 * @(#) VersionUtil.java  Create on 2004. 11. 8.
 * Copyright (c) e3ps. All rights reserverd
 */
package e3ps.common.util;

import wt.enterprise.Master;
import wt.enterprise.RevisionControlled;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.WTObject;
import wt.folder.Folder;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.folder.Foldered;
import wt.inf.container.WTContained;
import wt.inf.container.WTContainerHelper;
import wt.inf.container.WTContainerRef;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleManaged;
import wt.lifecycle.LifeCycleTemplate;
import wt.lifecycle.State;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.series.HarvardSeries;
import wt.series.MultilevelSeries;
import wt.series.Series;
import wt.series.SeriesException;
import wt.series.SeriesIncrementInvalidException;
import wt.team.TeamHelper;
import wt.team.TeamManaged;
import wt.team.TeamTemplate;
import wt.team.TeamTemplateReference;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.IterationIdentifier;
import wt.vc.Mastered;
import wt.vc.VersionControlException;
import wt.vc.VersionControlHelper;
import wt.vc.VersionIdentifier;
import wt.vc.Versioned;
import ext.ket.shared.log.Kogger;

/**
 * 
 * @author Choi Seunghwan, skyprda@e3ps.com
 * @version 1.00, 2004. 11. 8.
 * @since 1.4
 */
public class VersionUtil
{
    public static RevisionControlled getLatestObject(Master master) throws WTException
    {
        RevisionControlled rc = null;
        QueryResult queryResult = VersionControlHelper.service.allVersionsOf(master);

        while (queryResult.hasMoreElements())
        {
            RevisionControlled obj = ((RevisionControlled) queryResult.nextElement());
            if (rc == null || obj.getVersionIdentifier().getSeries().greaterThan(rc.getVersionIdentifier().getSeries()))
            {
                rc = obj;
            }
        }
        if (rc != null)
            return (RevisionControlled) VersionControlHelper.getLatestIteration(rc, false);
        else
            return rc;
    }

    public static RevisionControlled getVersionObject(Master master, String version) throws WTException
    {
        RevisionControlled rc = null;
        QueryResult queryResult = wt.vc.VersionControlHelper.service.allVersionsOf(master);

        while (queryResult.hasMoreElements())
        {
            RevisionControlled obj = ((RevisionControlled) queryResult.nextElement());
            //            if
            // (obj.getVersionDisplayIdentifier().getLocalizedMessage(Locale.KOREA).equals(version))
            if (obj.getVersionIdentifier().getSeries().getValue().equals(version))
            {
                rc = obj;
            }
        }
        if (rc != null)
            return (RevisionControlled) wt.vc.VersionControlHelper.getLatestIteration(rc, false);
        else
            return rc;
    }

    /**
     * 최신 버전 객체를 반환한다.
     * 
     * @param targetClass
     * @param master
     * @return
     */
    public static WTObject getNewestIteration(Class targetClass, Mastered master)
    {
        try
        {
            QuerySpec query = new QuerySpec(targetClass);
            query.appendWhere(VersionControlHelper.getSearchCondition(targetClass, master), new int[] { 0 });
            query.appendAnd();
            query.appendWhere(VersionControlHelper.getSearchCondition(targetClass, true), new int[] { 0 });

            QueryResult result = PersistenceHelper.manager.find(query);
            while (result.hasMoreElements())
                return (WTObject) result.nextElement();
        }
        catch (QueryException e)
        {
            Kogger.error(VersionUtil.class, e);
        }
        catch (VersionControlException e)
        {
            Kogger.error(VersionUtil.class, e);
        }
        catch (WTException e)
        {
            Kogger.error(VersionUtil.class, e);
        }
        return null;
    }

    /**
     * Major와 Minor 버전을 모두 출력한다.
     * 
     * @param obj
     * @return @throws
     *            Exception
     */
    public static String getVersion(RevisionControlled obj) throws Exception
    {
        return getMajorVersion(obj) + "." + VersionControlHelper.getIterationIdentifier(obj).getSeries().getValue();
    }

    /**
     * Major 버전만을 출력한다.
     * 
     * @param obj
     * @return @throws
     *            Exception
     */
    public static String getMajorVersion(RevisionControlled obj) throws Exception
    {
        return VersionControlHelper.getVersionIdentifier(obj).getSeries().getValue();
    }

    public static void setVersion(Versioned versioned, String ver) throws SeriesIncrementInvalidException,
            VersionControlException, WTPropertyVetoException, WTException
    {
        //VersionControlHelper.getQualifiedIdentifier(versioned);
        MultilevelSeries multilevelseries = MultilevelSeries.newMultilevelSeries("wt.vc.VersionIdentifier", ver);
        VersionIdentifier versionidentifier = VersionIdentifier.newVersionIdentifier(multilevelseries);
        VersionControlHelper.setVersionIdentifier(versioned, versionidentifier);

        Series series = Series.newSeries("wt.vc.IterationIdentifier", "1");
        IterationIdentifier iterationidentifier = IterationIdentifier.newIterationIdentifier(series);
        VersionControlHelper.setIterationIdentifier(versioned, iterationidentifier);
    }

    public static Versioned revise(Versioned obj, String ver, String state) throws VersionControlException,
            WTPropertyVetoException, WTException
    {
        return revise(obj, ver, null, state);
    }

    public static Versioned revise(Versioned obj, String ver, String lifecycle, String state)
            throws VersionControlException, WTPropertyVetoException, WTException
    {
        if(lifecycle == null)
            lifecycle = ((LifeCycleManaged) obj).getLifeCycleName();
        Folder folder = FolderHelper.service.getFolder((FolderEntry) obj);
//        MultilevelSeries multilevelseries = MultilevelSeries.newMultilevelSeries("wt.vc.VersionIdentifier", ver);
//        VersionIdentifier vi = VersionIdentifier.newVersionIdentifier(multilevelseries);
        
        VersionIdentifier vi = VersionControlHelper.nextVersionId(obj);
		MultilevelSeries multilevelseries = MultilevelSeries.newMultilevelSeries("wt.vc.VersionIdentifier", ver, vi.getSeries().getLevel());
	    vi = VersionIdentifier.newVersionIdentifier(multilevelseries);
        obj = VersionControlHelper.service.newVersion(obj, vi, VersionControlHelper.firstIterationId(obj));
        FolderHelper.assignLocation((FolderEntry) obj, folder);
        LifeCycleHelper.setLifeCycle((LifeCycleManaged) obj, LifeCycleHelper.service.getLifeCycleTemplate(lifecycle));
        obj = (Versioned) PersistenceHelper.manager.save((Persistable) obj);

        if (state != null)
        {
            LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) obj, State.toState(state), false);
        }
        return obj;
    }

    public static Versioned revise(Versioned obj)
    {
        return revise(obj, null);
    }
    
    public static Versioned revise(Versioned obj, String lifecycle)
    {
        if(lifecycle == null)
            lifecycle = ((LifeCycleManaged) obj).getLifeCycleName();
        try
        {
            Folder folder = FolderHelper.service.getFolder((FolderEntry) obj);
            obj = VersionControlHelper.service.newVersion(obj);
            FolderHelper.assignLocation((FolderEntry) obj, folder);
            LifeCycleHelper.setLifeCycle((LifeCycleManaged) obj, LifeCycleHelper.service
                    .getLifeCycleTemplate(lifecycle));
            return (Versioned) PersistenceHelper.manager.save((Persistable) obj);
        }
        catch (WTException e)
        {
            Kogger.error(VersionUtil.class, e);
        }
        catch (WTPropertyVetoException e)
        {
            Kogger.error(VersionUtil.class, e);
        }
        return null;
    }
    
	
	public static Versioned doRevise(Versioned versioned, String version, String lifecycle, String location, String state, String team, String note) 
			throws WTException {
		Versioned newVer = null;
		try {
			/*
			if(!VersionHelper.isLatestRevision(versioned)) {
				throw new WTException("최신버전이 아닙니다.");
			}
			*/
			
			if( (version != null) && (version.trim().length() > 0) ) {
				VersionIdentifier newVersionId = getVersionIdentifier(versioned, version.trim());
				IterationIdentifier newIterationId = VersionControlHelper.firstIterationId(versioned);
				newVer = VersionControlHelper.service.newVersion(versioned, newVersionId, newIterationId);	
			} else {			
				newVer = VersionControlHelper.service.newVersion(versioned);
			}
			
			if( (note != null) && (note.trim().length() > 0) ) {
                VersionControlHelper.setNote(newVer, note);
			}
			
			updateRevised(versioned, newVer, location, lifecycle, team);
			newVer = (Versioned)PersistenceHelper.manager.save(newVer);
			
			
			if ( (newVer instanceof LifeCycleManaged) && (state != null) && (state.trim().length() > 0)) {
	            LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) newVer, State.toState(state.trim()), false);
			}
		}
		catch (WTPropertyVetoException e) {
			Kogger.error(VersionUtil.class, e);
			throw new WTException(e);
		}
		catch(WTException e) {
			throw new WTException(e);
		}
		catch(Exception e) {
			throw new WTException(e);
		}
		return newVer;
    }
	

	public static VersionIdentifier getVersionIdentifier(Versioned versioned, String version) throws WTException {
		if (versioned != null) {
			MultilevelSeries vi0 = VersionControlHelper.getVersionIdentifier(versioned).getSeries();
			
			try {
				MultilevelSeries series = null;
				if (vi0 instanceof HarvardSeries) {
					series = HarvardSeries.newMultilevelSeries(((HarvardSeries)vi0).getSeriesName(), version, vi0.getLevel());
					//series = HarvardSeries.newHarvardSeries(((HarvardSeries)vi0).getSeriesName(), version);					
				} else {
					series = MultilevelSeries.newMultilevelSeries(VersionIdentifier.class.getName(), version, vi0.getLevel());
				}
				VersionIdentifier vi = VersionIdentifier.newVersionIdentifier(series);
				return vi;
			}
			catch(SeriesException e) {
				Kogger.error(VersionUtil.class, e);
				throw new WTException(e);
			}
			catch(WTException e) {
				Kogger.error(VersionUtil.class, e);
				throw new WTException(e);
			}
		}
		return null;
	}
	
	public static Versioned updateRevised(Versioned versioned, Versioned newVer, String location, String lifecycle, String team) 
		throws WTException, WTPropertyVetoException {
		
		WTContainerRef wtContainerRef = WTContainerHelper.getContainerRef((WTContained)versioned);
		if (versioned instanceof Foldered) {
			Folder folder = null;
			if( (location != null) && (location.trim().length() > 0) ) {
				folder = FolderHelper.service.getFolder(location, wtContainerRef);
			} else {
				folder = FolderHelper.getFolder((FolderEntry)versioned);
			}
			FolderHelper.assignLocation((FolderEntry)newVer,folder);
		}
		
		if (versioned instanceof LifeCycleManaged) {
			LifeCycleTemplate lct = null;
			if( (lifecycle != null) && (lifecycle.trim().length() > 0) ) {
				lct = LifeCycleHelper.service.getLifeCycleTemplate( lifecycle, wtContainerRef);
			} else {
				lct = LifeCycleHelper.service.getLifeCycleTemplate( ((LifeCycleManaged)versioned).getLifeCycleName(), wtContainerRef);
			}
			LifeCycleHelper.setLifeCycle((LifeCycleManaged)newVer, lct);
		}
		
		if (versioned instanceof TeamManaged) {
			TeamTemplate tt = null;
			
			if( (team == null) || (team.trim().length() == 0)) {
				team = ((TeamManaged)versioned).getTeamTemplateName();
			}
			
			if( (team != null) && (team.trim().length() > 0) ) {
				tt = TeamHelper.service.getTeamTemplate(wtContainerRef, team);
				if(tt != null) {
					((TeamManaged)newVer).setTeamId(null);
					((TeamManaged)newVer).setTeamTemplateId(TeamTemplateReference.newTeamTemplateReference(tt));
				}
			}
		}
		return newVer;
	}


}
