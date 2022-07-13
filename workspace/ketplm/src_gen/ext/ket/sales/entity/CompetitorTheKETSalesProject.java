package ext.ket.sales.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETSalesProject"),
   roleA=@GeneratedRole(
      name="competitor",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="theKETSalesProject",
      type=ext.ket.sales.entity.KETSalesProject.class,
      cardinality=Cardinality.ONE
   )
)

public class CompetitorTheKETSalesProject extends _CompetitorTheKETSalesProject implements Externalizable {
   static final long serialVersionUID = 1;
   public static CompetitorTheKETSalesProject newCompetitorTheKETSalesProject( e3ps.common.code.NumberCode competitor, ext.ket.sales.entity.KETSalesProject theKETSalesProject ) throws wt.util.WTException {
      CompetitorTheKETSalesProject CompetitorTheKETSalesProject_instance = new CompetitorTheKETSalesProject();
      CompetitorTheKETSalesProject_instance.initialize(competitor, theKETSalesProject);
      return CompetitorTheKETSalesProject_instance;
   }
}
