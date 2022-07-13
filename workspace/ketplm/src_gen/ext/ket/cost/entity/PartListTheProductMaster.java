package ext.ket.cost.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="ProductMaster"),
   roleA=@GeneratedRole(
      name="partList",
      type=ext.ket.cost.entity.PartList.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theProductMaster",
      type=ext.ket.cost.entity.ProductMaster.class,
      cardinality=Cardinality.ONE
   )
)

public class PartListTheProductMaster extends _PartListTheProductMaster implements Externalizable {
   static final long serialVersionUID = 1;
   public static PartListTheProductMaster newPartListTheProductMaster( ext.ket.cost.entity.PartList partList, ext.ket.cost.entity.ProductMaster theProductMaster ) throws wt.util.WTException {
      PartListTheProductMaster PartListTheProductMaster_instance = new PartListTheProductMaster();
      PartListTheProductMaster_instance.initialize(partList, theProductMaster);
      return PartListTheProductMaster_instance;
   }
}
