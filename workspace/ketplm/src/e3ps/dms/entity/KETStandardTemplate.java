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

package e3ps.dms.entity;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import wt.doc.WTDocument;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;

/**
 *
 * <p>
 * Use the <code>newKETStandardTemplate</code> static factory method(s),
 * not the <code>KETStandardTemplate</code> constructor, to construct instances
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
   @GeneratedProperty(name="divisionCode", type=String.class,
      javaDoc="사업부코드"),
   @GeneratedProperty(name="templateDescription", type=String.class,
      javaDoc="양식설명",
      constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="deptCode", type=String.class,
      javaDoc="관리부서"),
   @GeneratedProperty(name="attribute1", type=String.class,
      javaDoc="기타1"),
   @GeneratedProperty(name="attribute2", type=String.class,
      javaDoc="기타2"),
   @GeneratedProperty(name="attribute3", type=String.class,
      javaDoc="기타3"),
   @GeneratedProperty(name="attribute4", type=String.class,
      javaDoc="기타4"),
   @GeneratedProperty(name="attribute5", type=String.class,
      javaDoc="기타5"),
   @GeneratedProperty(name="attribute6", type=String.class,
      javaDoc="기타6"),
   @GeneratedProperty(name="attribute7", type=String.class,
      javaDoc="기타7"),
   @GeneratedProperty(name="attribute8", type=String.class,
      javaDoc="기타8"),
   @GeneratedProperty(name="attribute9", type=String.class,
      javaDoc="기타9"),
   @GeneratedProperty(name="attribute10", type=String.class,
      javaDoc="기타10")
   })
public class KETStandardTemplate extends _KETStandardTemplate {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    KETStandardTemplate
    * @exception wt.util.WTException
    **/
   public static KETStandardTemplate newKETStandardTemplate()
            throws WTException {

      KETStandardTemplate instance = new KETStandardTemplate();
      instance.initialize();
      return instance;
   }

}
