package com.epam.training.sportsbetting.db;

import org.springframework.data.repository.CrudRepository;

import com.epam.training.sportsbetting.domain.bet.Bet;

public interface BetRepository extends CrudRepository<Bet, Integer> {

    Bet findBetById(Integer id);

}
