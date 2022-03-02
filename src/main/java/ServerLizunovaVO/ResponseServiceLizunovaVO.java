package ServerLizunovaVO;

import ClassesLizunovaVO.*;
import DatabaseLizunovaVO.ConstLizunovaVO;
import DatabaseLizunovaVO.DBHandlerLizunovaVO;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;

import static MainLizunovaVO.ConnectLizunovaVO.coos;

public class ResponseServiceLizunovaVO {
    private DBHandlerLizunovaVO dbHandler = new DBHandlerLizunovaVO();


    public void Login(ObjectInputStream sois, ObjectOutputStream soos) throws IOException, ClassNotFoundException, SQLException {
        String login = (String) sois.readObject();
        String password = (String) sois.readObject();
        String root = dbHandler.Authorise(login, password);
        soos.writeObject(root);
    }

    public void Registration(ObjectInputStream sois, ObjectOutputStream soos) throws IOException, ClassNotFoundException, SQLException {
        String userLogin = (String) sois.readObject();
        String addName = (String) sois.readObject();
        String addSurname = (String) sois.readObject();
        String addSecondName = (String) sois.readObject();
        String addPassword = (String) sois.readObject();
        Integer role = (Integer) sois.readObject();
        if (!dbHandler.FindUser(userLogin)) {
            dbHandler.signUp(userLogin, addName, addSurname, addSecondName, addPassword, role);
            soos.writeObject("Все ок");
        }
        else soos.writeObject("Данные уже записаны.");
    }
    public void AllUsers(ObjectInputStream sois, ObjectOutputStream soos) throws IOException, ClassNotFoundException, SQLException {
        ArrayList<UserLizunovaVO> list = dbHandler.dbUsers();
        soos.writeObject(list);
    }

    public void DeleteUser(ObjectInputStream sois, ObjectOutputStream soos) throws IOException, ClassNotFoundException, SQLException {
        Integer deleteID = (Integer) sois.readObject();
        dbHandler.deleteUser(deleteID);
    }

    public void UpdateTime(ObjectInputStream sois, ObjectOutputStream soos) throws IOException, ClassNotFoundException, SQLException {
        Integer taskID = (Integer) sois.readObject();
        Integer taskTime = (Integer) sois.readObject();
        dbHandler.setTime(taskID, taskTime);
    }

    public void Save(ObjectInputStream sois, ObjectOutputStream soos) throws IOException, ClassNotFoundException, SQLException {
        int id = sois.readInt();


        String eSurname = (String) sois.readObject();
        String eName = (String) sois.readObject();
        String eSecondName = (String) sois.readObject();
        String ePass = (String) sois.readObject();
        Integer eRole = (Integer) sois.readObject();
        dbHandler.editUser(id, eSurname, eName, eSecondName, ePass, eRole);
    }

    public void MainWindow(ObjectInputStream sois, ObjectOutputStream soos) throws IOException, ClassNotFoundException, SQLException {
        ArrayList<TaskLizunovaVO> adminList = dbHandler.dbAllTasks();
        soos.writeObject(adminList);
    }


    public void getDevelopmentAreas(ObjectInputStream sois, ObjectOutputStream soos) throws IOException, ClassNotFoundException, SQLException {
        ArrayList<DevelopmentFieldLizunovaVO> developmentAreas = dbHandler.dbDevelopmentAreas();
        soos.writeObject(developmentAreas);
    }

    public void getDepartments(ObjectInputStream sois, ObjectOutputStream soos) throws IOException, ClassNotFoundException, SQLException {
        ArrayList<DepartmentLizunovaVO> departments = dbHandler.dbDepartments();
        soos.writeObject(departments);
    }

    public void getRoles(ObjectInputStream sois, ObjectOutputStream soos) throws IOException, ClassNotFoundException, SQLException {
        ArrayList<RoleLizunovaVO> roles = dbHandler.dbRoles();
        soos.writeObject(roles);
    }

    public void getPositions(ObjectInputStream sois, ObjectOutputStream soos) throws IOException, ClassNotFoundException, SQLException {
        ArrayList<PositionLizunovaVO> projects = dbHandler.dbPositions();
        soos.writeObject(projects);
    }

    public void getProjects(ObjectInputStream sois, ObjectOutputStream soos) throws IOException, ClassNotFoundException, SQLException {
        ArrayList<ProjectLizunovaVO> projects = dbHandler.dbProjects();
        soos.writeObject(projects);
    }

