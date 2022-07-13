package ext.ket.project.trycondition.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=true,
   tableProperties=@TableProperties(tableName="KETTryPress"),
   roleA=@GeneratedRole(
      name="theKETTryPress",
      type=ext.ket.project.trycondition.entity.KETTryPress.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="productMethod",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ONE
   )
)

public class ProductMethodLink extends _ProductMethodLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static ProductMethodLink newProductMethodLink( ext.ket.project.trycondition.entity.KETTryPress theKETTryPress, e3ps.common.code.NumberCode productMethod ) throws wt.util.WTException {
      ProductMethodLink ProductMethodLink_instance = new ProductMethodLink();
      ProductMethodLink_instance.initialize(theKETTryPress, productMethod);
      return ProductMethodLink_instance;
   }
}
