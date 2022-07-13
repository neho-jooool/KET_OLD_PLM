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

package e3ps.lesson;

import e3ps.common.code.NumberCode;
import e3ps.groupware.company.Department;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;
import wt.content.ContentHolder;
import wt.content.HttpContentOperation;
import wt.fc.InvalidAttributeException;
import wt.fc.ObjectReference;
import wt.fc.PersistInfo;
import wt.fc.Persistable;
import wt.introspection.ClassInfo;
import wt.introspection.WTIntrospectionException;
import wt.introspection.WTIntrospector;
import wt.org.WTUser;
import wt.part.WTPart;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;
import e3ps.common.code.NumberCode;  // Preserved unmodeled dependency

/**
 *
 * <p>
 * Use the <code>newLessonLearn</code> static factory method(s), not the
 * <code>LessonLearn</code> constructor, to construct instances of this
 * class.  Instances must be constructed using the static factory(s), in
 * order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(interfaces={ContentHolder.class}, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="problem", type=String.class,
      javaDoc="문제점",
      constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="cause", type=String.class,
      javaDoc="원인",
      constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="improve", type=String.class,
      javaDoc="개선대책",
      constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="insert_date", type=Timestamp.class,
      javaDoc="입력일자"),
   @GeneratedProperty(name="equip_no", type=String.class,
      javaDoc="설비번호",
      constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="projectOid", type=String.class),
   @GeneratedProperty(name="occur_date", type=Timestamp.class,
      javaDoc="발생일자"),
   @GeneratedProperty(name="dieNo", type=String.class,
      javaDoc="dieNo"),
   @GeneratedProperty(name="partNo", type=String.class,
      javaDoc="partNo"),
   @GeneratedProperty(name="cause_gb", type=String.class,
      javaDoc="원인구분"),
   @GeneratedProperty(name="improve_gb", type=String.class,
      javaDoc="개선대책구분"),
   @GeneratedProperty(name="equip_nm", type=String.class,
      javaDoc="설비명",
      constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="die_nm", type=String.class),
   @GeneratedProperty(name="part_nm", type=String.class)
   },
   foreignKeys={
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="user", type=wt.org.WTUser.class),
      myRole=@MyRole(name="theLessonLearn", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="department", type=e3ps.groupware.company.Department.class),
      myRole=@MyRole(name="theLessonLearn", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="part", type=wt.part.WTPart.class),
      myRole=@MyRole(name="theLessonLearn", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="die", type=wt.part.WTPart.class),
      myRole=@MyRole(name="theLessonLearn", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="factory", type=e3ps.common.code.NumberCode.class),
      myRole=@MyRole(name="theLessonLearn", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="gubun", type=e3ps.common.code.NumberCode.class),
      myRole=@MyRole(name="theLessonLearn", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="product_gubun", type=e3ps.common.code.NumberCode.class),
      myRole=@MyRole(name="theLessonLearn", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="cause_gubun", type=e3ps.common.code.NumberCode.class),
      myRole=@MyRole(name="theLessonLearn", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="improve_gubun", type=e3ps.common.code.NumberCode.class),
      myRole=@MyRole(name="theLessonLearn", cardinality=Cardinality.ONE))
   })
public class LessonLearn extends _LessonLearn {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    LessonLearn
    * @exception wt.util.WTException
    **/
   public static LessonLearn newLessonLearn()
            throws WTException {

      LessonLearn instance = new LessonLearn();
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
