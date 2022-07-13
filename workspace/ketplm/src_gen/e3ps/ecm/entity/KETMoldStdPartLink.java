package e3ps.ecm.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETMoldStdPartLine"),
   roleA=@GeneratedRole(
      name="doc",
      type=e3ps.ecm.entity.KETMoldECADocLink.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="part",
      type=e3ps.ecm.entity.KETMoldStdPartLine.class
   )
)

public class KETMoldStdPartLink extends _KETMoldStdPartLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static KETMoldStdPartLink newKETMoldStdPartLink( e3ps.ecm.entity.KETMoldECADocLink doc, e3ps.ecm.entity.KETMoldStdPartLine part ) throws wt.util.WTException {
      KETMoldStdPartLink KETMoldStdPartLink_instance = new KETMoldStdPartLink();
      KETMoldStdPartLink_instance.initialize(doc, part);
      return KETMoldStdPartLink_instance;
   }
}
