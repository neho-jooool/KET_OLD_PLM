package e3ps.ecm.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=true,
   tableProperties=@TableProperties(tableName="KETMeetingCall"),
   roleA=@GeneratedRole(
      name="theKETMeetingCall",
      type=e3ps.ecm.entity.KETMeetingCall.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="callAttendance",
      type=wt.org.WTUser.class,
      cardinality=Cardinality.ZERO_TO_ONE
   )
)

public class TheKETMeetingCallCallAttendance extends _TheKETMeetingCallCallAttendance implements Externalizable {
   static final long serialVersionUID = 1;
   public static TheKETMeetingCallCallAttendance newTheKETMeetingCallCallAttendance( e3ps.ecm.entity.KETMeetingCall theKETMeetingCall, wt.org.WTUser callAttendance ) throws wt.util.WTException {
      TheKETMeetingCallCallAttendance TheKETMeetingCallCallAttendance_instance = new TheKETMeetingCallCallAttendance();
      TheKETMeetingCallCallAttendance_instance.initialize(theKETMeetingCall, callAttendance);
      return TheKETMeetingCallCallAttendance_instance;
   }
}
