package ext.ket.project.gate.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETGateCheckSheet"),
   roleA=@GeneratedRole(
      name="targetType",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="check",
      type=ext.ket.project.gate.entity.GateCheckSheet.class,
      cardinality=Cardinality.ONE
   )
)

public class GateCheckTargetTypeLink extends _GateCheckTargetTypeLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static GateCheckTargetTypeLink newGateCheckTargetTypeLink( e3ps.common.code.NumberCode targetType, ext.ket.project.gate.entity.GateCheckSheet check ) throws wt.util.WTException {
      GateCheckTargetTypeLink GateCheckTargetTypeLink_instance = new GateCheckTargetTypeLink();
      GateCheckTargetTypeLink_instance.initialize(targetType, check);
      return GateCheckTargetTypeLink_instance;
   }
}
