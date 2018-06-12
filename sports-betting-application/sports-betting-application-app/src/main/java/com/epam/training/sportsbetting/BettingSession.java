package com.epam.training.sportsbetting;

import com.epam.training.sportsbetting.db.SportBettingDatabase;
import com.epam.training.sportsbetting.domain.outcome.OutcomeOdd;
import com.epam.training.sportsbetting.domain.sportevent.SportEvent;
import com.epam.training.sportsbetting.domain.wager.Wager;
import com.epam.training.sportsbetting.service.SportBettingService;
import com.epam.training.sportsbetting.ui.ReadFromConsole;
import com.epam.training.sportsbetting.ui.WriteToConsole;
import java.util.List;

public class BettingSession {

    private OutcomeOdd selectedOdd;
    private double choosedAmount;

    private final SportBettingDatabase sbDatabase;;
    private final SportBettingService sbService;
    private final ReadFromConsole readFromConsole;
    private final WriteToConsole writeToConsole;

    public BettingSession(SportBettingDatabase sbDatabase, SportBettingService sbService, ReadFromConsole readFromConsole, WriteToConsole writeToConsole) {
        super();
        this.sbDatabase = sbDatabase;
        this.sbService = sbService;
        this.readFromConsole = readFromConsole;
        this.writeToConsole = writeToConsole;
    }

    public void makeWagers() {
        List<Wager> wagers = sbDatabase.getWagers();
        selectedOdd = selectOdd();
        while (selectedOdd != null) {
            sbService.createWager(sbDatabase.getPlayer(), choosedAmount, selectedOdd, wagers);
            writeToConsole.printBalance(sbDatabase.getPlayer());
            if (sbDatabase.getPlayer().getBalance() > 0) {
                selectedOdd = selectOdd();
            } else {
                selectedOdd = null;
            }
        }
        List<SportEvent> sportEvents = sbDatabase.getSportEvents();
        for (Wager wager : wagers) {
            wager.setSportEvent(sportEvents.get(0));
        }
    }

    OutcomeOdd selectOdd() {
        writeToConsole.printOutcomes(sbDatabase.getSportEvents());
        sbDatabase.setSavedOdds(sbService.getOdds(sbDatabase.getSportEvents()));
        boolean dataIsValid = false;
        int choosedNumber;
        OutcomeOdd result = null;
        do {
            String readLine = readFromConsole.readOneLine();
            if ((readLine).equals("q")) {
                dataIsValid = true;
            } else {
                choosedNumber = Integer.parseInt(readLine);
                if (choosedNumber <= sbDatabase.getSavedOdds().size() && choosedNumber > 0) {
                    dataIsValid = true;
                    choosedAmount = readFromConsole.readAmount(sbDatabase.getPlayer());
                    if (choosedAmount > 0) {
                        result = sbDatabase.getSavedOdds().get(choosedNumber - 1);
                    }
                }
            }
        } while (!dataIsValid);
        return result;
    }
}
