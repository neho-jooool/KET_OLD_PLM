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

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;

import wt.clients.vc.CheckInOutTaskLogic;  // Preserved unmodeled dependency
import wt.enterprise.RevisionControlled;  // Preserved unmodeled dependency
import wt.fc.IdentityHelper;
import wt.fc.PersistenceHelper;  // Preserved unmodeled dependency
import wt.fc.QueryResult;  // Preserved unmodeled dependency
import wt.fc.ReferenceFactory;  // Preserved unmodeled dependency
import wt.folder.Folder;
import wt.folder.FolderEntry;  // Preserved unmodeled dependency
import wt.folder.FolderHelper;  // Preserved unmodeled dependency
import wt.folder.FolderNotFoundException;  // Preserved unmodeled dependency
import wt.inf.container.WTContainerRef;  // Preserved unmodeled dependency
import wt.lifecycle.LifeCycleHelper;  // Preserved unmodeled dependency
import wt.lifecycle.LifeCycleTemplate;  // Preserved unmodeled dependency
import wt.part.QuantityUnit;  // Preserved unmodeled dependency
import wt.part.WTPart;
import wt.part.WTPartMaster;  // Preserved unmodeled dependency
import wt.part.WTPartMasterIdentity;
import wt.pdmlink.PDMLinkProduct;  // Preserved unmodeled dependency
import wt.pom.Transaction;  // Preserved unmodeled dependency
import wt.query.QuerySpec;  // Preserved unmodeled dependency
import wt.query.SearchCondition;  // Preserved unmodeled dependency
import wt.services.ManagerException;
import wt.services.StandardManager;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;  // Preserved unmodeled dependency
import wt.vc.VersionControlHelper;  // Preserved unmodeled dependency
import wt.vc.Versioned;
import wt.vc.views.ViewHelper;  // Preserved unmodeled dependency
import wt.vc.wip.WorkInProgressException;  // Preserved unmodeled dependency
import wt.vc.wip.WorkInProgressHelper;  // Preserved unmodeled dependency
import e3ps.bom.dao.BOMSearchUtilDao;
import e3ps.bom.framework.util.Registry;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.KETObjectUtil;
import e3ps.common.util.StringUtil;  // Preserved unmodeled dependency
import e3ps.common.util.WCUtil;  // Preserved unmodeled dependency
import e3ps.sap.ItemMasterInterface;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecSetter;
// Preserved unmodeled dependency
// Preserved unmodeled dependency
// Preserved unmodeled dependency
// Preserved unmodeled dependency
// Preserved unmodeled dependency
import ext.ket.shared.log.Kogger;
// Preserved unmodeled dependency

