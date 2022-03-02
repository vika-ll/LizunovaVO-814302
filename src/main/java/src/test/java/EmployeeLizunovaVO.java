import java.io.Serializable;
import java.lang.reflect.Field;

public class EmployeeLizunovaVO implements Serializable {
    public Integer ID;
    public String name;
    public String secondName;
    public String lastName;
    public PositionLizunovaVO position;

    public EmployeeLizunovaVO(Integer ID, String name, String secondName, String lastName, PositionLizunovaVO position) {
        this.ID = ID;
        this.name = name;
        this.secondName = secondName;
        this.lastName = lastName;
        this.position = position;
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

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public PositionLizunovaVO getPosition() {
        return position;
    }

    public void setPosition(PositionLizunovaVO position) {
        this.position = position;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        String newLine = System.getProperty("line.separator");

        result.append( this.getClass().getName() );
        result.append( " Object {" );
        result.append(newLine);

        //determine fields declared in this class only (no fields of superclass)
        Field[] fields = this.getClass().getDeclaredFields();

        //print field names paired with their values
        for ( Field field : fields  ) {
            result.append("  ");
            try {
                result.append( field.getName() );
                result.append(": ");
                //requires access to private field:
                result.append( field.get(this) );
            } catch ( IllegalAccessException ex ) {
                System.out.println(ex);
            }
            result.append(newLine);
        }
        result.append("}");

        return result.toString();
    }
}
