package ext.ket.sales.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETSalesCSMeetingManage"),
   roleA=@GeneratedRole(
      name="createUser",
      type=wt.org.WTUser.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theKETSalesCSMeetingManage",
      type=ext.ket.sales.entity.KETSalesCSMeetingManage.class,
      cardinality=Cardinality.ONE
   )
)

public class CreateUserTheKETSalesCSMeetingManage extends _CreateUserTheKETSalesCSMeetingManage implements Externalizable {
   static final long serialVersionUID = 1;
   public static CreateUserTheKETSalesCSMeetingManage newCreateUserTheKETSalesCSMeetingManage( wt.org.WTUser createUser, ext.ket.sales.entity.KETSalesCSMeetingManage theKETSalesCSMeetingManage ) throws wt.util.WTException {
      CreateUserTheKETSalesCSMeetingManage CreateUserTheKETSalesCSMeetingManage_instance = new CreateUserTheKETSalesCSMeetingManage();
      CreateUserTheKETSalesCSMeetingManage_instance.initialize(createUser, theKETSalesCSMeetingManage);
      return CreateUserTheKETSalesCSMeetingManage_instance;
   }
}
