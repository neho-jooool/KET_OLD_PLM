package ext.ket.sales.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETSalesProject"),
   roleA=@GeneratedRole(
      name="proejctState",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theKETSalesProject",
      type=ext.ket.sales.entity.KETSalesProject.class,
      cardinality=Cardinality.ONE
   )
)

public class ProejctStateTheKETSalesProject extends _ProejctStateTheKETSalesProject implements Externalizable {
   static final long serialVersionUID = 1;
   public static ProejctStateTheKETSalesProject newProejctStateTheKETSalesProject( e3ps.common.code.NumberCode proejctState, ext.ket.sales.entity.KETSalesProject theKETSalesProject ) throws wt.util.WTException {
      ProejctStateTheKETSalesProject ProejctStateTheKETSalesProject_instance = new ProejctStateTheKETSalesProject();
      ProejctStateTheKETSalesProject_instance.initialize(proejctState, theKETSalesProject);
      return ProejctStateTheKETSalesProject_instance;
   }
}
