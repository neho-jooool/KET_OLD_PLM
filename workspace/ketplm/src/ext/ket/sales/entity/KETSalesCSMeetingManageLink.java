/* bcwti≪≫≪≫Copyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.≪≫≪≫This software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.≪≫≪≫ecwti
 */

package ext.ket.sales.entity;

import ext.ket.sales.entity.KETSalesCSMeetingApproval;
import ext.ket.sales.entity.KETSalesCSMeetingManage;
import ext.ket.sales.entity.KETSalesProject;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import wt.fc.ObjectReference;
import wt.fc.ObjectToObjectLink;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;

/**
 *
 * <p>
 * Use the <code>newKETSalesCSMeetingManageLink</code> static factory method(s),
 * not the <code>KETSalesCSMeetingManageLink</code> constructor, to construct
 * instances of this class.  Instances must be constructed using the static
 * factory(s), in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsBinaryLink(superClass=ObjectToObjectLink.class, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="sortNo", type=String.class),
   @GeneratedProperty(name="csState", type=String.class),
   @GeneratedProperty(name="deptSortNo", type=String.class,
      javaDoc="부서별 sortNo"),
   @GeneratedProperty(name="csFile", type=String.class,
      javaDoc="applicationData"),
   @GeneratedProperty(name="FileSortOption", type=String.class,
      javaDoc="front / back")
   },
   foreignKeys={
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="csMeetingApprove", type=ext.ket.sales.entity.KETSalesCSMeetingApproval.class),
      myRole=@MyRole(name="theKETSalesCSMeetingManageLink", cardinality=Cardinality.ONE))
   },
   roleA=@GeneratedRole(name="csProject", type=KETSalesProject.class,
      cardinality=Cardinality.ZERO_TO_ONE),
   roleB=@GeneratedRole(name="csDegree", type=KETSalesCSMeetingManage.class),
   tableProperties=@TableProperties(tableName="KETSalesCSMeetingManageLink")
)
public class KETSalesCSMeetingManageLink extends _KETSalesCSMeetingManageLink {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @param     csProject
    * @param     csDegree
    * @return    KETSalesCSMeetingManageLink
    * @exception wt.util.WTException
    **/
   public static KETSalesCSMeetingManageLink newKETSalesCSMeetingManageLink( KETSalesProject csProject, KETSalesCSMeetingManage csDegree )
            throws WTException {

      KETSalesCSMeetingManageLink instance = new KETSalesCSMeetingManageLink();
      instance.initialize( csProject, csDegree );
      return instance;
   }

}
