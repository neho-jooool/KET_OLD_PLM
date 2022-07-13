package e3ps.ecm.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETMoldChangePlan"),
   roleA=@GeneratedRole(
      name="prodEcoOwner",
      type=wt.org.WTUser.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="theKETMoldChangePlan",
      type=e3ps.ecm.entity.KETMoldChangePlan.class,
      cardinality=Cardinality.ONE
   )
)

public class KETMoldPlanPEcoOwnerLink extends _KETMoldPlanPEcoOwnerLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static KETMoldPlanPEcoOwnerLink newKETMoldPlanPEcoOwnerLink( wt.org.WTUser prodEcoOwner, e3ps.ecm.entity.KETMoldChangePlan theKETMoldChangePlan ) throws wt.util.WTException {
      KETMoldPlanPEcoOwnerLink KETMoldPlanPEcoOwnerLink_instance = new KETMoldPlanPEcoOwnerLink();
      KETMoldPlanPEcoOwnerLink_instance.initialize(prodEcoOwner, theKETMoldChangePlan);
      return KETMoldPlanPEcoOwnerLink_instance;
   }
}
