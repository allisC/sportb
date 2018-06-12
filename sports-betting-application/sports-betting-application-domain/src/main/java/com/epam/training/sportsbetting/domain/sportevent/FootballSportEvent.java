package com.epam.training.sportsbetting.domain.sportevent;

import java.time.LocalDate;

public class FootballSportEvent extends SportEvent {

    public FootballSportEvent(String title, LocalDate startDate, LocalDate endDate) {
        super(title, startDate,  endDate);
    
    }
}
