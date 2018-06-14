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

import com.epam.training.sportsbetting.db.SportsEventRepository;
import com.epam.training.sportsbetting.db.WagerRepository;
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
import com.epam.training.sportsbetting.domain.user.Currency;
import com.epam.training.sportsbetting.domain.user.Player;//
import com.epam.training.sportsbetting.domain.wager.Wager;
import com.epam.training.sportsbetting.service.SportBettingService;
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
    private SportsEventRepository footballSportsEventRepository;

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private WagerRepository wagerRepository;

    public App(SportBettingService sbService, ReadFromConsole readFromConsole, WriteToConsole writeToConsole,
            BettingSession bettingSession) {
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

            springDataExample(applicationContext);

            App app = applicationContext.getBean(App.class);
            app.runApp();
        }

    }

    private static void springDataExample(ApplicationContext context) {
        // PlayerRepository pr = context.getBean(PlayerRepository.class);
        pr = context.getBean(PlayerRepository.class);
        br = context.getBean(BetRepository.class);
        Player player = createPlayer();

        pr.save(player);

        System.out.println("findPlayerByName(Cs) : " + pr.findPlayerByName("Cs"));
        System.out.println("br.findAll() " + br.findAll());

    }

    private static Player createPlayer() { // (String name, String accountNumber, double balance, Currency currency, LocalDate birthDate) {
        Player player = new Player();
        player.setName("Cs");
        player.setAccountNumber("22");
        player.setBalance(66.0);
        player.setCurrency(Currency.EUR);
        player.setBirthDate(LocalDate.of(2000, 6, 30));
        return player;

    }

    public void runApp() {
        this.player = playerRepository.findOne(1);
        Player player = playerRepository.findOne(1);
        generateSportEvents();
        readFromConsole.readPlayerDataFromConsole();
        writeToConsole.printWelcomeMessage(player);
        bettingSession.makeWagers();
        sbService.setResults(player, getWagers());
        writeToConsole.listWinnerOutcomes(player, getWagers());
        // toXml();
        pr.save(this.player);

    }

    public List<Wager> getWagersBeetweenDate(LocalDateTime from, LocalDateTime to) {
        List<Wager> wagers = getWagers();
        List<Wager> wagersBetweenDates = new ArrayList<>();
        for (Wager wager : wagers) {
            if (wager.getTimeStamp().isAfter(from) && wager.getTimeStamp().isBefore(to))
                wagersBetweenDates.add(wager);
        }
        return wagersBetweenDates;
    }

    public void toXml() {
        List<Wager> wagers = getWagers();
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

    private List<Wager> getWagers() {
        Iterator<Wager> iterator = wagerRepository.findAll().iterator();
        List<Wager> wagers = new ArrayList<>();
        while (iterator.hasNext()) {
            wagers.add(iterator.next());
        }
        return wagers;
    }

    public void generateSportEvents() {


        List<SportEvent> sportEvents = new ArrayList<SportEvent>();
        SportEvent event = new FootballSportEvent("Arsenal vs Chelsea", LocalDate.of(2016, 01, 01), LocalDate.of(2016, 01, 05));
        sportEvents.add(event);

        Outcome outcome1 = new Outcome("1");
        List<Outcome> listOutcomes1 = new ArrayList<>();
        listOutcomes1.add(outcome1);
        Bet bet1 = new Bet(event, "Oliver Giroud will score ", listOutcomes1);
        event.getBets().add(bet1);
        OutcomeOdd oOdd = new OutcomeOdd(outcome1, 10.0, LocalDate.of(2016, 02, 03), LocalDate.of(2016, 02, 06));
        outcome1.outcomeOdds.add(oOdd);

        Outcome outcome3 = new Outcome("3");
        List<Outcome> listOutcomes2 = new ArrayList<>();
        listOutcomes2.add(outcome3);
        Bet bet2 = new Bet(event, "the number of scored goals will be ", listOutcomes2);
        event.getBets().add(bet2);
        OutcomeOdd oOdd2 = new OutcomeOdd(outcome3, 1.3, LocalDate.of(2016, 02, 03), LocalDate.of(2016, 02, 06));
        outcome3.outcomeOdds.add(oOdd2);

        Outcome outcome5 = new Outcome("Arsenal");
        Outcome outcome6 = new Outcome("Chelsea");
        List<Outcome> listOutcomes3 = new ArrayList<>();
        listOutcomes3.add(outcome5);
        listOutcomes3.add(outcome6);
        Bet bet3 = new Bet(event, "the winner will be ", listOutcomes3);
        event.getBets().add(bet3);
        OutcomeOdd oOdd3 = new OutcomeOdd(outcome5, 4.0, LocalDate.of(2016, 02, 03), LocalDate.of(2016, 02, 04));
        outcome5.outcomeOdds.add(oOdd3);
        OutcomeOdd oOdd4 = new OutcomeOdd(outcome6, 2.5, LocalDate.of(2016, 02, 05), LocalDate.of(2016, 02, 06));
        outcome6.outcomeOdds.add(oOdd4);

        footballSportsEventRepository.save(sportEvents.get(0));

    }
}
