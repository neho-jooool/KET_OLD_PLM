package ext.ket.invest.entity;

/* bcwti≪≫≪≫Copyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.≪≫≪≫This software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.≪≫≪≫ecwti
 */
import ext.ket.invest.entity.KETInvestTask;
import wt.fc.ObjectToObjectLink;
import wt.org.WTUser;
import wt.util.WTException;
import com.ptc.windchill.annotations.metadata.*;

/**
 *
 * <p>
 * Use the <code>newKETInvestMemberLink</code> static factory method(s),
 * not the <code>KETInvestMemberLink</code> constructor, to construct instances
 * of this class.  Instances must be constructed using the static factory(s),
 * in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsBinaryLink(superClass=ObjectToObjectLink.class, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   roleA=@GeneratedRole(name="theKETInvestTask", type=KETInvestTask.class,
      cardinality=Cardinality.ONE),
   roleB=@GeneratedRole(name="member", type=WTUser.class),
   tableProperties=@TableProperties(tableName="KETInvestMemberLink")
)
public class KETInvestMemberLink extends _KETInvestMemberLink {


   static final long serialVersionUID = 1;

   /**
    * Default factory for the class.
    *
    * @param     theKETInvestTask
    * @param     member
    * @return    KETInvestMemberLink
    * @exception wt.util.WTException
    **/
   public static KETInvestMemberLink newKETInvestMemberLink( KETInvestTask theKETInvestTask, WTUser member )
            throws WTException {

      KETInvestMemberLink instance = new KETInvestMemberLink();
      instance.initialize( theKETInvestTask, member );
      return instance;
   }

}
