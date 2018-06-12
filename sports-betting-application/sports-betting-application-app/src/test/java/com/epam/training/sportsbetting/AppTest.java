package com.epam.training.sportsbetting;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.epam.training.sportsbetting.db.SportBettingDatabase;
import com.epam.training.sportsbetting.domain.user.Player;
import com.epam.training.sportsbetting.service.SportBettingService;
import com.epam.training.sportsbetting.ui.ReadFromConsole;
import com.epam.training.sportsbetting.ui.WriteToConsole;

public class AppTest {

    @InjectMocks
    private App underTest;

    @Mock
    private SportBettingDatabase sbDatabase;

    @Mock
    private SportBettingService sbService;

    @Mock
    private ReadFromConsole readFromConsole;

    @Mock
    private WriteToConsole writeToConsole;

    @Mock
    private BettingSession bettingSession;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRunAppShouldCallAllMethods() {
        // Given
        InOrder inOrder = BDDMockito.inOrder(sbDatabase, sbService, readFromConsole, writeToConsole, bettingSession);
        Player player = new Player();
        Mockito.when(sbDatabase.getPlayer()).thenReturn(player);

        // When
        underTest.runApp();

        // Then
        inOrder.verify(readFromConsole).readPlayerDataFromConsole();
        inOrder.verify(writeToConsole).printWelcomeMessage(player);
        inOrder.verify(bettingSession).makeWagers();
        inOrder.verify(sbService).setResults(sbDatabase.getPlayer(), sbDatabase.getWagers());
        inOrder.verify(writeToConsole).listWinnerOutcomes(sbDatabase.getPlayer(), sbDatabase.getWagers());

    }
}
