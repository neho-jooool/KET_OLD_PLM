/* bcwti≪≫≪≫Copyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.≪≫≪≫This software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.≪≫≪≫ecwti
 */

package ext.ket.common;

import java.lang.String;
import java.sql.Timestamp;
import wt.fc.InvalidAttributeException;
import wt.util.WTException;
import com.ptc.windchill.annotations.metadata.*;

/**
 *
 * <p>
 * Use the <code>newKETHistoryMaster</code> static factory method(s), not
 * the <code>KETHistoryMaster</code> constructor, to construct instances
 * of this class.  Instances must be constructed using the static factory(s),
 * in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="historyType", type=String.class,
      javaDoc="이력유형"),
   @GeneratedProperty(name="targetId", type=String.class,
      javaDoc="대상자아이디"),
   @GeneratedProperty(name="targetName", type=String.class,
      javaDoc="대상자이름"),
   @GeneratedProperty(name="targetContent", type=String.class,
      javaDoc="대상",
      constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="deptcode", type=String.class,
      javaDoc="소속부서코드"),
   @GeneratedProperty(name="deptName", type=String.class,
      javaDoc="소속부서명"),
   @GeneratedProperty(name="deptManagerId", type=String.class,
      javaDoc="소속부서팀장아이디"),
   @GeneratedProperty(name="deptManagerName", type=String.class,
      javaDoc="소속부서팀장이름"),
   @GeneratedProperty(name="historyDate", type=Timestamp.class,
      javaDoc="이력일")
   })
public class KETHistoryMaster extends _KETHistoryMaster {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    KETHistoryMaster
    * @exception wt.util.WTException
    **/
   public static KETHistoryMaster newKETHistoryMaster()
            throws WTException {

      KETHistoryMaster instance = new KETHistoryMaster();
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
