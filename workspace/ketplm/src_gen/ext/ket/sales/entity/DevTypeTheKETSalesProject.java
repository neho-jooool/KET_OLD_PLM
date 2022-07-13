package ext.ket.sales.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETSalesProject"),
   roleA=@GeneratedRole(
      name="devType",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="theKETSalesProject",
      type=ext.ket.sales.entity.KETSalesProject.class,
      cardinality=Cardinality.ONE
   )
)

public class DevTypeTheKETSalesProject extends _DevTypeTheKETSalesProject implements Externalizable {
   static final long serialVersionUID = 1;
   public static DevTypeTheKETSalesProject newDevTypeTheKETSalesProject( e3ps.common.code.NumberCode devType, ext.ket.sales.entity.KETSalesProject theKETSalesProject ) throws wt.util.WTException {
      DevTypeTheKETSalesProject DevTypeTheKETSalesProject_instance = new DevTypeTheKETSalesProject();
      DevTypeTheKETSalesProject_instance.initialize(devType, theKETSalesProject);
      return DevTypeTheKETSalesProject_instance;
   }
}
