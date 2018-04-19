package com.hotent.core.ldap.map;

import com.hotent.core.ldap.model.LdapGroup;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.ldap.core.ContextAssembler;
import org.springframework.ldap.core.DirContextOperations;

public class LdapGroupAssemabller implements ContextAssembler {
	public Object mapFromContext(Object context) {
		DirContextOperations ctx = (DirContextOperations) context;
		LdapGroup group = new LdapGroup();
		group.setAdminCount(ctx.getStringAttribute("adminCount"));
		group.setCn(ctx.getStringAttribute("cn"));
		group.setDescription(ctx.getStringAttribute("description"));
		group.setDistinguishedName(ctx.getStringAttribute("distinguishedName"));
		group.setInfo(ctx.getStringAttribute("info"));
		group.setMail(ctx.getStringAttribute("mail"));
		group.setMembers(ctx.getStringAttributes("member"));
		group.setName(ctx.getStringAttribute("name"));
		group.setsAMAccountName(ctx.getStringAttribute("sAMAccountName"));
		group.setsAMAccountType(ctx.getStringAttribute("sAMAccountType"));
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss\'.\'S\'Z\'");
		Date createDate = null;
		Date changeDate = null;

		try {
			changeDate = dateFormat.parse(ctx.getStringAttribute("whenChanged"));
			createDate = dateFormat.parse(ctx.getStringAttribute("whenCreated"));
		} catch (ParseException arg7) {
			arg7.printStackTrace();
		}

		group.setWhenChanged(changeDate);
		group.setWhenCreated(createDate);
		return group;
	}

	public void mapToContext(Object obj, Object ctx) {
	}
}