package hello.bookBindingModel;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

public class PlayerBindingModel {
    private int Id;
    private String name;
    private int teamId;
    private int positionId;
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
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }
}
