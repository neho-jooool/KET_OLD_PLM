package e3ps.ecm.beans;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Hashtable;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.query.QuerySpec;
import wt.util.WTException;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETObjectUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.StringUtil;
import e3ps.dms.entity.KETProjectDocument;
import e3ps.ecm.dao.MoldPlanDao;
import e3ps.ecm.entity.KETMoldChangePlan;
import e3ps.ecm.entity.KETMoldECAEpmDocLink;
import e3ps.ecm.entity.KETProdECAEpmDocLink;

/**
 * 클래스명 : MoldPlanBeans
 * 설명 :
 * 작성자 : 오승연
 * 작성일자 : 2010. 10. 13.
 */
public class MoldPlanBeans {

	public ArrayList<Hashtable<String, String>> getMoldPlanExcelList( Hashtable<String, String> searchCon ) throws Exception
	{
		Connection conn = null;
		MoldPlanDao pDao = null;
		ArrayList<Hashtable<String, String>> planList = new ArrayList<Hashtable<String, String>>();

		try
		{
			conn = PlmDBUtil.getConnection();
			pDao = new MoldPlanDao( conn );

			planList = pDao.getMoldPlanExcelList( searchCon );

		}
		catch( Exception e )
		{
			throw e;
		}
		finally
		{
			PlmDBUtil.close( conn );
		}

		return planList;
	}

	public String getNewScheduleId() throws Exception
	{
		String scheduleId = "";
		Connection conn = null;
		MoldPlanDao planDao = null;

		try
		{
			conn = PlmDBUtil.getConnection();

			planDao = new MoldPlanDao( conn );

			scheduleId = planDao.getNewScheduleId();
		}
		catch( Exception e )
		{
			throw e;
		}
		finally
		{
			PlmDBUtil.close( conn );
		}

		return scheduleId;
	}

	public KETProjectDocument[] getNewRefDocList( String[] docOidList ) throws WTException
	{
		KETProjectDocument[] docList = new KETProjectDocument[ docOidList.length ];

		for( int dCnt=0; dCnt < docOidList.length; dCnt++ )
		{
			docList[dCnt] = (KETProjectDocument)KETObjectUtil.getObject(docOidList[dCnt]);
		}

		return docList;
	}

	public void modifyMoldEpmDocLinkPlanId( String scheduleId, String chgPlanId ) throws Exception
	{
		KETMoldECAEpmDocLink epmDocLink = null;
		try
		{
			QuerySpec spec = new QuerySpec( KETMoldECAEpmDocLink.class );
			SearchUtil.appendEQUAL(spec, KETMoldECAEpmDocLink.class, KETMoldECAEpmDocLink.SCHEDULE_ID, scheduleId, 0);
			QueryResult result = PersistenceHelper.manager.find(spec);

			while( result.hasMoreElements() )
			{
				epmDocLink = ( KETMoldECAEpmDocLink )result.nextElement();
				epmDocLink.setScheduleId( chgPlanId );

				PersistenceHelper.manager.modify(epmDocLink);
			}
		}
		catch( Exception e )
		{
			throw e;
		}
	}

	public void modifyProdEpmDocLinkPlanId( String scheduleId, String chgPlanId ) throws Exception
	{
		KETProdECAEpmDocLink epmDocLink = null;
		try
		{
			QuerySpec spec = new QuerySpec( KETProdECAEpmDocLink.class );
			SearchUtil.appendEQUAL(spec, KETProdECAEpmDocLink.class, KETProdECAEpmDocLink.SCHEDULE_ID, scheduleId, 0);
			QueryResult result = PersistenceHelper.manager.find(spec);

			while( result.hasMoreElements() )
			{
				epmDocLink = ( KETProdECAEpmDocLink )result.nextElement();
				epmDocLink.setScheduleId( chgPlanId );

				PersistenceHelper.manager.modify(epmDocLink);
			}
		}
		catch( Exception e )
		{
			throw e;
		}
	}

