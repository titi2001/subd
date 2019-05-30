package entity;

import javax.persistence.*;

@Entity
@Table(name = "positions")
public class Position {

    private int id;
    private String name;
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
}
