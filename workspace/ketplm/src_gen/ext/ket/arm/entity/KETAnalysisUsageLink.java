package ext.ket.arm.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETAnalysisRequestMaster"),
   roleA=@GeneratedRole(
      name="usage",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="master",
      type=ext.ket.arm.entity.KETAnalysisRequestMaster.class,
      cardinality=Cardinality.ONE
   )
)

public class KETAnalysisUsageLink extends _KETAnalysisUsageLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static KETAnalysisUsageLink newKETAnalysisUsageLink( e3ps.common.code.NumberCode usage, ext.ket.arm.entity.KETAnalysisRequestMaster master ) throws wt.util.WTException {
      KETAnalysisUsageLink KETAnalysisUsageLink_instance = new KETAnalysisUsageLink();
      KETAnalysisUsageLink_instance.initialize(usage, master);
      return KETAnalysisUsageLink_instance;
   }
}
