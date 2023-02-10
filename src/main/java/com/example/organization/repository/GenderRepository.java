package com.example.organization.repository;

import com.example.organization.model.Gender;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GenderRepository extends JpaRepository<Gender, Long> {
}
