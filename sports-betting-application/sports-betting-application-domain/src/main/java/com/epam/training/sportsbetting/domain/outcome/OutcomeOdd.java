package com.epam.training.sportsbetting.domain.outcome;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "oddValue", "validFrom", "validTo" })
@Entity
public class OutcomeOdd {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    // Outcome outcome;
    private double oddValue;
    private LocalDate validFrom;
    private LocalDate validTo;

    public OutcomeOdd(Outcome outcome, double oddValue, LocalDate validFrom, LocalDate validTo) {
        super();
        // this.outcome = outcome;
        this.oddValue = oddValue;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }

    public double getOddValue() {
        return oddValue;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    // public Outcome getOutcome() {
    // return outcome;
    // }
    //
    // public void setOutcome(Outcome outcome) {
    // this.outcome = outcome;
    // }

    public void setOddValue(double oddValue) {
        this.oddValue = oddValue;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }

    // @Override
    // public String toString() {
    // return "OutcomeOdd [outcome=" + outcome + ", oddValue=" + oddValue + ", validFrom=" + validFrom + ", validTo=" + validTo + "]";
    // }

}
