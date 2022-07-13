package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="MoldItemInfo"),
   roleA=@GeneratedRole(
      name="material",
      type=e3ps.project.material.MoldMaterial.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="moldItem",
      type=e3ps.project.MoldItemInfo.class,
      cardinality=Cardinality.ONE
   )
)

public class MoldItemMaterialLink extends _MoldItemMaterialLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static MoldItemMaterialLink newMoldItemMaterialLink( e3ps.project.material.MoldMaterial material, e3ps.project.MoldItemInfo moldItem ) throws wt.util.WTException {
      MoldItemMaterialLink MoldItemMaterialLink_instance = new MoldItemMaterialLink();
      MoldItemMaterialLink_instance.initialize(material, moldItem);
      return MoldItemMaterialLink_instance;
   }
}
