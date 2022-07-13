package ext.ket.sales.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETSalesProject"),
   roleA=@GeneratedRole(
      name="rankType",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theKETSalesProject",
      type=ext.ket.sales.entity.KETSalesProject.class,
      cardinality=Cardinality.ONE
   )
)

public class RankTypeTheKETSalesProject extends _RankTypeTheKETSalesProject implements Externalizable {
   static final long serialVersionUID = 1;
   public static RankTypeTheKETSalesProject newRankTypeTheKETSalesProject( e3ps.common.code.NumberCode rankType, ext.ket.sales.entity.KETSalesProject theKETSalesProject ) throws wt.util.WTException {
      RankTypeTheKETSalesProject RankTypeTheKETSalesProject_instance = new RankTypeTheKETSalesProject();
      RankTypeTheKETSalesProject_instance.initialize(rankType, theKETSalesProject);
      return RankTypeTheKETSalesProject_instance;
   }
}
