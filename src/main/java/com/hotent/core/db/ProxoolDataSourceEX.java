package com.hotent.core.db;

import com.hotent.core.encrypt.EncryptUtil;
import org.logicalcobwebs.proxool.ProxoolDataSource;

public class ProxoolDataSourceEX extends ProxoolDataSource {
	private String password = "";

	public void setPassword(String password) {
		String pwd = "";

		try {
			pwd = EncryptUtil.decrypt(password);
		} catch (Exception arg3) {
			arg3.printStackTrace();
		}

		this.password = pwd;
	}

	public String getPassword() {
		return this.password;
	}
}