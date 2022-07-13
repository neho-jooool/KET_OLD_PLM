package e3ps.groupware.board;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="ImproveBoard"),
   roleA=@GeneratedRole(
      name="user",
      type=wt.org.WTUser.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="improveboard",
      type=e3ps.groupware.board.ImproveBoard.class,
      cardinality=Cardinality.ZERO_TO_ONE
   )
)

public class manager extends _manager implements Externalizable {
   static final long serialVersionUID = 1;
   public static manager newmanager( wt.org.WTUser user, e3ps.groupware.board.ImproveBoard improveboard ) throws wt.util.WTException {
      manager manager_instance = new manager();
      manager_instance.initialize(user, improveboard);
      return manager_instance;
   }
}
