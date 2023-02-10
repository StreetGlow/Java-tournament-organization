package com.example.organization.controller;

import com.example.organization.model.Player;
import com.example.organization.model.Team;
import com.example.organization.model.Tournament;
import com.example.organization.service.PlayerService;
import com.example.organization.service.TeamService;
import com.example.organization.service.TournamentService;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
public class TournamentController {
    public Long var;
    private final TeamService teamService;
    private final TournamentService tournamentService;
    private final PlayerService playerService;

    public TournamentController(TeamService teamService, TournamentService tournamentService, PlayerService playerService) {
        this.teamService = teamService;
        this.tournamentService = tournamentService;
        this.playerService = playerService;
    }

    @GetMapping("/tournaments/list")
    public String showTournaments(Model model){
        return showTournamentsList(model, null ,1, "date", "asc");
    }

    @GetMapping("/tournaments/list/{pageNumber}")
    public String showTournamentsList(Model model, @Param("keyword") String keyword, @PathVariable("pageNumber") int pageNumber, @Param("sortField") String sortField, @Param("sortDir") String sortDir) {
        Page<Tournament> pageTournaments = tournamentService.findALlTournaments(pageNumber, keyword, sortField, sortDir);
        long totalItems = pageTournaments.getTotalElements();
        int totalPages = pageTournaments.getTotalPages();
        List<Tournament> listTournaments = pageTournaments.getContent();

        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("listTournaments", listTournaments);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("keyword", keyword);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("sortDirReverse", sortDir.equals("asc") ? "desc" : "asc");
        return "tournaments";
    }

    @GetMapping("/tournaments/{id}/info")
    public String showTournamentInfo(@PathVariable("id") Long id, Model model) {
        Tournament tournament = tournamentService.findTournament(id);
        List<Team> listTeams = teamService.findALlTeams();
        List<Player> listPlayers = playerService.findALlPLayers();
        listTeams.removeIf(lt -> !lt.getTournament().getId().equals(id));
        model.addAttribute("tournament", tournament);
        model.addAttribute("listTeams", listTeams);
        model.addAttribute("listPlayers", listPlayers);
        return "tournamentInfo";
    }

    @GetMapping("/tournaments/create")//Only Admin
    public String createTournament(Model model, @ModelAttribute("success") String success) {
        model.addAttribute("tournament", new Tournament());
        LocalDate dateToday = LocalDate.now();
        model.addAttribute("success", success);
        model.addAttribute("dateToday", dateToday);
        return "createTournament";
    }

    @PostMapping("/tournaments/save")
    public String saveTournament(Tournament tournament, RedirectAttributes redirectAttributes){
        tournamentService.saveTournament(tournament);
        redirectAttributes.addFlashAttribute("success", "Tournament \"" + tournament.getName() + "\" successfully created!");
        return "redirect:/tournaments/create";
    }

    @GetMapping("/tournaments/list/edit/{id}")
    public String showTournamentEdit(@PathVariable("id") Long id, Model model){
    Tournament tournament = tournamentService.findTournament(id);
        model.addAttribute("tournament", tournament);
        return "createTournament";
        }
}