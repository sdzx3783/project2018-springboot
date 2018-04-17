package com.hotent.core.ldap.dao;

import com.hotent.core.ldap.model.LdapUser;
import java.util.List;
import java.util.Map;
import org.springframework.ldap.control.PagedResult;
import org.springframework.ldap.control.PagedResultsCookie;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.filter.Filter;

public interface LdapUserDao {
	List<LdapUser> getAll();

	List<LdapUser> get();

	List<LdapUser> get(Filter arg0);

	List<LdapUser> get(DistinguishedName arg0);

	List<LdapUser> get(Filter arg0, DistinguishedName arg1);

	PagedResult get(PagedResultsCookie arg0, int arg1);

	boolean authenticate(String arg0, String arg1);

	void addUser(LdapUser arg0);

	List<LdapUser> getAll(Map<String, Object> arg0);

	PagedResult get(Filter arg0, DistinguishedName arg1, PagedResultsCookie arg2, int arg3);
}