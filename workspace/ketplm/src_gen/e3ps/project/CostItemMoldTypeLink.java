package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="CostInfo"),
   roleA=@GeneratedRole(
      name="moldType",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="costItem",
      type=e3ps.project.CostInfo.class,
      cardinality=Cardinality.ONE
   )
)

public class CostItemMoldTypeLink extends _CostItemMoldTypeLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static CostItemMoldTypeLink newCostItemMoldTypeLink( e3ps.common.code.NumberCode moldType, e3ps.project.CostInfo costItem ) throws wt.util.WTException {
      CostItemMoldTypeLink CostItemMoldTypeLink_instance = new CostItemMoldTypeLink();
      CostItemMoldTypeLink_instance.initialize(moldType, costItem);
      return CostItemMoldTypeLink_instance;
   }
}
