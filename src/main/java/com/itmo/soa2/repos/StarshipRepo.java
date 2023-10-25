package com.itmo.soa2.repos;

import com.itmo.soa2.entities.Starship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StarshipRepo extends JpaRepository<Starship, Integer> {
}
