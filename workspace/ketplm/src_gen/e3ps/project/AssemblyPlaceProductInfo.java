package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="ProductInfo"),
   roleA=@GeneratedRole(
      name="assemblyPlace",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="productInfo",
      type=e3ps.project.ProductInfo.class,
      cardinality=Cardinality.ONE
   )
)

public class AssemblyPlaceProductInfo extends _AssemblyPlaceProductInfo implements Externalizable {
   static final long serialVersionUID = 1;
   public static AssemblyPlaceProductInfo newAssemblyPlaceProductInfo( e3ps.common.code.NumberCode assemblyPlace, e3ps.project.ProductInfo productInfo ) throws wt.util.WTException {
      AssemblyPlaceProductInfo AssemblyPlaceProductInfo_instance = new AssemblyPlaceProductInfo();
      AssemblyPlaceProductInfo_instance.initialize(assemblyPlace, productInfo);
      return AssemblyPlaceProductInfo_instance;
   }
}
