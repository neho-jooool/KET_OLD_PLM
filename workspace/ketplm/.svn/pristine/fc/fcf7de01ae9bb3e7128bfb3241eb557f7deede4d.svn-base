/* bcwti
 *
 * Copyright (c) 2008 Parametric Technology Corporation (PTC). All Rights
 * Reserved.
 *
 * This software is the confidential and proprietary information of PTC
 * and is subject to the terms of a software license agreement. You shall
 * not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement.
 *
 * ecwti
 */

package e3ps.ews.entity;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import java.sql.Timestamp;
import wt.doc.WTDocument;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;
import java.util.Vector;  // Preserved unmodeled dependency

/**
 *
 * <p>
 * Use the <code>newKETEarlyWarning</code> static factory method(s), not
 * the <code>KETEarlyWarning</code> constructor, to construct instances
 * of this class.  Instances must be constructed using the static factory(s),
 * in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(superClass=WTDocument.class, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="pjtNo", type=String.class,
      javaDoc="프로젝트 번호"),
   @GeneratedProperty(name="inOut", type=String.class,
      javaDoc="사내품 또는 외주품 구분정보"),
   @GeneratedProperty(name="proteamNo", type=String.class,
      javaDoc="대상 제품을 생산하는 팀의 코드값"),
   @GeneratedProperty(name="partnerNo", type=String.class,
      javaDoc="대상 제품을 생산하는 협력사의 코드값"),
   @GeneratedProperty(name="fstCharge", type=String.class,
      javaDoc="수행담당자(정) 아이디 값"),
   @GeneratedProperty(name="sndCharge", type=String.class,
      javaDoc="수행담당자(부) 아이디 값"),
   @GeneratedProperty(name="workingMM", type=int.class,
      javaDoc="초기유동관리 활동개월수"),
   @GeneratedProperty(name="startDate", type=Timestamp.class,
      javaDoc="초기유동관리 시작일"),
   @GeneratedProperty(name="endDate", type=Timestamp.class,
      javaDoc="초기유동관리 종료일"),
   @GeneratedProperty(name="planDate", type=Timestamp.class,
      javaDoc="초기유동관리 계획서 제출날짜"),
   @GeneratedProperty(name="attribute1", type=String.class,
      javaDoc="추가 속성 항목1",
      constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="attribute2", type=String.class,
      javaDoc="추가 속성 항목2",
      constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="attribute3", type=String.class,
      javaDoc="추가 속성 항목3",
      constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="attribute4", type=String.class,
      javaDoc="추가 속성 항목4",
      constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="attribute5", type=String.class,
      javaDoc="추가 속성 항목5",
      constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="attribute6", type=String.class,
      javaDoc="추가 속성 항목6",
      constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="attribute7", type=String.class,
      javaDoc="추가 속성 항목7",
      constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="attribute8", type=String.class,
      javaDoc="추가 속성 항목8",
      constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="attribute9", type=String.class,
      javaDoc="추가 속성 항목9",
      constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="attribute10", type=String.class,
      javaDoc="추가 속성 항목10",
      constraints=@PropertyConstraints(upperLimit=4000))
   })
public class KETEarlyWarning extends _KETEarlyWarning {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    KETEarlyWarning
    * @exception wt.util.WTException
    **/
   public static KETEarlyWarning newKETEarlyWarning()
            throws WTException {

      KETEarlyWarning instance = new KETEarlyWarning();
      instance.initialize();
      return instance;
   }

}
