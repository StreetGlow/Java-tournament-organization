package com.example.organization.controller;

import com.example.organization.model.Gender;
import com.example.organization.model.Player;
import com.example.organization.model.Team;
import com.example.organization.service.GenderService;
import com.example.organization.service.PlayerService;
import com.example.organization.service.TeamService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class PlayerController {

    private final PlayerService playerService;
    private final TeamService teamService;
    private final GenderService genderService;

    public PlayerController(PlayerService playerService, TeamService teamService, GenderService genderService) {

        this.playerService = playerService;
        this.teamService = teamService;
        this.genderService = genderService;
    }

    @GetMapping("/players/new")
    public String showPlayerForm(Model model, @ModelAttribute("success") String success, @ModelAttribute("error") String error, @ModelAttribute("errorCode") String errorCode) {
        List<Team> listTeams = teamService.findALlTeams();
        List<Gender> listGenders = genderService.findALlGenders();
        model.addAttribute("success", success);
        model.addAttribute("error", error);
        model.addAttribute("player", new Player());
        model.addAttribute("listTeams", listTeams);
        model.addAttribute("listGenders", listGenders);

        return "playerRegistration";
    }

    @PostMapping("/players/save")
    public String savePlayer(Player player, @Param("code") String code, Model model, RedirectAttributes redirectAttributes) {
        List<Player> listPlayers = playerService.findALlPLayers();
        List<Team> listTeams = teamService.findALlTeams();
        Team team = listTeams.stream().filter(t -> t.getTeamCode().equals(code)).findAny().orElse(null);

        if (team == null) {
            redirectAttributes.addFlashAttribute("errorCode", "Team code is wrong!");
        } else if (listPlayers.stream().filter(lp -> team.getId().equals(lp.getTeam().getId())).count() >= team.getSize()) {
            redirectAttributes.addFlashAttribute("error", "Team \"" + team.getName() + "\" is already full!");
        } else {
            player.setTeam(team);
            playerService.savePlayer(player);
            redirectAttributes.addFlashAttribute("success", "Player \"" + player.getName() + "\" successfully added!");
        }
        return "redirect:/players/new";
    }
}