package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="WorkProject"),
   roleA=@GeneratedRole(
      name="devDept",
      type=e3ps.groupware.company.Department.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theWorkProject",
      type=e3ps.project.WorkProject.class,
      cardinality=Cardinality.ONE
   )
)

public class DevDeptTheWorkProject extends _DevDeptTheWorkProject implements Externalizable {
   static final long serialVersionUID = 1;
   public static DevDeptTheWorkProject newDevDeptTheWorkProject( e3ps.groupware.company.Department devDept, e3ps.project.WorkProject theWorkProject ) throws wt.util.WTException {
      DevDeptTheWorkProject DevDeptTheWorkProject_instance = new DevDeptTheWorkProject();
      DevDeptTheWorkProject_instance.initialize(devDept, theWorkProject);
      return DevDeptTheWorkProject_instance;
   }
}
