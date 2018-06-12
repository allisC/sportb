package com.epam.training.sportsbetting;

import java.io.File;
import java.math.BigDecimal;

import com.epam.training.sportsbetting.config.SpringConfigurationDataSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

import com.epam.training.sportsbetting.config.SpringConfigurationJpa;
import com.epam.training.sportsbetting.config.SpringConfigurationSpringData;
import com.epam.training.sportsbetting.db.PlayerRepository;
import com.epam.training.sportsbetting.db.SportBettingDatabase;
import com.epam.training.sportsbetting.domain.user.Currency;
import com.epam.training.sportsbetting.domain.user.Player;//
import com.epam.training.sportsbetting.domain.wager.Wager;
import com.epam.training.sportsbetting.service.SportBettingService;
import com.epam.training.sportsbetting.ui.ReadFromConsole;
import com.epam.training.sportsbetting.ui.WriteToConsole;

public class App {

    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    private final SportBettingDatabase sbDatabase;
    private final SportBettingService sbService;
    private final ReadFromConsole readFromConsole;
    private final WriteToConsole writeToConsole;
    private final BettingSession bettingSession;
    Player player;

    @Autowired
    private Environment env;

    public App(SportBettingDatabase sbDatabase, SportBettingService sbService, ReadFromConsole readFromConsole, WriteToConsole writeToConsole,
            BettingSession bettingSession) {
        super();
        this.sbDatabase = sbDatabase;
        this.sbService = sbService;
        this.readFromConsole = readFromConsole;
        this.writeToConsole = writeToConsole;
        this.bettingSession = bettingSession;
        this.player = sbDatabase.getPlayer();
    }

    public static void main(String[] args) {

        LOG.info("Application starting");
//        try (AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class)) {
//            App app = applicationContext.getBean(App.class);
//            app.runApp();
//        }
        
        try (AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class, 
                SpringConfigurationDataSource.class,
                SpringConfigurationSpringData.class, 
                SpringConfigurationJpa.class)) {
            
            springDataExample(applicationContext);
            
            App app = applicationContext.getBean(App.class);
            app.runApp();
        }
        

    }
    
    private static void springDataExample(ApplicationContext context) {
        PlayerRepository pr = context.getBean(PlayerRepository.class);
        Player player = createPlayer();

        pr.save(player);

        System.out.println(pr.findPlayerByName("Cs"));

        }
    
    
    private static Player createPlayer() { //(String name, String accountNumber, double balance, Currency currency, LocalDate birthDate) {
        Player player = new Player();
        player.setName("Cs");
        player.setAccountNumber("22");
        player.setBalance(66.0);
        player.setCurrency(Currency.EUR);
        player.setBirthDate(LocalDate.of(2000, 6, 30));
        return player;

    }

    public void runApp() {
        readFromConsole.readPlayerDataFromConsole();
        writeToConsole.printWelcomeMessage(sbDatabase.getPlayer());
        bettingSession.makeWagers();
        sbService.setResults(sbDatabase.getPlayer(), sbDatabase.getWagers());
        writeToConsole.listWinnerOutcomes(sbDatabase.getPlayer(), sbDatabase.getWagers());
        toXml();
    }

    public List<Wager> getWagersBeetweenDate(LocalDateTime from, LocalDateTime to) {
        List<Wager> wagers = sbDatabase.getWagers();
        List<Wager> wagersBeetweenDates = new ArrayList<>();
        for (Wager wager : wagers) {
            if (wager.getTimeStamp().isAfter(from) && wager.getTimeStamp().isBefore(to))
                wagersBeetweenDates.add(wager);
        }
        return wagersBeetweenDates;
    }

    public void toXml() {
        List<Wager> wagers = sbDatabase.getWagers();
        File file = new File(env.getProperty("xmlFileName"));
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Wager.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(wagers.get(0), file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        LOG.info("Xml file is ready");

    }
}
