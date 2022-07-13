package ext.ket.sales.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETSalesProject"),
   roleA=@GeneratedRole(
      name="salesState",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="theKETSalesProject",
      type=ext.ket.sales.entity.KETSalesProject.class,
      cardinality=Cardinality.ONE
   )
)

public class SalesStateTheKETSalesProject extends _SalesStateTheKETSalesProject implements Externalizable {
   static final long serialVersionUID = 1;
   public static SalesStateTheKETSalesProject newSalesStateTheKETSalesProject( e3ps.common.code.NumberCode salesState, ext.ket.sales.entity.KETSalesProject theKETSalesProject ) throws wt.util.WTException {
      SalesStateTheKETSalesProject SalesStateTheKETSalesProject_instance = new SalesStateTheKETSalesProject();
      SalesStateTheKETSalesProject_instance.initialize(salesState, theKETSalesProject);
      return SalesStateTheKETSalesProject_instance;
   }
}
