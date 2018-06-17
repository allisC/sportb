package com.epam.training.sportsbetting.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.epam.training.sportsbetting.db.OutcomeOddRepository;
import com.epam.training.sportsbetting.db.PlayerRepository;
import com.epam.training.sportsbetting.db.SportsEventRepository;
import com.epam.training.sportsbetting.db.WagerRepository;
import com.epam.training.sportsbetting.domain.bet.Bet;
import com.epam.training.sportsbetting.domain.outcome.Outcome;
import com.epam.training.sportsbetting.domain.outcome.OutcomeOdd;
import com.epam.training.sportsbetting.domain.sportevent.SportEvent;
import com.epam.training.sportsbetting.domain.user.Player;
import com.epam.training.sportsbetting.domain.wager.Wager;

public class SportBettingService {

    private final ResultGenerator resultGenerator;
    @Autowired
    private WagerRepository wagerRepository;
    @Autowired
    private SportsEventRepository sportsEventRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private OutcomeOddRepository outcomeOddRepository;

    public SportBettingService(ResultGenerator resultGenerator) {
        super();
        this.resultGenerator = resultGenerator;
    }

    public void createWager(Player player, double choosedAmount, OutcomeOdd selectedOdd, List<Wager> wagers) {
        wagers.add(new Wager(player, selectedOdd, choosedAmount));
        player.setBalance(player.getBalance() - choosedAmount);
    }

    public void setResults(Player player, List<Wager> wagers) {
        double wonAmount = 0;
        for (Wager wager : wagers) {
            wager.setWon(resultGenerator.isWon());
            wager.setProcessed(true);
            if (wager.isWon()) {
                wonAmount += 
                        wager.getAmount() * 
                        wager.getOutcomeodd()
                        .getOddValue();
            }
        }
        // TODO: save player + wagers' status
        player.setBalance(player.getBalance() + wonAmount);
        wagerRepository.save(wagers);
    }

    public LocalDate stringToDate(String givenDate) {
        String[] dateString = new String[3];
        dateString = givenDate.split("\\.|-|:");
        int[] dateInt = new int[3];
        for (int i = 0; i < 3; i++) {
            dateInt[i] = Integer.parseInt(dateString[i]);
        }
        LocalDate givenDate2 = LocalDate.of(dateInt[0], dateInt[1], dateInt[2]);
        return givenDate2;
    }

    public ArrayList<OutcomeOdd> getOdds(List<SportEvent> sportEvents) {
        ArrayList<OutcomeOdd> savedOdd = new ArrayList<OutcomeOdd>();
        for (SportEvent spE : sportEvents) {
            for (Bet bet : spE.bets) {
                for (Outcome outcome : bet.getOutcomes()) {
                    for (OutcomeOdd outcomeOdd : outcome.outcomeOdds) {
                        savedOdd.add(outcomeOdd);
                    }
                }
            }
        }
        outcomeOddRepository.save(savedOdd);
        return savedOdd;
    }

    // CS: ::add
    public List<SportEvent> getSportEvents() {
        List<SportEvent> sportEvents = new ArrayList<>();
        sportsEventRepository.findAll().forEach(sportEvents::add);
        return sportEvents;
    }

    public Player getCurrentPlayer() {
        return playerRepository.findOne(1);
    }

}
