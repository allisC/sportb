package com.epam.training.sportsbetting;

import com.epam.training.sportsbetting.db.*;
import com.epam.training.sportsbetting.domain.outcome.OutcomeOdd;
import com.epam.training.sportsbetting.domain.sportevent.FootballSportEvent;
import com.epam.training.sportsbetting.domain.sportevent.SportEvent;
import com.epam.training.sportsbetting.domain.user.Player;
import com.epam.training.sportsbetting.domain.wager.Wager;
import com.epam.training.sportsbetting.service.SportBettingService;
import com.epam.training.sportsbetting.ui.ReadFromConsole;
import com.epam.training.sportsbetting.ui.WriteToConsole;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BettingSession {

    private OutcomeOdd selectedOdd;
    private double choosedAmount;

    private final SportBettingService sbService;
    private final ReadFromConsole readFromConsole;
    private final WriteToConsole writeToConsole;


    public BettingSession(SportBettingService sbService, ReadFromConsole readFromConsole, WriteToConsole writeToConsole) {
        super();
        this.sbService = sbService;
        this.readFromConsole = readFromConsole;
        this.writeToConsole = writeToConsole;
    }

    public List<Wager> makeWagers(Player player) {
        List<Wager> wagers = new ArrayList<>();
        selectedOdd = selectOdd();

        while (selectedOdd != null) {
            sbService.createWager(player, choosedAmount, selectedOdd, wagers);
            writeToConsole.printBalance(player);
            if (player.getBalance() > 0) {
                selectedOdd = selectOdd();
            } else {
                selectedOdd = null;
            }
        }
        return wagers;
    }

    OutcomeOdd selectOdd() {
        List<SportEvent> sportEvents = sbService.getSportEvents();
        writeToConsole.printOutcomes(sportEvents);
        ArrayList<OutcomeOdd> odds = sbService.getOdds(sportEvents);
//        outcomeOddRepository.save(odds);
        boolean dataIsValid = false;
        int choosedNumber;
        OutcomeOdd result = null;
        do {
            String readLine = readFromConsole.readOneLine();
            if ((readLine).equals("q")) {
                dataIsValid = true;
            } else {
                choosedNumber = Integer.parseInt(readLine);
                if (choosedNumber <= odds.size() && choosedNumber > 0) {
                    dataIsValid = true;
                    choosedAmount = readFromConsole.readAmount(sbService.getCurrentPlayer());
//                    choosedAmount = readFromConsole.readAmount(playerRepository.findOne(1));
                    if (choosedAmount > 0) {
                        result = odds.get(choosedNumber - 1);
                    }
                }
            }
        } while (!dataIsValid);
        return result;
    }

}
