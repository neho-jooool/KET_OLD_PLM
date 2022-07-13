package ext.ket.project.program.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETProgramSchedule"),
   roleA=@GeneratedRole(
      name="subContractor",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theKETProgramSchedule",
      type=ext.ket.project.program.entity.KETProgramSchedule.class,
      cardinality=Cardinality.ONE
   )
)

public class SubContractorTheKETProgramSchedule extends _SubContractorTheKETProgramSchedule implements Externalizable {
   static final long serialVersionUID = 1;
   public static SubContractorTheKETProgramSchedule newSubContractorTheKETProgramSchedule( e3ps.common.code.NumberCode subContractor, ext.ket.project.program.entity.KETProgramSchedule theKETProgramSchedule ) throws wt.util.WTException {
      SubContractorTheKETProgramSchedule SubContractorTheKETProgramSchedule_instance = new SubContractorTheKETProgramSchedule();
      SubContractorTheKETProgramSchedule_instance.initialize(subContractor, theKETProgramSchedule);
      return SubContractorTheKETProgramSchedule_instance;
   }
}
