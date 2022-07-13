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
      name="subject",
      type=e3ps.groupware.company.Department.class,
      cardinality=Cardinality.ZERO_TO_ONE
   )
)

public class TheKETMeetingMinutesSubject extends _TheKETMeetingMinutesSubject implements Externalizable {
   static final long serialVersionUID = 1;
   public static TheKETMeetingMinutesSubject newTheKETMeetingMinutesSubject( e3ps.ecm.entity.KETMeetingMinutes theKETMeetingMinutes, e3ps.groupware.company.Department subject ) throws wt.util.WTException {
      TheKETMeetingMinutesSubject TheKETMeetingMinutesSubject_instance = new TheKETMeetingMinutesSubject();
      TheKETMeetingMinutesSubject_instance.initialize(theKETMeetingMinutes, subject);
      return TheKETMeetingMinutesSubject_instance;
   }
}
