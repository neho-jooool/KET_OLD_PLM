package e3ps.ecm.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=true,
   tableProperties=@TableProperties(tableName="KETChangeRequestExpansion"),
   roleA=@GeneratedRole(
      name="theKETChangeRequestExpansion",
      type=e3ps.ecm.entity.KETChangeRequestExpansion.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="moldChange",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   )
)

public class TheKETChangeRequestExpansionMoldChange extends _TheKETChangeRequestExpansionMoldChange implements Externalizable {
   static final long serialVersionUID = 1;
   public static TheKETChangeRequestExpansionMoldChange newTheKETChangeRequestExpansionMoldChange( e3ps.ecm.entity.KETChangeRequestExpansion theKETChangeRequestExpansion, e3ps.common.code.NumberCode moldChange ) throws wt.util.WTException {
      TheKETChangeRequestExpansionMoldChange TheKETChangeRequestExpansionMoldChange_instance = new TheKETChangeRequestExpansionMoldChange();
      TheKETChangeRequestExpansionMoldChange_instance.initialize(theKETChangeRequestExpansion, moldChange);
      return TheKETChangeRequestExpansionMoldChange_instance;
   }
}
