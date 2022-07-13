package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="ReviewProject"),
   roleA=@GeneratedRole(
      name="devDept",
      type=e3ps.groupware.company.Department.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theReviewProject",
      type=e3ps.project.ReviewProject.class,
      cardinality=Cardinality.ONE
   )
)

public class DevDeptTheReviewProject extends _DevDeptTheReviewProject implements Externalizable {
   static final long serialVersionUID = 1;
   public static DevDeptTheReviewProject newDevDeptTheReviewProject( e3ps.groupware.company.Department devDept, e3ps.project.ReviewProject theReviewProject ) throws wt.util.WTException {
      DevDeptTheReviewProject DevDeptTheReviewProject_instance = new DevDeptTheReviewProject();
      DevDeptTheReviewProject_instance.initialize(devDept, theReviewProject);
      return DevDeptTheReviewProject_instance;
   }
}
