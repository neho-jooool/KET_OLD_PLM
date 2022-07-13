package ext.ket.cost.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="CostPartTypeCodeMaster"),
   roleA=@GeneratedRole(
      name="manufactureCode",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theCostPartTypeCodeMaster",
      type=ext.ket.cost.entity.CostPartTypeCodeMaster.class,
      cardinality=Cardinality.ONE
   )
)

public class ManufactureCodeTheCostPartTypeCodeMaster extends _ManufactureCodeTheCostPartTypeCodeMaster implements Externalizable {
   static final long serialVersionUID = 1;
   public static ManufactureCodeTheCostPartTypeCodeMaster newManufactureCodeTheCostPartTypeCodeMaster( e3ps.common.code.NumberCode manufactureCode, ext.ket.cost.entity.CostPartTypeCodeMaster theCostPartTypeCodeMaster ) throws wt.util.WTException {
      ManufactureCodeTheCostPartTypeCodeMaster ManufactureCodeTheCostPartTypeCodeMaster_instance = new ManufactureCodeTheCostPartTypeCodeMaster();
      ManufactureCodeTheCostPartTypeCodeMaster_instance.initialize(manufactureCode, theCostPartTypeCodeMaster);
      return ManufactureCodeTheCostPartTypeCodeMaster_instance;
   }
}
