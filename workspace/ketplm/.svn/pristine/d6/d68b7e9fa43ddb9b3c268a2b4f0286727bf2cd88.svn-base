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

package e3ps.dms.entity;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.Boolean;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import wt.fc.InvalidAttributeException;
import wt.fc.ObjectReference;
import wt.fc.PersistInfo;
import wt.fc.Persistable;
import wt.folder.Folder;
import wt.introspection.ClassInfo;
import wt.introspection.WTIntrospectionException;
import wt.introspection.WTIntrospector;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;

/**
 *
 * <p>
 * Use the <code>newKETDocumentCategory</code> static factory method(s),
 * not the <code>KETDocumentCategory</code> constructor, to construct instances
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
   @GeneratedProperty(name="categoryCode", type=String.class,
      javaDoc="분류식별자"),
   @GeneratedProperty(name="categoryName", type=String.class,
      javaDoc="분류 이름"),
   @GeneratedProperty(name="docTypeCode", type=String.class,
      javaDoc="문서번호 채번 시 앞 2자리 구분자로 사용"),
   @GeneratedProperty(name="sortNo", type=int.class,
      javaDoc="분류체계 정렬순서"),
   @GeneratedProperty(name="categoryLevel", type=int.class,
      javaDoc="분류체계 레벨"),
   @GeneratedProperty(name="parentCategoryCode", type=String.class,
      javaDoc="상위분류 식별자"),
   @GeneratedProperty(name="isUsed", type=Boolean.class,
      javaDoc="사용여부"),
   @GeneratedProperty(name="isAPQP", type=Boolean.class,
      javaDoc="APQP대상여부 구분 값"),
   @GeneratedProperty(name="isPSO10", type=Boolean.class,
      javaDoc="PSP10대상여부 구분 값"),
   @GeneratedProperty(name="isESIR", type=Boolean.class,
      javaDoc="ESIR대상여부 구분 값"),
   @GeneratedProperty(name="isISIRCar", type=Boolean.class,
      javaDoc="ISIR대상여부 구분 값"),
   @GeneratedProperty(name="isISIRElec", type=Boolean.class,
      javaDoc="ISIR대상여부 구분 값"),
   @GeneratedProperty(name="isANPQP", type=Boolean.class,
      javaDoc="ANPQP대상여부 구분 값"),
   @GeneratedProperty(name="isSYMC", type=Boolean.class,
      javaDoc="SYMC대상여부 구분 값"),
   @GeneratedProperty(name="isDRCheckSheet", type=Boolean.class,
      javaDoc="DR CheckSheet 대상여부 구분값"),
   @GeneratedProperty(name="attribute1", type=String.class,
      javaDoc="추가 속성 항목1"),
   @GeneratedProperty(name="attribute2", type=String.class,
      javaDoc="추가 속성 항목2"),
   @GeneratedProperty(name="attribute3", type=String.class,
      javaDoc="추가 속성 항목3"),
   @GeneratedProperty(name="attribute4", type=String.class,
      javaDoc="추가 속성 항목4"),
   @GeneratedProperty(name="attribute5", type=String.class,
      javaDoc="추가 속성 항목5"),
   @GeneratedProperty(name="attribute6", type=String.class,
      javaDoc="추가 속성 항목6"),
   @GeneratedProperty(name="attribute7", type=String.class,
      javaDoc="추가 속성 항목7"),
   @GeneratedProperty(name="attribute8", type=String.class,
      javaDoc="추가 속성 항목8"),
   @GeneratedProperty(name="attribute9", type=String.class,
      javaDoc="추가 속성 항목9"),
   @GeneratedProperty(name="attribute10", type=String.class,
      javaDoc="추가 속성 항목10")
   },
   foreignKeys={
   @GeneratedForeignKey(name="KETFolderDocumentCategory", myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="folder", type=wt.folder.Folder.class,
         constraints=@PropertyConstraints(required=true)),
      myRole=@MyRole(name="documentCategory", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(name="KETDocumentCategoryParentChild", myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="parent", type=e3ps.dms.entity.KETDocumentCategory.class,
         constraints=@PropertyConstraints(required=true)),
      myRole=@MyRole(name="child"))
   })
public class KETDocumentCategory extends _KETDocumentCategory {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    KETDocumentCategory
    * @exception wt.util.WTException
    **/
   public static KETDocumentCategory newKETDocumentCategory()
            throws WTException {

      KETDocumentCategory instance = new KETDocumentCategory();
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
