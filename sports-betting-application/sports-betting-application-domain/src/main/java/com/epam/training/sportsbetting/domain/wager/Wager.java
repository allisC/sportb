package com.epam.training.sportsbetting.domain.wager;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.epam.training.sportsbetting.domain.user.*;

import com.epam.training.sportsbetting.domain.outcome.OutcomeOdd;
import com.epam.training.sportsbetting.domain.sportevent.SportEvent;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "player", "outcomeodd", "amount", "currency", "timeStamp", "isProcessed", "isWon", "sportEvent" })
@XmlRootElement
public class Wager {

    // Wager: the wager placed by a Player on an outcome;
    // It stores the odd, the amount and the currency of the bet,
    // the date and time when the bet is created and the state of having been processed or not.

    Player player;
    private OutcomeOdd outcomeodd;
    private double amount;
    private Currency currency;
    LocalDateTime timeStamp;
    private boolean isProcessed;
    private boolean isWon;
    private SportEvent sportEvent;

    public Wager() {
    }

    public Wager(Player player, OutcomeOdd outcomeodd, double amount) {
        super();
        this.player = player;
        this.outcomeodd = outcomeodd;
        this.amount = amount;
        this.currency = player.getCurrency();
        this.timeStamp = LocalDateTime.now();
        this.setProcessed(false);
    }

    // @Override
    // public String toString() {
    // return "Wager [player=" + player.getName() + ", outcomeodd=" + getOutcomeodd().getOddValue() + ", amount=" + getAmount() + ", currency=" + getCurrency()
    // + ", timeStamp=" + timeStamp + ", isProcessed=" + isProcessed() + ", isWon=" + isWon() + "]";
    // }

    private boolean isProcessed() {
        return isProcessed;
    }

    public void setProcessed(boolean isProcessed) {
        this.isProcessed = isProcessed;
    }

    public boolean isWon() {
        return isWon;
    }

    public void setWon(boolean isWon) {
        this.isWon = isWon;
    }

    public double getAmount() {
        return amount;
    }

    public OutcomeOdd getOutcomeodd() {
        return outcomeodd;
    }

    private Currency getCurrency() {
        return currency;
    }

    public void setOutcomeodd(OutcomeOdd outcomeodd) {
        this.outcomeodd = outcomeodd;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public SportEvent getSportEvent() {
        return sportEvent;
    }

    public void setSportEvent(SportEvent sportEvent) {
        this.sportEvent = sportEvent;
    }
}
