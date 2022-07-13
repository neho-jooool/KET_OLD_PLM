package ext.ket.issue.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETIssueTask"),
   roleA=@GeneratedRole(
      name="issueMaster",
      type=ext.ket.issue.entity.KETIssueMaster.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="issueTask",
      type=ext.ket.issue.entity.KETIssueTask.class
   )
)

public class ketIssueTaskLink extends _ketIssueTaskLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static ketIssueTaskLink newketIssueTaskLink( ext.ket.issue.entity.KETIssueMaster issueMaster, ext.ket.issue.entity.KETIssueTask issueTask ) throws wt.util.WTException {
      ketIssueTaskLink ketIssueTaskLink_instance = new ketIssueTaskLink();
      ketIssueTaskLink_instance.initialize(issueMaster, issueTask);
      return ketIssueTaskLink_instance;
   }
}
