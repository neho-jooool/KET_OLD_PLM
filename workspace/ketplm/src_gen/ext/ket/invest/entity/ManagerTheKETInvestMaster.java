package ext.ket.invest.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETInvestMaster"),
   roleA=@GeneratedRole(
      name="manager",
      type=wt.org.WTUser.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theKETInvestMaster",
      type=ext.ket.invest.entity.KETInvestMaster.class,
      cardinality=Cardinality.ONE
   )
)

public class ManagerTheKETInvestMaster extends _ManagerTheKETInvestMaster implements Externalizable {
   static final long serialVersionUID = 1;
   public static ManagerTheKETInvestMaster newManagerTheKETInvestMaster( wt.org.WTUser manager, ext.ket.invest.entity.KETInvestMaster theKETInvestMaster ) throws wt.util.WTException {
      ManagerTheKETInvestMaster ManagerTheKETInvestMaster_instance = new ManagerTheKETInvestMaster();
      ManagerTheKETInvestMaster_instance.initialize(manager, theKETInvestMaster);
      return ManagerTheKETInvestMaster_instance;
   }
}
