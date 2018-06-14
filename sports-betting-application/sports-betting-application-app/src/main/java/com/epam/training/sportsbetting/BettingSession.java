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

    @Autowired
    private WagerRepository wagerRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private SportsEventRepository sportsEventRepository;
    @Autowired
    private FootBallSportsEventRepository footBallSportsEventRepository;
    @Autowired
    private OutcomeOddRepository outcomeOddRepository;

    public BettingSession(SportBettingService sbService, ReadFromConsole readFromConsole, WriteToConsole writeToConsole) {
        super();
        this.sbService = sbService;
        this.readFromConsole = readFromConsole;
        this.writeToConsole = writeToConsole;
    }

    public void makeWagers() {
        List<Wager> wagers = new ArrayList<>();
        Iterator<Wager> iterator = wagerRepository.findAll().iterator();
        while (iterator.hasNext()) wagers.add(iterator.next());
        selectedOdd = selectOdd();
        Player player = playerRepository.findOne(1);
        while (selectedOdd != null) {
            sbService.createWager(player, choosedAmount, selectedOdd, wagers);
            writeToConsole.printBalance(player);
            if (player.getBalance() > 0) {
                selectedOdd = selectOdd();
            } else {
                selectedOdd = null;
            }
        }
        SportEvent sportEvent = sportsEventRepository.findAll().iterator().next();
        for (Wager wager : wagers) {
            wager.setSportEvent(sportEvent);
        }
    }

    OutcomeOdd selectOdd() {
        List<SportEvent> sportEvents = getSportEvents();
        writeToConsole.printOutcomes(sportEvents);
        ArrayList<OutcomeOdd> odds = sbService.getOdds(sportEvents);
        outcomeOddRepository.save(odds);
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
                    choosedAmount = readFromConsole.readAmount(playerRepository.findOne(1));
                    if (choosedAmount > 0) {
                        result = odds.get(choosedNumber - 1);
                    }
                }
            }
        } while (!dataIsValid);
        return result;
    }

    private List<SportEvent> getSportEvents() {
        Iterator<FootballSportEvent> iterator = footBallSportsEventRepository.findAll().iterator();
        List<SportEvent> sportEvents = new ArrayList<>();
        while (iterator.hasNext()) {
            sportEvents.add(iterator.next());
        }
        return sportEvents;
    }
}
