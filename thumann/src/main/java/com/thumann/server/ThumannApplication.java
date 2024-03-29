package com.thumann.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAuthorizationServer
@EnableResourceServer
@SpringBootApplication
@EnableTransactionManagement
public class ThumannApplication extends SpringBootServletInitializer
{

    private static Class<ThumannApplication> applicationClass = ThumannApplication.class;

    public static void main( String[] args )
    {
        SpringApplication.run( applicationClass, args );
    }

    @Override
    protected SpringApplicationBuilder configure( SpringApplicationBuilder application )
    {
        return application.sources( applicationClass );
    }

//	@Bean
//	public DataSource dataSource()
//	{
//		MysqlDataSource datasource = new MysqlDataSource();
//		datasource.setUrl("jdbc:mysql://localhost:8888/schule");
//		datasource.setUser("root");
//		datasource.setPassword("rienderien");
////		spring.datasource.url=
////			spring.datasource.username=root
////			spring.datasource.password=rienderien
////			spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
//		return datasource;
//	}
//
//	@Bean
//	public PlatformTransactionManager txManager()
//	{
//		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource());
//		dataSourceTransactionManager.setRollbackOnCommitFailure(true);
//		return dataSourceTransactionManager; // (2)
//	}

}
