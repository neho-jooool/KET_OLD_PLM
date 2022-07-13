package e3ps.project.trySchdule;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="TrySchdule"),
   roleA=@GeneratedRole(
      name="moldMaster",
      type=e3ps.project.E3PSProjectMaster.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="theTrySchdule",
      type=e3ps.project.trySchdule.TrySchdule.class,
      cardinality=Cardinality.ONE
   )
)

public class MoldMasterTheTrySchdule extends _MoldMasterTheTrySchdule implements Externalizable {
   static final long serialVersionUID = 1;
   public static MoldMasterTheTrySchdule newMoldMasterTheTrySchdule( e3ps.project.E3PSProjectMaster moldMaster, e3ps.project.trySchdule.TrySchdule theTrySchdule ) throws wt.util.WTException {
      MoldMasterTheTrySchdule MoldMasterTheTrySchdule_instance = new MoldMasterTheTrySchdule();
      MoldMasterTheTrySchdule_instance.initialize(moldMaster, theTrySchdule);
      return MoldMasterTheTrySchdule_instance;
   }
}
