package ext.ket.project.trycondition.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETTryMold"),
   roleA=@GeneratedRole(
      name="temperatureSensor",
      type=e3ps.common.code.NumberCode.class,
      javaDoc="온도센서",
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theKETTryMold",
      type=ext.ket.project.trycondition.entity.KETTryMold.class,
      cardinality=Cardinality.ONE
   )
)

public class TempSensorLink extends _TempSensorLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static TempSensorLink newTempSensorLink( e3ps.common.code.NumberCode temperatureSensor, ext.ket.project.trycondition.entity.KETTryMold theKETTryMold ) throws wt.util.WTException {
      TempSensorLink TempSensorLink_instance = new TempSensorLink();
      TempSensorLink_instance.initialize(temperatureSensor, theKETTryMold);
      return TempSensorLink_instance;
   }
}
