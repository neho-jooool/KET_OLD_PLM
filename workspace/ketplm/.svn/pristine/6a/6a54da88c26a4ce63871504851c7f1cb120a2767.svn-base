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

import wt.doc.WTDocument;
import wt.util.WTException;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.ForeignKeyRole;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedForeignKey;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.MyRole;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;
import com.ptc.windchill.annotations.metadata.Serialization;
import com.ptc.windchill.annotations.metadata.TableProperties;

/**
 * 
 * <p>
 * Use the <code>newGateAssessResult</code> static factory method(s), not the <code>GateAssessResult</code> constructor, to construct
 * instances of this class. Instances must be constructed using the static factory(s), in order to ensure proper initialization of the
 * instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

@GenAsPersistable(superClass = WTDocument.class, serializable = Serialization.EXTERNALIZABLE_BASIC, properties = {
        @GeneratedProperty(name = "finalResult", type = String.class, javaDoc = "최종평가"),
        @GeneratedProperty(name = "passComment", type = String.class, javaDoc = "합격근거", constraints = @PropertyConstraints(upperLimit = 4000)) }, foreignKeys = { @GeneratedForeignKey(name = "GateAssRsltPjtLink", foreignKeyRole = @ForeignKeyRole(name = "project", type = e3ps.project.E3PSProject.class), myRole = @MyRole(name = "result", cardinality = Cardinality.ZERO_TO_ONE)) }, tableProperties = @TableProperties(tableName = "KETGateAssessResult"))
public class GateAssessResult extends _GateAssessResult {

    static final long serialVersionUID = 1;

    /**
     * Default factory for the class.
     * 
     * @return GateAssessResult
     * @exception wt.util.WTException
     **/
    public static GateAssessResult newGateAssessResult() throws WTException {

	GateAssessResult instance = new GateAssessResult();
	instance.initialize();
	return instance;
    }

}
