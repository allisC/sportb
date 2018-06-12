package com.epam.training.sportsbetting;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;

import com.epam.training.sportsbetting.config.SpringConfigurationSpringData;
import com.epam.training.sportsbetting.db.SportBettingDatabase;
import com.epam.training.sportsbetting.service.ResultGenerator;
import com.epam.training.sportsbetting.service.SportBettingService;
import com.epam.training.sportsbetting.ui.IO;
import com.epam.training.sportsbetting.ui.ReadFromConsole;
import com.epam.training.sportsbetting.ui.WriteToConsole;

@Configuration
@PropertySource("classpath:config.properties")
@EnableAspectJAutoProxy
@ComponentScan(basePackages = { "com.epam.training.sportsbetting" })
@Import(SpringConfigurationSpringData.class)
public class AppConfig {

    @Bean
    public IO io() {
        return new IO();
    }

    @Bean
    public ResultGenerator resultGenerator() {
        return new ResultGenerator();
    }

    @Bean
    public SportBettingService sportsBettingService() {
        return new SportBettingService(resultGenerator());
    }

    @Bean
    public SportBettingDatabase sportBettingDatabase() {
        return new SportBettingDatabase();
    }

    @Bean
    public ReadFromConsole readFromConsole() {
        return new ReadFromConsole(sportsBettingService(), sportBettingDatabase(), io());
    }

    @Bean
    public WriteToConsole writeToConsole() {
        return new WriteToConsole(io());
    }

    @Bean
    public BettingSession bettingSession() {
        return new BettingSession(sportBettingDatabase(), sportsBettingService(), readFromConsole(), writeToConsole());
    }

    @Bean
    public App app() {
        return new App(sportBettingDatabase(), sportsBettingService(), readFromConsole(), writeToConsole(), bettingSession());
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
    
    @Bean   //new
    public static PropertySourcesPlaceholderConfigurer config() {
    PropertySourcesPlaceholderConfigurer result = new PropertySourcesPlaceholderConfigurer();
    result.setFileEncoding("UTF-8");
    return result;
    }

}
