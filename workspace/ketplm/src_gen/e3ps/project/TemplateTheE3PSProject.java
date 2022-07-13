package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="E3PSProject"),
   roleA=@GeneratedRole(
      name="template",
      type=e3ps.project.TemplateProject.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theE3PSProject",
      type=e3ps.project.E3PSProject.class,
      cardinality=Cardinality.ONE
   )
)

public class TemplateTheE3PSProject extends _TemplateTheE3PSProject implements Externalizable {
   static final long serialVersionUID = 1;
   public static TemplateTheE3PSProject newTemplateTheE3PSProject( e3ps.project.TemplateProject template, e3ps.project.E3PSProject theE3PSProject ) throws wt.util.WTException {
      TemplateTheE3PSProject TemplateTheE3PSProject_instance = new TemplateTheE3PSProject();
      TemplateTheE3PSProject_instance.initialize(template, theE3PSProject);
      return TemplateTheE3PSProject_instance;
   }
}
