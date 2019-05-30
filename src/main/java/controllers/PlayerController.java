package controllers;

import entity.Player;
import entity.Position;
import entity.Team;
import javafx.geometry.Pos;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import repository.PlayerRepository;
import repository.PositionRepository;
import repository.TeamRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    @GetMapping("/createPlayer")
    public ModelAndView createPlayer(ModelAndView modelAndView) {
        return new ModelAndView("base-layout")
                .addObject("view", "players/createPlayer");
    }

    @PostMapping("/createPlayer")
    public String create(Player player) {
        Player p = new Player;
        p.setId(player.getId());
        p.setName(player.getName());
        p.setPositionId(player.getPositionId());
        p.setTeamId(player.getTeamId());
        this.playerRepository.save(p);
        return "redirect:/";
    }
    @GetMapping("/createPosition")
    public ModelAndView createPosition(ModelAndView modelAndView) {
        return new ModelAndView("base-layout")
                .addObject("view", "players/createPosition");
    }

    @PostMapping("/createPosition")
    public String createPosition(Position position) {
        Position p = new Position();
        p.setId(position.getId());
        p.setName(position.getName());
        this.positionRepository.save(p);
        return "redirect:/";
    }
    @GetMapping("/createTeam")
    public ModelAndView createTeam(ModelAndView modelAndView) {
        return new ModelAndView("base-layout")
                .addObject("view", "players/createTeam");
    }

    @PostMapping("/createTeam")
    public String createTeam(Team team) {
        Team p = new Team();
        p.setId(team.getId());
        p.setName(team.getName());
        this.teamRepository.save(p);
        return "redirect:/";
    }
}
