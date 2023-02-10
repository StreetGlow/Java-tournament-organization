package com.example.organization.service;

import com.example.organization.model.Gender;
import com.example.organization.repository.GenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenderService {
    private final GenderRepository genderRepo;

    @Autowired
    public GenderService(GenderRepository genderRepo) {
        this.genderRepo = genderRepo;
    }

    public List<Gender> findALlGenders() {

        return genderRepo.findAll();
    }

    public Gender editGender(Gender gender) {

        return genderRepo.save(gender);
    }

    public void deleteGender(Long id) {

        genderRepo.deleteById(id);
    }
}
