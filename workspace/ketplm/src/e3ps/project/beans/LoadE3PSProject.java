package e3ps.project.beans;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Vector;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.org.WTUser;
import wt.pom.Transaction;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;
import e3ps.common.code.NumberCode;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.StringUtil;
import e3ps.project.E3PSProject;
//import e3ps.part.PartServerUtil;
import e3ps.project.ExtendScheduleData;
import ext.ket.shared.log.Kogger;

public class LoadE3PSProject implements RemoteAccess {
	public static E3PSProject createJELProject(Hashtable hash) throws Exception {
		if (!wt.method.RemoteMethodServer.ServerFlag) {
			Class argTypes[] = new Class[]{Hashtable.class};
			Object args[] = new Object[]{hash};
			try {
				return (E3PSProject)RemoteMethodServer.getDefault().invoke("createJELProject", "e3ps.project.beans.LoadJELProject", null, argTypes, args);
			} catch (RemoteException e) {
				Kogger.error(LoadE3PSProject.class, e);
				throw new WTException(e);
			} catch (InvocationTargetException e) {
				Kogger.error(LoadE3PSProject.class, e);
				throw new WTException(e);
			}
		}
		
		Transaction tr = new Transaction();
		E3PSProject newPjt = null;
		
		try {
			tr.start();
			//0. Init Value Setting
			Calendar tempCal = Calendar.getInstance();
			//1. Attribute Setting
			String planStartDate = (String) hash.get("planStartDate");									//계획 시작일자
			String planEndDate = (String) hash.get("planEndDate");										//계획 종료일자
			String projectNo = (String) hash.get("projectNo");											//프로젝트NO
			String projectName = (String) hash.get("projectName");										//프로젝트명
			String projectDesc = (String) hash.get("projectDesc");										//프로젝트 설명
			String projectType = (String) hash.get("projectType");										//프로젝트종류
			String projectProduct = (String) hash.get("projectProduct");								//PRODUCT
			String projectAcceptanceDate = (String) hash.get("projectAcceptanceDate");					//수주일자
			String projectDeliveredDate = (String) hash.get("projectDeliveredDate");					//출하일자
			String projectFab = (String) hash.get("projectFab");										//FAB Name
			String projectSite = (String) hash.get("projectSite");										//SITE
			String projectAcceptanceType = (String) hash.get("projectAcceptanceType");					//수주형태
			String projectSaleType = (String) hash.get("projectSaleType");								//판매형태
			String projectConsignment = (String) hash.get("projectConsignment");						//출하조건
			String projectCustomer = (String) hash.get("projectCustomer");								//거래처
			String processoid = (String) hash.get("processoid");										//공정명
			String pmUser = (String) hash.get("pmUser");												//PM(사용자ID)
			
			//2. ExtendScheduleData Object Create
			ExtendScheduleData extSchedule = ExtendScheduleData.newExtendScheduleData();
			//2.1 총 기간 [planEndDate - planStartDate]
			if(StringUtil.checkString(planStartDate.trim()) && StringUtil.checkString(planEndDate.trim())) {
				int tempDuration = DateUtil.getDaysFromTo(planEndDate.trim(), planStartDate.trim());
				extSchedule.setDuration(tempDuration);
			}
			//2.2 이력 [최초 생성 시: 0]
			extSchedule.setScheduleHistory(0);
			//2.3  계획 시작일자 & 실제 시작일자
			if(StringUtil.checkString(planStartDate.trim())) {
				tempCal.setTime(DateUtil.parseDateStr(planStartDate.trim()));
				extSchedule.setPlanStartDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
				extSchedule.setExecStartDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
			}
			//2.4 계획 종료일자
			if(StringUtil.checkString(planEndDate.trim())) {
				tempCal.setTime(DateUtil.parseDateStr(planEndDate.trim()));
				extSchedule.setPlanEndDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
			}
			//2.5 ExtendScheduleData Object Save
			extSchedule = (ExtendScheduleData) PersistenceHelper.manager.save(extSchedule);
			
//			//3. JELProject Object Create
//			newPjt = JELProject.newJELProject();
//			//3.1 프로젝트 NO [ERP Attribute]
//			newPjt.setPjtNo(StringUtil.checkNull(projectNo.trim()));
//			//3.2 프로젝트 명 [ERP Attribute]
//			newPjt.setPjtName(StringUtil.checkNull(projectName.trim()));
//			//3.3 순서 [최초 생성 시: 0]
//			newPjt.setPjtSeq(0);
//			//3.4 설명 [PLM Attribute]
//			newPjt.setPjtDesc(StringUtil.checkNull(projectDesc));
//			//3.5 프로젝트종류 [PLM Attribute] (0: 영업수주, 1: 양산, 2: 개발)
//			if("개발".equalsIgnoreCase(projectType.trim())) {
//				newPjt.setPjtType(2);
//			} else if("양산".equalsIgnoreCase(projectType.trim())) {
//				newPjt.setPjtType(1);
//			}
//			//3.6 이력 [최초 생성 시: 0]
//			newPjt.setPjtHistory(0);
//			//3.7 일정[ExtendScheduleData Object Setting]
//			newPjt.setPjtSchedule(ObjectReference.newObjectReference(extSchedule));
			//3.8 PRODUCT [ERP Attribute]
			/*if(StringUtil.checkString(projectProduct.trim())) {
				newPjt.setProduct(projectProduct.trim());
			}*/
			//3.9 수주일자 [PLM Attribute]
			/*if(StringUtil.checkString(projectAcceptanceDate.trim())) {
				tempCal.setTime(DateUtil.parseDateStr(projectAcceptanceDate.trim()));
				newPjt.setAcceptanceDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
			}*/
			//3.10 출하일자 [ERP Attribute]
			/*if(StringUtil.checkString(projectDeliveredDate.trim())) {
				tempCal.setTime(DateUtil.parseDateStr(projectDeliveredDate.trim()));
				newPjt.setDeliveredDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
			}*/
			//3.11 FAB Name [PLM Attribute]
			/*if(StringUtil.checkString(projectFab.trim())) {
				newPjt.setFabName(projectFab.trim());
			}*/
			//3.12 프로젝트 완료율
//			newPjt.setPjtCompletion(0);
//			//3.13 프로젝트 상태
//			newPjt.setPjtState(ProjectStateHelper.manager.READY);
//			//3.14 마지막 버젼의미 [PLM Attribute - Search용]
//			newPjt.setLastest(true);
//			//3.15 JELProject Object Save
//			newPjt = (JELProject) PersistenceHelper.manager.save(newPjt);
			
			//4. LINK Object Create & JELProject Object Link
			//4.1 SITE Link [PLM Attribute]
			/*
			if(StringUtil.checkString(projectSite.trim())) {
				NumberCode siteNum = getNumberCodeForName("SITE", projectSite.trim());
				if(siteNum != null) {
					newPjt = JELProjectLinkCreate(newPjt, siteNum, "SITE");
				}
			}
			if(StringUtil.checkString(projectAcceptanceType.trim())) {
				NumberCode acceptanceNum = getNumberCodeForName("ACCEPTANCETYPE", projectAcceptanceType.trim());
				if(acceptanceNum != null) {
					newPjt = JELProjectLinkCreate(newPjt, acceptanceNum, "ACCEPTANCETYPE");
				}
			}
			if(StringUtil.checkString(projectSaleType.trim())) {
				NumberCode saleNum = getNumberCodeForName("SALETYPE", projectSaleType.trim());
				if(saleNum != null) {
					newPjt = JELProjectLinkCreate(newPjt, saleNum, "SALETYPE");
				}
			}
			if(StringUtil.checkString(projectConsignment.trim())) {
				NumberCode consignNum = getNumberCodeForName("CONSIGNMENT", projectConsignment.trim());
				if(consignNum != null) {
					newPjt = JELProjectLinkCreate(newPjt, consignNum, "CONSIGNMENT");
				}
			}
			if(StringUtil.checkString(projectCustomer.trim())) {
			   NumberCode customerNum = getNumberCodeForName("CUSTOMER", projectCustomer.trim());
			   if(customerNum != null) {
				   	newPjt = JELProjectLinkCreate(newPjt, customerNum, "CUSTOMER");	
			   }
			}
			*/
			//4.6 공정명 Link [PLM Attribute]
			/*if(hash.get("processoid") != null) {
			   Vector processVec = getParamsVector(hash.get("processoid"));
			   Iterator iter = processVec.iterator();
	           while(iter.hasNext()){
	               NumberCode code = (NumberCode)CommonUtil.getObject(iter.next().toString());
	               if(code != null) {
	            	   ProcessDivisionCodeLink plink = ProcessDivisionCodeLink.newProcessDivisionCodeLink(newPjt, code);
		               PersistenceHelper.manager.save(plink);
		               
		               newPjt = (JELProject) PersistenceHelper.manager.refresh(newPjt);
	               }
	           }
			}*/
			
			//5. PM(WTUser) Link Create
			//5.1 WTUser Object Setting
			WTUser userPM = CommonUtil.findUserID(pmUser);
			//5.2 PM Create
//			ProjectUserHelper.manager.setPM(newPjt, userPM, 0);
//			
//			//6. HistoryManager Object Create
//			//6.1 HistoryManager Object Initial Create(최초생성 시)
//			Hashtable mgmtHash = new Hashtable();
//			
//			mgmtHash.put("JELProject", newPjt);
//			mgmtHash.put("historyType", "PROJECTCREATE");
//			JELProjectHelper.service.createHistoryManager(mgmtHash);
//			
//			//7. MPart Object Create and JELProject Object Link
//			if(!ProjectPartMasterHelper.manager.checkPartMaster(newPjt.getPjtNo().trim())) {
//				Config conf = ConfigImpl.getInstance();
//				JELProjectData pjtData = new JELProjectData(newPjt);
//			   
//				String defaultPath = conf.getString("folder.wtpart.plm"); 		//"/Part/PLM"
//				Folder saveFolder = FolderTaskLogic.getFolder(defaultPath);
//			   
//				Hashtable partHash = new Hashtable();
//				
//				partHash.put("number", pjtData.pjtNo);
//				partHash.put("name", pjtData.pjtNo);
//				partHash.put("view", "Engineering");
//				partHash.put("folder", CommonUtil.getOIDString(saveFolder));
//				partHash.put("type", "product");
//				partHash.put("lifecycleState", "RELEASED");
//			   
//				PartServerUtil.createPart(partHash);
//			}
			
			tr.commit();
			tr = null;
			
			return newPjt;
		} catch (Exception e) {
			Kogger.error(LoadE3PSProject.class, e);
			if(tr != null) tr.rollback();
			tr = null;
		}
		
		return null;
	}
	
