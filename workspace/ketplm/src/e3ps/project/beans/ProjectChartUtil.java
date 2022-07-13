package e3ps.project.beans;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.query.ClassAttribute;
import wt.query.ExistsExpression;
import wt.query.NegatedExpression;
import wt.query.OrderBy;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.query.TableExpression;
import wt.util.WTAttributeNameIfc;
import wt.util.WTException;
import e3ps.common.util.StringUtil;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSTask;
import e3ps.project.ExtendScheduleData;
import e3ps.project.MoldProject;
import e3ps.project.ProductProject;
import e3ps.project.ReviewProject;
import ext.ket.shared.log.Kogger;



public class ProjectChartUtil implements wt.method.RemoteAccess, java.io.Serializable {
	static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
	
	public static Object projectDeptChart(HashMap map) throws WTException {
		if(!SERVER) {
			Class argTypes[] = new Class[]{HashMap.class};
			Object args[] = new Object[]{map};
			Object obj = null;
			try {
				obj = wt.method.RemoteMethodServer.getDefault().invoke(
						"projectDeptChart",
						ProjectChartUtil.class.getName(),
						null,
						argTypes,
						args);
			}
			catch(RemoteException e) {
				Kogger.error(ProgramManagerHelper.class, e);
				throw new WTException(e);
			}
			catch(InvocationTargetException e) {
				Kogger.error(ProgramManagerHelper.class, e);
				throw new WTException(e);
			}
			return obj;
		}
		
		QueryResult result = null;
		try {
			/*
			SELECT A2.*, A0.*, A1.*, A3.PLANSTARTDATE, A3.PLANENDDATE
			FROM E3PSProject A0, E3PSTask A1, ExtendScheduleData A3
			WHERE  
				   (A0.idA2A2 = A1.idA3B4) AND (A0.lastest = 1) AND (A0.checkOutState <> 'c/o') 	   
				   AND (A1.idA3A4 = A3.idA2A2) 
				   AND (A3.planStartDate > TO_DATE('2009:12:01:23:59:59','YYYY:MM:DD:HH24:MI:SS')) 
				   AND (A3.planStartDate < TO_DATE('2009:12:31:23:59:59','YYYY:MM:DD:HH24:MI:SS'))
				   AND not exists (select * from E3PSTask B1 Where A1.ida2a2 = B1.IDA3PARENTREFERENCE)
			ORDER BY A3.PLANSTARTDATE, A3.PLANENDDATE
			;

			*/
			
			ReferenceFactory rf = new ReferenceFactory();
			
			String pjtOid = StringUtil.trimToEmpty((String)map.get("pjtOid"));
			String pjtType = StringUtil.trimToEmpty((String)map.get("pjtType"));
			
			String yyyy = StringUtil.trimToEmpty((String)map.get("yyyy"));
			String mm = StringUtil.trimToEmpty((String)map.get("mm"));
			
			E3PSProject project = null;
			
			Timestamp startStamp = null;
			Timestamp endStamp = null;
			
			
			if(pjtOid.length() > 0) {
				project = (E3PSProject)rf.getReference(pjtOid).getObject();
			}
			
			if(yyyy.length() > 0) {
				int sYear = 0;
				int sMonth = 0;
				int eYear = 0;
				int eMonth = 0;
				
				sYear = Integer.parseInt(yyyy);
				if(mm.length() > 0) {
					sMonth = Integer.parseInt(mm);
					
					if(sMonth >= 12) {
						eYear = sYear + 1;
						eMonth = 1;
					}
					else {
						eYear = sYear;
						eMonth = sMonth + 1;
					}
					
				} else {
					sMonth = 1;
					
					eYear = sYear + 1;					
					eMonth = sMonth;
				}
				startStamp = new Timestamp(getDate(1, sMonth, sYear, 0, 0, 0).getTime());
				endStamp = new Timestamp(getDate(1, eMonth, eYear, 0, 0, 0).getTime());
			}
			
			SearchCondition sc = null;
			
			QuerySpec qs = new QuerySpec();
			qs.setAdvancedQueryEnabled(true);
			/*
			0	ReviewProject
			1	ReviewProject
			2	ProductProject
			3	MoldProject
			4	ProductProject
			*/
			int idx_pjtClass = 0;
			Class idx_taget = null;
			if(pjtType.equals("0") || pjtType.equals("1")) {
				idx_pjtClass = qs.addClassList(ReviewProject.class, true);
				idx_taget = ReviewProject.class;
			}else if(pjtType.equals("2") || pjtType.equals("4")) {
				idx_pjtClass = qs.addClassList(ProductProject.class, true);
				idx_taget = ProductProject.class;
			}else {
				idx_pjtClass = qs.addClassList(MoldProject.class, true);
				idx_taget = MoldProject.class;
			}
			
			int idx_taskClass = qs.addClassList(E3PSTask.class, true);
			int idx_scheduleClass = qs.addClassList(ExtendScheduleData.class, false);
			
			if(project != null) {
				qs.appendAnd();
				qs.appendWhere(
						new SearchCondition(idx_taget, WTAttributeNameIfc.ID_NAME, SearchCondition.EQUAL,
								project.getPersistInfo().getObjectIdentifier().getId()),
						new int[] {idx_pjtClass});
			}
			if(qs.getConditionCount() > 0){
				qs.appendAnd();
			}
			
			qs.appendWhere(new SearchCondition(idx_taget, "pjtType", SearchCondition.EQUAL, Integer.parseInt(pjtType)),new int[] { idx_pjtClass});
			qs.appendAnd();
			qs.appendWhere(new SearchCondition(idx_taget, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true")),new int[] { idx_pjtClass});
			qs.appendAnd();
			qs.appendWhere(new SearchCondition(idx_taget, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"),new int[] {idx_pjtClass});

			qs.appendAnd();
			SearchCondition sc1 = new SearchCondition(
					new ClassAttribute(idx_taget, WTAttributeNameIfc.ID_NAME), "=", 
					new ClassAttribute(E3PSTask.class, "projectReference.key.id")
			);
			sc1.setFromIndicies(new int[]{idx_pjtClass, idx_taskClass}, 0);
			sc1.setOuterJoin(0);
			qs.appendWhere(sc1, new int[]{idx_pjtClass, idx_taskClass});

			
			qs.appendAnd();
			SearchCondition sc2 = new SearchCondition(
					new ClassAttribute(E3PSTask.class, "taskSchedule.key.id"), "=", 
					new ClassAttribute(ExtendScheduleData.class, WTAttributeNameIfc.ID_NAME)
			);
			sc2.setFromIndicies(new int[]{idx_taskClass, idx_scheduleClass}, 0);
			sc2.setOuterJoin(0);
			qs.appendWhere(sc2, new int[]{idx_taskClass, idx_scheduleClass});
			
			
			if(startStamp != null) {
				//ToDay 시작
				qs.appendAnd();
				qs.appendWhere(new SearchCondition(ExtendScheduleData.class ,"planStartDate",">=",startStamp ),new int[] {idx_scheduleClass});
			}
			
			if(endStamp != null) {
				// ToDay 끝
				qs.appendAnd();
				qs.appendWhere(new SearchCondition(ExtendScheduleData.class ,"planStartDate","<",endStamp ),new int[] {idx_scheduleClass});
			}
			
			QuerySpec subSelect = new QuerySpec();
			subSelect.getFromClause().setAliasPrefix("B");
			int altIndex = subSelect.appendClassList(E3PSTask.class, false);
			subSelect.appendSelect(new ClassAttribute(E3PSTask.class, WTAttributeNameIfc.ID_NAME), new int[] { altIndex }, true);

			TableExpression[] tables = new TableExpression[2];
			String[] aliases = new String[2];
			tables[0] = qs.getFromClause().getTableExpressionAt(idx_taskClass);
			aliases[0] = qs.getFromClause().getAliasAt(idx_taskClass);
			
			tables[1] = subSelect.getFromClause().getTableExpressionAt(altIndex);
			aliases[1] = subSelect.getFromClause().getAliasAt(altIndex);
			SearchCondition correlatedJoin = new SearchCondition(E3PSTask.class, WTAttributeNameIfc.ID_NAME,
										E3PSTask.class,"parentReference.key.id");
			subSelect.appendWhere(correlatedJoin, tables, aliases);

			qs.appendAnd();
			qs.appendWhere(new NegatedExpression(new ExistsExpression(subSelect)), null);
			
			
			//order by 절
		    ClassAttribute sortCa = null;
			OrderBy orderby = null;
			int sortIdx = 0;
			
			sortCa = new ClassAttribute(ExtendScheduleData.class, ExtendScheduleData.PLAN_START_DATE);
			sortCa.setColumnAlias("wtsort" + String.valueOf(sortIdx++));
			qs.appendSelect(sortCa, new int[]{idx_scheduleClass}, false);
			orderby = new OrderBy(sortCa, false, null);
			qs.appendOrderBy(orderby, new int[]{idx_scheduleClass});
			
			sortCa = new ClassAttribute(ExtendScheduleData.class, ExtendScheduleData.PLAN_END_DATE);
			sortCa.setColumnAlias("wtsort" + String.valueOf(sortIdx++));
			qs.appendSelect(sortCa, new int[]{idx_scheduleClass}, false);
			orderby = new OrderBy(sortCa, false, null);
			qs.appendOrderBy(orderby, new int[]{idx_scheduleClass});
			
			//Kogger.debug(getClass(), "##### program department chart status query begin ######################");
			Kogger.debug(ProgramManagerHelper.class, qs.toString());
			//Kogger.debug(getClass(), "##### program department chart status query end ######################");
			
			result = PersistenceHelper.manager.find(qs);
			/*
			
			TreeMap progmap = new TreeMap();
			TreeMap pjtmap = new TreeMap();
			TreeMap teammap = new TreeMap();
			HashMap pjtteam = new HashMap();
			
			while(result.hasMoreElements()) {
				Object object[] = (Object[])result.nextElement();
				
				ProjectManager rProgram = (ProjectManager)object[0];
				E3PSProject rProject = (E3PSProject)object[1];
				E3PSTask rTask = (E3PSTask)object[2];
				
				if(!progmap.containsKey(rProgram.getName())) {
					progmap.put(rProgram.getName(), rProgram);
				}
				
				TreeMap tmap = (TreeMap)pjtmap.get(rProgram.getName());
				if(tmap == null) {
					tmap = new TreeMap();
				}
				
				tmap.put(rProject.getPjtNo(), rProject);
				pjtmap.put(rProgram.getName(), tmap);
				
				
				Department rTeam = rTask.getDepartment();
				String teamCode = "";
				if(rTeam == null) {
					teamCode = "NONE";
				} else {
					teamCode = rTeam.getCode();
				}
				teammap.put(teamCode, teamCode);
				
				
				HashMap pteam = (HashMap)pjtteam.get(rProject.getPjtNo());
				if(pteam == null) {
					pteam = new HashMap();
				}
				ArrayList ttask = (ArrayList)pteam.get(teamCode);
				if(ttask == null) {
					ttask = new ArrayList();
				}
				ttask.add(rTask);
				pteam.put(teamCode, ttask);
				pjtteam.put(rProject.getPjtNo(), pteam);
			}
			
			
			Iterator titr = teammap.values().iterator();
			while(titr.hasNext()) {
				String teamCode = (String)titr.next();
				if("NONE".equals(teamCode)) {
					continue;
				}
				Department d = e3ps.groupware.company.DepartmentHelper.manager.getDepartment(teamCode);
				
			}
			*/
		}
		catch(Exception e) {
			Kogger.error(ProgramManagerHelper.class, e);
		}
		return result;
	}
	
	public static Date getDate(int day, int month, int year, int hourofday, int minute, int second) {
		if (day == 0 && month == 0 && year == 0)
			return null;
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, day, hourofday, minute, second);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();     
	}
}
