package ext.ket.cost.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="ProductMaster"),
   roleA=@GeneratedRole(
      name="deliverCode",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="theProductMaster",
      type=ext.ket.cost.entity.ProductMaster.class,
      cardinality=Cardinality.ONE
   )
)

public class DeliverCodeTheProductMaster extends _DeliverCodeTheProductMaster implements Externalizable {
   static final long serialVersionUID = 1;
   public static DeliverCodeTheProductMaster newDeliverCodeTheProductMaster( e3ps.common.code.NumberCode deliverCode, ext.ket.cost.entity.ProductMaster theProductMaster ) throws wt.util.WTException {
      DeliverCodeTheProductMaster DeliverCodeTheProductMaster_instance = new DeliverCodeTheProductMaster();
      DeliverCodeTheProductMaster_instance.initialize(deliverCode, theProductMaster);
      return DeliverCodeTheProductMaster_instance;
   }
}
