package ext.ket.project.program.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETProgramSchedule"),
   roleA=@GeneratedRole(
      name="master",
      type=ext.ket.project.program.entity.KETProgramMaster.class,
      javaDoc="The master for an iteration. A master must exist for any iteration.   @see wt.vc.Mastered",
      supportedAPI=SupportedAPI.PUBLIC,
      autoNavigate=true,
      cardinality=Cardinality.ONE,
      accessors=@PropertyAccessors(
         getAccess=GetAccess.PRIVATE
      )
   ),
   roleB=@GeneratedRole(
      name="iteration",
      type=ext.ket.project.program.entity.KETProgramSchedule.class,
      javaDoc="The iterations for one master. Also, it is possible for a master to exist without any associated iterations.  @see wt.vc.Iterated",
      supportedAPI=SupportedAPI.PUBLIC,
      accessors=@PropertyAccessors(
         getAccess=GetAccess.PRIVATE,
         setAccess=SetAccess.PROTECTED
      )
   )
)

public class KETProgramScheduleLink extends _KETProgramScheduleLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static KETProgramScheduleLink newKETProgramScheduleLink( ext.ket.project.program.entity.KETProgramMaster master, ext.ket.project.program.entity.KETProgramSchedule iteration ) throws wt.util.WTException {
      KETProgramScheduleLink KETProgramScheduleLink_instance = new KETProgramScheduleLink();
      KETProgramScheduleLink_instance.initialize(master, iteration);
      return KETProgramScheduleLink_instance;
   }
}
