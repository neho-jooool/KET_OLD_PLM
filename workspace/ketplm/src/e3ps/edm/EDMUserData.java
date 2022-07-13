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

package e3ps.edm;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import java.sql.Timestamp;
import wt.fc.InvalidAttributeException;
import wt.fc.ObjectReference;
import wt.fc.PersistInfo;
import wt.fc.Persistable;
import wt.introspection.ClassInfo;
import wt.introspection.WTIntrospectionException;
import wt.introspection.WTIntrospector;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;
import e3ps.groupware.company.Department;  // Preserved unmodeled dependency
import e3ps.groupware.company.DepartmentHelper;  // Preserved unmodeled dependency

/**
 *
 * <p>
 * Use the <code>newEDMUserData</code> static factory method(s), not the
 * <code>EDMUserData</code> constructor, to construct instances of this
 * class.  Instances must be constructed using the static factory(s), in
 * order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="initCreatorNo", type=String.class,
      javaDoc="최초 작성자 사번"),
   @GeneratedProperty(name="initCreatorId", type=String.class,
      javaDoc="최초 작성자 ID"),
   @GeneratedProperty(name="initCreatorName", type=String.class),
   @GeneratedProperty(name="initDeptName", type=String.class,
      javaDoc="최초 작성자의 소속 부서(작성 부서)"),
   @GeneratedProperty(name="initDeptEnName", type=String.class,
      javaDoc="최초 작성자의 소속 부서(작성 부서 영문명)"),
   @GeneratedProperty(name="initCreateTime", type=Timestamp.class),
   @GeneratedProperty(name="creatorNo", type=String.class,
      javaDoc="버전별 작성자 사번"),
   @GeneratedProperty(name="creatorId", type=String.class,
      javaDoc="버전별 작성자 ID"),
   @GeneratedProperty(name="creatorName", type=String.class),
   @GeneratedProperty(name="creatorDeptName", type=String.class,
      javaDoc="버전별 작성자의 소속 부서(작성 부서)"),
   @GeneratedProperty(name="creatorDeptEnName", type=String.class,
      javaDoc="버전별 작성자의 소속 부서(작성 부서 영문명)"),
   @GeneratedProperty(name="createTime", type=Timestamp.class),
   @GeneratedProperty(name="modifierNo", type=String.class,
      javaDoc="버전별 수정자의 사번"),
   @GeneratedProperty(name="modifierId", type=String.class,
      javaDoc="버전별 수정자의 ID"),
   @GeneratedProperty(name="modifierName", type=String.class),
   @GeneratedProperty(name="modifierDeptName", type=String.class,
      javaDoc="버전별 수정자의 소속 부서(작성 부서)"),
   @GeneratedProperty(name="modifierDeptEnName", type=String.class,
      javaDoc="버전별 수정자의 소속 부서(작성 부서 영문명)"),
   @GeneratedProperty(name="modifyTime", type=Timestamp.class),
   @GeneratedProperty(name="objNumber", type=String.class),
   @GeneratedProperty(name="objVersion", type=String.class),
   @GeneratedProperty(name="objClassname", type=String.class),
   @GeneratedProperty(name="objBranchId", type=long.class),
   @GeneratedProperty(name="preVersionUserData", type=ObjectReference.class,
      constraints=@PropertyConstraints(required=true)),
   @GeneratedProperty(name="objData", type=ObjectReference.class,
      constraints=@PropertyConstraints(required=true))
   })
public class EDMUserData extends _EDMUserData {


   static final long serialVersionUID = 1;




   /**
    * @return    Object
    **/
   public Object clone() {
	   try {
		   EDMUserData d = EDMUserData.newEDMUserData();
		   d.setInitCreatorNo( (this.getInitCreatorNo()==null)? "":this.getInitCreatorNo() );
		   d.setInitCreatorId( (this.getInitCreatorId()==null)? "":this.getInitCreatorId() );
		   d.setInitCreatorName( (this.getInitCreatorName()==null)? "":this.getInitCreatorName() );
		   d.setInitDeptName( (this.getInitDeptName()==null) ? "":this.getInitCreatorName());
		   if(this.getInitCreateTime() != null) { d.setInitCreateTime(this.getInitCreateTime()); }
		   
		   d.setCreatorNo( (this.getCreatorNo()==null)? "":this.getCreatorNo() );
		   d.setCreatorId( (this.getCreatorId()==null)? "":this.getCreatorId() );
		   d.setCreatorName( (this.getCreatorName()==null)? "":this.getCreatorName() );
		   d.setCreatorDeptName( (this.getCreatorDeptName()==null)? "":this.getCreatorDeptName() );
		   if(this.getCreateTime() != null) { d.setCreateTime(this.getCreateTime()); }
		   
		   d.setObjNumber( (this.getObjNumber()==null)? "":this.getObjNumber() );
		   d.setObjVersion( (this.getObjVersion()==null)? "":this.getObjVersion() );
		   d.setObjClassname( (this.getObjClassname()==null)? "":this.getObjClassname() );
		   d.setObjBranchId( (this.getObjBranchId()==0)? 0:this.getObjBranchId() );
		   
		   d.setPreVersionUserData(ObjectReference.newObjectReference(this));
		   if( (this.getObjData() != null) && (this.getObjData().getObject() != null) ){ d.setObjData(this.getObjData()); }
		   
		   
		   wt.org.WTUser wtuser = (wt.org.WTUser)wt.session.SessionHelper.manager.getPrincipal();
		   e3ps.groupware.company.Department dept = e3ps.groupware.company.DepartmentHelper.manager.getDepartment(wtuser);
		   String deptName = (dept==null)? "":dept.getName();
		   d.setModifierNo(wtuser.getName());
		   d.setModifierId(wtuser.getName());
		   d.setModifierName(wtuser.getFullName());
		   d.setModifierDeptName(deptName);
		   d.setModifyTime(new java.sql.Timestamp(System.currentTimeMillis()));	   
		   
		   return wt.fc.PersistenceHelper.manager.save(d);
	   }
	   catch(Exception e) {
		   e.printStackTrace();
	   }
	   
      return null;
   }

   /**
    * Default factory for the class.
    *
    * @return    EDMUserData
    * @exception wt.util.WTException
    **/
   public static EDMUserData newEDMUserData()
            throws WTException {

      EDMUserData instance = new EDMUserData();
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
