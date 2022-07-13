/* bcwti????Copyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.????This software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.????ecwti
 */

package ext.ket.cost.entity;

import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.Serialization;

import e3ps.common.impl.Tree;
import wt.fc.InvalidAttributeException;
import wt.util.WTException;

/**
 *
 * <p>
 * Use the <code>newCostPartType</code> static factory method(s), not the
 * <code>CostPartType</code> constructor, to construct instances of this
 * class.  Instances must be constructed using the static factory(s), in
 * order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(interfaces={Tree.class}, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="name", type=String.class),
   @GeneratedProperty(name="code", type=String.class),
   @GeneratedProperty(name="lvl", type=String.class),
   @GeneratedProperty(name="lvlOption", type=String.class),
   @GeneratedProperty(name="parentCfg", type=String.class),
   @GeneratedProperty(name="childCfg", type=String.class),
   @GeneratedProperty(name="taskCode", type=String.class),
   @GeneratedProperty(name="workHour", type=String.class, //initialValue="\"21\"",
   javaDoc="조업도(시간)"),
   @GeneratedProperty(name="workDay", type=String.class, //initialValue="\"20\"",
   javaDoc="조업도(일)"),
   @GeneratedProperty(name="workYear", type=String.class, //initialValue="\"12\"",
   javaDoc="년조업도(월)"),
   @GeneratedProperty(name="capaYear", type=String.class, javaDoc="적용년수(capa)" //,initialValue="\"6\""
   ),
   @GeneratedProperty(name="mftFactory1", type=String.class, javaDoc="생산정보 - 생산국(입고처)"),
   @GeneratedProperty(name="mftFactory2", type=String.class,javaDoc="생산지(유/무상)"),
   @GeneratedProperty(name="capaCfg", type=boolean.class, initialValue="false",javaDoc="capa 설정(감가비)"),
   @GeneratedProperty(name="costRatioCode", type=String.class ,javaDoc="원가비율분석용 코드"),
   @GeneratedProperty(name="sortLocation", type=Integer.class)
   })
public class CostPartType extends _CostPartType {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    CostPartType
    * @exception wt.util.WTException
    **/
   public static CostPartType newCostPartType()
            throws WTException {

      CostPartType instance = new CostPartType();
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
