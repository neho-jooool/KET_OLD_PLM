package e3ps.project.moldPart;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="MoldSubPart"),
   roleA=@GeneratedRole(
      name="manager",
      type=e3ps.project.moldPart.MoldPartManager.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theMoldSubPart",
      type=e3ps.project.moldPart.MoldSubPart.class,
      cardinality=Cardinality.ONE
   )
)

public class ManagerTheMoldSubPart extends _ManagerTheMoldSubPart implements Externalizable {
   static final long serialVersionUID = 1;
   public static ManagerTheMoldSubPart newManagerTheMoldSubPart( e3ps.project.moldPart.MoldPartManager manager, e3ps.project.moldPart.MoldSubPart theMoldSubPart ) throws wt.util.WTException {
      ManagerTheMoldSubPart ManagerTheMoldSubPart_instance = new ManagerTheMoldSubPart();
      ManagerTheMoldSubPart_instance.initialize(manager, theMoldSubPart);
      return ManagerTheMoldSubPart_instance;
   }
}
