package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="ProjectDeptRole"),
   roleA=@GeneratedRole(
      name="department",
      type=e3ps.groupware.company.Department.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theProjectDeptRole",
      type=e3ps.project.ProjectDeptRole.class,
      cardinality=Cardinality.ONE
   )
)

public class DepartmentTheProjectDeptRole extends _DepartmentTheProjectDeptRole implements Externalizable {
   static final long serialVersionUID = 1;
   public static DepartmentTheProjectDeptRole newDepartmentTheProjectDeptRole( e3ps.groupware.company.Department department, e3ps.project.ProjectDeptRole theProjectDeptRole ) throws wt.util.WTException {
      DepartmentTheProjectDeptRole DepartmentTheProjectDeptRole_instance = new DepartmentTheProjectDeptRole();
      DepartmentTheProjectDeptRole_instance.initialize(department, theProjectDeptRole);
      return DepartmentTheProjectDeptRole_instance;
   }
}
