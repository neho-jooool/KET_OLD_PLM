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

package e3ps.part.service;

import java.io.Serializable;
import java.sql.Connection;  // Preserved unmodeled dependency
import java.sql.Date;  // Preserved unmodeled dependency
import java.util.Calendar;  // Preserved unmodeled dependency
import java.util.Hashtable;

import wt.fc.PersistenceHelper;  // Preserved unmodeled dependency
import wt.fc.ReferenceFactory;  // Preserved unmodeled dependency
import wt.pom.Transaction;  // Preserved unmodeled dependency
import wt.services.ManagerException;
import wt.services.StandardManager;
import wt.util.WTException;
import e3ps.common.code.NumberCode;  // Preserved unmodeled dependency
import e3ps.common.code.NumberCodeHelper;  // Preserved unmodeled dependency
import e3ps.common.util.KETStringUtil;  // Preserved unmodeled dependency
import e3ps.common.util.PlmDBUtil;  // Preserved unmodeled dependency
import e3ps.part.dao.KETNewPartListDao;  // Preserved unmodeled dependency
import e3ps.part.entity.KETNewPartList;  // Preserved unmodeled dependency
import e3ps.part.entity.KETNewPartListTypeLink;  // Preserved unmodeled dependency
import ext.ket.shared.log.Kogger;