    public void getEmployees(ObjectInputStream sois, ObjectOutputStream soos) throws IOException, ClassNotFoundException, SQLException {
        ArrayList<EmployeeLizunovaVO> employees = dbHandler.dbEmployees();
        soos.writeObject(employees);
    }

    public void getStatuses(ObjectInputStream sois, ObjectOutputStream soos) throws IOException, ClassNotFoundException, SQLException {
        ArrayList<TaskStatusLizunovaVO> statuses = dbHandler.dbStatuses();
        soos.writeObject(statuses);
    }

    public void AddTaskData(ObjectInputStream sois, ObjectOutputStream soos) throws IOException, ClassNotFoundException, SQLException {

        String taskName = (String) sois.readObject();
        DevelopmentFieldLizunovaVO developmentArea = (DevelopmentFieldLizunovaVO) sois.readObject();
        Integer priorityScore = (Integer) sois.readObject();
        Integer modulesCount = (Integer) sois.readObject();
        Integer requirementsSpecification = (Integer) sois.readObject();
        EmployeeLizunovaVO employee = (EmployeeLizunovaVO) sois.readObject();
        Integer complexity = (Integer) sois.readObject();
        TaskStatusLizunovaVO status = (TaskStatusLizunovaVO) sois.readObject();
        Integer performance = (Integer) sois.readObject();
        ProjectLizunovaVO project = (ProjectLizunovaVO) sois.readObject();

        TaskLizunovaVO task = new TaskLizunovaVO(null, taskName, complexity, modulesCount, priorityScore, requirementsSpecification, developmentArea, status, performance, employee, project);
  dbHandler.addTaskData(task);
    }


    public void DeleteTaskData(ObjectInputStream sois, ObjectOutputStream soos) throws IOException, ClassNotFoundException, SQLException {
        int ID = sois.readInt();
        dbHandler.deleteTaskData(ID);
    }

    public void DeleteEmployeeData(ObjectInputStream sois, ObjectOutputStream soos) throws IOException, ClassNotFoundException, SQLException {
        int ID = sois.readInt();

        dbHandler.deleteEmployeeData(ID);
    }

    public void EditEmployeeData(ObjectInputStream sois, ObjectOutputStream soos) throws IOException, ClassNotFoundException, SQLException {
        int ID = sois.readInt();
        String surName = (String) sois.readObject();
        String name = (String) sois.readObject();
        String secondName = (String) sois.readObject();
        PositionLizunovaVO position = (PositionLizunovaVO) sois.readObject();

        EmployeeLizunovaVO employee = new EmployeeLizunovaVO(ID, name, secondName, surName, position);



        dbHandler.editEmployeeData(employee);
    }

    public void AddEmployeeData(ObjectInputStream sois, ObjectOutputStream soos) throws IOException, ClassNotFoundException, SQLException {
        String surName = (String) sois.readObject();
        String name = (String) sois.readObject();
        String secondName = (String) sois.readObject();
        PositionLizunovaVO position = (PositionLizunovaVO) sois.readObject();
        EmployeeLizunovaVO employee = new EmployeeLizunovaVO(0, name, secondName, surName, position);

        dbHandler.addEmployeeData(employee);
    }


    public void EditTaskData(ObjectInputStream sois, ObjectOutputStream soos) throws IOException, ClassNotFoundException, SQLException {
        int ID = sois.readInt();
        String taskName = (String) sois.readObject();
        DevelopmentFieldLizunovaVO developmentArea = (DevelopmentFieldLizunovaVO) sois.readObject();
        Integer priorityScore = (Integer) sois.readObject();
        Integer modulesCount = (Integer) sois.readObject();
        Integer requirementsSpecification = (Integer) sois.readObject();
        EmployeeLizunovaVO employee = (EmployeeLizunovaVO) sois.readObject();
        Integer complexity = (Integer) sois.readObject();
        TaskStatusLizunovaVO status = (TaskStatusLizunovaVO) sois.readObject();
        Integer performance = (Integer) sois.readObject();
        ProjectLizunovaVO project = (ProjectLizunovaVO) sois.readObject();

        TaskLizunovaVO task = new TaskLizunovaVO(ID, taskName, complexity, modulesCount, priorityScore, requirementsSpecification, developmentArea, status, performance, employee, project);

        dbHandler.editTaskData(task);
    }

    public void Block(ObjectInputStream sois, ObjectOutputStream soos) throws IOException, ClassNotFoundException, SQLException {
        Integer userID = (Integer) sois.readObject();
        dbHandler.blockUser(userID);
    }
}
