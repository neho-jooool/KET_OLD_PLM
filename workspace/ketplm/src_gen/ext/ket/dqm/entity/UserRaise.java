package ext.ket.dqm.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETDqmRaise"),
   roleA=@GeneratedRole(
      name="user",
      type=wt.org.WTUser.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="raise",
      type=ext.ket.dqm.entity.KETDqmRaise.class,
      cardinality=Cardinality.ZERO_TO_ONE
   )
)

public class UserRaise extends _UserRaise implements Externalizable {
   static final long serialVersionUID = 1;
   public static UserRaise newUserRaise( wt.org.WTUser user, ext.ket.dqm.entity.KETDqmRaise raise ) throws wt.util.WTException {
      UserRaise UserRaise_instance = new UserRaise();
      UserRaise_instance.initialize(user, raise);
      return UserRaise_instance;
   }
}
