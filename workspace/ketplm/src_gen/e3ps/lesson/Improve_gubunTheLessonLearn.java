package e3ps.lesson;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="LessonLearn"),
   roleA=@GeneratedRole(
      name="improve_gubun",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="theLessonLearn",
      type=e3ps.lesson.LessonLearn.class,
      cardinality=Cardinality.ONE
   )
)

public class Improve_gubunTheLessonLearn extends _Improve_gubunTheLessonLearn implements Externalizable {
   static final long serialVersionUID = 1;
   public static Improve_gubunTheLessonLearn newImprove_gubunTheLessonLearn( e3ps.common.code.NumberCode improve_gubun, e3ps.lesson.LessonLearn theLessonLearn ) throws wt.util.WTException {
      Improve_gubunTheLessonLearn Improve_gubunTheLessonLearn_instance = new Improve_gubunTheLessonLearn();
      Improve_gubunTheLessonLearn_instance.initialize(improve_gubun, theLessonLearn);
      return Improve_gubunTheLessonLearn_instance;
   }
}
