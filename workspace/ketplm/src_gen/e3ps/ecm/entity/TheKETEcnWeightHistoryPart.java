package e3ps.ecm.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=true,
   tableProperties=@TableProperties(tableName="KETEcnWeightHistory"),
   roleA=@GeneratedRole(
      name="theKETEcnWeightHistory",
      type=e3ps.ecm.entity.KETEcnWeightHistory.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="part",
      type=wt.part.WTPart.class,
      cardinality=Cardinality.ZERO_TO_ONE
   )
)

public class TheKETEcnWeightHistoryPart extends _TheKETEcnWeightHistoryPart implements Externalizable {
   static final long serialVersionUID = 1;
   public static TheKETEcnWeightHistoryPart newTheKETEcnWeightHistoryPart( e3ps.ecm.entity.KETEcnWeightHistory theKETEcnWeightHistory, wt.part.WTPart part ) throws wt.util.WTException {
      TheKETEcnWeightHistoryPart TheKETEcnWeightHistoryPart_instance = new TheKETEcnWeightHistoryPart();
      TheKETEcnWeightHistoryPart_instance.initialize(theKETEcnWeightHistory, part);
      return TheKETEcnWeightHistoryPart_instance;
   }
}
