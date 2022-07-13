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

package e3ps.project;

import wt.fc.InvalidAttributeException;
import wt.util.WTException;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.ForeignKeyRole;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedForeignKey;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.MyRole;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;
import com.ptc.windchill.annotations.metadata.Serialization;

/**
 * 제품정보
 * <p>
 * Use the <code>newProductInfo</code> static factory method(s), not the <code>ProductInfo</code> constructor, to construct instances of this class. Instances must be constructed using the static
 * factory(s), in order to ensure proper initialization of the instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

@GenAsPersistable(serializable = Serialization.EXTERNALIZABLE_BASIC, properties = { @GeneratedProperty(name = "pNum", type = String.class),
        @GeneratedProperty(name = "pName", type = String.class, javaDoc = "품명"), @GeneratedProperty(name = "areas", type = String.class, javaDoc = "적용부위"),
        @GeneratedProperty(name = "usage", type = String.class, javaDoc = "usage"), @GeneratedProperty(name = "price", type = String.class, javaDoc = "판매가"),
        @GeneratedProperty(name = "cost", type = String.class, javaDoc = "원가≪≫"), @GeneratedProperty(name = "rate", type = String.class, javaDoc = "수익률"),
        @GeneratedProperty(name = "estimated", type = String.class, javaDoc = "고객예상가"), @GeneratedProperty(name = "targetRate", type = String.class, javaDoc = "목표 수익률"),
        @GeneratedProperty(name = "investments", type = String.class, javaDoc = "목표투자비"), @GeneratedProperty(name = "year1", type = String.class, javaDoc = "1년차 예상수량"),
        @GeneratedProperty(name = "year2", type = String.class, javaDoc = "2년차"), @GeneratedProperty(name = "year3", type = String.class, javaDoc = "3년차"),
        @GeneratedProperty(name = "year4", type = String.class, javaDoc = "4년차"), @GeneratedProperty(name = "year5", type = String.class, javaDoc = "5년차"),
        @GeneratedProperty(name = "year6", type = String.class), @GeneratedProperty(name = "year7", type = String.class), @GeneratedProperty(name = "year8", type = String.class),
        @GeneratedProperty(name = "year9", type = String.class), @GeneratedProperty(name = "year10", type = String.class), @GeneratedProperty(name = "seqNum", type = String.class),
        @GeneratedProperty(name = "reviewProjectNo", type = String.class), @GeneratedProperty(name = "reviewSeqNo", type = String.class),
        @GeneratedProperty(name = "assemblyPlaceType", type = String.class, javaDoc = "조립처(사내/외주) 구분"), @GeneratedProperty(name = "assemblyPartnerNo", type = String.class, javaDoc = "조립처 파트너NO") }, foreignKeys = {
        @GeneratedForeignKey(name = "ProjectProductInfoLink", myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "project", type = e3ps.project.E3PSProject.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "productInfo", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "assembledType", type = e3ps.common.code.NumberCode.class, constraints = @PropertyConstraints(required = false)), myRole = @MyRole(name = "productInfo", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "assemblyPlace", type = e3ps.common.code.NumberCode.class, constraints = @PropertyConstraints(required = false)), myRole = @MyRole(name = "productInfo", cardinality = Cardinality.ONE)) })
public class ProductInfo extends _ProductInfo {

    static final long serialVersionUID = 1;

    /**
     * Default factory for the class.
     * 
     * @return ProductInfo
     * @exception wt.util.WTException
     **/
    public static ProductInfo newProductInfo() throws WTException {

	ProductInfo instance = new ProductInfo();
	instance.initialize();
	return instance;
    }

    /**
     * Supports initialization, following construction of an instance. Invoked by "new" factory having the same signature.
     * 
     * @exception wt.util.WTException
     **/
    protected void initialize() throws WTException {

    }

    /**
     * Gets the value of the attribute: IDENTITY. Supplies the identity of the object for business purposes. The identity is composed of name, number or possibly other attributes. The identity does
     * not include the type of the object.
     * 
     * 
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * @deprecated Replaced by IdentityFactory.getDispayIdentifier(object) to return a localizable equivalent of getIdentity(). To return a localizable value which includes the object type, use
     *             IdentityFactory.getDisplayIdentity(object). Other alternatives are ((WTObject)obj).getDisplayIdentifier() and ((WTObject)obj).getDisplayIdentity().
     * 
     * @return String
     **/
    public String getIdentity() {

	return null;
    }

    /**
     * Gets the value of the attribute: TYPE. Identifies the type of the object for business purposes. This is typically the class name of the object but may be derived from some other attribute of
     * the object.
     * 
     * 
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * @deprecated Replaced by IdentityFactory.getDispayType(object) to return a localizable equivalent of getType(). Another alternative is ((WTObject)obj).getDisplayType().
     * 
     * @return String
     **/
    public String getType() {

	return null;
    }

    @Override
    public void checkAttributes() throws InvalidAttributeException {

    }

}
