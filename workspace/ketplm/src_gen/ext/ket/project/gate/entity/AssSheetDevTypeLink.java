package ext.ket.project.gate.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETAssessSheet"),
   roleA=@GeneratedRole(
      name="devType",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theAssessSheet",
      type=ext.ket.project.gate.entity.AssessSheet.class,
      cardinality=Cardinality.ONE
   )
)

public class AssSheetDevTypeLink extends _AssSheetDevTypeLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static AssSheetDevTypeLink newAssSheetDevTypeLink( e3ps.common.code.NumberCode devType, ext.ket.project.gate.entity.AssessSheet theAssessSheet ) throws wt.util.WTException {
      AssSheetDevTypeLink AssSheetDevTypeLink_instance = new AssSheetDevTypeLink();
      AssSheetDevTypeLink_instance.initialize(devType, theAssessSheet);
      return AssSheetDevTypeLink_instance;
   }
}
