package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="ProjectOutput"),
   roleA=@GeneratedRole(
      name="outputType",
      type=e3ps.project.outputtype.ProjectOutPutType.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="theProjectOutput",
      type=e3ps.project.ProjectOutput.class,
      cardinality=Cardinality.ONE
   )
)

public class OutputTypeTheProjectOutput extends _OutputTypeTheProjectOutput implements Externalizable {
   static final long serialVersionUID = 1;
   public static OutputTypeTheProjectOutput newOutputTypeTheProjectOutput( e3ps.project.outputtype.ProjectOutPutType outputType, e3ps.project.ProjectOutput theProjectOutput ) throws wt.util.WTException {
      OutputTypeTheProjectOutput OutputTypeTheProjectOutput_instance = new OutputTypeTheProjectOutput();
      OutputTypeTheProjectOutput_instance.initialize(outputType, theProjectOutput);
      return OutputTypeTheProjectOutput_instance;
   }
}
