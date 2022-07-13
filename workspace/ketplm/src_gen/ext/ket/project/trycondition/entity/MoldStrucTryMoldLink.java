package ext.ket.project.trycondition.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETTryMold"),
   roleA=@GeneratedRole(
      name="moldStruc",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theKETTryMold",
      type=ext.ket.project.trycondition.entity.KETTryMold.class,
      cardinality=Cardinality.ONE
   )
)

public class MoldStrucTryMoldLink extends _MoldStrucTryMoldLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static MoldStrucTryMoldLink newMoldStrucTryMoldLink( e3ps.common.code.NumberCode moldStruc, ext.ket.project.trycondition.entity.KETTryMold theKETTryMold ) throws wt.util.WTException {
      MoldStrucTryMoldLink MoldStrucTryMoldLink_instance = new MoldStrucTryMoldLink();
      MoldStrucTryMoldLink_instance.initialize(moldStruc, theKETTryMold);
      return MoldStrucTryMoldLink_instance;
   }
}