	private static NumberCode getNumberCodeForName(String codeType, String name) {
		if (name == null) { return null; }
		try {
			QuerySpec select = new QuerySpec(NumberCode.class);
			select.appendWhere(new SearchCondition(NumberCode.class, NumberCode.CODE_TYPE, SearchCondition.EQUAL, codeType), new int[] { 0 });
			select.appendAnd();
			select.appendWhere(new SearchCondition(NumberCode.class, NumberCode.NAME, SearchCondition.EQUAL, name), new int[] { 0 });
			QueryResult result = PersistenceHelper.manager.find(select);
			while (result != null && result.hasMoreElements()) {
				return (NumberCode) result.nextElement();
			}
		} catch (QueryException e) {
			Kogger.error(LoadE3PSProject.class, e);
		} catch (WTException e) {
			Kogger.error(LoadE3PSProject.class, e);
		} catch (Exception e) {
			Kogger.error(LoadE3PSProject.class, e);
		}
		return null;
	}
	
	private static E3PSProject JELProjectLinkCreate(E3PSProject project, NumberCode numberCode, String codeType) throws WTException {
		/*if("SITE".equalsIgnoreCase(codeType)) {
			JELProjectSiteLink link = JELProjectSiteLink.newJELProjectSiteLink(project, numberCode);
			PersistenceHelper.manager.save(link);
			project = (JELProject) PersistenceHelper.manager.refresh(project);
		}*/
		
		return project;
	}
	
	private static Vector getParamsVector(Object obj){
       Vector vec = new Vector();
       if(obj == null) return vec;
       if(obj instanceof String){
           vec.add(obj.toString());
       }else if(obj instanceof String[]){
           String[] strs = (String[])obj;
           for(int i=0; i < strs.length; i++){
               vec.addElement(strs[i]);
           }
       }else if(obj instanceof Vector){
           vec = (Vector)obj;
       }
       return vec;
   }
}
