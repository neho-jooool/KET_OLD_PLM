package ext.ket.cost.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="CostPacking"),
   roleA=@GeneratedRole(
      name="partList",
      type=ext.ket.cost.entity.PartList.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="costPacking",
      type=ext.ket.cost.entity.CostPacking.class
   )
)

public class costPackingListLink extends _costPackingListLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static costPackingListLink newcostPackingListLink( ext.ket.cost.entity.PartList partList, ext.ket.cost.entity.CostPacking costPacking ) throws wt.util.WTException {
      costPackingListLink costPackingListLink_instance = new costPackingListLink();
      costPackingListLink_instance.initialize(partList, costPacking);
      return costPackingListLink_instance;
   }
}
