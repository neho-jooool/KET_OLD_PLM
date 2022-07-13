package ext.ket.project.gate.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETAssessSheet"),
   roleA=@GeneratedRole(
      name="prodCategory",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theAssessSheet",
      type=ext.ket.project.gate.entity.AssessSheet.class,
      cardinality=Cardinality.ONE
   )
)

public class AssSheetProdCtgrLink extends _AssSheetProdCtgrLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static AssSheetProdCtgrLink newAssSheetProdCtgrLink( e3ps.common.code.NumberCode prodCategory, ext.ket.project.gate.entity.AssessSheet theAssessSheet ) throws wt.util.WTException {
      AssSheetProdCtgrLink AssSheetProdCtgrLink_instance = new AssSheetProdCtgrLink();
      AssSheetProdCtgrLink_instance.initialize(prodCategory, theAssessSheet);
      return AssSheetProdCtgrLink_instance;
   }
}
