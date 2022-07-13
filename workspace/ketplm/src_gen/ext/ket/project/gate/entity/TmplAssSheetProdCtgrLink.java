package ext.ket.project.gate.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETTemplateAssessSheet"),
   roleA=@GeneratedRole(
      name="prodCategory",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theTemplateAssessSheet",
      type=ext.ket.project.gate.entity.TemplateAssessSheet.class,
      cardinality=Cardinality.ONE
   )
)

public class TmplAssSheetProdCtgrLink extends _TmplAssSheetProdCtgrLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static TmplAssSheetProdCtgrLink newTmplAssSheetProdCtgrLink( e3ps.common.code.NumberCode prodCategory, ext.ket.project.gate.entity.TemplateAssessSheet theTemplateAssessSheet ) throws wt.util.WTException {
      TmplAssSheetProdCtgrLink TmplAssSheetProdCtgrLink_instance = new TmplAssSheetProdCtgrLink();
      TmplAssSheetProdCtgrLink_instance.initialize(prodCategory, theTemplateAssessSheet);
      return TmplAssSheetProdCtgrLink_instance;
   }
}
