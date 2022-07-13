/* bcwti
 *
 * Copyright (c) 2008 Parametric Technology Corporation (PTC). All Rights
 * Reserved.
 *
 * This software is the confidential and proprietary information of PTC
 * and is subject to the terms of a software license agreement. You shall
 * not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement.
 *
 * ecwti
 */

package e3ps.bom.service;

import java.io.FileWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

import wt.fc.Persistable;
import wt.method.RemoteAccess;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.services.ServiceFactory;
import wt.util.WTException;
import e3ps.wfm.entity.KETWfmApprovalMaster;

@Deprecated
public class KETBomServiceFwd implements KETBomService, RemoteAccess {

	public Vector searchItem(Hashtable searchAttrHash) throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		return ServiceFactory.getService(KETBomService.class).searchItem(searchAttrHash);
	}

	public String searchDuplicationItem(Hashtable searchAttrHash) throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		return ServiceFactory.getService(KETBomService.class).searchDuplicationItem(searchAttrHash);
	}

	public String createBom(Hashtable createAttrHash) throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		return ServiceFactory.getService(KETBomService.class).createBom(createAttrHash);
	}

	public Vector myBom(Hashtable inputParams) throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		return ServiceFactory.getService(KETBomService.class).myBom(inputParams);
	}

	public Vector searchBom(Hashtable inputParams) throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		return ServiceFactory.getService(KETBomService.class).searchBom(inputParams);
	}

	public Hashtable getBom(String bomOid) throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		return ServiceFactory.getService(KETBomService.class).getBom(bomOid);
	}

	public Vector searchWorkList() throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		return ServiceFactory.getService(KETBomService.class).searchWorkList();
	}

	public void removeBom(String bomOid) throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		ServiceFactory.getService(KETBomService.class).removeBom(bomOid);
	}

	public void setCoworkerCheckOut(Hashtable inputParams) throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		ServiceFactory.getService(KETBomService.class).setCoworkerCheckOut(inputParams);
	}

	public void setCoworkerCheckIn(Hashtable inputParams) throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		ServiceFactory.getService(KETBomService.class).setCoworkerCheckIn(inputParams);
	}

	public String setAllEndWorking(String bomOid, String headerType) throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		return ServiceFactory.getService(KETBomService.class).setAllEndWorking(bomOid, headerType);
	}

	public Vector getCheckOuter(Vector inputParams) throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		return ServiceFactory.getService(KETBomService.class).getCheckOuter(inputParams);
	}

	public String changeEndWorkFlag(Hashtable inputParams) throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		return ServiceFactory.getService(KETBomService.class).changeEndWorkFlag(inputParams);
	}

	public Vector getItemVersion(Hashtable inputParams) throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		return ServiceFactory.getService(KETBomService.class).getItemVersion(inputParams);
	}

	public Hashtable getBomEco(String bomEcoOid) throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		return ServiceFactory.getService(KETBomService.class).getBomEco(bomEcoOid);
	}

	public Vector searchBomEco(Hashtable inputParams) throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		return ServiceFactory.getService(KETBomService.class).searchBomEco(inputParams);
	}

	public Vector searchBomEcoWorkList() throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		return ServiceFactory.getService(KETBomService.class).searchBomEcoWorkList();
	}

	public Vector searchParentItem(String itemCode, String itemDesc, String orgCode) throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		return ServiceFactory.getService(KETBomService.class).searchParentItem(itemCode, itemDesc, orgCode);
	}

	public String manageCoworker(String bomOid, Vector coworkers) throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		return ServiceFactory.getService(KETBomService.class).manageCoworker(bomOid, coworkers);
	}

	public String getItemOid(String itemCode) throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		return ServiceFactory.getService(KETBomService.class).getItemOid(itemCode);
	}

	public void checkOutItem(Vector inputParams) throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		ServiceFactory.getService(KETBomService.class).checkOutItem(inputParams);
	}

	public void checkInItem(Vector inputParams) throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		ServiceFactory.getService(KETBomService.class).checkInItem(inputParams);
	}

	public void cancelCheckOutItem(Vector inputParams) throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		ServiceFactory.getService(KETBomService.class).cancelCheckOutItem(inputParams);
	}

	public void reviseItem(Vector inputParams) throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		ServiceFactory.getService(KETBomService.class).reviseItem(inputParams);
	}

	public void cancelReviseItem(Vector inputParams) throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		ServiceFactory.getService(KETBomService.class).cancelReviseItem(inputParams);
	}

	public Vector myBomEco(Hashtable inputParams) throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		return ServiceFactory.getService(KETBomService.class).myBomEco(inputParams);
	}

	public void setCoworkerCancelCheckOut(Hashtable inputParams) throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		ServiceFactory.getService(KETBomService.class).setCoworkerCancelCheckOut(inputParams);
	}

	public Vector checkAuthority(Vector itemCodeVec) throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		return ServiceFactory.getService(KETBomService.class).checkAuthority(itemCodeVec);
	}

	public Boolean setKetPartUsageLink(Hashtable ht, Integer chk) throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		return ServiceFactory.getService(KETBomService.class).setKetPartUsageLink(ht, chk);
	}

	public Boolean setKetPartUsageLink(ArrayList list, Integer chk, FileWriter fw) throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		return ServiceFactory.getService(KETBomService.class).setKetPartUsageLink(list, chk, fw);
	}

	public boolean startProcess(String oid, KETWfmApprovalMaster master) throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		return ServiceFactory.getService(KETBomService.class).startProcess(oid, master);
	}

	public String getIsBomComponent(String strNumber) throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		return ServiceFactory.getService(KETBomService.class).getIsBomComponent(strNumber);
	}

	public void createLine(Hashtable hashParam) throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		ServiceFactory.getService(KETBomService.class).createLine(hashParam);
	}

	public void updateMaster(String oid, String agreementType, String comment) throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		ServiceFactory.getService(KETBomService.class).updateMaster(oid, agreementType, comment);
	}

	public Persistable createMaster(Hashtable hashParam) throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		return ServiceFactory.getService(KETBomService.class).createMaster(hashParam);
	}

	public Boolean setWfKetPartUsageLink(Vector vc, Integer chk) throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		return ServiceFactory.getService(KETBomService.class).setWfKetPartUsageLink(vc, chk);
	}

	public Boolean getBomInterface(String oid) throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		return ServiceFactory.getService(KETBomService.class).getBomInterface(oid);
	}

	public Boolean[] getBomInterface2(String ecoLongValue, boolean isProd) throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		return ServiceFactory.getService(KETBomService.class).getBomInterface2(ecoLongValue, isProd);
	}

	public Boolean[] getBomInterface3(String partOid) throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		return ServiceFactory.getService(KETBomService.class).getBomInterface3(partOid);
	}

	public void updateItemMaster(String ecoHeaderNo, String ecoItemCode) throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		ServiceFactory.getService(KETBomService.class).updateItemMaster(ecoHeaderNo, ecoItemCode);
	}

	public String updateItemMasterERP(Hashtable hashUpdate) throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		return ServiceFactory.getService(KETBomService.class).updateItemMasterERP(hashUpdate);
	}

	public void updateEndWorkingFlag(String ecoHeaderNo, String flag) throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		ServiceFactory.getService(KETBomService.class).updateEndWorkingFlag(ecoHeaderNo, flag);
	}

	public void updateEndWorkingFlagNew(String newBomCode, String flag) throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		ServiceFactory.getService(KETBomService.class).updateEndWorkingFlagNew(newBomCode, flag);
	}

	public Boolean setWfKetPartUsageLinkEco(Vector vc, Integer chk) throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		return ServiceFactory.getService(KETBomService.class).setWfKetPartUsageLinkEco(vc, chk);
	}

	public Boolean getBomEcoInterface(String ecoLongValue) throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		return ServiceFactory.getService(KETBomService.class).getBomEcoInterface(ecoLongValue);
	}

	public Boolean[] getBomEcoInterface(String ecoLongValue, boolean isProd) throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		return ServiceFactory.getService(KETBomService.class).getBomEcoInterface(ecoLongValue, isProd);
	}

	public Boolean createPartUsageLink(Hashtable ht, WTPart parentPart, WTPart childPart, WTPartMaster childPartMaster, Integer chk, Connection conn) throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		return ServiceFactory.getService(KETBomService.class).createPartUsageLink(ht, parentPart, childPart, childPartMaster, chk, conn);
	}

	public Boolean updatePartUsageLink(Hashtable ht, WTPart parentPart, WTPart childPart, WTPartMaster childPartMaster, Integer chk, Connection conn) throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		return ServiceFactory.getService(KETBomService.class).updatePartUsageLink(ht, parentPart, childPart, childPartMaster, chk, conn);
	}

	public Boolean removePartUsageLink(Hashtable ht, WTPart parentPart, WTPart childPart, WTPartMaster childPartMaster, Integer chk, Connection conn) throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		return ServiceFactory.getService(KETBomService.class).removePartUsageLink(ht, parentPart, childPart, childPartMaster, chk, conn);
	}

	public Boolean setTransKetPartUsageLinkEco(String bomOid) throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		return ServiceFactory.getService(KETBomService.class).setTransKetPartUsageLinkEco(bomOid);
	}

	public Boolean setTransKetPartUsageLink(String bomOid) throws WTException {

		// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
		// forward to remote service implementation
		return ServiceFactory.getService(KETBomService.class).setTransKetPartUsageLink(bomOid);
	}
}
