package e3ps.ecm.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETMoldChangePlan"),
   roleA=@GeneratedRole(
      name="moldEcoOwner",
      type=wt.org.WTUser.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="theKETMoldChangePlan",
      type=e3ps.ecm.entity.KETMoldChangePlan.class,
      cardinality=Cardinality.ONE
   )
)

public class KETMoldPlanMEcoOwnerLink extends _KETMoldPlanMEcoOwnerLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static KETMoldPlanMEcoOwnerLink newKETMoldPlanMEcoOwnerLink( wt.org.WTUser moldEcoOwner, e3ps.ecm.entity.KETMoldChangePlan theKETMoldChangePlan ) throws wt.util.WTException {
      KETMoldPlanMEcoOwnerLink KETMoldPlanMEcoOwnerLink_instance = new KETMoldPlanMEcoOwnerLink();
      KETMoldPlanMEcoOwnerLink_instance.initialize(moldEcoOwner, theKETMoldChangePlan);
      return KETMoldPlanMEcoOwnerLink_instance;
   }
}
