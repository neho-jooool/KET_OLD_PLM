package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="TemplateTask"),
   roleA=@GeneratedRole(
      name="department",
      type=e3ps.groupware.company.Department.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="theTemplateTask",
      type=e3ps.project.TemplateTask.class,
      cardinality=Cardinality.ONE
   )
)

public class DepartmentTheTemplateTask extends _DepartmentTheTemplateTask implements Externalizable {
   static final long serialVersionUID = 1;
   public static DepartmentTheTemplateTask newDepartmentTheTemplateTask( e3ps.groupware.company.Department department, e3ps.project.TemplateTask theTemplateTask ) throws wt.util.WTException {
      DepartmentTheTemplateTask DepartmentTheTemplateTask_instance = new DepartmentTheTemplateTask();
      DepartmentTheTemplateTask_instance.initialize(department, theTemplateTask);
      return DepartmentTheTemplateTask_instance;
   }
}
