package com.epam.training.sportsbetting.ui;

import static org.junit.Assert.assertEquals;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.epam.training.sportsbetting.db.SportBettingDatabase;
import com.epam.training.sportsbetting.domain.bet.Bet;
import com.epam.training.sportsbetting.domain.outcome.Outcome;
import com.epam.training.sportsbetting.domain.outcome.OutcomeOdd;
import com.epam.training.sportsbetting.domain.sportevent.FootballSportEvent;
import com.epam.training.sportsbetting.domain.sportevent.SportEvent;
import com.epam.training.sportsbetting.domain.user.Currency;
import com.epam.training.sportsbetting.domain.user.Player;
import com.epam.training.sportsbetting.domain.wager.Wager;
import com.epam.training.sportsbetting.service.ResultGenerator;
import com.epam.training.sportsbetting.service.SportBettingService;

public class WriteToConsoleTest {

    @InjectMocks
    private WriteToConsole underTest;

    @Mock
    private IO io;

    @Mock
    private SportBettingService sbService;

    @Mock
    private SportBettingDatabase sbDatabase;

    @Mock
    private ResultGenerator resultGenerator;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() {
        System.setOut(System.out);
    }

    final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Test // working
    public void testPrintWelcomeMessageShouldPrintMessage() {
        // Given
//        OutputStream os = new ByteArrayOutputStream();
//        PrintStream ps = new PrintStream(os);
//        System.setOut(ps);

    	
        Player player = new Player();
        player.setName("Joe");
        player.setBalance(3.0);
        player.setCurrency(Currency.HUF);
        // When
        underTest.printWelcomeMessage(player);
        // Then
        //assertEquals("Welcome Joe, your balance is 3.0", os.toString());
        Mockito.verify(io).print("Welcome Joe");
        Mockito.verify(io).print(", your balance is 3.0");

    }

    @Test // working
    public void testPrintOutcomesShouldPrintSavedOdd() {
        // Given
        SportEvent spEvent = new FootballSportEvent("Arsenal vs. Chelsea", LocalDate.now(), LocalDate.now());
        List<SportEvent> sportEvents = new ArrayList<>(Arrays.asList(spEvent));
        Outcome outcome1 = new Outcome("1");
        List<Outcome> listOutcomes1 = new ArrayList<>();
        listOutcomes1.add(outcome1);
        Bet bet1 = new Bet(spEvent, "Oliver Giroud will score ", listOutcomes1);
        spEvent.getBets().add(bet1);
        OutcomeOdd oOdd = new OutcomeOdd(outcome1, 7.0, LocalDate.of(2018, 02, 03), LocalDate.of(2018, 02, 06));
        outcome1.outcomeOdds.add(oOdd);
        ArrayList<OutcomeOdd> expected = new ArrayList<>(Arrays.asList(oOdd));
     // When
        underTest.printOutcomes(sportEvents);
   // Then
        
        Mockito.verify(io).println(" Please choose an outcome to bet on! (choose a number or press q for quit)");
        Mockito.verify(io).println( "1 Bet on the Arsenal vs. Chelsea sport event, Oliver Giroud will score 1. The odd on this is 7.0, valid from 2018-02-03 to 2018-02-06"
        );
//        assertEquals(expected, result);
    }

    @Test
    public void testListWinerOutcomesShouldPrintWinnerOutcomesAndBalanceWhenWon() {
        // Given
        OutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        Player player = new Player();
        player.setBalance(3.0);
        player.setCurrency(Currency.HUF);

        OutcomeOdd outcomeodd = new OutcomeOdd(new Outcome("something"), 9.0, null, null);
        Wager wager1 = new Wager(player, outcomeodd, 8.0);
        List<Wager> wagers = new ArrayList<Wager>(Arrays.asList(wager1));
        wager1.setWon(true);
        // When
        underTest.listWinnerOutcomes(player, wagers);
        // Then    
        Mockito.verify(io).println(" The winner bets are: ");
        Mockito.verify(io).println("The winner is Outcome " + 1 + " " + "value="
                + wager1.getOutcomeodd().getOddValue() + ", outcomeOdds=" + wager1.getOutcomeodd().getOddValue() + " and valid from "
                + wager1.getOutcomeodd().getValidFrom() + " to " + wager1.getOutcomeodd().getValidTo());
    }

//    @Test 
//    public void testListWinerOutcomesShouldPrintBalanceWhenNotWon() {
//        // Given
//        OutputStream outputStream = new ByteArrayOutputStream();
//        PrintStream printStream = new PrintStream(outputStream);
//        System.setOut(printStream);
//        Player player = new Player();
//        player.setBalance(5.0);
//        player.setCurrency(Currency.EUR);
//        OutcomeOdd outcomeodd = new OutcomeOdd(new Outcome("something"), 9.0, null, null);
//        Wager wager = new Wager(player, outcomeodd, 8.0);
//        List<Wager> wagers = new ArrayList<Wager>(Arrays.asList(wager));
//        wager.setWon(false);
//        // When
//        underTest.listWinnerOutcomes(player, wagers);
//        // Then
//        assertEquals(" The winner bets are: " + System.getProperty("line.separator") + "Your new balance is 5.0 EUR", outputStream.toString());
//    }

    @Test // working
    public void testPrintBalanceShouldPrintBalance() {
        // Given
        Player player = new Player();
        player.setBalance(3.0);
        player.setCurrency(Currency.HUF);
        // When
        underTest.printBalance(player);
        // Then
        Mockito.verify(io).println("Your new balance is 3.0 HUF");

    }

}
