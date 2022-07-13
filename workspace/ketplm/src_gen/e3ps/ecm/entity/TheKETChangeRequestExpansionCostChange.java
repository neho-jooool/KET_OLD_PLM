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
      name="costChange",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   )
)

public class TheKETChangeRequestExpansionCostChange extends _TheKETChangeRequestExpansionCostChange implements Externalizable {
   static final long serialVersionUID = 1;
   public static TheKETChangeRequestExpansionCostChange newTheKETChangeRequestExpansionCostChange( e3ps.ecm.entity.KETChangeRequestExpansion theKETChangeRequestExpansion, e3ps.common.code.NumberCode costChange ) throws wt.util.WTException {
      TheKETChangeRequestExpansionCostChange TheKETChangeRequestExpansionCostChange_instance = new TheKETChangeRequestExpansionCostChange();
      TheKETChangeRequestExpansionCostChange_instance.initialize(theKETChangeRequestExpansion, costChange);
      return TheKETChangeRequestExpansionCostChange_instance;
   }
}
