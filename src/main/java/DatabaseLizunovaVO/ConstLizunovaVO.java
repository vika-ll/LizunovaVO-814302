package DatabaseLizunovaVO;

public class ConstLizunovaVO {
    public static final String EMPLOYEE_TABLE = "employee";
    public static final String EMPLOYEE_ID = "employee_id";
    public static final String EMPLOYEE_NAME = "employee_name";
    public static final String EMPLOYEE_SECOND_NAME = "employee_second_name";
    public static final String EMPLOYEE_LAST_NAME = "employee_last_name";
    public static final String EMPLOYEE_POSITION_ID = "position_position_id";
    public static final String EMPLOYEE_DEPARTMENT_ID = "position_department_department_id";

    public static final String TASK_TABLE = "task";
    public static final String TASK_ID = "task_id";
    public static final String TASK_NAME = "task_name";
    public static final String TASK_MODULES_COUNT = "modules_count";
    public static final String TASK_COMPLEXITY_SCORE = "complexity_score";
    public static final String TASK_time = "time";
    public static final String TASK_REQUIREMENTS_SPECIFICATION_SCORE = "requirements_specification_score";
    public static final String TASK_PRIORITY_SCORE = "priority_score";
    public static final String TASK_PERFORMANCE_SCORE = "performance_score";
    public static final String TASK_DEVELOPMENT_AREA_NAME = "development_area.development_area_name";
    public static final String TASK_DEVELOPMENT_AREA_ID = "development_area_development_area_id";
    public static final String TASK_EMPLOYEE_ID = "employee_employee_id";
    public static final String TASK_EMPLOYEE_NAME = "employee.employee_name";
    public static final String TASK_PROJECT_ID = "project_project_id";
    public static final String TASK_PROJECT_NAME = "project.project_name";
    public static final String TASK_STATUS_ID = "task_status_status_id";
    public static final String TASK_TASK_STATUS_ID = "task_status.status_id";
    public static final String TASK_TASK_STATUS_NAME = "task_status.status_name";

    public static final String USER_TABLE = "user";
    public static final String USER_ID = "user_id";
    public static final String USER_BLOCKED = "is_blocked";
    public static final String USER_PASSWORD = "password";
    public static final String USER_LOGIN = "login";
    public static final String USER_ROLE = "user_role_user_role_id";
    public static final String USER_PROFILE = "profile_profile_id";

    public static final String PROFILE_TABLE = "profile";
    public static final String PROFILE_NAME = "profile_name";
    public static final String PROFILE_ID = "profile_id";
    public static final String PROFILE_LAST_NAME = "profile_last_name";
    public static final String PROFILE_SECOND_NAME = "profile_second_name";


    public static final String ADMIN_WINDOW_TABLE =
             "SELECT * FROM task INNER JOIN development_area ON task.development_area_development_area_id = development_area.development_area_id INNER JOIN employee ON task.employee_employee_id = employee.employee_id INNER JOIN project ON task.project_project_id = project.project_id INNER JOIN task_status ON task.task_status_status_id = task_status.status_id INNER JOIN position ON employee.position_position_id = position.position_id INNER JOIN department ON position.department_department_id = department.department_id";
    public static final String USERS_TABLE = "SELECT * FROM user INNER JOIN profile ON user.profile_profile_id = profile.profile_id INNER JOIN user_role ON user.user_role_user_role_id = user_role.user_role_id";
    public static final String DEVELOPMENT_AREAS = "SELECT * FROM development_area";
    public static final String DEPARTMENTS = "SELECT * FROM department";
    public static final String ROLES = "SELECT * FROM user_role";
    public static final String PROJECTS = "SELECT * FROM project";
    public static final String POSITIONS = "SELECT * FROM position INNER JOIN department ON position.department_department_id = department.department_id";
    public static final String STATUSES = "SELECT * FROM task_status";
    public static final String EMPLOYEES = "SELECT * FROM employee INNER JOIN position ON employee.position_position_id = position.position_id INNER JOIN department ON employee.position_department_department_id = department.department_id";
    public static final String FIND_USER ="SELECT * FROM user WHERE user.login = ?";
}
