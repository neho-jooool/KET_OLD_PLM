package e3ps.project.material;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="MoldMaterial"),
   roleA=@GeneratedRole(
      name="materialMaker",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theMoldMaterial",
      type=e3ps.project.material.MoldMaterial.class,
      cardinality=Cardinality.ONE
   )
)

public class MaterialMakerTheMoldMaterial extends _MaterialMakerTheMoldMaterial implements Externalizable {
   static final long serialVersionUID = 1;
   public static MaterialMakerTheMoldMaterial newMaterialMakerTheMoldMaterial( e3ps.common.code.NumberCode materialMaker, e3ps.project.material.MoldMaterial theMoldMaterial ) throws wt.util.WTException {
      MaterialMakerTheMoldMaterial MaterialMakerTheMoldMaterial_instance = new MaterialMakerTheMoldMaterial();
      MaterialMakerTheMoldMaterial_instance.initialize(materialMaker, theMoldMaterial);
      return MaterialMakerTheMoldMaterial_instance;
   }
}
