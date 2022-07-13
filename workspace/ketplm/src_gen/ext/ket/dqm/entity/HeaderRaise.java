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
      name="raise",
      type=ext.ket.dqm.entity.KETDqmRaise.class,
      cardinality=Cardinality.ZERO_TO_ONE
   )
)

public class HeaderRaise extends _HeaderRaise implements Externalizable {
   static final long serialVersionUID = 1;
   public static HeaderRaise newHeaderRaise( ext.ket.dqm.entity.KETDqmHeader header, ext.ket.dqm.entity.KETDqmRaise raise ) throws wt.util.WTException {
      HeaderRaise HeaderRaise_instance = new HeaderRaise();
      HeaderRaise_instance.initialize(header, raise);
      return HeaderRaise_instance;
   }
}
