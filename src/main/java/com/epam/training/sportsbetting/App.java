package com.epam.training.sportsbetting;

import java.io.File;

import com.epam.training.sportsbetting.config.SpringConfigurationDataSource;
import com.epam.training.sportsbetting.config.SpringConfigurationJpa;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.epam.training.sportsbetting.domain.bet.Bet;
import com.epam.training.sportsbetting.domain.outcome.Outcome;
import com.epam.training.sportsbetting.domain.outcome.OutcomeOdd;
import com.epam.training.sportsbetting.domain.sportevent.FootballSportEvent;
import com.epam.training.sportsbetting.domain.sportevent.SportEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

import com.epam.training.sportsbetting.config.SpringConfigurationSpringData;
import com.epam.training.sportsbetting.db.BetRepository;
import com.epam.training.sportsbetting.db.PlayerRepository;
import com.epam.training.sportsbetting.db.SportsEventRepository;
import com.epam.training.sportsbetting.db.WagerRepository;
import com.epam.training.sportsbetting.domain.user.Currency;
import com.epam.training.sportsbetting.domain.user.Player;//
import com.epam.training.sportsbetting.domain.wager.Wager;
import com.epam.training.sportsbetting.service.SportBettingService;
import com.epam.training.sportsbetting.service.TestDataGenerator;
import com.epam.training.sportsbetting.ui.ReadFromConsole;
import com.epam.training.sportsbetting.ui.WriteToConsole;

public class App {

    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    private final SportBettingService sbService;
    private final ReadFromConsole readFromConsole;
    private final WriteToConsole writeToConsole;
    private final BettingSession bettingSession;
    Player player;
    static PlayerRepository pr;
    static BetRepository br;

    @Autowired
    private Environment env;

    @Autowired
    private TestDataGenerator testDataGenerator;

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private WagerRepository wagerRepository;

    public App(SportBettingService sbService, ReadFromConsole readFromConsole, WriteToConsole writeToConsole, BettingSession bettingSession) {
        super();
        this.sbService = sbService;
        this.readFromConsole = readFromConsole;
        this.writeToConsole = writeToConsole;
        this.bettingSession = bettingSession;
    }

    public static void main(String[] args) {

        LOG.info("Application starting");

        try (AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class,
                SpringConfigurationDataSource.class, SpringConfigurationSpringData.class, SpringConfigurationJpa.class)) {

            App app = applicationContext.getBean(App.class);
            app.runApp();
        }
    }


    public void runApp() {
    	testDataGenerator.generateSportEvents();
    	
        Player player = readFromConsole.readPlayerDataFromConsole();
        playerRepository.save(player);
        
        player = playerRepository.findOne(1);
        
        writeToConsole.printWelcomeMessage(player);
        
        List<Wager> wagers = bettingSession.makeWagers(player);
        //TODO: persist wagers + player
        playerRepository.save(player);
       // wagerRepository.save(wagers);  //Cs, elszáll nullpointer   
        
        sbService.setResults(player, wagers);
        
        writeToConsole.listWinnerOutcomes(player, wagers);  
        
        // toXml();
        // pr.save(this.player);

    }

}
