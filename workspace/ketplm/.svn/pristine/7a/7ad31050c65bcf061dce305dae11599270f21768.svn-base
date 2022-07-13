/* bcwti≪≫≪≫Copyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.≪≫≪≫This software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.≪≫≪≫ecwti
 */

package ext.ket.arm.entity;

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
import wt.doc.WTDocument;
import wt.fc.ObjectReference;
import wt.org.WTUser;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;

/**
 *
 * <p>
 * Use the <code>newKETAnalysisRequestMaster</code> static factory method(s),
 * not the <code>KETAnalysisRequestMaster</code> constructor, to construct
 * instances of this class.  Instances must be constructed using the static
 * factory(s), in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(superClass=WTDocument.class, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="analysisRequestNo", type=String.class),
   @GeneratedProperty(name="projectNo", type=String.class),
   @GeneratedProperty(name="analysisTitle", type=String.class),
   @GeneratedProperty(name="customerChargeName", type=String.class),
   @GeneratedProperty(name="partNo", type=String.class),
   @GeneratedProperty(name="startDate", type=Timestamp.class,
      javaDoc="해석의뢰시작일"),
   @GeneratedProperty(name="endDate", type=Timestamp.class,
      javaDoc="해석의뢰완료일"),
   @GeneratedProperty(name="etcComment", type=String.class),
   @GeneratedProperty(name="analysisObjectComment", type=String.class),
   @GeneratedProperty(name="analysisTargetComment", type=String.class)
   },
   foreignKeys={
   @GeneratedForeignKey(name="KETClientUser", myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="ketClientUser", type=wt.org.WTUser.class,
         constraints=@PropertyConstraints(required=true)),
      myRole=@MyRole(name="theKETAnalysisRequestMaster", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(name="KETClientDepartment", myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="ketClientDepartment", type=e3ps.groupware.company.Department.class,
         constraints=@PropertyConstraints(required=true)),
      myRole=@MyRole(name="theKETAnalysisRequestMaster", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(name="KETChargeUser", myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="ketChargeUser", type=wt.org.WTUser.class,
         constraints=@PropertyConstraints(required=true)),
      myRole=@MyRole(name="theKETAnalysisRequestMaster", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(name="KETChargeDepartment", myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="ketChargeDepartment", type=e3ps.groupware.company.Department.class,
         constraints=@PropertyConstraints(required=true)),
      myRole=@MyRole(name="theKETAnalysisRequestMaster", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(name="KETAnalysisCarTypeLink", myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="carType", type=e3ps.project.outputtype.OEMProjectType.class),
      myRole=@MyRole(name="master", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(name="KETAnalysisProcessLink", myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="process", type=e3ps.common.code.NumberCode.class),
      myRole=@MyRole(name="master", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(name="KETAnalysisUsageLink", myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="usage", type=e3ps.common.code.NumberCode.class),
      myRole=@MyRole(name="master", cardinality=Cardinality.ONE))
   })
public class KETAnalysisRequestMaster extends _KETAnalysisRequestMaster {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    KETAnalysisRequestMaster
    * @exception wt.util.WTException
    **/
   public static KETAnalysisRequestMaster newKETAnalysisRequestMaster()
            throws WTException {

      KETAnalysisRequestMaster instance = new KETAnalysisRequestMaster();
      instance.initialize();
      return instance;
   }

}
