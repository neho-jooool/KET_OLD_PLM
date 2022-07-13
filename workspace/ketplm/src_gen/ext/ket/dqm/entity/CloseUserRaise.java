package ext.ket.dqm.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETDqmRaise"),
   roleA=@GeneratedRole(
      name="closeUser",
      type=wt.org.WTUser.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="raise",
      type=ext.ket.dqm.entity.KETDqmRaise.class,
      cardinality=Cardinality.ZERO_TO_ONE
   )
)

public class CloseUserRaise extends _CloseUserRaise implements Externalizable {
   static final long serialVersionUID = 1;
   public static CloseUserRaise newCloseUserRaise( wt.org.WTUser closeUser, ext.ket.dqm.entity.KETDqmRaise raise ) throws wt.util.WTException {
      CloseUserRaise CloseUserRaise_instance = new CloseUserRaise();
      CloseUserRaise_instance.initialize(closeUser, raise);
      return CloseUserRaise_instance;
   }
}
