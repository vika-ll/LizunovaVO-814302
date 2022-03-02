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

public class AddNewUserControllerLizunovaVO implements ControllerLizunovaVO {

    @FXML
    private TextField userSurname;

    @FXML
    private TextField userSecondName;

    @FXML
    private TextField userLogin;

    @FXML
    private TextField userPass;

    @FXML
    private Button back;

    @FXML
    private Button AddButton;

    @FXML
    private TextField userName;

    @FXML
    private ComboBox<RoleLizunovaVO> userRole;

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
                    addUser();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            coos.writeObject("роли");
            ArrayList<RoleLizunovaVO> roles = (ArrayList<RoleLizunovaVO>) cois.readObject();

            ObservableList<RoleLizunovaVO> rolesList = FXCollections.observableArrayList(roles);

            userRole.setItems(rolesList);
            userRole.setConverter(new StringConverter<RoleLizunovaVO>() {

                @Override
                public String toString(RoleLizunovaVO object) {
                    return object.getName();
                }

                @Override
                public RoleLizunovaVO fromString(String string) {
                    return userRole.getItems().stream().filter(ap ->
                            ap.getName().equals(string)).findFirst().orElse(null);
                }
            });
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void addUser() throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        String login = userLogin.getText().trim();
        String surname = userSurname.getText().trim();
        String name = userName.getText().trim();
        String secondName = userSecondName.getText().trim();
        String pass = userPass.getText().trim();

        RoleLizunovaVO role = userRole.getSelectionModel().getSelectedItem();

        if (!name.equals("") && !surname.equals("") && !login.equals("") && !pass.equals("")) {
                    coos.writeObject("добавить пользователя");
                    coos.writeObject(login);
                    coos.writeObject(name);
                    coos.writeObject(surname);
                    coos.writeObject(secondName);
                    coos.writeObject(pass);
                    coos.writeObject(role.getID());
                    userName.setText("");
                    userSurname.setText("");
                    userSecondName.setText("");
                    userLogin.setText("");
                    userPass.setText("");
                    userRole.setValue(null);
                    try {
                        if(cois.readObject().equals("Данные уже записаны."))
                        {
                            alert.setTitle("Ошибка");
                            alert.setContentText("Такой пользователь уже существует.");
                            alert.showAndWait();
                        }
                        else {
                            alert.setTitle("Добавление пользователя");
                            alert.setContentText("Новый аккаунт успешно добавлен.");
                            alert.showAndWait();
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
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
    }
}
