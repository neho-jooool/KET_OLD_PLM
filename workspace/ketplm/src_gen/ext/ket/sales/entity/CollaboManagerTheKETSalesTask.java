package ext.ket.sales.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETSalesTask"),
   roleA=@GeneratedRole(
      name="collaboManager",
      type=wt.org.WTUser.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theKETSalesTask",
      type=ext.ket.sales.entity.KETSalesTask.class,
      cardinality=Cardinality.ONE
   )
)

public class CollaboManagerTheKETSalesTask extends _CollaboManagerTheKETSalesTask implements Externalizable {
   static final long serialVersionUID = 1;
   public static CollaboManagerTheKETSalesTask newCollaboManagerTheKETSalesTask( wt.org.WTUser collaboManager, ext.ket.sales.entity.KETSalesTask theKETSalesTask ) throws wt.util.WTException {
      CollaboManagerTheKETSalesTask CollaboManagerTheKETSalesTask_instance = new CollaboManagerTheKETSalesTask();
      CollaboManagerTheKETSalesTask_instance.initialize(collaboManager, theKETSalesTask);
      return CollaboManagerTheKETSalesTask_instance;
   }
}
