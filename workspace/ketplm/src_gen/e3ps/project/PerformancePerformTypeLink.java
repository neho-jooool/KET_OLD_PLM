package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="PerformType"),
   roleA=@GeneratedRole(
      name="perform",
      type=e3ps.project.Performance.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="pType",
      type=e3ps.project.PerformType.class,
      cardinality=Cardinality.ONE
   )
)

public class PerformancePerformTypeLink extends _PerformancePerformTypeLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static PerformancePerformTypeLink newPerformancePerformTypeLink( e3ps.project.Performance perform, e3ps.project.PerformType pType ) throws wt.util.WTException {
      PerformancePerformTypeLink PerformancePerformTypeLink_instance = new PerformancePerformTypeLink();
      PerformancePerformTypeLink_instance.initialize(perform, pType);
      return PerformancePerformTypeLink_instance;
   }
}
