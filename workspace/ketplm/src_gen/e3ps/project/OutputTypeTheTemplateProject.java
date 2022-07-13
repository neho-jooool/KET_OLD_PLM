package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="TemplateProject"),
   roleA=@GeneratedRole(
      name="outputType",
      type=e3ps.project.outputtype.ProjectOutPutType.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theTemplateProject",
      type=e3ps.project.TemplateProject.class,
      cardinality=Cardinality.ONE
   )
)

public class OutputTypeTheTemplateProject extends _OutputTypeTheTemplateProject implements Externalizable {
   static final long serialVersionUID = 1;
   public static OutputTypeTheTemplateProject newOutputTypeTheTemplateProject( e3ps.project.outputtype.ProjectOutPutType outputType, e3ps.project.TemplateProject theTemplateProject ) throws wt.util.WTException {
      OutputTypeTheTemplateProject OutputTypeTheTemplateProject_instance = new OutputTypeTheTemplateProject();
      OutputTypeTheTemplateProject_instance.initialize(outputType, theTemplateProject);
      return OutputTypeTheTemplateProject_instance;
   }
}
