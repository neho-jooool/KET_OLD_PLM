package ext.ket.dqm.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=true,
   tableProperties=@TableProperties(tableName="KETDqmTodoAtivity"),
   roleA=@GeneratedRole(
      name="todo",
      type=ext.ket.dqm.entity.KETDqmTodoAtivity.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="action",
      type=ext.ket.dqm.entity.KETDqmAction.class,
      cardinality=Cardinality.ZERO_TO_ONE
   )
)

public class TodoAction extends _TodoAction implements Externalizable {
   static final long serialVersionUID = 1;
   public static TodoAction newTodoAction( ext.ket.dqm.entity.KETDqmTodoAtivity todo, ext.ket.dqm.entity.KETDqmAction action ) throws wt.util.WTException {
      TodoAction TodoAction_instance = new TodoAction();
      TodoAction_instance.initialize(todo, action);
      return TodoAction_instance;
   }
}
