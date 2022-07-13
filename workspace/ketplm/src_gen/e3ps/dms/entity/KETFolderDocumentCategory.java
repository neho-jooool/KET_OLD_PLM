package e3ps.dms.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETDocumentCategory"),
   roleA=@GeneratedRole(
      name="folder",
      type=wt.folder.Folder.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="documentCategory",
      type=e3ps.dms.entity.KETDocumentCategory.class,
      cardinality=Cardinality.ONE
   )
)

public class KETFolderDocumentCategory extends _KETFolderDocumentCategory implements Externalizable {
   static final long serialVersionUID = 1;
   public static KETFolderDocumentCategory newKETFolderDocumentCategory( wt.folder.Folder folder, e3ps.dms.entity.KETDocumentCategory documentCategory ) throws wt.util.WTException {
      KETFolderDocumentCategory KETFolderDocumentCategory_instance = new KETFolderDocumentCategory();
      KETFolderDocumentCategory_instance.initialize(folder, documentCategory);
      return KETFolderDocumentCategory_instance;
   }
}
