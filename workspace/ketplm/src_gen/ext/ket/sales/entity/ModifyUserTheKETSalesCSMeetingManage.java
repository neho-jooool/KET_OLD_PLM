package ext.ket.sales.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETSalesCSMeetingManage"),
   roleA=@GeneratedRole(
      name="modifyUser",
      type=wt.org.WTUser.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theKETSalesCSMeetingManage",
      type=ext.ket.sales.entity.KETSalesCSMeetingManage.class,
      cardinality=Cardinality.ONE
   )
)

public class ModifyUserTheKETSalesCSMeetingManage extends _ModifyUserTheKETSalesCSMeetingManage implements Externalizable {
   static final long serialVersionUID = 1;
   public static ModifyUserTheKETSalesCSMeetingManage newModifyUserTheKETSalesCSMeetingManage( wt.org.WTUser modifyUser, ext.ket.sales.entity.KETSalesCSMeetingManage theKETSalesCSMeetingManage ) throws wt.util.WTException {
      ModifyUserTheKETSalesCSMeetingManage ModifyUserTheKETSalesCSMeetingManage_instance = new ModifyUserTheKETSalesCSMeetingManage();
      ModifyUserTheKETSalesCSMeetingManage_instance.initialize(modifyUser, theKETSalesCSMeetingManage);
      return ModifyUserTheKETSalesCSMeetingManage_instance;
   }
}
