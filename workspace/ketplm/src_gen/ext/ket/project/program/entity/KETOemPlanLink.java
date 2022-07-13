package ext.ket.project.program.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETProgramEvent"),
   roleA=@GeneratedRole(
      name="oemPlan",
      type=e3ps.project.outputtype.OEMPlan.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="event",
      type=ext.ket.project.program.entity.KETProgramEvent.class,
      cardinality=Cardinality.ZERO_TO_ONE
   )
)

public class KETOemPlanLink extends _KETOemPlanLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static KETOemPlanLink newKETOemPlanLink( e3ps.project.outputtype.OEMPlan oemPlan, ext.ket.project.program.entity.KETProgramEvent event ) throws wt.util.WTException {
      KETOemPlanLink KETOemPlanLink_instance = new KETOemPlanLink();
      KETOemPlanLink_instance.initialize(oemPlan, event);
      return KETOemPlanLink_instance;
   }
}
