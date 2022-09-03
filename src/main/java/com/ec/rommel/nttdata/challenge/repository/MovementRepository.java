package com.ec.rommel.nttdata.challenge.repository;

import com.ec.rommel.nttdata.challenge.entity.Movement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MovementRepository extends CrudRepository<Movement, UUID> {
}
