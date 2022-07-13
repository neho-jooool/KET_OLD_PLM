package ext.ket.project.trycondition.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETTryMold"),
   roleA=@GeneratedRole(
      name="injectPressUnit",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theKETTryMold",
      type=ext.ket.project.trycondition.entity.KETTryMold.class,
      cardinality=Cardinality.ONE
   )
)

public class InjectPressUnitLink extends _InjectPressUnitLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static InjectPressUnitLink newInjectPressUnitLink( e3ps.common.code.NumberCode injectPressUnit, ext.ket.project.trycondition.entity.KETTryMold theKETTryMold ) throws wt.util.WTException {
      InjectPressUnitLink InjectPressUnitLink_instance = new InjectPressUnitLink();
      InjectPressUnitLink_instance.initialize(injectPressUnit, theKETTryMold);
      return InjectPressUnitLink_instance;
   }
}
