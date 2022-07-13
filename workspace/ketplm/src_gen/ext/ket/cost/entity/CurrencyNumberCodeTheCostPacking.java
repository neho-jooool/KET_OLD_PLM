package ext.ket.cost.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="CostPacking"),
   roleA=@GeneratedRole(
      name="currencyNumberCode",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theCostPacking",
      type=ext.ket.cost.entity.CostPacking.class,
      cardinality=Cardinality.ONE
   )
)

public class CurrencyNumberCodeTheCostPacking extends _CurrencyNumberCodeTheCostPacking implements Externalizable {
   static final long serialVersionUID = 1;
   public static CurrencyNumberCodeTheCostPacking newCurrencyNumberCodeTheCostPacking( e3ps.common.code.NumberCode currencyNumberCode, ext.ket.cost.entity.CostPacking theCostPacking ) throws wt.util.WTException {
      CurrencyNumberCodeTheCostPacking CurrencyNumberCodeTheCostPacking_instance = new CurrencyNumberCodeTheCostPacking();
      CurrencyNumberCodeTheCostPacking_instance.initialize(currencyNumberCode, theCostPacking);
      return CurrencyNumberCodeTheCostPacking_instance;
   }
}
