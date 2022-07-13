package ext.ket.cost.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="CostPart"),
   roleA=@GeneratedRole(
      name="master",
      type=ext.ket.cost.entity.ProductMaster.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="theCostPart",
      type=ext.ket.cost.entity.CostPart.class,
      cardinality=Cardinality.ONE
   )
)

public class MasterTheCostPart extends _MasterTheCostPart implements Externalizable {
   static final long serialVersionUID = 1;
   public static MasterTheCostPart newMasterTheCostPart( ext.ket.cost.entity.ProductMaster master, ext.ket.cost.entity.CostPart theCostPart ) throws wt.util.WTException {
      MasterTheCostPart MasterTheCostPart_instance = new MasterTheCostPart();
      MasterTheCostPart_instance.initialize(master, theCostPart);
      return MasterTheCostPart_instance;
   }
}
