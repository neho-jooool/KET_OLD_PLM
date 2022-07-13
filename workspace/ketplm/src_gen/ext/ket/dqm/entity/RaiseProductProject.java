package ext.ket.dqm.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=true,
   tableProperties=@TableProperties(tableName="KETDqmRaise"),
   roleA=@GeneratedRole(
      name="raise",
      type=ext.ket.dqm.entity.KETDqmRaise.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="productProject",
      type=e3ps.project.ProductProject.class,
      cardinality=Cardinality.ZERO_TO_ONE
   )
)

public class RaiseProductProject extends _RaiseProductProject implements Externalizable {
   static final long serialVersionUID = 1;
   public static RaiseProductProject newRaiseProductProject( ext.ket.dqm.entity.KETDqmRaise raise, e3ps.project.ProductProject productProject ) throws wt.util.WTException {
      RaiseProductProject RaiseProductProject_instance = new RaiseProductProject();
      RaiseProductProject_instance.initialize(raise, productProject);
      return RaiseProductProject_instance;
   }
}
