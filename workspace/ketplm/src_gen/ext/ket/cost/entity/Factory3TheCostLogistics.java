package ext.ket.cost.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="CostLogistics"),
   roleA=@GeneratedRole(
      name="factory3",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="theCostLogistics",
      type=ext.ket.cost.entity.CostLogistics.class,
      cardinality=Cardinality.ZERO_TO_ONE
   )
)

public class Factory3TheCostLogistics extends _Factory3TheCostLogistics implements Externalizable {
   static final long serialVersionUID = 1;
   public static Factory3TheCostLogistics newFactory3TheCostLogistics( e3ps.common.code.NumberCode factory3, ext.ket.cost.entity.CostLogistics theCostLogistics ) throws wt.util.WTException {
      Factory3TheCostLogistics Factory3TheCostLogistics_instance = new Factory3TheCostLogistics();
      Factory3TheCostLogistics_instance.initialize(factory3, theCostLogistics);
      return Factory3TheCostLogistics_instance;
   }
}
