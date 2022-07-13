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
import wt.method.RemoteInterface;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.util.WTException;
import e3ps.wfm.entity.KETWfmApprovalMaster;

// Preserved unmodeled dependency

/**
 * 
 * @version 1.0
 **/

@RemoteInterface
public interface KETBomService {

	/**
	 * 
	 * <BR>
	 * <BR>
	 * <B>Supported API: </B>true
	 * 
	 * @param searchAttrHash
	 * @return Vector
	 * @exception wt.util.WTException
	 **/
	public Vector searchItem(Hashtable searchAttrHash) throws WTException;

	/**
	 * @param searchAttrHash
	 * @return String
	 * @exception wt.util.WTException
	 **/
	public String searchDuplicationItem(Hashtable searchAttrHash) throws WTException;

	/**
	 * @param createAttrHash
	 * @return String
	 * @exception wt.util.WTException
	 **/
	public String createBom(Hashtable createAttrHash) throws WTException;

	/**
	 * @param inputParams
	 * @return Vector
	 * @exception wt.util.WTException
	 **/
	public Vector myBom(Hashtable inputParams) throws WTException;

	/**
	 * @param inputParams
	 * @return Vector
	 * @exception wt.util.WTException
	 **/
	public Vector searchBom(Hashtable inputParams) throws WTException;

	/**
	 * @param bomOid
	 * @return Hashtable
	 * @exception wt.util.WTException
	 **/
	public Hashtable getBom(String bomOid) throws WTException;

	/**
	 * @return Vector
	 * @exception wt.util.WTException
	 **/
	public Vector searchWorkList() throws WTException;

	/**
	 * @param bomOid
	 * @exception wt.util.WTException
	 **/
	public void removeBom(String bomOid) throws WTException;

	/**
	 * @param inputParams
	 * @exception wt.util.WTException
	 **/
	public void setCoworkerCheckOut(Hashtable inputParams) throws WTException;

	/**
	 * @param inputParams
	 * @exception wt.util.WTException
	 **/
	public void setCoworkerCheckIn(Hashtable inputParams) throws WTException;

	/**
	 * @param bomOid
	 * @param headerType
	 * @return String
	 * @exception wt.util.WTException
	 **/
	public String setAllEndWorking(String bomOid, String headerType) throws WTException;

	/**
	 * @param inputParams
	 * @return Vector
	 * @exception wt.util.WTException
	 **/
	public Vector getCheckOuter(Vector inputParams) throws WTException;

	/**
	 * @param inputParams
	 * @return String
	 * @exception wt.util.WTException
	 **/
	public String changeEndWorkFlag(Hashtable inputParams) throws WTException;

	/**
	 * @param inputParams
	 * @return Vector
	 * @exception wt.util.WTException
	 **/
	public Vector getItemVersion(Hashtable inputParams) throws WTException;

	/**
	 * get LSISBOMECOHeader
	 * 
	 * @param bomEcoOid
	 * @return Hashtable
	 * @exception wt.util.WTException
	 **/
	public Hashtable getBomEco(String bomEcoOid) throws WTException;

	/**
	 * search specific LSISBOMECOHeader
	 * 
	 * @param inputParams
	 * @return Vector
	 * @exception wt.util.WTException
	 **/
	public Vector searchBomEco(Hashtable inputParams) throws WTException;

	/**
	 * search my LSISBOMECOHeader
	 * 
	 * @return Vector
	 * @exception wt.util.WTException
	 **/
	public Vector searchBomEcoWorkList() throws WTException;

	/**
	 * @param itemCode
	 * @param itemDesc
	 * @param orgCode
	 * @return Vector
	 * @exception wt.util.WTException
	 **/
	public Vector searchParentItem(String itemCode, String itemDesc, String orgCode) throws WTException;

	/**
	 * @param bomOid
	 * @param coworkers
	 * @return String
	 * @exception wt.util.WTException
	 **/
	public String manageCoworker(String bomOid, Vector coworkers) throws WTException;

	/**
	 * @param itemCode
	 * @return String
	 * @exception wt.util.WTException
	 **/
	public String getItemOid(String itemCode) throws WTException;

	/**
	 * @param inputParams
	 * @exception wt.util.WTException
	 **/
	public void checkOutItem(Vector inputParams) throws WTException;

	/**
	 * @param inputParams
	 * @exception wt.util.WTException
	 **/
	public void checkInItem(Vector inputParams) throws WTException;

	/**
	 * @param inputParams
	 * @exception wt.util.WTException
	 **/
	public void cancelCheckOutItem(Vector inputParams) throws WTException;

	/**
	 * @param inputParams
	 * @exception wt.util.WTException
	 **/
	public void reviseItem(Vector inputParams) throws WTException;

	/**
	 * @param inputParams
	 * @exception wt.util.WTException
	 **/
	public void cancelReviseItem(Vector inputParams) throws WTException;

	/**
	 * @param inputParams
	 * @return Vector
	 * @exception wt.util.WTException
	 **/
	public Vector myBomEco(Hashtable inputParams) throws WTException;

	/**
	 * @param inputParams
	 * @exception wt.util.WTException
	 **/
	public void setCoworkerCancelCheckOut(Hashtable inputParams) throws WTException;

	/**
	 * @param itemCodeVec
	 * @return Vector
	 * @exception wt.util.WTException
	 **/
	public Vector checkAuthority(Vector itemCodeVec) throws WTException;

