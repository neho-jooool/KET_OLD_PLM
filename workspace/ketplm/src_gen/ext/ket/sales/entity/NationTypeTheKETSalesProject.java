package ext.ket.sales.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETSalesProject"),
   roleA=@GeneratedRole(
      name="nationType",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="theKETSalesProject",
      type=ext.ket.sales.entity.KETSalesProject.class,
      cardinality=Cardinality.ONE
   )
)

public class NationTypeTheKETSalesProject extends _NationTypeTheKETSalesProject implements Externalizable {
   static final long serialVersionUID = 1;
   public static NationTypeTheKETSalesProject newNationTypeTheKETSalesProject( e3ps.common.code.NumberCode nationType, ext.ket.sales.entity.KETSalesProject theKETSalesProject ) throws wt.util.WTException {
      NationTypeTheKETSalesProject NationTypeTheKETSalesProject_instance = new NationTypeTheKETSalesProject();
      NationTypeTheKETSalesProject_instance.initialize(nationType, theKETSalesProject);
      return NationTypeTheKETSalesProject_instance;
   }
}
