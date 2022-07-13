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
      name="activity",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   )
)

public class TheKETChangeActivityActivity extends _TheKETChangeActivityActivity implements Externalizable {
   static final long serialVersionUID = 1;
   public static TheKETChangeActivityActivity newTheKETChangeActivityActivity( e3ps.ecm.entity.KETChangeActivity theKETChangeActivity, e3ps.common.code.NumberCode activity ) throws wt.util.WTException {
      TheKETChangeActivityActivity TheKETChangeActivityActivity_instance = new TheKETChangeActivityActivity();
      TheKETChangeActivityActivity_instance.initialize(theKETChangeActivity, activity);
      return TheKETChangeActivityActivity_instance;
   }
}
