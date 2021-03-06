package com.zty.springboot.config4jta;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.mysql.cj.jdbc.MysqlXADataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration // == xml
@MapperScan(basePackages = {"com.zty.springboot.mapper.users"}, sqlSessionFactoryRef = "userdbSqlSessionFactory")
public class UserDBDataSourceConfig4jtaConfig {

    @Value("${spring.datasource.userdb.jdbcUrl}")
    private String jdbcUrl ;
    @Value("${spring.datasource.userdb.username}")
    private String username ;
    @Value("${spring.datasource.userdb.driverClassName}")
    private String driverClassName ;
    @Value("${spring.datasource.userdb.password}")
    private String password ;





    /**
     * 配置一个数据源的bean
     *
     * @return
     */
    @Bean(name="userdbDataSource")
    public DataSource userdbDataSource() {
        //创建一个数据源的bean
        MysqlXADataSource xaDataSource = new MysqlXADataSource();
        xaDataSource.setUrl(jdbcUrl);
        xaDataSource.setUser(username);
        xaDataSource.setPassword(password);

        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        atomikosDataSourceBean.setXaDataSource(xaDataSource);
        atomikosDataSourceBean.setUniqueResourceName("userdbDataSource");
        atomikosDataSourceBean.setMaxPoolSize(30);
        atomikosDataSourceBean.setMinPoolSize(5);

        return atomikosDataSourceBean;
    }

    @Bean(name="userdbSqlSessionFactory")
    public SqlSessionFactory userdbSqlSessionFactory(@Qualifier("userdbDataSource") DataSource userdbDataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(userdbDataSource);
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name="userdbSqlSessionTemplate")
    public SqlSessionTemplate userdbSqlSessionTemplate(@Qualifier("userdbSqlSessionFactory") SqlSessionFactory userdbSqlSessionFactory) {
        return new SqlSessionTemplate(userdbSqlSessionFactory);
    }

}