package e3ps.project.trySchdule;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="TrySchdule"),
   roleA=@GeneratedRole(
      name="material",
      type=e3ps.project.material.MoldMaterial.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="theTrySchdule",
      type=e3ps.project.trySchdule.TrySchdule.class,
      cardinality=Cardinality.ONE
   )
)

public class MaterialTheTrySchdule extends _MaterialTheTrySchdule implements Externalizable {
   static final long serialVersionUID = 1;
   public static MaterialTheTrySchdule newMaterialTheTrySchdule( e3ps.project.material.MoldMaterial material, e3ps.project.trySchdule.TrySchdule theTrySchdule ) throws wt.util.WTException {
      MaterialTheTrySchdule MaterialTheTrySchdule_instance = new MaterialTheTrySchdule();
      MaterialTheTrySchdule_instance.initialize(material, theTrySchdule);
      return MaterialTheTrySchdule_instance;
   }
}
