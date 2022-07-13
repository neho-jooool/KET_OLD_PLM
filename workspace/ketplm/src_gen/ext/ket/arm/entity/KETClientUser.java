package ext.ket.arm.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETAnalysisRequestMaster"),
   roleA=@GeneratedRole(
      name="ketClientUser",
      type=wt.org.WTUser.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theKETAnalysisRequestMaster",
      type=ext.ket.arm.entity.KETAnalysisRequestMaster.class,
      cardinality=Cardinality.ONE
   )
)

public class KETClientUser extends _KETClientUser implements Externalizable {
   static final long serialVersionUID = 1;
   public static KETClientUser newKETClientUser( wt.org.WTUser ketClientUser, ext.ket.arm.entity.KETAnalysisRequestMaster theKETAnalysisRequestMaster ) throws wt.util.WTException {
      KETClientUser KETClientUser_instance = new KETClientUser();
      KETClientUser_instance.initialize(ketClientUser, theKETAnalysisRequestMaster);
      return KETClientUser_instance;
   }
}
