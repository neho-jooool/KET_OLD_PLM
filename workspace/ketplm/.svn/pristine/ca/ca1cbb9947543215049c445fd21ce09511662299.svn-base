package e3ps.project.historyprocess;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import wt.fc.ObjectReference;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.WTObject;
import wt.folder.Folder;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleManaged;
import wt.lifecycle.State;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.pom.Transaction;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.util.WCUtil;
import e3ps.project.CheckoutLink;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSTask;
import e3ps.project.ScheduleData;
import e3ps.project.TemplateProject;
import e3ps.project.TemplateTask;
import e3ps.project.beans.DefaultProjectTreeNode;
import e3ps.project.beans.ProjectHelper;
import e3ps.project.beans.ProjectTreeModel;
import e3ps.project.beans.ProjectTreeNode;
import e3ps.project.beans.ProjectTreeNodeData;
import e3ps.project.beans.ProjectUserHelper;
import e3ps.project.beans.TaskHelper;
import e3ps.project.beans.TemplateProjectModel;
import e3ps.project.beans.TemplateProjectTreeNode;
import e3ps.project.beans.TemplateProjectTreeNodeData;
import ext.ket.shared.log.Kogger;

public class HistoryHelper implements RemoteAccess {
	static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
	public static int DEFULT_TYPE = 0;
	public static int PLAN_MODIFY_TYPE = 1;
	public static int ETC = -1;

	public static boolean checkOutIn(TemplateProject original)throws Exception {
		if (!SERVER) {
			Class argTypes[] = new Class[]{TemplateProject.class};
			Object args[] = new Object[]{original};
			try {
				Object obj = RemoteMethodServer.getDefault().invoke("checkOutIn", HistoryHelper.class.getName() , null, argTypes, args);
				return ((Boolean)obj).booleanValue();
			} catch (RemoteException e) {
				Kogger.error(HistoryHelper.class, e);
				throw new WTException(e);
			} catch (InvocationTargetException e) {
				Kogger.error(HistoryHelper.class, e);
				throw new WTException(e);
			}
		}

		Transaction transaction = new Transaction();
		try{
			transaction.start();

			CheckoutLink link = HistoryHelper.checkOut(original,"일정 변경",1);
			HistoryHelper.checkIn(link.getWorkingCopy());

			transaction.commit();
	  	   	transaction = null;
	  	   	return true;

		}catch ( Exception e ) {
	  		   if ( transaction != null) {

	  			   transaction.rollback();
	  		   }

	  		   transaction = null;
	  		   Kogger.error(HistoryHelper.class, e);
			}
        return false;
	}

	public static CheckoutLink checkOut(TemplateProject original, String changMsg, int changType)throws Exception {
		if (!SERVER) {
			Class argTypes[] = new Class[]{TemplateProject.class, String.class, int.class};
			Object args[] = new Object[]{original, changMsg, changType};
			try {

				Object obj = RemoteMethodServer.getDefault().invoke("checkOut", HistoryHelper.class.getName() , null, argTypes, args);
				return (CheckoutLink)obj;

			} catch (RemoteException e) {
				Kogger.error(HistoryHelper.class, e);
				throw new WTException(e);
			} catch (InvocationTargetException e) {
				Kogger.error(HistoryHelper.class, e);
				throw new WTException(e);
			}
		}

        return checkOut(original, changMsg, changType, "");
	}

	public static CheckoutLink checkOut(TemplateProject original, String changMsg, int changType, String state)throws Exception {
		if (!SERVER) {
			Class argTypes[] = new Class[]{TemplateProject.class, String.class, int.class, String.class};
			Object args[] = new Object[]{original, changMsg, changType, state};
			try {

				Object obj = RemoteMethodServer.getDefault().invoke("checkOut", HistoryHelper.class.getName() , null, argTypes, args);
				return (CheckoutLink)obj;

			} catch (RemoteException e) {
				Kogger.error(HistoryHelper.class, e);
				throw new WTException(e);
			} catch (InvocationTargetException e) {
				Kogger.error(HistoryHelper.class, e);
				throw new WTException(e);
			}
		}
		String lifeCycleName = ((LifeCycleManaged) original).getLifeCycleName();
        Folder folder = FolderHelper.service.getFolder((FolderEntry) original);
        return checkOut(original, lifeCycleName, folder, changMsg, changType, state);
	}

