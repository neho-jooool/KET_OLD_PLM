package e3ps.project.outputtype;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=true,
   tableProperties=@TableProperties(tableName="OEMPlan"),
   roleA=@GeneratedRole(
      name="theOEMPlan",
      type=e3ps.project.outputtype.OEMPlan.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="oemType",
      type=e3ps.project.outputtype.OEMProjectType.class,
      cardinality=Cardinality.ZERO_TO_ONE
   )
)

public class TheOEMPlanOemType extends _TheOEMPlanOemType implements Externalizable {
   static final long serialVersionUID = 1;
   public static TheOEMPlanOemType newTheOEMPlanOemType( e3ps.project.outputtype.OEMPlan theOEMPlan, e3ps.project.outputtype.OEMProjectType oemType ) throws wt.util.WTException {
      TheOEMPlanOemType TheOEMPlanOemType_instance = new TheOEMPlanOemType();
      TheOEMPlanOemType_instance.initialize(theOEMPlan, oemType);
      return TheOEMPlanOemType_instance;
   }
}
