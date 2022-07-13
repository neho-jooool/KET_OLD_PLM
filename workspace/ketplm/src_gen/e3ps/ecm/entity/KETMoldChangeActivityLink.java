package e3ps.ecm.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETMoldChangeActivity"),
   roleA=@GeneratedRole(
      name="moldECO",
      type=e3ps.ecm.entity.KETMoldChangeOrder.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="moldECA",
      type=e3ps.ecm.entity.KETMoldChangeActivity.class
   )
)

public class KETMoldChangeActivityLink extends _KETMoldChangeActivityLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static KETMoldChangeActivityLink newKETMoldChangeActivityLink( e3ps.ecm.entity.KETMoldChangeOrder moldECO, e3ps.ecm.entity.KETMoldChangeActivity moldECA ) throws wt.util.WTException {
      KETMoldChangeActivityLink KETMoldChangeActivityLink_instance = new KETMoldChangeActivityLink();
      KETMoldChangeActivityLink_instance.initialize(moldECO, moldECA);
      return KETMoldChangeActivityLink_instance;
   }
}
