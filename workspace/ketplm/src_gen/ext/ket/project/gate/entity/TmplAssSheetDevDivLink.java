package ext.ket.project.gate.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETTemplateAssessSheet"),
   roleA=@GeneratedRole(
      name="devDiv",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theTemplateAssessSheet",
      type=ext.ket.project.gate.entity.TemplateAssessSheet.class,
      cardinality=Cardinality.ONE
   )
)

public class TmplAssSheetDevDivLink extends _TmplAssSheetDevDivLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static TmplAssSheetDevDivLink newTmplAssSheetDevDivLink( e3ps.common.code.NumberCode devDiv, ext.ket.project.gate.entity.TemplateAssessSheet theTemplateAssessSheet ) throws wt.util.WTException {
      TmplAssSheetDevDivLink TmplAssSheetDevDivLink_instance = new TmplAssSheetDevDivLink();
      TmplAssSheetDevDivLink_instance.initialize(devDiv, theTemplateAssessSheet);
      return TmplAssSheetDevDivLink_instance;
   }
}
