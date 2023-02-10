package com.example.organization.service;

import com.example.organization.exception.TournamentNotFoundExceptional;
import com.example.organization.model.Tournament;
import com.example.organization.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TournamentService {

    private static final int PAGE_SIZE = 5;
    private final TournamentRepository tournamentRepository;

    @Autowired
    public TournamentService(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public Tournament saveTournament(Tournament tournament) {

        return tournamentRepository.save(tournament);
    }

    public Page<Tournament> findALlTournaments(int pageNumber, String keyword, String sortField, String sortDir) {
       // Sort sort = Sort.by(sortField);
        // sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_SIZE, sortDir.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
        if (keyword != null) {
            return tournamentRepository.searchIgnoreCase(keyword, pageable);
        }
        return tournamentRepository.findAll(pageable);
    }

    public Tournament editTournament(Tournament tournament) {

        return tournamentRepository.save(tournament);
    }

    public Tournament findTournament(Long id) {
        return tournamentRepository.findById(id).orElseThrow(() -> new TournamentNotFoundExceptional("Tournament not found with id: " + id));
    }

    public void deleteTournament(Long id) {

        tournamentRepository.deleteById(id);
    }
}
