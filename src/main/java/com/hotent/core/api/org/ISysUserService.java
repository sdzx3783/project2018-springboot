package com.hotent.core.api.org;

import com.hotent.core.api.org.model.ISysUser;
import java.util.List;

public interface ISysUserService {
	ISysUser getById(Long arg0);

	ISysUser getByAccount(String arg0);

	List<ISysUser> getByGroup(Long arg0, String arg1);
}