package ext.ket.arm.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETAnalysisRequestMaster"),
   roleA=@GeneratedRole(
      name="ketChargeUser",
      type=wt.org.WTUser.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theKETAnalysisRequestMaster",
      type=ext.ket.arm.entity.KETAnalysisRequestMaster.class,
      cardinality=Cardinality.ONE
   )
)

public class KETChargeUser extends _KETChargeUser implements Externalizable {
   static final long serialVersionUID = 1;
   public static KETChargeUser newKETChargeUser( wt.org.WTUser ketChargeUser, ext.ket.arm.entity.KETAnalysisRequestMaster theKETAnalysisRequestMaster ) throws wt.util.WTException {
      KETChargeUser KETChargeUser_instance = new KETChargeUser();
      KETChargeUser_instance.initialize(ketChargeUser, theKETAnalysisRequestMaster);
      return KETChargeUser_instance;
   }
}
