package com.hotent.core.ldap.dao.impl;

import com.hotent.core.ldap.dao.LdapOrganizationalUnitDao;
import com.hotent.core.ldap.map.LdapOrganizationalUnitAssembler;
import com.hotent.core.ldap.model.LdapOrganizationalUnit;
import com.hotent.core.util.StringUtil;
import java.util.List;
import javax.annotation.Resource;
import javax.naming.InvalidNameException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.Filter;
import org.springframework.ldap.filter.LikeFilter;
import org.springframework.stereotype.Repository;

@Repository
public class LdapOrganizationalUnitDaoImpl implements LdapOrganizationalUnitDao {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	LdapTemplate ldapTemplate;

	public List<LdapOrganizationalUnit> getAll() {
		return this.get(this.getDn(""));
	}

	public List<LdapOrganizationalUnit> get(DistinguishedName dn) {
		EqualsFilter filter = new EqualsFilter("objectcategory", LdapOrganizationalUnit.OBJECTCLASS);
		this.logLdapQuey(filter.encode(), new Object[0]);
		return this.ldapTemplate.search(dn, filter.encode(), this.getContextMapper());
	}

	public List<LdapOrganizationalUnit> getByName(String orgName) {
		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter("objectcategory", LdapOrganizationalUnit.OBJECTCLASS));
		filter.and(new LikeFilter(LdapOrganizationalUnit.KEY_NAME, orgName));
		this.logLdapQuey(filter.encode(), new Object[0]);
		return this.ldapTemplate.search(this.getDn(""), filter.encode(), this.getContextMapper());
	}

	public List<LdapOrganizationalUnit> get(Filter filter, DistinguishedName dn) {
		AndFilter andFilter = new AndFilter();
		andFilter.and(new EqualsFilter("objectcategory", LdapOrganizationalUnit.OBJECTCLASS));
		andFilter.and(filter);
		this.logLdapQuey(andFilter.encode(), new Object[0]);

		try {
			return this.ldapTemplate.search(dn, andFilter.encode(), this.getContextMapper());
		} catch (Exception arg4) {
			arg4.printStackTrace();
			return null;
		}
	}

	public List<LdapOrganizationalUnit> get(Filter filter) {
		return this.get(filter, this.getDn((String) null));
	}

	private ContextMapper getContextMapper() {
		return new LdapOrganizationalUnitAssembler();
	}

	private DistinguishedName getDn(String dnstr) {
		DistinguishedName dn = new DistinguishedName();

		try {
			if (!StringUtil.isEmpty(dnstr)) {
				dn.add(dnstr);
			}
		} catch (InvalidNameException arg3) {
			arg3.printStackTrace();
		}

		return dn;
	}

	private void logLdapQuey(String message, Object... args) {
		String formatStr = String.format(message, args);
		this.logger.info("LDAP query statement:" + formatStr);
	}
}