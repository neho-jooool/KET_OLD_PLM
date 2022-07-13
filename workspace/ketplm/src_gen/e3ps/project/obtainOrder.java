package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="E3PSProject"),
   roleA=@GeneratedRole(
      name="obtainType",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="project",
      type=e3ps.project.E3PSProject.class,
      cardinality=Cardinality.ONE
   )
)

public class obtainOrder extends _obtainOrder implements Externalizable {
   static final long serialVersionUID = 1;
   public static obtainOrder newobtainOrder( e3ps.common.code.NumberCode obtainType, e3ps.project.E3PSProject project ) throws wt.util.WTException {
      obtainOrder obtainOrder_instance = new obtainOrder();
      obtainOrder_instance.initialize(obtainType, project);
      return obtainOrder_instance;
   }
}
