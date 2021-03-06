package com.epam.training.sportsbetting.domain.sportevent;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.epam.training.sportsbetting.domain.bet.Bet;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@XmlType(propOrder = { "title", "startDate", "endDate", "bets" })
@XmlAccessorType(XmlAccessType.FIELD)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
public abstract class SportEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    protected String title;
    protected LocalDate startDate;
    protected LocalDate endDate;
    @OneToMany(fetch = FetchType.EAGER)
    @Cascade(value = CascadeType.ALL)
    public List<Bet> bets = new ArrayList<Bet>();
    // @XmlTransient
    // protected Result result = new Result();

    public SportEvent() {
    }

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

    // public Result getResult() {
    // return result;
    // }
    //
    // void setResult(Result result) {
    // this.result = result;
    // }

}
