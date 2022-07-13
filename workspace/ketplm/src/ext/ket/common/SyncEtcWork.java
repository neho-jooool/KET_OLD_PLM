package ext.ket.common;

import wt.method.RemoteMethodServer;
import ext.ket.cost.code.service.CostCodeHelper;
import ext.ket.material.service.MaterialHelper;
import ext.ket.project.purchase.util.purchaseUtil;
import ext.ket.project.task.service.ProjectTaskCompHelper;
import ext.ket.shared.log.Kogger;

public class SyncEtcWork {
    public static void main(String[] args) throws Exception {

	RemoteMethodServer methodServer = RemoteMethodServer.getDefault();

	if (methodServer.getUserName() == null) {
	    String[] userInfo = { "wcadmin", "wcadmin" };

	    if (args.length >= 2) {
		userInfo[0] = args[0];
		userInfo[1] = args[1];
	    }

	    methodServer.setUserName(userInfo[0]);
	    methodServer.setPassword(userInfo[1]);

	    Kogger.debug(SyncEtcWork.class, "AUTHENTICATION SUCCESS");
	} else {
	    Kogger.debug(SyncEtcWork.class, "AUTHENTICATION FAILE");
	}

	Kogger.debug("구매프로젝트 일정 상태 동기화 Start");
	purchaseUtil putil = new purchaseUtil();
	try {
	    putil.PurchaseProjectStateUpdate();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	Kogger.debug("구매프로젝트 일정 상태 End");

	Kogger.debug("Sap 원자재 단가 동기화 Start");
	try {
	    CostCodeHelper.service.purchasePriceSapInterface();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	Kogger.debug("Sap 원자재 단가 동기화 End");

	Kogger.debug("프로젝트 비용 정보 동기화 Start");
	try {
	    ProjectTaskCompHelper.service.makeCostInfoAuto();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	Kogger.debug("프로젝트 비용 정보 동기화 Start");

	Kogger.debug("원재료 db sync Start");
	try {
	    // 원재료 db sync start
	    MaterialHelper.service.syncMaterialForPartLink();
	    // 원재료 db sync end
	} catch (Exception e) {
	    e.printStackTrace();
	}
	Kogger.debug("원재료 db sync End");
    }
}
