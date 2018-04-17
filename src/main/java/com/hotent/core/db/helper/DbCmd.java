package com.hotent.core.db.helper;

import com.hotent.core.db.helper.JdbcCommand;
import com.hotent.core.db.helper.ObjectHelper;
import com.hotent.core.db.helper.DbCmd.OperatorType;
import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class DbCmd<T> implements JdbcCommand {
	private ObjectHelper<T> helper;
	private T obj;
	private OperatorType type;

	public void setModel(T obj) {
		this.helper = new ObjectHelper();
		this.helper.setModel(obj);
		this.obj = obj;
	}

	public void setOperatorType(OperatorType type) {
		this.type = type;
	}

	public void execute(DataSource source) throws Exception {
		String sql = "";
		if (this.type == OperatorType.Add) {
			sql = this.helper.getAddSql();
		} else if (this.type == OperatorType.Upd) {
			sql = this.helper.getUpdSql();
		} else if (this.type == OperatorType.Del) {
			sql = this.helper.getDelSql();
		}

		BeanPropertySqlParameterSource namedParameters = new BeanPropertySqlParameterSource(this.obj);
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(source);
		template.update(sql, namedParameters);
	}
}