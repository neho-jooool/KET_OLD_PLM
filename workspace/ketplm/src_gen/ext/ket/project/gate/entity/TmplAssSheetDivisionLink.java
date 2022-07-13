package ext.ket.project.gate.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETTemplateAssessSheet"),
   roleA=@GeneratedRole(
      name="division",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theTemplateAssessSheet",
      type=ext.ket.project.gate.entity.TemplateAssessSheet.class,
      cardinality=Cardinality.ONE
   )
)

public class TmplAssSheetDivisionLink extends _TmplAssSheetDivisionLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static TmplAssSheetDivisionLink newTmplAssSheetDivisionLink( e3ps.common.code.NumberCode division, ext.ket.project.gate.entity.TemplateAssessSheet theTemplateAssessSheet ) throws wt.util.WTException {
      TmplAssSheetDivisionLink TmplAssSheetDivisionLink_instance = new TmplAssSheetDivisionLink();
      TmplAssSheetDivisionLink_instance.initialize(division, theTemplateAssessSheet);
      return TmplAssSheetDivisionLink_instance;
   }
}
