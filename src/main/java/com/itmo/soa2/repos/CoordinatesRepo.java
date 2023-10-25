package com.itmo.soa2.repos;

import com.itmo.soa2.entities.domain.Coordinates;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoordinatesRepo extends JpaRepository<Coordinates, Integer> {
}
