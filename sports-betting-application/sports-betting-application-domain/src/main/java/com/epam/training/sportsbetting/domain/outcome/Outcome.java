package com.epam.training.sportsbetting.domain.outcome;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
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
    @OneToMany(fetch = FetchType.EAGER)
    @Cascade(value = CascadeType.ALL)
    public List<OutcomeOdd> outcomeOdds = new ArrayList<OutcomeOdd>();

    public Outcome(String value) {
        super();
        this.value = value;
    }

    public Outcome() {
    }

    public String getValue() {
        return value;
    }

    void setValue(String value) {
        this.value = value;
    }


    // @Override
    // public String toString() {
    // return "Outcome [value=" + value + ", outcomeOdds=" + outcomeOdds + "]";
    // }

}
