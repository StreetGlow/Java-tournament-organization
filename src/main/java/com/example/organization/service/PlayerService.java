package com.example.organization.service;

import com.example.organization.exception.PlayerNotFoundExceptional;
import com.example.organization.model.Player;
import com.example.organization.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {
    private final PlayerRepository playerRepo;

    @Autowired
    public PlayerService(PlayerRepository playerRepo) {

        this.playerRepo = playerRepo;
    }

    public Player savePlayer(Player player) {

        return playerRepo.save(player);
    }

    public List<Player> findALlPLayers() {

        return playerRepo.findAll();
    }

    public Player editPlayer(Player player) {

        return playerRepo.save(player);
    }

    public Player findPlayer(Long id) {
        return playerRepo.findById(id).orElseThrow(() -> new PlayerNotFoundExceptional("Player not found with id: " + id));
    }

    public void deletePlayer(Long id) {

        playerRepo.deleteById(id);
    }
}
