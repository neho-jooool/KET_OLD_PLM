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

package e3ps.project;

import e3ps.groupware.company.Department;
import e3ps.project.E3PSProject;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import java.sql.Timestamp;
import wt.fc.ObjectReference;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;
import e3ps.groupware.company.Department;  // Preserved unmodeled dependency
import wt.fc.ObjectReference;  // Preserved unmodeled dependency
import wt.org.WTPrincipalReference;  // Preserved unmodeled dependency
import java.lang.Object;  // Preserved unmodeled dependency
import java.sql.SQLException;  // Preserved unmodeled dependency
import wt.fc.ObjectReference;  // Preserved unmodeled dependency
import wt.pds.PersistentRetrieveIfc;  // Preserved unmodeled dependency
import wt.pds.PersistentStoreIfc;  // Preserved unmodeled dependency
import wt.pom.DatastoreException;  // Preserved unmodeled dependency
import wt.util.WTException;  // Preserved unmodeled dependency

/**
 *
 * <p>
 * Use the <code>newReviewProject</code> static factory method(s), not the
 * <code>ReviewProject</code> constructor, to construct instances of this
 * class.  Instances must be constructed using the static factory(s), in
 * order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(superClass=E3PSProject.class,
   properties={
   @GeneratedProperty(name="requestDate", type=Timestamp.class,
      javaDoc="의뢰서 요청일"),
   @GeneratedProperty(name="receiveDate", type=Timestamp.class,
      javaDoc="의뢰서 접수일"),
   @GeneratedProperty(name="proposalDate", type=Timestamp.class,
      javaDoc="제안서 제출일"),
   @GeneratedProperty(name="estimateDate", type=Timestamp.class,
      javaDoc="견적서 제출일"),
   @GeneratedProperty(name="sop", type=Timestamp.class,
      javaDoc="SOP 일정"),
   @GeneratedProperty(name="attr3", type=String.class,
      javaDoc="개발자"),
   @GeneratedProperty(name="attr2", type=String.class,
      javaDoc="영업담당자"),
   @GeneratedProperty(name="attr1", type=String.class,
      javaDoc="기획담당자"),
   @GeneratedProperty(name="attr4", type=String.class),
   @GeneratedProperty(name="reviewResult", type=String.class,
   javaDoc="검토결과")
   },
   foreignKeys={
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="devDept", type=e3ps.groupware.company.Department.class,
         constraints=@PropertyConstraints(required=true)),
      myRole=@MyRole(name="theReviewProject", cardinality=Cardinality.ONE))
   })
public class ReviewProject extends _ReviewProject {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    ReviewProject
    * @exception wt.util.WTException
    **/
   public static ReviewProject newReviewProject()
            throws WTException {

      ReviewProject instance = new ReviewProject();
      instance.initialize();
      return instance;
   }

}
