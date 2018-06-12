package com.epam.training.sportsbetting.db;

import java.util.ArrayList;
import java.util.List;

import com.epam.training.sportsbetting.domain.outcome.OutcomeOdd;
import com.epam.training.sportsbetting.domain.sportevent.SportEvent;
import com.epam.training.sportsbetting.domain.user.Player;
import com.epam.training.sportsbetting.domain.wager.Wager;

//no unit test needed
public class SportBettingDatabase {

    private Player player = new Player();
    private List<Wager> wagers = new ArrayList<Wager>();
    private List<SportEvent> sportEvents;

    private List<OutcomeOdd> savedOdds;

    public SportBettingDatabase() {
        sportEvents = new TestDataGenerator().generateSportEvents();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<Wager> getWagers() {
        return wagers;
    }

    public void setWagers(List<Wager> wagers) {
        this.wagers = wagers;
    }

    public List<SportEvent> getSportEvents() {
        return sportEvents;
    }

    public void setSportEvents(List<SportEvent> sportEvents) {
        this.sportEvents = sportEvents;
    }

    public List<OutcomeOdd> getSavedOdds() {
        return savedOdds;
    }

    public void setSavedOdds(ArrayList<OutcomeOdd> savedOdds) {
        this.savedOdds = savedOdds;
    }

}
