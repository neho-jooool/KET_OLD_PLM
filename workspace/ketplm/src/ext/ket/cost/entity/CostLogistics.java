/* bcwti≪≫≪≫Copyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.≪≫≪≫This software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.≪≫≪≫ecwti
 */

package ext.ket.cost.entity;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.ForeignKeyRole;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedForeignKey;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.MyRole;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;
import com.ptc.windchill.annotations.metadata.Serialization;

import e3ps.common.impl.OwnPersistable;
import wt.fc.InvalidAttributeException;
import wt.util.WTException;

/**
 * 관세,물류비 관리 기준정보
 * <p>
 * Use the <code>newCostLogistics</code> static factory method(s), not the
 * <code>CostLogistics</code> constructor, to construct instances of this
 * class.  Instances must be constructed using the static factory(s), in
 * order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(interfaces={OwnPersistable.class}, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="datatype", type=String.class,
      javaDoc="구분(원재료:M,부품:P)"),
   @GeneratedProperty(name="rate", type=String.class,
      javaDoc="관세율"),
   @GeneratedProperty(name="cost", type=String.class,
      javaDoc="물류비용"),
   @GeneratedProperty(name="inLandCost", type=String.class,
      javaDoc="내륙운송비(평택)"),
   @GeneratedProperty(name="inHarborCost", type=String.class,
      javaDoc="항구부대비용(평택)"),
   @GeneratedProperty(name="seaCost", type=String.class,
      javaDoc="해상운송비"),
   @GeneratedProperty(name="outHarborCost", type=String.class,
      javaDoc="항구부대비용(위해)"),
   @GeneratedProperty(name="outLandCost", type=String.class,
      javaDoc="내륙운송비(중국)"),
   @GeneratedProperty(name="seaCurrency", type=String.class,
      javaDoc="환율코드(해상)"),
   @GeneratedProperty(name="harborCurrency", type=String.class,
      javaDoc="환율코드(항구)"),
   @GeneratedProperty(name="landCurrency", type=String.class,
      javaDoc="환율코드(내륙)"),
   @GeneratedProperty(name="note", type=String.class,
      javaDoc="비고"),
   @GeneratedProperty(name="sortingOrder1", type=Integer.class,
      javaDoc="생산국 NumberCode의 Sort"),
   @GeneratedProperty(name="sortingOrder2", type=Integer.class,
      javaDoc="생산국 NumberCode의 Sort"),
   @GeneratedProperty(name="sortingOrder3", type=Integer.class,
      javaDoc="생산국 NumberCode의 Sort"),
   @GeneratedProperty(name="p_container", type=String.class,
      javaDoc="pallet/Container"),
   @GeneratedProperty(name="inLandCurrency", type=String.class,
      javaDoc="출하내륙운송비환율"),
   @GeneratedProperty(name="inHarborCurrency", type=String.class,
      javaDoc="출하항구부대비용환율"),
   @GeneratedProperty(name="mft1", type=String.class),
   @GeneratedProperty(name="mft2", type=String.class),
   @GeneratedProperty(name="mft3", type=String.class),
   @GeneratedProperty(name="mft4", type=String.class),
   },
   foreignKeys={
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="factory1", type=e3ps.common.code.NumberCode.class,
         constraints=@PropertyConstraints(required=false)),
      myRole=@MyRole(name="theCostLogistics", cardinality=Cardinality.ZERO_TO_ONE )),
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="factory2", type=e3ps.common.code.NumberCode.class,
         constraints=@PropertyConstraints(required=false)),
      myRole=@MyRole(name="theCostLogistics", cardinality=Cardinality.ZERO_TO_ONE)),
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="factory3", type=e3ps.common.code.NumberCode.class,
         constraints=@PropertyConstraints(required=false)),
      myRole=@MyRole(name="theCostLogistics", cardinality=Cardinality.ZERO_TO_ONE))
   })
public class CostLogistics extends _CostLogistics {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    CostLogistics
    * @exception wt.util.WTException
    **/
   public static CostLogistics newCostLogistics()
            throws WTException {

      CostLogistics instance = new CostLogistics();
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
