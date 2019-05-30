package hello.bookBindingModel;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class PositionBindingModel { private int Id;
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
