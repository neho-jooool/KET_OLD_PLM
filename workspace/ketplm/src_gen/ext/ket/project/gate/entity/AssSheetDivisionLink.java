package ext.ket.project.gate.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETAssessSheet"),
   roleA=@GeneratedRole(
      name="division",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theAssessSheet",
      type=ext.ket.project.gate.entity.AssessSheet.class,
      cardinality=Cardinality.ONE
   )
)

public class AssSheetDivisionLink extends _AssSheetDivisionLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static AssSheetDivisionLink newAssSheetDivisionLink( e3ps.common.code.NumberCode division, ext.ket.project.gate.entity.AssessSheet theAssessSheet ) throws wt.util.WTException {
      AssSheetDivisionLink AssSheetDivisionLink_instance = new AssSheetDivisionLink();
      AssSheetDivisionLink_instance.initialize(division, theAssessSheet);
      return AssSheetDivisionLink_instance;
   }
}
