package ext.ket.project.purchase.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETPurchaseProject"),
   roleA=@GeneratedRole(
      name="department",
      type=e3ps.groupware.company.Department.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theKETPurchaseProject",
      type=ext.ket.project.purchase.entity.KETPurchaseProject.class,
      cardinality=Cardinality.ONE
   )
)

public class DepartmentTheKETPurchaseProject extends _DepartmentTheKETPurchaseProject implements Externalizable {
   static final long serialVersionUID = 1;
   public static DepartmentTheKETPurchaseProject newDepartmentTheKETPurchaseProject( e3ps.groupware.company.Department department, ext.ket.project.purchase.entity.KETPurchaseProject theKETPurchaseProject ) throws wt.util.WTException {
      DepartmentTheKETPurchaseProject DepartmentTheKETPurchaseProject_instance = new DepartmentTheKETPurchaseProject();
      DepartmentTheKETPurchaseProject_instance.initialize(department, theKETPurchaseProject);
      return DepartmentTheKETPurchaseProject_instance;
   }
}
