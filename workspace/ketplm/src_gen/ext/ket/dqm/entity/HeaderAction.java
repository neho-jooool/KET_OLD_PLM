package ext.ket.dqm.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=true,
   tableProperties=@TableProperties(tableName="KETDqmHeader"),
   roleA=@GeneratedRole(
      name="header",
      type=ext.ket.dqm.entity.KETDqmHeader.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="action",
      type=ext.ket.dqm.entity.KETDqmAction.class,
      cardinality=Cardinality.ZERO_TO_ONE
   )
)

public class HeaderAction extends _HeaderAction implements Externalizable {
   static final long serialVersionUID = 1;
   public static HeaderAction newHeaderAction( ext.ket.dqm.entity.KETDqmHeader header, ext.ket.dqm.entity.KETDqmAction action ) throws wt.util.WTException {
      HeaderAction HeaderAction_instance = new HeaderAction();
      HeaderAction_instance.initialize(header, action);
      return HeaderAction_instance;
   }
}
