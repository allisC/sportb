package com.epam.training.sportsbetting.domain.bet;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


import com.epam.training.sportsbetting.domain.outcome.Outcome;
import com.epam.training.sportsbetting.domain.sportevent.SportEvent;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@XmlType(propOrder = { "description" })
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Bet {

    public Bet() {
    }

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @XmlTransient  //Todo: maskepp 
    private Integer id;
    @XmlTransient
    @Transient //TODO: abstract class a SportEvent
    private SportEvent sportEvent;
    private String description;
    @XmlTransient
    @OneToMany(fetch = FetchType.EAGER)
    @Cascade(value = CascadeType.ALL)
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
