package ext.ket.cost.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="CostLogistics"),
   roleA=@GeneratedRole(
      name="factory1",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="theCostLogistics",
      type=ext.ket.cost.entity.CostLogistics.class,
      cardinality=Cardinality.ZERO_TO_ONE
   )
)

public class Factory1TheCostLogistics extends _Factory1TheCostLogistics implements Externalizable {
   static final long serialVersionUID = 1;
   public static Factory1TheCostLogistics newFactory1TheCostLogistics( e3ps.common.code.NumberCode factory1, ext.ket.cost.entity.CostLogistics theCostLogistics ) throws wt.util.WTException {
      Factory1TheCostLogistics Factory1TheCostLogistics_instance = new Factory1TheCostLogistics();
      Factory1TheCostLogistics_instance.initialize(factory1, theCostLogistics);
      return Factory1TheCostLogistics_instance;
   }
}
