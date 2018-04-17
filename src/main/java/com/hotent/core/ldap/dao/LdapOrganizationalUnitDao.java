package com.hotent.core.ldap.dao;

import com.hotent.core.ldap.model.LdapOrganizationalUnit;
import java.util.List;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.filter.Filter;

public interface LdapOrganizationalUnitDao {
	List<LdapOrganizationalUnit> getAll();

	List<LdapOrganizationalUnit> get(DistinguishedName arg0);

	List<LdapOrganizationalUnit> get(Filter arg0, DistinguishedName arg1);

	List<LdapOrganizationalUnit> get(Filter arg0);

	List<LdapOrganizationalUnit> getByName(String arg0);
}