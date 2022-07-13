package ext.ket.arm.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETAnalysisRequestMaster"),
   roleA=@GeneratedRole(
      name="process",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="master",
      type=ext.ket.arm.entity.KETAnalysisRequestMaster.class,
      cardinality=Cardinality.ONE
   )
)

public class KETAnalysisProcessLink extends _KETAnalysisProcessLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static KETAnalysisProcessLink newKETAnalysisProcessLink( e3ps.common.code.NumberCode process, ext.ket.arm.entity.KETAnalysisRequestMaster master ) throws wt.util.WTException {
      KETAnalysisProcessLink KETAnalysisProcessLink_instance = new KETAnalysisProcessLink();
      KETAnalysisProcessLink_instance.initialize(process, master);
      return KETAnalysisProcessLink_instance;
   }
}
