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
      name="eco",
      type=wt.change2.WTChangeOrder2.class,
      cardinality=Cardinality.ZERO_TO_ONE
   )
)

public class TheKETEcnWeightHistoryEco extends _TheKETEcnWeightHistoryEco implements Externalizable {
   static final long serialVersionUID = 1;
   public static TheKETEcnWeightHistoryEco newTheKETEcnWeightHistoryEco( e3ps.ecm.entity.KETEcnWeightHistory theKETEcnWeightHistory, wt.change2.WTChangeOrder2 eco ) throws wt.util.WTException {
      TheKETEcnWeightHistoryEco TheKETEcnWeightHistoryEco_instance = new TheKETEcnWeightHistoryEco();
      TheKETEcnWeightHistoryEco_instance.initialize(theKETEcnWeightHistory, eco);
      return TheKETEcnWeightHistoryEco_instance;
   }
}
