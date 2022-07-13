package e3ps.project.beans;

import java.util.Hashtable;
import java.util.Vector;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.code.NumberCodeType;
import e3ps.common.util.CommonUtil;
import e3ps.project.E3PSProject;
//import e3ps.part.PartServerUtil;
import ext.ket.shared.log.Kogger;

public class ProjectSaveAs  implements wt.method.RemoteAccess {

	
//	public static JELProject saveAs(Hashtable hash, JELProject oldPJT) throws Exception  {
//		
//
//		if (!wt.method.RemoteMethodServer.ServerFlag) {
//			Class argTypes[] = new Class[]{Hashtable.class, JELProject.class};
//			Object args[] = new Object[]{hash, oldPJT};
//			try {
//				return (JELProject)RemoteMethodServer.getDefault().invoke("saveAs", "e3ps.project.beans.ProjectSaveAs", null, argTypes, args);
//			} catch (RemoteException e) {
//				Kogger.error(getClass(), e);
//				throw new WTException(e);
//			} catch (InvocationTargetException e) {
//				Kogger.error(getClass(), e);
//				throw new WTException(e);
//			}
//		}
//		
//		Transaction tr = new Transaction();
//		
//		try{
//			tr.start();
//			String projectNo = (String) hash.get("projectNo");	// 프로젝트 번호
//			String projectName = (String) hash.get("projectName"); // 프로젝트 명
//			String projectProduct = (String) hash.get("projectProduct"); // PRODUCT
//			String projectCustomer = (String) hash.get("projectCustomer"); // 거래처 명
//			String projectDeliveredDate = (String) hash.get("projectDeliveredDate"); // 출하일자
//			
//			String projectFab = (String) hash.get("projectFabName");
//			String projectAcceptanceDate = (String) hash.get("projectAcceptanceDate");
//			String projectConsignment = (String) hash.get("projectConsignment");
//			
//			
//			if(projectNo == null && projectNo.length() > 0){
//				projectNo = "";
//			}
//			if(projectName == null && projectName.length() > 0){
//				projectName = "";
//			}
//			if(projectProduct == null && projectProduct.length() > 0){
//				projectProduct = "";
//			}
//			if(projectCustomer == null && projectCustomer.length() > 0){
//				projectCustomer = "";
//			}
//			if(projectDeliveredDate == null && projectDeliveredDate.length() > 0){
//				projectDeliveredDate = "";
//			}
//			if(projectFab == null && projectFab.length() > 0){
//				projectFab = "";
//			}
//			
//			
//			Calendar tempCal = Calendar.getInstance();
////			JELProject newPJT = (JELProject)oldPJT.duplicate();
////			newPJT.setWorkingCopy(false);
////			newPJT.setCheckOut(false);
////			newPJT.setLastest(true);
////			newPJT.setHistoryNote("");
////			newPJT.setHistoryNoteType(0);
////			newPJT.setPjtHistory(0);
//		
//			JELProjectMaster master = JELProjectMaster.newJELProjectMaster();
//			
////			newPJT.setMaster(master);
////			newPJT.setPjtNo(projectNo);
////			newPJT.setPjtName(projectName);
////			newPJT.setProduct(projectProduct);
////			newPJT.setProjectCustomer(projectCustomer);
////			newPJT.setProjectConsignment(projectConsignment);
//			
//			
//			
//		    /*if(StringUtil.checkString(projectAcceptanceDate.trim())) {
//			   SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd" ,Locale.KOREA);
//			   tempCal.setTime(sdf.parse(projectAcceptanceDate.trim()));
//			   newPJT.setAcceptanceDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
//		    }*/
//		   
//		    /*if(StringUtil.checkString(projectDeliveredDate.trim())) {
//			   SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd" ,Locale.KOREA);
//			   tempCal.setTime(sdf.parse(projectDeliveredDate.trim()));
//			   newPJT.setDeliveredDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
//		    }*/
//			
//			
//			
////			ScheduleData oldScheduleData = (ScheduleData)oldPJT.getPjtSchedule().getObject();
////			ScheduleData scheduleData = (ScheduleData)HistoryHelper.duplicate(oldScheduleData);
////			scheduleData = (ScheduleData)PersistenceHelper.manager.save(scheduleData);
//			
//			
//			Folder folder = FolderHelper.service.getFolder((FolderEntry) oldPJT);
//			
////            FolderHelper.assignLocation((FolderEntry) newPJT, folder);
////            LifeCycleHelper.setLifeCycle((LifeCycleManaged) newPJT, LifeCycleHelper.service
////					.getLifeCycleTemplate(oldPJT));
//		
//			/*
//			FolderHelper.assignFolder(newPJT, FolderHelper.service
//					.getFolder(project));
//			LifeCycleHelper.setLifeCycle(newPJT, LifeCycleHelper.service
//					.getLifeCycleTemplate(project));
//	*/
//	
////            newPJT.setPjtSchedule(ObjectReference.newObjectReference(scheduleData));
////			newPJT = (JELProject) PersistenceHelper.manager.save(newPJT);
//	
//	        
//			
////			ProjectUserHelper.manager.copyProjectUserInfo(newPJT, oldPJT);
////			ProjectUserHelper.manager.copyProjectViewDepartMentInfo(newPJT, oldPJT);
//			
//			
//			/*QueryResult rs = PersistenceHelper.manager.navigate(oldPJT, ProcessDivisionCodeLink.PROCESS_DIVISION_CODE_ROLE, ProcessDivisionCodeLink.class);
//			while (rs.hasMoreElements()) {
//				NumberCode processNum = (NumberCode)rs.nextElement();
//				ProcessDivisionCodeLink plink = ProcessDivisionCodeLink.newProcessDivisionCodeLink((JELProject)newPJT, processNum);
//				PersistenceHelper.manager.save(plink);
//			}	*/		
////			TaskHelper.manager.copyProjectFromProject((JELProject)newPJT, (JELProject)oldPJT);
//			
//
////		   boolean checkPart = ProjectPartMasterHelper.manager
////				.checkPartMaster(newPJT.getPjtNo().trim());
////		   if(!checkPart) {
////			   //11.2 JELProject NO와 동일한 WTPartMaster 없을 경우 - WTPartMaster 생성 및 검색/리스트 화면에서 JELProject NO로 WTPartMaster 검색
////			   Config conf = ConfigImpl.getInstance();
////			   JELProjectData pjtData = new JELProjectData(newPJT);
////			   String defaultPath = conf.getString("folder.wtpart.plm");//"/Part/PLM"
////			   Folder saveFolder = FolderTaskLogic.getFolder(defaultPath);
////			   Hashtable partHash = new Hashtable();
////			   
////			   partHash.put("number", pjtData.pjtNo);
////			   partHash.put("name", pjtData.pjtNo);
////			   partHash.put("view", "Engineering");
////			   partHash.put("folder", CommonUtil.getOIDString(saveFolder));
////			   partHash.put("type", "product");
////			   partHash.put("lifecycleState", "RELEASED");
////			   
////			   PartServerUtil.createPart(partHash);
////		   }
//		   
////		   Kogger.debug(getClass(), "newPJT"+newPJT);
////		   tr.commit();
////		   tr = null;
////		   return newPJT;
//		}catch(Exception ex){
//			Kogger.error(getClass(), ex);
//			throw new WTException(ex);
//		}finally{
//			if(tr!=null)tr.rollback();
//		}
//	}
//	
	/*private static JELProject JELProjectSiteLink(JELProject jelProject, NumberCode siteNum) {
		try {
			JELProjectSiteLink link = JELProjectSiteLink.newJELProjectSiteLink(jelProject, siteNum);
			PersistenceHelper.manager.save(link);
		} catch (WTException e) {
			Kogger.error(getClass(), e);
		}
		return jelProject;
	}*/
	   
