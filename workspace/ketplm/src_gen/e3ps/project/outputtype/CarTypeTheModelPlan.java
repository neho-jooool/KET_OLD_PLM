package e3ps.project.outputtype;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="ModelPlan"),
   roleA=@GeneratedRole(
      name="carType",
      type=e3ps.project.outputtype.OEMProjectType.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theModelPlan",
      type=e3ps.project.outputtype.ModelPlan.class,
      cardinality=Cardinality.ONE
   )
)

public class CarTypeTheModelPlan extends _CarTypeTheModelPlan implements Externalizable {
   static final long serialVersionUID = 1;
   public static CarTypeTheModelPlan newCarTypeTheModelPlan( e3ps.project.outputtype.OEMProjectType carType, e3ps.project.outputtype.ModelPlan theModelPlan ) throws wt.util.WTException {
      CarTypeTheModelPlan CarTypeTheModelPlan_instance = new CarTypeTheModelPlan();
      CarTypeTheModelPlan_instance.initialize(carType, theModelPlan);
      return CarTypeTheModelPlan_instance;
   }
}
