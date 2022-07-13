package e3ps.part.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETNewPartList"),
   roleA=@GeneratedRole(
      name="newparttype",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="newpartlist",
      type=e3ps.part.entity.KETNewPartList.class,
      cardinality=Cardinality.ONE
   )
)

public class KETNewPartListTypeLink extends _KETNewPartListTypeLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static KETNewPartListTypeLink newKETNewPartListTypeLink( e3ps.common.code.NumberCode newparttype, e3ps.part.entity.KETNewPartList newpartlist ) throws wt.util.WTException {
      KETNewPartListTypeLink KETNewPartListTypeLink_instance = new KETNewPartListTypeLink();
      KETNewPartListTypeLink_instance.initialize(newparttype, newpartlist);
      return KETNewPartListTypeLink_instance;
   }
}
