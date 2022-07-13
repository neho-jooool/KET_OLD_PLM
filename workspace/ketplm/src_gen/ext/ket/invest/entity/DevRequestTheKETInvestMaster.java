package ext.ket.invest.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETInvestMaster"),
   roleA=@GeneratedRole(
      name="devRequest",
      type=e3ps.dms.entity.KETDevelopmentRequest.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="theKETInvestMaster",
      type=ext.ket.invest.entity.KETInvestMaster.class,
      cardinality=Cardinality.ONE
   )
)

public class DevRequestTheKETInvestMaster extends _DevRequestTheKETInvestMaster implements Externalizable {
   static final long serialVersionUID = 1;
   public static DevRequestTheKETInvestMaster newDevRequestTheKETInvestMaster( e3ps.dms.entity.KETDevelopmentRequest devRequest, ext.ket.invest.entity.KETInvestMaster theKETInvestMaster ) throws wt.util.WTException {
      DevRequestTheKETInvestMaster DevRequestTheKETInvestMaster_instance = new DevRequestTheKETInvestMaster();
      DevRequestTheKETInvestMaster_instance.initialize(devRequest, theKETInvestMaster);
      return DevRequestTheKETInvestMaster_instance;
   }
}
