package ext.ket.project.purchase.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETPurchaseProject"),
   roleA=@GeneratedRole(
      name="createUser",
      type=wt.org.WTUser.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theKETPurchaseProject",
      type=ext.ket.project.purchase.entity.KETPurchaseProject.class,
      cardinality=Cardinality.ONE
   )
)

public class CreateUserTheKETPurchaseProject extends _CreateUserTheKETPurchaseProject implements Externalizable {
   static final long serialVersionUID = 1;
   public static CreateUserTheKETPurchaseProject newCreateUserTheKETPurchaseProject( wt.org.WTUser createUser, ext.ket.project.purchase.entity.KETPurchaseProject theKETPurchaseProject ) throws wt.util.WTException {
      CreateUserTheKETPurchaseProject CreateUserTheKETPurchaseProject_instance = new CreateUserTheKETPurchaseProject();
      CreateUserTheKETPurchaseProject_instance.initialize(createUser, theKETPurchaseProject);
      return CreateUserTheKETPurchaseProject_instance;
   }
}
