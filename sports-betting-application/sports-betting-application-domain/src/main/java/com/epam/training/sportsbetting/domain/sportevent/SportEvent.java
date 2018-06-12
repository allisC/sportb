package com.epam.training.sportsbetting.domain.sportevent;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.epam.training.sportsbetting.domain.bet.Bet;

@XmlType(propOrder = { "title", "startDate", "endDate", "bets" })
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class SportEvent {

    protected String title;
    protected LocalDate startDate;
    protected LocalDate endDate;

    public List<Bet> bets = new ArrayList<Bet>();
    @XmlTransient
    protected Result result = new Result();

    protected SportEvent(String title, LocalDate startDate, LocalDate endDate) {
        super();
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<Bet> getBets() {
        return bets;
    }

    void setBets(List<Bet> bets) {
        this.bets = bets;
    }

    public Result getResult() {
        return result;
    }

    void setResult(Result result) {
        this.result = result;
    }

}
