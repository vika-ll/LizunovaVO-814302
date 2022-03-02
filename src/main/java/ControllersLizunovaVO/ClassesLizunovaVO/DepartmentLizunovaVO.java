package ClassesLizunovaVO;

import java.io.Serializable;

public class DepartmentLizunovaVO implements Serializable {
    public Integer ID;
    public String name;

    public DepartmentLizunovaVO(Integer ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }
}

