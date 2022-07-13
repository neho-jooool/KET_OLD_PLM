package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="ModelInfo"),
   roleA=@GeneratedRole(
      name="model",
      type=e3ps.project.outputtype.OEMProjectType.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theModelInfo",
      type=e3ps.project.ModelInfo.class,
      cardinality=Cardinality.ONE
   )
)

public class ModelTheModelInfo extends _ModelTheModelInfo implements Externalizable {
   static final long serialVersionUID = 1;
   public static ModelTheModelInfo newModelTheModelInfo( e3ps.project.outputtype.OEMProjectType model, e3ps.project.ModelInfo theModelInfo ) throws wt.util.WTException {
      ModelTheModelInfo ModelTheModelInfo_instance = new ModelTheModelInfo();
      ModelTheModelInfo_instance.initialize(model, theModelInfo);
      return ModelTheModelInfo_instance;
   }
}
