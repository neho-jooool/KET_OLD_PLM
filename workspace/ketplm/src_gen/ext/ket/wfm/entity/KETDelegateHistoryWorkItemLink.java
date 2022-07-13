package ext.ket.wfm.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETTodoDelegateHistory"),
   roleA=@GeneratedRole(
      name="workitem",
      type=wt.workflow.work.WorkItem.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="delegateHostory",
      type=ext.ket.wfm.entity.KETTodoDelegateHistory.class,
      cardinality=Cardinality.ZERO_TO_ONE
   )
)

public class KETDelegateHistoryWorkItemLink extends _KETDelegateHistoryWorkItemLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static KETDelegateHistoryWorkItemLink newKETDelegateHistoryWorkItemLink( wt.workflow.work.WorkItem workitem, ext.ket.wfm.entity.KETTodoDelegateHistory delegateHostory ) throws wt.util.WTException {
      KETDelegateHistoryWorkItemLink KETDelegateHistoryWorkItemLink_instance = new KETDelegateHistoryWorkItemLink();
      KETDelegateHistoryWorkItemLink_instance.initialize(workitem, delegateHostory);
      return KETDelegateHistoryWorkItemLink_instance;
   }
}
