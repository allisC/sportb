package com.epam.training.sportsbetting.db;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epam.training.sportsbetting.domain.bet.Bet;
import com.epam.training.sportsbetting.domain.outcome.Outcome;
import com.epam.training.sportsbetting.domain.outcome.OutcomeOdd;
import com.epam.training.sportsbetting.domain.sportevent.FootballSportEvent;
import com.epam.training.sportsbetting.domain.sportevent.SportEvent;
import com.epam.training.sportsbetting.domain.user.Currency;
import com.epam.training.sportsbetting.domain.user.Player;

//No unitTests needed
public class TestDataGenerator {
    


    public List<SportEvent> generateSportEvents() {
        List<SportEvent> sportEvents = new ArrayList<SportEvent>();
        SportEvent event = new FootballSportEvent("Arsenal vs Chelsea", LocalDate.of(2016, 01, 01), LocalDate.of(2016, 01, 05));
        sportEvents.add(event);

        Outcome outcome1 = new Outcome("1");
        List<Outcome> listOutcomes1 = new ArrayList<>();
        listOutcomes1.add(outcome1);
        Bet bet1 = new Bet(event, "Oliver Giroud will score ", listOutcomes1);
        event.getBets().add(bet1);
        OutcomeOdd oOdd = new OutcomeOdd(outcome1, 10.0, LocalDate.of(2016, 02, 03), LocalDate.of(2016, 02, 06));
        outcome1.outcomeOdds.add(oOdd);

        Outcome outcome3 = new Outcome("3");
        List<Outcome> listOutcomes2 = new ArrayList<>();
        listOutcomes2.add(outcome3);
        Bet bet2 = new Bet(event, "the number of scored goals will be ", listOutcomes2);
        event.getBets().add(bet2);
        OutcomeOdd oOdd2 = new OutcomeOdd(outcome3, 1.3, LocalDate.of(2016, 02, 03), LocalDate.of(2016, 02, 06));
        outcome3.outcomeOdds.add(oOdd2);

        Outcome outcome5 = new Outcome("Arsenal");
        Outcome outcome6 = new Outcome("Chelsea");
        List<Outcome> listOutcomes3 = new ArrayList<>();
        listOutcomes3.add(outcome5);
        listOutcomes3.add(outcome6);
        Bet bet3 = new Bet(event, "the winner will be ", listOutcomes3);
        event.getBets().add(bet3);
        OutcomeOdd oOdd3 = new OutcomeOdd(outcome5, 4.0, LocalDate.of(2016, 02, 03), LocalDate.of(2016, 02, 04));
        outcome5.outcomeOdds.add(oOdd3);
        OutcomeOdd oOdd4 = new OutcomeOdd(outcome6, 2.5, LocalDate.of(2016, 02, 05), LocalDate.of(2016, 02, 06));
        outcome6.outcomeOdds.add(oOdd4);
        return sportEvents;
    }
}
