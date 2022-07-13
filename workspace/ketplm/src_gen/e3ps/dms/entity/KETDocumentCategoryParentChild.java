package e3ps.dms.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETDocumentCategory"),
   roleA=@GeneratedRole(
      name="parent",
      type=e3ps.dms.entity.KETDocumentCategory.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="child",
      type=e3ps.dms.entity.KETDocumentCategory.class
   )
)

public class KETDocumentCategoryParentChild extends _KETDocumentCategoryParentChild implements Externalizable {
   static final long serialVersionUID = 1;
   public static KETDocumentCategoryParentChild newKETDocumentCategoryParentChild( e3ps.dms.entity.KETDocumentCategory parent, e3ps.dms.entity.KETDocumentCategory child ) throws wt.util.WTException {
      KETDocumentCategoryParentChild KETDocumentCategoryParentChild_instance = new KETDocumentCategoryParentChild();
      KETDocumentCategoryParentChild_instance.initialize(parent, child);
      return KETDocumentCategoryParentChild_instance;
   }
}
