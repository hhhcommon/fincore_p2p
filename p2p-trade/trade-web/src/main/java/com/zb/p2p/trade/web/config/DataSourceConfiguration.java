package com.zb.p2p.trade.web.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DataSourceConfiguration {
	
	private static Logger LOGGER = LoggerFactory.getLogger(DataSourceConfiguration.class);

	@Value("${datasource.type}")
	private Class<? extends DataSource> dataSourceType;
	
 
	@Bean(name = "masterDataSource")
	@Primary
	@ConfigurationProperties(prefix = "datasource.master") 
	public DataSource masterDataSource() throws SQLException{
		DataSource masterDataSource = DataSourceBuilder.create().type(dataSourceType).build();
		LOGGER.info("========MASTER: {}=========", masterDataSource);
		return masterDataSource;
	}
 
	@Bean(name = "slaveDataSource")
	@ConfigurationProperties(prefix = "datasource.slave")
	public DataSource slaveDataSource(){
		DataSource slaveDataSource = DataSourceBuilder.create().type(dataSourceType).build();
		LOGGER.info("========SLAVE: {}=========", slaveDataSource);
		return slaveDataSource;
	}
  
	
  
}
