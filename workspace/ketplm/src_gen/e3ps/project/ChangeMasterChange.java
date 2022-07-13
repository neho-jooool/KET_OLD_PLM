package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=true,
   tableProperties=@TableProperties(tableName="ProductHistoryChage"),
   roleA=@GeneratedRole(
      name="change",
      type=e3ps.project.ProductHistoryChage.class
   ),
   roleB=@GeneratedRole(
      name="masterChange",
      type=e3ps.project.ProjectMaster.class,
      cardinality=Cardinality.ONE
   )
)

public class ChangeMasterChange extends _ChangeMasterChange implements Externalizable {
   static final long serialVersionUID = 1;
   public static ChangeMasterChange newChangeMasterChange( e3ps.project.ProductHistoryChage change, e3ps.project.ProjectMaster masterChange ) throws wt.util.WTException {
      ChangeMasterChange ChangeMasterChange_instance = new ChangeMasterChange();
      ChangeMasterChange_instance.initialize(change, masterChange);
      return ChangeMasterChange_instance;
   }
}
