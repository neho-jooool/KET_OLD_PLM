package e3ps.wfm.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETWfmApprovalHistory"),
   roleA=@GeneratedRole(
      name="appMaster",
      type=e3ps.wfm.entity.KETWfmApprovalMaster.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="history",
      type=e3ps.wfm.entity.KETWfmApprovalHistory.class
   )
)

public class KETWfmMasterHistoryLink extends _KETWfmMasterHistoryLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static KETWfmMasterHistoryLink newKETWfmMasterHistoryLink( e3ps.wfm.entity.KETWfmApprovalMaster appMaster, e3ps.wfm.entity.KETWfmApprovalHistory history ) throws wt.util.WTException {
      KETWfmMasterHistoryLink KETWfmMasterHistoryLink_instance = new KETWfmMasterHistoryLink();
      KETWfmMasterHistoryLink_instance.initialize(appMaster, history);
      return KETWfmMasterHistoryLink_instance;
   }
}
