package ext.ket.project.trycondition.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=true,
   tableProperties=@TableProperties(tableName="KETTryPress"),
   roleA=@GeneratedRole(
      name="tryHistory",
      type=ext.ket.project.trycondition.entity.KETTryPress.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="moldStruc",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ONE
   )
)

public class MoldStrucTryPressLink extends _MoldStrucTryPressLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static MoldStrucTryPressLink newMoldStrucTryPressLink( ext.ket.project.trycondition.entity.KETTryPress tryHistory, e3ps.common.code.NumberCode moldStruc ) throws wt.util.WTException {
      MoldStrucTryPressLink MoldStrucTryPressLink_instance = new MoldStrucTryPressLink();
      MoldStrucTryPressLink_instance.initialize(tryHistory, moldStruc);
      return MoldStrucTryPressLink_instance;
   }
}
