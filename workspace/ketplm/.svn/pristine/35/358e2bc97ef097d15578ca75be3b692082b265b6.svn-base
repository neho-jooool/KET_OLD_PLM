/* bcwti≪≫≪≫Copyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.≪≫≪≫This software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.≪≫≪≫ecwti
 */

package ext.ket.dms.entity;

import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;
import com.ptc.windchill.annotations.metadata.Serialization;

import wt.content.ContentHolder;
import wt.enterprise.Managed;
import wt.util.WTException;

/**
 *
 * <p>
 * Use the <code>newKETIssueMaster</code> static factory method(s), not
 * the <code>KETIssueMaster</code> constructor, to construct instances of
 * this class.  Instances must be constructed using the static factory(s),
 * in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(superClass=Managed.class, interfaces={ContentHolder.class}, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="docNo", type=String.class,
       javaDoc="문서번호"),
   @GeneratedProperty(name="docName", type=String.class,
       javaDoc="문서명"),
   @GeneratedProperty(name="moduleCode", type=String.class,
      javaDoc="모듈 CODE"),
   @GeneratedProperty(name="refView", type=String.class,
      javaDoc="관련화면"),
   @GeneratedProperty(name="description", type=String.class,
       javaDoc="양식설명",
   constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="version", type=Integer.class,
       javaDoc="버전",
       initialValue="0"),
   @GeneratedProperty(name="lastest", type=boolean.class, initialValue="false"),
   @GeneratedProperty(name="branchId", type=long.class, initialValue="0"),
   }
   )
public class KETSGDocument extends _KETSGDocument {


   static final long serialVersionUID = 1;

   /**
    * Default factory for the class.
    *
    * @return    KETSGDocument
    * @exception wt.util.WTException
    **/
   public static KETSGDocument newKETSGDocument()
            throws WTException {

      KETSGDocument instance = new KETSGDocument();
      instance.initialize();
      return instance;
   }

}
