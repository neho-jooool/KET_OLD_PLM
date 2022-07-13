package ext.ket.cost.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="CostInterfaceChildHistory"),
   roleA=@GeneratedRole(
      name="partList",
      type=ext.ket.cost.entity.PartList.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theCostInterfaceChildHistory",
      type=ext.ket.cost.entity.CostInterfaceChildHistory.class,
      cardinality=Cardinality.ONE
   )
)

public class PartListTheCostInterfaceChildHistory extends _PartListTheCostInterfaceChildHistory implements Externalizable {
   static final long serialVersionUID = 1;
   public static PartListTheCostInterfaceChildHistory newPartListTheCostInterfaceChildHistory( ext.ket.cost.entity.PartList partList, ext.ket.cost.entity.CostInterfaceChildHistory theCostInterfaceChildHistory ) throws wt.util.WTException {
      PartListTheCostInterfaceChildHistory PartListTheCostInterfaceChildHistory_instance = new PartListTheCostInterfaceChildHistory();
      PartListTheCostInterfaceChildHistory_instance.initialize(partList, theCostInterfaceChildHistory);
      return PartListTheCostInterfaceChildHistory_instance;
   }
}
