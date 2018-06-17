package com.epam.training.sportsbetting;

public class AppTest {

//    @InjectMocks
//    private App underTest;
//
//    @Mock
//    private SportBettingDatabase sbDatabase;
//
//    @Mock
//    private SportBettingService sbService;
//
//    @Mock
//    private ReadFromConsole readFromConsole;
//
//    @Mock
//    private WriteToConsole writeToConsole;
//
//    @Mock
//    private BettingSession bettingSession;
//
//    @Before
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void testRunAppShouldCallAllMethods() {
//        // Given
//        InOrder inOrder = BDDMockito.inOrder(sbDatabase, sbService, readFromConsole, writeToConsole, bettingSession);
//        Player player = new Player();
//        Mockito.when(sbDatabase.getPlayer()).thenReturn(player);
//
//        // When
//        underTest.runApp();
//
//        // Then
//        inOrder.verify(readFromConsole).readPlayerDataFromConsole();
//        inOrder.verify(writeToConsole).printWelcomeMessage(player);
//        inOrder.verify(bettingSession).makeWagers();
//        inOrder.verify(sbService).setResults(sbDatabase.getPlayer(), sbDatabase.getWagers());
//        inOrder.verify(writeToConsole).listWinnerOutcomes(sbDatabase.getPlayer(), sbDatabase.getWagers());

//    }
}
