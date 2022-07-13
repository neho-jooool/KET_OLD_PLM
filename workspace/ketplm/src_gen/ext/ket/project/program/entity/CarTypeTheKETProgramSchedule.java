package ext.ket.project.program.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETProgramSchedule"),
   roleA=@GeneratedRole(
      name="carType",
      type=e3ps.project.outputtype.OEMProjectType.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theKETProgramSchedule",
      type=ext.ket.project.program.entity.KETProgramSchedule.class,
      cardinality=Cardinality.ONE
   )
)

public class CarTypeTheKETProgramSchedule extends _CarTypeTheKETProgramSchedule implements Externalizable {
   static final long serialVersionUID = 1;
   public static CarTypeTheKETProgramSchedule newCarTypeTheKETProgramSchedule( e3ps.project.outputtype.OEMProjectType carType, ext.ket.project.program.entity.KETProgramSchedule theKETProgramSchedule ) throws wt.util.WTException {
      CarTypeTheKETProgramSchedule CarTypeTheKETProgramSchedule_instance = new CarTypeTheKETProgramSchedule();
      CarTypeTheKETProgramSchedule_instance.initialize(carType, theKETProgramSchedule);
      return CarTypeTheKETProgramSchedule_instance;
   }
}
