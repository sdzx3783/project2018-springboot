package com.makshi.framework.mainframe.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.hotent.core.mybatis.Dialect;
import com.hotent.core.table.DialectFactoryBean;
import com.hotent.core.table.TableOperatorFactoryBean;
import com.makshi.framework.mainframe.config.properties.HotentCoreProperties;

@Configuration
public class BeanConfig {
	@Bean(value = "localdialect")
    public DialectFactoryBean getDialectFactoryBean(@Qualifier("jdbcTemplate") JdbcTemplate jdbcTemplate)
    {
		DialectFactoryBean DialectFactoryBean = new DialectFactoryBean();
       return DialectFactoryBean;
    }
	@Bean(value = "tableOperator")
    public TableOperatorFactoryBean getTableOperatorFactoryBean(@Qualifier("jdbcTemplate") JdbcTemplate jdbcTemplate,
    		@Qualifier("localdialect") Dialect dialect,@Qualifier("hotentCoreProperties") HotentCoreProperties hotentCoreProperties)
    {
       TableOperatorFactoryBean tableOperatorFactoryBean = new TableOperatorFactoryBean();
       tableOperatorFactoryBean.setJdbcTemplate(jdbcTemplate);
       tableOperatorFactoryBean.setDialect(dialect);
       tableOperatorFactoryBean.setDbType(hotentCoreProperties.getJdbc().getDbType());
       return tableOperatorFactoryBean;
    }
}
