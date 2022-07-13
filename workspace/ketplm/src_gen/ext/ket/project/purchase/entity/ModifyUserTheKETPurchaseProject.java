package ext.ket.project.purchase.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETPurchaseProject"),
   roleA=@GeneratedRole(
      name="modifyUser",
      type=wt.org.WTUser.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theKETPurchaseProject",
      type=ext.ket.project.purchase.entity.KETPurchaseProject.class,
      cardinality=Cardinality.ONE
   )
)

public class ModifyUserTheKETPurchaseProject extends _ModifyUserTheKETPurchaseProject implements Externalizable {
   static final long serialVersionUID = 1;
   public static ModifyUserTheKETPurchaseProject newModifyUserTheKETPurchaseProject( wt.org.WTUser modifyUser, ext.ket.project.purchase.entity.KETPurchaseProject theKETPurchaseProject ) throws wt.util.WTException {
      ModifyUserTheKETPurchaseProject ModifyUserTheKETPurchaseProject_instance = new ModifyUserTheKETPurchaseProject();
      ModifyUserTheKETPurchaseProject_instance.initialize(modifyUser, theKETPurchaseProject);
      return ModifyUserTheKETPurchaseProject_instance;
   }
}
