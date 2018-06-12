package com.epam.training.sportsbetting.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import com.epam.training.sportsbetting.db.SportBettingDatabase;
import com.epam.training.sportsbetting.domain.user.Currency;
import com.epam.training.sportsbetting.domain.user.Player;
import com.epam.training.sportsbetting.service.SportBettingService;

public class ReadFromConsoleTest {

    @InjectMocks
    private ReadFromConsole underTest;

    @Mock
    private IO io;

    @Mock
    private SportBettingService sbService;

    @Mock
    private SportBettingDatabase sbDatabase;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test// (expected = NumberFormatException.class)
    public void testReadPlayerDataFromConsoleShouldCall5Method() {
        // Given
        Player player = new Player();
        Mockito.when(sbDatabase.getPlayer()).thenReturn(player);
        Mockito.when(io.readOneLine()).thenReturn("H").thenReturn("3").thenReturn("55").thenReturn("h")
        .thenReturn("HUG").thenReturn("HUM").thenReturn("HUF").thenReturn("1988-01-01");
        //String enteredData = io.readOneLine();
        //Mockito.doThrow(new NumberFormatException()).when(Long.parseLong(enteredData));
        Mockito.when(sbService.stringToDate("1988-01-01")).thenReturn(LocalDate.of(1988, 1, 1));

        // When
        underTest.readPlayerDataFromConsole();

        // Then
        assertEquals("H", player.getName());
        assertEquals("3", player.getAccountNumber());
        assertEquals(55.0, player.getBalance(), 0.001);
        assertEquals(Currency.HUF, player.getCurrency());
//        assertEquals(Currency.EUR, player.getCurrency());
//        assertEquals(Currency.USD, player.getCurrency());   
        assertEquals(LocalDate.of(1988, 1, 1), player.getBirthDate());

    }

//    @Test(expected = Exception.class)
//    public void testReadDateOfBirthShouldThrowExceptionWhenEnteredDataIsNotValid() {
//        // Given
//        Player player = new Player();
//        Mockito.when(sbDatabase.getPlayer()).thenReturn(player);
//        Mockito.when(io.readOneLine()).thenReturn("k");
//        Mockito.when(sbService.stringToDate("k")).thenThrow(new Exception());
//        // When
//        underTest.readDateOfBirth(player);
//    }

    @Test
    public void testReadAmountShouldReturnZeroWhenInputIsQ() {
        // Given
        Player player = new Player();
        Mockito.when(sbDatabase.getPlayer()).thenReturn(player);
        Mockito.when(io.readOneLine()).thenReturn("q");
        // When
        double result = underTest.readAmount(player);
        // Then
        assertEquals(0, result, 0.001);
    }

    @Test
    public void testReadAmountShouldReturnRightAmount() {
        // Given
        Player player = new Player();
        Mockito.when(sbDatabase.getPlayer()).thenReturn(player);
        player.setBalance(10.0);
        Mockito.when(io.readOneLine()).thenReturn("2");
        // Then
        assertEquals(8.0, player.getBalance() - underTest.readAmount(player), 0.001);
    }

//    @Test(expected = NumberFormatException.class)
//    public void testReadAmountShouldThrowExceptionWhenInputIsNotValid() {
//        // Given
//        Player player = new Player();
//        Mockito.when(sbDatabase.getPlayer()).thenReturn(player);
//        player.setBalance(1.0);
//        Mockito.when(io.readOneLine()).thenReturn("2x");
//        Double.parseDouble(io.readOneLine());
//        // When
//        underTest.readAmount(player);
//    }

}
