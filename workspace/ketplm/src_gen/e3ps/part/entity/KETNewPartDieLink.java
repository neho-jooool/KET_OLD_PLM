package e3ps.part.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETNewPartList"),
   roleA=@GeneratedRole(
      name="newdie",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="newpartlist",
      type=e3ps.part.entity.KETNewPartList.class,
      cardinality=Cardinality.ONE
   )
)

public class KETNewPartDieLink extends _KETNewPartDieLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static KETNewPartDieLink newKETNewPartDieLink( e3ps.common.code.NumberCode newdie, e3ps.part.entity.KETNewPartList newpartlist ) throws wt.util.WTException {
      KETNewPartDieLink KETNewPartDieLink_instance = new KETNewPartDieLink();
      KETNewPartDieLink_instance.initialize(newdie, newpartlist);
      return KETNewPartDieLink_instance;
   }
}
