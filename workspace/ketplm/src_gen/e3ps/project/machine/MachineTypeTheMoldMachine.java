package e3ps.project.machine;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="MoldMachine"),
   roleA=@GeneratedRole(
      name="machineType",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theMoldMachine",
      type=e3ps.project.machine.MoldMachine.class,
      cardinality=Cardinality.ONE
   )
)

public class MachineTypeTheMoldMachine extends _MachineTypeTheMoldMachine implements Externalizable {
   static final long serialVersionUID = 1;
   public static MachineTypeTheMoldMachine newMachineTypeTheMoldMachine( e3ps.common.code.NumberCode machineType, e3ps.project.machine.MoldMachine theMoldMachine ) throws wt.util.WTException {
      MachineTypeTheMoldMachine MachineTypeTheMoldMachine_instance = new MachineTypeTheMoldMachine();
      MachineTypeTheMoldMachine_instance.initialize(machineType, theMoldMachine);
      return MachineTypeTheMoldMachine_instance;
   }
}
