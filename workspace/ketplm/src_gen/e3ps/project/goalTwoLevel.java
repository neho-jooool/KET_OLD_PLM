package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="E3PSProject"),
   roleA=@GeneratedRole(
      name="subGoalType",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="project",
      type=e3ps.project.E3PSProject.class,
      cardinality=Cardinality.ONE
   )
)

public class goalTwoLevel extends _goalTwoLevel implements Externalizable {
   static final long serialVersionUID = 1;
   public static goalTwoLevel newgoalTwoLevel( e3ps.common.code.NumberCode subGoalType, e3ps.project.E3PSProject project ) throws wt.util.WTException {
      goalTwoLevel goalTwoLevel_instance = new goalTwoLevel();
      goalTwoLevel_instance.initialize(subGoalType, project);
      return goalTwoLevel_instance;
   }
}
