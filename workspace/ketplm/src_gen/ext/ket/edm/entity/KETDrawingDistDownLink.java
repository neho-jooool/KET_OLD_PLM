package ext.ket.edm.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=true,
   tableProperties=@TableProperties(tableName="KETDrawingDownHis"),
   roleA=@GeneratedRole(
      name="distHis",
      type=ext.ket.edm.entity.KETDrawingDownHis.class
   ),
   roleB=@GeneratedRole(
      name="distReq",
      type=ext.ket.edm.entity.KETDrawingDistRequest.class,
      cardinality=Cardinality.ONE
   )
)

public class KETDrawingDistDownLink extends _KETDrawingDistDownLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static KETDrawingDistDownLink newKETDrawingDistDownLink( ext.ket.edm.entity.KETDrawingDownHis distHis, ext.ket.edm.entity.KETDrawingDistRequest distReq ) throws wt.util.WTException {
      KETDrawingDistDownLink KETDrawingDistDownLink_instance = new KETDrawingDistDownLink();
      KETDrawingDistDownLink_instance.initialize(distHis, distReq);
      return KETDrawingDistDownLink_instance;
   }
}
