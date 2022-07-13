package e3ps.load.project;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.StringTokenizer;
import java.util.Vector;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import wt.fc.EnumeratedTypeUtil;
import wt.fc.ObjectReference;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleTemplate;
import wt.lifecycle.State;
import wt.org.WTUser;
import wt.pom.Transaction;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;
import wt.util.WTProperties;
import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.jdf.config.ConfigImpl;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.JExcelUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.util.WCUtil;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.People;
import e3ps.groupware.company.PeopleData;
import e3ps.groupware.company.PeopleHelper;
import e3ps.load.migration.edm.log.LogToFile;
import e3ps.project.DevelopType;
import e3ps.project.E3PSProject;
import e3ps.project.ExtendScheduleData;
import e3ps.project.PastType;
import e3ps.project.ProjectCustomereventLink;
import e3ps.project.ProjectSubcontractorLink;
import e3ps.project.ReviewDevelop;
import e3ps.project.ReviewProject;
import e3ps.project.beans.ProjectHelper;
import e3ps.project.beans.ProjectUserHelper;
import ext.ket.shared.log.Kogger;

public class ReviewProjectDataLoader implements wt.method.RemoteAccess, java.io.Serializable  {

	/**
	 * @param args
	 * Sang Min, Kim
	 * smkim@e3ps.com
	 */
	static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
	
	//private static String FILESERVER = "\\\\192.168.17.13";
	private static String EXCELFILE = "";

	// 192.168.16.103
	//데이터정리양식_프로젝트.xls
	
	private static String SEPARATOR = File.separator;
	
	static {
		try {
			String wt_home = WTProperties.getLocalProperties().getProperty("wt.home");
			EXCELFILE = wt_home + SEPARATOR + "loadFiles\\ket\\migration\\project";
		}
		catch(Exception e) {
			Kogger.error(ReviewProjectDataLoader.class, e);
		}
	}
	
	private static String logFile = "reviewlogfile.log";
	
	public static boolean loadFile(String fileName, String type) throws WTException {
		try {
			String filePath = EXCELFILE + SEPARATOR + fileName;
			File dataFile = new File(filePath);
			if(!dataFile.exists()) {
				Kogger.debug(ReviewProjectDataLoader.class, "File not found!!!");
				return false;
			}
			
			logFile = EXCELFILE + SEPARATOR;
			int pidx = fileName.lastIndexOf(".");
			if(pidx > 0) {
				logFile += fileName.substring(0, pidx);
			} else {
				logFile = fileName;
			}
			logFile += ".txt";
			
			Workbook workbook = Workbook.getWorkbook(dataFile);
			Sheet sheets[] = workbook.getSheets();
			
			Vector lds = new Vector();
			
			if(sheets.length > 0) {
				Sheet sheet = null;
				for(int i = 0; i < 1; i++) {
					sheet = sheets[i];
					
					int rows = sheet.getRows();	
					
					Cell hdrs[] = sheet.getRow(1);
					//System.out.print("#################################################################################################Loader Attr Start");
					//Kogger.debug(ReviewProjectDataLoader.class, );
					for(int k = 0; k < hdrs.length; k++) {
						Cell hdr = hdrs[k];
						System.out.print("["+hdr.getContents()+"]");
					}
					Kogger.debug(ReviewProjectDataLoader.class, "");
					for(int k = 3; k < rows; k++) {
						Cell cell[] = sheet.getRow(k);
						for(int a = 0; a < cell.length; a++) {
							System.out.print("<" + cell[a].getContents()+">");
						}
						ReviewData rdata = (ReviewData)getRowData(sheet.getRow(k), k+1, sheet.getName());
						
						if(rdata != null) {
							if(!rdata.isValidate) {
								//System.out.print("[["+ rdata.msg +"]]");
								//log(rdata.msg);
							} else {
								Vector v = new Vector();
								v.add(rdata);
								
								Vector v0 = (Vector)save(v);					
								for(int m = 0; m < v0.size(); m++) {
									ReviewData rdata0 = (ReviewData)v0.get(m);
									if( !(rdata0.isLoadCompleted) ) {
										System.out.print("[["+ rdata0.msg +"]]");
										//log(rdata0.msg);
									}
								}
							}
						}
						
					}
					//Kogger.debug(ReviewProjectDataLoader.class, );
					//System.out.print("#################################################################################################Loader Attr END ");
				}
			}
			workbook.close();
			workbook = null;
		}
		catch(Exception e) {
			Kogger.error(ReviewProjectDataLoader.class, e);
			throw new WTException("file name = " + fileName);
		}
		return true;
	}
	
