package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="E3PSProject"),
   roleA=@GeneratedRole(
      name="mainGoalType",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="project",
      type=e3ps.project.E3PSProject.class,
      cardinality=Cardinality.ONE
   )
)

public class goalOneLevel extends _goalOneLevel implements Externalizable {
   static final long serialVersionUID = 1;
   public static goalOneLevel newgoalOneLevel( e3ps.common.code.NumberCode mainGoalType, e3ps.project.E3PSProject project ) throws wt.util.WTException {
      goalOneLevel goalOneLevel_instance = new goalOneLevel();
      goalOneLevel_instance.initialize(mainGoalType, project);
      return goalOneLevel_instance;
   }
}
