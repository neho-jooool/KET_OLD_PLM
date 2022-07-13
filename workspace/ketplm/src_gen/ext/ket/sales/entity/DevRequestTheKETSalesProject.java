package ext.ket.sales.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETSalesProject"),
   roleA=@GeneratedRole(
      name="devRequest",
      type=e3ps.dms.entity.KETDevelopmentRequest.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="theKETSalesProject",
      type=ext.ket.sales.entity.KETSalesProject.class,
      cardinality=Cardinality.ONE
   )
)

public class DevRequestTheKETSalesProject extends _DevRequestTheKETSalesProject implements Externalizable {
   static final long serialVersionUID = 1;
   public static DevRequestTheKETSalesProject newDevRequestTheKETSalesProject( e3ps.dms.entity.KETDevelopmentRequest devRequest, ext.ket.sales.entity.KETSalesProject theKETSalesProject ) throws wt.util.WTException {
      DevRequestTheKETSalesProject DevRequestTheKETSalesProject_instance = new DevRequestTheKETSalesProject();
      DevRequestTheKETSalesProject_instance.initialize(devRequest, theKETSalesProject);
      return DevRequestTheKETSalesProject_instance;
   }
}
