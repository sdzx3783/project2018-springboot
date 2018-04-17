package com.hotent.core.ldap;

import com.hotent.core.ldap.LdapObjectEvent;
import com.hotent.core.ldap.LdapSettingModel;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.StringUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

public class LdapService {
	private LdapSettingModel ldapSettingModel;

	public LdapSettingModel getLdapSettingModel() {
		return this.ldapSettingModel;
	}

	public void setLdapSettingModel(LdapSettingModel ldapSettingModel) {
		this.ldapSettingModel = ldapSettingModel;
	}

	public boolean login(String account, String password) {
		try {
			Hashtable err = this.getEnvironment(account, password);
			InitialLdapContext ctx = new InitialLdapContext(err, (Control[]) null);
			ctx.close();
			return true;
		} catch (NamingException arg4) {
			arg4.printStackTrace();
			return false;
		}
	}

	public void syncUser() {
		List list = this.getLdapObjects(true);
		LdapObjectEvent events = new LdapObjectEvent(list);
		events.setUser(true);
		AppUtil.publishEvent(events);
	}

	public void syncOrg() {
		List list = this.getLdapObjects(false);
		LdapObjectEvent events = new LdapObjectEvent(list);
		events.setUser(false);
		AppUtil.publishEvent(events);
	}

	private List getLdapObjects(boolean isUser) {
		Hashtable env = this.getEnvironment();
		ArrayList list = new ArrayList();

		try {
			InitialLdapContext e = new InitialLdapContext(env, (Control[]) null);
			List arySearchScope = this.getSearchScope();
			Iterator i$ = arySearchScope.iterator();

			while (i$.hasNext()) {
				String scope = (String) i$.next();
				List tmplist = this.getByScope(isUser, e, scope);
				list.addAll(tmplist);
			}

			e.close();
		} catch (NamingException arg8) {
			arg8.printStackTrace();
			System.err.println("Throw Exception : " + arg8);
		}

		return list;
	}

	private List<String> getSearchScope() {
		String str = this.ldapSettingModel.getOrgNames();
		String domain = this.ldapSettingModel.getLdapBase();
		ArrayList list = new ArrayList();
		if (StringUtil.isEmpty(str)) {
			list.add(domain);
		} else {
			String[] aryOrgs = str.split("[,]");
			String[] arr$ = aryOrgs;
			int len$ = aryOrgs.length;

			for (int i$ = 0; i$ < len$; ++i$) {
				String tmp = arr$[i$];
				String ou = "OU=" + tmp + "," + domain;
				list.add(ou);
			}
		}

		return list;
	}

	private List<Map<String, String>> getByScope(boolean isUser, LdapContext ctx, String searchBase)
			throws NamingException {
		ArrayList list = new ArrayList();
		String searchFilter = isUser ? "(&(objectClass=user))" : "(&(objectClass=organizationalUnit))";
		SearchControls searchCtls = new SearchControls();
		searchCtls.setSearchScope(2);
		String fields = isUser ? this.ldapSettingModel.getUserFields() : this.ldapSettingModel.getOrgFields();
		String[] returnedAtts = fields.split("[.]");
		searchCtls.setReturningAttributes(returnedAtts);
		NamingEnumeration answer = ctx.search(searchBase, searchFilter, searchCtls);

		while (true) {
			Attributes attrs;
			do {
				if (!answer.hasMoreElements()) {
					return list;
				}

				SearchResult sr = (SearchResult) answer.next();
				attrs = sr.getAttributes();
			} while (attrs == null);

			HashMap entMap = new HashMap();
			NamingEnumeration ne = attrs.getAll();

			while (ne.hasMore()) {
				Attribute attr = (Attribute) ne.next();
				String key = attr.getID();
				String val = attr.get().toString();
				entMap.put(key, val);
			}

			list.add(entMap);
		}
	}

	private Hashtable getEnvironment() {
		String account = this.ldapSettingModel.getUserDn();
		String url = new String(this.ldapSettingModel.getUrl());
		Hashtable env = new Hashtable();
		env.put("java.naming.security.authentication", "simple");
		env.put("java.naming.security.principal", account);
		env.put("java.naming.security.credentials", this.ldapSettingModel.getPassword());
		env.put("java.naming.factory.initial", "com.sun.jndi.ldap.LdapCtxFactory");
		env.put("java.naming.provider.url", url);
		return env;
	}

	private Hashtable getEnvironment(String account, String password) {
		int idx = this.ldapSettingModel.getUserDn().indexOf("@");
		account = account + this.ldapSettingModel.getUserDn().substring(idx);
		String url = new String(this.ldapSettingModel.getUrl());
		Hashtable env = new Hashtable();
		env.put("java.naming.security.authentication", "simple");
		env.put("java.naming.security.principal", account);
		env.put("java.naming.security.credentials", password);
		env.put("java.naming.factory.initial", "com.sun.jndi.ldap.LdapCtxFactory");
		env.put("java.naming.provider.url", url);
		return env;
	}
}