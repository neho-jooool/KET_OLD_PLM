package ext.ket.dqm.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETDqmAction"),
   roleA=@GeneratedRole(
      name="user",
      type=wt.org.WTUser.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="action",
      type=ext.ket.dqm.entity.KETDqmAction.class,
      cardinality=Cardinality.ZERO_TO_ONE
   )
)

public class UserAction extends _UserAction implements Externalizable {
   static final long serialVersionUID = 1;
   public static UserAction newUserAction( wt.org.WTUser user, ext.ket.dqm.entity.KETDqmAction action ) throws wt.util.WTException {
      UserAction UserAction_instance = new UserAction();
      UserAction_instance.initialize(user, action);
      return UserAction_instance;
   }
}
