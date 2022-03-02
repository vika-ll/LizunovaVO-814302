package ClassesLizunovaVO;

import DatabaseLizunovaVO.ConstLizunovaVO;

import java.io.Serializable;
import java.lang.reflect.Field;

public class TaskLizunovaVO implements Serializable {
    private Integer ID;
    private String taskName;
    private Integer complexity;
    private Integer modulesCount;
    private Integer priority;
    private Integer requirementsSpecification;
    private DevelopmentFieldLizunovaVO developmentArea;
    private TaskStatusLizunovaVO status;
    private Integer performance;
    private EmployeeLizunovaVO employee;
    private ProjectLizunovaVO project;
    private Integer time;

    public TaskLizunovaVO(Integer ID, String taskName, Integer complexity, Integer modulesCount, Integer priority, Integer requirementsSpecification, DevelopmentFieldLizunovaVO developmentArea, TaskStatusLizunovaVO status, Integer performance, EmployeeLizunovaVO employee, ProjectLizunovaVO project, Integer time) {
        this.ID = ID;
        this.taskName = taskName;
        this.complexity = complexity;
        this.modulesCount = modulesCount;
        this.priority = priority;
        this.requirementsSpecification = requirementsSpecification;
        this.developmentArea = developmentArea;
        this.status = status;
        this.performance = performance;
        this.employee = employee;
        this.project = project;
        this.time = time;
    }

    public TaskLizunovaVO(Integer ID, String taskName, Integer complexity, Integer modulesCount, Integer priority, Integer requirementsSpecification, DevelopmentFieldLizunovaVO developmentArea, TaskStatusLizunovaVO status, Integer performance, EmployeeLizunovaVO employee, ProjectLizunovaVO project) {
        this.ID = ID;
        this.taskName = taskName;
        this.complexity = complexity;
        this.modulesCount = modulesCount;
        this.priority = priority;
        this.requirementsSpecification = requirementsSpecification;
        this.developmentArea = developmentArea;
        this.status = status;
        this.performance = performance;
        this.employee = employee;
        this.project = project;
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

    public TaskLizunovaVO(Integer ID) {
        this.ID = ID;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getComplexity() {
        return complexity;
    }

    public void setComplexity(Integer complexity) {
        this.complexity = complexity;
    }

    public DevelopmentFieldLizunovaVO getDevelopmentArea() {
        return developmentArea;
    }

    public void setDevelopmentArea(DevelopmentFieldLizunovaVO developmentArea) {
        this.developmentArea = developmentArea;
    }

    public EmployeeLizunovaVO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeLizunovaVO employee) {
        this.employee = employee;
    }

    public Integer getModulesCount() {
        return modulesCount;
    }

    public void setModulesCount(Integer modulesCount) {
        this.modulesCount = modulesCount;
    }

    public Integer getPerformance() {
        return performance;
    }

    public void setPerformance(Integer performance) {
        this.performance = performance;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getRequirementsSpecification() {
        return requirementsSpecification;
    }

    public void setRequirementsSpecification(Integer requirementsSpecification) {
        this.requirementsSpecification = requirementsSpecification;
    }

    public TaskStatusLizunovaVO getStatus() {
        return status;
    }

    public void setStatus(TaskStatusLizunovaVO status) {
        this.status = status;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public void setProject(ProjectLizunovaVO project) {
        this.project = project;
    }

    public ProjectLizunovaVO getProject() {
        return project;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}