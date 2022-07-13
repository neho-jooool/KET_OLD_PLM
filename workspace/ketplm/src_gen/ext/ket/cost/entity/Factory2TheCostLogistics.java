package ext.ket.cost.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="CostLogistics"),
   roleA=@GeneratedRole(
      name="factory2",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="theCostLogistics",
      type=ext.ket.cost.entity.CostLogistics.class,
      cardinality=Cardinality.ZERO_TO_ONE
   )
)

public class Factory2TheCostLogistics extends _Factory2TheCostLogistics implements Externalizable {
   static final long serialVersionUID = 1;
   public static Factory2TheCostLogistics newFactory2TheCostLogistics( e3ps.common.code.NumberCode factory2, ext.ket.cost.entity.CostLogistics theCostLogistics ) throws wt.util.WTException {
      Factory2TheCostLogistics Factory2TheCostLogistics_instance = new Factory2TheCostLogistics();
      Factory2TheCostLogistics_instance.initialize(factory2, theCostLogistics);
      return Factory2TheCostLogistics_instance;
   }
}