    /**
     * 함수명 : checkOut
     * @param original
     * @param lifeCycleName
     * @param folder
     * @param changMsg
     * @param changType
     * @param state
     * @return CheckoutLink
     * @throws Exception
     * 수정자 : BoLee
     * 수정일자 : 2013. 6. 24.
     * 수정내용 : [PLM System 1차개선] 일정변경사유 저장 없이 일정변경 상태만 변경할 수 있도록 수정
     */
	public static CheckoutLink checkOut(TemplateProject original, String lifeCycleName, Folder folder, String changMsg, int changType, String state)throws Exception{
		if (!SERVER) {
			Class argTypes[] = new Class[]{TemplateProject.class, String.class, Folder.class, String.class, int.class, String.class};
			Object args[] = new Object[]{original, lifeCycleName, folder, changMsg, changType, state};
			try {

				Object obj = RemoteMethodServer.getDefault().invoke("checkOut", HistoryHelper.class.getName() , null, argTypes, args);
				return (CheckoutLink)obj;

			} catch (RemoteException e) {
				Kogger.error(HistoryHelper.class, e);
				throw new WTException(e);
			} catch (InvocationTargetException e) {
				Kogger.error(HistoryHelper.class, e);
				throw new WTException(e);
			}
		}

		Transaction transaction = new Transaction();
		CheckoutLink link = null;
    	try {
    		transaction.start();

			boolean isLastest = original.isLastest();
			boolean isCheckOut = original.isCheckOut();
			if (!isLastest) {
				throw new Exception("최신이 아닙니다.");
			}
			if (isCheckOut) {
				throw new Exception("이미 체크아웃 되었습니다.");
			}

			TemplateProject workingCopy = null;

			workingCopy = (TemplateProject)original.duplicate();
			workingCopy.setTeamId(null);
			workingCopy.setModifier(SessionHelper.manager.getPrincipalReference());

			ScheduleData oldScheduleData = (ScheduleData)original.getPjtSchedule().getObject();
			ScheduleData scheduleData = (ScheduleData)duplicate(oldScheduleData);
			scheduleData = (ScheduleData)PersistenceHelper.manager.save(scheduleData);

            FolderHelper.assignLocation((FolderEntry) workingCopy, folder);
            LifeCycleHelper.setLifeCycle((LifeCycleManaged) workingCopy, LifeCycleHelper.service.getLifeCycleTemplate(lifeCycleName, WCUtil.getWTContainerRef()));

			/*
			FolderHelper.assignFolder(workingCopy, FolderHelper.service
					.getFolder(project));
			LifeCycleHelper.setLifeCycle(workingCopy, LifeCycleHelper.service
					.getLifeCycleTemplate(project));
			*/

			workingCopy.setWorkingCopy(true);
			workingCopy.setCheckOut(true);
			workingCopy.setCheckOutState("wrk");

			// [START] [PLM System 1차개선] 일정변경구분 값이 999(일정변경 상태 변경만 하는 경우)가 아닐 경우에만 일정변경사유 저장, 2013-06-24, BoLee
			// 일정변경구분 값 : Project 상세정보에서 [일정변경] 버튼 클릭 시 999, 금형 Project [디버깅생성] 버튼 클릭 시 디버깅사유와 동일함
			if ( changType != 999 ) {

			    workingCopy.setHistoryNote(changMsg);
			    workingCopy.setHistoryNoteType(changType);
                workingCopy.setHistoryNoteWebEditor( StringUtil.checkNull(changMsg).replaceAll("\n", "<br>") );
                workingCopy.setHistoryNoteWebEditorText( StringUtil.checkNull(changMsg).replaceAll("\n", "<br>") );
			}
			else {

                workingCopy.setHistoryNote("");
                workingCopy.setHistoryNoteType(changType);
                workingCopy.setHistoryNoteWebEditor("");
                workingCopy.setHistoryNoteWebEditorText("");
			}
            // [END] [PLM System 1차개선] 일정변경구분 값이 999(일정변경 상태 변경만 하는 경우)가 아닐 경우에만 일정변경사유 저장, 2013-06-24, BoLee

			workingCopy.setPjtSchedule(ObjectReference.newObjectReference(scheduleData));

			//
			workingCopy = (TemplateProject) PersistenceHelper.manager.save(workingCopy);

			if(state != null && state.length() > 0){
			//	Kogger.debug(getClass(), "state ========= " + state);
				workingCopy = (TemplateProject)LifeCycleHelper.service.setLifeCycleState(workingCopy, wt.lifecycle.State.toState(state), true);
			}

			WTObject wtobjectCopy = workingCopy;
//			e3ps.common.workflow.WorkProcessHelper.setAssignee(wtobjectCopy, SessionHelper.manager.getPrincipal());

	        original.setCheckOut(true);
	        original.setCheckOutState("c/o");

	        //
	       // original = (E3PSProject)LifeCycleHelper.service.setLifeCycleState(original, State.toState("PLANCHANGE"), true);
			original = (TemplateProject) PersistenceHelper.manager.save(original);


			TaskHelper.manager.copyRojectDeptRole(workingCopy, original);
			ProjectUserHelper.manager.copyProjectUserInfo(workingCopy, original);
			ProjectUserHelper.manager.copyProjectViewDepartMentInfo(workingCopy, original);

			if (original instanceof E3PSProject) {

				ProjectHelper.manager.copyProjectRef((E3PSProject)workingCopy, (E3PSProject)original);

				TaskHelper.manager.copyProjectFromProject((E3PSProject)workingCopy, (E3PSProject)original);
			}else{
				TaskHelper.manager.copyTemplateProjectFromTemplateProject(workingCopy, original);
			}

			link = CheckoutLink.newCheckoutLink(original, workingCopy);
			PersistenceHelper.manager.save(link);
			transaction.commit();
   	   	    transaction = null;
 	   } catch (Exception e) {
 		   Kogger.error(HistoryHelper.class, e);
 		   throw new WTException(e);
 	   }finally{
 		   if(transaction!=null){
 			   transaction.rollback();
 		   }
 	   }
		return link;
	}

