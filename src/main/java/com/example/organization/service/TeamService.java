package com.example.organization.service;

import com.example.organization.exception.TeamNotFoundExceptional;
import com.example.organization.model.Team;
import com.example.organization.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TeamService {
    private final TeamRepository teamRepo;

    @Autowired
    public TeamService(TeamRepository teamRepo) {

        this.teamRepo = teamRepo;
    }

    public Team saveTeam(Team team) {
    team.setTeamCode(UUID.randomUUID().toString());
        return teamRepo.save(team);
    }

    public List<Team> findALlTeams() {

        return teamRepo.findAll();
    }

    public Team editTeam(Team team) {

        return teamRepo.save(team);
    }

    public Team findTeam(Long id) {
        return teamRepo.findById(id).orElseThrow(() -> new TeamNotFoundExceptional("Team not found with id: " + id));
    }

    public void deleteTeam(Long id) {

        teamRepo.deleteById(id);
    }
}
