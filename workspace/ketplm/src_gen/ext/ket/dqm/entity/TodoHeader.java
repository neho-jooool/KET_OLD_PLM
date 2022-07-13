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
      name="header",
      type=ext.ket.dqm.entity.KETDqmHeader.class,
      cardinality=Cardinality.ZERO_TO_ONE
   )
)

public class TodoHeader extends _TodoHeader implements Externalizable {
   static final long serialVersionUID = 1;
   public static TodoHeader newTodoHeader( ext.ket.dqm.entity.KETDqmTodoAtivity todo, ext.ket.dqm.entity.KETDqmHeader header ) throws wt.util.WTException {
      TodoHeader TodoHeader_instance = new TodoHeader();
      TodoHeader_instance.initialize(todo, header);
      return TodoHeader_instance;
   }
}
