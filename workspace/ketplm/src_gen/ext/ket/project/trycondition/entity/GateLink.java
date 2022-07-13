package ext.ket.project.trycondition.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETTryMold"),
   roleA=@GeneratedRole(
      name="gateType",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theKETTryMold",
      type=ext.ket.project.trycondition.entity.KETTryMold.class,
      cardinality=Cardinality.ONE
   )
)

public class GateLink extends _GateLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static GateLink newGateLink( e3ps.common.code.NumberCode gateType, ext.ket.project.trycondition.entity.KETTryMold theKETTryMold ) throws wt.util.WTException {
      GateLink GateLink_instance = new GateLink();
      GateLink_instance.initialize(gateType, theKETTryMold);
      return GateLink_instance;
   }
}
