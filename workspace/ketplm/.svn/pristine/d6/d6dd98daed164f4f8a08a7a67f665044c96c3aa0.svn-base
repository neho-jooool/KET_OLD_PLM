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
import wt.org.WTUser;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;
import e3ps.project.ProjectOutput;  // Preserved unmodeled dependency
import e3ps.project.ProjectOutput;  // Preserved unmodeled dependency
import e3ps.project.ProjectOutput;  // Preserved unmodeled dependency

/**
 *
 * <p>
 * Use the <code>newProjectMemberLink</code> static factory method(s), not
 * the <code>ProjectMemberLink</code> constructor, to construct instances
 * of this class.  Instances must be constructed using the static factory(s),
 * in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsBinaryLink(superClass=ObjectToObjectLink.class, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="pjtMemberType", type=int.class,
      javaDoc="0: PM≪≫1: PL≪≫2: Role Member≪≫3: Etc Member≪≫4: Only View"),
   @GeneratedProperty(name="pjtRole", type=String.class,
      javaDoc="Role Name≪≫≪≫기구(Team_Machine)≪≫영업(Team_Sale)≪≫제어(Team_Control)≪≫연구(Team_RND)≪≫QC(Team_QC)≪≫SW(Team_SW)≪≫구매(Team_Purchase)≪≫CS(Team_CS)≪≫조립(Team_Make)"),
   @GeneratedProperty(name="pjtHistory", type=int.class,
      javaDoc="프로젝트 이력≪≫≪≫0: 최초생성 시≪≫1~N: 갱신 시")
   },
   roleA=@GeneratedRole(name="project", type=TemplateProject.class,
      cardinality=Cardinality.ONE),
   roleB=@GeneratedRole(name="member", type=WTUser.class,
      cardinality=Cardinality.ONE_TO_MANY),
   tableProperties=@TableProperties(tableName="ProjectMemberLink")
)
public class ProjectMemberLink extends _ProjectMemberLink {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @param     project
    * @param     member
    * @return    ProjectMemberLink
    * @exception wt.util.WTException
    **/
   public static ProjectMemberLink newProjectMemberLink( TemplateProject project, WTUser member )
            throws WTException {

      ProjectMemberLink instance = new ProjectMemberLink();
      instance.initialize( project, member );
      return instance;
   }

}
