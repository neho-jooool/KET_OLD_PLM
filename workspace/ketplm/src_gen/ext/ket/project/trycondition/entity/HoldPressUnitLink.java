package ext.ket.project.trycondition.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETTryMold"),
   roleA=@GeneratedRole(
      name="packingPressUnit",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theKETTryMold",
      type=ext.ket.project.trycondition.entity.KETTryMold.class,
      cardinality=Cardinality.ONE
   )
)

public class HoldPressUnitLink extends _HoldPressUnitLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static HoldPressUnitLink newHoldPressUnitLink( e3ps.common.code.NumberCode packingPressUnit, ext.ket.project.trycondition.entity.KETTryMold theKETTryMold ) throws wt.util.WTException {
      HoldPressUnitLink HoldPressUnitLink_instance = new HoldPressUnitLink();
      HoldPressUnitLink_instance.initialize(packingPressUnit, theKETTryMold);
      return HoldPressUnitLink_instance;
   }
}
