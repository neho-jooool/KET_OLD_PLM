package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="E3PSProject"),
   roleA=@GeneratedRole(
      name="manageProductType",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="project",
      type=e3ps.project.E3PSProject.class,
      cardinality=Cardinality.ONE
   )
)

public class manageProduct extends _manageProduct implements Externalizable {
   static final long serialVersionUID = 1;
   public static manageProduct newmanageProduct( e3ps.common.code.NumberCode manageProductType, e3ps.project.E3PSProject project ) throws wt.util.WTException {
      manageProduct manageProduct_instance = new manageProduct();
      manageProduct_instance.initialize(manageProductType, project);
      return manageProduct_instance;
   }
}
