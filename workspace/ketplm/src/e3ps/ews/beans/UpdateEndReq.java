/**
 * 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : UpdateEndReq.java
 * 작성자 : 김경희
 * 작성일자 : 2011. 2. 7.
 */
package e3ps.ews.beans;

import wt.doc.WTDocument;
import wt.doc.WTDocumentMaster;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.method.RemoteMethodServer;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionServerHelper;
import e3ps.common.obj.ObjectData;
import e3ps.common.obj.ObjectUtil;
import e3ps.common.util.CommonUtil;
import e3ps.ews.entity.KETEarlyWarning;
import e3ps.ews.entity.KETEarlyWarningEndReq;
import e3ps.ews.entity.KETEarlyWarningEndReqLink;
import e3ps.ews.entity.KETEarlyWarningResult;
import e3ps.ews.entity.KETEarlyWarningResultLink;
import e3ps.ews.entity.KETEarlyWarningStep;
import e3ps.ews.entity.KETEarlyWarningStepLink;
import e3ps.ews.entity.KETEndReqLink;
import e3ps.wfm.service.KETWfmHelper;
import ext.ket.shared.log.Kogger;

/**
 * 클래스명 : UpdateEndReq
 * 설명 : 해제신청 상태변경, todo 생성
 * 작성자 : 김경희
 * 작성일자 : 2011. 2. 7.
 */
public class UpdateEndReq {

