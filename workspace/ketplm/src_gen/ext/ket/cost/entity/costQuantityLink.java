package ext.ket.cost.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="CostQuantity"),
   roleA=@GeneratedRole(
      name="quantityMaster",
      type=ext.ket.cost.entity.ProductMaster.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="costQuantity",
      type=ext.ket.cost.entity.CostQuantity.class
   )
)

public class costQuantityLink extends _costQuantityLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static costQuantityLink newcostQuantityLink( ext.ket.cost.entity.ProductMaster quantityMaster, ext.ket.cost.entity.CostQuantity costQuantity ) throws wt.util.WTException {
      costQuantityLink costQuantityLink_instance = new costQuantityLink();
      costQuantityLink_instance.initialize(quantityMaster, costQuantity);
      return costQuantityLink_instance;
   }
}
