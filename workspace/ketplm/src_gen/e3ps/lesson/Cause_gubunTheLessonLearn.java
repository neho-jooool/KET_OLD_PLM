package e3ps.lesson;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="LessonLearn"),
   roleA=@GeneratedRole(
      name="cause_gubun",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="theLessonLearn",
      type=e3ps.lesson.LessonLearn.class,
      cardinality=Cardinality.ONE
   )
)

public class Cause_gubunTheLessonLearn extends _Cause_gubunTheLessonLearn implements Externalizable {
   static final long serialVersionUID = 1;
   public static Cause_gubunTheLessonLearn newCause_gubunTheLessonLearn( e3ps.common.code.NumberCode cause_gubun, e3ps.lesson.LessonLearn theLessonLearn ) throws wt.util.WTException {
      Cause_gubunTheLessonLearn Cause_gubunTheLessonLearn_instance = new Cause_gubunTheLessonLearn();
      Cause_gubunTheLessonLearn_instance.initialize(cause_gubun, theLessonLearn);
      return Cause_gubunTheLessonLearn_instance;
   }
}
