package com.zb.p2p.trade.web.config;

import com.zb.cloud.logcenter.sql.mybatis.ZbMybatisIntercreptor;
import com.zb.fincore.common.plugin.MybatisSQLFormatInterceptor;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@AutoConfigureAfter({DataSourceConfiguration.class})
@MapperScan(value = "com.zb.p2p.trade.persistence.dao")
public class MybatisConfiguration extends MybatisAutoConfiguration {
	
	public MybatisConfiguration(MybatisProperties properties, ObjectProvider<Interceptor[]> interceptorsProvider,
			ResourceLoader resourceLoader, ObjectProvider<DatabaseIdProvider> databaseIdProvider) {
		super(properties, interceptorsProvider, resourceLoader, databaseIdProvider);
	}

	@Resource(name = "masterDataSource")
	private DataSource masterDataSource;
  
	@Resource(name = "slaveDataSource")
	private DataSource slaveDataSource;
 
	@Bean(name="sqlSessionFactory")
  	public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
		//return super.sqlSessionFactory(dataSource);
		
		 SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
		 fb.setDataSource(dataSource);// 指定数据源(这个必须有，否则报错)
		 fb.setPlugins(new Interceptor[]{new ZbMybatisIntercreptor(), new MybatisSQLFormatInterceptor()});
		 // 下边两句仅仅用于*.xml文件，如果整个持久层操作不需要使用到xml文件的话（只用注解就可以搞定），则不加
		 //fb.setTypeAliasesPackage(env.getProperty("mybatis.typeAliasesPackage"));// 指定基包
		 fb.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:mybatis-config.xml"));
		 return fb.getObject();
	}
 
	@Bean(name="dataSource")
	public ReadWriteSplitRoutingDataSource dataSource(){
		ReadWriteSplitRoutingDataSource proxy = new ReadWriteSplitRoutingDataSource();
		Map<Object,Object> targetDataResources = new HashMap<Object, Object>();
		targetDataResources.put(DataBaseContextHolder.DataBaseType.MASTER.getCode(), masterDataSource);
		targetDataResources.put(DataBaseContextHolder.DataBaseType.SLAVE.getCode(), slaveDataSource);
    	proxy.setDefaultTargetDataSource(masterDataSource);//默认源
    	proxy.setTargetDataSources(targetDataResources);
    	return proxy;
	}

	/**
	 * MyBatis自动参与到spring事务管理中，要引用的数据源一致即可，否则事务管理会不起作用
	 * @return
	 */
	@Bean(name = "transactionManager")
	public DataSourceTransactionManager transactionManagers() {
		return new DataSourceTransactionManager(dataSource());
	}

}
