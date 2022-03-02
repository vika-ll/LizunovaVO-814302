package ControllersLizunovaVO;

import java.io.IOException;
import java.util.ArrayList;

import ClassesLizunovaVO.ProfileLizunovaVO;
import ClassesLizunovaVO.RoleLizunovaVO;
import ClassesLizunovaVO.TaskLizunovaVO;
import ClassesLizunovaVO.UserLizunovaVO;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import static MainLizunovaVO.ConnectLizunovaVO.cois;
import static MainLizunovaVO.ConnectLizunovaVO.coos;

public class ViewUsersControllerLizunovaVO implements ControllerLizunovaVO {
    @FXML
    private Button saveChanges, delete, edit, AddNewUser, back, block;

    @FXML
    private ComboBox<RoleLizunovaVO> editRole;

    @FXML
    private TextField editSurname, editName, editSecondName, editPassword;

    @FXML
    private TableColumn<UserLizunovaVO, String> password;

    @FXML
    private TableColumn<UserLizunovaVO, String> surname;

    @FXML
    private TableColumn<UserLizunovaVO, String> secondName;

    @FXML
    private TableColumn<UserLizunovaVO, String> role;

    @FXML
    private TableColumn<UserLizunovaVO, String> login;

    @FXML
    private TableView<UserLizunovaVO> tableView;

    @FXML
    private TableColumn<UserLizunovaVO, String> name;

    @FXML
    void initialize() {
        try {
            tableView.setPlaceholder(new Label(""));
            saveChanges.getStyleClass().add("toggle-button");
            saveChanges.setText("Сохранить изменения");

            delete.getStyleClass().add("toggle-button");
            delete.setText("Удалить");

            edit.getStyleClass().add("toggle-button");
            edit.setText("Редактировать запись");

            AddNewUser.getStyleClass().add("toggle-button");
            AddNewUser.setText("Добавить");

            block.getStyleClass().add("toggle-button");
            block.setText("Заблокировать");

            back.getStyleClass().add("secondary");
            back.setText("На главную");

            coos.writeObject("роли");
            ArrayList<RoleLizunovaVO> roles = (ArrayList<RoleLizunovaVO>) cois.readObject();


            ObservableList<RoleLizunovaVO> rolesList = FXCollections.observableArrayList(roles);

            editRole.setItems(rolesList);

            editRole.setConverter(new StringConverter<RoleLizunovaVO>() {

                @Override
                public String toString(RoleLizunovaVO object) {
                    return object.getName();
                }

                @Override
                public RoleLizunovaVO fromString(String string) {
                    return editRole.getItems().stream().filter(ap ->
                            ap.getName().equals(string)).findFirst().orElse(null);
                }
            });
            allUsers();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        back.setOnAction(event -> {
            SignInControllerLizunovaVO x = new SignInControllerLizunovaVO();
            SignInControllerLizunovaVO.openNewSceneAdmin("../ViewsLizunovaVO/AdminWindow.fxml", back, "Главное меню");
        });
        AddNewUser.setOnAction(event -> {
            SignInControllerLizunovaVO a = new SignInControllerLizunovaVO();
            SignInControllerLizunovaVO.openNewSceneAdmin("../ViewsLizunovaVO/AddNewUser.fxml", AddNewUser, "Добавление нового пользователя");
        });

        edit.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if (tableView.getSelectionModel().getSelectedItem() != null) {
                UserLizunovaVO userLizunovaVO = tableView.getSelectionModel().getSelectedItem();
                editSurname.setText(userLizunovaVO.getUserProfile().getLastName());
                editSecondName.setText(userLizunovaVO.getUserProfile().getSecondName());
                editName.setText(userLizunovaVO.getUserProfile().getName());
                editPassword.setText(userLizunovaVO.getPassword());
                editRole.setValue(userLizunovaVO.getUserRole());
            } else {
                alert.setTitle("Пользователь не выбран");
                alert.setHeaderText(null);
                alert.setContentText("Выберите пользователя в таблице.");
                alert.showAndWait();
            }
        });

