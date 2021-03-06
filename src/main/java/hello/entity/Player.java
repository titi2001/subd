package hello.entity;

import javax.persistence.*;

@Entity
@Table(name = "players")
public class Player {
    private int Id;
    private String name;
    private String image;
    private int team_Id;
    private int position_Id;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTeamId() {
        return team_Id;
    }

    public void setTeamId(int teamId) {
        this.team_Id = teamId;
    }

    public int getPositionId() {
        return position_Id;
    }

    public void setPositionId(int positionId) {
        this.position_Id = positionId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
