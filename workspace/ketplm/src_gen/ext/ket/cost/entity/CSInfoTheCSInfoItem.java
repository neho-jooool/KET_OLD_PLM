package ext.ket.cost.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="CSInfoItem"),
   roleA=@GeneratedRole(
      name="CSInfo",
      type=ext.ket.cost.entity.CSInfo.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theCSInfoItem",
      type=ext.ket.cost.entity.CSInfoItem.class,
      cardinality=Cardinality.ONE
   )
)

public class CSInfoTheCSInfoItem extends _CSInfoTheCSInfoItem implements Externalizable {
   static final long serialVersionUID = 1;
   public static CSInfoTheCSInfoItem newCSInfoTheCSInfoItem( ext.ket.cost.entity.CSInfo CSInfo, ext.ket.cost.entity.CSInfoItem theCSInfoItem ) throws wt.util.WTException {
      CSInfoTheCSInfoItem CSInfoTheCSInfoItem_instance = new CSInfoTheCSInfoItem();
      CSInfoTheCSInfoItem_instance.initialize(CSInfo, theCSInfoItem);
      return CSInfoTheCSInfoItem_instance;
   }
}