	public String getCurrentProcess(  KETMoldChangePlan plan )
	{
		String currentProcess = "";

		int planCnt = 0;
		int completeCnt = 0;
		int currentInx = 0;
		int cntInx = 0;
		boolean isProgress = true;

		if( plan.getProdDwgPlanDate() != null ){ planCnt++; }
		if( plan.getMoldChangePlanDate() != null ){ planCnt++; }
		if( plan.getWorkPlanDate() != null ){ planCnt++; }
		if( plan.getStorePlanDate() != null ){ planCnt++; }
		if( plan.getAssemblePlanDate() != null ){ planCnt++; }
		if( plan.getTryPlanDate() != null ){ planCnt++; }
		if( plan.getTestPlanDate() != null ){ planCnt++; }
		if( plan.getApprovePlanDate() != null ){ planCnt++; }

		if( plan.getProdDwgActualDate() != null ){ completeCnt++; }
		if( plan.getMoldChangeActualDate() != null ){ completeCnt++; }
		if( plan.getWorkActualDate() != null ){ completeCnt++; }
		if( plan.getStoreActualDate() != null ){ completeCnt++; }
		if( plan.getAssembleActualDate() != null ){ completeCnt++; }
		if( plan.getTryActualDate() != null ){ completeCnt++; }
		if( plan.getTestActualDate() != null ){ completeCnt++; }
		if( plan.getApproveActualDate() != null ){ completeCnt++; }

		if( planCnt != completeCnt )
		{
			currentInx = completeCnt+1;
		}

		if( plan.getProdDwgPlanDate() != null )
		{
			 cntInx++;
			if( cntInx == currentInx )
			{
				currentProcess = "제품도";
			}
		}
		if( plan.getMoldChangePlanDate() != null )
		{
			cntInx++;
			if( cntInx == currentInx )
			{
				currentProcess = "금형설계";
			}
		}
		if( plan.getWorkPlanDate() != null )
		{
			cntInx++;
			if( cntInx == currentInx )
			{
				currentProcess = "금형입고";
			}
		}
		if( plan.getStorePlanDate() != null )
		{
			cntInx++;
			if( cntInx == currentInx )
			{
				currentProcess = "금형부품";
			}
		}
		if( plan.getAssemblePlanDate() != null )
		{
			cntInx++;
			if( cntInx == currentInx )
			{
				currentProcess = "금형조립";
			}
		}
		if( plan.getTryPlanDate() != null )
		{
			cntInx++;
			if( cntInx == currentInx )
			{
				currentProcess = "금형Try";
			}
		}
		if( plan.getTestPlanDate() != null )
		{
			cntInx++;
			if( cntInx == currentInx )
			{
				currentProcess = "제품측정";
			}
		}
		if( plan.getApprovePlanDate() != null )
		{
			cntInx++;
			if( (cntInx == currentInx) ||  (planCnt == completeCnt))
			{
				currentProcess = "검토협의";
			}
		}

		return currentProcess;
	}

	public Timestamp getCurrentProcPlanDate( KETMoldChangePlan plan, String currentProcess )
	{
		Timestamp planDate = null;

		if( currentProcess.equals("제품도") )
		{
			planDate = plan.getProdDwgPlanDate();
		}
		else if( currentProcess.equals("금형설계") )
		{
			planDate = plan.getMoldChangePlanDate();
		}
		else if( currentProcess.equals("금형입고") )
		{
			planDate = plan.getStorePlanDate();
		}
		else if( currentProcess.equals("금형부품") )
		{
			planDate = plan.getWorkPlanDate();
		}
		else if( currentProcess.equals("금형조립") )
		{
			planDate = plan.getAssemblePlanDate();
		}
		else if( currentProcess.equals("금형Try") )
		{
			planDate = plan.getTryPlanDate();
		}
		else if( currentProcess.equals("제품측정") )
		{
			planDate = plan.getTestPlanDate();
		}
		else if( currentProcess.equals("검토협의") )
		{
			planDate = plan.getApprovePlanDate();
		}
		else{
			planDate = DateUtil.getCurrentTimestamp();
		}
		return planDate;
	}

