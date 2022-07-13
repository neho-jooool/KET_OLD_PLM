package ext.ket.sales.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETSalesTask"),
   roleA=@GeneratedRole(
      name="collaboTeam",
      type=e3ps.groupware.company.Department.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theKETSalesTask",
      type=ext.ket.sales.entity.KETSalesTask.class,
      cardinality=Cardinality.ONE
   )
)

public class CollaboTeamTheKETSalesTask extends _CollaboTeamTheKETSalesTask implements Externalizable {
   static final long serialVersionUID = 1;
   public static CollaboTeamTheKETSalesTask newCollaboTeamTheKETSalesTask( e3ps.groupware.company.Department collaboTeam, ext.ket.sales.entity.KETSalesTask theKETSalesTask ) throws wt.util.WTException {
      CollaboTeamTheKETSalesTask CollaboTeamTheKETSalesTask_instance = new CollaboTeamTheKETSalesTask();
      CollaboTeamTheKETSalesTask_instance.initialize(collaboTeam, theKETSalesTask);
      return CollaboTeamTheKETSalesTask_instance;
   }
}
