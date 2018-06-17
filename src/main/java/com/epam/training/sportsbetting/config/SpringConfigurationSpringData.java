package com.epam.training.sportsbetting.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.epam.training.sportsbetting.db")
public class SpringConfigurationSpringData {

}
