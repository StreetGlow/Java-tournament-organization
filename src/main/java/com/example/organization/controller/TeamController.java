package com.example.organization.controller;

import com.example.organization.model.Player;
import com.example.organization.model.Team;
import com.example.organization.model.Tournament;
import com.example.organization.service.PlayerService;
import com.example.organization.service.TeamService;
import com.example.organization.service.TournamentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class TeamController {

    private final TeamService teamService;
    private final TournamentService tournamentService;
    private final PlayerService playerService;

    public TeamController(TeamService teamService, TournamentService tournamentService, PlayerService playerService) {
        this.teamService = teamService;
        this.tournamentService = tournamentService;
        this.playerService = playerService;
    }

    @GetMapping("/teams/list")
    public String getAllTeams(Model model) {
        List<Team> listTeams = teamService.findALlTeams();
        List<Player> listPlayers = playerService.findALlPLayers();
        model.addAttribute("listTeams", listTeams);
        model.addAttribute("listPlayers", listPlayers);
        return "teamsList";
    }

    @PostMapping("/teams/save")
    public String saveTeam(Team team, RedirectAttributes redirectAttributes) {
        teamService.saveTeam(team);
        redirectAttributes.addAttribute("teamCode", team.getTeamCode());
        return "redirect:/teams/new/success";
    }

    @GetMapping("/teams/new/{id}")
    public String showTeamFormId(@PathVariable("id") Long id, Model model) {
       // List<Tournament> listTournaments = tournamentService.findALlTournaments();
        Tournament tournamentId = tournamentService.findTournament(id);
       // model.addAttribute("listTournaments", listTournaments);
        model.addAttribute("tournamentId", tournamentId);
        model.addAttribute("team", new Team());
        return "teamRegistration";
    }

    @GetMapping("/teams/new/success")
    public String showTeamSuccessCode(Model model, @ModelAttribute("teamCode") String teamCode) {
        model.addAttribute("teamCode", teamCode);
        return "teamRegistrationSuccess";
    }
}