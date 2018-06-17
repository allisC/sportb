package com.epam.training.sportsbetting.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;

import com.epam.training.sportsbetting.domain.bet.Bet;
import com.epam.training.sportsbetting.domain.outcome.Outcome;
import com.epam.training.sportsbetting.domain.outcome.OutcomeOdd;
import com.epam.training.sportsbetting.domain.sportevent.SportEvent;
import com.epam.training.sportsbetting.domain.user.Player;
import com.epam.training.sportsbetting.domain.wager.Wager;

public class WriteToConsole {

    @Autowired
    private MessageSource messageSource;

    @Value("${locale.default}")
    private Locale locale;

    public final IO io;

    public WriteToConsole(IO io) {
        super();
        this.io = io;
    }

    public void printWelcomeMessage(Player player) {
        io.print(messageSource.getMessage("welcome", new Object[] { player.getName() }, locale));
        // io.print("Welcome " + player.getName());
        io.print(messageSource.getMessage("your_balance_is", new Object[] { player.getBalance() }, locale));
        // io.print(", your balance is " + player.getBalance());
    }

    public void printOutcomes(List<SportEvent> sportEvents) {
        // io.println(" Please choose an outcome to bet on! (choose a number or press q for quit)");
        io.print(messageSource.getMessage("please_choose_an_outcome", null, locale));

        int index = 1;
        for (SportEvent spE : sportEvents) {
            for (Bet bet : spE.bets) {
                for (Outcome outcome : bet.getOutcomes()) {
                    for (OutcomeOdd outcomeOdd : outcome.outcomeOdds) {
                        // io.println(index++ + " Bet on the " + spE.getTitle() + " sport event, " + bet.getDescription() + outcome.getValue()
                        // + ". The odd on this is " + outcomeOdd.getOddValue() + ", valid from " + outcomeOdd.getValidFrom() + " to "
                        // + outcomeOdd.getValidTo());
                        io.print(messageSource.getMessage("bet_on_the", new Object[] { index++, spE.getTitle(), bet.getDescription(), outcome.getValue(),
                                outcomeOdd.getOddValue(), outcomeOdd.getValidFrom(), outcomeOdd.getValidTo() }, locale));
                    }
                }
            }
        }
    }

    public void listWinnerOutcomes(Player player, List<Wager> wagers) {
        // io.println(" The winner bets are: ");
        io.print(messageSource.getMessage("the_winner_bets", null, locale));
        int i = 0;
        for (Wager wager : wagers) {
            i++;
            if (wager.isWon() == true) {
                // io.println("The winner is Outcome " + i + " " + "value=" + wager.getOutcomeodd().getOddValue() + ", outcomeOdds="
                // + wager.getOutcomeodd().getOddValue() + " and valid from " + wager.getOutcomeodd().getValidFrom() + " to "
                // + wager.getOutcomeodd().getValidTo());
                io.print(messageSource.getMessage("the_winner_outcome", new Object[] { i, wager.getOutcomeodd().getOddValue(),
                        wager.getOutcomeodd().getOddValue(), wager.getOutcomeodd().getValidFrom(), wager.getOutcomeodd().getValidTo() }, locale));
            }
        }
        printBalance(player);
    }

    public void printBalance(Player player) {
        // io.println("Your new balance is " + player.getBalance() + " " + player.getCurrency());
        io.print(messageSource.getMessage("your_new_balance", new Object[] { player.getBalance(), player.getCurrency() }, locale));
    }

}
