package ClassesLizunovaVO;

import java.io.Serializable;

public class DevelopmentFieldLizunovaVO implements Serializable {
    public String name;
    public Integer id;

    public DevelopmentFieldLizunovaVO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}