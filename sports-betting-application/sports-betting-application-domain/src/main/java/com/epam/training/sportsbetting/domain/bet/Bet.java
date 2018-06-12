package com.epam.training.sportsbetting.domain.bet;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.epam.training.sportsbetting.domain.outcome.Outcome;
import com.epam.training.sportsbetting.domain.sportevent.SportEvent;

@XmlType(propOrder = { "description" })
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Bet {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @XmlTransient
    @Transient    
    private SportEvent sportEvent;
    private String description;
    @XmlTransient
    @Transient
    private List<Outcome> outcomes = new ArrayList<Outcome>(); // Different outcomes of the bet (list)

    public Bet(SportEvent sportEvent, String description, List<Outcome> outcomes) {
        super();
        this.sportEvent = sportEvent;
        this.description = description;
        this.outcomes = outcomes;
    }

    public String getDescription() {
        return description;
    }

    public List<Outcome> getOutcomes() {
        return outcomes;
    }

    @Override
    public String toString() {
        return "Bet [sportEvent=" + sportEvent + ", description=" + description + ", outcomes=" + outcomes + "]";
    }

}
