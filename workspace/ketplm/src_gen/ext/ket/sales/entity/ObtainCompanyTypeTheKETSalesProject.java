package ext.ket.sales.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETSalesProject"),
   roleA=@GeneratedRole(
      name="obtainCompanyType",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="theKETSalesProject",
      type=ext.ket.sales.entity.KETSalesProject.class,
      cardinality=Cardinality.ONE
   )
)

public class ObtainCompanyTypeTheKETSalesProject extends _ObtainCompanyTypeTheKETSalesProject implements Externalizable {
   static final long serialVersionUID = 1;
   public static ObtainCompanyTypeTheKETSalesProject newObtainCompanyTypeTheKETSalesProject( e3ps.common.code.NumberCode obtainCompanyType, ext.ket.sales.entity.KETSalesProject theKETSalesProject ) throws wt.util.WTException {
      ObtainCompanyTypeTheKETSalesProject ObtainCompanyTypeTheKETSalesProject_instance = new ObtainCompanyTypeTheKETSalesProject();
      ObtainCompanyTypeTheKETSalesProject_instance.initialize(obtainCompanyType, theKETSalesProject);
      return ObtainCompanyTypeTheKETSalesProject_instance;
   }
}
