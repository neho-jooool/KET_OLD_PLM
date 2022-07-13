package ext.ket.issue.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETIssueMaster"),
   roleA=@GeneratedRole(
      name="manager",
      type=wt.org.WTUser.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theKETIssueMaster",
      type=ext.ket.issue.entity.KETIssueMaster.class,
      cardinality=Cardinality.ONE
   )
)

public class ManagerTheKETIssueMaster extends _ManagerTheKETIssueMaster implements Externalizable {
   static final long serialVersionUID = 1;
   public static ManagerTheKETIssueMaster newManagerTheKETIssueMaster( wt.org.WTUser manager, ext.ket.issue.entity.KETIssueMaster theKETIssueMaster ) throws wt.util.WTException {
      ManagerTheKETIssueMaster ManagerTheKETIssueMaster_instance = new ManagerTheKETIssueMaster();
      ManagerTheKETIssueMaster_instance.initialize(manager, theKETIssueMaster);
      return ManagerTheKETIssueMaster_instance;
   }
}
