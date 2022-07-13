package e3ps.ecm.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=true,
   tableProperties=@TableProperties(tableName="KETMeetingMinutes"),
   roleA=@GeneratedRole(
      name="theKETMeetingMinutes",
      type=e3ps.ecm.entity.KETMeetingMinutes.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="charge",
      type=wt.org.WTUser.class,
      cardinality=Cardinality.ZERO_TO_ONE
   )
)

public class TheKETMeetingMinutesCharge extends _TheKETMeetingMinutesCharge implements Externalizable {
   static final long serialVersionUID = 1;
   public static TheKETMeetingMinutesCharge newTheKETMeetingMinutesCharge( e3ps.ecm.entity.KETMeetingMinutes theKETMeetingMinutes, wt.org.WTUser charge ) throws wt.util.WTException {
      TheKETMeetingMinutesCharge TheKETMeetingMinutesCharge_instance = new TheKETMeetingMinutesCharge();
      TheKETMeetingMinutesCharge_instance.initialize(theKETMeetingMinutes, charge);
      return TheKETMeetingMinutesCharge_instance;
   }
}