/**
 *
 * <p>
 * Use the <code>newStandardKETNewPartListService</code> static factory
 * method(s), not the <code>StandardKETNewPartListService</code> constructor,
 * to construct instances of this class.  Instances must be constructed
 * using the static factory(s), in order to ensure proper initialization
 * of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

public class StandardKETNewPartListService extends StandardManager implements KETNewPartListService, Serializable {


   private static final String RESOURCE = "e3ps.part.service.serviceResource";
   private static final String CLASSNAME = StandardKETNewPartListService.class.getName();



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
    * @return    StandardKETNewPartListService
    * @exception wt.util.WTException
    **/
   public static StandardKETNewPartListService newStandardKETNewPartListService()
            throws WTException {

      StandardKETNewPartListService instance = new StandardKETNewPartListService();
      instance.initialize();
      return instance;
   }

   @Override
   public boolean createNewPartList( Hashtable arg )
            throws WTException {

	   Transaction transaction = new Transaction();
	   KETNewPartList ketNewPartList =null;
	   Date date = new Date(Calendar.getInstance().getTimeInMillis());

	   try {
			transaction.start();
			ketNewPartList = KETNewPartList.newKETNewPartList();

			ketNewPartList.setPartNumber((String) arg.get("partNumber"));
			ketNewPartList.setPartName((String) arg.get("partName"));
			ketNewPartList.setRawMaterial((String) arg.get("rawMaterial"));
			ketNewPartList.setCustomer((String) KETStringUtil.nvl((String)arg.get("customer"), "-"));
			ketNewPartList.setRegister((String) arg.get("register"));
			ketNewPartList.setRegDate(date);
			ketNewPartList.setModifier((String) arg.get("register"));
			ketNewPartList.setModDate(date);
			ketNewPartList.setDescription((String) arg.get("description"));
			ketNewPartList.setDel(false);

			PersistenceHelper.manager.save(ketNewPartList);

			String newparttypeReference = arg.get("newparttypeReference")+"";
			String newchetypeReference = "NEWPRODUCTTYPE";
			NumberCode partListTypeCode = NumberCodeHelper.manager.getNumberCode("NEWPARTTYPE", newparttypeReference);

			if("PT001".equals(newparttypeReference)) {
				newchetypeReference = "NEWPRODUCTTYPE";
			} else {
				newchetypeReference = "NEWDIETYPE";
			}

			NumberCode partListTypeCode2 = NumberCodeHelper.manager.getNumberCode(newchetypeReference, (String) arg.get("newchetypeReference"));
			KETNewPartListTypeLink link = KETNewPartListTypeLink.newKETNewPartListTypeLink(partListTypeCode, ketNewPartList); // ???, ???
			KETNewPartListTypeLink link2 = KETNewPartListTypeLink.newKETNewPartListTypeLink(partListTypeCode2, ketNewPartList); // 3level
			//PersistenceHelper.manager.save(link);
			PersistenceHelper.manager.save(link2);

			transaction.commit();
			transaction = null;
		} catch ( Exception e ) {
			transaction.rollback();
			Kogger.debug(getClass(), "#############################"+ketNewPartList.getPartNumber());
			Kogger.error(getClass(), e);
		} finally {
			if( transaction != null )
			{
				transaction.rollback();
			}
		}

	   return false;
   }

   @Override
   public boolean updateNewPartList( Hashtable arg )
            throws WTException {
	   boolean isSuccess = false;
	   Transaction trx = new Transaction();

	   try {
		   trx.start();
		   String oId = (String) arg.get("oId");
		   Date date = new Date(Calendar.getInstance().getTimeInMillis());

		   if( oId != null ) {
				ReferenceFactory rf = new ReferenceFactory();
				KETNewPartList ketNewPartList = (KETNewPartList)rf.getReference( oId ).getObject();

				ketNewPartList.setPartNumber((String) arg.get("partNumber"));
				ketNewPartList.setPartName((String) arg.get("partName"));
				ketNewPartList.setRawMaterial((String) arg.get("rawMaterial"));
				ketNewPartList.setCustomer((String) arg.get("customer"));
				ketNewPartList.setModifier((String) arg.get("modifier"));
				ketNewPartList.setModDate(date);
				ketNewPartList.setDescription((String) arg.get("description"));
				if("true".equals(arg.get("del")+"")) {
					ketNewPartList.setDel(true);
				} else {
					ketNewPartList.setDel(false);
				}

				PersistenceHelper.manager.modify( ketNewPartList );
				isSuccess = true;
		   }

		   trx.commit();
           trx = null;
	   } catch( Exception e ) {
		   Kogger.error(getClass(), e);

		   trx.rollback();
		   trx = null;
	   } finally {
		   if( trx != null ) {
			   trx.rollback();
			   trx = null;
		   }
	   }

      return isSuccess;
   }

   @Override
   public boolean deleteNewPartList( String oId )
            throws WTException {
	   boolean isSuccess = false;
	   Transaction trx = new Transaction();

	   try {
		   trx.start();

		   if( oId != null ) {
			   ReferenceFactory rf = new ReferenceFactory();
			   KETNewPartList ketNewPartList = (KETNewPartList)rf.getReference( oId ).getObject();
			   ketNewPartList.setDel(true);
			   PersistenceHelper.manager.modify( ketNewPartList );

			   isSuccess = true;
		   }

		   trx.commit();
           trx = null;
	   } catch( Exception e ) {
		   Kogger.error(getClass(), e);

		   trx.rollback();
		   trx = null;
	   } finally {
		   if( trx != null ) {
			   trx.rollback();
			   trx = null;
		   }
	   }

      return isSuccess;
   }

   @Override
   public Hashtable getNewPartList( Hashtable arg )
            throws WTException {
	   Hashtable hash = new Hashtable();
	   Transaction trx = new Transaction();
	   KETNewPartList ketNewPartList = new KETNewPartList();
	 //?????
       Connection conn = null;

	   try {
		   trx.start();

		   // ????????
			conn = PlmDBUtil.getConnection();
			KETNewPartListDao ketNewPartListDao = new KETNewPartListDao(conn);

		   String newparttypeReference = arg.get("newparttypeReference")+"";
		   String newchetypeReference = "NEWPRODUCTTYPE";

			if("PT001".equals(newparttypeReference)) {
				newchetypeReference = "NEWPRODUCTTYPE";
			} else {
				newchetypeReference = "NEWDIETYPE";
			}

			//NumberCode partListTypeCode2 = NumberCodeHelper.manager.getNumberCode(newchetypeReference, (String) arg.get("newchetypeReference"));
			String spartListTypeCode2 = NumberCodeHelper.manager.getNumberCodeOid(newchetypeReference, (String) arg.get("newchetypeReference"));

			Kogger.debug(getClass(), "??? ?????= "+ spartListTypeCode2 );
			arg.put("spartListTypeCode2", spartListTypeCode2);

			hash = ketNewPartListDao.getKETNewPartListDao(arg);
		  Kogger.debug(getClass(), "select ??? ");
	   } catch( Exception e ) {
		   Kogger.error(getClass(), e);

		   trx.rollback();
		   trx = null;
	   } finally {
		   if( trx != null ) {
			   trx.rollback();
			   trx = null;
		   }
		   if(conn != null) {
				//????????
				PlmDBUtil.close(conn);
			}
	   }

	   return hash;
   }

   @Override
   public boolean deleteRealNewPartList( String oid )
            throws WTException {

      return false;
   }

}
