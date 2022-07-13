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
import e3ps.project.TemplateProject;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
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
 * Use the <code>newProjectViewDepartmentLink</code> static factory method(s),
 * not the <code>ProjectViewDepartmentLink</code> constructor, to construct
 * instances of this class.  Instances must be constructed using the static
 * factory(s), in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsBinaryLink(superClass=ObjectToObjectLink.class, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   roleA=@GeneratedRole(name="project", type=TemplateProject.class,
      cardinality=Cardinality.ONE),
   roleB=@GeneratedRole(name="department", type=Department.class),
   tableProperties=@TableProperties(tableName="ProjectViewDepartmentLink")
)
public class ProjectViewDepartmentLink extends _ProjectViewDepartmentLink {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @param     project
    * @param     department
    * @return    ProjectViewDepartmentLink
    * @exception wt.util.WTException
    **/
   public static ProjectViewDepartmentLink newProjectViewDepartmentLink( TemplateProject project, Department department )
            throws WTException {

      ProjectViewDepartmentLink instance = new ProjectViewDepartmentLink();
      instance.initialize( project, department );
      return instance;
   }

}
