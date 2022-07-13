package ext.ket.project.program.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETProgramSchedule"),
   roleA=@GeneratedRole(
      name="programAdmin",
      type=wt.org.WTUser.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="program",
      type=ext.ket.project.program.entity.KETProgramSchedule.class,
      cardinality=Cardinality.ONE
   )
)

public class KETProgramWTUserLink extends _KETProgramWTUserLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static KETProgramWTUserLink newKETProgramWTUserLink( wt.org.WTUser programAdmin, ext.ket.project.program.entity.KETProgramSchedule program ) throws wt.util.WTException {
      KETProgramWTUserLink KETProgramWTUserLink_instance = new KETProgramWTUserLink();
      KETProgramWTUserLink_instance.initialize(programAdmin, program);
      return KETProgramWTUserLink_instance;
   }
}
