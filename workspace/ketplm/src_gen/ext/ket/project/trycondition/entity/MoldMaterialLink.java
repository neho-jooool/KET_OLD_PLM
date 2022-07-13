package ext.ket.project.trycondition.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETTryPress"),
   roleA=@GeneratedRole(
      name="material",
      type=e3ps.project.material.MoldMaterial.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="tryPress",
      type=ext.ket.project.trycondition.entity.KETTryPress.class,
      cardinality=Cardinality.ONE
   )
)

public class MoldMaterialLink extends _MoldMaterialLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static MoldMaterialLink newMoldMaterialLink( e3ps.project.material.MoldMaterial material, ext.ket.project.trycondition.entity.KETTryPress tryPress ) throws wt.util.WTException {
      MoldMaterialLink MoldMaterialLink_instance = new MoldMaterialLink();
      MoldMaterialLink_instance.initialize(material, tryPress);
      return MoldMaterialLink_instance;
   }
}
