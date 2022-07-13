package e3ps.ecm.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETMoldChangePlan"),
   roleA=@GeneratedRole(
      name="moldECO",
      type=e3ps.ecm.entity.KETMoldChangeOrder.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="moldPlan",
      type=e3ps.ecm.entity.KETMoldChangePlan.class
   )
)

public class KETMoldECOPlanLink extends _KETMoldECOPlanLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static KETMoldECOPlanLink newKETMoldECOPlanLink( e3ps.ecm.entity.KETMoldChangeOrder moldECO, e3ps.ecm.entity.KETMoldChangePlan moldPlan ) throws wt.util.WTException {
      KETMoldECOPlanLink KETMoldECOPlanLink_instance = new KETMoldECOPlanLink();
      KETMoldECOPlanLink_instance.initialize(moldECO, moldPlan);
      return KETMoldECOPlanLink_instance;
   }
}
