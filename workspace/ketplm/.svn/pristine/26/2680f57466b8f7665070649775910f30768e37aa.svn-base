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

package e3ps.dms.service;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

import wt.method.RemoteInterface;
import wt.util.WTException;
import e3ps.dms.entity.KETDevelopmentRequest;
import e3ps.dms.entity.KETDocumentCategory;
import e3ps.dms.entity.KETProjectDocument;
import e3ps.dms.entity.KETStandardTemplate;
import e3ps.dms.entity.KETTechnicalDocument;

// Preserved unmodeled dependency

/**
 * 
 * @version 1.0
 **/

@RemoteInterface
public interface KETDmsService {

    /**
     * @return ArrayList
     * @exception wt.util.WTException
     **/
    public ArrayList selectDocCategoryForTree() throws WTException;

    /**
     * @param code
     * @param list
     * @return ArrayList
     * @exception wt.util.WTException
     * @throws Exception
     **/
    public ArrayList makeTree(String code, ArrayList list, boolean isSearch) throws Exception;

    /**
     * @exception wt.util.WTException
     **/
    public void makeRoot() throws WTException;

    /**
     * @param OID
     * @return KETDocumentCategory
     * @exception wt.util.WTException
     **/
    public KETDocumentCategory selectDocCategory(String OID) throws WTException;

    /**
     * 문서분류코드 등록
     * 
     * @param hash
     * @return KETDocumentCategory
     * @exception wt.util.WTException
     **/
    public KETDocumentCategory insertDocCategory(Hashtable hash) throws WTException;

    /**
     * @param hash
     * @return KETDocumentCategory
     * @exception wt.util.WTException
     **/
    public KETDocumentCategory updateDocCategory(Hashtable hash) throws WTException;

    /**
     * @param OID
     * @return String
     * @exception wt.util.WTException
     **/
    public String deleteDocCategory(String OID) throws WTException;

    /**
     * @param OID
     * @return String
     * @exception wt.util.WTException
     **/
    public String selectCategoryPath(String OID) throws WTException;

    /**
     * @param code
     * @return ArrayList
     * @exception wt.util.WTException
     **/
    public ArrayList selectChildDocCategory(String code) throws WTException;

    /**
     * @param hash
     * @return KETProjectDocument
     * @exception wt.util.WTException
     **/
    public KETProjectDocument insertProjectDoc(Hashtable hash) throws WTException;

    /**
     * @param hash
     * @return KETProjectDocument
     * @exception wt.util.WTException
     **/
    public KETProjectDocument updateProjectDoc(Hashtable hash) throws WTException;

    /**
     * @param OID
     * @return String
     * @exception wt.util.WTException
     **/
    public String deleteProjectDoc(String OID) throws WTException;

    /**
     * @param hash
     * @return Vector
     * @exception wt.util.WTException
     **/
    public Vector searchProjectDoc(Hashtable hash) throws WTException;

    /**
     * @param hash
     * @return KETProjectDocument
     * @exception wt.util.WTException
     **/
    public KETProjectDocument reviceProjectDoc(Hashtable hash) throws WTException;

    /**
     * @param hash
     * @return KETDevelopmentRequest
     * @exception wt.util.WTException
     **/
    public KETDevelopmentRequest insertDevRequest(Hashtable hash) throws WTException;

    /**
     * @param hash
     * @return KETDevelopmentRequest
     * @exception wt.util.WTException
     **/
    public KETDevelopmentRequest updateDevRequest(Hashtable hash) throws WTException;

    /**
     * @param OID
     * @return String
     * @exception wt.util.WTException
     **/
    public String deleteDevRequest(String OID) throws WTException;

    /**
     * 표준양식 등록
     * 
     * @param hash
     * @param files
     * @return KETStandardTemplate
     * @exception wt.util.WTException
     **/
    public KETStandardTemplate createStandardTemplate(Hashtable hash, Vector files) throws WTException;

    /**
     * 표준양식 수정
     * 
     * @param hash
     * @param files
     * @return KETStandardTemplate
     * @exception wt.util.WTException
     **/
    public KETStandardTemplate updateStandardTemplate(Hashtable hash, Vector files) throws WTException;

    /**
     * 표준양식 삭제
     * 
     * @param hash
     * @param files
     * @return Boolean
     * @exception wt.util.WTException
     **/
    public Boolean deleteStandardTemplate(Hashtable hash, Vector files) throws WTException;

    /**
     * @param hash
     * @return KETTechnicalDocument
     * @exception wt.util.WTException
     **/
    public KETTechnicalDocument insertTechDoc(Hashtable hash) throws WTException;

    /**
     * @param hash
     * @return KETTechnicalDocument
     * @exception wt.util.WTException
     **/
    public KETTechnicalDocument updateTechDoc(Hashtable hash) throws WTException;

    /**
     * @param OID
     * @return String
     * @exception wt.util.WTException
     **/
    public String deleteTechDoc(String OID) throws WTException;

    /**
     * @param hash
     * @return KETTechnicalDocument
     * @exception wt.util.WTException
     **/
    public KETTechnicalDocument reviceTechDoc(Hashtable hash) throws WTException;

    public ArrayList selectDocCategoryForTree(String categoryCode, boolean isSearch) throws WTException;

}