        saveChanges.setOnAction(event -> {
            try {
                SaveEdit();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        delete.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            try {
                if (tableView.getSelectionModel().getSelectedItem() != null) {
                    UserLizunovaVO userLizunovaVO = tableView.getSelectionModel().getSelectedItem();
                    deleteUser(userLizunovaVO.getID_user());
                } else {
                    alert.setTitle("Пользователь не выбран");
                    alert.setHeaderText(null);
                    alert.setContentText("Выберите пользователя в таблице.");
                    alert.showAndWait();
                }

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        block.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            try {
                if (tableView.getSelectionModel().getSelectedItem() != null) {
                    UserLizunovaVO userLizunovaVO = tableView.getSelectionModel().getSelectedItem();
                    blockUser(userLizunovaVO.getID_user());
                } else {
                    alert.setTitle("Пользователь не выбран");
                    alert.setHeaderText(null);
                    alert.setContentText("Выберите пользователя в таблице.");
                    alert.showAndWait();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

    }

    private void autoResizeColumns(TableView<?> table)
    {
        //Set the right policy
        table.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY);
        table.getColumns().stream().forEach( (column) ->
        {
            //Minimal width = columnheader
            Text t = new Text( column.getText() );
            double max = t.getLayoutBounds().getWidth();
            for ( int i = 0; i < table.getItems().size(); i++ )
            {
                //cell must not be empty
                if ( column.getCellData( i ) != null )
                {
                    t = new Text( column.getCellData( i ).toString() );
                    double calcwidth = t.getLayoutBounds().getWidth();
                    //remember new max-width
                    if ( calcwidth > max )
                    {
                        max = calcwidth;
                    }
                }
            }
            //set the new max-widht with some extra space
            column.setPrefWidth( max + 10.0d );
        } );
    }

    private void blockUser(Integer id) throws IOException, ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Блокировка");
        alert.setHeaderText(null);
        coos.writeObject("заблокировать");
        coos.writeObject(id);
        alert.setContentText("Аккаунт " + login.getText() + " заблокирован.");
        alert.showAndWait();
        allUsers();
    }

    private void allUsers() throws IOException, ClassNotFoundException {
        coos.writeObject("все пользователи");

        login.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getLogin()));
        name.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getUserProfile().getName()));
        secondName.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getUserProfile().getSecondName()));
        surname.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getUserProfile().getLastName()));
        password.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getPassword()));
        role.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getUserRole().getName()));


        ArrayList<UserLizunovaVO> list = new ArrayList<>();
        ArrayList<UserLizunovaVO> users = (ArrayList<UserLizunovaVO>) cois.readObject();
        if (!users.isEmpty()) {
            for (UserLizunovaVO u : users) {
                list.add(u);
            }
            tableView.setItems((FXCollections.observableList(list)));
            autoResizeColumns(tableView);
        } else {
            tableView.getItems().clear();
        }
    }


    private void deleteUser(Integer id) throws IOException, ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Удаление пользователя");
        alert.setHeaderText(null);
        coos.writeObject("удалить пользователя");
        coos.writeObject(id);
        alert.setContentText("Пользователь удален");
        alert.showAndWait();
        allUsers();
    }

    private void SaveEdit() throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

            if (tableView.getSelectionModel().getSelectedItem() != null) {
                alert.setTitle("Сохранение");
                alert.setHeaderText(null);
                coos.writeObject("сохранить");
                UserLizunovaVO userLizunovaVO = tableView.getSelectionModel().getSelectedItem();

            coos.writeInt(userLizunovaVO.getID_user());
            coos.writeObject(editSurname.getText());
            coos.writeObject(editName.getText());
            coos.writeObject(editSecondName.getText());
            coos.writeObject(editPassword.getText());
            coos.writeObject(editRole.getSelectionModel().getSelectedItem().getID());

            editSurname.setText("");
            editSecondName.setText("");
            editName.setText("");
            editPassword.setText("");
            editRole.setValue(null);

            alert.setContentText("Данные сохранены.");
            alert.showAndWait();
            } else {
                alert.setTitle("Пользователь не выбран");
                alert.setHeaderText(null);
                alert.setContentText("Выберите пользователя.");
                alert.showAndWait();
            }

        try {
            allUsers();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void openNewScene(String window, Button button,String title){
        Stage stage = (Stage)button.getScene().getWindow();
        stage.close();

        FXMLLoader loader = new FXMLLoader(AddNewTaskDataLizunovaVO.class.getResource(window));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = new Stage();
        stage.setTitle(title);
        stage.setResizable(false);
        Scene scene = new Scene(root);
        scene.getStylesheets().add("style.css");
        stage.setScene(scene);

        stage.show();
    }

}