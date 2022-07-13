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
      name="emergencyPosition",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   )
)

public class TheKETChangeRequestExpansionEmergencyPosition extends _TheKETChangeRequestExpansionEmergencyPosition implements Externalizable {
   static final long serialVersionUID = 1;
   public static TheKETChangeRequestExpansionEmergencyPosition newTheKETChangeRequestExpansionEmergencyPosition( e3ps.ecm.entity.KETChangeRequestExpansion theKETChangeRequestExpansion, e3ps.common.code.NumberCode emergencyPosition ) throws wt.util.WTException {
      TheKETChangeRequestExpansionEmergencyPosition TheKETChangeRequestExpansionEmergencyPosition_instance = new TheKETChangeRequestExpansionEmergencyPosition();
      TheKETChangeRequestExpansionEmergencyPosition_instance.initialize(theKETChangeRequestExpansion, emergencyPosition);
      return TheKETChangeRequestExpansionEmergencyPosition_instance;
   }
}
