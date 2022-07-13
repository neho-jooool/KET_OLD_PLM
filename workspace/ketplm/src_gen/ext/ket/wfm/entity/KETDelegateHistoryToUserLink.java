package ext.ket.wfm.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETTodoDelegateHistory"),
   roleA=@GeneratedRole(
      name="toUser",
      type=e3ps.groupware.company.People.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="delegateHistory",
      type=ext.ket.wfm.entity.KETTodoDelegateHistory.class,
      cardinality=Cardinality.ONE
   )
)

public class KETDelegateHistoryToUserLink extends _KETDelegateHistoryToUserLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static KETDelegateHistoryToUserLink newKETDelegateHistoryToUserLink( e3ps.groupware.company.People toUser, ext.ket.wfm.entity.KETTodoDelegateHistory delegateHistory ) throws wt.util.WTException {
      KETDelegateHistoryToUserLink KETDelegateHistoryToUserLink_instance = new KETDelegateHistoryToUserLink();
      KETDelegateHistoryToUserLink_instance.initialize(toUser, delegateHistory);
      return KETDelegateHistoryToUserLink_instance;
   }
}
