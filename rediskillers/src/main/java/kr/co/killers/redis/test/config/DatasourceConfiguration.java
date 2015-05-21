package kr.co.killers.redis.test.config;

import java.io.IOException;

import javax.naming.NamingException;
import javax.sql.DataSource;

import net.sf.log4jdbc.Log4jdbcProxyDataSource;
import net.sf.log4jdbc.tools.Log4JdbcCustomFormatter;
import net.sf.log4jdbc.tools.LoggingType;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DatasourceConfiguration {

	@Bean
	public SqlSessionFactoryBean sqlSessionFactoryBean(final DataSource dataSource, final ApplicationContext applicationContext) throws IOException {

		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();

		factoryBean.setDataSource(dataSource);
		factoryBean.setConfigLocation(applicationContext.getResource("classpath:/conf/spring/mybatis/mybatis-config.xml"));
		factoryBean.setMapperLocations(applicationContext.getResources("classpath*:/conf/sql/*.xml"));
		return factoryBean;
	}

	@Bean(destroyMethod = "clearCache")
	public SqlSessionTemplate sqlSession(final SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}

	@Bean
	public DataSource jdbcDataSource() {
		JndiObjectFactoryBean dataSource = new JndiObjectFactoryBean();
		dataSource.setExpectedType(DataSource.class);
		dataSource.setJndiName("java:/comp/env/jdbc/TestDB");
		DriverManagerDataSource dataSource2 = new DriverManagerDataSource();
		dataSource2.setDriverClassName("org.mariadb.jdbc.Driver");
		dataSource2.setUrl("jdbc:mariadb://172.19.242.193:3306/testDB");
		dataSource2.setUsername("test");
		dataSource2.setPassword("password");
		dataSource.setDefaultObject(dataSource2);
		try {
			dataSource.afterPropertiesSet();
		} catch (IllegalArgumentException | NamingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return (DataSource) dataSource.getObject();
	}

	public DataSource dataSource() {
		Log4jdbcProxyDataSource logDataSource = new Log4jdbcProxyDataSource(jdbcDataSource());
		Log4JdbcCustomFormatter logFormatter =new Log4JdbcCustomFormatter();

		logFormatter.setLoggingType(LoggingType.MULTI_LINE);
		logFormatter.setSqlPrefix("SQL:::");
		logDataSource.setLogFormatter(logFormatter);

		return logDataSource;
		
	}
}
