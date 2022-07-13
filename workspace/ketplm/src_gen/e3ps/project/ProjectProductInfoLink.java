package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="ProductInfo"),
   roleA=@GeneratedRole(
      name="project",
      type=e3ps.project.E3PSProject.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="productInfo",
      type=e3ps.project.ProductInfo.class,
      cardinality=Cardinality.ONE
   )
)

public class ProjectProductInfoLink extends _ProjectProductInfoLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static ProjectProductInfoLink newProjectProductInfoLink( e3ps.project.E3PSProject project, e3ps.project.ProductInfo productInfo ) throws wt.util.WTException {
      ProjectProductInfoLink ProjectProductInfoLink_instance = new ProjectProductInfoLink();
      ProjectProductInfoLink_instance.initialize(project, productInfo);
      return ProjectProductInfoLink_instance;
   }
}
