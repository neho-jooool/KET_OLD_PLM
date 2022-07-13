package ext.ket.sample.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETSamplePart"),
   roleA=@GeneratedRole(
      name="part",
      type=wt.part.WTPart.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="smplpart",
      type=ext.ket.sample.entity.KETSamplePart.class,
      cardinality=Cardinality.ZERO_TO_ONE
   )
)

public class PartSmplpart extends _PartSmplpart implements Externalizable {
   static final long serialVersionUID = 1;
   public static PartSmplpart newPartSmplpart( wt.part.WTPart part, ext.ket.sample.entity.KETSamplePart smplpart ) throws wt.util.WTException {
      PartSmplpart PartSmplpart_instance = new PartSmplpart();
      PartSmplpart_instance.initialize(part, smplpart);
      return PartSmplpart_instance;
   }
}
