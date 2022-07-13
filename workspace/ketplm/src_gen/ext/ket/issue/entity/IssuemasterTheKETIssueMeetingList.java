package ext.ket.issue.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETIssueMeetingList"),
   roleA=@GeneratedRole(
      name="issuemaster",
      type=ext.ket.issue.entity.KETIssueMaster.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="theKETIssueMeetingList",
      type=ext.ket.issue.entity.KETIssueMeetingList.class,
      cardinality=Cardinality.ONE
   )
)

public class IssuemasterTheKETIssueMeetingList extends _IssuemasterTheKETIssueMeetingList implements Externalizable {
   static final long serialVersionUID = 1;
   public static IssuemasterTheKETIssueMeetingList newIssuemasterTheKETIssueMeetingList( ext.ket.issue.entity.KETIssueMaster issuemaster, ext.ket.issue.entity.KETIssueMeetingList theKETIssueMeetingList ) throws wt.util.WTException {
      IssuemasterTheKETIssueMeetingList IssuemasterTheKETIssueMeetingList_instance = new IssuemasterTheKETIssueMeetingList();
      IssuemasterTheKETIssueMeetingList_instance.initialize(issuemaster, theKETIssueMeetingList);
      return IssuemasterTheKETIssueMeetingList_instance;
   }
}