	   /*private static JELProject JELProjectAcceptanceLink(JELProject jelProject, NumberCode acceptanceNum) {
		   try {
			   JELProjectAcceptanceLink link = JELProjectAcceptanceLink.newJELProjectAcceptanceLink(jelProject, acceptanceNum);
			   PersistenceHelper.manager.save(link);
		   } catch (WTException e) {
			   Kogger.error(getClass(), e);
		   }
		   return jelProject;
	   }*/
	   
	   /*private static JELProject JELProjectMarketingLink(JELProject jelProject, NumberCode saleNum) {
		   try {
			   JELProjectMarketingLink link = JELProjectMarketingLink.newJELProjectMarketingLink(jelProject, saleNum);
			   PersistenceHelper.manager.save(link);
		   } catch (WTException e) {
			   Kogger.error(getClass(), e);
		   }
		   return jelProject;
	   }*/
	   
/*	   private static JELProject JELProjectConsignmentLink(JELProject jelProject, NumberCode consignNum) {
		   try {
			   JELProjectConsignmentLink link = JELProjectConsignmentLink.newJELProjectConsignmentLink(jelProject, consignNum);
			   PersistenceHelper.manager.save(link);
		   } catch (WTException e) {
			   Kogger.error(getClass(), e);
		   }
		   return jelProject;
	   }
	   
	   private static JELProject JELProjectCustomerLink(JELProject jelProject, NumberCode customerNum) {
		   try {
			   JELProjectCustomerLink link = JELProjectCustomerLink.newJELProjectCustomerLink(jelProject, customerNum);
			   PersistenceHelper.manager.save(link);
		   } catch (WTException e) {
			   Kogger.error(getClass(), e);
		   }
		   return jelProject;
	   }*/
	   
	   public static NumberCode getNumberCodeForName(String codeType, String name) {
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
	           Kogger.error(ProjectSaveAs.class, e);
	       } catch (WTException e) {
	           Kogger.error(ProjectSaveAs.class, e);
	       } catch (Exception e) {
	           Kogger.error(ProjectSaveAs.class, e);
	       }

	       return null;
	   }
	   
	   private static NumberCode createNumberCodeObject(String codeType, String code) {
		   NumberCode customer = null;
		   
		   try {
			   customer = NumberCode.newNumberCode();
			   customer.setName(code);
			   Vector vec = NumberCodeHelper.manager.getNumberCodeForQuery(codeType);
			   customer.setCode(CommonUtil.zeroFill(vec.size() + 1, 4));
			   customer.setDescription("");
			   customer.setCodeType(NumberCodeType.toNumberCodeType(codeType));
			   customer = (NumberCode) PersistenceHelper.manager.store(customer);
		   } catch (WTException e) {
			   Kogger.error(ProjectSaveAs.class, e);
		   } catch (WTPropertyVetoException e) {
			   Kogger.error(ProjectSaveAs.class, e);
		   }
		   return customer;
	   }
	   
	   public static void main(String args[])throws Exception{
		   Hashtable hash = new Hashtable();
		   
		   E3PSProject project = (E3PSProject)CommonUtil.getObject("e3ps.project.JELProject:20402412");
	   }
}
