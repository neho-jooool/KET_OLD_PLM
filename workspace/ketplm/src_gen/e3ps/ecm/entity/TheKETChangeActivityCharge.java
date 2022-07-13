package e3ps.ecm.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=true,
   tableProperties=@TableProperties(tableName="KETChangeActivity"),
   roleA=@GeneratedRole(
      name="theKETChangeActivity",
      type=e3ps.ecm.entity.KETChangeActivity.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="charge",
      type=wt.org.WTUser.class,
      cardinality=Cardinality.ZERO_TO_ONE
   )
)

public class TheKETChangeActivityCharge extends _TheKETChangeActivityCharge implements Externalizable {
   static final long serialVersionUID = 1;
   public static TheKETChangeActivityCharge newTheKETChangeActivityCharge( e3ps.ecm.entity.KETChangeActivity theKETChangeActivity, wt.org.WTUser charge ) throws wt.util.WTException {
      TheKETChangeActivityCharge TheKETChangeActivityCharge_instance = new TheKETChangeActivityCharge();
      TheKETChangeActivityCharge_instance.initialize(theKETChangeActivity, charge);
      return TheKETChangeActivityCharge_instance;
   }
}
