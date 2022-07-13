package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="E3PSProject"),
   roleA=@GeneratedRole(
      name="devRequest",
      type=e3ps.dms.entity.KETDevelopmentRequest.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="theE3PSProject",
      type=e3ps.project.E3PSProject.class,
      cardinality=Cardinality.ONE
   )
)

public class DevRequestTheE3PSProject extends _DevRequestTheE3PSProject implements Externalizable {
   static final long serialVersionUID = 1;
   public static DevRequestTheE3PSProject newDevRequestTheE3PSProject( e3ps.dms.entity.KETDevelopmentRequest devRequest, e3ps.project.E3PSProject theE3PSProject ) throws wt.util.WTException {
      DevRequestTheE3PSProject DevRequestTheE3PSProject_instance = new DevRequestTheE3PSProject();
      DevRequestTheE3PSProject_instance.initialize(devRequest, theE3PSProject);
      return DevRequestTheE3PSProject_instance;
   }
}
