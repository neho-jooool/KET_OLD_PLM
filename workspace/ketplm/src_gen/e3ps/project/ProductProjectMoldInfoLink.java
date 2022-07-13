package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="MoldItemInfo"),
   roleA=@GeneratedRole(
      name="project",
      type=e3ps.project.ProductProject.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="moldInfo",
      type=e3ps.project.MoldItemInfo.class,
      cardinality=Cardinality.ONE
   )
)

public class ProductProjectMoldInfoLink extends _ProductProjectMoldInfoLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static ProductProjectMoldInfoLink newProductProjectMoldInfoLink( e3ps.project.ProductProject project, e3ps.project.MoldItemInfo moldInfo ) throws wt.util.WTException {
      ProductProjectMoldInfoLink ProductProjectMoldInfoLink_instance = new ProductProjectMoldInfoLink();
      ProductProjectMoldInfoLink_instance.initialize(project, moldInfo);
      return ProductProjectMoldInfoLink_instance;
   }
}
