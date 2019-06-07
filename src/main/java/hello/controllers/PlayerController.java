package hello.controllers;

import hello.bookBindingModel.*;
import hello.entity.*;
import org.aspectj.apache.bcel.util.Play;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import hello.repository.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PlayerController {
    private static String UPLOADED_FOLDER = "E:\\Downloads\\mar\\src\\main\\resources\\static\\";
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
                .addObject("teams", this.teamRepository.findAll())
                .addObject("positions", this.positionRepository.findAll());
    }
    @PostMapping("/")
    public ModelAndView search(ModelAndView modelAndView, @RequestParam String search) {
        List<Player> result = new ArrayList<>();
        List<Player> original = this.playerRepository.findAll();
        for(int i = 0; i < original.size(); i++){
            if(original.get(i).getName().toLowerCase().contains(search.toLowerCase())){
                result.add(original.get(i));
            }
        }
        return new ModelAndView("base-layout")
                .addObject("view", "players/index")
                .addObject("players", result)
                .addObject("teams", this.teamRepository.findAll())
                .addObject("positions", this.positionRepository.findAll());
    }
    @GetMapping("/error")
    public ModelAndView error(ModelAndView modelAndView) {
        return new ModelAndView("base-layout")
                .addObject("view", "players/index")
                .addObject("players", this.playerRepository.findAll())
                .addObject("teams", this.teamRepository.findAll())
                .addObject("positions", this.positionRepository.findAll());
    }
    @GetMapping("/createPlayer")
    public ModelAndView createPlayer(ModelAndView modelAndView) {
        return new ModelAndView("base-layout")
                .addObject("view", "players/createPlayer");
    }

    @PostMapping("/createPlayer")
    public String create(Player player, @RequestParam("uploadingFiles") MultipartFile uploadingFiles, @RequestParam("teamName") String teamName,@RequestParam("positionName") String positionName ) throws IOException {
        Player p = new Player();
        p.setId(player.getId());
        p.setName(player.getName());
        for(int i = 0; i < this.teamRepository.findAll().size(); i++){
            if(teamName.equals(this.teamRepository.findAll().get(i).getName())){
                p.setTeamId(this.teamRepository.findAll().get(i).getId());
            }
        }
        for(int i = 0; i < this.positionRepository.findAll().size(); i++){
            if(positionName.equals(this.positionRepository.findAll().get(i).getName())){
                p.setPositionId(this.positionRepository.findAll().get(i).getId());
            }
        }
            byte[] bytes = uploadingFiles.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + uploadingFiles.getOriginalFilename());
            Files.write(path, bytes);
            p.setImage(uploadingFiles.getOriginalFilename());
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
    @GetMapping("/editPlayer/{id}")
    public ModelAndView editPlayer(@PathVariable Long id) {
        return new ModelAndView("base-layout")
                .addObject("view", "players/editPlayer")
                .addObject("player", this.playerRepository.findById(Math.toIntExact(id)).get());

    }
    @GetMapping("/viewPlayer/{id}")
    public ModelAndView viewPlayer(@PathVariable Long id) {
        return new ModelAndView("base-layout")
                .addObject("view", "players/viewPlayer")
                .addObject("player", this.playerRepository.findById(Math.toIntExact(id)).get())
                .addObject("teams", this.teamRepository.findAll())
                .addObject("positions", this.positionRepository.findAll());

    }
    @GetMapping("/viewTeam/{id}")
    public ModelAndView viewTeam(@PathVariable Long id) {
        return new ModelAndView("base-layout")
                .addObject("view", "players/viewTeam")
                .addObject("team", this.teamRepository.findById(Math.toIntExact(id)).get())
                .addObject("players", this.playerRepository.findAll())
                .addObject("positions", this.positionRepository.findAll());

    }
    @GetMapping("/viewPosition/{id}")
    public ModelAndView viewPosition(@PathVariable Long id) {
        return new ModelAndView("base-layout")
                .addObject("view", "players/viewPosition")
                .addObject("position", this.positionRepository.findById(Math.toIntExact(id)).get())
                .addObject("players", this.playerRepository.findAll());

    }
    @PostMapping("/editPlayer/{id}")
    public ModelAndView editPlayer(@PathVariable Long id, PlayerBindingModel model) throws IOException  {
        Player p = this.playerRepository.findById(Math.toIntExact(id)).get();
        p.setName(model.getName());
        p.setTeamId(model.getTeamId());
        p.setPositionId(model.getPositionId());
        p.setId(model.getId());
        this.playerRepository.save(p);
        return new ModelAndView("redirect:/");
    }
    @GetMapping("/editPosition/{id}")
    public ModelAndView editPosition(@PathVariable Long id) {
        return new ModelAndView("base-layout")
                .addObject("view", "players/editPosition")
                .addObject("position", this.positionRepository.findById(Math.toIntExact(id)).get());

    }
    @PostMapping("/editPosition/{id}")
    public ModelAndView editPosition(@PathVariable Long id, PositionBindingModel model) throws IOException  {
        Position p = this.positionRepository.findById(Math.toIntExact(id)).get();
        p.setName(model.getName());
        p.setId(model.getId());
        this.positionRepository.save(p);
        return new ModelAndView("redirect:/");
    }
    @GetMapping("/editTeam/{id}")
    public ModelAndView editTeam(@PathVariable Long id) {
        return new ModelAndView("base-layout")
                .addObject("view", "players/editTeam")
                .addObject("team", this.teamRepository.findById(Math.toIntExact(id)).get());

    }
    @PostMapping("/editTeam/{id}")
    public ModelAndView editTeam(@PathVariable Long id, TeamBindingModel model) throws IOException  {
        Team p = this.teamRepository.findById(Math.toIntExact(id)).get();
        p.setName(model.getName());
        p.setId(model.getId());
        this.teamRepository.save(p);
        return new ModelAndView("redirect:/");
    }
    @GetMapping("/deletePlayer/{id}")
    public ModelAndView deletePlayer(@PathVariable Long id) {
        return new ModelAndView("base-layout")
                .addObject("view", "players/deletePlayer")
                .addObject("player", this.playerRepository.findById(Math.toIntExact(id)).get());

    }
    @PostMapping("/deletePlayer/{id}")
    public ModelAndView processDelete(@PathVariable Long id) {
        this.playerRepository.deleteById(Math.toIntExact(id));
        return new ModelAndView("redirect:/");
    }
    @GetMapping("/deletePosition/{id}")
    public ModelAndView deleteePosition(@PathVariable Long id) {
        return new ModelAndView("base-layout")
                .addObject("view", "players/deletePosition")
                .addObject("position", this.positionRepository.findById(Math.toIntExact(id)).get());

    }
    @PostMapping("/deletePosition/{id}")
    public ModelAndView pr(@PathVariable Long id) {
        this.positionRepository.deleteById(Math.toIntExact(id));
        return new ModelAndView("redirect:/");
    }
    @GetMapping("/deleteTeam/{id}")
    public ModelAndView deleteTeam(@PathVariable Long id) {
        return new ModelAndView("base-layout")
                .addObject("view", "players/deleteTeam")
                .addObject("team", this.teamRepository.findById(Math.toIntExact(id)).get());

    }
    @PostMapping("/deleteTeam/{id}")
    public ModelAndView prt(@PathVariable Long id) {
        this.teamRepository.deleteById(Math.toIntExact(id));
        return new ModelAndView("redirect:/");
    }
}
