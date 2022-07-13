package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="WorkProject"),
   roleA=@GeneratedRole(
      name="workType",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="theWorkProject",
      type=e3ps.project.WorkProject.class,
      cardinality=Cardinality.ONE
   )
)

public class WorkTypeTheWorkProject extends _WorkTypeTheWorkProject implements Externalizable {
   static final long serialVersionUID = 1;
   public static WorkTypeTheWorkProject newWorkTypeTheWorkProject( e3ps.common.code.NumberCode workType, e3ps.project.WorkProject theWorkProject ) throws wt.util.WTException {
      WorkTypeTheWorkProject WorkTypeTheWorkProject_instance = new WorkTypeTheWorkProject();
      WorkTypeTheWorkProject_instance.initialize(workType, theWorkProject);
      return WorkTypeTheWorkProject_instance;
   }
}
