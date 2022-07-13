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

package e3ps.groupware.company;

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
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;


/**
 * proxy : 대결재자
 * <p>
 * Use the <code>newPeople</code> static factory method(s), not the <code>People</code>
 * constructor, to construct instances of this class.  Instances must be
 * constructed using the static factory(s), in order to ensure proper initialization
 * of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(interfaces={ContentHolder.class}, versions={-5802461581330891587L},
   properties={
   @GeneratedProperty(name="id", type=String.class),
   @GeneratedProperty(name="name", type=String.class),
   @GeneratedProperty(name="email", type=String.class),
   @GeneratedProperty(name="duty", type=String.class,
      javaDoc="직위 : eSolution.properties 파일에 정의된 직위체계를 따른다."),
   @GeneratedProperty(name="dutyCode", type=String.class,
      javaDoc="사내 서열을 표현하기 위한 필드(낮을수록 서열이 높다)"),
   @GeneratedProperty(name="officeTel", type=String.class,
      javaDoc="회사전화번호"),
   @GeneratedProperty(name="homeTel", type=String.class,
      javaDoc="집전화번호"),
   @GeneratedProperty(name="cellTel", type=String.class,
      javaDoc="핸드폰번호"),
   @GeneratedProperty(name="address", type=String.class,
      javaDoc="주소"),
   @GeneratedProperty(name="priority", type=int.class),
   @GeneratedProperty(name="password", type=String.class),
   @GeneratedProperty(name="isDisable", type=boolean.class, initialValue="false"),
   @GeneratedProperty(name="sortNum", type=int.class, initialValue="0",
      constraints=@PropertyConstraints(upperLimit=30)),
   @GeneratedProperty(name="proxy", type=ObjectReference.class),
   @GeneratedProperty(name="warningCount", type=int.class, initialValue="0"),
   @GeneratedProperty(name="pwChangeDate", type=Timestamp.class),
   @GeneratedProperty(name="enterDate", type=Timestamp.class,
      javaDoc="입사일≪≫"),
   @GeneratedProperty(name="retrireDate", type=Timestamp.class,
      javaDoc="퇴사일"),
   @GeneratedProperty(name="gradeName", type=String.class),
   @GeneratedProperty(name="gradeCode", type=String.class),
   @GeneratedProperty(name="ccCode", type=String.class),
   @GeneratedProperty(name="perNo", type=String.class,
      javaDoc="주민번호"),
   @GeneratedProperty(name="bankCode", type=String.class,
      javaDoc="은행코드"),
   @GeneratedProperty(name="nationalCode", type=String.class,
      javaDoc="국가코드"),
   @GeneratedProperty(name="accountNo", type=String.class,
      javaDoc="계좌번호"),
   @GeneratedProperty(name="zipNo", type=String.class,
      javaDoc="우편번호"),
   @GeneratedProperty(name="chief", type=String.class),
   @GeneratedProperty(name="retireTargetDate", type=Timestamp.class,javaDoc="퇴사예정일"),
   },
   foreignKeys={
   @GeneratedForeignKey(name="WTUserPeopleLink", myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="user", type=wt.org.WTUser.class,
         constraints=@PropertyConstraints(required=true)),
      myRole=@MyRole(name="people", cardinality=Cardinality.ZERO_TO_ONE)),
   @GeneratedForeignKey(name="DepartmentPeopleLink",
      foreignKeyRole=@ForeignKeyRole(name="department", type=e3ps.groupware.company.Department.class),
      myRole=@MyRole(name="people"))
   })
public class People extends _People {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    People
    * @exception wt.util.WTException
    **/
   public static People newPeople()
            throws WTException {

      People instance = new People();
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

   /**
    * Reads the non-transient fields of this class from an external source.
    *
    * @param     input
    * @param     readSerialVersionUID
    * @param     superDone
    * @return    boolean
    * @exception java.io.IOException
    * @exception java.lang.ClassNotFoundException
    **/
   boolean readVersion_5802461581330891587L( ObjectInput input, long readSerialVersionUID, boolean superDone )
            throws IOException, ClassNotFoundException {

      accountNo = (String)input.readObject();
      address = (String)input.readObject();
      bankCode = (String)input.readObject();
      ccCode = (String)input.readObject();
      cellTel = (String)input.readObject();
      chief = (String)input.readObject();
      departmentReference = (ObjectReference)input.readObject();
      duty = (String)input.readObject();
      dutyCode = (String)input.readObject();
      email = (String)input.readObject();
      enterDate = (Timestamp)input.readObject();
      gradeCode = (String)input.readObject();
      gradeName = (String)input.readObject();
      homeTel = (String)input.readObject();
      id = (String)input.readObject();
      isDisable = input.readBoolean();
      name = (String)input.readObject();
      nationalCode = (String)input.readObject();
      officeTel = (String)input.readObject();
      password = (String)input.readObject();
      perNo = (String)input.readObject();
      priority = input.readInt();
      proxy = (ObjectReference)input.readObject();
      pwChangeDate = (Timestamp)input.readObject();
      retrireDate = (Timestamp)input.readObject();
      sortNum = input.readInt();
      thePersistInfo = (PersistInfo)input.readObject();
      userReference = (ObjectReference)input.readObject();
      warningCount = input.readInt();
      zipNo = (String)input.readObject();
      if ( !( input instanceof wt.pds.PDSObjectInput ) ) {           // for non-persistent fields
         contentVector = (Vector)input.readObject();
         hasContents = input.readBoolean();
         httpVector = (Vector)input.readObject();
         operation = (HttpContentOperation)input.readObject();
      }

      return true;
   }

}
