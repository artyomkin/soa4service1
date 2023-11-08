package com.itmo.soa2.repos;

import com.itmo.soa2.entities.domain.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChapterRepo extends JpaRepository<Chapter, String> {

    boolean existsByName(String name);

    void deleteByName(String name);
}
