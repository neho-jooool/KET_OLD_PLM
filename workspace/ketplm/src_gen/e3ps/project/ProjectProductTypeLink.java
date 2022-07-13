package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="E3PSProject"),
   roleA=@GeneratedRole(
      name="productType",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="project",
      type=e3ps.project.E3PSProject.class,
      cardinality=Cardinality.ONE
   )
)

public class ProjectProductTypeLink extends _ProjectProductTypeLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static ProjectProductTypeLink newProjectProductTypeLink( e3ps.common.code.NumberCode productType, e3ps.project.E3PSProject project ) throws wt.util.WTException {
      ProjectProductTypeLink ProjectProductTypeLink_instance = new ProjectProductTypeLink();
      ProjectProductTypeLink_instance.initialize(productType, project);
      return ProjectProductTypeLink_instance;
   }
}
