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

package e3ps.groupware.company;

import wt.method.RemoteInterface;
import wt.util.WTException;

// Preserved unmodeled dependency

/**
 * 
 * @version 1.0
 **/

@RemoteInterface
public interface E3PSCompanyService {

    /**
     * 
     * <BR>
     * <BR>
     * <B>Supported API: </B>true
     * 
     * @exception wt.util.WTException
     **/
    public void syncPeopleWTUser() throws WTException;

    /**
     * 
     * <BR>
     * <BR>
     * <B>Supported API: </B>true
     * 
     * @exception java.lang.Exception
     * @exception wt.util.WTException
     **/
    public void syncWTGroupUser() throws Exception, WTException;

    /**
     * 
     * <BR>
     * <BR>
     * <B>Supported API: </B>true
     * 
     * @param id
     * @param password
     * @return String
     * @exception wt.util.WTException
     **/
    public String changePassword(String id, String password) throws WTException;

    /**
     * 
     * <BR>
     * <BR>
     * <B>Supported API: </B>true
     * 
     * @exception wt.util.WTException
     **/
    public void syncDepartment() throws WTException, Exception;

    /**
     * 
     * <BR>
     * <BR>
     * <B>Supported API: </B>true
     * 
     * @exception wt.util.WTException
     **/
    public void checkChiefInfo() throws WTException;

    /**
     * @param userId
     * @return String
     * @exception wt.util.WTException
     **/
    public String getPassword(String userId) throws WTException;

}
