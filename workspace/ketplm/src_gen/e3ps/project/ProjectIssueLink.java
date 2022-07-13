package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="ProjectIssue"),
   roleA=@GeneratedRole(
      name="project",
      type=e3ps.project.E3PSProject.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="issue",
      type=e3ps.project.ProjectIssue.class
   )
)

public class ProjectIssueLink extends _ProjectIssueLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static ProjectIssueLink newProjectIssueLink( e3ps.project.E3PSProject project, e3ps.project.ProjectIssue issue ) throws wt.util.WTException {
      ProjectIssueLink ProjectIssueLink_instance = new ProjectIssueLink();
      ProjectIssueLink_instance.initialize(project, issue);
      return ProjectIssueLink_instance;
   }
}
