package com.hotent.core.api.org;

import com.hotent.core.api.org.model.IPosition;
import com.hotent.core.api.org.model.ISysOrg;
import com.hotent.core.api.org.model.ISysUser;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;

public interface ICurrentContext {
	ISysUser getCurrentUser();

	Locale getLocale();

	void setLocale(Locale arg0);

	Long getCurrentUserId();

	void setCurrentUserAccount(String arg0);

	void setCurrentUser(ISysUser arg0);

	void setCurrentPos(Long arg0);

	ISysOrg getCurrentOrg();

	ISysOrg getCurrentCompany();

	Long getCurrentCompanyId();

	IPosition getCurrentPos();

	Long getCurrentPosId();

	Long getCurrentOrgId();

	String getCurrentUserSkin(HttpServletRequest arg0);

	void cleanCurUser();

	void removeCurrentOrg();

	void clearAll();

	void removeCurrentUser();

	boolean isSuperAdmin();

	boolean isSuperAdmin(ISysUser arg0);
}