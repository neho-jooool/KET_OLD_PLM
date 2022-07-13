package ext.ket.project.trycondition.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETTryMold"),
   roleA=@GeneratedRole(
      name="mountThickness",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theKETTryMold",
      type=ext.ket.project.trycondition.entity.KETTryMold.class,
      cardinality=Cardinality.ONE
   )
)

public class MountingThicknessLink extends _MountingThicknessLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static MountingThicknessLink newMountingThicknessLink( e3ps.common.code.NumberCode mountThickness, ext.ket.project.trycondition.entity.KETTryMold theKETTryMold ) throws wt.util.WTException {
      MountingThicknessLink MountingThicknessLink_instance = new MountingThicknessLink();
      MountingThicknessLink_instance.initialize(mountThickness, theKETTryMold);
      return MountingThicknessLink_instance;
   }
}
