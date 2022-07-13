/* bcwti≪≫≪≫Copyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.≪≫≪≫This software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.≪≫≪≫ecwti
 */

package ext.ket.cost.entity;

import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.Serialization;

import e3ps.common.impl.OwnPersistable;
import wt.fc.InvalidAttributeException;
import wt.util.WTException;

/**
 * 수지 원재료 정보
 * <p>
 * Use the <code>newCostMaterialInfo</code> static factory method(s), not
 * the <code>CostMaterialInfo</code> constructor, to construct instances
 * of this class.  Instances must be constructed using the static factory(s),
 * in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(interfaces={OwnPersistable.class}, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="partNo", type=String.class,
      javaDoc="원재료 품번"),
   @GeneratedProperty(name="partName", type=String.class,
      javaDoc="원재료명"),
   @GeneratedProperty(name="materialPrice", type=String.class, initialValue="\"0\"",
      javaDoc="원재료 단가(수지, 비철)"),
   @GeneratedProperty(name="grade", type=String.class,
      javaDoc="grade"),
   @GeneratedProperty(name="color", type=String.class),
   @GeneratedProperty(name="scrapCost", type=String.class,
      javaDoc="스크랩판매비"),
   @GeneratedProperty(name="scrapRecycle", type=String.class,
      javaDoc="스크랩재생비"),
   @GeneratedProperty(name="rate", type=String.class,
      javaDoc="비율"),
   @GeneratedProperty(name="erpCheck", type=boolean.class, initialValue="false",
      javaDoc="자재존재여부")
   })
public class CostMaterialInfo extends _CostMaterialInfo {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    CostMaterialInfo
    * @exception wt.util.WTException
    **/
   public static CostMaterialInfo newCostMaterialInfo()
            throws WTException {

      CostMaterialInfo instance = new CostMaterialInfo();
      instance.initialize();
      return instance;
   }

   /**
    * Supports initialization, following construction of an instance.  Invoked
    * by "new" factory having the same signature.
    *
    * @exception wt.util.WTException
    **/
   protected void initialize()
            throws WTException {

   }

   /**
    * Gets the value of the attribute: IDENTITY.
    * Supplies the identity of the object for business purposes.  The identity
    * is composed of name, number or possibly other attributes.  The identity
    * does not include the type of the object.
    *
    *
    * <BR><BR><B>Supported API: </B>false
    *
    * @deprecated Replaced by IdentityFactory.getDispayIdentifier(object)
    * to return a localizable equivalent of getIdentity().  To return a
    * localizable value which includes the object type, use IdentityFactory.getDisplayIdentity(object).
    * Other alternatives are ((WTObject)obj).getDisplayIdentifier() and
    * ((WTObject)obj).getDisplayIdentity().
    *
    * @return    String
    **/
   public String getIdentity() {

      return null;
   }

   /**
    * Gets the value of the attribute: TYPE.
    * Identifies the type of the object for business purposes.  This is
    * typically the class name of the object but may be derived from some
    * other attribute of the object.
    *
    *
    * <BR><BR><B>Supported API: </B>false
    *
    * @deprecated Replaced by IdentityFactory.getDispayType(object) to return
    * a localizable equivalent of getType().  Another alternative is ((WTObject)obj).getDisplayType().
    *
    * @return    String
    **/
   public String getType() {

      return null;
   }

   @Override
   public void checkAttributes()
            throws InvalidAttributeException {

   }

}
