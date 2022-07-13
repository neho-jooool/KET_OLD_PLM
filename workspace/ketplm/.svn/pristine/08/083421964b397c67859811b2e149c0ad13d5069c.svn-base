/* bcwti????Copyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.????This software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.????ecwti
 */

package ext.ket.cost.entity;

import com.ptc.windchill.annotations.metadata.ColumnProperties;
import com.ptc.windchill.annotations.metadata.ColumnType;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;
import com.ptc.windchill.annotations.metadata.Serialization;

import e3ps.common.impl.Tree;
import wt.fc.InvalidAttributeException;
import wt.util.WTException;

/**
 *
 * <p>
 * Use the <code>newCostFormula</code> static factory method(s), not the
 * <code>CostFormula</code> constructor, to construct instances of this
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
   @GeneratedProperty(name="formula", type=String.class,
      constraints=@PropertyConstraints(upperLimit=8000)),
   @GeneratedProperty(name="formulaKeys", type=String.class,
      constraints=@PropertyConstraints(upperLimit=8000)),
   @GeneratedProperty(name="calculationStd", type=String.class),
   @GeneratedProperty(name="sortLocation", type=Integer.class),
   @GeneratedProperty(name="deptType", type=String.class),
   @GeneratedProperty(name="partType", type=String.class,
      constraints=@PropertyConstraints(upperLimit=8000)),
   @GeneratedProperty(name="mftFactory1", type=String.class,
      constraints=@PropertyConstraints(upperLimit=8000)),
   @GeneratedProperty(name="mftFactory2", type=Object.class,columnProperties=@ColumnProperties(columnType=ColumnType.BLOB )),
   @GeneratedProperty(name="mftFactory2_old", type=String.class,constraints=@PropertyConstraints(upperLimit=8000)),
   @GeneratedProperty(name="mappingCode", type=String.class),
   @GeneratedProperty(name="name", type=String.class),
   @GeneratedProperty(name="formulaVersion", type=Integer.class, initialValue="0", javaDoc="수식 버전"),
   @GeneratedProperty(name="status", type=String.class, initialValue="\"INWORK\"",javaDoc="수식배포상태"),//INWORK, COMPLETED
   })
public class CostFormula extends _CostFormula {

   static final long serialVersionUID = 1;

   /**
    * Default factory for the class.
    *
    * @return    CostFormula
    * @exception wt.util.WTException
    **/
   public static CostFormula newCostFormula()
            throws WTException {

      CostFormula instance = new CostFormula();
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
