package ext.ket.cost.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="CostReduceCode"),
   roleA=@GeneratedRole(
      name="factory",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theCostReduceCode",
      type=ext.ket.cost.entity.CostReduceCode.class,
      cardinality=Cardinality.ONE
   )
)

public class FactoryTheCostReduceCode extends _FactoryTheCostReduceCode implements Externalizable {
   static final long serialVersionUID = 1;
   public static FactoryTheCostReduceCode newFactoryTheCostReduceCode( e3ps.common.code.NumberCode factory, ext.ket.cost.entity.CostReduceCode theCostReduceCode ) throws wt.util.WTException {
      FactoryTheCostReduceCode FactoryTheCostReduceCode_instance = new FactoryTheCostReduceCode();
      FactoryTheCostReduceCode_instance.initialize(factory, theCostReduceCode);
      return FactoryTheCostReduceCode_instance;
   }
}
