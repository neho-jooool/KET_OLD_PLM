package ext.ket.arm.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETAnalysisRequestMaster"),
   roleA=@GeneratedRole(
      name="carType",
      type=e3ps.project.outputtype.OEMProjectType.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="master",
      type=ext.ket.arm.entity.KETAnalysisRequestMaster.class,
      cardinality=Cardinality.ONE
   )
)

public class KETAnalysisCarTypeLink extends _KETAnalysisCarTypeLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static KETAnalysisCarTypeLink newKETAnalysisCarTypeLink( e3ps.project.outputtype.OEMProjectType carType, ext.ket.arm.entity.KETAnalysisRequestMaster master ) throws wt.util.WTException {
      KETAnalysisCarTypeLink KETAnalysisCarTypeLink_instance = new KETAnalysisCarTypeLink();
      KETAnalysisCarTypeLink_instance.initialize(carType, master);
      return KETAnalysisCarTypeLink_instance;
   }
}