/**
 *
 * <p>
 * Use the <code>newStandardKETPartService</code> static factory method(s),
 * not the <code>StandardKETPartService</code> constructor, to construct
 * instances of this class.  Instances must be constructed using the static
 * factory(s), in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

public class StandardKETPartService extends StandardManager implements KETPartService, Serializable {


   private static final String RESOURCE = "e3ps.bom.service.serviceResource";
   private static final String CLASSNAME = StandardKETPartService.class.getName();

   private final String ROOT_PART_PATH = "/Default/";
   private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");


   /**
    * Returns the conceptual (modeled) name for the class.
    *
    * <BR><BR><B>Supported API: </B>false
    *
    * @deprecated
    *
    * @return    String
    **/
   public String getConceptualClassname() {

      return CLASSNAME;
   }

   /**
    * @exception wt.services.ManagerException
    **/
   @Override
   protected void performStartupProcess()
            throws ManagerException {

      super.performStartupProcess();

   }

   /**
    * Default factory for the class.
    *
    * @return    StandardKETPartService
    * @exception wt.util.WTException
    **/
   public static StandardKETPartService newStandardKETPartService()
            throws WTException {

      StandardKETPartService instance = new StandardKETPartService();
      instance.initialize();
      return instance;
   }

   @Override
   public WTPart getPart( String number, String version )
            throws WTException {

       QuerySpec qs = new QuerySpec(WTPart.class);

       qs.appendWhere(VersionControlHelper.getSearchCondition(WTPart.class, true), new int[] { 0 });
       qs.appendAnd();
       qs.appendWhere(new SearchCondition(WTPart.class, "master>number", "=", number), new int[] { 0 });
       qs.appendAnd();
       qs.appendWhere(new SearchCondition(WTPart.class, "versionInfo.identifier.versionId", "=", version), new int[] { 0 });
       QueryResult qr = PersistenceHelper.manager.find(qs);
       if ( qr.hasMoreElements() ) {
           return (WTPart) qr.nextElement();
       }

      return null;
   }

   @Override
   public Hashtable create( Hashtable hash )
            throws WTException {

       Hashtable<String, String> rtnVal = new Hashtable<String, String>();
       Transaction trx = new Transaction();

       String itemCode = (String) hash.get("number");					// part number
       String itemName = (String) hash.get("name");					// part name
       String unit = (String) hash.get("unit");							// unit
       String partType = (String) hash.get("partType");					// partType
       String partGroup = (String) hash.get("partGroup");				// partGroup
       String partWeight = (String) hash.get("partWeight");			// partWeight
       String isDeleted = (String) hash.get("isDeleted");				// isDeleted
       String bomFlag = (String) hash.get("bomFlag");					// bomFlag
       String prodCode = (String) hash.get("prodCode");				// prodCode
       String prodName = (String) hash.get("prodName");			// prodName
       String isNew = (String) hash.get("isNew");						// isNew
       String yazaki = (String) hash.get("yazaki");						// IS YAZAKI
       String yazakiNo = (String) hash.get("yazakiNo");				// YAZAKI No
       String weightUnit = (String) hash.get("weightUnit");			// weightUnit
       String partGroupDesc = (String) hash.get("partGroupDesc");	// partGroupDesc

       // Unit Setting
       if (unit != null && unit.equals("")) {
    	   unit = "KET_EA";
       } else if (unit.equals("%")){
    	   unit = "KET_PERCENT";
       } else if (unit.equalsIgnoreCase("EA") || unit.equalsIgnoreCase("M") || unit.equalsIgnoreCase("MM")||
    		      unit.equalsIgnoreCase("G") || unit.equalsIgnoreCase("MG") || unit.equalsIgnoreCase("KG") ||
    		   	  unit.equalsIgnoreCase("AM") || unit.equalsIgnoreCase("CV")|| unit.equalsIgnoreCase("CA'") || unit.equalsIgnoreCase("P")||
    		   	  unit.equalsIgnoreCase("PAC") || unit.equalsIgnoreCase("REL")|| unit.equalsIgnoreCase("ROL") || unit.equalsIgnoreCase("ST")) {
    	   unit = "KET_" + unit.toUpperCase();
       } else {
    	   throw new WTException();
       }

Kogger.debug(getClass(), "############## Part CREATE #####################");
Kogger.debug(getClass(), "@@@@@@@ itemCode : " + itemCode);
Kogger.debug(getClass(), "@@@@@@@ itemName : " + itemName);
Kogger.debug(getClass(), "@@@@@@@ unit : " + unit);
Kogger.debug(getClass(), "@@@@@@@ partType : " + partType);
Kogger.debug(getClass(), "@@@@@@@ partGroup : " + partGroup);
Kogger.debug(getClass(), "@@@@@@@ partGroupDesc : " + partGroupDesc);
Kogger.debug(getClass(), "@@@@@@@ partWeight : " + partWeight);
Kogger.debug(getClass(), "@@@@@@@ weightUnit : " + weightUnit);
Kogger.debug(getClass(), "@@@@@@@ isDeleted : " + isDeleted);
Kogger.debug(getClass(), "@@@@@@@ bomFlag : " + bomFlag);
Kogger.debug(getClass(), "@@@@@@@ prodCode : " + prodCode);
Kogger.debug(getClass(), "@@@@@@@ prodName : " + prodName);
Kogger.debug(getClass(), "@@@@@@@ isNew : " + isNew);
Kogger.debug(getClass(), "@@@@@@@ yazaki : " + yazaki);
Kogger.debug(getClass(), "@@@@@@@ yazakiNo : " + yazakiNo);
Kogger.debug(getClass(), "################################################");

       try {
           trx.start();

           WTPart part = WTPart.newWTPart();

           part.setNumber(itemCode);

           PDMLinkProduct e3psProduct = WCUtil.getPDMLinkProduct();
           WTContainerRef wtContainerRef = WTContainerRef.newWTContainerRef(e3psProduct);
           part.setContainer(e3psProduct);

           // part name setting
           if (partType != null && partType.equals("CAST")) { 		// for Die No
        	   if (prodName != null && !prodName.equals("") && !prodName.equals("O")) {
        		   part.setName(prodName.trim());
        	   } else {
        		   part.setName(itemName.trim());
        	   }
           } else {
        	   part.setName(itemName.trim());
           }
           part.setDefaultUnit(QuantityUnit.toQuantityUnit(unit));

           // View Setting (default "Design")
           ViewHelper.assignToView(part, ViewHelper.service.getView("Design"));

           // Folder Setting
           FolderHelper.assignLocation((FolderEntry) part, getPartFolder(messageRegistry.getString("part")));

           // Life Cycle Setting (Default)
           LifeCycleTemplate tmpLifeCycle = LifeCycleHelper.service.getLifeCycleTemplate("KET_PART_LC", wtContainerRef);
           part = (WTPart) LifeCycleHelper.setLifeCycle(part, tmpLifeCycle);

           // Standard Attributue Setting
           // 1 : PartType
           if (partType != null && partType.equals("CAST")) {
        	   PartSpecSetter.setPartSpec(part, PartSpecEnum.SpPartType, "D");
           } else if (partType != null && partType.equals("DIEM")) {
        	   PartSpecSetter.setPartSpec(part, PartSpecEnum.SpPartType, "M");
           } else {
        	   PartSpecSetter.setPartSpec(part, PartSpecEnum.SpPartType, "F"); // Product > P를 임의로 F로 변경
        	   throw new Exception("Product Type 재정의가 필요합니다....");
           }

           // 2 : BOMFlag
           if (bomFlag != null && bomFlag.equals("X")) {
        	   PartSpecSetter.setPartSpec(part, PartSpecEnum.SpBOMFlag, "OLD");
           } else {
        	   PartSpecSetter.setPartSpec(part, PartSpecEnum.SpBOMFlag, "NEW");
           }

           // 3 : IsDeleted (when the part is deleted, the value is 'X')
           if (isDeleted != null && isDeleted.equals("X")) {
               PartSpecSetter.setPartSpec(part, PartSpecEnum.SpIsDeleted, "Y");
           } else {
               PartSpecSetter.setPartSpec(part, PartSpecEnum.SpIsDeleted, "N");
           }

           // 4 : Is YAZAKI
           if ( yazaki != null && !yazaki.equals("O") ) {
               PartSpecSetter.setPartSpec(part, PartSpecEnum.SpIsYazaki, yazaki);
           } else {
               PartSpecSetter.setPartSpec(part, PartSpecEnum.SpIsYazaki, "");
           }
           
           // 5 : YAZAKI No
           if ( yazakiNo != null && !yazakiNo.equals("O") ) {
               PartSpecSetter.setPartSpec(part, PartSpecEnum.SpYazakiNo, yazakiNo);
           } else {
               PartSpecSetter.setPartSpec(part, PartSpecEnum.SpYazakiNo, "");
           }
           
           // 6 : PartGroup (Desc) -- 삭제됨
//           if ( partGroupDesc != null && !partGroupDesc.equals("O") ) {
//               PartSpecSetter.setPartSpec(part, PartSpecEnum.PartGroup, partGroupDesc);
//           } else {
//               PartSpecSetter.setPartSpec(part, PartSpecEnum.PartGroup, "");
//           }
           
           // 7 : PartWeight               
           if ( partWeight != null && !partWeight.equals("O") ) {
               PartSpecSetter.setPartSpec(part, PartSpecEnum.SpNetWeight, partWeight);
           } else {
               PartSpecSetter.setPartSpec(part, PartSpecEnum.SpNetWeight, "");
           }
           // 8 : weightUnit               
           if ( weightUnit != null && !weightUnit.equals("O") ) {
               PartSpecSetter.setPartSpec(part, PartSpecEnum.SpWeightUnit, weightUnit);
           } else {
               PartSpecSetter.setPartSpec(part, PartSpecEnum.SpWeightUnit, "");
           }
           
           // Part Create
           part = (WTPart) PersistenceHelper.manager.save(part);
           
           trx.commit();
           trx = null;
           
           rtnVal.put("rslt", "S");
           rtnVal.put("msg", "Part Created!!");
           rtnVal.put("oid", KETObjectUtil.getOidString(part));
//           rtnVal.put("oid", PersistenceHelper.getObjectIdentifier(part).toString());
           rtnVal.put("number", itemCode);
           
       } catch ( Exception e ) {
           rtnVal.put("rslt", "F");
           rtnVal.put("msg", "Part [" + itemCode + "] Create Fail!!");
           rtnVal.put("oid", "");
           Kogger.debug(getClass(), "@@@@@@@@@@ Part ["+ itemCode +"] Create Fail !!!");
           Kogger.error(getClass(), e);
           //throw new WTException(e);
       } finally {
           if ( trx != null ) {
               trx.rollback();
           }
       }

      return rtnVal;
   }

   @Override
   public Hashtable createByExcel( Hashtable hash )
            throws WTException {
       Hashtable<String, String> rtnVal = new Hashtable<String, String>();
       Transaction trx = new Transaction();

       try {
           	trx.start();

//           	for(int inx = 0; inx < hash.length; inx++) {

           		String itemCode = (String) hash.get("itemCode");		// part number
           		String itemName = (String) hash.get("itemName");		// part name
           		String unit = (String) hash.get("unit");					// unit

                // Unit Setting
                if (unit != null && unit.equals("")) {
             	   unit = "ea";
                }

           		WTPart part = WTPart.newWTPart();

           		part.setNumber(itemCode);

           		PDMLinkProduct e3psProduct = WCUtil.getPDMLinkProduct();
           		WTContainerRef wtContainerRef = WTContainerRef.newWTContainerRef(e3psProduct);
           		part.setContainer(e3psProduct);

           		part.setName(itemName.trim());
           		part.setDefaultUnit(QuantityUnit.toQuantityUnit(unit.toLowerCase()));

           		// View Setting (default "Design")
           		ViewHelper.assignToView(part, ViewHelper.service.getView("Design"));

           		// Folder Setting
           		FolderHelper.assignLocation((FolderEntry) part, getPartFolder(messageRegistry.getString("part")));

           		// Life Cycle Setting (Default)
           		LifeCycleTemplate tmpLifeCycle = LifeCycleHelper.service.getLifeCycleTemplate("KET_PART_LC", wtContainerRef);
           		part = (WTPart) LifeCycleHelper.setLifeCycle(part, tmpLifeCycle);

           		// IBA (Additional Attribute) Setting
           		PartSpecSetter.setPartSpec(part, PartSpecEnum.SpPartType, "M");	// Mold Part
           		PartSpecSetter.setPartSpec(part, PartSpecEnum.SpBOMFlag, "NEW");
           		
           		// Part Create
           		part = (WTPart) PersistenceHelper.manager.save(part);


			rtnVal.put("msg", "Part Created !!");
			rtnVal.put("oid", CommonUtil.getOIDString(part));
//           	}

           	trx.commit();
           	trx = null;
       	}
       	catch ( Exception e ) {
       		throw new WTException(e);
       	}
       	finally {
       		if ( trx != null ) {
       			trx.rollback();
       		}
       	}

       return rtnVal;
   }

   @Override
   public Hashtable delete( Hashtable hash )
            throws WTException {

      return null;
   }

   @Override
   public Hashtable modify( Hashtable hash )
            throws WTException {
       Hashtable rtnVal = new Hashtable();
       Transaction trx = new Transaction();
       WTPart wt = null;
       String itemCode = "";

       try {
           trx.start();

           // PLM exist?
           itemCode = (String) hash.get("number");
           wt = getPart(itemCode);

           if (wt != null) {				// Update
	           rtnVal = modify(hash, false);
	           rtnVal.put("rslt", "S");
           } else {							// Create
        	   rtnVal = create(hash);
           }

           trx.commit();
           trx = null;
       }
       catch ( Exception e ) {
           rtnVal.put("rslt", "F");
           rtnVal.put("oid", "");
           Kogger.error(getClass(), e);
       }
       finally {
           if ( trx != null ) {
               trx.rollback();
           }
       }

       return rtnVal;
   }

   @Override
   public WTPart getPart( String number )
            throws WTException {
	   
	   WTPart part = null;
	   BOMSearchUtilDao dao = new BOMSearchUtilDao();
	   
	   try {
		   
		   String strOid = dao.getLastestPartOid( number );
		   if ( strOid != null && !strOid.equals("") )
		   {
			   part = (WTPart) KETObjectUtil.getObject( strOid );
		   }
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
		
	    return part;

//       QuerySpec qs = new QuerySpec(WTPart.class);
//
//       qs.appendWhere(VersionControlHelper.getSearchCondition(WTPart.class, true), new int[] { 0 });
//       qs.appendAnd();
//       qs.appendWhere(new SearchCondition(WTPart.class, "master>number", "=", number), new int[] { 0 });
//
//       try {
//    	   SearchUtil.addLastVersionCondition(qs, WTPart.class, 0);
//	       QueryResult qr = PersistenceHelper.manager.find(qs);
//	       if ( qr.hasMoreElements() ) {
//	           Object o = qr.nextElement();
//	           if ( o instanceof WTPart ) {
//	               return (WTPart) o;
//	           } else {
//	               Object[] arry = (Object[]) o;
//	               return (WTPart) arry[0];
//	           }
//	       }
//       } catch (RemoteException e) {
//    	   Kogger.error(getClass(), e);
//       } catch (WTPropertyVetoException e) {
//    	   Kogger.error(getClass(), e);
//       }

   }

   @Override
   public Hashtable modify( Hashtable hash, Boolean reviseFlag )
            throws WTException {
       Hashtable rtnVal = new Hashtable();

       String itemCode = (String) hash.get("number");						// part number
       String itemName = (String) hash.get("name");						// part name
       String unit = (String) hash.get("unit");								// unit
       String partType = (String) hash.get("partType");						// partType
       String partGroup = (String) hash.get("partGroup");					// partGroup
       String partGroupDesc = (String) hash.get("partGroupDesc");		// partGroup
       String partWeight = (String) hash.get("partWeight");				// partWeight
       String weightUnit = (String) hash.get("weightUnit");				// weightUnit
       String isDeleted = (String) hash.get("isDeleted");					// isDeleted
       String bomFlag = (String) hash.get("bomFlag");						// bomFlag
       String prodCode = (String) hash.get("prodCode");					// prodCode
       String prodName = (String) hash.get("prodName");				// prodName
       String yazaki = (String) hash.get("yazaki");							// is YAZAKI 
       String yazakiNo = (String) hash.get("yazakiNo");					// YAZAKI No
//       String isNew = (String) hash.get("isNew");							// isNew

       // Unit Setting
       if (unit != null && unit.equals("")) {
    	   unit = "KET_EA";
       } else if (unit.equals("%")){
    	   unit = "KET_PERCENT";
       } else if (unit.equalsIgnoreCase("EA") || unit.equalsIgnoreCase("M") || unit.equalsIgnoreCase("MM")||
    		      unit.equalsIgnoreCase("G") || unit.equalsIgnoreCase("MG") || unit.equalsIgnoreCase("KG") ||
    		   	  unit.equalsIgnoreCase("AM") || unit.equalsIgnoreCase("CV")|| unit.equalsIgnoreCase("CA'") || unit.equalsIgnoreCase("P")||
    		   	  unit.equalsIgnoreCase("PAC") || unit.equalsIgnoreCase("REL")|| unit.equalsIgnoreCase("ROL") || unit.equalsIgnoreCase("ST")) {
    	   unit = "KET_" + unit.toUpperCase();
       } else {
    	   throw new WTException();
       }

Kogger.debug(getClass(), "########### Part modify #####################");
Kogger.debug(getClass(), "@@@@@@@ itemCode : " + itemCode);
Kogger.debug(getClass(), "@@@@@@@ itemName : " + itemName);
Kogger.debug(getClass(), "@@@@@@@ unit : " + unit);
Kogger.debug(getClass(), "@@@@@@@ partType : " + partType);
Kogger.debug(getClass(), "@@@@@@@ partGroup : " + partGroup);
Kogger.debug(getClass(), "@@@@@@@ partGroupDesc : " + partGroupDesc);
Kogger.debug(getClass(), "@@@@@@@ weightUnit : " + weightUnit);
Kogger.debug(getClass(), "@@@@@@@ partWeight : " + partWeight);
Kogger.debug(getClass(), "@@@@@@@ isDeleted : " + isDeleted);
Kogger.debug(getClass(), "@@@@@@@ bomFlag : " + bomFlag);
Kogger.debug(getClass(), "@@@@@@@ prodCode : " + prodCode);
Kogger.debug(getClass(), "@@@@@@@ prodName : " + prodName);
Kogger.debug(getClass(), "@@@@@@@ yazaki : " + yazaki);
Kogger.debug(getClass(), "@@@@@@@ yazakiNo : " + yazakiNo);
//Kogger.debug(getClass(), "@@@@@@@ isNew : " + isNew);
Kogger.debug(getClass(), "#############################################");

       WTPart part = null;
       WTPart oldPart = null;
       WTPartMaster partMaster = null;
       String oid = "";

       try {
    	   oid = KETObjectUtil.getOidString(getPart(itemCode));

           if ( StringUtil.checkString(oid) ) {
               ReferenceFactory rf = new ReferenceFactory();
               oldPart = (WTPart) rf.getReference(oid).getObject();

               // Working Copy
               part = getWorkingCopy(oldPart);
               part.setDefaultUnit(QuantityUnit.toQuantityUnit(unit));
               
               // Standard Attribute Setting
               // 1 : BOMFlag
               if (bomFlag != null && bomFlag.equals("X")) {
            	   PartSpecSetter.setPartSpec(part, PartSpecEnum.SpBOMFlag, "OLD");
               } else {
            	   PartSpecSetter.setPartSpec(part, PartSpecEnum.SpBOMFlag, "NEW");
               }

               // 2 : IsDeleted (when the part is deleted, the value is 'X')
               if (isDeleted != null && isDeleted.equals("X")) {
            	   PartSpecSetter.setPartSpec(part, PartSpecEnum.SpIsDeleted, "Y");
               } else {
            	   PartSpecSetter.setPartSpec(part, PartSpecEnum.SpIsDeleted, "N");
               }
               
               // 3 : Is YAZAKI
               if ( yazaki != null && !yazaki.equals("O") ) {
            	   PartSpecSetter.setPartSpec(part, PartSpecEnum.SpIsYazaki, yazaki);
               } else {
            	   PartSpecSetter.setPartSpec(part, PartSpecEnum.SpIsYazaki, "");
               }
               
               // 4 : YAZAKI No
               if ( yazakiNo != null && !yazakiNo.equals("O") ) {
            	   PartSpecSetter.setPartSpec(part, PartSpecEnum.SpYazakiNo, yazakiNo);
               } else {
            	   PartSpecSetter.setPartSpec(part, PartSpecEnum.SpYazakiNo, "");
               }
               
               // 5 : PartGroup (Desc) - // 삭제됨 - 1차고도화
//               if ( partGroupDesc != null && !partGroupDesc.equals("O") ) {
//            		PartSpecSetter.setPartSpec(part, PartSpecEnum.SpPartGroup, partGroupDesc);
//               } else {
//            		PartSpecSetter.setPartSpec(part, PartSpecEnum.SpPartGroup, "");
//               }
               
               // 6 : PartWeight               
               if ( partWeight != null && !partWeight.equals("O") ) {
            		PartSpecSetter.setPartSpec(part, PartSpecEnum.SpNetWeight, partWeight);
               } else {
            		PartSpecSetter.setPartSpec(part, PartSpecEnum.SpNetWeight, "");
               }
               // 7 : weightUnit               
               if ( weightUnit != null && !weightUnit.equals("O") ) {
            	   PartSpecSetter.setPartSpec(part, PartSpecEnum.SpWeightUnit, weightUnit);
               } else {
        	   PartSpecSetter.setPartSpec(part, PartSpecEnum.SpWeightUnit, "");
               }
               
               part = (WTPart) PersistenceHelper.manager.modify(part);

               // Check-in
               if ( WorkInProgressHelper.isCheckedOut(part) ) {
                   part = (WTPart) WorkInProgressHelper.service.checkin(part, "");
               }
               part = (WTPart) PersistenceHelper.manager.refresh(part);

               // Part Name Update
               String newItemName = "";
               if (partType != null && partType.equals("CAST")) { 		// for Die No
            	   if (prodName != null && !prodName.equals("") && !prodName.equals("O")) {	// when the related Product Name is empty, item name is same to master name
            		   newItemName = prodName;
            	   } else {
            		   newItemName = itemName;
            	   }
               } else {
            	   newItemName = itemName;
               }
               
               partMaster = (WTPartMaster) (part.getMaster());
               if ( !part.getName().equals(newItemName) ) {
                   WTPartMasterIdentity identity = (WTPartMasterIdentity) partMaster.getIdentificationObject();
                   identity.setName(newItemName);
                   partMaster = (WTPartMaster) IdentityHelper.service.changeIdentity(partMaster, identity);
               }

               // Unit Update
               if ( !(partMaster.getDefaultUnit().toString()).equalsIgnoreCase(unit) ) {
                   partMaster.setDefaultUnit(QuantityUnit.toQuantityUnit(unit));
                   partMaster = (WTPartMaster) PersistenceHelper.manager.modify(partMaster);
               }
           }

           rtnVal.put("oid", KETObjectUtil.getOidString(part));
//           rtnVal.put("part", part);
       }
       catch ( Exception e ) {
           throw new WTException(e);
       }

       return rtnVal;
   }

   @Override
   public WTPart reviseUpdate( WTPart befPart )
            throws WTException {

      return null;
   }

   @Override
   public Hashtable reviseUpdate( Hashtable hash )
            throws WTException {
       Hashtable rtnVal = null;
       Transaction trx = new Transaction();

       String oid = (String) hash.get("oid");

       try {
           trx.start();

           if ( StringUtil.checkString(oid) ) {
               ReferenceFactory rf = new ReferenceFactory();
               Versioned vs = (Versioned) rf.getReference(oid).getObject();

               // before Revise Setting Info get(Life cycle, folder, view)
               String lifecycle = ((RevisionControlled) vs).getLifeCycleName();
               Folder folder = FolderHelper.service.getFolder((FolderEntry) vs);

               WTPart part = (WTPart) VersionControlHelper.service.newVersion(vs);
               PDMLinkProduct e3psProduct = WCUtil.getPDMLinkProduct();
               WTContainerRef wtContainerRef = WTContainerRef.newWTContainerRef(e3psProduct);
               part.setContainer(e3psProduct);

               // Folder Setting
               FolderHelper.assignLocation((FolderEntry) part, folder);

               // Life cycle Setting (Default)
               LifeCycleTemplate tmpLifeCycle = LifeCycleHelper.service.getLifeCycleTemplate(lifecycle, wtContainerRef);
               part = (WTPart) LifeCycleHelper.setLifeCycle(part, tmpLifeCycle);

               // Save
               part = (WTPart) PersistenceHelper.manager.save(part);

               hash.put("oid", CommonUtil.getOIDString(part));

               rtnVal = modify(hash, true);
           }

           trx.commit();
           trx = null;
       }
       catch ( Exception e ) {
           throw new WTException(e);
       }
       finally {
           if ( trx != null ) {
               trx.rollback();
           }
       }
       return rtnVal;
   }

   @Override
   public Folder getPartFolder( String folderPath )
            throws WTException {

       Folder tmpfolder = null;
       WTContainerRef wtContainerRef = null;
       try {
           wtContainerRef = WTContainerRef.newWTContainerRef(WCUtil.getPDMLinkProduct());
           tmpfolder = FolderHelper.service.getFolder(ROOT_PART_PATH + folderPath, wtContainerRef);
       }
       catch ( FolderNotFoundException ex ) {
           tmpfolder = null;
       } catch (Exception e) {
    	   Kogger.error(getClass(), e);
       }

       if ( tmpfolder == null ) {
           String tmpfolderFullPath[] = folderPath.split("/");
           String tmpfolderPath = ROOT_PART_PATH;

           for ( int inxA = 0; inxA < tmpfolderFullPath.length; inxA++ ) {
               tmpfolder = null;
               tmpfolderPath = tmpfolderPath + "/" + tmpfolderFullPath[inxA];
               try {
                   tmpfolder = FolderHelper.service.getFolder(tmpfolderPath, wtContainerRef);
               }
               catch ( FolderNotFoundException ex ) {
                   tmpfolder = null;
               }

               if ( tmpfolder == null ) {
                   tmpfolder = FolderHelper.service.createSubFolder(tmpfolderPath, wtContainerRef);
               }
           }
       }
       return tmpfolder;
   }

   @Override
   public WTPart getWorkingCopy( WTPart _part )
            throws WTException {

       WTPart rsltPart = null;
       try {
           if ( !WorkInProgressHelper.isCheckedOut(_part) ) {
               if ( !CheckInOutTaskLogic.isCheckedOut(_part) ) {
                   WorkInProgressHelper.service.checkout(_part, CheckInOutTaskLogic.getCheckoutFolder(), "");
               }
               rsltPart = (WTPart) WorkInProgressHelper.service.workingCopyOf(_part);
           } else {
               if ( !WorkInProgressHelper.isWorkingCopy(_part) ) {
                   rsltPart = (WTPart) WorkInProgressHelper.service.workingCopyOf(_part);
               }
           }
           if ( rsltPart == null ) {
               rsltPart = _part;
           }
       }
       catch ( WorkInProgressException e ) {
           Kogger.error(getClass(), e);
       }
       catch ( WTException e ) {
           Kogger.error(getClass(), e);
       }
       catch ( WTPropertyVetoException e ) {
           Kogger.error(getClass(), e);
       }
       return rsltPart;

   }

   @Override
   public Hashtable approval( Hashtable hash )
            throws WTException {

      return null;
   }

   @Override
   public String getLatestMapprovalState( WTPart _part )
            throws WTException {

      return null;
   }

   @Override
   public Vector createSapPart( Hashtable hash )
            throws WTException {
	   Vector vecResult = ItemMasterInterface.setMoldMasterInfo(hash);

      return vecResult;
   }

}
