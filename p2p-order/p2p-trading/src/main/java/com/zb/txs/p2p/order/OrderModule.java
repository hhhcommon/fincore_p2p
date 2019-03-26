package com.zb.txs.p2p.order;


import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.zb.cloud.logcenter.sql.mybatis.ZbMybatisIntercreptor;
import com.zb.txs.p2p.order.httpclient.*;
import lombok.Data;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import retrofit2.Retrofit;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@ComponentScan("com.zb.txs.p2p.order")
@MapperScan(value = "com.zb.txs.p2p.order.persistence.mapper", sqlSessionFactoryRef = "orderSqlSessionFactory")
public class OrderModule {

    @Bean(name = "orderTransactionManager")
    public DataSourceTransactionManager transactionManager(DataSource ptopDataSource) {
        return new DataSourceTransactionManager(ptopDataSource);
    }

    @Bean(name = "orderSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(DataSource ptopDataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setPlugins(new Interceptor[]{new ZbMybatisIntercreptor()});
        sessionFactory.setDataSource(ptopDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/order/*.xml"));
        return sessionFactory.getObject();
    }

    @Configuration
    @ConfigurationProperties(prefix = "ptop.datasource")
    @Data
    static class DruidDatasourceConfiguration {
        private HashMap<String, String> config = new HashMap<>();

        @Bean(name = "ptopDataSource")
        public DataSource druidDataSource() throws Exception {
            return DruidDataSourceFactory.createDataSource(config);
        }
    }

    @Bean(name = "orderSqlSessionTemplate")
    public SqlSessionTemplate orderSqlSessionTemplate(@Qualifier("orderSqlSessionFactory") SqlSessionFactory orderSqlSessionFactory) {
        return new SqlSessionTemplate(orderSqlSessionFactory);
    }

    @Bean
    public OrderClient orderClient(@Qualifier("payRetrofit") Retrofit retrofit) {
        return retrofit.create(OrderClient.class);
    }
    
    @Bean
    public FinancialorderClient financialOrderClient(@Qualifier("financialRetrofit") Retrofit retrofit) {
        return retrofit.create(FinancialorderClient.class);
    }

    @Bean
    public TradeprocessClient tradeprocessClient(@Qualifier("financialRetrofit") Retrofit retrofit) {
        return retrofit.create(TradeprocessClient.class);
    }

    @Bean
    public PmsClient pmsClient(@Qualifier("pmsRetrofit") Retrofit retrofit) {
        return retrofit.create(PmsClient.class);
    }

    @Bean
    public MemberClient memberClient(@Qualifier("memberRetrofit") Retrofit retrofit) {
        return retrofit.create(MemberClient.class);
    }

    @Bean
    public TxsProductClient txsProductClient(@Qualifier("txsProductRetrofit") Retrofit retrofit) {
        return retrofit.create(TxsProductClient.class);
    }
}
