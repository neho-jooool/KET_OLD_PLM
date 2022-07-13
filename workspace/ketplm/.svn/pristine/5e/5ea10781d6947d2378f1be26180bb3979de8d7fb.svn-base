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

package e3ps.project;

import e3ps.common.impl.OwnPersistable;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import wt.fc.InvalidAttributeException;
import wt.fc.PersistInfo;
import wt.introspection.ClassInfo;
import wt.introspection.WTIntrospectionException;
import wt.introspection.WTIntrospector;
import wt.org.WTPrincipalReference;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;

/**
 * 성과관리 평가 기준지표(Weights)≪≫isProject
 * <p>
 * Use the <code>newWeights</code> static factory method(s), not the <code>Weights</code>
 * constructor, to construct instances of this class.  Instances must be
 * constructed using the static factory(s), in order to ensure proper initialization
 * of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(interfaces={OwnPersistable.class}, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="quality", type=int.class),
   @GeneratedProperty(name="cost", type=int.class),
   @GeneratedProperty(name="deliveryOne", type=int.class),
   @GeneratedProperty(name="deliveryTwo", type=int.class),
   @GeneratedProperty(name="deliveryThree", type=int.class),
   @GeneratedProperty(name="technical", type=int.class),
   @GeneratedProperty(name="wType", type=int.class),
   @GeneratedProperty(name="isProject", type=boolean.class, initialValue="false"),
   @GeneratedProperty(name="totalS", type=int.class),
   @GeneratedProperty(name="totalA", type=int.class),
   @GeneratedProperty(name="totalB", type=int.class),
   @GeneratedProperty(name="totalC", type=int.class),
   @GeneratedProperty(name="totalD", type=int.class),
   @GeneratedProperty(name="qS", type=String.class),
   @GeneratedProperty(name="qA", type=String.class),
   @GeneratedProperty(name="qB", type=String.class),
   @GeneratedProperty(name="qC", type=String.class),
   @GeneratedProperty(name="qD", type=String.class),
   @GeneratedProperty(name="cS", type=String.class),
   @GeneratedProperty(name="cA", type=String.class),
   @GeneratedProperty(name="cB", type=String.class),
   @GeneratedProperty(name="cC", type=String.class),
   @GeneratedProperty(name="cD", type=String.class),
   @GeneratedProperty(name="c2S", type=String.class),
   @GeneratedProperty(name="c2A", type=String.class),
   @GeneratedProperty(name="c2B", type=String.class),
   @GeneratedProperty(name="c2C", type=String.class),
   @GeneratedProperty(name="c2D", type=String.class),
   @GeneratedProperty(name="dOneS", type=String.class),
   @GeneratedProperty(name="dOneA", type=String.class),
   @GeneratedProperty(name="dOneB", type=String.class),
   @GeneratedProperty(name="dOneC", type=String.class),
   @GeneratedProperty(name="dOneD", type=String.class),
   @GeneratedProperty(name="dTwoS", type=String.class),
   @GeneratedProperty(name="dTwoA", type=String.class),
   @GeneratedProperty(name="dTwoB", type=String.class),
   @GeneratedProperty(name="dTwoC", type=String.class),
   @GeneratedProperty(name="dTwoD", type=String.class),
   @GeneratedProperty(name="dThreeS", type=String.class),
   @GeneratedProperty(name="dThreeA", type=String.class),
   @GeneratedProperty(name="dThreeB", type=String.class),
   @GeneratedProperty(name="dThreeC", type=String.class),
   @GeneratedProperty(name="dThreeD", type=String.class),
   @GeneratedProperty(name="tS", type=String.class),
   @GeneratedProperty(name="tA", type=String.class),
   @GeneratedProperty(name="tB", type=String.class),
   @GeneratedProperty(name="tC", type=String.class),
   @GeneratedProperty(name="tD", type=String.class),
   @GeneratedProperty(name="keyNo", type=String.class)
   })
public class Weights extends _Weights {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    Weights
    * @exception wt.util.WTException
    **/
   public static Weights newWeights()
            throws WTException {

      Weights instance = new Weights();
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
