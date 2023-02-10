package com.example.organization.repository;

import com.example.organization.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PlayerRepository extends JpaRepository<Player, Long> {
}
