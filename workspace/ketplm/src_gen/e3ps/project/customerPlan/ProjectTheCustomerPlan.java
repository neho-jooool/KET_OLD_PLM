package e3ps.project.customerPlan;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="CustomerPlan"),
   roleA=@GeneratedRole(
      name="project",
      type=e3ps.project.E3PSProject.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theCustomerPlan",
      type=e3ps.project.customerPlan.CustomerPlan.class,
      cardinality=Cardinality.ONE
   )
)

public class ProjectTheCustomerPlan extends _ProjectTheCustomerPlan implements Externalizable {
   static final long serialVersionUID = 1;
   public static ProjectTheCustomerPlan newProjectTheCustomerPlan( e3ps.project.E3PSProject project, e3ps.project.customerPlan.CustomerPlan theCustomerPlan ) throws wt.util.WTException {
      ProjectTheCustomerPlan ProjectTheCustomerPlan_instance = new ProjectTheCustomerPlan();
      ProjectTheCustomerPlan_instance.initialize(project, theCustomerPlan);
      return ProjectTheCustomerPlan_instance;
   }
}
