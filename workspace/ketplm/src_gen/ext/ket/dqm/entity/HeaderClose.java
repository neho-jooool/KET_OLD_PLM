package ext.ket.dqm.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=true,
   tableProperties=@TableProperties(tableName="KETDqmHeader"),
   roleA=@GeneratedRole(
      name="header",
      type=ext.ket.dqm.entity.KETDqmHeader.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="close",
      type=ext.ket.dqm.entity.KETDqmClose.class,
      cardinality=Cardinality.ZERO_TO_ONE
   )
)

public class HeaderClose extends _HeaderClose implements Externalizable {
   static final long serialVersionUID = 1;
   public static HeaderClose newHeaderClose( ext.ket.dqm.entity.KETDqmHeader header, ext.ket.dqm.entity.KETDqmClose close ) throws wt.util.WTException {
      HeaderClose HeaderClose_instance = new HeaderClose();
      HeaderClose_instance.initialize(header, close);
      return HeaderClose_instance;
   }
}