	public String getCurrentPlanStatus( KETMoldChangePlan plan )
	{
		String planStatus = "";

		int planCnt = 0;
		int completeCnt = 0;
		int currentInx = 0;
		int cntInx = 0;
		boolean isProgress = true;

		if( plan.getProdDwgPlanDate() != null ){ planCnt++; }
		if( plan.getMoldChangePlanDate() != null ){ planCnt++; }
		if( plan.getWorkPlanDate() != null ){ planCnt++; }
		if( plan.getStorePlanDate() != null ){ planCnt++; }
		if( plan.getAssemblePlanDate() != null ){ planCnt++; }
		if( plan.getTryPlanDate() != null ){ planCnt++; }
		if( plan.getTestPlanDate() != null ){ planCnt++; }
		if( plan.getApprovePlanDate() != null ){ planCnt++; }

		if( plan.getProdDwgActualDate() != null ){ completeCnt++; }
		if( plan.getMoldChangeActualDate() != null ){ completeCnt++; }
		if( plan.getWorkActualDate() != null ){ completeCnt++; }
		if( plan.getStoreActualDate() != null ){ completeCnt++; }
		if( plan.getAssembleActualDate() != null ){ completeCnt++; }
		if( plan.getTryActualDate() != null ){ completeCnt++; }
		if( plan.getTestActualDate() != null ){ completeCnt++; }
		if( plan.getApproveActualDate() != null ){ completeCnt++; }

		if( planCnt != completeCnt )
		{
			currentInx = completeCnt+1;
		}

		if( plan.getProdDwgPlanDate() != null )
		{
			 cntInx++;
			if( cntInx == currentInx )
			{

				if( plan.getProdDwgActualDate() != null &&  plan.getProdDwgPlanDate().compareTo( plan.getProdDwgActualDate() ) < 0  ||
						plan.getProdDwgActualDate() == null &&  DateUtil.getTimeFormat(plan.getProdDwgPlanDate(),"yyyyMMdd").compareTo(DateUtil.getTimeFormat(DateUtil.getCurrentTimestamp(),"yyyyMMdd")) < 0 )
				{
					isProgress = false;
				}
			}
		}

		if( plan.getMoldChangePlanDate() != null )
		{
			cntInx++;
			if( cntInx == currentInx )
			{
				if( plan.getMoldChangeActualDate() != null &&  plan.getMoldChangePlanDate().compareTo( plan.getMoldChangeActualDate() ) < 0 ||
						plan.getMoldChangeActualDate() == null &&  DateUtil.getTimeFormat(plan.getMoldChangePlanDate(),"yyyyMMdd").compareTo(DateUtil.getTimeFormat(DateUtil.getCurrentTimestamp(),"yyyyMMdd")) < 0 )
				{
					isProgress = false;
				}
			}
		}

		if( plan.getWorkPlanDate() != null )
		{
			cntInx++;
			if( cntInx == currentInx )
			{
				if( plan.getWorkActualDate() != null &&  plan.getWorkPlanDate().compareTo( plan.getWorkActualDate() ) < 0 ||
						plan.getWorkActualDate() == null &&  DateUtil.getTimeFormat(plan.getWorkPlanDate(),"yyyyMMdd").compareTo(DateUtil.getTimeFormat(DateUtil.getCurrentTimestamp(),"yyyyMMdd")) < 0 )
				{
					isProgress = false;
				}
			}
		}

		if( plan.getStorePlanDate() != null )
		{
			cntInx++;
			if( cntInx == currentInx )
			{
				if( plan.getStoreActualDate() != null &&  plan.getStorePlanDate().compareTo( plan.getStoreActualDate() ) < 0 ||
						plan.getStoreActualDate() == null &&  DateUtil.getTimeFormat(plan.getStorePlanDate(),"yyyyMMdd").compareTo(DateUtil.getTimeFormat(DateUtil.getCurrentTimestamp(),"yyyyMMdd")) < 0 )
				{
					isProgress = false;
				}
			}
		}

		if( plan.getAssemblePlanDate() != null )
		{
			cntInx++;
			if( cntInx == currentInx )
			{
				if( plan.getAssembleActualDate() != null &&  plan.getAssemblePlanDate().compareTo( plan.getAssembleActualDate() ) < 0 ||
						plan.getAssembleActualDate() == null &&  DateUtil.getTimeFormat(plan.getAssemblePlanDate(),"yyyyMMdd").compareTo(DateUtil.getTimeFormat(DateUtil.getCurrentTimestamp(),"yyyyMMdd")) < 0 )
				{
					isProgress = false;
				}
			}
		}

		if( plan.getTryPlanDate() != null )
		{
			cntInx++;
			if( cntInx == currentInx )
			{
				if( plan.getTryActualDate() != null &&  plan.getTryPlanDate().compareTo( plan.getTryActualDate() ) < 0 ||
						plan.getTryActualDate() == null &&  DateUtil.getTimeFormat(plan.getTryPlanDate(),"yyyyMMdd").compareTo(DateUtil.getTimeFormat(DateUtil.getCurrentTimestamp(),"yyyyMMdd")) < 0 )
				{
					isProgress = false;
				}
			}
		}

		if( plan.getTestPlanDate() != null )
		{
			cntInx++;
			if( cntInx == currentInx )
			{
				if( plan.getTestActualDate() != null &&  plan.getTestPlanDate().compareTo( plan.getTestActualDate() ) < 0 ||
						plan.getTestActualDate() == null &&  DateUtil.getTimeFormat(plan.getTestPlanDate(),"yyyyMMdd").compareTo(DateUtil.getTimeFormat(DateUtil.getCurrentTimestamp(),"yyyyMMdd")) < 0 )
				{
					isProgress = false;
				}
			}
		}

		if( plan.getApprovePlanDate() != null )
		{
			cntInx++;
			if( cntInx == currentInx )
			{
				if( plan.getApproveActualDate() != null &&  plan.getApprovePlanDate().compareTo( plan.getApproveActualDate() ) < 0 ||
						plan.getApproveActualDate() == null &&  DateUtil.getTimeFormat(plan.getApprovePlanDate(),"yyyyMMdd").compareTo(DateUtil.getTimeFormat(DateUtil.getCurrentTimestamp(),"yyyyMMdd")) < 0 )
				{
					isProgress = false;
				}
			}
		}

		if( StringUtil.checkNull(plan.getScheduleStatus()).length() > 0 )
		{
			if( planCnt !=0 && planCnt == completeCnt )
			{
				if( "합격".equals(plan.getMeasureType()) || "일정재수립".equals(plan.getFailAction()) ||  StringUtil.checkNull(plan.getResult()).length() >0)  {
					if(StringUtil.checkNull(plan.getAttribute2()).length() > 0  || StringUtil.checkNull(plan.getAttribute1()).equals("No")  ){
						planStatus = "C";
					}else{
						planStatus = "P";
					}
				}else{
					planStatus = "P";
				}

			}

			else if( planCnt != completeCnt && isProgress )
			{
				planStatus = "P";
			}
			else if( planCnt != completeCnt && !isProgress )
			{
				planStatus = "D";
			}else if( planCnt == 0 && "R".equals(plan.getScheduleStatus()) ){
				planStatus = "R";
			}
		}
		return planStatus;
	}

