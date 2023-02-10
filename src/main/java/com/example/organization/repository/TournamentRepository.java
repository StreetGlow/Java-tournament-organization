package com.example.organization.repository;

import com.example.organization.model.Tournament;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TournamentRepository extends JpaRepository<Tournament, Long> {

    @Query("SELECT t FROM Tournament t WHERE LOWER(CONCAT(t.game, ' ', t.name, ' ', t.teamSizeRequired, ' ', t.time, ' ', t.date, ' ', t.prize)) LIKE LOWER(CONCAT('%', ?1,'%'))")
    Page<Tournament> searchIgnoreCase(String keyword, Pageable pageable);
}
