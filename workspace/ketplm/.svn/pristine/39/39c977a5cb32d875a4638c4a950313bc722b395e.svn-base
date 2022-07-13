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

import java.io.IOException;
import java.io.ObjectInput;

import wt.fc.ObjectToObjectLink;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.ForeignKeyRole;
import com.ptc.windchill.annotations.metadata.GenAsBinaryLink;
import com.ptc.windchill.annotations.metadata.GeneratedForeignKey;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.GeneratedRole;
import com.ptc.windchill.annotations.metadata.MyRole;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;
import com.ptc.windchill.annotations.metadata.TableProperties;

/**
 * 
 * <p>
 * Use the <code>newKETDocumentPartLink</code> static factory method(s), not the <code>KETDocumentPartLink</code> constructor, to construct instances of this class. Instances must
 * be constructed using the static factory(s), in order to ensure proper initialization of the instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

@GenAsBinaryLink(superClass = ObjectToObjectLink.class, versions = { 2538346186404157511L }, roleA = @GeneratedRole(name = "document", type = KETProjectDocument.class), roleB = @GeneratedRole(name = "part", type = WTPart.class), properties = { @GeneratedProperty(name = "partNo", type = String.class, javaDoc = "partNo") }, foreignKeys = { @GeneratedForeignKey(myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "partMaster", type = wt.part.WTPartMaster.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "theKETDocumentPartLink", cardinality = Cardinality.ONE)) }, tableProperties = @TableProperties(tableName = "KETDocumentPartLink"))
public class KETDocumentPartLink extends _KETDocumentPartLink {

	static final long serialVersionUID = 1;

	/**
	 * Default factory for the class.
	 * 
	 * @param document
	 * @param part
	 * @return KETDocumentPartLink
	 * @exception wt.util.WTException
	 **/
	public static KETDocumentPartLink newKETDocumentPartLink(KETProjectDocument document, WTPart part) throws WTException {

		KETDocumentPartLink instance = new KETDocumentPartLink();
		instance.initialize(document, part);

		// #################### 2019.03.05 MODIFY BY DHKIM #######################################
		// ################ CUSTOMIZE NEW INSTANCE ATTRIBUTE SETTING START #######################

		WTPartMaster master = (WTPartMaster) part.getMaster();

		try {
			instance.setPartMaster(master);
			instance.setPartNo(master.getNumber());
		} catch (WTPropertyVetoException e) {
			e.printStackTrace();
		}

		// ################ CUSTOMIZE NEW INSTANCE ATTRIBUTE SETTING END #########################
		// #################### 2019.03.05 MODIFY BY DHKIM #######################################

		return instance;
	}

	/**
	 * Reads the non-transient fields of this class from an external source.
	 * 
	 * @param input
	 * @param readSerialVersionUID
	 * @param superDone
	 * @return boolean
	 * @exception java.io.IOException
	 * @exception java.lang.ClassNotFoundException
	 **/
	boolean readVersion2538346186404157511L(ObjectInput input, long readSerialVersionUID, boolean superDone) throws IOException, ClassNotFoundException {

		if (!superDone) // if not doing backward compatibility
			super.readExternal(input); // handle super class

		return true;
	}

}
