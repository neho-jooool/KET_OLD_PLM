package e3ps.ecm.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETMoldChangePlan"),
   roleA=@GeneratedRole(
      name="prodECO",
      type=e3ps.ecm.entity.KETProdChangeOrder.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="moldPlan",
      type=e3ps.ecm.entity.KETMoldChangePlan.class
   )
)

public class KETProdECOPlanLink extends _KETProdECOPlanLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static KETProdECOPlanLink newKETProdECOPlanLink( e3ps.ecm.entity.KETProdChangeOrder prodECO, e3ps.ecm.entity.KETMoldChangePlan moldPlan ) throws wt.util.WTException {
      KETProdECOPlanLink KETProdECOPlanLink_instance = new KETProdECOPlanLink();
      KETProdECOPlanLink_instance.initialize(prodECO, moldPlan);
      return KETProdECOPlanLink_instance;
   }
}