	public static void log(String msg) {
		try {
			LogToFile logger = new LogToFile(logFile,true);
			logger.log(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Kogger.error(ReviewProjectDataLoader.class, e);
		}
	}	

	public static Object getRowData(Cell[] cells, int rownum, String sheetName) throws WTException {
		ReviewData data = null;
		
		StringBuffer sb = new StringBuffer();
		try {
			if(JExcelUtil.getContent(cells,0).length() == 0) {
				return null;
			}
			
			sb.append("\n############## >>> [ row:"+rownum+" ]-[ sheet:"+sheetName+" ] <<< ##############");
			data = new ReviewData(cells, rownum);
			
			E3PSProject project = null;
			
			if(data.projectNo.length() == 0) {
				data.isValidate = false;
				sb.append("\n").append("-프로젝트 번호 :입력값이 없습니다.");
			}
			else{
				project = ProjectHelper.getProject(data.projectNo.toUpperCase());
				if(project != null) {
					data.isValidate = true;
					sb.append("\n").append("-프로젝트 번호:이미 등록된 번호 입니다.["+data.projectNo+"]");
				}
			}
			data.isExist = (project==null)? false:true;
			
			if(data.planStartDate.length() == 0) {
				data.isValidate = false;
				sb.append("\n").append("-개발시작일  :입력값이 없습니다.");
			}
			
			if(data.projectTypeName.length() == 0) {
				data.isValidate = false;
				sb.append("\n").append("-사업부 :입력값이 없습니다.");
			}
			if(data.developenttype.length() == 0) {
				data.isValidate = false;
				sb.append("\n").append("-개발구분 :입력값이 없습니다.");
			}
			if(data.salesDeptName.length() == 0) {
				data.isValidate = false;
				sb.append("\n").append("-영업담당부서명 :입력값이 없습니다.");
			}
			if(data.salesID.length() == 0) {
				data.isValidate = false;
				sb.append("\n").append("-영업담당자자 ID :입력값이 없습니다.");
			}		
			if(data.devDeptName.length() == 0) {
				data.isValidate = false;
				sb.append("\n").append("-개발담당부서명 :입력값이 없습니다.");
			}
			if(data.pmID.length() == 0) {
				data.isValidate = false;
				sb.append("\n").append("-개발담당자자 ID :입력값이 없습니다.");
			}
			if(data.proposalDate.length() == 0) {
				data.isValidate = false;
				sb.append("\n").append("-개발원가제출일 :입력값이 없습니다.");
			}
			if(data.estimateDate.length() == 0) {
				data.isValidate = false;
				sb.append("\n").append("-개발제안서제출일 :입력값이 없습니다.");
			}
			if(data.projectName.length() == 0) {
				data.isValidate = false;
				sb.append("\n").append("-제품명 :입력값이 없습니다.");
			}
			if(data.rank.length() == 0) {
				data.isValidate = false;
				sb.append("\n").append("-Rank :입력값이 없습니다.");
			}
			if(data.producttype.length() == 0) {
				data.isValidate = false;
				sb.append("\n").append("-제품구분 :입력값이 없습니다.");
			}
			if(data.assembledtype.length() == 0) {
				data.isValidate = false;
				sb.append("\n").append("-조립구분 :입력값이 없습니다.");
			}
			if(data.customereventName.length() == 0) {
				data.isValidate = false;
				sb.append("\n").append("-최종사용처 :입력값이 없습니다.");
			}
			if(data.subcontractorName.length() == 0) {
				data.isValidate = false;
				sb.append("\n").append("-개발검토의뢰처 :입력값이 없습니다.");
			}
			if(data.pastTypeName.length() == 0) {
				data.isValidate = false;
				sb.append("\n").append("-과거차문제점  :입력값이 없습니다.");
			}
			if(data.developTypeName.length() == 0) {
				data.isValidate = false;
				sb.append("\n").append("-문제점개선안  :입력값이 없습니다.");
			}
			
		}
		catch(Exception e) {
			Kogger.error(ReviewProjectDataLoader.class, e);
			throw new WTException(e);
		}
		finally {
			if(data != null) {
				data.msg = sb.toString();
			}
		}
		return data;
	}	
	
	
	public static ReviewData save(ReviewData data) throws WTException {
		try {
			if(!data.isValidate) {
				return data;
			}
			Kogger.debug(ReviewProjectDataLoader.class, "############################ Save Start");
			Kogger.debug(ReviewProjectDataLoader.class, data.projectNo);
			Kogger.debug(ReviewProjectDataLoader.class, data.planStartDate);
			Kogger.debug(ReviewProjectDataLoader.class, data.planEndDate);
			Kogger.debug(ReviewProjectDataLoader.class, data.projectTypeName);
			Kogger.debug(ReviewProjectDataLoader.class, data.developenttype);
			Kogger.debug(ReviewProjectDataLoader.class, data.salesDeptName);
			Kogger.debug(ReviewProjectDataLoader.class, data.salesID);
			Kogger.debug(ReviewProjectDataLoader.class, data.devDeptName);
			Kogger.debug(ReviewProjectDataLoader.class, data.pmID);
			Kogger.debug(ReviewProjectDataLoader.class, data.proposalDate);
			Kogger.debug(ReviewProjectDataLoader.class, data.estimateDate);
			Kogger.debug(ReviewProjectDataLoader.class, data.projectName);
			Kogger.debug(ReviewProjectDataLoader.class, data.rank);
			Kogger.debug(ReviewProjectDataLoader.class, data.producttype);
			Kogger.debug(ReviewProjectDataLoader.class, data.assembledtype);
			Kogger.debug(ReviewProjectDataLoader.class, data.customereventName);
			Kogger.debug(ReviewProjectDataLoader.class, data.subcontractorName);
			Kogger.debug(ReviewProjectDataLoader.class, data.pastType);
			Kogger.debug(ReviewProjectDataLoader.class, data.developType);
			Kogger.debug(ReviewProjectDataLoader.class, data.pastDesc);
			Kogger.debug(ReviewProjectDataLoader.class, data.improveDesc);
			Kogger.debug(ReviewProjectDataLoader.class, data.reviewDesc);
			Kogger.debug(ReviewProjectDataLoader.class, data.state);
			Kogger.debug(ReviewProjectDataLoader.class, "############################ Save End");
		   
			
		   //Number Code
		   String developenttype = "";
		   String producttype = "";
		   String assembledtype = "";
		   String rank = "";
		   NumberCode developent1Level = null;
		   NumberCode producttype2Level = null;
		   NumberCode assembledtype1Level = null;
		   NumberCode rank2Level = null;
		  
		   
		   if(data.projectTypeName.equals("자동차")){
			   developent1Level = NumberCodeHelper.manager.getNumberCodeName("DEVELOPENTTYPE", data.developenttype);
			   producttype2Level = NumberCodeHelper.manager.getNumberCodeOnlyOneType("PRODUCTTYPE", "자동차", data.producttype);
			   assembledtype1Level = NumberCodeHelper.manager.getNumberCodeName("ASSEMBLEDTYPE", data.assembledtype);
			   rank2Level = NumberCodeHelper.manager.getNumberCodeOnlyOneType("RANK", "자동차", data.rank);
			   developenttype = (developent1Level == null)?"":CommonUtil.getOIDString(developent1Level);
			   producttype = (producttype == null)?"":CommonUtil.getOIDString(producttype2Level);
			   assembledtype = (assembledtype == null)?"":CommonUtil.getOIDString(assembledtype1Level);
			   rank = (rank == null)?"":CommonUtil.getOIDString(rank2Level);
		   }else{
			   developent1Level = NumberCodeHelper.manager.getNumberCodeName("DEVELOPENTTYPE", data.developenttype);
			   producttype2Level = NumberCodeHelper.manager.getNumberCodeOnlyOneType("PRODUCTTYPE", "전자", data.producttype);
			   assembledtype1Level = NumberCodeHelper.manager.getNumberCodeName("ASSEMBLEDTYPE", data.assembledtype);
			   rank2Level = NumberCodeHelper.manager.getNumberCodeOnlyOneType("RANK", "전자", data.rank);
			   developenttype = (developent1Level == null)?"":CommonUtil.getOIDString(developent1Level);
			   producttype = (producttype == null)?"":CommonUtil.getOIDString(producttype2Level);
			   assembledtype = (assembledtype == null)?"":CommonUtil.getOIDString(assembledtype1Level);
			   rank = (rank == null)?"":CommonUtil.getOIDString(rank2Level);
		   }
		   
			
	       String stateCheck = "";
		   if(data.state.equals("진행")){
			   stateCheck = "PROGRESS";
		   }else{
			   stateCheck = "COMPLETED";
		   }
		   
		   ReviewProject project = null;
		   String lifecycle = "KET_REVIEW_PMS_LC";
		   Calendar tempCal = Calendar.getInstance();
		   
		   project = ReviewProject.newReviewProject();
		   
		   ExtendScheduleData schedule = ExtendScheduleData.newExtendScheduleData();
		   if(StringUtil.checkString(data.planStartDate.trim()) && StringUtil.checkString(data.planEndDate.trim())) {
			   int tempDuration = DateUtil.getDaysFromTo(data.planEndDate, data.planStartDate);
			   schedule.setDuration(tempDuration);
		   }
		   schedule.setScheduleHistory(0);
		   
		   if(StringUtil.checkString(data.planStartDate.trim())) {
			   tempCal.setTime(DateUtil.parseDateStr(data.planStartDate));
			   schedule.setPlanStartDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
		   }
		   schedule.setExecWork(0);
		   schedule = (ExtendScheduleData) PersistenceHelper.manager.save(schedule);
		   String folderPath = ConfigImpl.getInstance().getString("folder.project");
	       FolderHelper.assignLocation((FolderEntry)project,  FolderHelper.service.getFolder(folderPath, WCUtil.getWTContainerRef()));
	       LifeCycleTemplate lct = LifeCycleHelper.service.getLifeCycleTemplate(lifecycle, WCUtil.getWTContainerRef());
	       LifeCycleHelper.setLifeCycle(project, lct);
	       //LifeCycleHelper.service.setLifeCycleState(project, State.toState(stateCheck), true);
	       
	       if(StringUtil.checkString(data.proposalDate.trim())) {
			   tempCal.setTime(DateUtil.parseDateStr(data.proposalDate));
			   project.setProposalDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
		   }
	       if(StringUtil.checkString(data.estimateDate.trim())) {
			   tempCal.setTime(DateUtil.parseDateStr(data.estimateDate));
			   project.setEstimateDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
		   }
	       
		   project.setPjtNo(data.projectNo.trim());
		   project.setPjtSeq(0);
		   project.setPjtHistory(0);
		   project.setPjtCompletion(0);
		   project.setLastest(true);
		   project.setPjtSchedule(ObjectReference.newObjectReference(schedule));
		   
		   
		   if(StringUtil.checkString(data.projectName.trim())){
			   project.setPjtName(StringUtil.checkNull(data.projectName.trim()));
		   }
		   project.setPjtState(1);
		   if(data.projectTypeName.equals("자동차")){
			   project.setPjtType(1);
		   }else{
			   project.setPjtType(0);
		   }
		   if(developenttype != null && developenttype.length() > 0) {
			   NumberCode n1 = (NumberCode)CommonUtil.getObject(developenttype);
			   project.setDevelopentType(n1);
			   
		   }
		   if(producttype != null && producttype.length() > 0) {
			   NumberCode n2 = (NumberCode)CommonUtil.getObject(producttype);
			   project.setProductType(n2);
		   }
		   if(assembledtype != null && assembledtype.length() > 0) {
			   NumberCode n3 = (NumberCode)CommonUtil.getObject(assembledtype);
			   project.setAssembledType(n3);
		   }
		   if(rank != null && rank.length() > 0) {
			   NumberCode n4 = (NumberCode)CommonUtil.getObject(rank);
			   project.setRank(n4);
		   }
		   
		   //Development Department
		   People devPeople =PeopleHelper.manager.getPeople(data.pmID);
		   Department depart = null;
		   WTUser wtdevUser = null;
		   if(devPeople != null ){
			   depart = (Department)devPeople.getDepartment();
			   PeopleData pData = new PeopleData(devPeople);
			   wtdevUser = (WTUser) pData.wtuser;
		   }
		   if(depart != null) { project.setDevDept(depart); }
		   if(wtdevUser != null) ProjectUserHelper.manager.setPM(project, wtdevUser, 0);
		  
		   //enterprise Department 
		   project.setAttr1(data.projectTypeName);
		   
		   // salesUser
		   People salesPeople =PeopleHelper.manager.getPeople(data.salesID);
		   if(salesPeople  != null){
			   PeopleData pData = new PeopleData(salesPeople);
			   WTUser wtsalesUser =  (WTUser) pData.wtuser;
			   if(wtsalesUser != null) { project.setBusiness(wtsalesUser); }
		   }
		   
		   
		   //save
		   project = (ReviewProject) PersistenceHelper.manager.save(project);
		   
		   String ce = data.customereventName;
		   String sc = data.subcontractorName;
		   StringTokenizer cetoken = new StringTokenizer(ce, ",");
		   int token1 = 0;
		   String ceValue[] = new String[cetoken.countTokens()];
		   NumberCode nc = null;
		   while(cetoken.hasMoreElements()){
			   ceValue[token1] = cetoken.nextToken();
			   nc = NumberCodeHelper.manager.getNumberCodeName("CUSTOMEREVENT", ceValue[token1]);
        	   if(nc != null){
        		   	ProjectCustomereventLink plink = ProjectCustomereventLink.newProjectCustomereventLink(nc, project);
        	   		PersistenceHelper.manager.save(plink);
        	   }else{
        		   throw new Exception("최종 사용처 "+ ceValue[token1]+" 정보가 없습니다.");
        	   }	
			   token1++;
		   }
		   
		   StringTokenizer sctoken = new StringTokenizer(sc, ",");
		   int token2 = 0;
		   String scValue[] = new String[sctoken.countTokens()];
		   while(sctoken.hasMoreElements()){
			   scValue[token2] = sctoken.nextToken();
			   nc = NumberCodeHelper.manager.getNumberCodeName("SUBCONTRACTOR", scValue[token2]);
        	   if(nc != null){
        		   ProjectSubcontractorLink plink = ProjectSubcontractorLink.newProjectSubcontractorLink(nc, project);
        		   PersistenceHelper.manager.save(plink);
        	   }else{
        		   throw new Exception("개발검토 의뢰처  "+ scValue[token2]+" 정보가 없습니다.");
        	   }
        	   token2++;
		   }
		   //project = (ReviewProject) PersistenceHelper.manager.refresh(project);
		   
		   project = (ReviewProject)LifeCycleHelper.service.setLifeCycleState(project, State.toState(stateCheck), false);
		   
			ReviewDevelop rd = null;
			QuerySpec rdqs = null;
			QueryResult rdqr = null;
			rdqs = new QuerySpec(ReviewDevelop.class);
			rdqs.appendWhere(new SearchCondition(ReviewDevelop.class,"projectReference.key.id","=",CommonUtil.getOIDLongValue(project)), new int[0]); 
			rdqr = PersistenceHelper.manager.find(rdqs);
			if(rdqr.hasMoreElements()){
				rd = (ReviewDevelop)rdqr.nextElement();
			}else{
				rd = ReviewDevelop.newReviewDevelop();
			}
			rd.setProject(project);
			rd.setPastType(data.pastType);
			rd.setDeveloptype(data.developType);
			rd.setPastDesc(data.pastDesc);
			rd.setImproveDesc(data.improveDesc);
			rd.setReviewDesc(data.reviewDesc);
			rd = (ReviewDevelop)PersistenceHelper.manager.save(rd);		
	
		}
		catch(Exception e) {
			Kogger.error(ReviewProjectDataLoader.class, e);
			throw new WTException(e);
		}
		return data;
	}
	
	public static ReviewData update(ReviewData data) throws WTException {
		try {
			if(!data.isValidate) {
				return data;
			}
			Kogger.debug(ReviewProjectDataLoader.class, "############################ update Start");
			Kogger.debug(ReviewProjectDataLoader.class, data.projectNo);
			Kogger.debug(ReviewProjectDataLoader.class, data.planStartDate);
			Kogger.debug(ReviewProjectDataLoader.class, data.planEndDate);
			Kogger.debug(ReviewProjectDataLoader.class, data.projectTypeName);
			Kogger.debug(ReviewProjectDataLoader.class, data.developenttype);
			Kogger.debug(ReviewProjectDataLoader.class, data.salesDeptName);
			Kogger.debug(ReviewProjectDataLoader.class, data.salesID);
			Kogger.debug(ReviewProjectDataLoader.class, data.devDeptName);
			Kogger.debug(ReviewProjectDataLoader.class, data.pmID);
			Kogger.debug(ReviewProjectDataLoader.class, data.proposalDate);
			Kogger.debug(ReviewProjectDataLoader.class, data.estimateDate);
			Kogger.debug(ReviewProjectDataLoader.class, data.projectName);
			Kogger.debug(ReviewProjectDataLoader.class, data.rank);
			Kogger.debug(ReviewProjectDataLoader.class, data.producttype);
			Kogger.debug(ReviewProjectDataLoader.class, data.assembledtype);
			Kogger.debug(ReviewProjectDataLoader.class, data.customereventName);
			Kogger.debug(ReviewProjectDataLoader.class, data.subcontractorName);
			Kogger.debug(ReviewProjectDataLoader.class, data.pastType);
			Kogger.debug(ReviewProjectDataLoader.class, data.developType);
			Kogger.debug(ReviewProjectDataLoader.class, data.pastDesc);
			Kogger.debug(ReviewProjectDataLoader.class, data.improveDesc);
			Kogger.debug(ReviewProjectDataLoader.class, data.reviewDesc);
			Kogger.debug(ReviewProjectDataLoader.class, data.state);
			Kogger.debug(ReviewProjectDataLoader.class, "############################ update End");
		   
			
		   //Number Code
		   String developenttype = "";
		   String producttype = "";
		   String assembledtype = "";
		   String rank = "";
		   NumberCode developent1Level = null;
		   NumberCode producttype2Level = null;
		   NumberCode assembledtype1Level = null;
		   NumberCode rank2Level = null;
		  
		   
		   if(data.projectTypeName.equals("자동차")){
			   developent1Level = NumberCodeHelper.manager.getNumberCodeName("DEVELOPENTTYPE", data.developenttype);
			   producttype2Level = NumberCodeHelper.manager.getNumberCodeOnlyOneType("PRODUCTTYPE", "자동차", data.producttype);
			   assembledtype1Level = NumberCodeHelper.manager.getNumberCodeName("ASSEMBLEDTYPE", data.assembledtype);
			   rank2Level = NumberCodeHelper.manager.getNumberCodeOnlyOneType("RANK", "자동차", data.rank);
			   developenttype = (developent1Level == null)?"":CommonUtil.getOIDString(developent1Level);
			   producttype = (producttype == null)?"":CommonUtil.getOIDString(producttype2Level);
			   assembledtype = (assembledtype == null)?"":CommonUtil.getOIDString(assembledtype1Level);
			   rank = (rank == null)?"":CommonUtil.getOIDString(rank2Level);
		   }else{
			   developent1Level = NumberCodeHelper.manager.getNumberCodeName("DEVELOPENTTYPE", data.developenttype);
			   producttype2Level = NumberCodeHelper.manager.getNumberCodeOnlyOneType("PRODUCTTYPE", "전자", data.producttype);
			   assembledtype1Level = NumberCodeHelper.manager.getNumberCodeName("ASSEMBLEDTYPE", data.assembledtype);
			   rank2Level = NumberCodeHelper.manager.getNumberCodeOnlyOneType("RANK", "전자", data.rank);
			   developenttype = (developent1Level == null)?"":CommonUtil.getOIDString(developent1Level);
			   producttype = (producttype == null)?"":CommonUtil.getOIDString(producttype2Level);
			   assembledtype = (assembledtype == null)?"":CommonUtil.getOIDString(assembledtype1Level);
			   rank = (rank == null)?"":CommonUtil.getOIDString(rank2Level);
		   }
		   
			
	       String stateCheck = "";
		   if(data.state.equals("진행")){
			   stateCheck = "PROGRESS";
		   }else{
			   stateCheck = "COMPLETED";
		   }
		   
		   ReviewProject project = null;
		   String lifecycle = "KET_REVIEW_PMS_LC";
		   Calendar tempCal = Calendar.getInstance();
		   
		   //project
		   E3PSProject e3ps = ProjectHelper.getProject(data.projectNo.toUpperCase()); 
		   Object obj = e3ps;
		   project = (ReviewProject)obj;
		   
		   ExtendScheduleData schedule = ExtendScheduleData.newExtendScheduleData();
		   if(StringUtil.checkString(data.planStartDate.trim()) && StringUtil.checkString(data.planEndDate.trim())) {
			   int tempDuration = DateUtil.getDaysFromTo(data.planEndDate, data.planStartDate);
			   schedule.setDuration(tempDuration);
		   }
		   schedule.setScheduleHistory(0);
		   
		   if(StringUtil.checkString(data.planStartDate.trim())) {
			   tempCal.setTime(DateUtil.parseDateStr(data.planStartDate));
			   schedule.setPlanStartDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
		   }
		   schedule.setExecWork(0);
		   schedule = (ExtendScheduleData) PersistenceHelper.manager.save(schedule);
		   //LifeCycleHelper.service.setLifeCycleState(project, State.toState(stateCheck), true);
	       
	       if(StringUtil.checkString(data.proposalDate.trim())) {
			   tempCal.setTime(DateUtil.parseDateStr(data.proposalDate));
			   project.setProposalDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
		   }
	       if(StringUtil.checkString(data.estimateDate.trim())) {
			   tempCal.setTime(DateUtil.parseDateStr(data.estimateDate));
			   project.setEstimateDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
		   }
	       
		   project.setPjtNo(data.projectNo.trim());
		   project.setPjtSeq(0);
		   project.setPjtHistory(0);
		   project.setPjtCompletion(0);
		   project.setLastest(true);
		   project.setPjtSchedule(ObjectReference.newObjectReference(schedule));
		   
		   
		   if(StringUtil.checkString(data.projectName.trim())){
			   project.setPjtName(StringUtil.checkNull(data.projectName.trim()));
		   }
		   project.setPjtState(1);
		   if(data.projectTypeName.equals("자동차")){
			   project.setPjtType(1);
		   }else{
			   project.setPjtType(0);
		   }
		   if(developenttype != null && developenttype.length() > 0) {
			   NumberCode n1 = (NumberCode)CommonUtil.getObject(developenttype);
			   project.setDevelopentType(n1);
			   
		   }
		   if(producttype != null && producttype.length() > 0) {
			   NumberCode n2 = (NumberCode)CommonUtil.getObject(producttype);
			   project.setProductType(n2);
		   }
		   if(assembledtype != null && assembledtype.length() > 0) {
			   NumberCode n3 = (NumberCode)CommonUtil.getObject(assembledtype);
			   project.setAssembledType(n3);
		   }
		   if(rank != null && rank.length() > 0) {
			   NumberCode n4 = (NumberCode)CommonUtil.getObject(rank);
			   project.setRank(n4);
		   }
		   
		   //Development Department
		   People devPeople =PeopleHelper.manager.getPeople(data.pmID);
		   Department depart = null;
		   WTUser wtdevUser = null;
		   if(devPeople != null ){
			   depart = (Department)devPeople.getDepartment();
			   PeopleData pData = new PeopleData(devPeople);
			   wtdevUser = (WTUser) pData.wtuser;
		   }
		   if(depart != null) { project.setDevDept(depart); }
		   if(wtdevUser != null) ProjectUserHelper.manager.setPM(project, wtdevUser, 0);
		  
		   //enterprise Department 
		   project.setAttr1(data.projectTypeName);
		   
		   // salesUser
		   People salesPeople =PeopleHelper.manager.getPeople(data.salesID);
		   if(salesPeople  != null){
			   PeopleData pData = new PeopleData(salesPeople);
			   WTUser wtsalesUser =  (WTUser) pData.wtuser;
			   if(wtsalesUser != null) { project.setBusiness(wtsalesUser); }
		   }
		   
		   //save
		   project = (ReviewProject) PersistenceHelper.manager.save(project);
		   
		   String ce = data.customereventName;
		   String sc = data.subcontractorName;
		   StringTokenizer cetoken = new StringTokenizer(ce, ",");
		   int token1 = 0;
		   String ceValue[] = new String[cetoken.countTokens()];
		   NumberCode nc = null;
			
		   QueryResult result = null;
			QuerySpec qs = new QuerySpec();
			int index = qs.appendClassList(ProjectCustomereventLink.class, true);
			qs.appendWhere(new SearchCondition(ProjectCustomereventLink.class,
													"roleBObjectRef.key.id","=",
														CommonUtil.getOIDLongValue(project)),new int[] {index} );
			result = PersistenceHelper.manager.find(qs);
			
			while(result.hasMoreElements())
			{
				Object[] object = (Object[])result.nextElement();
				ProjectCustomereventLink link = (ProjectCustomereventLink)object[0];
				if(link != null){
				PersistenceHelper.manager.delete(link);
				}
			}		   
		   
		   while(cetoken.hasMoreElements()){
			   ceValue[token1] = cetoken.nextToken();
			   nc = NumberCodeHelper.manager.getNumberCodeName("CUSTOMEREVENT", ceValue[token1]);
        	   if(nc != null){
        		   	ProjectCustomereventLink plink = ProjectCustomereventLink.newProjectCustomereventLink(nc, project);
        	   		PersistenceHelper.manager.save(plink);
        	   }else{
        		   throw new Exception("최종 사용처 "+ ceValue[token1]+" 정보가 없습니다.");
        	   }	
			   token1++;
		   }
		   
		   StringTokenizer sctoken = new StringTokenizer(sc, ",");
		   int token2 = 0;
		   String scValue[] = new String[sctoken.countTokens()];
		    QueryResult result2 = null;
			QuerySpec qs2 = new QuerySpec();
			int index2 = qs2.appendClassList(ProjectSubcontractorLink.class, true);
			qs2.appendWhere(new SearchCondition(ProjectSubcontractorLink.class,
													"roleBObjectRef.key.id","=",
														CommonUtil.getOIDLongValue(project)),new int[] {index2} );
			result2 = PersistenceHelper.manager.find(qs2);
			
			while(result2.hasMoreElements())
			{
				Object[] object = (Object[])result2.nextElement();
				ProjectSubcontractorLink link = (ProjectSubcontractorLink)object[0];
				if(link != null){
				PersistenceHelper.manager.delete(link);
				}
			}
		   
		   while(sctoken.hasMoreElements()){
			   scValue[token2] = sctoken.nextToken();
			   nc = NumberCodeHelper.manager.getNumberCodeName("SUBCONTRACTOR", scValue[token2]);
        	   if(nc != null){
        		   ProjectSubcontractorLink plink = ProjectSubcontractorLink.newProjectSubcontractorLink(nc, project);
        		   PersistenceHelper.manager.save(plink);
        	   }else{
        		   throw new Exception("개발검토 의뢰처  "+ scValue[token2]+" 정보가 없습니다.");
        	   }
        	   token2++;
		   }
		   //project = (ReviewProject) PersistenceHelper.manager.refresh(project);
		   
		   project = (ReviewProject)LifeCycleHelper.service.setLifeCycleState(project, State.toState(stateCheck), false);
		   
			ReviewDevelop rd = null;
			QuerySpec rdqs = null;
			QueryResult rdqr = null;
			rdqs = new QuerySpec(ReviewDevelop.class);
			rdqs.appendWhere(new SearchCondition(ReviewDevelop.class,"projectReference.key.id","=",CommonUtil.getOIDLongValue(project)), new int[0]); 
			rdqr = PersistenceHelper.manager.find(rdqs);
			if(rdqr.hasMoreElements()){
				rd = (ReviewDevelop)rdqr.nextElement();
			}else{
				rd = ReviewDevelop.newReviewDevelop();
			}
			rd.setProject(project);
			rd.setPastType(data.pastType);
			rd.setDeveloptype(data.developType);
			rd.setPastDesc(data.pastDesc);
			rd.setImproveDesc(data.improveDesc);
			rd.setReviewDesc(data.reviewDesc);
			rd = (ReviewDevelop)PersistenceHelper.manager.save(rd);		
			
		}
		catch(Exception e) {
			Kogger.error(ReviewProjectDataLoader.class, e);
			throw new WTException(e);
		}
		return data;
	}
	
	public static Object save(Vector data) throws WTException {
		if(!SERVER) {
			Class argTypes[] = new Class[]{Vector.class};
			Object args[] = new Object[]{data};
			Object obj = null;
			try {
				obj = wt.method.RemoteMethodServer.getDefault().invoke(
						"save",
						ReviewProjectDataLoader.class.getName(),
						null,
						argTypes,
						args);
			}
			catch(RemoteException e) {
				Kogger.error(ReviewProjectDataLoader.class, e);
				throw new WTException(e);
			}
			catch(InvocationTargetException e) {
				Kogger.error(ReviewProjectDataLoader.class, e);
				throw new WTException(e);
			}
			return obj;
		}
		
		Transaction trx = new Transaction();
		
		Vector r = new Vector();
		try {
			trx.start();
			
			for(int i = 0; i < data.size(); i++) {
				ReviewData rev = (ReviewData)data.get(i);
				
				try {
					if(rev.isExist) {
						rev = update(rev);
					} else {
						rev = save((ReviewData)data.get(i));
					}
					rev.isLoadCompleted = true;
				} catch(Exception e) {
					rev.isLoadCompleted = false;
					rev.msg += "\n" + e.getLocalizedMessage();
					throw new Exception(e);
				}
				
				r.add(rev);
			}
			
			trx.commit();
			trx = null;
		}
		catch(Exception e) {
			trx.rollback();
			Kogger.error(ReviewProjectDataLoader.class, e);
		}
		finally {
			if(trx != null) {
				trx = null;
			}
		}
		return r;
	}


	
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ReviewProjectDataLoader rl = new ReviewProjectDataLoader();
		String type = "자동차";
		rl.loadFile("데이터정리양식_프로젝트.xls", type);
		
	}

}

