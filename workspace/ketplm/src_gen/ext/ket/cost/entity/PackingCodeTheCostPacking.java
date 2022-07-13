package ext.ket.cost.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="CostPacking"),
   roleA=@GeneratedRole(
      name="packingCode",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theCostPacking",
      type=ext.ket.cost.entity.CostPacking.class,
      cardinality=Cardinality.ONE
   )
)

public class PackingCodeTheCostPacking extends _PackingCodeTheCostPacking implements Externalizable {
   static final long serialVersionUID = 1;
   public static PackingCodeTheCostPacking newPackingCodeTheCostPacking( e3ps.common.code.NumberCode packingCode, ext.ket.cost.entity.CostPacking theCostPacking ) throws wt.util.WTException {
      PackingCodeTheCostPacking PackingCodeTheCostPacking_instance = new PackingCodeTheCostPacking();
      PackingCodeTheCostPacking_instance.initialize(packingCode, theCostPacking);
      return PackingCodeTheCostPacking_instance;
   }
}
