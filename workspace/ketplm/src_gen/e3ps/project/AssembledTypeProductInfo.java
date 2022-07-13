package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="ProductInfo"),
   roleA=@GeneratedRole(
      name="assembledType",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="productInfo",
      type=e3ps.project.ProductInfo.class,
      cardinality=Cardinality.ONE
   )
)

public class AssembledTypeProductInfo extends _AssembledTypeProductInfo implements Externalizable {
   static final long serialVersionUID = 1;
   public static AssembledTypeProductInfo newAssembledTypeProductInfo( e3ps.common.code.NumberCode assembledType, e3ps.project.ProductInfo productInfo ) throws wt.util.WTException {
      AssembledTypeProductInfo AssembledTypeProductInfo_instance = new AssembledTypeProductInfo();
      AssembledTypeProductInfo_instance.initialize(assembledType, productInfo);
      return AssembledTypeProductInfo_instance;
   }
}
