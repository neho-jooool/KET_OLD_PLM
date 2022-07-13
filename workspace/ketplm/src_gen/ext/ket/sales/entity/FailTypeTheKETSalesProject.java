package ext.ket.sales.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETSalesProject"),
   roleA=@GeneratedRole(
      name="failType",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="theKETSalesProject",
      type=ext.ket.sales.entity.KETSalesProject.class,
      cardinality=Cardinality.ONE
   )
)

public class FailTypeTheKETSalesProject extends _FailTypeTheKETSalesProject implements Externalizable {
   static final long serialVersionUID = 1;
   public static FailTypeTheKETSalesProject newFailTypeTheKETSalesProject( e3ps.common.code.NumberCode failType, ext.ket.sales.entity.KETSalesProject theKETSalesProject ) throws wt.util.WTException {
      FailTypeTheKETSalesProject FailTypeTheKETSalesProject_instance = new FailTypeTheKETSalesProject();
      FailTypeTheKETSalesProject_instance.initialize(failType, theKETSalesProject);
      return FailTypeTheKETSalesProject_instance;
   }
}
