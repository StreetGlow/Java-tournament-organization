package com.example.organization.model;

import javax.persistence.*;

@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @Column(length = 20)
    private String name;
    @Column(length = 1)
    private int size;
    @Column(length = 3, nullable = true, updatable = true)
    private Integer seed;
    @Column(nullable = true, updatable = false)//must be 'nullable = false' - delete team data in data.sql
    private String teamCode;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    public Team() {
    }
    public Team(String name, int size, Integer seed, Tournament tournament) {
        this.name = name;
        this.size = size;
        this.seed = seed;
        this.tournament = tournament;
    }

    public Team(Long id, String name, int size, Integer seed, String teamCode, Tournament tournament) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.seed = seed;
        this.teamCode = teamCode;
        this.tournament = tournament;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Integer getSeed() {
        return seed;
    }

    public void setSeed(Integer seed) {
        this.seed = seed;
    }

    public String getTeamCode() {
        return teamCode;
    }

    public void setTeamCode(String teamCode) {
        this.teamCode = teamCode;
    }

    public Tournament getTournament() {
        return tournament;
    }
    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }
}
