package com.epam.training.sportsbetting.domain.outcome;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "value", "outcomeOdds" })
@Entity
public class Outcome {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    
    private String value;

    public Outcome(String value) {
        super();
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    void setValue(String value) {
        this.value = value;
    }

    @OneToMany
    public List<OutcomeOdd> outcomeOdds = new ArrayList<OutcomeOdd>();

    // @Override
    // public String toString() {
    // return "Outcome [value=" + value + ", outcomeOdds=" + outcomeOdds + "]";
    // }

}
