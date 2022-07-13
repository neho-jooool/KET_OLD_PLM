package e3ps.ecm.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=true,
   tableProperties=@TableProperties(tableName="KETCompetentDepartment"),
   roleA=@GeneratedRole(
      name="theKETCompetentDepartment",
      type=e3ps.ecm.entity.KETCompetentDepartment.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="review",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   )
)

public class TheKETCompetentDepartmentReview extends _TheKETCompetentDepartmentReview implements Externalizable {
   static final long serialVersionUID = 1;
   public static TheKETCompetentDepartmentReview newTheKETCompetentDepartmentReview( e3ps.ecm.entity.KETCompetentDepartment theKETCompetentDepartment, e3ps.common.code.NumberCode review ) throws wt.util.WTException {
      TheKETCompetentDepartmentReview TheKETCompetentDepartmentReview_instance = new TheKETCompetentDepartmentReview();
      TheKETCompetentDepartmentReview_instance.initialize(theKETCompetentDepartment, review);
      return TheKETCompetentDepartmentReview_instance;
   }
}
