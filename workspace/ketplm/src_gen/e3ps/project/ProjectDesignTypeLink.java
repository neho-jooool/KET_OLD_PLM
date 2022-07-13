package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="E3PSProject"),
   roleA=@GeneratedRole(
      name="designType",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="project",
      type=e3ps.project.E3PSProject.class,
      cardinality=Cardinality.ONE
   )
)

public class ProjectDesignTypeLink extends _ProjectDesignTypeLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static ProjectDesignTypeLink newProjectDesignTypeLink( e3ps.common.code.NumberCode designType, e3ps.project.E3PSProject project ) throws wt.util.WTException {
      ProjectDesignTypeLink ProjectDesignTypeLink_instance = new ProjectDesignTypeLink();
      ProjectDesignTypeLink_instance.initialize(designType, project);
      return ProjectDesignTypeLink_instance;
   }
}
