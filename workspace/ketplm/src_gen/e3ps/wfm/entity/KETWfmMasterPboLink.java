package e3ps.wfm.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETWfmApprovalMaster"),
   roleA=@GeneratedRole(
      name="pbo",
      type=wt.fc.Persistable.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="appMaster",
      type=e3ps.wfm.entity.KETWfmApprovalMaster.class,
      cardinality=Cardinality.ZERO_TO_ONE
   )
)

public class KETWfmMasterPboLink extends _KETWfmMasterPboLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static KETWfmMasterPboLink newKETWfmMasterPboLink( wt.fc.Persistable pbo, e3ps.wfm.entity.KETWfmApprovalMaster appMaster ) throws wt.util.WTException {
      KETWfmMasterPboLink KETWfmMasterPboLink_instance = new KETWfmMasterPboLink();
      KETWfmMasterPboLink_instance.initialize(pbo, appMaster);
      return KETWfmMasterPboLink_instance;
   }
}
