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
      name="ecr",
      type=wt.change2.WTChangeRequest2.class,
      cardinality=Cardinality.ZERO_TO_ONE
   )
)

public class TheKETChangeRequestExpansionEcr extends _TheKETChangeRequestExpansionEcr implements Externalizable {
   static final long serialVersionUID = 1;
   public static TheKETChangeRequestExpansionEcr newTheKETChangeRequestExpansionEcr( e3ps.ecm.entity.KETChangeRequestExpansion theKETChangeRequestExpansion, wt.change2.WTChangeRequest2 ecr ) throws wt.util.WTException {
      TheKETChangeRequestExpansionEcr TheKETChangeRequestExpansionEcr_instance = new TheKETChangeRequestExpansionEcr();
      TheKETChangeRequestExpansionEcr_instance.initialize(theKETChangeRequestExpansion, ecr);
      return TheKETChangeRequestExpansionEcr_instance;
   }
}
