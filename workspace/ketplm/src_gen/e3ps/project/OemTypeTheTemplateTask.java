package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="TemplateTask"),
   roleA=@GeneratedRole(
      name="oemType",
      type=e3ps.project.outputtype.OEMProjectType.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="theTemplateTask",
      type=e3ps.project.TemplateTask.class,
      cardinality=Cardinality.ONE
   )
)

public class OemTypeTheTemplateTask extends _OemTypeTheTemplateTask implements Externalizable {
   static final long serialVersionUID = 1;
   public static OemTypeTheTemplateTask newOemTypeTheTemplateTask( e3ps.project.outputtype.OEMProjectType oemType, e3ps.project.TemplateTask theTemplateTask ) throws wt.util.WTException {
      OemTypeTheTemplateTask OemTypeTheTemplateTask_instance = new OemTypeTheTemplateTask();
      OemTypeTheTemplateTask_instance.initialize(oemType, theTemplateTask);
      return OemTypeTheTemplateTask_instance;
   }
}
