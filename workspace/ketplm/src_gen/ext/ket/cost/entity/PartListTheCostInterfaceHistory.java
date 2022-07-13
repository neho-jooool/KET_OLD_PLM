package ext.ket.cost.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="CostInterfaceHistory"),
   roleA=@GeneratedRole(
      name="partList",
      type=ext.ket.cost.entity.PartList.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theCostInterfaceHistory",
      type=ext.ket.cost.entity.CostInterfaceHistory.class,
      cardinality=Cardinality.ONE
   )
)

public class PartListTheCostInterfaceHistory extends _PartListTheCostInterfaceHistory implements Externalizable {
   static final long serialVersionUID = 1;
   public static PartListTheCostInterfaceHistory newPartListTheCostInterfaceHistory( ext.ket.cost.entity.PartList partList, ext.ket.cost.entity.CostInterfaceHistory theCostInterfaceHistory ) throws wt.util.WTException {
      PartListTheCostInterfaceHistory PartListTheCostInterfaceHistory_instance = new PartListTheCostInterfaceHistory();
      PartListTheCostInterfaceHistory_instance.initialize(partList, theCostInterfaceHistory);
      return PartListTheCostInterfaceHistory_instance;
   }
}
