package com.example.organization.repository;

import com.example.organization.model.Bracket;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BracketRepository extends JpaRepository<Bracket, Long> {
}
