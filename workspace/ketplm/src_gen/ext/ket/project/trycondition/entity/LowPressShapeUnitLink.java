package ext.ket.project.trycondition.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETTryMold"),
   roleA=@GeneratedRole(
      name="lowPressShapeUnit",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theKETTryMold",
      type=ext.ket.project.trycondition.entity.KETTryMold.class,
      cardinality=Cardinality.ONE
   )
)

public class LowPressShapeUnitLink extends _LowPressShapeUnitLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static LowPressShapeUnitLink newLowPressShapeUnitLink( e3ps.common.code.NumberCode lowPressShapeUnit, ext.ket.project.trycondition.entity.KETTryMold theKETTryMold ) throws wt.util.WTException {
      LowPressShapeUnitLink LowPressShapeUnitLink_instance = new LowPressShapeUnitLink();
      LowPressShapeUnitLink_instance.initialize(lowPressShapeUnit, theKETTryMold);
      return LowPressShapeUnitLink_instance;
   }
}
