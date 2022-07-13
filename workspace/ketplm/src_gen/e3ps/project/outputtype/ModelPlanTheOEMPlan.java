package e3ps.project.outputtype;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="OEMPlan"),
   roleA=@GeneratedRole(
      name="modelPlan",
      type=e3ps.project.outputtype.ModelPlan.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="theOEMPlan",
      type=e3ps.project.outputtype.OEMPlan.class,
      cardinality=Cardinality.ONE
   )
)

public class ModelPlanTheOEMPlan extends _ModelPlanTheOEMPlan implements Externalizable {
   static final long serialVersionUID = 1;
   public static ModelPlanTheOEMPlan newModelPlanTheOEMPlan( e3ps.project.outputtype.ModelPlan modelPlan, e3ps.project.outputtype.OEMPlan theOEMPlan ) throws wt.util.WTException {
      ModelPlanTheOEMPlan ModelPlanTheOEMPlan_instance = new ModelPlanTheOEMPlan();
      ModelPlanTheOEMPlan_instance.initialize(modelPlan, theOEMPlan);
      return ModelPlanTheOEMPlan_instance;
   }
}
