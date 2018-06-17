package com.epam.training.sportsbetting.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.epam.training.sportsbetting.domain.outcome.Outcome;
import com.epam.training.sportsbetting.domain.outcome.OutcomeOdd;
import com.epam.training.sportsbetting.domain.user.Player;
import com.epam.training.sportsbetting.domain.wager.Wager;

public class SportBettingServiceTest {

    @Mock
    private ResultGenerator resultGenerator;

    @InjectMocks
    private SportBettingService underTest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSetResultShouldReturnWonAmount() {
        // GIVEN
        OutcomeOdd outcomeodd = new OutcomeOdd(new Outcome("valami"), 9.0, null, null);
        Player player = new Player();
        Wager wager = new Wager(player, outcomeodd, 8.0);
        List<Wager> wagers = new ArrayList<Wager>(Arrays.asList(wager));
        BDDMockito.given(resultGenerator.isWon()).willReturn(true);
        // WHEN
        underTest.setResults(player, wagers);
        // THEN
        Assert.assertEquals(72.0, player.getBalance(), 0.001);

    }

    @Test
    public void testCreateWager() {
        // GIVEN
        Player player = new Player();
        OutcomeOdd outcomeodd = new OutcomeOdd(new Outcome("anything"), 0.0, null, null);
        Wager wager = new Wager(player, outcomeodd, 8.0);
        List<Wager> wagers = new ArrayList<Wager>(Arrays.asList(wager));
        player.setBalance(3.0);
        // WHEN
        underTest.createWager(player, 2.0, outcomeodd, wagers);

        // THEN
        Assert.assertEquals(wager, wagers.get(0));
        Assert.assertEquals(1.0, player.getBalance(), 0.001);

    }

    @Test
    public void testSetResultShouldReturnZero() {
        // GIVEN
        OutcomeOdd outcomeodd = new OutcomeOdd(new Outcome("valami"), 9.0, null, null);
        Player player = new Player();
        Wager wager = new Wager(player, outcomeodd, 8.0);
        List<Wager> wagers = new ArrayList<Wager>(Arrays.asList(wager));
        BDDMockito.given(resultGenerator.isWon()).willReturn(false);

        // WHEN
        underTest.setResults(player, wagers);

        // THEN
        Assert.assertEquals(0, player.getBalance(), 0.001);
    }

    @Test
    public void testStringToDateShouldReturntheStringDateinLocalDateFormat() {
        // GIVEN
        String givenDate = "2000.01.01.";
        LocalDate expected = LocalDate.of(2000, 1, 1);
        // WHEN
        LocalDate result = underTest.stringToDate(givenDate);
        // THEN
        Assert.assertEquals(expected, result);
    }
}
