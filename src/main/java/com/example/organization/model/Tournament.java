package com.example.organization.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @Column(length = 45)
    private String name;
    @Column(length = 1)
    private int teamSizeRequired;
    @Column
    private Date date;
    @Column
    private String time;
    @Column
    private String prize;
    @Column(length = 45)
    private String game;
    @Column(nullable = true, updatable = true)
    private Long identifierBracket;
    @Column(length = 250)
    private String info;

    public Tournament() {
    }

    public Tournament(Long id, String name, int teamSizeRequired, Date date, String time, String prize, String game, Long identifierBracket, String info) {
        this.id = id;
        this.name = name;
        this.teamSizeRequired = teamSizeRequired;
        this.date = date;
        this.time = time;
        this.prize = prize;
        this.game = game;
        this.identifierBracket = identifierBracket;
        this.info = info;
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

    public int getTeamSizeRequired() {
        return teamSizeRequired;
    }

    public void setTeamSizeRequired(int teamSizeRequired) {
        this.teamSizeRequired = teamSizeRequired;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public Long getIdentifierBracket() {
        return identifierBracket;
    }

    public void setIdentifierBracket(Long identifierBracket) {
        this.identifierBracket = identifierBracket;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