class ReviewData implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public int rownum;
	public String projectNo;
	public String planStartDate;
	public String planEndDate;
	public String projectTypeName;
	public String developenttype;
	public String salesDeptName;
	public String salesID;
	public String devDeptName;
	public String pmID;
	public String proposalDate;
	public String estimateDate;
	public String projectName;
	public String rank;
	public String producttype;
	public String assembledtype;
	public String customereventName;
	public String subcontractorName;
	public String pastTypeName;
	public String developTypeName;
	public String pastDesc;
	public String improveDesc;
	public String reviewDesc;
	public PastType pastType;
	public DevelopType developType;
	public String state;
	
	public String msg;
	
	public boolean isExist = false;
	public boolean isDocNumberSizeValid = true;
	public boolean isValidate = true;
	public boolean isRepPartExist = true;
	
	public boolean isLoadCompleted = false;
	
	public ReviewData(Cell[] cells, int rownum) {
		this.rownum = rownum;
		
		int colIdex = -1;
		projectNo = JExcelUtil.getContent(cells,++colIdex);
		planStartDate = JExcelUtil.getContent(cells,++colIdex);
		planEndDate = JExcelUtil.getContent(cells,++colIdex);
		projectTypeName = JExcelUtil.getContent(cells,++colIdex);
		developenttype = JExcelUtil.getContent(cells,++colIdex);
		salesDeptName = JExcelUtil.getContent(cells,++colIdex);
		salesID = JExcelUtil.getContent(cells,++colIdex);
		devDeptName = JExcelUtil.getContent(cells,++colIdex);
		pmID = JExcelUtil.getContent(cells,++colIdex);
		proposalDate = JExcelUtil.getContent(cells,++colIdex);
		estimateDate = JExcelUtil.getContent(cells,++colIdex);
		projectName = JExcelUtil.getContent(cells,++colIdex);
		rank = JExcelUtil.getContent(cells,++colIdex);
		producttype = JExcelUtil.getContent(cells,++colIdex);
		assembledtype = JExcelUtil.getContent(cells,++colIdex);
		customereventName = JExcelUtil.getContent(cells,++colIdex);
		subcontractorName = JExcelUtil.getContent(cells,++colIdex);
		pastTypeName = JExcelUtil.getContent(cells,++colIdex);
		developTypeName = JExcelUtil.getContent(cells,++colIdex);
		pastType = getPastType();
		developType = getDevelopType();
		pastDesc = JExcelUtil.getContent(cells,++colIdex);
		improveDesc = JExcelUtil.getContent(cells,++colIdex);
		reviewDesc = JExcelUtil.getContent(cells,++colIdex);
		state = JExcelUtil.getContent(cells,++colIdex);
	}
	
	PastType[] pt = PastType.getPastTypeSet();
	DevelopType[] dt = DevelopType.getDevelopTypeSet();

	public PastType getPastType() {
		for( int i=0; i<pt.length; i++ ) {
			if(pt[i].getDisplay().equals(pastTypeName)){
				return  (PastType)EnumeratedTypeUtil.toEnumeratedType(pt[i].getStringValue());
			}
		}
		return null;
	}
	
	public DevelopType getDevelopType() {
		for( int i=0; i<dt.length; i++ ) {
			if(dt[i].getDisplay().equals(developTypeName)){
				return  (DevelopType)EnumeratedTypeUtil.toEnumeratedType(dt[i].getStringValue());
			}
		}
		return null;
	}


	
	
	
}	


