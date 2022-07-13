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
import e3ps.project.outputtype.OEMProjectType;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import java.sql.Timestamp;
import wt.change2.WTChangeRequest2;
import wt.enterprise.Managed;
import wt.fc.ObjectReference;
import wt.inf.container.WTContained;
import wt.inf.container.WTContainer;
import wt.inf.container.WTContainerRef;
import wt.org.WTUser;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;

/**
 * 제품, 금형 ECR의 확장팩
 * <p>
 * Use the <code>newKETChangeRequestExpansion</code> static factory method(s),
 * not the <code>KETChangeRequestExpansion</code> constructor, to construct
 * instances of this class.  Instances must be constructed using the static
 * factory(s), in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(superClass=Managed.class, interfaces={WTContained.class}, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="moldChangeCode", type=String.class,
      javaDoc="NumberCode의 코드(금형변경)≪≫CODETYPE : 'MOLDCHANGE'"),
   @GeneratedProperty(name="costChangeCode", type=String.class,
      javaDoc="NumberCode의 코드(원가변동)≪≫CODETYPE : 'COSTCHANGE'"),
   @GeneratedProperty(name="emergencyPositionCode", type=String.class,
      javaDoc="NumberCode의 코드(긴급도)≪≫CODETYPE : 'EMERGENCYPOSITION'"),
   @GeneratedProperty(name="carTypeCode", type=String.class,
      javaDoc="OEMProjectType의 코드(차종)"),
   @GeneratedProperty(name="reviewRequestDate", type=Timestamp.class,
      javaDoc="검토요청기한(금형일 경우 회신기한)"),
   @GeneratedProperty(name="subjectCode", type=String.class,
      javaDoc="주관부서코드"),
   @GeneratedProperty(name="chargeName", type=String.class,
      javaDoc="담당자 ID(WTUser의 Name)"),
   @GeneratedProperty(name="webEditor", type=Object.class,
      javaDoc="현상",
      columnProperties=@ColumnProperties(columnType=ColumnType.BLOB)),
   @GeneratedProperty(name="webEditorText", type=Object.class,
      javaDoc="현상",
      columnProperties=@ColumnProperties(columnType=ColumnType.BLOB)),
   @GeneratedProperty(name="webEditor1", type=Object.class,
      javaDoc="문제점 및 요구사항",
      columnProperties=@ColumnProperties(columnType=ColumnType.BLOB)),
   @GeneratedProperty(name="webEditorText1", type=Object.class,
      javaDoc="문제점 및 요구사항",
      columnProperties=@ColumnProperties(columnType=ColumnType.BLOB))
   },
   foreignKeys={
   @GeneratedForeignKey(
      foreignKeyRole=@ForeignKeyRole(name="ecr", type=wt.change2.WTChangeRequest2.class),
      myRole=@MyRole(name="theKETChangeRequestExpansion", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(
      foreignKeyRole=@ForeignKeyRole(name="moldChange", type=e3ps.common.code.NumberCode.class),
      myRole=@MyRole(name="theKETChangeRequestExpansion", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(
      foreignKeyRole=@ForeignKeyRole(name="emergencyPosition", type=e3ps.common.code.NumberCode.class),
      myRole=@MyRole(name="theKETChangeRequestExpansion", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(
      foreignKeyRole=@ForeignKeyRole(name="costChange", type=e3ps.common.code.NumberCode.class),
      myRole=@MyRole(name="theKETChangeRequestExpansion", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(
      foreignKeyRole=@ForeignKeyRole(name="carType", type=e3ps.project.outputtype.OEMProjectType.class),
      myRole=@MyRole(name="theKETChangeRequestExpansion", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(
      foreignKeyRole=@ForeignKeyRole(name="subject", type=e3ps.groupware.company.Department.class),
      myRole=@MyRole(name="theKETChangeRequestExpansion", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(
      foreignKeyRole=@ForeignKeyRole(name="charge", type=wt.org.WTUser.class),
      myRole=@MyRole(name="theKETChangeRequestExpansion", cardinality=Cardinality.ONE))
   })
public class KETChangeRequestExpansion extends _KETChangeRequestExpansion {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    KETChangeRequestExpansion
    * @exception wt.util.WTException
    **/
   public static KETChangeRequestExpansion newKETChangeRequestExpansion()
            throws WTException {

      KETChangeRequestExpansion instance = new KETChangeRequestExpansion();
      instance.initialize();
      return instance;
   }

}
