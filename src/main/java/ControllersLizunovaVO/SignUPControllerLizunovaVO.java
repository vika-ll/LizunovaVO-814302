package ControllersLizunovaVO;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static MainLizunovaVO.ConnectLizunovaVO.cois;
import static MainLizunovaVO.ConnectLizunovaVO.coos;


public class SignUPControllerLizunovaVO implements ControllerLizunovaVO {


    @FXML
    private TextField name;

    @FXML
    private TextField password;

    @FXML
    private TextField login;

    @FXML
    private Button back;

    @FXML
    private Button saveChanges;

    @FXML
    private TextField surname;

    @FXML
    private TextField secondName;

    @FXML
    void initialize() {

        saveChanges.setOnAction(event -> {
            try {
                signUpUser();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        back.setOnAction(event -> {
            SignInControllerLizunovaVO m = new SignInControllerLizunovaVO();
            SignInControllerLizunovaVO.openNewSceneAdmin("/ViewsLizunovaVO/SignIn.fxml", back, "Авторизация");
        });
    }

    private void signUpUser() throws IOException, ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        String Name = name.getText().trim();
        String Surname = surname.getText().trim();
        String SecondName = secondName.getText().trim();
        String userLogin = login.getText().trim();
        String Password = password.getText().trim();
        if(!Name.equals("") && !Surname.equals("") && !SecondName.equals("") && !userLogin.equals("") && !Password.equals("")){
                coos.writeObject("регистрация");
                coos.writeObject(userLogin);
                coos.writeObject(Name);
                coos.writeObject(Surname);
                coos.writeObject(SecondName);
                coos.writeObject(Password);
                coos.writeObject(2);

                name.setText("");
                surname.setText("");
                login.setText("");
                password.setText("");
                secondName.setText("");
                if(cois.readObject().equals("Данные уже записаны."))
                {
                    alert.setTitle("Ошибка");
                    alert.setContentText("Пользователь с таким логином уже существует.");
                    alert.showAndWait();
                }
                else {
                alert.setContentText("Вы успешно зарегистрированы.");
                alert.setTitle("Регистрация");
                alert.showAndWait();
                }
        }
        else {
            alert.setContentText("Необходимо заполнить все поля.");
            alert.setTitle("Ошибка");
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

        FXMLLoader loader = new FXMLLoader(SignUPControllerLizunovaVO.class.getResource(window));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = new Stage();
        stage.setTitle(title);
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
