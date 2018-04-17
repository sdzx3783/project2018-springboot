package com.hotent.core.ldap.dao;

import com.hotent.core.ldap.model.LdapOrganization;
import java.util.List;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.filter.Filter;

public interface LdapOrganizationDao {
	List<LdapOrganization> getAll();

	List<LdapOrganization> get(DistinguishedName arg0);

	List<LdapOrganization> get(Filter arg0);

	List<LdapOrganization> get(Filter arg0, DistinguishedName arg1);

	List<LdapOrganization> getByName(String arg0);
}