package ext.ket.dqm.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETDqmRaise"),
   roleA=@GeneratedRole(
      name="pmUser",
      type=wt.org.WTUser.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="raise",
      type=ext.ket.dqm.entity.KETDqmRaise.class,
      cardinality=Cardinality.ZERO_TO_ONE
   )
)

public class PmUserRaise extends _PmUserRaise implements Externalizable {
   static final long serialVersionUID = 1;
   public static PmUserRaise newPmUserRaise( wt.org.WTUser pmUser, ext.ket.dqm.entity.KETDqmRaise raise ) throws wt.util.WTException {
      PmUserRaise PmUserRaise_instance = new PmUserRaise();
      PmUserRaise_instance.initialize(pmUser, raise);
      return PmUserRaise_instance;
   }
}
