package e3ps.project.machine;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="MoldMachine"),
   roleA=@GeneratedRole(
      name="machineMaker",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theMoldMachine",
      type=e3ps.project.machine.MoldMachine.class,
      cardinality=Cardinality.ONE
   )
)

public class MachineMakerTheMoldMachine extends _MachineMakerTheMoldMachine implements Externalizable {
   static final long serialVersionUID = 1;
   public static MachineMakerTheMoldMachine newMachineMakerTheMoldMachine( e3ps.common.code.NumberCode machineMaker, e3ps.project.machine.MoldMachine theMoldMachine ) throws wt.util.WTException {
      MachineMakerTheMoldMachine MachineMakerTheMoldMachine_instance = new MachineMakerTheMoldMachine();
      MachineMakerTheMoldMachine_instance.initialize(machineMaker, theMoldMachine);
      return MachineMakerTheMoldMachine_instance;
   }
}
