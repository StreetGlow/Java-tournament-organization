package com.example.organization.controller;

import com.example.organization.model.Bracket;
import com.example.organization.model.Team;
import com.example.organization.model.Tournament;
import com.example.organization.service.BracketService;
import com.example.organization.service.TeamService;
import com.example.organization.service.TournamentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class BracketController {

    private final TeamService teamService;
    private final TournamentService tournamentService;
    private final BracketService bracketService;

    public BracketController(TeamService teamService, TournamentService tournamentService, BracketService bracketService) {
        this.teamService = teamService;
        this.tournamentService = tournamentService;
        this.bracketService = bracketService;
    }

    @GetMapping("/bracket/delete/{id}")
    public String deleteBracket(@PathVariable("id") Long id, Model model) {
        Tournament tournament = tournamentService.findTournament(id);
        if (tournament.getIdentifierBracket() != null) {
            List<Team> listTeamsAll = teamService.findALlTeams();
            List<Team> listTeams = new ArrayList<>();

            tournament.setIdentifierBracket(null);
            tournamentService.saveTournament(tournament);

            bracketService.findALlBrackets().stream().filter(b -> b.getTeam1().getTournament().getId().equals(id)).forEach(b -> bracketService.deleteBracket(b.getId()));

            listTeamsAll.stream().filter(t -> t.getTournament().getId().equals(id)).forEach(t -> {
                t.setSeed(null);
                teamService.saveTeam(t);
                if (t.getName().equals("-")) {
                    teamService.deleteTeam(t.getId());
                }
            });
        }
        return "redirect:/tournaments/list";
    }

    @GetMapping("/bracket/create/{id}")
    public String createBracket(@PathVariable("id") Long id, Model model) {
        Tournament tournament = tournamentService.findTournament(id);
        if (tournament.getIdentifierBracket() == null && teamService.findALlTeams().stream().filter(t -> tournament.getId().equals(t.getTournament().getId())).count() > 7) {
            List<Team> listTeamsAll = teamService.findALlTeams();
            List<Bracket> listBracketSave = new ArrayList<>();
            List<Team> listTeams = new ArrayList<>();

            tournament.setIdentifierBracket(id);
            tournamentService.saveTournament(tournament);

            for (Team team : listTeamsAll) {
                if (team.getTournament().getId().equals(id)) {
                    listTeams.add(team);//ADD - remove team if don't have enough players
                }
            }

            int emptyTeams = 0;
            if (listTeams.size() < 8) {
                emptyTeams = 8 - listTeams.size();
            }
            if (listTeams.size() > 8 && listTeams.size() < 16) {
                emptyTeams = 16 - listTeams.size();
            }
            if (listTeams.size() > 16 && listTeams.size() < 32) {
                emptyTeams = 32 - listTeams.size();
            }
            if (listTeams.size() > 32 && listTeams.size() < 64) {
                emptyTeams = 64 - listTeams.size();
            }

            Collections.shuffle(listTeams);
            for (Team team : listTeams) {
                team.setSeed(listTeams.indexOf(team) + 1);
                teamService.saveTeam(team);
            }

            if (emptyTeams != 0) {
                Set<Integer> setEmptyTeamsSeedList = new HashSet();
                while (setEmptyTeamsSeedList.size() < emptyTeams) {
                    setEmptyTeamsSeedList = new Random().ints(emptyTeams, 1, listTeams.size() + 1)
                            .boxed()
                            .collect(Collectors.toSet());
                }

                for (Team team : listTeams) {
                    for (Integer integerSeed : setEmptyTeamsSeedList) {
                        if (team.getSeed().equals(integerSeed)) {
                            Bracket bracket = new Bracket(0, -1, 1, team, null);
                            listBracketSave.add(bracket);
                        }
                    }
                }

                listTeams = listTeams.stream().filter(t -> listBracketSave.stream().noneMatch(b -> t.getId().equals(b.getTeam1().getId()))).collect(Collectors.toList());
            }
            for (int i = 0; i < listTeams.size(); i += 2) {
                Bracket bracket = new Bracket(1, listTeams.get(i), listTeams.get(i + 1));
                listBracketSave.add(bracket);
            }
            Collections.shuffle(listBracketSave);
            listBracketSave.forEach(bracketService::saveBracket);
        }
        return "redirect:/tournaments/list";
    }

    @GetMapping("/bracket/{id}")
    public String showBracket(@PathVariable("id") Long id, Model model) {
        List<Team> listTeamsAll = teamService.findALlTeams();
        List<Team> listTeamsSort = new ArrayList<>();
        List<Bracket> listBracketTournament = new ArrayList<>();
        List<Bracket> listBracketAll = bracketService.findALlBrackets();

        for (Bracket bracket : listBracketAll) {
            if (bracket.getTeam1().getTournament().getId().equals(id)) {
                listBracketTournament.add(bracket);
            }
        }

        List<Bracket> listBracketTournamentR1 = new ArrayList<>();
        List<Bracket> listBracketTournamentR2 = new ArrayList<>();
        List<Bracket> listBracketTournamentR3 = new ArrayList<>();
        List<Bracket> listBracketTournamentR4 = new ArrayList<>();
        List<Bracket> listBracketTournamentR5 = new ArrayList<>();
        List<Bracket> listBracketTournamentR6 = new ArrayList<>();

        for (Bracket bracket : listBracketTournament) {
            if (bracket.getRound() == 1) {
                listBracketTournamentR1.add(bracket);
            } else if (bracket.getRound() == 2) {
                listBracketTournamentR2.add(bracket);
            } else if (bracket.getRound() == 3) {
                listBracketTournamentR3.add(bracket);
            } else if (bracket.getRound() == 4) {
                listBracketTournamentR4.add(bracket);
            } else if (bracket.getRound() == 5) {
                listBracketTournamentR5.add(bracket);
            } else if (bracket.getRound() == 6) {
                listBracketTournamentR6.add(bracket);
            }
        }

        model.addAttribute("listBracketTournamentR1", listBracketTournamentR1);
        if (listBracketTournamentR1.stream().noneMatch(bracket -> bracket.getResult1() == null)) {
            model.addAttribute("listBracketTournamentR2", listBracketTournamentR2);
        }
        if (listBracketTournamentR2.stream().noneMatch(bracket -> bracket.getResult1() == null)) {
            model.addAttribute("listBracketTournamentR3", listBracketTournamentR3);
        }
        if (listBracketTournamentR3.stream().noneMatch(bracket -> bracket.getResult1() == null)) {
            model.addAttribute("listBracketTournamentR4", listBracketTournamentR4);
        }
        if (listBracketTournamentR4.stream().noneMatch(bracket -> bracket.getResult1() == null)) {
            model.addAttribute("listBracketTournamentR5", listBracketTournamentR5);
        }
        if (listBracketTournamentR5.stream().noneMatch(bracket -> bracket.getResult1() == null)) {
            model.addAttribute("listBracketTournamentR6", listBracketTournamentR6);
        }
        return "bracket";
    }

    @GetMapping("/bracket/results/{id}")
    public String bracketResultsMap(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Tournament tournament = tournamentService.findTournament(id);
        redirectAttributes.addAttribute("tournament", tournament);
        return "redirect:/bracket/results";
    }

    @GetMapping("/bracket/results")
    public String bracketResults(Model model, @ModelAttribute("tournament") Tournament tournament) {
        List<Team> listTeamsTournament = new ArrayList<>();

        List<Team> listTeamsAll = teamService.findALlTeams();
        List<Bracket> listBracketAll = bracketService.findALlBrackets();
        List<Team> listTeamsUp = new ArrayList<>();
        List<Team> listTeamsDown = new ArrayList<>();
        List<Bracket> listBracketTournament = new ArrayList<>();
        List<Bracket> listBracketTournamentTableShow = new ArrayList<>();

        for (Team team : listTeamsAll) {
            if (team.getTournament().getId().equals(tournament.getId())) {
                listTeamsTournament.add(team);
            }
        }

        for (Bracket bracket : listBracketAll) {
            if (bracket.getTeam1().getTournament().getId().equals(tournament.getId())) {
                listBracketTournament.add(bracket);
                if (bracket.getTeam2() != null) {
                    listBracketTournamentTableShow.add(bracket);
                }
            }
        }
        List<Bracket> listBracketTournamentRoundFilter = new ArrayList<>();

        int tournamentTeamPool = 0;

        if (listTeamsTournament.size() <= 8) {
            tournamentTeamPool = 8;
        } else if (listTeamsTournament.size() <= 16) {
            tournamentTeamPool = 16;
        } else if (listTeamsTournament.size() <= 32) {
            tournamentTeamPool = 32;
        } else if (listTeamsTournament.size() <= 64) {
            tournamentTeamPool = 64;
        }

        int round = 1;
        for (Bracket bracket : listBracketTournament) {
            if (bracket.getResult1() != null) {
                listBracketTournamentRoundFilter.add(bracket);
            }
        }

        if (listBracketTournamentRoundFilter.size() >= (tournamentTeamPool / 2)) {//8
            round = 2;
            if (listBracketTournamentRoundFilter.size() >= (tournamentTeamPool / 4) + (tournamentTeamPool / 2)) {//12
                round = 3;
                if (listBracketTournamentRoundFilter.size() >= (tournamentTeamPool / 8) + (tournamentTeamPool / 4) + (tournamentTeamPool / 2)) {//14
                    round = 4;
                    if (listBracketTournamentRoundFilter.size() >= (tournamentTeamPool / 16) + (tournamentTeamPool / 8) + (tournamentTeamPool / 4) + (tournamentTeamPool / 2)) {
                        round = 5;
                        if (listBracketTournamentRoundFilter.size() >= (tournamentTeamPool / 32) + (tournamentTeamPool / 16) + (tournamentTeamPool / 8) + (tournamentTeamPool / 4) + (tournamentTeamPool / 2)) {
                            round = 6;
                        }
                    }
                }
            }
        }

        List<Team> listTeamsTrnm = new ArrayList<>();
        if (listBracketTournamentRoundFilter.size() == listBracketTournament.size()) {
            // listTeamsTournament.clear();
            for (Bracket bracket : listBracketTournament) {

                if (bracket.getRound() == round - 1 && bracket.getResult1() != null) {
                    if (bracket.getResult1() > bracket.getResult2() || bracket.getTeam2() == null) {
                        listTeamsTrnm.add(bracket.getTeam1());
                    } else {
                        listTeamsTrnm.add(bracket.getTeam2());
                    }
                }
            }

            listBracketTournament.clear();
            if (listTeamsTrnm.size() % 2 == 0) {
                for (int i = 0; i < listTeamsTrnm.size(); i += 2) {
                    Bracket bracket = new Bracket(round, listTeamsTrnm.get(i), listTeamsTrnm.get(i + 1));
                    listBracketTournament.add(bracket);
                }
                listBracketTournament.forEach(bracketService::saveBracket);
            }
        }

        int finalRound = round;
        listBracketTournament.removeIf(b -> b.getResult1() != null || b.getRound() > finalRound);

        model.addAttribute("listBracketTournamentTableShow", listBracketTournamentTableShow);
        model.addAttribute("listBracketTournament", listBracketTournament);// promjenit naziv i u htmlu listBracketTournament
        model.addAttribute("round", round);
        model.addAttribute("listBracketAll", listBracketAll);
        model.addAttribute("listTeamsUp", listTeamsUp);
        model.addAttribute("listTeamsDown", listTeamsDown);

        model.addAttribute("bracket", new Bracket());

        return "bracketResults";
    }

    @PostMapping("/bracket/results/save")
    public String saveBracketResults(Model model, Bracket bracket, RedirectAttributes redirectAttributes) {

        Tournament tournament = bracket.getTeam1().getTournament();
        redirectAttributes.addAttribute("tournament", tournament);
        if (bracket.getResult1().equals(bracket.getResult2())) {
            redirectAttributes.addFlashAttribute("error", "Result can't be tied!");
        } else {
            bracketService.saveBracket(bracket);
            redirectAttributes.addFlashAttribute("success", "Results successfully saved!");


            List<Bracket> listBracketAll = bracketService.findALlBrackets();
            List<Bracket> listBracketTournament = new ArrayList<>();

            for (Bracket bracketS : listBracketAll) {
                if (bracketS.getTeam1().getTournament().getId().equals(tournament.getId()) && bracketS.getTeam2() != null) {
                    listBracketTournament.add(bracketS);
                }
            }

            for (Bracket bracket1 : listBracketTournament) {
                if (bracket1.getTeam1().getId().equals(bracket.getTeam1().getId())) {
                    if (bracket1.getRound() > bracket.getRound()) {
                        if (bracket.getResult1() > bracket.getResult2()) {
                            bracket1.setTeam1(bracket.getTeam1());
                        } else {
                            bracket1.setTeam1(bracket.getTeam2());
                        }
                        if (bracket1.getResult1() != null) {
                            bracket1.setResult1(null);
                            bracket1.setResult2(null);
                        }
                    }
                } else if (bracket1.getTeam2().getId().equals(bracket.getTeam2().getId())) {
                    if (bracket1.getRound() > bracket.getRound()) {
                        if (bracket.getResult1() > bracket.getResult2()) {
                            bracket1.setTeam2(bracket.getTeam1());
                        } else {
                            bracket1.setTeam2(bracket.getTeam2());
                        }
                        if (bracket1.getResult1() != null) {
                            bracket1.setResult1(null);
                            bracket1.setResult2(null);
                        }
                    }
                } else if (bracket1.getTeam1().getId().equals(bracket.getTeam2().getId())) {
                    if (bracket1.getRound() > bracket.getRound()) {
                        if (bracket.getResult1() > bracket.getResult2()) {
                            bracket1.setTeam1(bracket.getTeam1());
                        } else {
                            bracket1.setTeam1(bracket.getTeam2());
                        }
                        if (bracket1.getResult1() != null) {
                            bracket1.setResult1(null);
                            bracket1.setResult2(null);
                        }
                    }
                } else if (bracket1.getTeam2().getId().equals(bracket.getTeam1().getId())) {
                    if (bracket1.getRound() > bracket.getRound()) {
                        if (bracket.getResult1() > bracket.getResult2()) {
                            bracket1.setTeam2(bracket.getTeam1());
                        } else {
                            bracket1.setTeam2(bracket.getTeam2());
                        }
                        if (bracket1.getResult1() != null) {
                            bracket1.setResult1(null);
                            bracket1.setResult2(null);
                        }
                    }

                }
                bracketService.saveBracket(bracket1);
            }
        }
        return "redirect:/bracket/results";
    }

    @GetMapping("/bracket/results/edit/{id}")
    public String bracketResultsEdit(@PathVariable("id") Long id, Model model) {
        Bracket bracket = bracketService.findBracket(id);//napravit i delete bracketa ako bi radilo
        model.addAttribute("bracket", bracket);

        return "bracketResults";
    }
}
