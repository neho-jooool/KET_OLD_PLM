package ext.ket.dqm.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETDqmRaise"),
   roleA=@GeneratedRole(
      name="actionUser",
      type=wt.org.WTUser.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="action",
      type=ext.ket.dqm.entity.KETDqmRaise.class,
      cardinality=Cardinality.ZERO_TO_ONE
   )
)

public class ActionUserAction extends _ActionUserAction implements Externalizable {
   static final long serialVersionUID = 1;
   public static ActionUserAction newActionUserAction( wt.org.WTUser actionUser, ext.ket.dqm.entity.KETDqmRaise action ) throws wt.util.WTException {
      ActionUserAction ActionUserAction_instance = new ActionUserAction();
      ActionUserAction_instance.initialize(actionUser, action);
      return ActionUserAction_instance;
   }
}
