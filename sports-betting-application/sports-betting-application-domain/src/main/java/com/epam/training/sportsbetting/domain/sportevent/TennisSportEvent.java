package com.epam.training.sportsbetting.domain.sportevent;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class TennisSportEvent extends SportEvent {

    public TennisSportEvent(String title, LocalDate startDate, LocalDate endDate) {
        super(title, startDate, endDate);
      
        
    }
}
