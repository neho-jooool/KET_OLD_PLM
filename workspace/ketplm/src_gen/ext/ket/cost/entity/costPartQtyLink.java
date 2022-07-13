package ext.ket.cost.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="CostQuantity"),
   roleA=@GeneratedRole(
      name="costPart",
      type=ext.ket.cost.entity.CostPart.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="costQuantity",
      type=ext.ket.cost.entity.CostQuantity.class
   )
)

public class costPartQtyLink extends _costPartQtyLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static costPartQtyLink newcostPartQtyLink( ext.ket.cost.entity.CostPart costPart, ext.ket.cost.entity.CostQuantity costQuantity ) throws wt.util.WTException {
      costPartQtyLink costPartQtyLink_instance = new costPartQtyLink();
      costPartQtyLink_instance.initialize(costPart, costQuantity);
      return costPartQtyLink_instance;
   }
}
