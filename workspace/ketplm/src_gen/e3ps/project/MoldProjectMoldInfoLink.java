package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="MoldProject"),
   roleA=@GeneratedRole(
      name="moldInfo",
      type=e3ps.project.MoldItemInfo.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="moldProject",
      type=e3ps.project.MoldProject.class,
      cardinality=Cardinality.ONE
   )
)

public class MoldProjectMoldInfoLink extends _MoldProjectMoldInfoLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static MoldProjectMoldInfoLink newMoldProjectMoldInfoLink( e3ps.project.MoldItemInfo moldInfo, e3ps.project.MoldProject moldProject ) throws wt.util.WTException {
      MoldProjectMoldInfoLink MoldProjectMoldInfoLink_instance = new MoldProjectMoldInfoLink();
      MoldProjectMoldInfoLink_instance.initialize(moldInfo, moldProject);
      return MoldProjectMoldInfoLink_instance;
   }
}
