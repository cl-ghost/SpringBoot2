package com.cl.sbw.db.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import com.cl.sbw.db.datasource.ThreadLocalRountingDataSource;
import com.cl.sbw.db.unil.Until.DataSourceType;



/**
 * 数据库连接配置
 * @author cl
 *
 */
@Configuration
@EnableTransactionManagement
public class DruidDBConfig {
	
	@Value("${druid.type}")
	private Class<? extends DataSource> dataSourceType;
	
	//******************** 数据源配置 start  *****************************
		@Bean(name = "master")
		@Primary
		@ConfigurationProperties(prefix = "druid.db.master")
		public DataSource masterDataSource() {
			//DataSource masterDB = DataSourceBuilder.create().type(dataSourceType).build();
			DruidDataSource masterDB = new DruidDataSource();
			masterDB.setTimeBetweenEvictionRunsMillis(1000*60);
			masterDB.setMinEvictableIdleTimeMillis(1000*30);
			return initDataSourceInfo(masterDB);
		}
		
		
		@Bean(name = "slave")
		@ConfigurationProperties(prefix = "druid.db.slave")
		public DataSource slaveDataSource() {
			DruidDataSource slaveDataSource = new DruidDataSource();
			slaveDataSource.setTimeBetweenEvictionRunsMillis(60*1000);
			slaveDataSource.setMinEvictableIdleTimeMillis(1000*30);
			return initDataSourceInfo(slaveDataSource);
		}
		
		
		public DruidDataSource initDataSourceInfo(DruidDataSource dataSource) {
			dataSource.setTimeBetweenConnectErrorMillis(1000*60);
			dataSource.setMinEvictableIdleTimeMillis(1000*60*30);
			dataSource.setValidationQuery("select 1 from dual");
			dataSource.setTestWhileIdle(true);
			dataSource.setTestOnBorrow(true);
			dataSource.setTestOnReturn(false);
			dataSource.setPoolPreparedStatements(true);
			dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
			dataSource.setMaxWait(1000*45);
			return dataSource;
		}
		
	//****************** 数据源配置 end ***********************************
		
		@Bean(name = "stat-filter")
		public StatFilter druidStateFilter() {
			StatFilter filter = new StatFilter();
			filter.setMergeSql(true);
			filter.setSlowSqlMillis(2000);
			filter.setLogSlowSql(true);
			return filter;
		}
	
		@Bean(name="druid-stat-interceptor")
		public DruidStatInterceptor druidDruidStatInterceptor() {
			DruidStatInterceptor interceptor = new DruidStatInterceptor();
			return interceptor;
		}
		
	
		@Bean(name="druid-stat-pointcut")
		public JdkRegexpMethodPointcut druidJdkRegexpMethodPointcut() {
			JdkRegexpMethodPointcut jdkReg = new JdkRegexpMethodPointcut();
			jdkReg.setPatterns("com.cl.sbw.service.*","com.cl.sbw.db.*");
			return jdkReg;
		}
		
		
		//**************** 数据源配置
		@Bean(name="dataSourceConfig")
		public AbstractRoutingDataSource druidThreadLocLRountingDataSource() {
			ThreadLocalRountingDataSource threadDataSource = new ThreadLocalRountingDataSource();
			 Map<Object, Object> targetDataResources = new HashMap<>();
			 targetDataResources.put(DataSourceType.MASTER, masterDataSource());
			 targetDataResources.put(DataSourceType.SLAVE, slaveDataSource());
			 threadDataSource.setDefaultTargetDataSource(masterDataSource());
			 threadDataSource.setTargetDataSources(targetDataResources);
			 threadDataSource.afterPropertiesSet();
			 return threadDataSource;
		}

		
		@Bean(name="sqlSessionFactory")
		public SqlSessionFactoryBean sqlSessionFactory() {
			SqlSessionFactoryBean sqlSession = new SqlSessionFactoryBean();
			sqlSession.setDataSource(druidThreadLocLRountingDataSource());
			sqlSession.setTypeAliasesPackage("com.cl.sbw.dao.pojo");
			ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			try {
				sqlSession.setMapperLocations(resolver.getResources("classpath:Mapper/*.xml"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			return sqlSession;
		}
		
		public MapperScannerConfigurer mapperScannerConfig() {
			MapperScannerConfigurer mapperScanner = new MapperScannerConfigurer();
			mapperScanner.setSqlSessionFactoryBeanName("sqlSessionFactory");
			mapperScanner.setBasePackage("com.cl.sbw");
			return mapperScanner;
		}
		
		@Bean
	    public ServletRegistrationBean druidServlet() {
	        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
	        //白名单：
	        servletRegistrationBean.addInitParameter("allow","127.0.0.1");
	        //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
	        servletRegistrationBean.addInitParameter("deny","192.168.1.73");
	        //登录查看信息的账号密码.
	        servletRegistrationBean.addInitParameter("loginUsername","admin2");
	        servletRegistrationBean.addInitParameter("loginPassword","123456");
	        //是否能够重置数据.
	        servletRegistrationBean.addInitParameter("resetEnable","false");
	        return servletRegistrationBean;
	    }
		
		@Bean
	    public FilterRegistrationBean filterRegistrationBean() {
	        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
	        filterRegistrationBean.setFilter(new WebStatFilter());
	        filterRegistrationBean.addUrlPatterns("/*");
	        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
	        return filterRegistrationBean;
	    }
}
