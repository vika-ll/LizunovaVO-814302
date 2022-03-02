package DatabaseLizunovaVO;

import ClassesLizunovaVO.*;

import java.sql.*;
import java.util.ArrayList;

public class DBHandlerLizunovaVO extends DBConnectLizunovaVO {
    Connection dbConnection;

    public DBHandlerLizunovaVO() {
    }

    public Connection getDbConnection()
            throws ClassNotFoundException, SQLException {
        dbConnection = DbConnectionLizunovaVO.getInstance().getDbConnection();
       return dbConnection;
    }


    public void signUp(String userLogin, String surname, String name, String secondName, String password, Integer role) {
        String insert = "INSERT INTO " +  ConstLizunovaVO.USER_TABLE + "(" + ConstLizunovaVO.USER_ID + ", "+ ConstLizunovaVO.USER_LOGIN + ", " + ConstLizunovaVO.USER_PASSWORD + ", " + ConstLizunovaVO.USER_BLOCKED + ", " + ConstLizunovaVO.USER_ROLE+ ", " +  ConstLizunovaVO.USER_PROFILE+ ") " + "VALUES(?,?,?,?,?,?)";
        String insert2 = "INSERT INTO " + ConstLizunovaVO.PROFILE_TABLE + " ( " + ConstLizunovaVO.PROFILE_ID + ", " + ConstLizunovaVO.PROFILE_NAME+ ", "+ ConstLizunovaVO.PROFILE_LAST_NAME+ ", " + ConstLizunovaVO.PROFILE_SECOND_NAME + ") " + "VALUES(LAST_INSERT_ID(),?,?,?)";
        String update1 = " UPDATE " + ConstLizunovaVO.USER_TABLE + " SET " + ConstLizunovaVO.USER_PROFILE + " = LAST_INSERT_ID()" + " WHERE " + ConstLizunovaVO.USER_ID + " = LAST_INSERT_ID() ";
        try {
            PreparedStatement statement = getDbConnection().prepareStatement(insert);
            statement.setNull(1, Types.INTEGER);
            statement.setString(2, userLogin);
            statement.setString(3, password);
            statement.setBoolean(4, false);
            statement.setInt(5, role);
            statement.setNull(6, Types.INTEGER);

            PreparedStatement statement2 = getDbConnection().prepareStatement(insert2);
            statement2.setString(1, name);
            statement2.setString(2, surname);
            statement2.setString(3, secondName);

            PreparedStatement statement3 = getDbConnection().prepareStatement(update1);
            statement.executeUpdate();
            statement2.executeUpdate();
            statement3.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String Authorise(String login, String password) throws SQLException, ClassNotFoundException {
        ResultSet rSet;
        String role;

        String select = "SELECT * FROM " + ConstLizunovaVO.USER_TABLE + " INNER JOIN user_role ON user.user_role_user_role_id = user_role.user_role_id" + " WHERE " + ConstLizunovaVO.USER_LOGIN + " =? AND " + ConstLizunovaVO.USER_PASSWORD + " =? ";

        PreparedStatement statement = getDbConnection().prepareStatement(select);
        statement.setString(1, login);
        statement.setString(2, password);
        rSet = statement.executeQuery();

        if (rSet.next()) {
            if (rSet.getInt("is_blocked") == 1) {
                return "Заблокирован";
            }
            role = rSet.getString("role_name");
            switch (role) {
                case "Администратор":
                    return "Администратор";
                case "Пользователь":
                    return "Пользователь";
                default:
                    return "null";
            }
        } else {
            return "null";
        }
    }

    public ArrayList<UserLizunovaVO> dbUsers() throws SQLException, ClassNotFoundException {
        ResultSet rSet;
        String select = ConstLizunovaVO.USERS_TABLE;
        PreparedStatement statement = getDbConnection().prepareStatement(select);
        rSet = statement.executeQuery();
        ArrayList<UserLizunovaVO> list = new ArrayList<>();
            while (rSet.next()) {
                RoleLizunovaVO role = new RoleLizunovaVO(rSet.getInt("user_role_id"), rSet.getString("role_name"));
                ProfileLizunovaVO profile = new ProfileLizunovaVO(rSet.getInt("profile_id"),rSet.getString("profile_name"),rSet.getString("profile_last_name"),rSet.getString("profile_second_name"));
                UserLizunovaVO user = new UserLizunovaVO(rSet.getInt("user_id"), rSet.getString("login"),rSet.getString("password"), rSet.getBoolean("is_blocked"), role, profile);
                System.out.println(user);
                list.add(user);
            }
        return list;
    }

   // public void addUserAdmin( String login, String name, String surname, String secondname, String password, String role) {
//        String insert = "INSERT INTO " + ConstLizunovaVO.USER_TABLE + "(" + ConstLizunovaVO.USER_LOGIN + ", " + ConstLizunovaVO.USER_NAME + ", " + ConstLizunovaVO.USER_SURNAME +", " + ConstLizunovaVO.USER_SECONDNAME+
//                ", " + ConstLizunovaVO.USER_PASSWORD + ", " + ConstLizunovaVO.USER_ROLE + ")" +  "VALUES( ?,?,?,?,?,? )";
//        try {
//            PreparedStatement statement = getDbConnection().prepareStatement(insert);
//            statement.setString(1, login);
//            statement.setString(2, name);
//            statement.setString(3, surname);
//            statement.setString(4, secondname);
//            statement.setString(5, password);
//            statement.setString(6, role);
//            statement.executeUpdate();
//        } catch (SQLException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }

   // }

    public void deleteUser(Integer id) throws SQLException, ClassNotFoundException {
        String delete = "DELETE FROM " + ConstLizunovaVO.USER_TABLE + " WHERE " + ConstLizunovaVO.USER_ID+ "=?";
        PreparedStatement statement = getDbConnection().prepareStatement(delete);
        statement.setInt(1, id);
        statement.execute();
    }

    public void setTime(Integer id, Integer time) throws SQLException, ClassNotFoundException {
        String update1 = "UPDATE " + ConstLizunovaVO.TASK_TABLE + " SET " + ConstLizunovaVO.TASK_time + "=?" + " WHERE " + ConstLizunovaVO.TASK_ID + "=?";
        PreparedStatement statement = getDbConnection().prepareStatement(update1);
        statement.setInt(1, time);
        statement.setInt(2, id);
        System.out.println(update1);
        System.out.println(time);
        System.out.println(id);
        statement.executeUpdate();
    }

    public void editUser(int id, String surname, String name, String secondname, String pass, Integer role) throws SQLException, ClassNotFoundException {
        String updateUser = "UPDATE " + ConstLizunovaVO.USER_TABLE + " SET " + ConstLizunovaVO.USER_PASSWORD + "=?"+ ", " + ConstLizunovaVO.USER_ROLE + "=?" +
                " WHERE " + ConstLizunovaVO.USER_ID + "=?";

        System.out.println(updateUser);
        System.out.println(pass);
        System.out.println(role);
        System.out.println(id);
//        String updateProfile = "UPDATE " + ConstLizunovaVO.PROFILE_TABLE + " SET " + ConstLizunovaVO.PROFILE_NAME + "=?"+ ", " + ConstLizunovaVO.PROFILE_LAST_NAME + "=?" + ", " + ConstLizunovaVO.PROFILE_SECOND_NAME + "=?" +
//                " WHERE " + ConstLizunovaVO.PROFILE_ID + "=?";


        PreparedStatement statementUser = getDbConnection().prepareStatement(updateUser);
     //   PreparedStatement statementProfile = getDbConnection().prepareStatement(updateProfile);
        statementUser.setString(1, pass);
        statementUser.setInt(2, role);
        statementUser.setInt(3, id);

//        statementProfile.setString(1, name);
//        statementProfile.setString(2, surname);
//        statementProfile.setString(3, secondname);
//        statementProfile.setInt(4, id);
        statementUser.executeUpdate();
    //    statementProfile.executeUpdate();
    }




    public boolean FindUser(String userLogin) throws SQLException, ClassNotFoundException {
        ResultSet rSet;
        PreparedStatement statement = getDbConnection().prepareStatement(ConstLizunovaVO.FIND_USER);
        statement.setString(1, userLogin);
        rSet = statement.executeQuery();
        if (rSet.next()) {
            return true;
        }
        else {
            return false;
        }
    }

    public ArrayList<RoleLizunovaVO> dbRoles() throws SQLException, ClassNotFoundException {
        ResultSet rSet;
        PreparedStatement statement = getDbConnection().prepareStatement(ConstLizunovaVO.ROLES);
        rSet = statement.executeQuery();
        ArrayList<RoleLizunovaVO> rolesList = new ArrayList<>();
        if (rSet==null) return rolesList;
        else {
            while (rSet.next()) {
                RoleLizunovaVO role = new RoleLizunovaVO(rSet.getInt("user_role_id"), rSet.getString("role_name"));
                rolesList.add(role);
            }
            return rolesList;
        }
    }

    public ArrayList<DevelopmentFieldLizunovaVO> dbDevelopmentAreas() throws SQLException, ClassNotFoundException {
        ResultSet rSet;
        PreparedStatement statement = getDbConnection().prepareStatement(ConstLizunovaVO.DEVELOPMENT_AREAS);
        rSet = statement.executeQuery();
        ArrayList<DevelopmentFieldLizunovaVO> areasList = new ArrayList<>();
        if (rSet==null) return areasList;
        else {
            while (rSet.next()) {
                DevelopmentFieldLizunovaVO area = new DevelopmentFieldLizunovaVO(rSet.getInt("development_area_id"), rSet.getString("development_area_name"));
                areasList.add(area);
            }
            return areasList;
        }
    }

    public ArrayList<DepartmentLizunovaVO> dbDepartments() throws SQLException, ClassNotFoundException {
        ResultSet rSet;
        PreparedStatement statement = getDbConnection().prepareStatement(ConstLizunovaVO.DEPARTMENTS);
        rSet = statement.executeQuery();
        ArrayList<DepartmentLizunovaVO> departmentsList = new ArrayList<>();
        if (rSet==null) return departmentsList;
        else {
            while (rSet.next()) {
                DepartmentLizunovaVO department = new DepartmentLizunovaVO(rSet.getInt("department_id"), rSet.getString("department_name"));
                departmentsList.add(department);
            }
            return departmentsList;
        }
    }

    public ArrayList<PositionLizunovaVO> dbPositions() throws SQLException, ClassNotFoundException {
        ResultSet rSet;
        PreparedStatement statement = getDbConnection().prepareStatement(ConstLizunovaVO.POSITIONS);
        rSet = statement.executeQuery();
        ArrayList<PositionLizunovaVO> positions = new ArrayList<>();
        if (rSet==null) return positions;
        else {
            while (rSet.next()) {
                DepartmentLizunovaVO department = new DepartmentLizunovaVO(rSet.getInt("department_id"), rSet.getString("department_name"));
                PositionLizunovaVO position = new PositionLizunovaVO(rSet.getInt("position_id"), rSet.getString("position_name"), department);
                positions.add(position);
            }
            return positions;
        }
    }

    public ArrayList<ProjectLizunovaVO> dbProjects() throws SQLException, ClassNotFoundException {
        ResultSet rSet;
        PreparedStatement statement = getDbConnection().prepareStatement(ConstLizunovaVO.PROJECTS);
        rSet = statement.executeQuery();
        ArrayList<ProjectLizunovaVO> projects = new ArrayList<>();
        if (rSet==null) return projects;
        else {
            while (rSet.next()) {
                ProjectLizunovaVO project = new ProjectLizunovaVO(rSet.getInt("project_id"), rSet.getString("project_name"));
                projects.add(project);
            }
            return projects;
        }
    }

    public ArrayList<EmployeeLizunovaVO> dbEmployees() throws SQLException, ClassNotFoundException {
        ResultSet rSet;
        PreparedStatement statement = getDbConnection().prepareStatement(ConstLizunovaVO.EMPLOYEES);
        rSet = statement.executeQuery();
        ArrayList<EmployeeLizunovaVO> employees = new ArrayList<>();
        if (rSet==null) return employees;
        else {
            while (rSet.next()) {
                Integer ID = rSet.getInt(ConstLizunovaVO.EMPLOYEE_ID);
                String name = rSet.getString(ConstLizunovaVO.EMPLOYEE_NAME);
                String secondName = rSet.getString(ConstLizunovaVO.EMPLOYEE_SECOND_NAME);
                String lastName = rSet.getString(ConstLizunovaVO.EMPLOYEE_LAST_NAME);
                DepartmentLizunovaVO department = new DepartmentLizunovaVO(rSet.getInt("department_id"), rSet.getString("department_name"));
                PositionLizunovaVO position = new PositionLizunovaVO(rSet.getInt("position_id"), rSet.getString("position_name"), department);
                EmployeeLizunovaVO employee = new EmployeeLizunovaVO(ID, name, secondName, lastName, position);
                employees.add(employee);
            }
            return employees;
        }
    }

    public ArrayList<TaskStatusLizunovaVO> dbStatuses() throws SQLException, ClassNotFoundException {
        ResultSet rSet;
        PreparedStatement statement = getDbConnection().prepareStatement(ConstLizunovaVO.STATUSES);
        rSet = statement.executeQuery();
        ArrayList<TaskStatusLizunovaVO> statuses = new ArrayList<>();
        if (rSet==null) return statuses;
        else {
            while (rSet.next()) {
                TaskStatusLizunovaVO status = new TaskStatusLizunovaVO(rSet.getInt("status_id"), rSet.getString("status_name"));
                statuses.add(status);
            }
            return statuses;
        }
    }

    public ArrayList<TaskLizunovaVO> dbAllTasks() throws SQLException, ClassNotFoundException {


        ResultSet rSet;
       // String worker;
        PreparedStatement statement = getDbConnection().prepareStatement(ConstLizunovaVO.ADMIN_WINDOW_TABLE);
     //   statement.setString(1, month);
      //  statement.setString(2, month);
      //  statement.setString(3, month);
        rSet = statement.executeQuery();
        ArrayList<TaskLizunovaVO> adminList = new ArrayList<>();
        if (rSet==null) return adminList;
        else {
            while (rSet.next()) {
                Integer ID = rSet.getInt(ConstLizunovaVO.TASK_ID);
                String taskName = rSet.getString(ConstLizunovaVO.TASK_NAME);
                Integer complexityScore = rSet.getInt(ConstLizunovaVO.TASK_COMPLEXITY_SCORE);
                Integer modulesCount = rSet.getInt(ConstLizunovaVO.TASK_MODULES_COUNT);
                Integer priorityScore = rSet.getInt(ConstLizunovaVO.TASK_PRIORITY_SCORE);
                Integer requirementsSpecificationScore = rSet.getInt(ConstLizunovaVO.TASK_REQUIREMENTS_SPECIFICATION_SCORE);
                DevelopmentFieldLizunovaVO developmentArea = new DevelopmentFieldLizunovaVO(rSet.getInt(ConstLizunovaVO.TASK_DEVELOPMENT_AREA_ID),  rSet.getString(ConstLizunovaVO.TASK_DEVELOPMENT_AREA_NAME));//rSet.getString(ConstLizunovaVO.TASK_DEVELOPMENT_AREA_NAME);
                TaskStatusLizunovaVO taskStatus = new TaskStatusLizunovaVO(rSet.getInt(ConstLizunovaVO.TASK_TASK_STATUS_ID), rSet.getString(ConstLizunovaVO.TASK_TASK_STATUS_NAME));//rSet.getInt(ConstLizunovaVO.TASK_TASK_STATUS_ID);
                Integer performanceScore = rSet.getInt(ConstLizunovaVO.TASK_PERFORMANCE_SCORE);

                Integer employeeId = rSet.getInt(ConstLizunovaVO.EMPLOYEE_ID);
                String employee_name = rSet.getString(ConstLizunovaVO.EMPLOYEE_NAME);
                String employee_secondName = rSet.getString(ConstLizunovaVO.EMPLOYEE_SECOND_NAME);
                String employee_lastName = rSet.getString(ConstLizunovaVO.EMPLOYEE_LAST_NAME);
                DepartmentLizunovaVO department = new DepartmentLizunovaVO(rSet.getInt("department_id"), rSet.getString("department_name"));
                PositionLizunovaVO position = new PositionLizunovaVO(rSet.getInt("position_id"), rSet.getString("position_name"), department);
                EmployeeLizunovaVO employee = new EmployeeLizunovaVO(employeeId, employee_name, employee_secondName, employee_lastName, position);

                ProjectLizunovaVO project = new ProjectLizunovaVO(rSet.getInt("project_id"), rSet.getString("project_name"));

                String projectId = rSet.getString(ConstLizunovaVO.TASK_PROJECT_NAME);
                Integer taskTime = rSet.getInt(ConstLizunovaVO.TASK_time);
              //  adminList.add("");///.add(new TaskLizunovaVO(ID));
                adminList.add(new TaskLizunovaVO(ID, taskName, complexityScore, modulesCount, priorityScore, requirementsSpecificationScore, developmentArea, taskStatus, performanceScore, employee, project, taskTime));
            }
            return adminList;
        }
    }

    public void editTaskData(TaskLizunovaVO task){

        String update1 = " UPDATE " + ConstLizunovaVO.TASK_TABLE + " SET " + ConstLizunovaVO.TASK_NAME + " = ? " + " , " +
                ConstLizunovaVO.TASK_DEVELOPMENT_AREA_ID + " = ?" + ", " + ConstLizunovaVO.TASK_COMPLEXITY_SCORE + "= ? " + ", " + ConstLizunovaVO.TASK_PROJECT_ID + "= ? " + ", " + ConstLizunovaVO.TASK_EMPLOYEE_ID + "= ? " + ", " + ConstLizunovaVO.TASK_MODULES_COUNT + "= ? " + ", " + ConstLizunovaVO.TASK_PRIORITY_SCORE + "= ? " + ", " + ConstLizunovaVO.TASK_PERFORMANCE_SCORE + "= ?" + ", " + ConstLizunovaVO.TASK_REQUIREMENTS_SPECIFICATION_SCORE + "= ?" + " WHERE " + ConstLizunovaVO.TASK_ID + " = ? ";

        try {
            PreparedStatement statement1 = getDbConnection().prepareStatement(update1);
            statement1.setString(1, task.getTaskName());
            statement1.setInt(2, task.getDevelopmentArea().getId());
            statement1.setInt(3, task.getComplexity());
            statement1.setInt(4, task.getProject().getID());
            statement1.setInt(5, task.getEmployee().getID());
            statement1.setInt(6, task.getModulesCount());
            statement1.setInt(7, task.getPriority());
            statement1.setInt(8, task.getPerformance());
            statement1.setInt(9, task.getRequirementsSpecification());
            statement1.setInt(10, task.getID());

            statement1.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void editEmployeeData(EmployeeLizunovaVO employee){

        String update1 = " UPDATE " + ConstLizunovaVO.EMPLOYEE_TABLE + " SET " + ConstLizunovaVO.EMPLOYEE_NAME + " = ? " + " , " +
                ConstLizunovaVO.EMPLOYEE_SECOND_NAME + " = ?" + ", " + ConstLizunovaVO.EMPLOYEE_LAST_NAME + "= ? " + ", " + ConstLizunovaVO.EMPLOYEE_POSITION_ID + "= ? " + ", " + ConstLizunovaVO.EMPLOYEE_DEPARTMENT_ID + "= ? " + " WHERE " + ConstLizunovaVO.EMPLOYEE_ID + " = ? ";

        try {
            PreparedStatement statement1 = getDbConnection().prepareStatement(update1);
            statement1.setString(1, employee.getName());
            statement1.setString(2, employee.getLastName());
            statement1.setString(3, employee.getSecondName());
            statement1.setInt(4, employee.getPosition().getID());
            statement1.setInt(5, employee.getPosition().getDepartmentLizunovaVO().getID());
            statement1.setInt(6, employee.getID());
            statement1.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addEmployeeData(EmployeeLizunovaVO employee) {
        ResultSet rSet;
        String maxId = "SELECT employee_id FROM employee WHERE employee_id =(SELECT max(employee_id) from employee)";
        Integer value = null;

        try {
            PreparedStatement statement = getDbConnection().prepareStatement(maxId);
            rSet = statement.executeQuery();

            if (rSet == null) return;
            else {
                while (rSet.next()) {
                    value = rSet.getInt("employee_id");
                    System.out.println(value);
                }
            }
        }catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        String insert = "INSERT INTO " +  ConstLizunovaVO.EMPLOYEE_TABLE + "(" + ConstLizunovaVO.EMPLOYEE_ID + ", "+ ConstLizunovaVO.EMPLOYEE_NAME + ", " + ConstLizunovaVO.EMPLOYEE_SECOND_NAME + ", " + ConstLizunovaVO.EMPLOYEE_LAST_NAME + ", " + ConstLizunovaVO.EMPLOYEE_POSITION_ID + ", " +  ConstLizunovaVO.EMPLOYEE_DEPARTMENT_ID + ") " + "VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement statement1 = getDbConnection().prepareStatement(insert);

            statement1.setInt(1, value + 1);
            statement1.setString(2, employee.getName());
            statement1.setString(3, employee.getLastName());
            statement1.setString(4, employee.getSecondName());
            statement1.setInt(5, employee.getPosition().getID());
            statement1.setInt(6, employee.getPosition().getDepartmentLizunovaVO().getID());
            statement1.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addTaskData(TaskLizunovaVO task){
      //  INSERT INTO `vika_ll`.`task` (`task_id`, `task_name`, `modules_count`, `complexity_score`, `time`, `requirements_specification_score`, `priority_score`, `performance_score`, `development_area_development_area_id`, `employee_employee_id`) VALUES ('2', 'sadasdas', '1', '1', '1', '1', '1', '1', '1', '1');

        String insert = "INSERT INTO " + ConstLizunovaVO.TASK_TABLE + " ("+ ConstLizunovaVO.TASK_ID + " , " + ConstLizunovaVO.TASK_NAME + " , "
                + ConstLizunovaVO.TASK_MODULES_COUNT + " , " + ConstLizunovaVO.TASK_COMPLEXITY_SCORE + " , " + ConstLizunovaVO.TASK_time + " , " + ConstLizunovaVO.TASK_REQUIREMENTS_SPECIFICATION_SCORE + " , " + ConstLizunovaVO.TASK_PRIORITY_SCORE + " , " + ConstLizunovaVO.TASK_PERFORMANCE_SCORE + " , " + ConstLizunovaVO.TASK_DEVELOPMENT_AREA_ID + " , " + ConstLizunovaVO.TASK_EMPLOYEE_ID + " , " + ConstLizunovaVO.TASK_PROJECT_ID  + " , " + ConstLizunovaVO.TASK_STATUS_ID + ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
System.out.println(insert);
        try {
            PreparedStatement statement1 = getDbConnection().prepareStatement(insert);
            statement1.setNull(1, Types.INTEGER);
            statement1.setString(2, task.getTaskName());
            statement1.setInt(3, task.getModulesCount());
            statement1.setInt(4, task.getComplexity());
            statement1.setNull(5, Types.INTEGER);
            statement1.setInt(6, task.getRequirementsSpecification());
            statement1.setInt(7, task.getPriority());
            statement1.setInt(8, task.getPerformance());
            statement1.setInt(9, task.getDevelopmentArea().getId());
            statement1.setInt(10, task.getEmployee().getID());
            statement1.setInt(11, task.getProject().getID());
            statement1.setInt(12, task.getStatus().id);
            System.out.println(statement1);
            statement1.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void blockUser(Integer userID) throws SQLException, ClassNotFoundException {
        String update = "UPDATE " + ConstLizunovaVO.USER_TABLE + " SET " + ConstLizunovaVO.USER_BLOCKED + "=? " +
                "WHERE " + ConstLizunovaVO.USER_ID + "=?";
        PreparedStatement statement = getDbConnection().prepareStatement(update);
        statement.setBoolean(1, true);
        statement.setInt(2, userID);
        statement.executeUpdate();
    }


    public void deleteTaskData(int ID) throws SQLException, ClassNotFoundException {


        String delete2 = "DELETE FROM " + ConstLizunovaVO.TASK_TABLE + " WHERE " + ConstLizunovaVO.TASK_ID + " =? ";
        PreparedStatement statement2 = getDbConnection().prepareStatement(delete2);
        statement2.setInt(1,ID);
        statement2.executeUpdate();
    }

    public void deleteEmployeeData(int ID) throws SQLException, ClassNotFoundException {
        String delete2 = "DELETE FROM " + ConstLizunovaVO.EMPLOYEE_TABLE + " WHERE " + ConstLizunovaVO.EMPLOYEE_ID + " =? ";
        PreparedStatement statement2 = getDbConnection().prepareStatement(delete2);
        statement2.setInt(1,ID);
        statement2.executeUpdate();
    }

}

