package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="MoldItemInfo"),
   roleA=@GeneratedRole(
      name="costInfo",
      type=e3ps.project.CostInfo.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="moldItem",
      type=e3ps.project.MoldItemInfo.class,
      cardinality=Cardinality.ONE
   )
)

public class CostInfoMoldItemLink extends _CostInfoMoldItemLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static CostInfoMoldItemLink newCostInfoMoldItemLink( e3ps.project.CostInfo costInfo, e3ps.project.MoldItemInfo moldItem ) throws wt.util.WTException {
      CostInfoMoldItemLink CostInfoMoldItemLink_instance = new CostInfoMoldItemLink();
      CostInfoMoldItemLink_instance.initialize(costInfo, moldItem);
      return CostInfoMoldItemLink_instance;
   }
}
