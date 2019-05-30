package hello.entity;

import javax.persistence.*;

@Entity
@Table(name = "teams")
public class Team {
    private int Id;
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
