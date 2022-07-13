package e3ps.lesson;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="LessonLearn"),
   roleA=@GeneratedRole(
      name="gubun",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="theLessonLearn",
      type=e3ps.lesson.LessonLearn.class,
      cardinality=Cardinality.ONE
   )
)

public class GubunTheLessonLearn extends _GubunTheLessonLearn implements Externalizable {
   static final long serialVersionUID = 1;
   public static GubunTheLessonLearn newGubunTheLessonLearn( e3ps.common.code.NumberCode gubun, e3ps.lesson.LessonLearn theLessonLearn ) throws wt.util.WTException {
      GubunTheLessonLearn GubunTheLessonLearn_instance = new GubunTheLessonLearn();
      GubunTheLessonLearn_instance.initialize(gubun, theLessonLearn);
      return GubunTheLessonLearn_instance;
   }
}
