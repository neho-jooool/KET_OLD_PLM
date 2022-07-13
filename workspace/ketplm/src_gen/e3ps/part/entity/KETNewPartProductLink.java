package e3ps.part.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETNewPartList"),
   roleA=@GeneratedRole(
      name="newproduct",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="newpartlist",
      type=e3ps.part.entity.KETNewPartList.class,
      cardinality=Cardinality.ONE
   )
)

public class KETNewPartProductLink extends _KETNewPartProductLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static KETNewPartProductLink newKETNewPartProductLink( e3ps.common.code.NumberCode newproduct, e3ps.part.entity.KETNewPartList newpartlist ) throws wt.util.WTException {
      KETNewPartProductLink KETNewPartProductLink_instance = new KETNewPartProductLink();
      KETNewPartProductLink_instance.initialize(newproduct, newpartlist);
      return KETNewPartProductLink_instance;
   }
}
