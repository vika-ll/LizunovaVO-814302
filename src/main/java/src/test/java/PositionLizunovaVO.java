import java.io.Serializable;

public class PositionLizunovaVO implements Serializable  {
    public Integer ID;
    public String name;
    public DepartmentLizunovaVO department;

    public PositionLizunovaVO(Integer id, String name, DepartmentLizunovaVO department) {
        this.ID = id;
        this.name = name;
        this.department = department;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DepartmentLizunovaVO getDepartmentLizunovaVO() {
        return department;
    }

    public void setDepartmentLizunovaVO(DepartmentLizunovaVO DepartmentLizunovaVO) {
        this.department = department;
    }
}
