package ext.ket.cost.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="CostInterfaceHistory"),
   roleA=@GeneratedRole(
      name="costPart",
      type=ext.ket.cost.entity.CostPart.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theCostInterfaceHistory",
      type=ext.ket.cost.entity.CostInterfaceHistory.class,
      cardinality=Cardinality.ONE
   )
)

public class CostPartTheCostInterfaceHistory extends _CostPartTheCostInterfaceHistory implements Externalizable {
   static final long serialVersionUID = 1;
   public static CostPartTheCostInterfaceHistory newCostPartTheCostInterfaceHistory( ext.ket.cost.entity.CostPart costPart, ext.ket.cost.entity.CostInterfaceHistory theCostInterfaceHistory ) throws wt.util.WTException {
      CostPartTheCostInterfaceHistory CostPartTheCostInterfaceHistory_instance = new CostPartTheCostInterfaceHistory();
      CostPartTheCostInterfaceHistory_instance.initialize(costPart, theCostInterfaceHistory);
      return CostPartTheCostInterfaceHistory_instance;
   }
}
