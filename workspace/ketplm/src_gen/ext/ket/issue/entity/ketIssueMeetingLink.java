package ext.ket.issue.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETIssueMeetingList"),
   roleA=@GeneratedRole(
      name="meetingHeader",
      type=ext.ket.issue.entity.KETIssueMeetingHeader.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="meetingList",
      type=ext.ket.issue.entity.KETIssueMeetingList.class
   )
)

public class ketIssueMeetingLink extends _ketIssueMeetingLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static ketIssueMeetingLink newketIssueMeetingLink( ext.ket.issue.entity.KETIssueMeetingHeader meetingHeader, ext.ket.issue.entity.KETIssueMeetingList meetingList ) throws wt.util.WTException {
      ketIssueMeetingLink ketIssueMeetingLink_instance = new ketIssueMeetingLink();
      ketIssueMeetingLink_instance.initialize(meetingHeader, meetingList);
      return ketIssueMeetingLink_instance;
   }
}
