package e3ps.project.customerPlan;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="CustomerPlan"),
   roleA=@GeneratedRole(
      name="customer",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theCustomerPlan",
      type=e3ps.project.customerPlan.CustomerPlan.class,
      cardinality=Cardinality.ONE
   )
)

public class CustomerTheCustomerPlan extends _CustomerTheCustomerPlan implements Externalizable {
   static final long serialVersionUID = 1;
   public static CustomerTheCustomerPlan newCustomerTheCustomerPlan( e3ps.common.code.NumberCode customer, e3ps.project.customerPlan.CustomerPlan theCustomerPlan ) throws wt.util.WTException {
      CustomerTheCustomerPlan CustomerTheCustomerPlan_instance = new CustomerTheCustomerPlan();
      CustomerTheCustomerPlan_instance.initialize(customer, theCustomerPlan);
      return CustomerTheCustomerPlan_instance;
   }
}
