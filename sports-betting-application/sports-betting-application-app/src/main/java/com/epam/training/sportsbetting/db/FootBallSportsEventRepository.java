package com.epam.training.sportsbetting.db;

import com.epam.training.sportsbetting.domain.sportevent.FootballSportEvent;
import org.springframework.data.repository.CrudRepository;

public interface FootBallSportsEventRepository extends CrudRepository<FootballSportEvent, Integer> {
}
