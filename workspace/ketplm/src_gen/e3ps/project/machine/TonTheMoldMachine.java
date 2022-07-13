package e3ps.project.machine;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="MoldMachine"),
   roleA=@GeneratedRole(
      name="ton",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theMoldMachine",
      type=e3ps.project.machine.MoldMachine.class,
      cardinality=Cardinality.ONE
   )
)

public class TonTheMoldMachine extends _TonTheMoldMachine implements Externalizable {
   static final long serialVersionUID = 1;
   public static TonTheMoldMachine newTonTheMoldMachine( e3ps.common.code.NumberCode ton, e3ps.project.machine.MoldMachine theMoldMachine ) throws wt.util.WTException {
      TonTheMoldMachine TonTheMoldMachine_instance = new TonTheMoldMachine();
      TonTheMoldMachine_instance.initialize(ton, theMoldMachine);
      return TonTheMoldMachine_instance;
   }
}
