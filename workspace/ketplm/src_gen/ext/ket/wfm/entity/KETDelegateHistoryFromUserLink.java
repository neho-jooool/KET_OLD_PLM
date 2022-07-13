package ext.ket.wfm.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETTodoDelegateHistory"),
   roleA=@GeneratedRole(
      name="fromUser",
      type=e3ps.groupware.company.People.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="delegateHistory",
      type=ext.ket.wfm.entity.KETTodoDelegateHistory.class,
      cardinality=Cardinality.ONE
   )
)

public class KETDelegateHistoryFromUserLink extends _KETDelegateHistoryFromUserLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static KETDelegateHistoryFromUserLink newKETDelegateHistoryFromUserLink( e3ps.groupware.company.People fromUser, ext.ket.wfm.entity.KETTodoDelegateHistory delegateHistory ) throws wt.util.WTException {
      KETDelegateHistoryFromUserLink KETDelegateHistoryFromUserLink_instance = new KETDelegateHistoryFromUserLink();
      KETDelegateHistoryFromUserLink_instance.initialize(fromUser, delegateHistory);
      return KETDelegateHistoryFromUserLink_instance;
   }
}
