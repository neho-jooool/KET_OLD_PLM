package ext.ket.edm.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETDrawingDownHis"),
   roleA=@GeneratedRole(
      name="user",
      type=wt.org.WTUser.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="distHis",
      type=ext.ket.edm.entity.KETDrawingDownHis.class
   )
)

public class KETDrawingDownUserLink extends _KETDrawingDownUserLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static KETDrawingDownUserLink newKETDrawingDownUserLink( wt.org.WTUser user, ext.ket.edm.entity.KETDrawingDownHis distHis ) throws wt.util.WTException {
      KETDrawingDownUserLink KETDrawingDownUserLink_instance = new KETDrawingDownUserLink();
      KETDrawingDownUserLink_instance.initialize(user, distHis);
      return KETDrawingDownUserLink_instance;
   }
}
