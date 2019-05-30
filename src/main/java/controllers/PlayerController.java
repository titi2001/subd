package controllers;

import entity.Position;
import javafx.geometry.Pos;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import repository.PlayerRepository;
import repository.PositionRepository;
import repository.TeamRepository;

public class PlayerController {
    private final PlayerRepository playerRepository;
    private final PositionRepository positionRepository;
    private final TeamRepository teamRepository;
    public PlayerController(PlayerRepository playerRepository, PositionRepository positionRepository, TeamRepository teamRepository){
        this.playerRepository = playerRepository;
        this.positionRepository = positionRepository;
        this.teamRepository = teamRepository;
    }
    @GetMapping("/")
    public ModelAndView index(ModelAndView modelAndView) {

        return new ModelAndView("base-layout")
                .addObject("view", "players/index")
                .addObject("players", this.playerRepository.findAll())
                .addObject("teams", this.playerRepository.findAll())
                .addObject("positions", this.playerRepository.findAll());
    }
}
