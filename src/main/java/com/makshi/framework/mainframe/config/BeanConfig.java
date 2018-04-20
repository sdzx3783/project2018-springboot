package com.makshi.framework.mainframe.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.hotent.core.mybatis.Dialect;
import com.hotent.core.table.DialectFactoryBean;
import com.hotent.core.table.TableOperatorFactoryBean;
import com.makshi.framework.mainframe.config.properties.HotentCoreProperties;

@Configuration
public class BeanConfig {
	@Bean(value = "sessionLocaleResolver")
    public SessionLocaleResolver getSessionLocaleResolver()
    {
        return new SessionLocaleResolver();
    }
	@Bean(value = "dialect")
    public DialectFactoryBean getDialectFactoryBean(@Qualifier("jdbcTemplate") JdbcTemplate jdbcTemplate)
    {
		DialectFactoryBean DialectFactoryBean = new DialectFactoryBean();
       return DialectFactoryBean;
    }
	@Bean(value = "tableOperator")
    public TableOperatorFactoryBean getTableOperatorFactoryBean(@Qualifier("jdbcTemplate") JdbcTemplate jdbcTemplate,
    		@Qualifier("dialect") Dialect dialect,@Qualifier("hotentCoreProperties") HotentCoreProperties hotentCoreProperties)
    {
       TableOperatorFactoryBean tableOperatorFactoryBean = new TableOperatorFactoryBean();
       tableOperatorFactoryBean.setJdbcTemplate(jdbcTemplate);
       tableOperatorFactoryBean.setDialect(dialect);
       tableOperatorFactoryBean.setDbType(hotentCoreProperties.getJdbc().getDbType());
       return tableOperatorFactoryBean;
    }
}
