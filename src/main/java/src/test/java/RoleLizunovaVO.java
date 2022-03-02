import java.io.Serializable;

public class RoleLizunovaVO implements Serializable  {
    public Integer ID;
    public String name;

    public RoleLizunovaVO(Integer ID, String name) {
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
