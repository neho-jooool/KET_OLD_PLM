package ext.ket.cost.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="CostPacking"),
   roleA=@GeneratedRole(
      name="packingMaster",
      type=ext.ket.cost.entity.ProductMaster.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="costPacking",
      type=ext.ket.cost.entity.CostPacking.class
   )
)

public class costPackingLink extends _costPackingLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static costPackingLink newcostPackingLink( ext.ket.cost.entity.ProductMaster packingMaster, ext.ket.cost.entity.CostPacking costPacking ) throws wt.util.WTException {
      costPackingLink costPackingLink_instance = new costPackingLink();
      costPackingLink_instance.initialize(packingMaster, costPacking);
      return costPackingLink_instance;
   }
}
