package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="ReviewDevelop"),
   roleA=@GeneratedRole(
      name="project",
      type=e3ps.project.ReviewProject.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="review",
      type=e3ps.project.ReviewDevelop.class,
      cardinality=Cardinality.ONE
   )
)

public class ReviewDevelopProjectLink extends _ReviewDevelopProjectLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static ReviewDevelopProjectLink newReviewDevelopProjectLink( e3ps.project.ReviewProject project, e3ps.project.ReviewDevelop review ) throws wt.util.WTException {
      ReviewDevelopProjectLink ReviewDevelopProjectLink_instance = new ReviewDevelopProjectLink();
      ReviewDevelopProjectLink_instance.initialize(project, review);
      return ReviewDevelopProjectLink_instance;
   }
}
