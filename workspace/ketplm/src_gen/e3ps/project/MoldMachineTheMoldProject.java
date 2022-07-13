package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="MoldProject"),
   roleA=@GeneratedRole(
      name="moldMachine",
      type=e3ps.project.machine.MoldMachine.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="theMoldProject",
      type=e3ps.project.MoldProject.class,
      cardinality=Cardinality.ONE
   )
)

public class MoldMachineTheMoldProject extends _MoldMachineTheMoldProject implements Externalizable {
   static final long serialVersionUID = 1;
   public static MoldMachineTheMoldProject newMoldMachineTheMoldProject( e3ps.project.machine.MoldMachine moldMachine, e3ps.project.MoldProject theMoldProject ) throws wt.util.WTException {
      MoldMachineTheMoldProject MoldMachineTheMoldProject_instance = new MoldMachineTheMoldProject();
      MoldMachineTheMoldProject_instance.initialize(moldMachine, theMoldProject);
      return MoldMachineTheMoldProject_instance;
   }
}
