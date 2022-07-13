package ext.ket.project.trycondition.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETTryCondition"),
   roleA=@GeneratedRole(
      name="moldProject",
      type=e3ps.project.MoldProject.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theKETTryCondition",
      type=ext.ket.project.trycondition.entity.KETTryCondition.class,
      cardinality=Cardinality.ONE
   )
)

public class KETTryConditionProjectLink extends _KETTryConditionProjectLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static KETTryConditionProjectLink newKETTryConditionProjectLink( e3ps.project.MoldProject moldProject, ext.ket.project.trycondition.entity.KETTryCondition theKETTryCondition ) throws wt.util.WTException {
      KETTryConditionProjectLink KETTryConditionProjectLink_instance = new KETTryConditionProjectLink();
      KETTryConditionProjectLink_instance.initialize(moldProject, theKETTryCondition);
      return KETTryConditionProjectLink_instance;
   }
}
