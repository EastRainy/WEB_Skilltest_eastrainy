package com.practice.Skilltest;


import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;


@SpringBootApplication
public class SkilltestApplication {

	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception{


		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSource);
		sqlSessionFactory.setConfigLocation(new PathMatchingResourcePatternResolver()
				.getResource("classpath:mybatis/mybatis-config.xml"));

		return sqlSessionFactory.getObject();
	}

	public static void main(String[] args) {
		SpringApplication.run(SkilltestApplication.class, args);
	}
}
