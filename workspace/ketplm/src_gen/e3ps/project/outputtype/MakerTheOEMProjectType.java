package e3ps.project.outputtype;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="OEMProjectType"),
   roleA=@GeneratedRole(
      name="maker",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theOEMProjectType",
      type=e3ps.project.outputtype.OEMProjectType.class,
      cardinality=Cardinality.ONE
   )
)

public class MakerTheOEMProjectType extends _MakerTheOEMProjectType implements Externalizable {
   static final long serialVersionUID = 1;
   public static MakerTheOEMProjectType newMakerTheOEMProjectType( e3ps.common.code.NumberCode maker, e3ps.project.outputtype.OEMProjectType theOEMProjectType ) throws wt.util.WTException {
      MakerTheOEMProjectType MakerTheOEMProjectType_instance = new MakerTheOEMProjectType();
      MakerTheOEMProjectType_instance.initialize(maker, theOEMProjectType);
      return MakerTheOEMProjectType_instance;
   }
}
