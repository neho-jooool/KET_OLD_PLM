package e3ps.project.outputtype;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="ModelPlan"),
   roleA=@GeneratedRole(
      name="formType",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theModelPlan",
      type=e3ps.project.outputtype.ModelPlan.class,
      cardinality=Cardinality.ONE
   )
)

public class FormTypeTheModelPlan extends _FormTypeTheModelPlan implements Externalizable {
   static final long serialVersionUID = 1;
   public static FormTypeTheModelPlan newFormTypeTheModelPlan( e3ps.common.code.NumberCode formType, e3ps.project.outputtype.ModelPlan theModelPlan ) throws wt.util.WTException {
      FormTypeTheModelPlan FormTypeTheModelPlan_instance = new FormTypeTheModelPlan();
      FormTypeTheModelPlan_instance.initialize(formType, theModelPlan);
      return FormTypeTheModelPlan_instance;
   }
}
