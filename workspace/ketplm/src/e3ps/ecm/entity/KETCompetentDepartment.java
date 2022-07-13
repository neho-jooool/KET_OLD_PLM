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

package e3ps.ecm.entity;

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
import wt.enterprise.Managed;
import wt.fc.ObjectReference;
import wt.org.WTUser;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;

/**
 * �ְ��μ�
 * <p>
 * Use the <code>newKETCompetentDepartment</code> static factory method(s),
 * not the <code>KETCompetentDepartment</code> constructor, to construct
 * instances of this class.  Instances must be constructed using the static
 * factory(s), in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(superClass=Managed.class, interfaces={ContentHolder.class}, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="replyDeadline", type=Timestamp.class,
      javaDoc="회신기한(ECR의 검토요청기한)"),
   @GeneratedProperty(name="subjectCode", type=String.class,
      javaDoc="주관부서코드"),
   @GeneratedProperty(name="chargeName", type=String.class,
      javaDoc="담당자 ID(WTUser의 Name)"),
   @GeneratedProperty(name="webEditor", type=Object.class,
      columnProperties=@ColumnProperties(columnType=ColumnType.BLOB)),
   @GeneratedProperty(name="webEditorText", type=Object.class,
      columnProperties=@ColumnProperties(columnType=ColumnType.BLOB)),
   @GeneratedProperty(name="reviewCode", type=String.class,
      javaDoc="NumberCode의 코드(검토결과)"),
   @GeneratedProperty(name="meetingCode", type=String.class,
      javaDoc="NumberCode의 코드(회의필요여부)")
   },
   foreignKeys={
   @GeneratedForeignKey(
      foreignKeyRole=@ForeignKeyRole(name="charge", type=wt.org.WTUser.class),
      myRole=@MyRole(name="theKETCompetentDepartment", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(
      foreignKeyRole=@ForeignKeyRole(name="subject", type=e3ps.groupware.company.Department.class),
      myRole=@MyRole(name="theKETCompetentDepartment", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(
      foreignKeyRole=@ForeignKeyRole(name="review", type=e3ps.common.code.NumberCode.class),
      myRole=@MyRole(name="theKETCompetentDepartment", cardinality=Cardinality.ONE))
   })
public class KETCompetentDepartment extends _KETCompetentDepartment {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    KETCompetentDepartment
    * @exception wt.util.WTException
    **/
   public static KETCompetentDepartment newKETCompetentDepartment()
            throws WTException {

      KETCompetentDepartment instance = new KETCompetentDepartment();
      instance.initialize();
      return instance;
   }

}
