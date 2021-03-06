package com.zty.springboot.config4jta;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.mysql.cj.jdbc.MysqlXADataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration // == xml
@MapperScan(basePackages = {"com.zty.springboot.mapper.goods"}, sqlSessionFactoryRef = "goodsdbSqlSessionFactory")
public class GoodsDBDataSourceConfig4jtaConfig {

    @Value("${spring.datasource.goodsdb.jdbcUrl}")
    private String jdbcUrl ;
    @Value("${spring.datasource.goodsdb.username}")
    private String username ;
    @Value("${spring.datasource.goodsdb.driverClassName}")
    private String driverClassName ;
    @Value("${spring.datasource.goodsdb.password}")
    private String password ;



    /**
     * 配置一个数据源的bean
     *
     * @return
     */
    @Bean(name="goodsdbDataSource")
    public DataSource goodsdbDataSource() {
        //创建一个数据源的bean
        MysqlXADataSource xaDataSource = new MysqlXADataSource();
        xaDataSource.setUrl(jdbcUrl);
        xaDataSource.setUser(username);
        xaDataSource.setPassword(password);

        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        atomikosDataSourceBean.setXaDataSource(xaDataSource);
        atomikosDataSourceBean.setUniqueResourceName("goodsdbDataSource");
        atomikosDataSourceBean.setMaxPoolSize(30);
        atomikosDataSourceBean.setMinPoolSize(5);

        return atomikosDataSourceBean;
    }

    @Bean(name="goodsdbSqlSessionFactory")
    public SqlSessionFactory goodsdbSqlSessionFactory(@Qualifier("goodsdbDataSource") DataSource goodsdbDataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(goodsdbDataSource);
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name="goodsdbSqlSessionTemplate")
    public SqlSessionTemplate goodsdbSqlSessionTemplate(@Qualifier("goodsdbSqlSessionFactory") SqlSessionFactory goodsdbSqlSessionFactory) {
        return new SqlSessionTemplate(goodsdbSqlSessionFactory);
    }
    
}