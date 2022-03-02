package ClassesLizunovaVO;

import java.io.Serializable;

public class ProjectLizunovaVO implements Serializable {
    public Integer ID;
    public String name;

    public ProjectLizunovaVO(Integer id, String name) {
        this.ID = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getID() {
        return ID;
    }
}
