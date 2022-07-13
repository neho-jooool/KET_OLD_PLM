package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="E3PSTask"),
   roleA=@GeneratedRole(
      name="pointDivision",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="theE3PSTask",
      type=e3ps.project.E3PSTask.class
   )
)

public class PointDivisionTheE3PSTask extends _PointDivisionTheE3PSTask implements Externalizable {
   static final long serialVersionUID = 1;
   public static PointDivisionTheE3PSTask newPointDivisionTheE3PSTask( e3ps.common.code.NumberCode pointDivision, e3ps.project.E3PSTask theE3PSTask ) throws wt.util.WTException {
      PointDivisionTheE3PSTask PointDivisionTheE3PSTask_instance = new PointDivisionTheE3PSTask();
      PointDivisionTheE3PSTask_instance.initialize(pointDivision, theE3PSTask);
      return PointDivisionTheE3PSTask_instance;
   }
}
