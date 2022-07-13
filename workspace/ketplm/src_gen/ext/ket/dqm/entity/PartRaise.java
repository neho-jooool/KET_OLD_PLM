package ext.ket.dqm.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETDqmRaise"),
   roleA=@GeneratedRole(
      name="part",
      type=wt.part.WTPart.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="raise",
      type=ext.ket.dqm.entity.KETDqmRaise.class,
      cardinality=Cardinality.ZERO_TO_ONE
   )
)

public class PartRaise extends _PartRaise implements Externalizable {
   static final long serialVersionUID = 1;
   public static PartRaise newPartRaise( wt.part.WTPart part, ext.ket.dqm.entity.KETDqmRaise raise ) throws wt.util.WTException {
      PartRaise PartRaise_instance = new PartRaise();
      PartRaise_instance.initialize(part, raise);
      return PartRaise_instance;
   }
}
