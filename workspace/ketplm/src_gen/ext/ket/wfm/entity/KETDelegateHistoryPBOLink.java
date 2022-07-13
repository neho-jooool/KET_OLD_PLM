package ext.ket.wfm.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETTodoDelegateHistory"),
   roleA=@GeneratedRole(
      name="pbo",
      type=wt.fc.Persistable.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="delegateHistory",
      type=ext.ket.wfm.entity.KETTodoDelegateHistory.class,
      cardinality=Cardinality.ZERO_TO_ONE
   )
)

public class KETDelegateHistoryPBOLink extends _KETDelegateHistoryPBOLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static KETDelegateHistoryPBOLink newKETDelegateHistoryPBOLink( wt.fc.Persistable pbo, ext.ket.wfm.entity.KETTodoDelegateHistory delegateHistory ) throws wt.util.WTException {
      KETDelegateHistoryPBOLink KETDelegateHistoryPBOLink_instance = new KETDelegateHistoryPBOLink();
      KETDelegateHistoryPBOLink_instance.initialize(pbo, delegateHistory);
      return KETDelegateHistoryPBOLink_instance;
   }
}
