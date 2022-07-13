package ext.ket.arm.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETAnalysisRequestMaster"),
   roleA=@GeneratedRole(
      name="ketClientDepartment",
      type=e3ps.groupware.company.Department.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theKETAnalysisRequestMaster",
      type=ext.ket.arm.entity.KETAnalysisRequestMaster.class,
      cardinality=Cardinality.ONE
   )
)

public class KETClientDepartment extends _KETClientDepartment implements Externalizable {
   static final long serialVersionUID = 1;
   public static KETClientDepartment newKETClientDepartment( e3ps.groupware.company.Department ketClientDepartment, ext.ket.arm.entity.KETAnalysisRequestMaster theKETAnalysisRequestMaster ) throws wt.util.WTException {
      KETClientDepartment KETClientDepartment_instance = new KETClientDepartment();
      KETClientDepartment_instance.initialize(ketClientDepartment, theKETAnalysisRequestMaster);
      return KETClientDepartment_instance;
   }
}
