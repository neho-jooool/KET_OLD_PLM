package ext.ket.project.gate.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=true,
   tableProperties=@TableProperties(tableName="KETGateAssessResult"),
   roleA=@GeneratedRole(
      name="result",
      type=ext.ket.project.gate.entity.GateAssessResult.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="project",
      type=e3ps.project.E3PSProject.class,
      cardinality=Cardinality.ZERO_TO_ONE
   )
)

public class GateAssRsltPjtLink extends _GateAssRsltPjtLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static GateAssRsltPjtLink newGateAssRsltPjtLink( ext.ket.project.gate.entity.GateAssessResult result, e3ps.project.E3PSProject project ) throws wt.util.WTException {
      GateAssRsltPjtLink GateAssRsltPjtLink_instance = new GateAssRsltPjtLink();
      GateAssRsltPjtLink_instance.initialize(result, project);
      return GateAssRsltPjtLink_instance;
   }
}
