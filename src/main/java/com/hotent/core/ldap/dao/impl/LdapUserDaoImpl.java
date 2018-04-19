package com.hotent.core.ldap.dao.impl;

import com.hotent.core.ldap.dao.LdapUserDao;
import com.hotent.core.ldap.map.LdapUserAssembler;
import com.hotent.core.ldap.model.LdapUser;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.naming.InvalidNameException;
import javax.naming.directory.SearchControls;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.control.PagedResult;
import org.springframework.ldap.control.PagedResultsCookie;
import org.springframework.ldap.control.PagedResultsDirContextProcessor;
import org.springframework.ldap.core.ContextAssembler;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.Filter;
import org.springframework.ldap.filter.LikeFilter;
import org.springframework.stereotype.Repository;

@Repository
public class LdapUserDaoImpl implements LdapUserDao {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	LdapTemplate ldapTemplate;

	public List<LdapUser> getAll() {
		return this.get(this.getDn(""));
	}

	public List<LdapUser> getAll(Map<String, Object> params) {
		AndFilter filter = new AndFilter();
		if (BeanUtils.isNotEmpty(params)) {
			Iterator keys = params.keySet().iterator();

			while (keys.hasNext()) {
				String key = (String) keys.next();
				filter.and(new LikeFilter(key, params.get(key) + "*"));
			}
		}

		this.logLdapQuey(filter.encode(), new Object[0]);
		return this.get(filter, this.getDn(""));
	}

	public List<LdapUser> get() {
		ArrayList ldapUserList = new ArrayList();
		byte pageSize = 100;
		PagedResult pagedResult = this.get((PagedResultsCookie) null, pageSize);
		List users = pagedResult.getResultList();
		ldapUserList.addAll(users);

		for (byte[] bytesCookie = pagedResult.getCookie().getCookie(); users.size() == pageSize
				&& bytesCookie != null; bytesCookie = pagedResult.getCookie().getCookie()) {
			pagedResult = this.get(pagedResult.getCookie(), pageSize);
			users = pagedResult.getResultList();
			ldapUserList.addAll(users);
		}

		return ldapUserList;
	}

	public PagedResult get(PagedResultsCookie cookie, int pageSize) {
		PagedResultsDirContextProcessor pagePagedResultsDirContextProcessor = new PagedResultsDirContextProcessor(
				pageSize, cookie);
		SearchControls searchControls = new SearchControls();
		searchControls.setSearchScope(2);
		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter("objectclass", LdapUser.OBJECTCLASS));
		List list = this.ldapTemplate.search(this.getDn((String) null), filter.encode(), searchControls,
				this.getContextMapper(), pagePagedResultsDirContextProcessor);
		PagedResult pageResult = new PagedResult(list, pagePagedResultsDirContextProcessor.getCookie());
		return pageResult;
	}

	public List<LdapUser> get(Filter filter) {
		return this.get(filter, this.getDn((String) null));
	}

	public List<LdapUser> get(DistinguishedName dn) {
		EqualsFilter filter = new EqualsFilter("objectcategory", LdapUser.OBJECTCLASS);
		this.logLdapQuey(filter.encode(), new Object[0]);
		return this.get(filter, dn);
	}

	public List<LdapUser> get(Filter filter, DistinguishedName dn) {
		ArrayList ldapUserList = new ArrayList();
		byte pageSize = 2;
		this.logLdapQuey(filter.encode(), new Object[0]);
		PagedResult pagedResult = this.get(filter, this.getDn((String) null), (PagedResultsCookie) null, pageSize);
		List users = pagedResult.getResultList();
		ldapUserList.addAll(users);

		while (users.size() == pageSize) {
			pagedResult = this.get(filter, this.getDn((String) null), pagedResult.getCookie(), pageSize);
			users = pagedResult.getResultList();
			ldapUserList.addAll(users);
		}

		return ldapUserList;
	}

	public PagedResult get(Filter filter, DistinguishedName dn, PagedResultsCookie cookie, int pageSize) {
		PagedResultsDirContextProcessor pagePagedResultsDirContextProcessor = new PagedResultsDirContextProcessor(
				pageSize, cookie);
		SearchControls searchControls = new SearchControls();
		searchControls.setSearchScope(2);
		AndFilter andfilter = new AndFilter();
		andfilter.and(filter);
		andfilter.and(new EqualsFilter("objectclass", LdapUser.OBJECTCLASS));
		List list = this.ldapTemplate.search(dn, andfilter.encode(), searchControls, this.getContextMapper(),
				pagePagedResultsDirContextProcessor);
		PagedResult pageResult = new PagedResult(list, pagePagedResultsDirContextProcessor.getCookie());
		return pageResult;
	}

	public void addUser(LdapUser user) {
		DirContextAdapter ctx = new DirContextAdapter(this.getDn("ou=HR,ou=hotent"));
		this.getContextMapper().mapToContext(user, ctx);
		this.ldapTemplate.bind(ctx);
	}

	public boolean authenticate(String userId, String password) {
		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter("objectcategory", LdapUser.OBJECTCLASS))
				.and(new EqualsFilter(LdapUser.KEY_SAMACCOUNTNAME, userId));
		boolean authenticated = this.ldapTemplate.authenticate(this.getDn((String) null), filter.encode(), password);
		return authenticated;
	}

	private ContextAssembler getContextMapper() {
		return new LdapUserAssembler();
	}

	private DistinguishedName getDn(String dnstr) {
		DistinguishedName dn = new DistinguishedName();

		try {
			if (!StringUtil.isEmpty(dnstr)) {
				dn.addAll(new DistinguishedName(dnstr));
			}
		} catch (InvalidNameException arg3) {
			arg3.printStackTrace();
		}

		this.logger.info(dn.encode());
		return dn;
	}

	private void logLdapQuey(String message, Object... args) {
		String formatStr = String.format(message, args);
		this.logger.info("LDAP query statement:" + formatStr);
	}
}