	/**
	 * 함수명 : main
	 * 설명 : 해제신청 상태변경, todo 생성
	 * @param args
	 * 작성자 : 김경희
	 * 작성일자 : 2011. 2. 7.
	 */
	public static void main(String[] args) throws Exception {
		try {
			RemoteMethodServer methodServer = RemoteMethodServer.getDefault();

			/*if( methodServer.getUserName() == null ){
				String [] userInfo = {"wcadmin", "wcadmin"};

				if( args.length >= 2 ){
					userInfo[0] = args[0];
					userInfo[1] = args[1];
				}

				methodServer.setUserName( userInfo[0] );
				methodServer.setPassword( userInfo[1] );

				Kogger.debug(UpdateEndReq.class, "AUTHENTICATION SUCCESS");
			}else {
				Kogger.debug(UpdateEndReq.class, "AUTHENTICATION FAILE");
			}*/
			SessionServerHelper.manager.setAccessEnforced(true);
			Kogger.debug(UpdateEndReq.class, "********************"+"해제신청 todo 시작입니다.");
			
			//초기유동관리 단계 객체
			KETEarlyWarningStep ketEarlyWarningStep = null;
			
			//초기유동관리 지정 객체
			QueryResult isEarlyWarning = null;
			KETEarlyWarningStepLink ketEarlyWarningStepLink = null;
			Object earlyWarningMaster = null; 
			ObjectData earlyWarninMasterData = null;
			String oid = null;
			KETEarlyWarning ketEarlyWarning = null;
			
			//실적보고서 객체
			QueryResult isResult = null;
			KETEarlyWarningResultLink ketEarlyWarningResultLink = null;
			Object resultMaster = null; 
            ObjectData resultMasterData = null;
            String resultOid = null;
            KETEarlyWarningResult ketEarlyWarningResult = null;
            
            //해제신청서 객체
            QueryResult isEndReq = null;
            KETEarlyWarningEndReqLink ketEarlyWarningEndReqLink = null;
            Object endReqMaster = null; 
      	    ObjectData endReqMasterData = null;
			String endReqOid = null;
			KETEarlyWarningEndReq ketEarlyWarningEndReq = null;
			
			//해제판정서 객체
			QueryResult isEnd = null;
            
            boolean endFlag = false;
			
            //실적보고 상태의 초기유동관리 객체 조회
			QuerySpec qs = new QuerySpec();
			int idx = qs.appendClassList(KETEarlyWarningStep.class, true);
			
			SearchCondition sc = new SearchCondition(KETEarlyWarningStep.class, "state.state", SearchCondition.EQUAL, "EWRREPORT");
			qs.appendWhere(sc, new int[] {idx});
			qs.appendAnd();
			SearchCondition sc0 = new SearchCondition(KETEarlyWarningStep.class, "iterationInfo.latest", SearchCondition.IS_TRUE, Boolean.getBoolean("true") );
			qs.appendWhere(sc0, new int[] {idx});
			
			QueryResult qr = PersistenceHelper.manager.find( qs );
			
			while(qr.hasMoreElements()){
				Object[] po = (Object[])qr.nextElement();
				ketEarlyWarningStep = (KETEarlyWarningStep)po[0];
				
				isEarlyWarning = PersistenceHelper.navigate((WTDocumentMaster)ketEarlyWarningStep.getMaster(), KETEarlyWarningStepLink.ROLE_BOBJECT_ROLE, KETEarlyWarningStepLink.class, false);
				while(isEarlyWarning.hasMoreElements()){
					ketEarlyWarningStepLink = (KETEarlyWarningStepLink)isEarlyWarning.nextElement();
					earlyWarningMaster = (Object)ObjectUtil.getLatestObject((WTDocumentMaster)ketEarlyWarningStepLink.getRoleBObject());
					earlyWarninMasterData = new ObjectData((WTDocument)earlyWarningMaster);
				}
				oid = earlyWarninMasterData.getOid();
				ketEarlyWarning = (KETEarlyWarning)CommonUtil.getObject(oid);
				
				isResult = PersistenceHelper.navigate((WTDocumentMaster)ketEarlyWarning.getMaster(), KETEarlyWarningResultLink.ROLE_BOBJECT_ROLE, KETEarlyWarningResultLink.class, false);
				
				if (isResult != null && isResult.size() != 0){	 
					while(isResult.hasMoreElements()){
                 		ketEarlyWarningResultLink = (KETEarlyWarningResultLink)isResult.nextElement();
             			resultMaster = (Object)ObjectUtil.getLatestObject((WTDocumentMaster)ketEarlyWarningResultLink.getRoleBObject());
             			resultMasterData = new ObjectData((WTDocument)resultMaster);
             		}
					resultOid = resultMasterData.getOid();
					ketEarlyWarningResult = (KETEarlyWarningResult)CommonUtil.getObject(resultOid);
					
					if (ketEarlyWarningResult != null){
						
						//해제신청서 객체가 없을 시 todo 발생
						isEndReq = PersistenceHelper.navigate((WTDocumentMaster)ketEarlyWarning.getMaster(), KETEarlyWarningEndReqLink.ROLE_BOBJECT_ROLE, KETEarlyWarningEndReqLink.class, false);
						if  (isEndReq== null || isEndReq.size() == 0){	 
							
							KETWfmHelper.service.createWorkItem(ketEarlyWarningResult);
							
						//해제판정에서 기간연장이 되었을 경우  todo 발생
						}else{
							while(isEndReq.hasMoreElements()){
								ketEarlyWarningEndReqLink = (KETEarlyWarningEndReqLink)isEndReq.nextElement();
								endReqMaster = (Object)ObjectUtil.getLatestObject((WTDocumentMaster)ketEarlyWarningEndReqLink.getRoleBObject());
								endReqMasterData = new ObjectData((WTDocument)endReqMaster);
								endReqOid = endReqMasterData.getOid();
								ketEarlyWarningEndReq = (KETEarlyWarningEndReq)CommonUtil.getObject(endReqOid);
								
								isEnd = PersistenceHelper.navigate((WTDocumentMaster)ketEarlyWarningEndReq.getMaster(), KETEndReqLink.ROLE_BOBJECT_ROLE, KETEndReqLink.class, false);
							    if (isEnd == null || isEnd.size() == 0){
							    	endFlag = true;
							    }
							}
							
							if (!endFlag){
								KETWfmHelper.service.createWorkItem(ketEarlyWarningResult);
	                 		}
						}
					}
				}
			}
			
			Kogger.debug(UpdateEndReq.class, "********************"+"해제신청 todo 끝입니다.");
			
		} catch( Exception e ) {
			Kogger.error(UpdateEndReq.class, e);
		}

	}

}
