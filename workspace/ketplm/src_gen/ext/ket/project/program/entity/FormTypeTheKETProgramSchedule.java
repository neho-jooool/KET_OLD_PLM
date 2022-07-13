package ext.ket.project.program.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETProgramSchedule"),
   roleA=@GeneratedRole(
      name="formType",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theKETProgramSchedule",
      type=ext.ket.project.program.entity.KETProgramSchedule.class,
      cardinality=Cardinality.ONE
   )
)

public class FormTypeTheKETProgramSchedule extends _FormTypeTheKETProgramSchedule implements Externalizable {
   static final long serialVersionUID = 1;
   public static FormTypeTheKETProgramSchedule newFormTypeTheKETProgramSchedule( e3ps.common.code.NumberCode formType, ext.ket.project.program.entity.KETProgramSchedule theKETProgramSchedule ) throws wt.util.WTException {
      FormTypeTheKETProgramSchedule FormTypeTheKETProgramSchedule_instance = new FormTypeTheKETProgramSchedule();
      FormTypeTheKETProgramSchedule_instance.initialize(formType, theKETProgramSchedule);
      return FormTypeTheKETProgramSchedule_instance;
   }
}