	public boolean savePlanComment( String scheduleId, String userId, String userName, String comment, String userLineOrder ) throws Exception
	{
		Connection conn = null;
		MoldPlanDao planDao = null;
		boolean isSuccess = false;
		try
		{
			conn = PlmDBUtil.getConnection(false);

			planDao = new MoldPlanDao( conn );

			if( planDao.countUserComment(scheduleId, userId)  > 0 )
			{
				if( !"".equals( StringUtil.checkNull( userLineOrder ) ) ){
					isSuccess = planDao.updateUserComment( scheduleId, userId, comment, userLineOrder );
				}else{
					int lineOrder = planDao.countMaxCommentLines( scheduleId );
					isSuccess = planDao.insertUserComment( scheduleId, userId, userName, comment, lineOrder );
				}
			}
			else
			{
				int lineOrder = planDao.countMaxCommentLines( scheduleId );
				isSuccess = planDao.insertUserComment( scheduleId, userId, userName, comment, lineOrder );
			}

			if( isSuccess )
			{
				conn.commit();
			}
			else
			{
				conn.rollback();
			}
		}
		catch( Exception e )
		{
			conn.rollback();

			throw e;
		}
		finally
		{
			PlmDBUtil.close( conn );
		}

		return isSuccess;
	}

	public boolean deleteUserComment( String scheduleId, String userId, String userLineOrder ) throws Exception
	{
		Connection conn = null;
		MoldPlanDao planDao = null;
		boolean isSuccess = false;
		try
		{
			conn = PlmDBUtil.getConnection(false);
			planDao = new MoldPlanDao( conn );

			isSuccess = planDao.deleteUserComment( scheduleId, userId, userLineOrder );

			if( isSuccess )
			{
				conn.commit();
			}
			else
			{
				conn.rollback();
			}
		}
		catch( Exception e )
		{
			conn.rollback();

			throw e;
		}
		finally
		{
			PlmDBUtil.close( conn );
		}

		return isSuccess;

	}

	public ArrayList<Hashtable<String, String>> getUserComments( String scheduleId ) throws Exception
	{
		ArrayList<Hashtable<String, String>> commentList = new ArrayList<Hashtable<String, String>>();
		Connection conn = null;

		try
		{
			conn = PlmDBUtil.getConnection();
			MoldPlanDao planDao = new MoldPlanDao( conn );

			commentList = planDao.getUserComments( scheduleId );

		}
		catch( Exception e )
		{
			throw e;
		}
		finally
		{
			PlmDBUtil.close( conn );
		}

		return commentList;
	}

}

