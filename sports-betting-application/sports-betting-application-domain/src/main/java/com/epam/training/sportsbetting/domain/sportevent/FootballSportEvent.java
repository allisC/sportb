package com.epam.training.sportsbetting.domain.sportevent;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class FootballSportEvent extends SportEvent {

    public FootballSportEvent() {
    }

    public FootballSportEvent(String title, LocalDate startDate, LocalDate endDate) {
        super(title, startDate,  endDate);
    
    }
}
