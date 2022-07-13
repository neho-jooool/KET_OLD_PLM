package ext.ket.cost.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=true,
   tableProperties=@TableProperties(tableName="CostPart"),
   roleA=@GeneratedRole(
      name="part",
      type=ext.ket.cost.entity.CostPart.class,
      cardinality=Cardinality.ONE_TO_MANY
   ),
   roleB=@GeneratedRole(
      name="report",
      type=ext.ket.cost.entity.CostReport.class,
      cardinality=Cardinality.ZERO_TO_ONE
   )
)

public class costReportPartLink extends _costReportPartLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static costReportPartLink newcostReportPartLink( ext.ket.cost.entity.CostPart part, ext.ket.cost.entity.CostReport report ) throws wt.util.WTException {
      costReportPartLink costReportPartLink_instance = new costReportPartLink();
      costReportPartLink_instance.initialize(part, report);
      return costReportPartLink_instance;
   }
}
