package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="ProjectIssueAnswer"),
   roleA=@GeneratedRole(
      name="dependencyAnswer",
      type=e3ps.project.ProjectIssueAnswer.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theProjectIssueAnswer",
      type=e3ps.project.ProjectIssueAnswer.class,
      cardinality=Cardinality.ONE
   )
)

public class DependencyAnswerTheProjectIssueAnswer extends _DependencyAnswerTheProjectIssueAnswer implements Externalizable {
   static final long serialVersionUID = 1;
   public static DependencyAnswerTheProjectIssueAnswer newDependencyAnswerTheProjectIssueAnswer( e3ps.project.ProjectIssueAnswer dependencyAnswer, e3ps.project.ProjectIssueAnswer theProjectIssueAnswer ) throws wt.util.WTException {
      DependencyAnswerTheProjectIssueAnswer DependencyAnswerTheProjectIssueAnswer_instance = new DependencyAnswerTheProjectIssueAnswer();
      DependencyAnswerTheProjectIssueAnswer_instance.initialize(dependencyAnswer, theProjectIssueAnswer);
      return DependencyAnswerTheProjectIssueAnswer_instance;
   }
}
