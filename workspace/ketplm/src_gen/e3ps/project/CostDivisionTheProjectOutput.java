package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="ProjectOutput"),
   roleA=@GeneratedRole(
      name="costDivision",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="theProjectOutput",
      type=e3ps.project.ProjectOutput.class
   )
)

public class CostDivisionTheProjectOutput extends _CostDivisionTheProjectOutput implements Externalizable {
   static final long serialVersionUID = 1;
   public static CostDivisionTheProjectOutput newCostDivisionTheProjectOutput( e3ps.common.code.NumberCode costDivision, e3ps.project.ProjectOutput theProjectOutput ) throws wt.util.WTException {
      CostDivisionTheProjectOutput CostDivisionTheProjectOutput_instance = new CostDivisionTheProjectOutput();
      CostDivisionTheProjectOutput_instance.initialize(costDivision, theProjectOutput);
      return CostDivisionTheProjectOutput_instance;
   }
}
