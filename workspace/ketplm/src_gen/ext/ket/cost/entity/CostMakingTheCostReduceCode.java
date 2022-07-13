package ext.ket.cost.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="CostReduceCode"),
   roleA=@GeneratedRole(
      name="costMaking",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theCostReduceCode",
      type=ext.ket.cost.entity.CostReduceCode.class,
      cardinality=Cardinality.ONE
   )
)

public class CostMakingTheCostReduceCode extends _CostMakingTheCostReduceCode implements Externalizable {
   static final long serialVersionUID = 1;
   public static CostMakingTheCostReduceCode newCostMakingTheCostReduceCode( e3ps.common.code.NumberCode costMaking, ext.ket.cost.entity.CostReduceCode theCostReduceCode ) throws wt.util.WTException {
      CostMakingTheCostReduceCode CostMakingTheCostReduceCode_instance = new CostMakingTheCostReduceCode();
      CostMakingTheCostReduceCode_instance.initialize(costMaking, theCostReduceCode);
      return CostMakingTheCostReduceCode_instance;
   }
}
