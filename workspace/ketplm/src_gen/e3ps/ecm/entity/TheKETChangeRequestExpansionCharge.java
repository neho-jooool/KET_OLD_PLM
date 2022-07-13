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
      name="charge",
      type=wt.org.WTUser.class,
      cardinality=Cardinality.ZERO_TO_ONE
   )
)

public class TheKETChangeRequestExpansionCharge extends _TheKETChangeRequestExpansionCharge implements Externalizable {
   static final long serialVersionUID = 1;
   public static TheKETChangeRequestExpansionCharge newTheKETChangeRequestExpansionCharge( e3ps.ecm.entity.KETChangeRequestExpansion theKETChangeRequestExpansion, wt.org.WTUser charge ) throws wt.util.WTException {
      TheKETChangeRequestExpansionCharge TheKETChangeRequestExpansionCharge_instance = new TheKETChangeRequestExpansionCharge();
      TheKETChangeRequestExpansionCharge_instance.initialize(theKETChangeRequestExpansion, charge);
      return TheKETChangeRequestExpansionCharge_instance;
   }
}