	public static TemplateProject checkIn(TemplateProject project, String historyNote)throws Exception{
		if (!SERVER) {
			Class argTypes[] = new Class[]{TemplateProject.class, String.class};
			Object args[] = new Object[]{project, historyNote};
			try {
				Object obj = RemoteMethodServer.getDefault().invoke("checkIn", HistoryHelper.class.getName() , null, argTypes, args);
				return (TemplateProject)obj;

			} catch (RemoteException e) {
				Kogger.error(HistoryHelper.class, e);
				throw new WTException(e);
			} catch (InvocationTargetException e) {
				Kogger.error(HistoryHelper.class, e);
				throw new WTException(e);
			}
		}
		return checkIn(project, DEFULT_TYPE, historyNote);

	}

	/**
	 * 함수명 : checkIn
	 * 수정내용 : [PLM System 1차개선] Project 이력 Iteration 설정 추가
	 * @param project
	 * @param historyType
	 * @param historyNote
	 * @return
	 * @throws Exception
	 * 수정자 : BoLee
	 * 수정일자 : 2013. 7. 4.
	 */
	public static TemplateProject checkIn(TemplateProject project, int historyType, String historyNote)throws Exception{

		if (!SERVER) {
			Class argTypes[] = new Class[]{TemplateProject.class, int.class, String.class};
			Object args[] = new Object[]{project, new Integer(historyType), historyNote};
			try {
				Object obj = RemoteMethodServer.getDefault().invoke("checkIn", HistoryHelper.class.getName() , null, argTypes, args);
				return (TemplateProject)obj;

			} catch (RemoteException e) {
				Kogger.error(HistoryHelper.class, e);
				throw new WTException(e);
			} catch (InvocationTargetException e) {
				Kogger.error(HistoryHelper.class, e);
				throw new WTException(e);
			}
		}
		Transaction transaction = new Transaction();

    	try {
    		transaction.start();

			if (!project.isWorkingCopy()) {
				throw new Exception("체크인을 할 수 없습니다.");
			}

			QueryResult rs = PersistenceHelper.manager.navigate(project,
					CheckoutLink.ORIGINAL_COPY_ROLE, CheckoutLink.class);

			TemplateProject original = null;
			if (rs.hasMoreElements()) {
				original = (TemplateProject) rs.nextElement();
			}

			if (original == null) {
				throw new Exception("원본 객체가 존재하지 않습니다.");
			}
			Kogger.debug(HistoryHelper.class, "check In~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			original.setLastest(false);
			original.setCheckOut(false);
			original.setCheckOutState("c/i");

			original = (TemplateProject)PersistenceHelper.manager.save(original);

			project.setCheckOut(false);
			project.setWorkingCopy(false);
			project.setCheckOutState("c/i");
			project.setPjtHistory(original.getPjtHistory() + 1);
			// [START] [PLM System 1차개선] Project 이력 Iteration 설정 추가, 2013-07-04, BoLee
			project.setPjtIteration(0);
            // [END] [PLM System 1차개선] Project 이력 Iteration 설정 추가, 2013-07-04, BoLee
			project.setLastest(true);
			//project.setHistoryNote(historyNote);
			//project.setHistoryNoteType(historyType);

			project = (TemplateProject) PersistenceHelper.manager.save(project);
			transaction.commit();
	   	    transaction = null;
	   } catch (Exception e) {
		   Kogger.error(HistoryHelper.class, e);
		   throw new WTException(e);
	   }finally{
		   if(transaction!=null){
			   transaction.rollback();
		   }
	   }
		return project;
	}

    /**
     * 함수명 : checkInWithoutApproval
     * 설명 : [PLM System 1차개선] 결재 프로세스 없이 일정 변경 완료하는 경우(기존 단계 일정 내에서 일정 변경하는 경우) Project Check In & Project 상태 변경 (PROGRESS)
     * @param project
     * @return
     * @throws Exception
     * 작성자 : BoLee
     * 작성일자 : 2013. 7. 4.
     */
	public static TemplateProject checkInWithoutApproval(TemplateProject project)throws Exception{

        if (!SERVER) {

            Class argTypes[] = new Class[]{TemplateProject.class};
            Object args[] = new Object[]{project};

            try {
                Object obj = RemoteMethodServer.getDefault().invoke("checkInWithoutApproval", HistoryHelper.class.getName() , null, argTypes, args);
                return (TemplateProject)obj;

            }
            catch (RemoteException e) {
                Kogger.error(HistoryHelper.class, e);
                throw new WTException(e);
            }
            catch (InvocationTargetException e) {
                Kogger.error(HistoryHelper.class, e);
                throw new WTException(e);
            }
        }

        Transaction transaction = new Transaction();
        QueryResult qResult = null;
        TemplateProject original = null;

        try {

            transaction.start();

            // 체크인 대상 객체가 Working Copy가 아닐 경우 Exception 발생
            if ( !project.isWorkingCopy() ) {
                throw new Exception("체크인을 할 수 없습니다.");
            }

            System.out.println("=============( Project 삭제되는 현상에 대한 원인 검증용 Logging!! )=============================");
            System.out.println(" project.isWorkingCopy()  : "+project.isWorkingCopy());
            System.out.println("1-1. Before API Call : PersistenceHelper.manager.navigate(project, CheckoutLink.ORIGINAL_COPY_ROLE, CheckoutLink.class) ");
	
            // 원본 객체 검색
            qResult = PersistenceHelper.manager.navigate(project, CheckoutLink.ORIGINAL_COPY_ROLE, CheckoutLink.class);

            System.out.println("1-1. API Execution Done : PersistenceHelper.manager.navigate(project, CheckoutLink.ORIGINAL_COPY_ROLE, CheckoutLink.class) ");
            
            if ( qResult.hasMoreElements() ) {
                original = (TemplateProject)qResult.nextElement();
            }

            if ( original == null ) {
                throw new Exception("원본 객체가 존재하지 않습니다.");
            }

            // 원본 객체 저장

            original.setLastest(false);
            original.setCheckOut(false);
            original.setCheckOutState("c/i");

            System.out.println("1-2. Before API Call : (TemplateProject)PersistenceHelper.manager.save(original) ");
            
            original = (TemplateProject)PersistenceHelper.manager.save(original);

            System.out.println("1-2. API Execution Done : (TemplateProject)PersistenceHelper.manager.save(original) ");
            
            // 체크인 대상 객체 저장
            project.setCheckOut(false);
            project.setWorkingCopy(false);
            project.setCheckOutState("c/i");
            project.setPjtHistory(original.getPjtHistory());            // Project 이력은 이전 버전 유지
            project.setPjtIteration(original.getPjtIteration() + 1);    // Project 이력 Iteration 증가
            project.setLastest(true);

            System.out.println("1-3. Before API Call : (TemplateProject) PersistenceHelper.manager.save(project) ");
            project = (TemplateProject) PersistenceHelper.manager.save(project);
            System.out.println("1-3. API Execution Done : (TemplateProject) PersistenceHelper.manager.save(project) ");
            
            // Project 상태 변경 (PROGRESS)
            System.out.println("1-4. Before API Call : (TemplateProject)LifeCycleHelper.service.setLifeCycleState(project, State.toState(), true) ");
            project = (TemplateProject)LifeCycleHelper.service.setLifeCycleState(project, State.toState("PROGRESS"), true);
            System.out.println("1-4. API Execution Done : (TemplateProject)LifeCycleHelper.service.setLifeCycleState(project, State.toState(), true) ");
            
            transaction.commit();
            transaction = null;
        }
        catch (Exception e) {
           Kogger.error(HistoryHelper.class, e);
           throw new WTException(e);
        }
        finally {
           if (transaction != null) {
               transaction.rollback();
           }
        }

        return project;
    }

	public static TemplateProject checkIn(TemplateProject project)
			throws Exception {

		if (!SERVER) {
			Class argTypes[] = new Class[]{TemplateProject.class};
			Object args[] = new Object[]{project};
			try {

				Object obj = RemoteMethodServer.getDefault().invoke("checkIn", HistoryHelper.class.getName() , null, argTypes, args);
				return (TemplateProject)obj;

			} catch (RemoteException e) {
				Kogger.error(HistoryHelper.class, e);
				throw new WTException(e);
			} catch (InvocationTargetException e) {
				Kogger.error(HistoryHelper.class, e);
				throw new WTException(e);
			}
		}
		return checkIn(project, DEFULT_TYPE, "");
	}

	public static void undoCheckOut(TemplateProject project)
	throws Exception {

		if (!SERVER) {
			Class argTypes[] = new Class[]{TemplateProject.class};
			Object args[] = new Object[]{project};
			try {

				Object obj = RemoteMethodServer.getDefault().invoke("undoCheckOut", HistoryHelper.class.getName() , null, argTypes, args);
				return;

			} catch (RemoteException e) {
				Kogger.error(HistoryHelper.class, e);
				throw new WTException(e);
			} catch (InvocationTargetException e) {
				Kogger.error(HistoryHelper.class, e);
				throw new WTException(e);
			}
		}


		if (!project.isWorkingCopy()) {
			throw new Exception("undoCheckOut 할 수 없습니다.");
		}

		PersistenceHelper.manager.delete(project);
	}

	public static Persistable duplicate(Persistable obj)
    throws WTException
	{
			Persistable copyObj = null;
	    try
	    {
	        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
	        ObjectOutputStream objectoutputstream = new ObjectOutputStream(bytearrayoutputstream);
	        objectoutputstream.writeObject(obj);
	        objectoutputstream.flush();
	        ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(bytearrayoutputstream.toByteArray());
	        ObjectInputStream objectinputstream = new ObjectInputStream(bytearrayinputstream);
	        copyObj = (Persistable)objectinputstream.readObject();
	        objectoutputstream.close();
	        objectinputstream.close();
	        if(copyObj instanceof Persistable){
	            PersistenceHelper.makeNonPersistent(copyObj);
	        }
	    }
	    catch(Exception exception)
	    {

	    }
	    return copyObj;
	}

	public static void main(String args[])throws Exception{

	/*
		TemplateProject oldProject = (TemplateProject) CommonUtil.getObject("e3ps.project.TemplateProject:20442216");
		TemplateProject newProject = (TemplateProject) CommonUtil.getObject("e3ps.project.TemplateProject:20442260");

		TemplateProjectTreeNode node1 = TemplateProjectModel.getLoadTree(newProject, null);

		TemplateProjectTreeNode node2 = TemplateProjectModel.getLoadTree(oldProject, null);
		*/
		//HistoryHelper.checkOut(gg);

	//	TemplateProject templateProject = (TemplateProject)CommonUtil.getObject("e3ps.project.TemplateProject:20440230");
	  //  HistoryHelper.undoCheckOut(gg);
	}

	public static ProjectTreeNode getCompareProject(E3PSProject newProject, E3PSProject oldProject, HashMap hashMap){

		if (!SERVER) {
			Class argTypes[] = new Class[]{E3PSProject.class, E3PSProject.class, HashMap.class};
			Object args[] = new Object[]{newProject, oldProject, hashMap};
			try {

				Object obj = RemoteMethodServer.getDefault().invoke("getCompareProject", HistoryHelper.class.getName() , null, argTypes, args);
				return (ProjectTreeNode)obj;

			}  catch (Exception e) {
				Kogger.error(HistoryHelper.class, e);
				//throw new WTException(e);
			}
		}

		System.out.println("2-2. getCompareProject() 시작!!");
		
		System.out.println("2-2. Before API Call : ProjectTreeModel.getLoadTree(newProject) ");
		ProjectTreeNode newNode = ProjectTreeModel.getLoadTree(newProject);
		System.out.println("2-2. API Execution Done : ProjectTreeModel.getLoadTree(newProject) ");
		
		System.out.println("2-3. Before API Call : ProjectTreeModel.getLoadTree(oldProject) ");
		ProjectTreeNode oldNode = ProjectTreeModel.getLoadTree(oldProject);
		System.out.println("2-3. API Execution Done : ProjectTreeModel.getLoadTree(oldProject) ");
		
		System.out.println("2-4. Before API Call : (ProjectTreeNodeData)newNode.getUserObject() ");
		ProjectTreeNodeData newData = (ProjectTreeNodeData)newNode.getUserObject();
		System.out.println("2-4. API Execution Done : (ProjectTreeNodeData)newNode.getUserObject() ");
		
		System.out.println("2-5. Before API Call : (ProjectTreeNodeData)oldNode.getUserObject() ");
		ProjectTreeNodeData oldData = (ProjectTreeNodeData)oldNode.getUserObject();
		System.out.println("2-5. API Execution Done : (ProjectTreeNodeData)oldNode.getUserObject() ");

		int result = DefaultProjectTreeNode.MODIFY;

		if(newData.getDuration() == oldData.getDuration()){
			result = DefaultProjectTreeNode.DEFAULT;
		}
		newNode.setCompareResult(result);
		
		System.out.println("2-6. Before API Call : compareNode(newNode, oldNode, hashMap) ");
		compareNode(newNode, oldNode, hashMap);
		System.out.println("2-6. Before API Call : compareNode(newNode, oldNode, hashMap) ");
		
		return newNode;
	}

	public static void compareNode(ProjectTreeNode newNode, ProjectTreeNode oldNode, HashMap hash){

		HashMap map = new HashMap();

		for(int i = 0; i < newNode.getChildCount(); i++){
			ProjectTreeNode child = (ProjectTreeNode)newNode.getChildAt(i);
			ProjectTreeNodeData data = (ProjectTreeNodeData)child.getUserObject();
			E3PSTask task = (E3PSTask)data.getData();
			map.put(task.getTaskName(), child);
		}

		for(int i = 0; i < oldNode.getChildCount(); i++){

			ProjectTreeNode child = (ProjectTreeNode)oldNode.getChildAt(i);
			ProjectTreeNodeData data = (ProjectTreeNodeData)child.getUserObject();
			E3PSTask task = (E3PSTask)data.getData();
			ProjectTreeNode compareNode = (ProjectTreeNode)map.get(task.getTaskName());

			if(compareNode == null){
				setCompareResultWithChild(child, DefaultProjectTreeNode.DELTE);//child.setCompareResult(DefaultProjectTreeNode.DELTE);
				newNode.add(child);
			}else{
				ProjectTreeNodeData cdata = (ProjectTreeNodeData)compareNode.getUserObject();

				boolean isStartTimeEqual = cdata.getPlanStartDate().getTime() == data.getPlanStartDate().getTime();
				boolean isEndTimeEqual = cdata.getPlanEndDate().getTime() == data.getPlanEndDate().getTime();
				if( isStartTimeEqual &&  isEndTimeEqual){
					compareNode.setCompareResult(DefaultProjectTreeNode.DEFAULT);
				}else{
					compareNode.setCompareResult(DefaultProjectTreeNode.MODIFY);
				}
				compareNode.setCompareTask(task);
				compareNode(compareNode, child, hash);

			}
		}
	}

	public static TemplateProjectTreeNode getCompareProject(TemplateProject newProject, TemplateProject oldProject){

		if (!SERVER) {
			Class argTypes[] = new Class[]{TemplateProject.class, TemplateProject.class};
			Object args[] = new Object[]{newProject, oldProject};
			try {

				Object obj = RemoteMethodServer.getDefault().invoke("getCompareProject", HistoryHelper.class.getName() , null, argTypes, args);
				return (TemplateProjectTreeNode)obj;

			}  catch (Exception e) {
				Kogger.error(HistoryHelper.class, e);
				//throw new WTException(e);
			}
		}

		//TemplateProjectTreeNode node1 = (TemplateProjectTreeNode) CommonUtil.getObject("e3ps.project.TemplateProject:20442216");
		//TemplateProjectTreeNode node2 = (TemplateProjectTreeNode) CommonUtil.getObject("e3ps.project.TemplateProject:20442260");

		TemplateProjectTreeNode node1 = TemplateProjectModel.getLoadTree(newProject, null);
		TemplateProjectTreeNode node2 = TemplateProjectModel.getLoadTree(oldProject, null);

		TemplateProjectTreeNodeData data1 = (TemplateProjectTreeNodeData)node1.getUserObject();
		TemplateProjectTreeNodeData data2 = (TemplateProjectTreeNodeData)node2.getUserObject();

		int result = DefaultProjectTreeNode.MODIFY;

		if(data1.getDuration() == data2.getDuration()){
			result = DefaultProjectTreeNode.DEFAULT;
		}

		node1.setCompareResult(result);

		compareNode(node1, node2);
		return node1;
	}

	public static void compareNode(TemplateProjectTreeNode newNode, TemplateProjectTreeNode oldNode){

		HashMap map = new HashMap();
		for(int i = 0; i < newNode.getChildCount(); i++){
			TemplateProjectTreeNode child = (TemplateProjectTreeNode)newNode.getChildAt(i);
			TemplateProjectTreeNodeData data = (TemplateProjectTreeNodeData)child.getUserObject();
			TemplateTask task = (TemplateTask)data.getData();
			map.put(task.getTaskName(), child);
		}

		for(int i = 0; i < oldNode.getChildCount(); i++){
			TemplateProjectTreeNode child = (TemplateProjectTreeNode)oldNode.getChildAt(i);
			TemplateProjectTreeNodeData data = (TemplateProjectTreeNodeData)child.getUserObject();
			TemplateTask task = (TemplateTask)data.getData();
			TemplateProjectTreeNode compareNode = (TemplateProjectTreeNode)map.get(task.getTaskName());

			if(compareNode == null){
				setCompareResultWithChild(child, DefaultProjectTreeNode.DELTE);//child.setCompareResult(DefaultProjectTreeNode.DELTE);
				newNode.add(child);
			}else{
				TemplateProjectTreeNodeData cdata = (TemplateProjectTreeNodeData)compareNode.getUserObject();
				if(cdata.getDuration() == data.getDuration()){
					compareNode.setCompareResult(DefaultProjectTreeNode.DEFAULT);
				}else{
					compareNode.setCompareResult(DefaultProjectTreeNode.MODIFY);
				}
				compareNode.setCompareTask(task);
				compareNode(compareNode, child);
			}
		}
	}

	public static void setCompareResultWithChild(DefaultProjectTreeNode node, int compareResult){
		node.setCompareResult(compareResult);
		for(int i = 0; i < node.getChildCount(); i++){
			DefaultProjectTreeNode child = (DefaultProjectTreeNode)node.getChildAt(i);
			setCompareResultWithChild(child, compareResult);
		}

	}

	public static QueryResult getHistory(Object obj)throws Exception{
		QueryResult qr = null;
		QuerySpec  qs = null;
		long loid;
		Class target = null;
		qs = new QuerySpec();

		if(obj instanceof TemplateProject ){
			target = TemplateProject.class;
			TemplateProject template = (TemplateProject)obj;
			loid = template.getMaster().getPersistInfo().getObjectIdentifier().getId();
		}
		else{
			target = E3PSProject.class;
			E3PSProject project = (E3PSProject)obj;
			loid = project.getMaster().getPersistInfo().getObjectIdentifier().getId();
		}

		int idx = qs.addClassList(target , true);
		qs.appendWhere(new SearchCondition(target,"masterReference.key.id", SearchCondition.EQUAL,
				loid),new int[] {idx});
		SearchUtil.setOrderBy(qs,target,idx, "thePersistInfo.theObjectIdentifier.id" , "c1", true);
		qr = PersistenceHelper.manager.find(qs);
		return qr;
	}

    /**
     * 함수명 : saveChangeHistory
     * 설명 : [PLM System 1차개선] 일정변경사유 저장
     * @param original
     * @param changeType
     * @param historyNoteWebEditor
     * @param historyNoteWebEditorText
     * @return TemplateProject
     * @throws Exception
     * 작성자 : BoLee
     * 작성일자 : 2013. 6. 24.
     */
	public static TemplateProject saveChangeHistory(TemplateProject original, Map<String, Object> map) throws Exception {

        boolean isLatest = false;
        boolean isCheckOut = false;
        boolean isWorkingCopy = false;
        Transaction transaction = new Transaction();

        if (!SERVER) {
            Class argTypes[] = new Class[]{TemplateProject.class, Map.class};
            Object args[] = new Object[]{original, map};
            try {

                Object obj = RemoteMethodServer.getDefault().invoke("saveChangeHistory", HistoryHelper.class.getName(), null, argTypes, args);
                return (TemplateProject)obj;

            } catch (RemoteException e) {
                Kogger.error(HistoryHelper.class, e);
                throw new WTException(e);
            } catch (InvocationTargetException e) {
                Kogger.error(HistoryHelper.class, e);
                throw new WTException(e);
            }
        }

        try {
            transaction.start();

            if ( original != null ) {

                isLatest = original.isLastest();
                isCheckOut = original.isCheckOut();
                isWorkingCopy = original.isWorkingCopy();

                if ( !isLatest ) {
                    // 최신 버전이 아닐 경우 Exception 발생
                    throw new Exception("최신이 아닙니다.");
                }

                if ( isCheckOut ) {

                    if ( isWorkingCopy ) {

                        // 체크아웃 상태이고 Working Copy일 경우 일정변경사유 저장

                        //Integer.parseInt(), , historyNoteWebEditorText

                        original.setHistoryNoteType( Integer.parseInt( (String) map.get("historyNoteType") ) );
                        original.setHistoryNoteWebEditor( map.get("historyNoteWebEditor") );
                        original.setHistoryNoteWebEditorText( map.get("historyNoteWebEditorText") );
                        original.setHistoryRole( (String) map.get("historyRoleComma") );

                        original = (TemplateProject)PersistenceHelper.manager.save(original);
                    }
                    else {
                        // 체크아웃 상태이고 Working Copy가 아닐 경우 Exception 발생
                        throw new Exception("이미 체크아웃 되었습니다.");
                    }
                }
            }

            transaction.commit();
            transaction = null;
        }
        catch (Exception e) {
           Kogger.error(HistoryHelper.class, e);
           throw new WTException(e);
        }
        finally {
           if ( transaction != null) {
               transaction.rollback();
           }
        }

        return original;
    }
}
