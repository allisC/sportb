package com.epam.training.sportsbetting.db;

import org.springframework.data.repository.CrudRepository;

import com.epam.training.sportsbetting.domain.wager.Wager;

public interface WagerRepository extends CrudRepository<Wager, Integer> {

    Wager findWagerById(Integer id);

}
