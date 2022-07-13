package ext.ket.project.gate.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETTemplateGateCheckSheet"),
   roleA=@GeneratedRole(
      name="targetType",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="sheet",
      type=ext.ket.project.gate.entity.TemplateGateCheckSheet.class,
      cardinality=Cardinality.ONE
   )
)

public class GateChkSheetTagtTypeLink extends _GateChkSheetTagtTypeLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static GateChkSheetTagtTypeLink newGateChkSheetTagtTypeLink( e3ps.common.code.NumberCode targetType, ext.ket.project.gate.entity.TemplateGateCheckSheet sheet ) throws wt.util.WTException {
      GateChkSheetTagtTypeLink GateChkSheetTagtTypeLink_instance = new GateChkSheetTagtTypeLink();
      GateChkSheetTagtTypeLink_instance.initialize(targetType, sheet);
      return GateChkSheetTagtTypeLink_instance;
   }
}
