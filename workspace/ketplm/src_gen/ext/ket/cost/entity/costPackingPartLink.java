package ext.ket.cost.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="CostPacking"),
   roleA=@GeneratedRole(
      name="costPart",
      type=ext.ket.cost.entity.CostPart.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="costPacking",
      type=ext.ket.cost.entity.CostPacking.class
   )
)

public class costPackingPartLink extends _costPackingPartLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static costPackingPartLink newcostPackingPartLink( ext.ket.cost.entity.CostPart costPart, ext.ket.cost.entity.CostPacking costPacking ) throws wt.util.WTException {
      costPackingPartLink costPackingPartLink_instance = new costPackingPartLink();
      costPackingPartLink_instance.initialize(costPart, costPacking);
      return costPackingPartLink_instance;
   }
}
