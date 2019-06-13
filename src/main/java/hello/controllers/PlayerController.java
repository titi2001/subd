package hello.controllers;

import hello.bookBindingModel.*;
import hello.entity.*;
import org.aspectj.apache.bcel.util.Play;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PlayerController {
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/basketball?useSSL=false&createDatabaseIfNotExist=true", "root", "root");
    private static String UPLOADED_FOLDER = "E:\\Downloads\\mar\\src\\main\\resources\\static\\";

    public PlayerController() throws SQLException {
    }

    public Player getPlayer(int id) throws SQLException {
        Player p = new Player();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT id, name, position_id, team_id, image FROM players WHERE id = " + id + ";");

        while (rs.next())
        {
            p.setId(rs.getInt("id"));
            p.setName(rs.getString("name"));
            p.setPositionId(rs.getInt("position_id"));
            p.setTeamId(rs.getInt("team_id"));
            p.setImage(rs.getString("image"));
        }
        st.close();
        return p;
    }
    public List<Player> getAllPlayers() throws SQLException {
        List<Player> players = new ArrayList<>();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT id, name, position_id, team_id, image FROM players WHERE id != -1;");

        while (rs.next())
        {
            Player p = new Player();
            p.setId(rs.getInt("id"));
            p.setName(rs.getString("name"));
            p.setPositionId(rs.getInt("position_id"));
            p.setTeamId(rs.getInt("team_id"));
            p.setImage(rs.getString("image"));
            players.add(p);
        }
        st.close();
        return players;
    }
    public Position getPosition(int id) throws SQLException{
        Position p = new Position();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT id, name from positions WHERE id = " + id + ";");

        while (rs.next())
        {
            p.setId(rs.getInt("id"));
            p.setName(rs.getString("name"));
        }
        st.close();
        return p;
    }
    public List<Position> getAllPositions() throws SQLException {

        List<Position> positions = new ArrayList<>();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT id, name FROM positions WHERE id != -1;");

        while (rs.next())
        {
            Position position = new Position();
            position.setId(rs.getInt("id"));
            position.setName(rs.getString("name"));
            positions.add(position);
        }
        st.close();
        return positions;
    }
    public Team getTeam(int id) throws SQLException {
        Team t = new Team();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT id, name FROM teams WHERE id = " + id + ";");

        while (rs.next())
        {
            t.setId(rs.getInt("id"));
            t.setName(rs.getString("name"));
        }
        st.close();
        return t;
    }
    public List<Team> getAllTeams() throws SQLException {

        List<Team> teams = new ArrayList<>();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT id, name FROM teams WHERE id != -1;");

        while (rs.next())
        {
            Team team = new Team();
            team.setId(rs.getInt("id"));
            team.setName(rs.getString("name"));
            teams.add(team);
        }
        st.close();
        return teams;
    }
    public Player searchPlayer(String searchTerm) throws SQLException {
        Player p = new Player();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT id, name, position_id, team_id, image FROM players WHERE name LIKE CONCAT('%','" + searchTerm + "', '%');");
        while (rs.next())
        {
            p.setId(rs.getInt("id"));
            p.setName(rs.getString("name"));
            p.setPositionId(rs.getInt("position_id"));
            p.setTeamId(rs.getInt("team_id"));
            p.setImage(rs.getString("image"));
        }
        st.close();
        return p;
    }



    @GetMapping("/")
    public ModelAndView index(ModelAndView modelAndView) throws SQLException {
        return new ModelAndView("base-layout")
                .addObject("view", "players/index")
                .addObject("players", getAllPlayers())
                .addObject("teams", getAllTeams())
                .addObject("positions", getAllPositions());
    }
    @PostMapping("/")
    public ModelAndView search(ModelAndView modelAndView, @RequestParam String search) throws SQLException {
        return new ModelAndView("base-layout")
                .addObject("view", "players/index")
                .addObject("players", searchPlayer(search))
                .addObject("teams", getAllTeams())
                .addObject("positions", getAllPositions());
    }
    @GetMapping("/error")
    public ModelAndView error(ModelAndView modelAndView) throws SQLException {
        return new ModelAndView("base-layout")
                .addObject("view", "players/index")
                .addObject("players", getAllPlayers())
                .addObject("teams", getAllTeams())
                .addObject("positions", getAllPositions());
    }
    @GetMapping("/createPlayer")
    public ModelAndView createPlayer(ModelAndView modelAndView) {
        return new ModelAndView("base-layout")
                .addObject("view", "players/createPlayer");
    }

    @PostMapping("/createPlayer")
    public String create(Player player, @RequestParam("uploadingFiles") MultipartFile uploadingFiles, @RequestParam("teamName") String teamName,@RequestParam("positionName") String positionName, @RequestParam("name") String n ) throws IOException, SQLException {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT id from positions where name = '" + positionName + "';");
        int pos = 0;
        int tea = 0;
        while (rs.next())
        {
            pos = rs.getInt("id");
            break;
        }
        ResultSet r = st.executeQuery("SELECT id from teams where name = '" + teamName + "';");
        while (r.next())
        {
            tea = r.getInt("id");
            break;
        }
        st.close();
        byte[] bytes = uploadingFiles.getBytes();
        Path path = Paths.get(UPLOADED_FOLDER + uploadingFiles.getOriginalFilename());
        Files.write(path, bytes);
        String query = "INSERT INTO players (name, position_id, team_id, image) VALUES ('" + n + "', " + pos + ", " + tea + ", '" + uploadingFiles.getOriginalFilename() + "');";
        PreparedStatement ss = conn.prepareStatement(query);
        ss.executeUpdate();
        ss.close();
        return "redirect:/";
    }
    @GetMapping("/createPosition")
    public ModelAndView createPosition(ModelAndView modelAndView) {
        return new ModelAndView("base-layout")
                .addObject("view", "players/createPosition");
    }

    @PostMapping("/createPosition")
    public String createPosition(Position position) throws SQLException {
        Statement st = conn.createStatement();
        String query = "INSERT INTO positions (name) VALUES ('" + position.getName() + "');";
        PreparedStatement ss = conn.prepareStatement(query);
        ss.executeUpdate();
        ss.close();
        return "redirect:/";
    }
    @GetMapping("/createTeam")
    public ModelAndView createTeam(ModelAndView modelAndView) {
        return new ModelAndView("base-layout")
                .addObject("view", "players/createTeam");
    }

    @PostMapping("/createTeam")
    public String createTeam(Team team) throws SQLException {
        Statement st = conn.createStatement();
        String query = "INSERT INTO teams (name) VALUES ('" + team.getName() + "');";
        PreparedStatement ss = conn.prepareStatement(query);
        ss.executeUpdate();
        ss.close();
        return "redirect:/";
    }
    @GetMapping("/editPlayer/{id}")
    public ModelAndView editPlayer(@PathVariable Long id) throws SQLException {
        return new ModelAndView("base-layout")
                .addObject("view", "players/editPlayer")
                .addObject("player", getPlayer(Math.toIntExact(id)));

    }
    @GetMapping("/viewPlayer/{id}")
    public ModelAndView viewPlayer(@PathVariable Long id) throws SQLException {
        return new ModelAndView("base-layout")
                .addObject("view", "players/viewPlayer")
                .addObject("player", getPlayer(Math.toIntExact(id)))
                .addObject("teams", getAllTeams())
                .addObject("positions", getAllPositions());

    }
    @GetMapping("/viewTeam/{id}")
    public ModelAndView viewTeam(@PathVariable Long id) throws SQLException {
        return new ModelAndView("base-layout")
                .addObject("view", "players/viewTeam")
                .addObject("team", getTeam(Math.toIntExact(id)))
                .addObject("players", getAllPlayers())
                .addObject("positions", getAllPositions());

    }
    @GetMapping("/viewPosition/{id}")
    public ModelAndView viewPosition(@PathVariable Long id) throws SQLException {
        return new ModelAndView("base-layout")
                .addObject("view", "players/viewPosition")
                .addObject("position", getPosition(Math.toIntExact(id)))
                .addObject("players", getAllPlayers());

    }
    @PostMapping("/editPlayer/{id}")
    public ModelAndView editPlayer(@PathVariable Long id, PlayerBindingModel model, @RequestParam("name") String name, @RequestParam("teamName") String teamName,@RequestParam("positionName") String positionName) throws IOException, SQLException {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT id from positions where name = '" + positionName + "';");
        int pos = 0;
        int tea = 0;
        while (rs.next())
        {
            pos = rs.getInt("id");
            break;
        }
        ResultSet r = st.executeQuery("SELECT id from teams where name = '" + teamName + "';");
        while (r.next())
        {
            tea = r.getInt("id");
            break;
        }
        st.close();
        String query = "UPDATE players " + "SET name = '" + name + "', position_id = " + pos + " , team_id = " + tea + " WHERE id = " + id + ";";
        PreparedStatement ss = conn.prepareStatement(query);
        ss.executeUpdate();
        ss.close();
        return new ModelAndView("redirect:/");
    }
    @GetMapping("/editPosition/{id}")
    public ModelAndView editPosition(@PathVariable Long id) throws SQLException {
        return new ModelAndView("base-layout")
                .addObject("view", "players/editPosition")
                .addObject("position", getPosition(Math.toIntExact(id)));

    }
    @PostMapping("/editPosition/{id}")
    public ModelAndView editPosition(@PathVariable Long id, PositionBindingModel model) throws IOException, SQLException {
        Statement st = conn.createStatement();
        String query = "UPDATE positions " + "SET name = '" + model.getName() + "' WHERE id = " + id + ";";
        PreparedStatement ss = conn.prepareStatement(query);
        ss.executeUpdate();
        ss.close();
        return new ModelAndView("redirect:/");
    }
    @GetMapping("/editTeam/{id}")
    public ModelAndView editTeam(@PathVariable Long id) throws SQLException {
        return new ModelAndView("base-layout")
                .addObject("view", "players/editTeam")
                .addObject("team", getTeam(Math.toIntExact(id)));

    }
    @PostMapping("/editTeam/{id}")
    public ModelAndView editTeam(@PathVariable Long id, TeamBindingModel model) throws IOException, SQLException {
        Statement st = conn.createStatement();
        String query = "UPDATE teams " + "SET name = '" + model.getName() + "' WHERE id = " + id + ";";
        PreparedStatement ss = conn.prepareStatement(query);
        ss.executeUpdate();
        ss.close();
        return new ModelAndView("redirect:/");
    }
    @GetMapping("/deletePlayer/{id}")
    public ModelAndView deletePlayer(@PathVariable Long id) throws SQLException {
        return new ModelAndView("base-layout")
                .addObject("view", "players/deletePlayer")
                .addObject("player", getPlayer(Math.toIntExact(id)));

    }
    @PostMapping("/deletePlayer/{id}")
    public ModelAndView processDelete(@PathVariable Long id) throws SQLException {
        String query = "Delete FROM players where id = " + id + ";";
        PreparedStatement st = conn.prepareStatement(query);
        st.executeUpdate();
        return new ModelAndView("redirect:/");
    }
    @GetMapping("/deletePosition/{id}")
    public ModelAndView deleteePosition(@PathVariable Long id) throws SQLException {
        return new ModelAndView("base-layout")
                .addObject("view", "players/deletePosition")
                .addObject("position", getPosition(Math.toIntExact(id)));

    }
    @PostMapping("/deletePosition/{id}")
    public ModelAndView pr(@PathVariable Long id) throws SQLException {
        String query = "Delete FROM positions where id = " + id + ";";
        PreparedStatement st = conn.prepareStatement(query);
        st.executeUpdate();
        return new ModelAndView("redirect:/");
    }
    @GetMapping("/deleteTeam/{id}")
    public ModelAndView deleteTeam(@PathVariable Long id) throws SQLException {
        return new ModelAndView("base-layout")
                .addObject("view", "players/deleteTeam")
                .addObject("team", getTeam(Math.toIntExact(id)));

    }
    @PostMapping("/deleteTeam/{id}")
    public ModelAndView prt(@PathVariable Long id) throws SQLException {
        String query = "Delete FROM teams where id = " + id + ";";
        PreparedStatement st = conn.prepareStatement(query);
        st.executeUpdate();
        return new ModelAndView("redirect:/");
    }
}
