package ext.ket.sample.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETSampleRequest"),
   roleA=@GeneratedRole(
      name="user",
      type=wt.org.WTUser.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="contractor",
      type=ext.ket.sample.entity.KETSampleRequest.class,
      cardinality=Cardinality.ZERO_TO_ONE
   )
)

public class UserContractor extends _UserContractor implements Externalizable {
   static final long serialVersionUID = 1;
   public static UserContractor newUserContractor( wt.org.WTUser user, ext.ket.sample.entity.KETSampleRequest contractor ) throws wt.util.WTException {
      UserContractor UserContractor_instance = new UserContractor();
      UserContractor_instance.initialize(user, contractor);
      return UserContractor_instance;
   }
}
