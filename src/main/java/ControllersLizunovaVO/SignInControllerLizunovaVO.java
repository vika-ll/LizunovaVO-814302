package ControllersLizunovaVO;

import java.io.IOException;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static MainLizunovaVO.ConnectLizunovaVO.cois;
import static MainLizunovaVO.ConnectLizunovaVO.coos;

public class SignInControllerLizunovaVO implements ControllerLizunovaVO {

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField loginField;

    @FXML
    private Button signUpButton;

    @FXML
    private Button signInButton;


    @FXML
    void initialize() {
        signInButton.getStyleClass().add("toggle-button");
        signInButton.setText("Войти");
        signInButton.setOnAction(event -> {
                  try {
                    loginUser();
                } catch (ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                }
            }
        );
        signUpButton.setOnAction(event -> openNewSceneAdmin("../ViewsLizunovaVO/SignUP.fxml", signUpButton, "Регистрация"));

    }

    private void loginUser() throws IOException, ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);

        coos.writeObject("вход");
        String login = loginField.getText().trim();
        String pass = passwordField.getText().trim();
        if ((!login.equals("")) && (!pass.equals("")))
        {
            coos.writeObject(login);
            coos.writeObject(pass);
            String root = (String) cois.readObject();
            if (root.equals("Администратор"))
                openNewSceneAdmin("../ViewsLizunovaVO/AdminWindow.fxml", signInButton, "Главное меню");
            if (root.equals("Пользователь")) {
                String params=loginField.getText().trim();
                openNewSceneUser(params, "../ViewsLizunovaVO/UserWindow.fxml", signInButton, "Главное меню пользователя");
            }
            if (root.equals("Заблокирован")){
                alert.setContentText("Данный аккаунт заблокирован. Для восстановления доступа обратитесь к руководителю отдела.");
                alert.showAndWait();
            }
            if (root.equals("null")) {
                alert.setContentText("Пользователь не зарегистрирован в системе.");
                alert.showAndWait();
            }
        } else {
            alert.setContentText("Необходимо заполнить все поля.");
            alert.showAndWait();
        }
    }



    public static void openNewSceneAdmin(String window, Button button, String title){
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


    public static void openNewSceneUser(String params, String window, Button button, String title) throws IOException, ClassNotFoundException {
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


