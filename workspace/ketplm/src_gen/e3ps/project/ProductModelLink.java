package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=true,
   tableProperties=@TableProperties(tableName="ModelInfo"),
   roleA=@GeneratedRole(
      name="model",
      type=e3ps.project.ModelInfo.class,
      cardinality=Cardinality.ONE_TO_MANY
   ),
   roleB=@GeneratedRole(
      name="product",
      type=e3ps.project.ProductInfo.class,
      cardinality=Cardinality.ONE
   )
)

public class ProductModelLink extends _ProductModelLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static ProductModelLink newProductModelLink( e3ps.project.ModelInfo model, e3ps.project.ProductInfo product ) throws wt.util.WTException {
      ProductModelLink ProductModelLink_instance = new ProductModelLink();
      ProductModelLink_instance.initialize(model, product);
      return ProductModelLink_instance;
   }
}
