package ext.ket.cost.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="CostInterfaceChildHistory"),
   roleA=@GeneratedRole(
      name="costPart",
      type=ext.ket.cost.entity.CostPart.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theCostInterfaceChildHistory",
      type=ext.ket.cost.entity.CostInterfaceChildHistory.class,
      cardinality=Cardinality.ONE
   )
)

public class CostPartTheCostInterfaceChildHistory extends _CostPartTheCostInterfaceChildHistory implements Externalizable {
   static final long serialVersionUID = 1;
   public static CostPartTheCostInterfaceChildHistory newCostPartTheCostInterfaceChildHistory( ext.ket.cost.entity.CostPart costPart, ext.ket.cost.entity.CostInterfaceChildHistory theCostInterfaceChildHistory ) throws wt.util.WTException {
      CostPartTheCostInterfaceChildHistory CostPartTheCostInterfaceChildHistory_instance = new CostPartTheCostInterfaceChildHistory();
      CostPartTheCostInterfaceChildHistory_instance.initialize(costPart, theCostInterfaceChildHistory);
      return CostPartTheCostInterfaceChildHistory_instance;
   }
}
