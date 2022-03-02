package ControllersLizunovaVO;

import ClassesLizunovaVO.*;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import static MainLizunovaVO.ConnectLizunovaVO.cois;
import static MainLizunovaVO.ConnectLizunovaVO.coos;

public class EmployeesControllerLizunovaVO implements ControllerLizunovaVO {

    @FXML
    private Button saveChanges, delete, edit, addNewEmployee, back;

    @FXML
    private ComboBox<PositionLizunovaVO> editSpecialization;

    @FXML
    private TextField editSurname, editName, editSecondName;

    @FXML
    private TableColumn<EmployeeLizunovaVO, String> surname;

    @FXML
    private TableColumn<EmployeeLizunovaVO, String> secondName;

    @FXML
    private TableColumn<EmployeeLizunovaVO, String> specialization;

    @FXML
    private TableColumn<EmployeeLizunovaVO, String> department;

    @FXML
    private TableView<EmployeeLizunovaVO> tableView;

    @FXML
    private TableColumn<EmployeeLizunovaVO, String> name;


    public void autoResizeColumns(TableView<?> table) {
        //Set the right policy
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getColumns().stream().forEach((column) ->
        {
            //Minimal width = columnheader
            Text t = new Text(column.getText());
            double max = t.getLayoutBounds().getWidth();
            for (int i = 0; i < table.getItems().size(); i++) {
                //cell must not be empty
                if (column.getCellData(i) != null) {
                    t = new Text(column.getCellData(i).toString());
                    double calcwidth = t.getLayoutBounds().getWidth();
                    //remember new max-width
                    if (calcwidth > max) {
                        max = calcwidth;
                    }
                }
            }
            //set the new max-widht with some extra space
            column.setPrefWidth(max + 10.0d);
        });
    }

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

            addNewEmployee.getStyleClass().add("toggle-button");
            addNewEmployee.setText("Добавить");

            back.getStyleClass().add("secondary");
            back.setText("На главную");

            editSpecialization.setConverter(new StringConverter<PositionLizunovaVO>() {

                @Override
                public String toString(PositionLizunovaVO object) {
                    return object.getName();
                }

                @Override
                public PositionLizunovaVO fromString(String string) {
                    return editSpecialization.getItems().stream().filter(ap ->
                            ap.getName().equals(string)).findFirst().orElse(null);
                }
            });


            coos.writeObject("позиции");
            ArrayList<PositionLizunovaVO> positions = (ArrayList<PositionLizunovaVO>) cois.readObject();
            ObservableList<PositionLizunovaVO> positionsList = FXCollections.observableArrayList(positions);
            editSpecialization.setItems(positionsList);

            coos.writeObject("исполнители");
            employeeTable();
            autoResizeColumns(tableView);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        back.setOnAction(event -> {
            SignInControllerLizunovaVO x = new SignInControllerLizunovaVO();
            SignInControllerLizunovaVO.openNewSceneAdmin("../ViewsLizunovaVO/AdminWindow.fxml", back, "Главное меню");
        });
        addNewEmployee.setOnAction(event -> {
            AdminWindowControllerLizunovaVO.openNewScene("../ViewsLizunovaVO/AddNewEmployee.fxml", addNewEmployee, "Добавить нового сотрудника");
        });


        edit.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if (tableView.getSelectionModel().getSelectedItem() != null) {
                EmployeeLizunovaVO item = tableView.getSelectionModel().getSelectedItem();


                editName.setText(item.getName());
                editSecondName.setText(item.getLastName());
                editSurname.setText(item.getSecondName());
                editSpecialization.setValue(item.getPosition());
            } else {
                alert.setTitle("Сотрудник не выбран");
                alert.setHeaderText(null);
                alert.setContentText("Выберите сотрудника в таблице.");
                alert.showAndWait();
            }
        });

        delete.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            try {
                if (tableView.getSelectionModel().getSelectedItem() != null) {
                    EmployeeLizunovaVO item = tableView.getSelectionModel().getSelectedItem();
                    deleteTaskData(item.getID());
                } else {
                    alert.setTitle("Сотрудник не выбран");
                    alert.setHeaderText(null);
                    alert.setContentText("Выберите сотрудника в таблице.");
                    alert.showAndWait();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });


        saveChanges.setOnAction(event -> {
            try {
                editEmployeeData();
                coos.writeObject("исполнители");
                employeeTable();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

    }

    public static void openNewScene(String window, Button button, String title) {
        Stage stage = (Stage) button.getScene().getWindow();
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

    private void deleteTaskData(int ID) throws IOException, ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Удаление данных сотрудника");
        alert.setHeaderText(null);
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            coos.writeObject("удалить сотрудника");
            coos.writeInt(ID);
            alert.setContentText("Данные сотрудника удалены.");
            alert.showAndWait();
            coos.writeObject("исполнители");
            employeeTable();
        } else {
            alert.setTitle("Сотрудник не выбран");
            alert.setHeaderText(null);
            alert.setContentText("Выберите сотрудника в таблице");
            alert.showAndWait();
        }
    }

    void editEmployeeData() throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if ( tableView.getSelectionModel().getSelectedItem() != null) {
            if(!editName.getText().equals("") && !editSurname.getText().equals("") && !editSecondName.getText().equals("")){
                alert.setTitle("Cохранение изменений");
                alert.setHeaderText(null);
                coos.writeObject("изменить сотрудника");
                EmployeeLizunovaVO item = tableView.getSelectionModel().getSelectedItem();

                coos.writeInt(item.getID());
                coos.writeObject(editSurname.getText());
                coos.writeObject(editName.getText());
                coos.writeObject(editSecondName.getText());
                coos.writeObject(editSpecialization.getSelectionModel().getSelectedItem());

                alert.setContentText("Сохранено.");
                alert.showAndWait();
            }
            else {
                alert.setTitle("Ошибка");
                alert.setHeaderText(null);
                alert.setContentText("Введите число.");
                alert.showAndWait();
            }
        } else {
            alert.setTitle("Сотрудник не выбран");
            alert.setHeaderText(null);
            alert.setContentText("Выберите сотрудника в таблице");
            alert.showAndWait();
        }
        try {
            coos.writeObject("исполнители");
            employeeTable();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void employeeTable() throws IOException, ClassNotFoundException {
        name.setCellValueFactory( cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
        surname.setCellValueFactory( cellData -> new ReadOnlyStringWrapper(cellData.getValue().getSecondName()));
        secondName.setCellValueFactory( cellData -> new ReadOnlyStringWrapper(cellData.getValue().getLastName()));
        specialization.setCellValueFactory( cellData -> new ReadOnlyStringWrapper(cellData.getValue().getPosition().getName()));
        department.setCellValueFactory( cellData -> new ReadOnlyStringWrapper(cellData.getValue().getPosition().getDepartmentLizunovaVO().getName()));


            ArrayList<EmployeeLizunovaVO> list = new ArrayList<>();
            ArrayList<EmployeeLizunovaVO> employees = (ArrayList<EmployeeLizunovaVO>) cois.readObject();
            if (!employees.isEmpty()) {
            for (EmployeeLizunovaVO u : employees) {
                list.add(u);
            }
            tableView.setItems((FXCollections.observableList(list)));
        }
        else {
            tableView.getItems().clear();
        }
    }
}
