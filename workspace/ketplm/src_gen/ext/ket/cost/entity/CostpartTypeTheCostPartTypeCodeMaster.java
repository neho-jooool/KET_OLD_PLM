package ext.ket.cost.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="CostPartTypeCodeMaster"),
   roleA=@GeneratedRole(
      name="costpartType",
      type=ext.ket.cost.entity.CostPartType.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theCostPartTypeCodeMaster",
      type=ext.ket.cost.entity.CostPartTypeCodeMaster.class,
      cardinality=Cardinality.ONE
   )
)

public class CostpartTypeTheCostPartTypeCodeMaster extends _CostpartTypeTheCostPartTypeCodeMaster implements Externalizable {
   static final long serialVersionUID = 1;
   public static CostpartTypeTheCostPartTypeCodeMaster newCostpartTypeTheCostPartTypeCodeMaster( ext.ket.cost.entity.CostPartType costpartType, ext.ket.cost.entity.CostPartTypeCodeMaster theCostPartTypeCodeMaster ) throws wt.util.WTException {
      CostpartTypeTheCostPartTypeCodeMaster CostpartTypeTheCostPartTypeCodeMaster_instance = new CostpartTypeTheCostPartTypeCodeMaster();
      CostpartTypeTheCostPartTypeCodeMaster_instance.initialize(costpartType, theCostPartTypeCodeMaster);
      return CostpartTypeTheCostPartTypeCodeMaster_instance;
   }
}
