package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="TemplateProject"),
   roleA=@GeneratedRole(
      name="oemType",
      type=e3ps.project.outputtype.OEMProjectType.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="theTemplateProject",
      type=e3ps.project.TemplateProject.class,
      cardinality=Cardinality.ONE
   )
)

public class OemTypeTheTemplateProject extends _OemTypeTheTemplateProject implements Externalizable {
   static final long serialVersionUID = 1;
   public static OemTypeTheTemplateProject newOemTypeTheTemplateProject( e3ps.project.outputtype.OEMProjectType oemType, e3ps.project.TemplateProject theTemplateProject ) throws wt.util.WTException {
      OemTypeTheTemplateProject OemTypeTheTemplateProject_instance = new OemTypeTheTemplateProject();
      OemTypeTheTemplateProject_instance.initialize(oemType, theTemplateProject);
      return OemTypeTheTemplateProject_instance;
   }
}
