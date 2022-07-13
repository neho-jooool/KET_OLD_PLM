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

package ext.ket.project.gate.entity;

import java.io.IOException;
import java.io.ObjectInput;

import wt.fc.ObjectToObjectLink;
import wt.util.WTException;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.GenAsBinaryLink;
import com.ptc.windchill.annotations.metadata.GeneratedRole;
import com.ptc.windchill.annotations.metadata.TableProperties;

/**
 * 
 * <p>
 * Use the <code>newTmplAssShtTmplGateChkLink</code> static factory method(s), not the <code>TmplAssShtTmplGateChkLink</code> constructor,
 * to construct instances of this class. Instances must be constructed using the static factory(s), in order to ensure proper initialization
 * of the instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

@GenAsBinaryLink(superClass = ObjectToObjectLink.class, versions = { 2538346186404157511L }, roleA = @GeneratedRole(name = "tmplCheck", type = TemplateGateCheckSheet.class), roleB = @GeneratedRole(name = "tmplAssess", type = TemplateAssessSheet.class, cardinality = Cardinality.ONE), tableProperties = @TableProperties(tableName = "TmplAssShtTmplGateChkLink"))
public class TmplAssShtTmplGateChkLink extends _TmplAssShtTmplGateChkLink {

    static final long serialVersionUID = 1;

    /**
     * Default factory for the class.
     * 
     * @param tmplCheck
     * @param tmplAssess
     * @return TmplAssShtTmplGateChkLink
     * @exception wt.util.WTException
     **/
    public static TmplAssShtTmplGateChkLink newTmplAssShtTmplGateChkLink(TemplateGateCheckSheet tmplCheck, TemplateAssessSheet tmplAssess)
	    throws WTException {

	TmplAssShtTmplGateChkLink instance = new TmplAssShtTmplGateChkLink();
	instance.initialize(tmplCheck, tmplAssess);
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
    boolean readVersion2538346186404157511L(ObjectInput input, long readSerialVersionUID, boolean superDone) throws IOException,
	    ClassNotFoundException {

	if (!superDone) // if not doing backward compatibility
	    super.readExternal(input); // handle super class

	return true;
    }

}
