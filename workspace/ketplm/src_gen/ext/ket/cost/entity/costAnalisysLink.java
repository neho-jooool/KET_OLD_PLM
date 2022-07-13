package ext.ket.cost.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="CostAnalysis"),
   roleA=@GeneratedRole(
      name="costPart",
      type=ext.ket.cost.entity.CostPart.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="costAnalysis",
      type=ext.ket.cost.entity.CostAnalysis.class
   )
)

public class costAnalisysLink extends _costAnalisysLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static costAnalisysLink newcostAnalisysLink( ext.ket.cost.entity.CostPart costPart, ext.ket.cost.entity.CostAnalysis costAnalysis ) throws wt.util.WTException {
      costAnalisysLink costAnalisysLink_instance = new costAnalisysLink();
      costAnalisysLink_instance.initialize(costPart, costAnalysis);
      return costAnalisysLink_instance;
   }
}
