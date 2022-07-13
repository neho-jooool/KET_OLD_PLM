package ext.ket.arm.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETAnalysisRequestMaster"),
   roleA=@GeneratedRole(
      name="ketChargeDepartment",
      type=e3ps.groupware.company.Department.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theKETAnalysisRequestMaster",
      type=ext.ket.arm.entity.KETAnalysisRequestMaster.class,
      cardinality=Cardinality.ONE
   )
)

public class KETChargeDepartment extends _KETChargeDepartment implements Externalizable {
   static final long serialVersionUID = 1;
   public static KETChargeDepartment newKETChargeDepartment( e3ps.groupware.company.Department ketChargeDepartment, ext.ket.arm.entity.KETAnalysisRequestMaster theKETAnalysisRequestMaster ) throws wt.util.WTException {
      KETChargeDepartment KETChargeDepartment_instance = new KETChargeDepartment();
      KETChargeDepartment_instance.initialize(ketChargeDepartment, theKETAnalysisRequestMaster);
      return KETChargeDepartment_instance;
   }
}
