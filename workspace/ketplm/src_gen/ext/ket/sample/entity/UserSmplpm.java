package ext.ket.sample.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETSamplePart"),
   roleA=@GeneratedRole(
      name="user",
      type=wt.org.WTUser.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="smplpm",
      type=ext.ket.sample.entity.KETSamplePart.class,
      cardinality=Cardinality.ZERO_TO_ONE
   )
)

public class UserSmplpm extends _UserSmplpm implements Externalizable {
   static final long serialVersionUID = 1;
   public static UserSmplpm newUserSmplpm( wt.org.WTUser user, ext.ket.sample.entity.KETSamplePart smplpm ) throws wt.util.WTException {
      UserSmplpm UserSmplpm_instance = new UserSmplpm();
      UserSmplpm_instance.initialize(user, smplpm);
      return UserSmplpm_instance;
   }
}
