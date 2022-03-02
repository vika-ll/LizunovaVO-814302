package ControllersLizunovaVO;


import java.io.IOException;
import java.util.ArrayList;

import ClassesLizunovaVO.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import static MainLizunovaVO.ConnectLizunovaVO.cois;
import static MainLizunovaVO.ConnectLizunovaVO.coos;


public class AddNewTaskDataLizunovaVO implements ControllerLizunovaVO {

    @FXML
    private TextField edit_taskName;

    @FXML
    private ComboBox<String> edit_complexity;

    @FXML
    private ComboBox<String> edit_priority;

    @FXML
    private ComboBox<String> edit_requirementsSpecification;

    @FXML
    private ComboBox<DevelopmentFieldLizunovaVO> edit_developmentArea;

    @FXML
    private TextField edit_modulesCount;

    @FXML
    private ComboBox<TaskStatusLizunovaVO> edit_status;

    @FXML
    private TextField edit_performance;

    @FXML
    private ComboBox<EmployeeLizunovaVO> edit_employee;

    @FXML
    private ComboBox<ProjectLizunovaVO> edit_project;

    @FXML
    private Button addData, back;

    @FXML
    void initialize()  {
        try {
            addData.getStyleClass().add("toggle-button");
            addData.setText("Добавить задачу");

            back.getStyleClass().add("secondary");
            back.setText("Назад");

            addData.setOnAction(event -> {
                try {
                    addData();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            back.setOnAction(event -> {
                AddNewTaskDataLizunovaVO.openNewScene("../ViewsLizunovaVO/AdminWindow.fxml", back, "Главное меню");
            });

            coos.writeObject("области разработки");
            ArrayList<DevelopmentFieldLizunovaVO> areas = (ArrayList<DevelopmentFieldLizunovaVO>) cois.readObject();

            coos.writeObject("проекты");
            ArrayList<ProjectLizunovaVO> projects = (ArrayList<ProjectLizunovaVO>) cois.readObject();

            coos.writeObject("исполнители");
            ArrayList<EmployeeLizunovaVO> employees = (ArrayList<EmployeeLizunovaVO>) cois.readObject();

            coos.writeObject("статусы");
            ArrayList<TaskStatusLizunovaVO> statuses = (ArrayList<TaskStatusLizunovaVO>) cois.readObject();

            ObservableList<String> defaultParams = FXCollections.observableArrayList(DefaultParamsEnumLizunovaVO.names());
            ObservableList<ProjectLizunovaVO> projectsList = FXCollections.observableArrayList(projects);
            ObservableList<EmployeeLizunovaVO> employeesList = FXCollections.observableArrayList(employees);
            ObservableList<DevelopmentFieldLizunovaVO> fieldsList = FXCollections.observableArrayList(areas);
            ObservableList<TaskStatusLizunovaVO> statusesList = FXCollections.observableArrayList(statuses);
            edit_developmentArea.setItems(fieldsList);
            edit_project.setItems(projectsList);
            edit_employee.setItems(employeesList);
            edit_complexity.setItems(defaultParams);
            edit_requirementsSpecification.setItems(defaultParams);
            edit_priority.setItems(defaultParams);
            edit_status.setItems(statusesList);

            edit_status.setConverter(new StringConverter<TaskStatusLizunovaVO>() {

                @Override
                public String toString(TaskStatusLizunovaVO object) {
                    return object.getName();
                }

                @Override
                public TaskStatusLizunovaVO fromString(String string) {
                    return edit_status.getItems().stream().filter(ap ->
                            ap.getName().equals(string)).findFirst().orElse(null);
                }
            });

            edit_employee.setConverter(new StringConverter<EmployeeLizunovaVO>() {

                @Override
                public String toString(EmployeeLizunovaVO object) {
                    return object.getName();
                }

                @Override
                public EmployeeLizunovaVO fromString(String string) {
                    return edit_employee.getItems().stream().filter(ap ->
                            ap.getName().equals(string)).findFirst().orElse(null);
                }
            });

            edit_project.setConverter(new StringConverter<ProjectLizunovaVO>() {

                @Override
                public String toString(ProjectLizunovaVO object) {
                    return object.getName();
                }

                @Override
                public ProjectLizunovaVO fromString(String string) {
                    return edit_project.getItems().stream().filter(ap ->
                            ap.getName().equals(string)).findFirst().orElse(null);
                }
            });

            edit_developmentArea.setConverter(new StringConverter<DevelopmentFieldLizunovaVO>() {

                @Override
                public String toString(DevelopmentFieldLizunovaVO object) {
                    return object.getName();
                }

                @Override
                public DevelopmentFieldLizunovaVO fromString(String string) {
                    return edit_developmentArea.getItems().stream().filter(ap ->
                            ap.getName().equals(string)).findFirst().orElse(null);
                }
            });
        } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
        }}

    public static boolean isInt(String x) throws NumberFormatException
    {
        try {
            Integer.parseInt(x);
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    public void addData() throws IOException {
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
         alert.setHeaderText(null);
         String taskName = edit_taskName.getText().trim();
         Integer complexity = edit_complexity.getSelectionModel().getSelectedIndex() + 1;
         Integer modulesCount = Integer.parseInt(edit_modulesCount.getText());
         Integer priority = edit_priority.getSelectionModel().getSelectedIndex() + 1;
         Integer requirementsSpec = edit_requirementsSpecification.getSelectionModel().getSelectedIndex() + 1;
         DevelopmentFieldLizunovaVO DevelopmentFieldLizunovaVO = edit_developmentArea.getSelectionModel().getSelectedItem();
         TaskStatusLizunovaVO status = edit_status.getSelectionModel().getSelectedItem();
         Integer performance = Integer.parseInt(edit_performance.getText());
         EmployeeLizunovaVO employee = edit_employee.getSelectionModel().getSelectedItem();
         ProjectLizunovaVO project = edit_project.getSelectionModel().getSelectedItem();

         if(!taskName.equals("") && complexity != null && modulesCount != null && priority != null && requirementsSpec != null && DevelopmentFieldLizunovaVO != null && status != null && performance != null &&
        employee != null && project != null ) {
             if (complexity > 0 && modulesCount > 0 && performance > 0) {
                 coos.writeObject("новые данные");
                 coos.writeObject(edit_taskName.getText());
                 coos.writeObject(edit_developmentArea.getSelectionModel().getSelectedItem());
                 coos.writeObject(edit_priority.getSelectionModel().getSelectedIndex() + 1);
                 coos.writeObject(Integer.parseInt(edit_modulesCount.getText()));
                 coos.writeObject(edit_requirementsSpecification.getSelectionModel().getSelectedIndex() + 1);
                 coos.writeObject(edit_employee.getSelectionModel().getSelectedItem());
                 coos.writeObject(edit_complexity.getSelectionModel().getSelectedIndex() + 1);
                 coos.writeObject(edit_status.getSelectionModel().getSelectedItem());
                 coos.writeObject(Integer.parseInt(edit_performance.getText()));
                 coos.writeObject(edit_project.getSelectionModel().getSelectedItem());

                 edit_taskName.setText("");
                 edit_project.setValue(null);
                 edit_complexity.setValue("");
                 edit_project.setValue(null);
                 edit_employee.setValue(null);
                 edit_developmentArea.setValue(null);
                 edit_status.setValue(null);
                 edit_modulesCount.setText("");
                 edit_performance.setText("");
                 edit_requirementsSpecification.setValue("");
                 edit_priority.setValue("");
             }
                 else {
                     alert.setTitle("Ошибка");
                     alert.setContentText("Проверьте корректность введенных данных.");
                     alert.showAndWait();
                 }
         } else {
             alert.setTitle("Ошибка");
             alert.setContentText("Внимание: заполните все поля.");
             alert.showAndWait();
         }
     }

    public static void openNewScene(String window, Button button, String title){
        Stage stage = (Stage)button.getScene().getWindow();
        stage.close();

        FXMLLoader loader = new FXMLLoader(UserWindowControllerLizunovaVO.class.getResource(window));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = new Stage();
        stage.setTitle(title);
        Scene scene = new Scene(root);
        scene.getStylesheets().add("style.css");
        stage.setScene(scene);
        stage.show();
    }
}
