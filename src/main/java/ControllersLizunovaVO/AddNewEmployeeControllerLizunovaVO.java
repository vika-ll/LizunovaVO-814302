package ControllersLizunovaVO;

import ClassesLizunovaVO.DepartmentLizunovaVO;
import ClassesLizunovaVO.DevelopmentFieldLizunovaVO;
import ClassesLizunovaVO.PositionLizunovaVO;
import ClassesLizunovaVO.RoleLizunovaVO;
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

import java.io.IOException;
import java.util.ArrayList;

import static MainLizunovaVO.ConnectLizunovaVO.cois;
import static MainLizunovaVO.ConnectLizunovaVO.coos;


public class AddNewEmployeeControllerLizunovaVO implements ControllerLizunovaVO {

    @FXML
    private TextField userSurname;

    @FXML
    private TextField userSecondName;

    @FXML
    private Button back;

    @FXML
    private Button AddButton;

    @FXML
    private TextField userName;

    @FXML
    private ComboBox<PositionLizunovaVO> specialization;

    @FXML
    private ComboBox<DepartmentLizunovaVO> department;

    @FXML
    void initialize() {
        try {

            AddButton.getStyleClass().add("toggle-button");
            AddButton.setText("Добавить");

            back.getStyleClass().add("secondary");
            back.setText("Назад");


            back.setOnAction(event -> openNewScene("/ViewsLizunovaVO/Users.fxml", back, "Управление пользователями"));
            AddButton.setOnAction(event -> {
                try {
                    addNewEmployee();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            coos.writeObject("позиции");
            ArrayList<PositionLizunovaVO> positions = (ArrayList<PositionLizunovaVO>) cois.readObject();
            ObservableList<PositionLizunovaVO> positionsList = FXCollections.observableArrayList(positions);
            specialization.setItems(positionsList);

            coos.writeObject("департаменты");
            ArrayList<DepartmentLizunovaVO> departments = (ArrayList<DepartmentLizunovaVO>) cois.readObject();
            ObservableList<DepartmentLizunovaVO> departmentsList = FXCollections.observableArrayList(departments);
            department.setItems(departmentsList);

            department.setConverter(new StringConverter<DepartmentLizunovaVO>() {

                @Override
                public String toString(DepartmentLizunovaVO object) {
                    return object.getName();
                }

                @Override
                public DepartmentLizunovaVO fromString(String string) {
                    return department.getItems().stream().filter(ap ->
                            ap.getName().equals(string)).findFirst().orElse(null);
                }
            });

            specialization.setConverter(new StringConverter<PositionLizunovaVO>() {

                @Override
                public String toString(PositionLizunovaVO object) {
                    return object.getName();
                }

                @Override
                public PositionLizunovaVO fromString(String string) {
                    return specialization.getItems().stream().filter(ap ->
                            ap.getName().equals(string)).findFirst().orElse(null);
                }
            });
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void addNewEmployee() throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);

        String surname = userSurname.getText().trim();
        String name = userName.getText().trim();
        String secondName = userSecondName.getText().trim();
        DepartmentLizunovaVO departmentValue = department.getSelectionModel().getSelectedItem();
        PositionLizunovaVO positionValue = specialization.getSelectionModel().getSelectedItem();
        positionValue.getDepartmentLizunovaVO().setID(departmentValue.getID());

        if (!name.equals("") && !surname.equals("") && !secondName.equals("")) {
            coos.writeObject("добавить сотрудника");

            coos.writeObject(secondName);
            coos.writeObject(name);
            coos.writeObject(surname);
            coos.writeObject(positionValue);

            userName.setText("");
            userSurname.setText("");
            userSecondName.setText("");
            department.setValue(null);
            specialization.setValue(null);
        }
        else {
            alert.setTitle("Ошибка");
            alert.setContentText("Заполните все поля.");
            alert.showAndWait();
        }
    }

    public static boolean isInt(String x) throws NumberFormatException
    {
        try {
            Integer.parseInt(x);
            return true;
        } catch(Exception e) {
            return false;
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