package ext.ket.project.program.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETProgramSchedule"),
   roleA=@GeneratedRole(
      name="lastUsingBuyer",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theKETProgramSchedule",
      type=ext.ket.project.program.entity.KETProgramSchedule.class,
      cardinality=Cardinality.ONE
   )
)

public class LastUsingBuyerTheKETProgramSchedule extends _LastUsingBuyerTheKETProgramSchedule implements Externalizable {
   static final long serialVersionUID = 1;
   public static LastUsingBuyerTheKETProgramSchedule newLastUsingBuyerTheKETProgramSchedule( e3ps.common.code.NumberCode lastUsingBuyer, ext.ket.project.program.entity.KETProgramSchedule theKETProgramSchedule ) throws wt.util.WTException {
      LastUsingBuyerTheKETProgramSchedule LastUsingBuyerTheKETProgramSchedule_instance = new LastUsingBuyerTheKETProgramSchedule();
      LastUsingBuyerTheKETProgramSchedule_instance.initialize(lastUsingBuyer, theKETProgramSchedule);
      return LastUsingBuyerTheKETProgramSchedule_instance;
   }
}
