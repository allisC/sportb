package com.epam.training.sportsbetting;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.epam.training.sportsbetting.db.SportBettingDatabase;
import com.epam.training.sportsbetting.domain.outcome.Outcome;
import com.epam.training.sportsbetting.domain.outcome.OutcomeOdd;
import com.epam.training.sportsbetting.domain.sportevent.FootballSportEvent;
import com.epam.training.sportsbetting.domain.sportevent.SportEvent;
import com.epam.training.sportsbetting.domain.user.Currency;
import com.epam.training.sportsbetting.domain.user.Player;
import com.epam.training.sportsbetting.domain.wager.Wager;
import com.epam.training.sportsbetting.service.SportBettingService;
import com.epam.training.sportsbetting.ui.IO;
import com.epam.training.sportsbetting.ui.ReadFromConsole;
import com.epam.training.sportsbetting.ui.WriteToConsole;

public class BettingSessionTest {

    @InjectMocks
    private BettingSession underTest;

    @Mock
    private SportBettingService sbService;

    @Mock
    private SportBettingDatabase sbDatabase;

    @Mock
    private ReadFromConsole readFromConsole;

    @Mock
    private WriteToConsole writeToConsole;

    @Mock
    private IO io;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSelectOddShouldReturnTheSelectedOutcomeOdd() {
        // Given selectodd-hoz

        SportEvent spEvent = new FootballSportEvent("Arsenal vs. Chelsea", LocalDate.now(), LocalDate.now());
        List<SportEvent> sportEvents = new ArrayList<>(Arrays.asList(spEvent));
        Mockito.when(sbDatabase.getSportEvents()).thenReturn(sportEvents);
        Outcome outcome1 = new Outcome("1");
        OutcomeOdd oOdd = new OutcomeOdd(outcome1, 7.0, LocalDate.of(2018, 02, 03), LocalDate.of(2018, 02, 06));
        ArrayList<OutcomeOdd> listOutcomeOdds = new ArrayList<>();
        listOutcomeOdds.add(oOdd);
        Mockito.when(sbService.getOdds(sportEvents)).thenReturn(listOutcomeOdds);
        Mockito.when(readFromConsole.readOneLine()).thenReturn("1").thenReturn("q");
        Mockito.when(sbDatabase.getSavedOdds()).thenReturn(listOutcomeOdds);
        Mockito.when(readFromConsole.readAmount(sbDatabase.getPlayer())).thenReturn(1.0);

        // When selectodd-hoz
        OutcomeOdd resultOdd = underTest.selectOdd();
        OutcomeOdd resultNull = underTest.selectOdd();

        // Then selectodd-hoz
        assertEquals(oOdd, resultOdd);
        assertEquals(null, resultNull);

        Mockito.verify(sbDatabase, times(4)).getSportEvents();
        Mockito.verify(writeToConsole, times(2)).printOutcomes(sportEvents);
        Mockito.verify(readFromConsole, times(2)).readOneLine();
        Mockito.verify(sbDatabase, times(2)).getSavedOdds();
        Mockito.verify(readFromConsole).readAmount(sbDatabase.getPlayer());

    }

}
