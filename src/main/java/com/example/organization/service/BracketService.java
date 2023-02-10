package com.example.organization.service;

import com.example.organization.exception.BracketNotFoundExceptional;
import com.example.organization.exception.TeamNotFoundExceptional;
import com.example.organization.model.Bracket;
import com.example.organization.model.Team;
import com.example.organization.repository.BracketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BracketService {
    private final BracketRepository bracketRepo;

    @Autowired
    public BracketService(BracketRepository bracketRepo) {
        this.bracketRepo = bracketRepo;
    }

    public Bracket saveBracket(Bracket bracket) {

        return bracketRepo.save(bracket);
    }

    public List<Bracket> findALlBrackets() {

        return bracketRepo.findAll();
    }

    public Bracket editBracket(Bracket bracket) {

        return bracketRepo.save(bracket);
    }

    public Bracket findBracket(Long id) {
        return bracketRepo.findById(id).orElseThrow(() -> new BracketNotFoundExceptional("Bracket not found with id: " + id));
    }

    public void deleteBracket(Long id) {

        bracketRepo.deleteById(id);
    }
}
