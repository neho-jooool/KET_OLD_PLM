package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=true,
   tableProperties=@TableProperties(tableName="E3PSProject"),
   roleA=@GeneratedRole(
      name="history",
      type=e3ps.project.E3PSProject.class,
      cardinality=Cardinality.ONE_TO_MANY
   ),
   roleB=@GeneratedRole(
      name="master",
      type=e3ps.project.E3PSProjectMaster.class,
      autoNavigate=true,
      cardinality=Cardinality.ONE
   )
)

public class MasterE3PSHistory extends _MasterE3PSHistory implements Externalizable {
   static final long serialVersionUID = 1;
   public static MasterE3PSHistory newMasterE3PSHistory( e3ps.project.E3PSProject history, e3ps.project.E3PSProjectMaster master ) throws wt.util.WTException {
      MasterE3PSHistory MasterE3PSHistory_instance = new MasterE3PSHistory();
      MasterE3PSHistory_instance.initialize(history, master);
      return MasterE3PSHistory_instance;
   }
}
