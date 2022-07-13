package ext.ket.invest.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETInvestTask"),
   roleA=@GeneratedRole(
      name="investMaster",
      type=ext.ket.invest.entity.KETInvestMaster.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="investTask",
      type=ext.ket.invest.entity.KETInvestTask.class
   )
)

public class KETInvestTaskLink extends _KETInvestTaskLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static KETInvestTaskLink newKETInvestTaskLink( ext.ket.invest.entity.KETInvestMaster investMaster, ext.ket.invest.entity.KETInvestTask investTask ) throws wt.util.WTException {
      KETInvestTaskLink KETInvestTaskLink_instance = new KETInvestTaskLink();
      KETInvestTaskLink_instance.initialize(investMaster, investTask);
      return KETInvestTaskLink_instance;
   }
}
