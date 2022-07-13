package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="E3PSProject"),
   roleA=@GeneratedRole(
      name="business",
      type=wt.org.WTUser.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="theE3PSProject",
      type=e3ps.project.E3PSProject.class,
      cardinality=Cardinality.ONE
   )
)

public class BusinessTheE3PSProject extends _BusinessTheE3PSProject implements Externalizable {
   static final long serialVersionUID = 1;
   public static BusinessTheE3PSProject newBusinessTheE3PSProject( wt.org.WTUser business, e3ps.project.E3PSProject theE3PSProject ) throws wt.util.WTException {
      BusinessTheE3PSProject BusinessTheE3PSProject_instance = new BusinessTheE3PSProject();
      BusinessTheE3PSProject_instance.initialize(business, theE3PSProject);
      return BusinessTheE3PSProject_instance;
   }
}
