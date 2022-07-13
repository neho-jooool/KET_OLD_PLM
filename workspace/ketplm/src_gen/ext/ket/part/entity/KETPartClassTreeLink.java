package ext.ket.part.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETPartClassification"),
   roleA=@GeneratedRole(
      name="parent",
      type=ext.ket.part.entity.KETPartClassification.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="child",
      type=ext.ket.part.entity.KETPartClassification.class
   )
)

public class KETPartClassTreeLink extends _KETPartClassTreeLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static KETPartClassTreeLink newKETPartClassTreeLink( ext.ket.part.entity.KETPartClassification parent, ext.ket.part.entity.KETPartClassification child ) throws wt.util.WTException {
      KETPartClassTreeLink KETPartClassTreeLink_instance = new KETPartClassTreeLink();
      KETPartClassTreeLink_instance.initialize(parent, child);
      return KETPartClassTreeLink_instance;
   }
}
