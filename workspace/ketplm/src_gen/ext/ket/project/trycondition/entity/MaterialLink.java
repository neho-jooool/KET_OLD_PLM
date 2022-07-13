package ext.ket.project.trycondition.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=true,
   tableProperties=@TableProperties(tableName="KETTryMold"),
   roleA=@GeneratedRole(
      name="theKETTryMold",
      type=ext.ket.project.trycondition.entity.KETTryMold.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="material",
      type=e3ps.project.material.MoldMaterial.class,
      cardinality=Cardinality.ONE
   )
)

public class MaterialLink extends _MaterialLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static MaterialLink newMaterialLink( ext.ket.project.trycondition.entity.KETTryMold theKETTryMold, e3ps.project.material.MoldMaterial material ) throws wt.util.WTException {
      MaterialLink MaterialLink_instance = new MaterialLink();
      MaterialLink_instance.initialize(theKETTryMold, material);
      return MaterialLink_instance;
   }
}
