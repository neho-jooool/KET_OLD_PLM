/* bcwti≪≫≪≫Copyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.≪≫≪≫This software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.≪≫≪≫ecwti
 */

package ext.ket.sales.entity;

import e3ps.common.code.NumberCode;
import e3ps.dms.entity.KETDevelopmentRequest;
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
import wt.doc.WTDocument;
import wt.fc.ObjectReference;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;

/**
 *
 * <p>
 * Use the <code>newKETSalesProject</code> static factory method(s), not
 * the <code>KETSalesProject</code> constructor, to construct instances
 * of this class.  Instances must be constructed using the static factory(s),
 * in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(superClass=WTDocument.class, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="projectNo", type=String.class),
   @GeneratedProperty(name="projectName", type=String.class),
   @GeneratedProperty(name="applyArea", type=String.class,
      javaDoc="적용부"),
   @GeneratedProperty(name="sop", type=Timestamp.class,
      javaDoc="SOP"),
   @GeneratedProperty(name="investCost", type=String.class,
      javaDoc="투자비"),
   @GeneratedProperty(name="planTotal", type=String.class,
      javaDoc="기획총대수"),
   @GeneratedProperty(name="planYear", type=String.class,
      javaDoc="기획 년대수"),
   @GeneratedProperty(name="expectSalesTotal", type=String.class,
      javaDoc="예상총매출"),
   @GeneratedProperty(name="expectSalesYear", type=String.class,
      javaDoc="예상년매출"),
   @GeneratedProperty(name="projectResult", type=String.class,
      javaDoc="프로젝트 결과",
      constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="failReason", type=String.class,
      javaDoc="실패사유",
      constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="startPlanDate", type=Timestamp.class,
      javaDoc="계획시작일"),
   @GeneratedProperty(name="endPlanDate", type=Timestamp.class,
      javaDoc="계획종료일"),
   @GeneratedProperty(name="startResultDate", type=Timestamp.class,
      javaDoc="실적 시작일"),
   @GeneratedProperty(name="endResultDate", type=Timestamp.class,
      javaDoc="실적 종료일"),
   @GeneratedProperty(name="csYn", type=String.class,
      javaDoc="CS회의여부"),
   @GeneratedProperty(name="bigo", type=String.class,
      javaDoc="비고",
      constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="webEditor", type=Object.class,
      columnProperties=@ColumnProperties(columnType=ColumnType.BLOB)),
   @GeneratedProperty(name="webEditorText", type=Object.class,
      javaDoc="영업목표",
      columnProperties=@ColumnProperties(columnType=ColumnType.BLOB)),
   @GeneratedProperty(name="approveDate", type=Timestamp.class),
   @GeneratedProperty(name="centerCode", type=String.class,javaDoc="센터부서코드"),
   @GeneratedProperty(name="workShopYN", type=String.class,javaDoc="워크샵 여부")
   },
   foreignKeys={
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="devType", type=e3ps.common.code.NumberCode.class),
      myRole=@MyRole(name="theKETSalesProject", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="nationType", type=e3ps.common.code.NumberCode.class),
      myRole=@MyRole(name="theKETSalesProject", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="obtainCompanyType", type=e3ps.common.code.NumberCode.class),
      myRole=@MyRole(name="theKETSalesProject", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="failType", type=e3ps.common.code.NumberCode.class),
      myRole=@MyRole(name="theKETSalesProject", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="proejctState", type=e3ps.common.code.NumberCode.class,
         constraints=@PropertyConstraints(required=true)),
      myRole=@MyRole(name="theKETSalesProject", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="rankType", type=e3ps.common.code.NumberCode.class,
         constraints=@PropertyConstraints(required=true)),
      myRole=@MyRole(name="theKETSalesProject", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="oemType", type=e3ps.project.outputtype.OEMProjectType.class,
         constraints=@PropertyConstraints(required=true)),
      myRole=@MyRole(name="theKETSalesProject", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="competitor", type=e3ps.common.code.NumberCode.class),
      myRole=@MyRole(name="theKETSalesProject", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="salesState", type=e3ps.common.code.NumberCode.class),
      myRole=@MyRole(name="theKETSalesProject", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="afterSalesState", type=e3ps.common.code.NumberCode.class),
      myRole=@MyRole(name="theKETSalesProject", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="devRequest", type=e3ps.dms.entity.KETDevelopmentRequest.class),
      myRole=@MyRole(name="theKETSalesProject", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="salesTeam", type=e3ps.groupware.company.Department.class,
         constraints=@PropertyConstraints(required=true)),
      myRole=@MyRole(name="theKETSalesProject", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="mainCategoryType", type=e3ps.common.code.NumberCode.class),
      myRole=@MyRole(name="theKETSalesProject", cardinality=Cardinality.ONE))
   })
public class KETSalesProject extends _KETSalesProject {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    KETSalesProject
    * @exception wt.util.WTException
    **/
   public static KETSalesProject newKETSalesProject()
            throws WTException {

      KETSalesProject instance = new KETSalesProject();
      instance.initialize();
      return instance;
   }

}
