package e3ps.project.material;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="MoldMaterial"),
   roleA=@GeneratedRole(
      name="materialType",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theMoldMaterial",
      type=e3ps.project.material.MoldMaterial.class,
      cardinality=Cardinality.ONE
   )
)

public class MaterialTypeTheMoldMaterial extends _MaterialTypeTheMoldMaterial implements Externalizable {
   static final long serialVersionUID = 1;
   public static MaterialTypeTheMoldMaterial newMaterialTypeTheMoldMaterial( e3ps.common.code.NumberCode materialType, e3ps.project.material.MoldMaterial theMoldMaterial ) throws wt.util.WTException {
      MaterialTypeTheMoldMaterial MaterialTypeTheMoldMaterial_instance = new MaterialTypeTheMoldMaterial();
      MaterialTypeTheMoldMaterial_instance.initialize(materialType, theMoldMaterial);
      return MaterialTypeTheMoldMaterial_instance;
   }
}
