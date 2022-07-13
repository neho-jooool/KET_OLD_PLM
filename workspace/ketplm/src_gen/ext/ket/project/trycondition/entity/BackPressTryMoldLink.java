package ext.ket.project.trycondition.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETTryMold"),
   roleA=@GeneratedRole(
      name="backPressUnit",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theKETTryMold",
      type=ext.ket.project.trycondition.entity.KETTryMold.class,
      cardinality=Cardinality.ONE
   )
)

public class BackPressTryMoldLink extends _BackPressTryMoldLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static BackPressTryMoldLink newBackPressTryMoldLink( e3ps.common.code.NumberCode backPressUnit, ext.ket.project.trycondition.entity.KETTryMold theKETTryMold ) throws wt.util.WTException {
      BackPressTryMoldLink BackPressTryMoldLink_instance = new BackPressTryMoldLink();
      BackPressTryMoldLink_instance.initialize(backPressUnit, theKETTryMold);
      return BackPressTryMoldLink_instance;
   }
}
