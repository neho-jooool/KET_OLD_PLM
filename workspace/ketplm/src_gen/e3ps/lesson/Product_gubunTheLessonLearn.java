package e3ps.lesson;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="LessonLearn"),
   roleA=@GeneratedRole(
      name="product_gubun",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="theLessonLearn",
      type=e3ps.lesson.LessonLearn.class,
      cardinality=Cardinality.ONE
   )
)

public class Product_gubunTheLessonLearn extends _Product_gubunTheLessonLearn implements Externalizable {
   static final long serialVersionUID = 1;
   public static Product_gubunTheLessonLearn newProduct_gubunTheLessonLearn( e3ps.common.code.NumberCode product_gubun, e3ps.lesson.LessonLearn theLessonLearn ) throws wt.util.WTException {
      Product_gubunTheLessonLearn Product_gubunTheLessonLearn_instance = new Product_gubunTheLessonLearn();
      Product_gubunTheLessonLearn_instance.initialize(product_gubun, theLessonLearn);
      return Product_gubunTheLessonLearn_instance;
   }
}
