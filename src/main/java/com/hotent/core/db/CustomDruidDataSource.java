package com.hotent.core.db;

import com.alibaba.druid.pool.DruidDataSource;
import com.hotent.core.encrypt.EncryptUtil;

public class CustomDruidDataSource extends DruidDataSource {
	private static final long serialVersionUID = 7472503562887812863L;
	private volatile String decPwd = "";

	public String getPassword() {
		if ("".equals(this.decPwd)) {
			try {
				String e = super.getPassword();
				this.decPwd = EncryptUtil.decrypt(e);
			} catch (Exception arg1) {
				arg1.printStackTrace();
			}
		}

		return this.decPwd;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(EncryptUtil.encrypt("root"));
	}
}