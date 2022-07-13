package ext.ket.edm.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=true,
   tableProperties=@TableProperties(tableName="KETDrawingDistFileType"),
   roleA=@GeneratedRole(
      name="distFileType",
      type=ext.ket.edm.entity.KETDrawingDistFileType.class,
      cardinality=Cardinality.ONE_TO_MANY
   ),
   roleB=@GeneratedRole(
      name="distReq",
      type=ext.ket.edm.entity.KETDrawingDistRequest.class,
      cardinality=Cardinality.ONE
   )
)

public class KETDrawingDistFileTypeLink extends _KETDrawingDistFileTypeLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static KETDrawingDistFileTypeLink newKETDrawingDistFileTypeLink( ext.ket.edm.entity.KETDrawingDistFileType distFileType, ext.ket.edm.entity.KETDrawingDistRequest distReq ) throws wt.util.WTException {
      KETDrawingDistFileTypeLink KETDrawingDistFileTypeLink_instance = new KETDrawingDistFileTypeLink();
      KETDrawingDistFileTypeLink_instance.initialize(distFileType, distReq);
      return KETDrawingDistFileTypeLink_instance;
   }
}
