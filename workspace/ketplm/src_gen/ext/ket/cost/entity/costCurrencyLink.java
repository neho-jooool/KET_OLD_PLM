package ext.ket.cost.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="CostCurrencyInfo"),
   roleA=@GeneratedRole(
      name="master",
      type=ext.ket.cost.entity.ProductMaster.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="costCurrency",
      type=ext.ket.cost.entity.CostCurrencyInfo.class,
      cardinality=Cardinality.ONE_TO_MANY
   )
)

public class costCurrencyLink extends _costCurrencyLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static costCurrencyLink newcostCurrencyLink( ext.ket.cost.entity.ProductMaster master, ext.ket.cost.entity.CostCurrencyInfo costCurrency ) throws wt.util.WTException {
      costCurrencyLink costCurrencyLink_instance = new costCurrencyLink();
      costCurrencyLink_instance.initialize(master, costCurrency);
      return costCurrencyLink_instance;
   }
}
