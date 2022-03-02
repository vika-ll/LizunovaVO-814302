package ClassesLizunovaVO;

import javafx.concurrent.Task;

import java.io.Serializable;
import java.util.stream.Stream;

public class TaskStatusLizunovaVO implements Serializable {
    public String name;
    public Integer id;

    public TaskStatusLizunovaVO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}