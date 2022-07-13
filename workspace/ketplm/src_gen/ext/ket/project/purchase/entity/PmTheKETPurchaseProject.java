package ext.ket.project.purchase.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETPurchaseProject"),
   roleA=@GeneratedRole(
      name="pm",
      type=wt.org.WTUser.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theKETPurchaseProject",
      type=ext.ket.project.purchase.entity.KETPurchaseProject.class,
      cardinality=Cardinality.ONE
   )
)

public class PmTheKETPurchaseProject extends _PmTheKETPurchaseProject implements Externalizable {
   static final long serialVersionUID = 1;
   public static PmTheKETPurchaseProject newPmTheKETPurchaseProject( wt.org.WTUser pm, ext.ket.project.purchase.entity.KETPurchaseProject theKETPurchaseProject ) throws wt.util.WTException {
      PmTheKETPurchaseProject PmTheKETPurchaseProject_instance = new PmTheKETPurchaseProject();
      PmTheKETPurchaseProject_instance.initialize(pm, theKETPurchaseProject);
      return PmTheKETPurchaseProject_instance;
   }
}
