package ext.ket.invest.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETInvestDateHistory"),
   roleA=@GeneratedRole(
      name="investMaster",
      type=ext.ket.invest.entity.KETInvestMaster.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="investHistory",
      type=ext.ket.invest.entity.KETInvestDateHistory.class
   )
)

public class KETInvestDateHistoryLink extends _KETInvestDateHistoryLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static KETInvestDateHistoryLink newKETInvestDateHistoryLink( ext.ket.invest.entity.KETInvestMaster investMaster, ext.ket.invest.entity.KETInvestDateHistory investHistory ) throws wt.util.WTException {
      KETInvestDateHistoryLink KETInvestDateHistoryLink_instance = new KETInvestDateHistoryLink();
      KETInvestDateHistoryLink_instance.initialize(investMaster, investHistory);
      return KETInvestDateHistoryLink_instance;
   }
}
