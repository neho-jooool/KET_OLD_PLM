package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="MoldItemInfo"),
   roleA=@GeneratedRole(
      name="productType",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="moldItem",
      type=e3ps.project.MoldItemInfo.class,
      cardinality=Cardinality.ONE
   )
)

public class MoldItemProductTypeLInk extends _MoldItemProductTypeLInk implements Externalizable {
   static final long serialVersionUID = 1;
   public static MoldItemProductTypeLInk newMoldItemProductTypeLInk( e3ps.common.code.NumberCode productType, e3ps.project.MoldItemInfo moldItem ) throws wt.util.WTException {
      MoldItemProductTypeLInk MoldItemProductTypeLInk_instance = new MoldItemProductTypeLInk();
      MoldItemProductTypeLInk_instance.initialize(productType, moldItem);
      return MoldItemProductTypeLInk_instance;
   }
}
