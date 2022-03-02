import java.io.Serializable;

public class ProfileLizunovaVO implements Serializable {
    private int id;
    private String name;
    private String lastName;
    private String secondName;

    public ProfileLizunovaVO(int id, String name, String lastName, String secondName) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.secondName = secondName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

}