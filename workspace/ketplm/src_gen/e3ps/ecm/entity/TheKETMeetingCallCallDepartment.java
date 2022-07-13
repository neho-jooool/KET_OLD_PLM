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
      name="callDepartment",
      type=e3ps.groupware.company.Department.class,
      cardinality=Cardinality.ZERO_TO_ONE
   )
)

public class TheKETMeetingCallCallDepartment extends _TheKETMeetingCallCallDepartment implements Externalizable {
   static final long serialVersionUID = 1;
   public static TheKETMeetingCallCallDepartment newTheKETMeetingCallCallDepartment( e3ps.ecm.entity.KETMeetingCall theKETMeetingCall, e3ps.groupware.company.Department callDepartment ) throws wt.util.WTException {
      TheKETMeetingCallCallDepartment TheKETMeetingCallCallDepartment_instance = new TheKETMeetingCallCallDepartment();
      TheKETMeetingCallCallDepartment_instance.initialize(theKETMeetingCall, callDepartment);
      return TheKETMeetingCallCallDepartment_instance;
   }
}
