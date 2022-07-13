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

import wt.method.RemoteAccess;
import wt.services.ServiceFactory;
import wt.util.WTException;

@Deprecated
public class E3PSCompanyServiceFwd implements E3PSCompanyService, RemoteAccess {

    public void syncPeopleWTUser() throws WTException {

	// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
	// forward to remote service implementation
	ServiceFactory.getService(E3PSCompanyService.class).syncPeopleWTUser();
    }

    public void syncWTGroupUser() throws Exception, WTException {

	// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
	// forward to remote service implementation
	ServiceFactory.getService(E3PSCompanyService.class).syncWTGroupUser();
    }

    public String changePassword(String id, String password) throws WTException {

	// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
	// forward to remote service implementation
	return ServiceFactory.getService(E3PSCompanyService.class).changePassword(id, password);
    }

    public void syncDepartment() throws WTException, Exception {

	// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
	// forward to remote service implementation
	ServiceFactory.getService(E3PSCompanyService.class).syncDepartment();
    }

    public void checkChiefInfo() throws WTException {

	// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
	// forward to remote service implementation
	ServiceFactory.getService(E3PSCompanyService.class).checkChiefInfo();
    }

    public String getPassword(String userId) throws WTException {

	// TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
	// forward to remote service implementation
	return ServiceFactory.getService(E3PSCompanyService.class).getPassword(userId);
    }
}
