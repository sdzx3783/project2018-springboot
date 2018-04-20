package com.makshi.framework.mainframe.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "hotent")
public class HotentCoreProperties {
	
	private GenIdProperties genId;
	private JdbcDbProperties jdbc;
	
	
	private String customer_table_prefix;
	private String customer_column_prefix;
	private String titleLen;
	
	public String getTitleLen() {
		return titleLen;
	}

	public void setTitleLen(String titleLen) {
		this.titleLen = titleLen;
	}

	public String getCustomer_column_prefix() {
		return customer_column_prefix;
	}

	public void setCustomer_column_prefix(String customer_column_prefix) {
		this.customer_column_prefix = customer_column_prefix;
	}

	public String getCustomer_table_prefix() {
		return customer_table_prefix;
	}

	public void setCustomer_table_prefix(String customer_table_prefix) {
		this.customer_table_prefix = customer_table_prefix;
	}

	public JdbcDbProperties getJdbc() {
		return jdbc;
	}

	public void setJdbc(JdbcDbProperties jdbc) {
		this.jdbc = jdbc;
	}

	public GenIdProperties getGenId() {
		return genId;
	}

	public void setGenId(GenIdProperties genId) {
		this.genId = genId;
	}


	
	
	
}
