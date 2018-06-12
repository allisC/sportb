package com.epam.training.sportsbetting.domain.outcome;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "value", "outcomeOdds" })
public class Outcome {

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

    public List<OutcomeOdd> outcomeOdds = new ArrayList<OutcomeOdd>();

    // @Override
    // public String toString() {
    // return "Outcome [value=" + value + ", outcomeOdds=" + outcomeOdds + "]";
    // }

}
