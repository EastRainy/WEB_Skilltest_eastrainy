package com.practice.Skilltest;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.Reader;

@SpringBootApplication
public class SkilltestApplication {

	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception{

		Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

		reader.close();

		return sqlSessionFactory;
	}

	public static void main(String[] args) {
		SpringApplication.run(SkilltestApplication.class, args);
	}


}
