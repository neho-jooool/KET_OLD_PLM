package ext.ket.sales.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETSalesCSMeetingManageLink"),
   roleA=@GeneratedRole(
      name="csMeetingApprove",
      type=ext.ket.sales.entity.KETSalesCSMeetingApproval.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="theKETSalesCSMeetingManageLink",
      type=ext.ket.sales.entity.KETSalesCSMeetingManageLink.class,
      cardinality=Cardinality.ONE
   )
)

public class CsMeetingApproveTheKETSalesCSMeetingManageLink extends _CsMeetingApproveTheKETSalesCSMeetingManageLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static CsMeetingApproveTheKETSalesCSMeetingManageLink newCsMeetingApproveTheKETSalesCSMeetingManageLink( ext.ket.sales.entity.KETSalesCSMeetingApproval csMeetingApprove, ext.ket.sales.entity.KETSalesCSMeetingManageLink theKETSalesCSMeetingManageLink ) throws wt.util.WTException {
      CsMeetingApproveTheKETSalesCSMeetingManageLink CsMeetingApproveTheKETSalesCSMeetingManageLink_instance = new CsMeetingApproveTheKETSalesCSMeetingManageLink();
      CsMeetingApproveTheKETSalesCSMeetingManageLink_instance.initialize(csMeetingApprove, theKETSalesCSMeetingManageLink);
      return CsMeetingApproveTheKETSalesCSMeetingManageLink_instance;
   }
}
