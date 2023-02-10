package com.example.organization.model;

import javax.persistence.*;

@Entity
public class Bracket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @Column(length = 5)
    private Integer result1;
    @Column(length = 3)
    private Integer result2;
    @Column(length = 3)
    private Integer round;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team1_id")
    private Team team1;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team2_id")
    private Team team2;

    public Bracket() {
    }

    public Bracket(Integer round, Team team1, Team team2) {
        this.round = round;
        this.team1 = team1;
        this.team2 = team2;
    }

    public Bracket(Integer result1, Integer result2, Integer round, Team team1, Team team2) {
        this.result1 = result1;
        this.result2 = result2;
        this.round = round;
        this.team1 = team1;
        this.team2 = team2;
    }

    public Bracket(Long id, Integer result1, Integer result2, Integer round, Team team1, Team team2) {
        this.id = id;
        this.result1 = result1;
        this.result2 = result2;
        this.round = round;
        this.team1 = team1;
        this.team2 = team2;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getResult1() {
        return result1;
    }

    public void setResult1(Integer result1) {
        this.result1 = result1;
    }

    public Integer getResult2() {
        return result2;
    }

    public void setResult2(Integer result2) {
        this.result2 = result2;
    }

    public Integer getRound() {
        return round;
    }

    public void setRound(Integer round) {
        this.round = round;
    }

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }
}
