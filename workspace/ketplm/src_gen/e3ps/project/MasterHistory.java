package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="TemplateProject"),
   roleA=@GeneratedRole(
      name="master",
      type=e3ps.project.ProjectMaster.class,
      autoNavigate=true,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="history",
      type=e3ps.project.TemplateProject.class,
      cardinality=Cardinality.ONE_TO_MANY
   )
)

public class MasterHistory extends _MasterHistory implements Externalizable {
   static final long serialVersionUID = 1;
   public static MasterHistory newMasterHistory( e3ps.project.ProjectMaster master, e3ps.project.TemplateProject history ) throws wt.util.WTException {
      MasterHistory MasterHistory_instance = new MasterHistory();
      MasterHistory_instance.initialize(master, history);
      return MasterHistory_instance;
   }
}
