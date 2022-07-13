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
      name="close",
      type=ext.ket.dqm.entity.KETDqmClose.class,
      cardinality=Cardinality.ZERO_TO_ONE
   )
)

public class TodoClose extends _TodoClose implements Externalizable {
   static final long serialVersionUID = 1;
   public static TodoClose newTodoClose( ext.ket.dqm.entity.KETDqmTodoAtivity todo, ext.ket.dqm.entity.KETDqmClose close ) throws wt.util.WTException {
      TodoClose TodoClose_instance = new TodoClose();
      TodoClose_instance.initialize(todo, close);
      return TodoClose_instance;
   }
}
