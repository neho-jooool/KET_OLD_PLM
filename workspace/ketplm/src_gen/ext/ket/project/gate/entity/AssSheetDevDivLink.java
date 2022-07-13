package ext.ket.project.gate.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETAssessSheet"),
   roleA=@GeneratedRole(
      name="devDiv",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theAssessSheet",
      type=ext.ket.project.gate.entity.AssessSheet.class,
      cardinality=Cardinality.ONE
   )
)

public class AssSheetDevDivLink extends _AssSheetDevDivLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static AssSheetDevDivLink newAssSheetDevDivLink( e3ps.common.code.NumberCode devDiv, ext.ket.project.gate.entity.AssessSheet theAssessSheet ) throws wt.util.WTException {
      AssSheetDevDivLink AssSheetDevDivLink_instance = new AssSheetDevDivLink();
      AssSheetDevDivLink_instance.initialize(devDiv, theAssessSheet);
      return AssSheetDevDivLink_instance;
   }
}