	/**
	 * @param ht
	 * @param chk
	 * @return Boolean
	 * @exception wt.util.WTException
	 **/
	public Boolean setKetPartUsageLink(Hashtable ht, Integer chk) throws WTException;

	/**
	 * 
	 * 
	 * @param list
	 * @param chk
	 * @return
	 * @throws WTException
	 * @메소드명 : setKetPartUsageLink
	 * @작성자 : 황정태
	 * @작성일 : 2015. 6. 8.
	 * @설명 :
	 * @수정이력 - 수정일,수정자,수정내용
	 * 
	 */
	public Boolean setKetPartUsageLink(ArrayList list, Integer chk, FileWriter fw) throws WTException;

	/**
	 * @param oid
	 * @param master
	 * @return boolean
	 * @exception wt.util.WTException
	 **/
	public boolean startProcess(String oid, KETWfmApprovalMaster master) throws WTException;

	/**
	 * @param strNumber
	 * @return String
	 * @exception wt.util.WTException
	 **/
	public String getIsBomComponent(String strNumber) throws WTException;

	/**
	 * @param hashParam
	 * @exception wt.util.WTException
	 **/
	public void createLine(Hashtable hashParam) throws WTException;

	/**
	 * @param oid
	 * @param agreementType
	 * @param comment
	 * @exception wt.util.WTException
	 **/
	public void updateMaster(String oid, String agreementType, String comment) throws WTException;

	/**
	 * @param hashParam
	 * @return Persistable
	 * @exception wt.util.WTException
	 **/
	public Persistable createMaster(Hashtable hashParam) throws WTException;

	/**
	 * @param vc
	 * @param chk
	 * @return Boolean
	 * @exception wt.util.WTException
	 **/
	public Boolean setWfKetPartUsageLink(Vector vc, Integer chk) throws WTException;

	/**
	 * @param oid
	 * @return Boolean
	 * @exception wt.util.WTException
	 * 
	 * @deprecated
	 * 
	 **/
	public Boolean getBomInterface(String oid) throws WTException;

	/**
	 * @param ecoLongValue
	 * @param isProd
	 * @return Boolean
	 * @exception wt.util.WTException
	 **/
	public Boolean[] getBomInterface2(String ecoLongValue, boolean isProd) throws WTException;

	/**
	 * @param ecoHeaderNo
	 * @param ecoItemCode
	 * @exception wt.util.WTException
	 **/
	public Boolean[] getBomInterface3(String partOid) throws WTException;

	/**
	 * @param ecoHeaderNo
	 * @param ecoItemCode
	 * @exception wt.util.WTException
	 **/

	public void updateItemMaster(String ecoHeaderNo, String ecoItemCode) throws WTException;

	/**
	 * @param hashUpdate
	 * @return String
	 * @exception wt.util.WTException
	 **/
	public String updateItemMasterERP(Hashtable hashUpdate) throws WTException;

	/**
	 * @param ecoHeaderNo
	 * @param flag
	 * @exception wt.util.WTException
	 **/
	public void updateEndWorkingFlag(String ecoHeaderNo, String flag) throws WTException;

	/**
	 * @param newBomCode
	 * @param flag
	 * @exception wt.util.WTException
	 **/
	public void updateEndWorkingFlagNew(String newBomCode, String flag) throws WTException;

	/**
	 * @param vc
	 * @param chk
	 * @return Boolean
	 * @exception wt.util.WTException
	 **/
	public Boolean setWfKetPartUsageLinkEco(Vector vc, Integer chk) throws WTException;

	/**
	 * @param oid
	 * @return Boolean
	 * @exception wt.util.WTException
	 * 
	 * @deprecated
	 * 
	 **/
	public Boolean getBomEcoInterface(String ecoLongValue) throws WTException;

	/**
	 * @param oid
	 * @return Boolean
	 * @exception wt.util.WTException
	 **/
	public Boolean[] getBomEcoInterface(String ecoLongValue, boolean isProd) throws WTException;

	/**
	 * @param ht
	 * @param parentPart
	 * @param childPart
	 * @param childPartMaster
	 * @param chk
	 * @param conn
	 * @return Boolean
	 * @exception wt.util.WTException
	 **/
	public Boolean createPartUsageLink(Hashtable ht, WTPart parentPart, WTPart childPart, WTPartMaster childPartMaster, Integer chk, Connection conn) throws WTException;

	/**
	 * @param ht
	 * @param parentPart
	 * @param childPart
	 * @param childPartMaster
	 * @param chk
	 * @param conn
	 * @return Boolean
	 * @exception wt.util.WTException
	 **/
	public Boolean updatePartUsageLink(Hashtable ht, WTPart parentPart, WTPart childPart, WTPartMaster childPartMaster, Integer chk, Connection conn) throws WTException;

	/**
	 * @param ht
	 * @param parentPart
	 * @param childPart
	 * @param childPartMaster
	 * @param chk
	 * @param conn
	 * @return Boolean
	 * @exception wt.util.WTException
	 **/
	public Boolean removePartUsageLink(Hashtable ht, WTPart parentPart, WTPart childPart, WTPartMaster childPartMaster, Integer chk, Connection conn) throws WTException;

	/**
	 * @param bomOid
	 * @return Boolean
	 * @exception wt.util.WTException
	 **/
	public Boolean setTransKetPartUsageLinkEco(String bomOid) throws WTException;

	/**
	 * @param bomOid
	 * @return Boolean
	 * @exception wt.util.WTException
	 **/
	public Boolean setTransKetPartUsageLink(String bomOid) throws WTException;

}
