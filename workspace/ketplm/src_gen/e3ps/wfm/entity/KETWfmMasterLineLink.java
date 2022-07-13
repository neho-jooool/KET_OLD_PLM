package e3ps.wfm.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETWfmApprovalLine"),
   roleA=@GeneratedRole(
      name="appMaster",
      type=e3ps.wfm.entity.KETWfmApprovalMaster.class,
      supportedAPI=SupportedAPI.PUBLIC,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="line",
      type=e3ps.wfm.entity.KETWfmApprovalLine.class,
      supportedAPI=SupportedAPI.PUBLIC
   )
)

public class KETWfmMasterLineLink extends _KETWfmMasterLineLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static KETWfmMasterLineLink newKETWfmMasterLineLink( e3ps.wfm.entity.KETWfmApprovalMaster appMaster, e3ps.wfm.entity.KETWfmApprovalLine line ) throws wt.util.WTException {
      KETWfmMasterLineLink KETWfmMasterLineLink_instance = new KETWfmMasterLineLink();
      KETWfmMasterLineLink_instance.initialize(appMaster, line);
      return KETWfmMasterLineLink_instance;
   }
}
