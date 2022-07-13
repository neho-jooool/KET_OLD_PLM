package ext.ket.sales.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETSalesProject"),
   roleA=@GeneratedRole(
      name="salesTeam",
      type=e3ps.groupware.company.Department.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theKETSalesProject",
      type=ext.ket.sales.entity.KETSalesProject.class,
      cardinality=Cardinality.ONE
   )
)

public class SalesTeamTheKETSalesProject extends _SalesTeamTheKETSalesProject implements Externalizable {
   static final long serialVersionUID = 1;
   public static SalesTeamTheKETSalesProject newSalesTeamTheKETSalesProject( e3ps.groupware.company.Department salesTeam, ext.ket.sales.entity.KETSalesProject theKETSalesProject ) throws wt.util.WTException {
      SalesTeamTheKETSalesProject SalesTeamTheKETSalesProject_instance = new SalesTeamTheKETSalesProject();
      SalesTeamTheKETSalesProject_instance.initialize(salesTeam, theKETSalesProject);
      return SalesTeamTheKETSalesProject_instance;
   }
}
