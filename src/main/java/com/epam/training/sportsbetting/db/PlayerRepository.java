package com.epam.training.sportsbetting.db;

import org.springframework.data.repository.CrudRepository;

import com.epam.training.sportsbetting.domain.user.Player;

public interface PlayerRepository extends CrudRepository<Player, Integer> {

    Player findPlayerByName(String name);

}
