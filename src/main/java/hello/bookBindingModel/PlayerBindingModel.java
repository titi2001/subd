package hello.bookBindingModel;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

public class PlayerBindingModel {
    private int Id;
    private String name;
    private int team_Id;
    private int position_Id;
    @javax.persistence.Id
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
}
