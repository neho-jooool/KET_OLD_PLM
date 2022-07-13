package ext.ket.project.gate.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETTemplateAssessSheet"),
   roleA=@GeneratedRole(
      name="devType",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theTemplateAssessSheet",
      type=ext.ket.project.gate.entity.TemplateAssessSheet.class,
      cardinality=Cardinality.ONE
   )
)

public class TmplAssSheetDevTypeLink extends _TmplAssSheetDevTypeLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static TmplAssSheetDevTypeLink newTmplAssSheetDevTypeLink( e3ps.common.code.NumberCode devType, ext.ket.project.gate.entity.TemplateAssessSheet theTemplateAssessSheet ) throws wt.util.WTException {
      TmplAssSheetDevTypeLink TmplAssSheetDevTypeLink_instance = new TmplAssSheetDevTypeLink();
      TmplAssSheetDevTypeLink_instance.initialize(devType, theTemplateAssessSheet);
      return TmplAssSheetDevTypeLink_instance;
   }
}
