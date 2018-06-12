package com.epam.training.sportsbetting.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class SpringConfigurationDataSource {
	
private static String dbUrl = "jdbc:mysql://localhost/sportsbetting_csilla_vizmathy";
	private static String username = "root";
	private static String password = "root";
	
	@Bean
	public DataSource dataSource() {
		return new DriverManagerDataSource(dbUrl, username, password);
	}
}
