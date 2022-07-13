package ext.ket.edm.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=true,
   tableProperties=@TableProperties(tableName="KETDrawingDistDept"),
   roleA=@GeneratedRole(
      name="distDept",
      type=ext.ket.edm.entity.KETDrawingDistDept.class
   ),
   roleB=@GeneratedRole(
      name="distReq",
      type=ext.ket.edm.entity.KETDrawingDistRequest.class,
      cardinality=Cardinality.ONE
   )
)

public class KETDrawingDistDeptLink extends _KETDrawingDistDeptLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static KETDrawingDistDeptLink newKETDrawingDistDeptLink( ext.ket.edm.entity.KETDrawingDistDept distDept, ext.ket.edm.entity.KETDrawingDistRequest distReq ) throws wt.util.WTException {
      KETDrawingDistDeptLink KETDrawingDistDeptLink_instance = new KETDrawingDistDeptLink();
      KETDrawingDistDeptLink_instance.initialize(distDept, distReq);
      return KETDrawingDistDeptLink_instance;
   }
}
