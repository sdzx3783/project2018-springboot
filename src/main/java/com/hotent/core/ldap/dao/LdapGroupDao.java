package com.hotent.core.ldap.dao;

import com.hotent.core.ldap.model.LdapGroup;
import java.util.List;
import org.springframework.ldap.core.DistinguishedName;

public interface LdapGroupDao {
	List<LdapGroup> getAll();

	List<LdapGroup> getByDN(DistinguishedName arg0);
}