package com.hotent.core.api.util;

import com.hotent.core.api.org.ICurrentContext;
import com.hotent.core.api.org.model.IPosition;
import com.hotent.core.api.org.model.ISysOrg;
import com.hotent.core.api.org.model.ISysUser;
import com.hotent.core.util.AppUtil;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;

public class ContextUtil {
	public static final String CurrentOrg = "CurrentOrg_";
	public static final String CurrentCompany = "CurrentCompany_";
	public static final String CurrentPos = "CurrentPos_";
	private static ICurrentContext context_;

	public static String getPositionKey(Long userId) {
		String posKey = "CurrentPos_" + userId;
		return posKey;
	}

	public static String getOrgKey(Long userId) {
		String orgKey = "CurrentOrg_" + userId;
		return orgKey;
	}

	public static String getCompanyKey(Long userId) {
		String orgKey = "CurrentCompany_" + userId;
		return orgKey;
	}

	public static synchronized ICurrentContext getContext() {
		if (context_ == null) {
			context_ = (ICurrentContext) AppUtil.getBean(ICurrentContext.class);
		}

		return context_;
	}

	public static ISysUser getCurrentUser() {
		ICurrentContext context = getContext();
		return context.getCurrentUser();
	}

	public static Locale getLocale() {
		ICurrentContext context = getContext();
		return context.getLocale();
	}

	public static void setLocale(Locale locale) {
		ICurrentContext context = getContext();
		context.setLocale(locale);
	}

	public static Long getCurrentUserId() {
		ICurrentContext context = getContext();
		return context.getCurrentUserId();
	}

	public static void setCurrentUserAccount(String account) {
		ICurrentContext context = getContext();
		context.setCurrentUserAccount(account);
	}

	public static void setCurrentUser(ISysUser sysUser) {
		ICurrentContext context = getContext();
		context.setCurrentUser(sysUser);
	}

	public static void setCurrentPos(Long posId) {
		ICurrentContext context = getContext();
		context.setCurrentPos(posId);
	}

	public static ISysOrg getCurrentOrg() {
		ICurrentContext context = getContext();
		return context.getCurrentOrg();
	}

	public static ISysOrg getCurrentCompany() {
		ICurrentContext context = getContext();
		return context.getCurrentCompany();
	}

	public static Long getCurrentCompanyId() {
		ICurrentContext context = getContext();
		return context.getCurrentCompanyId();
	}

	public static IPosition getCurrentPos() {
		ICurrentContext context = getContext();
		return context.getCurrentPos();
	}

	public static Long getCurrentPosId() {
		ICurrentContext context = getContext();
		return context.getCurrentPosId();
	}

	public static Long getCurrentOrgId() {
		ICurrentContext context = getContext();
		return context.getCurrentOrgId();
	}

	public static String getCurrentUserSkin(HttpServletRequest request) {
		ICurrentContext context = getContext();
		return context.getCurrentUserSkin(request);
	}

	public static void cleanCurUser() {
		ICurrentContext context = getContext();
		context.cleanCurUser();
	}

	public static void removeCurrentOrg() {
		ICurrentContext context = getContext();
		context.removeCurrentOrg();
	}

	public static void clearAll() {
		ICurrentContext context = getContext();
		context.clearAll();
	}

	public static void removeCurrentUser() {
		ICurrentContext context = getContext();
		context.removeCurrentUser();
	}

	public static boolean isSuperAdmin() {
		ICurrentContext context = getContext();
		return context.isSuperAdmin();
	}

	public static boolean isSuperAdmin(ISysUser user) {
		ICurrentContext context = getContext();
		return context.isSuperAdmin(user);
	}
}