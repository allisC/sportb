package com.epam.training.sportsbetting.db;

import org.springframework.data.repository.CrudRepository;

import com.epam.training.sportsbetting.domain.outcome.OutcomeOdd;

public interface OutcomeOddRepository extends CrudRepository<OutcomeOdd, Integer> {

    OutcomeOdd findOutcomeOddById(Integer id);

}
