package com.epam.training.sportsbetting.db;

import com.epam.training.sportsbetting.domain.sportevent.SportEvent;
import org.springframework.data.repository.CrudRepository;

public interface SportsEventRepository extends CrudRepository<SportEvent, Integer> {

}
