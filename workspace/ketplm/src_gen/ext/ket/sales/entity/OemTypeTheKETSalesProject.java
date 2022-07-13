package ext.ket.sales.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETSalesProject"),
   roleA=@GeneratedRole(
      name="oemType",
      type=e3ps.project.outputtype.OEMProjectType.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theKETSalesProject",
      type=ext.ket.sales.entity.KETSalesProject.class,
      cardinality=Cardinality.ONE
   )
)

public class OemTypeTheKETSalesProject extends _OemTypeTheKETSalesProject implements Externalizable {
   static final long serialVersionUID = 1;
   public static OemTypeTheKETSalesProject newOemTypeTheKETSalesProject( e3ps.project.outputtype.OEMProjectType oemType, ext.ket.sales.entity.KETSalesProject theKETSalesProject ) throws wt.util.WTException {
      OemTypeTheKETSalesProject OemTypeTheKETSalesProject_instance = new OemTypeTheKETSalesProject();
      OemTypeTheKETSalesProject_instance.initialize(oemType, theKETSalesProject);
      return OemTypeTheKETSalesProject_instance;
   }
}
