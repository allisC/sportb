package com.epam.training.sportsbetting.db;

import org.springframework.data.repository.CrudRepository;

import com.epam.training.sportsbetting.domain.outcome.Outcome;

public interface OutcomeRepository extends CrudRepository<Outcome, Integer> {

    Outcome findOutcomeById(Integer id);

}
