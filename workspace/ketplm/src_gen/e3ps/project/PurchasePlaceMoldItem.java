package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="MoldItemInfo"),
   roleA=@GeneratedRole(
      name="purchasePlace",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="moldItem",
      type=e3ps.project.MoldItemInfo.class,
      cardinality=Cardinality.ONE
   )
)

public class PurchasePlaceMoldItem extends _PurchasePlaceMoldItem implements Externalizable {
   static final long serialVersionUID = 1;
   public static PurchasePlaceMoldItem newPurchasePlaceMoldItem( e3ps.common.code.NumberCode purchasePlace, e3ps.project.MoldItemInfo moldItem ) throws wt.util.WTException {
      PurchasePlaceMoldItem PurchasePlaceMoldItem_instance = new PurchasePlaceMoldItem();
      PurchasePlaceMoldItem_instance.initialize(purchasePlace, moldItem);
      return PurchasePlaceMoldItem_instance;
   }
}
