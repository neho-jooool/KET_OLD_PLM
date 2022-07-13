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

package e3ps.ecm.entity;

import e3ps.dms.entity.KETProjectDocument;
import e3ps.ecm.entity.KETProdChangeActivity;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import wt.fc.ObjectToObjectLink;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;

/**
 *
 * <p>
 * Use the <code>newKETProdECADocLink</code> static factory method(s), not
 * the <code>KETProdECADocLink</code> constructor, to construct instances
 * of this class.  Instances must be constructed using the static factory(s),
 * in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsBinaryLink(superClass=ObjectToObjectLink.class, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="docType", type=String.class),
   @GeneratedProperty(name="preVersion", type=String.class),
   @GeneratedProperty(name="afterVersion", type=String.class),
   @GeneratedProperty(name="docTypeDesc", type=String.class,
      javaDoc="상세구분")
   },
   roleA=@GeneratedRole(name="doc", type=KETProjectDocument.class,
      cardinality=Cardinality.ONE),
   roleB=@GeneratedRole(name="prodECA", type=KETProdChangeActivity.class),
   tableProperties=@TableProperties(tableName="KETProdECADocLink")
)
public class KETProdECADocLink extends _KETProdECADocLink {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @param     doc
    * @param     prodECA
    * @return    KETProdECADocLink
    * @exception wt.util.WTException
    **/
   public static KETProdECADocLink newKETProdECADocLink( KETProjectDocument doc, KETProdChangeActivity prodECA )
            throws WTException {

      KETProdECADocLink instance = new KETProdECADocLink();
      instance.initialize( doc, prodECA );
      return instance;
   }

}
