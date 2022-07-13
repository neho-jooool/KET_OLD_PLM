package ext.ket.sales.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETSalesProject"),
   roleA=@GeneratedRole(
      name="afterSalesState",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="theKETSalesProject",
      type=ext.ket.sales.entity.KETSalesProject.class,
      cardinality=Cardinality.ONE
   )
)

public class AfterSalesStateTheKETSalesProject extends _AfterSalesStateTheKETSalesProject implements Externalizable {
   static final long serialVersionUID = 1;
   public static AfterSalesStateTheKETSalesProject newAfterSalesStateTheKETSalesProject( e3ps.common.code.NumberCode afterSalesState, ext.ket.sales.entity.KETSalesProject theKETSalesProject ) throws wt.util.WTException {
      AfterSalesStateTheKETSalesProject AfterSalesStateTheKETSalesProject_instance = new AfterSalesStateTheKETSalesProject();
      AfterSalesStateTheKETSalesProject_instance.initialize(afterSalesState, theKETSalesProject);
      return AfterSalesStateTheKETSalesProject_instance;
   }
}
