package ControllersLizunovaVO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;


import ClassesLizunovaVO.*;

import com.sun.javafx.scene.control.skin.TableHeaderRow;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import static MainLizunovaVO.ConnectLizunovaVO.cois;
import static MainLizunovaVO.ConnectLizunovaVO.coos;

public class AdminWindowControllerLizunovaVO implements ControllerLizunovaVO {

    @FXML
    private TextField searchField;

    @FXML
    private TextField edit_taskName;

    @FXML
    private TextField calcTimeField;

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
    private TableView<TaskLizunovaVO> tableView;

    @FXML
    private TableColumn<TaskLizunovaVO, String> taskName;

    @FXML
    private TableColumn<TaskLizunovaVO, Integer> modulesCount;

    @FXML
    private TableColumn<TaskLizunovaVO, String> complexity;

    @FXML
    private TableColumn<TaskLizunovaVO, String> priority;

    @FXML
    private TableColumn<TaskLizunovaVO, String> requirementsSpecification;

    @FXML
    private TableColumn<TaskLizunovaVO, String> developmentArea;

    @FXML
    private TableColumn<TaskLizunovaVO, String> status;

    @FXML
    private TableColumn<TaskLizunovaVO, Integer> performance;

    @FXML
    private TableColumn<TaskLizunovaVO, String> employee;

    @FXML
    private TableColumn<TaskLizunovaVO, String> project;

    @FXML
    private TableColumn<TaskLizunovaVO, Integer> time;

    @FXML
    private Button reload, add, manageUsers, delete, signIN, search, edit, save, manageEmployees, saveFile, statistics, calcTime;


    public void autoResizeColumns(TableView<?> table)
    {
        table.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY);
        table.getColumns().stream().forEach( (column) ->
        {
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

    @FXML
    void initialize()  {
        try {
            calcTimeField.setEditable(false);
            tableView.setPlaceholder(new Label(""));
            autoResizeColumns(tableView);
            tableView.getStyleClass().add("table-view");
            edit_employee.getStyleClass().add("combo-box");

            save.getStyleClass().add("toggle-button");
            save.setText("Сохранить изменения");
            add.getStyleClass().add("toggle-button");
            add.setText("Добавить");

            calcTime.getStyleClass().add("toggle-button");
            calcTime.setText("Рассчитать время");

            reload.getStyleClass().add("toggle-button");
            reload.setText("Обновить");

            delete.getStyleClass().add("toggle-button");
            delete.setText("Удалить");

            edit.getStyleClass().add("toggle-button");
            edit.setText("Редактировать запись");

            search.getStyleClass().add("toggle-button");
            search.setText("Поиск");

            signIN.getStyleClass().add("toggle-button");
            signIN.setText("Сменить аккаунт");

            manageUsers.getStyleClass().add("secondary");
            manageUsers.setText("Управление пользователями");

            manageEmployees.getStyleClass().add("secondary");
            manageEmployees.setText("Исполнители");

            statistics.getStyleClass().add("secondary");
            statistics.setText("Статистика");

            saveFile.getStyleClass().add("secondary");
            saveFile.setText("Сохранить отчет");


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

            coos.writeObject("главная");
            adminTable();
            } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
       }

        saveFile.setOnAction(event -> {
            try {
                writeFile();
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        calcTime.setOnAction(event ->
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            try {

                if (tableView.getSelectionModel().getSelectedItem() != null) {
                    TaskLizunovaVO task = tableView.getSelectionModel().getSelectedItem();

                    double complexity =  DefaultParamsEnumLizunovaVO.fromInteger(task.getComplexity()).getCalcValue(DefaultParamsTypeEnumLizunovaVO.COMPLEXITY);
                    double modulesCount = task.getModulesCount() * DefaultParamsTypeEnumLizunovaVO.MODULES_COUNT.getBaseValue();
                    double priority = DefaultParamsEnumLizunovaVO.fromInteger(task.getPriority()).getCalcValue(DefaultParamsTypeEnumLizunovaVO.PRIORITY);
                    double detailsReq = DefaultParamsEnumLizunovaVO.fromInteger(task.getRequirementsSpecification()).getCalcValue(DefaultParamsTypeEnumLizunovaVO.DETAILS_REQ);
                    double performance = task.getPerformance() * DefaultParamsTypeEnumLizunovaVO.PERFORMANCE.getBaseValue();
                    Double value = Math.ceil(complexity + modulesCount + priority + detailsReq + performance);
                    Integer valueUpscale = value.intValue();
                    coos.writeObject("записать время");
                    coos.writeObject(task.getID());
                    coos.writeObject(valueUpscale);
                    calcTimeField.setText(valueUpscale.toString());
                    coos.writeObject("главная");
                    adminTable();
                } else {
                    alert.setTitle("Задача не выбрана");
                    alert.setHeaderText(null);
                    alert.setContentText("Выберите задачу из таблицы.");
                    alert.showAndWait();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        manageUsers.setOnAction(event ->
        {
            ViewUsersControllerLizunovaVO zzz=new ViewUsersControllerLizunovaVO();
            ViewUsersControllerLizunovaVO.openNewScene("../ViewsLizunovaVO/Users.fxml", manageUsers, "Управление пользователями");
        });

        statistics.setOnAction(event ->
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if (tableView.getSelectionModel().getSelectedItem() != null) {
                AdminWindowControllerLizunovaVO.openStatisticsScene("../ViewsLizunovaVO/pieChartStatistics.fxml", statistics, "Статистика", tableView.getSelectionModel().getSelectedItem());
            } else {
                alert.setTitle("Задача не выбрана");
                alert.setHeaderText(null);
                alert.setContentText("Выберите задачу в таблице.");
                alert.showAndWait();
            }
        });

        signIN.setOnAction(event -> {
            SignInControllerLizunovaVO.openNewSceneAdmin("../ViewsLizunovaVO/SignIn.fxml", signIN, "Авторизация");
        });

        add.setOnAction(event -> openNewScene("../ViewsLizunovaVO/AddNewTaskData.fxml", add, "Добавление новой задачи"));

        manageEmployees.setOnAction(event ->
        {
            EmployeesControllerLizunovaVO.openNewScene("../ViewsLizunovaVO/Employees.fxml", manageEmployees, "Управление сотрудниками");
        });

        edit.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if (tableView.getSelectionModel().getSelectedItem() != null) {
                TaskLizunovaVO item = tableView.getSelectionModel().getSelectedItem();


                edit_complexity.setValue(DefaultParamsEnumLizunovaVO.fromInteger(item.getComplexity()).toString());
                edit_modulesCount.setText(Integer.toString(item.getModulesCount()));
                edit_taskName.setText(item.getTaskName());
                edit_status.setValue(item.getStatus());
                edit_performance.setText(Integer.toString(item.getPerformance()));
                edit_employee.setValue(item.getEmployee());
                edit_project.setValue(item.getProject());
                edit_developmentArea.setValue(item.getDevelopmentArea());
                edit_priority.setValue(DefaultParamsEnumLizunovaVO.fromInteger(item.getPriority()).toString());
                edit_requirementsSpecification.setValue(DefaultParamsEnumLizunovaVO.fromInteger(item.getRequirementsSpecification()).toString());
            } else {
                alert.setTitle("Задача не выбрана");
                alert.setHeaderText(null);
                alert.setContentText("Выберите задачу в таблице.");
                alert.showAndWait();
            }
        });

        delete.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            try {
                if (tableView.getSelectionModel().getSelectedItem() != null) {
                    TaskLizunovaVO item = tableView.getSelectionModel().getSelectedItem();
                  //  WorkerLizunovaVO item = tableView.getSelectionModel().getSelectedItem();
                    deleteTaskData(item.getID());
                } else {
                    alert.setTitle("Задача не выбрана");
                    alert.setHeaderText(null);
                    alert.setContentText("Выберите задачу в таблице.");
                    alert.showAndWait();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        save.setOnAction(event -> {
            try {
                editTaskData();
                coos.writeObject("главная");
                adminTable();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        reload.setOnAction(event -> {
            try {
                coos.writeObject("главная");

                adminTable();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        search.setOnAction(event -> {
            try {
                searchTask();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        signIN.setOnAction(event -> {
            SignInControllerLizunovaVO.openNewSceneAdmin("../ViewsLizunovaVO/SignIn.fxml", signIN, "Авторизация");
        });

    }

    private void writeFile() throws FileNotFoundException {
        ArrayList<TaskLizunovaVO> list = new ArrayList<>();
            int size=tableView.getItems().size();
            for (int i=0; i<size;i++)
                list.add(tableView.getItems().get(i));
            PrintWriter file;
            file = new PrintWriter("otchet.txt");
            for (int i=0; i<list.size(); i++)
            {
                TaskLizunovaVO item = list.get(i);
                String taskName = item.getTaskName();
                String complexity = DefaultParamsEnumLizunovaVO.fromInteger(item.getPriority()).toString();


                String modulesCount = Integer.toString(item.getModulesCount());
                String status = item.getStatus().getName();
                String performance = Integer.toString(item.getPerformance());
                String project = item.getProject().getName();
                String devArea = item.getDevelopmentArea().getName();
                String time = item.getTime().toString();
                String priority = DefaultParamsEnumLizunovaVO.fromInteger(item.getPriority()).toString();
                String detailsReq = DefaultParamsEnumLizunovaVO.fromInteger(item.getRequirementsSpecification()).toString();
                EmployeeLizunovaVO employee = item.getEmployee();


                String s = "Задача №"+(i+1)+"#Название: " + taskName + "#Сложность логики: " + complexity
                        + "#Число модулей: " + modulesCount
                        + "#Приоритетность: " + priority
                        + "#Детализация требовании: " + detailsReq
                        + "#Область разработки: "  + devArea
                        + "#Статус задачи: " + status
                        + "#Производительность: " + performance
                        + "#Проект: " + project
                        + "#Исполнитель: " + employee.name + " " + employee.getSecondName() + " " + employee.getLastName()
                        + "#Время: " + time
                        +"\n";
                String[] list1 = s.split("#");
                for (String value : list1)
                {file.println(value);
                    System.out.println(value);
                }
            }
            file.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Сохранение отчета");
            alert.setHeaderText(null);
            alert.setContentText("Отчет сохранен.");
            alert.showAndWait();
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

    public static void openStatisticsScene(String window, Button button, String title, TaskLizunovaVO task){
        Stage st = new Stage();
        st.close();
        FXMLLoader loader = new FXMLLoader(UserWindowControllerLizunovaVO.class.getResource(window));

        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        PieChartStatisticsControllerLizunovaVO controller = loader.<PieChartStatisticsControllerLizunovaVO>getController();
        controller.setTask(task);

        Scene scene = new Scene(root);
        scene.getStylesheets().add("style.css");
        st.setScene(scene);
        st.setTitle(title);
        st.show();
    }

    public void adminTable() throws IOException, ClassNotFoundException {
        taskName.setCellValueFactory(new PropertyValueFactory<TaskLizunovaVO, String>("taskName"));
        complexity.setCellValueFactory( cellData -> new ReadOnlyStringWrapper(DefaultParamsEnumLizunovaVO.fromInteger(cellData.getValue().getComplexity()).toString()));
        modulesCount.setCellValueFactory(new PropertyValueFactory<TaskLizunovaVO, Integer>("modulesCount"));
        priority.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(DefaultParamsEnumLizunovaVO.fromInteger(cellData.getValue().getPriority()).toString()));
        requirementsSpecification.setCellValueFactory( cellData -> new ReadOnlyStringWrapper(DefaultParamsEnumLizunovaVO.fromInteger(cellData.getValue().getRequirementsSpecification()).toString()));
        developmentArea.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getDevelopmentArea().name));
        status.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getStatus().name));
        performance.setCellValueFactory(new PropertyValueFactory<TaskLizunovaVO, Integer>("performance"));
        employee.setCellValueFactory(cellData -> new ReadOnlyStringWrapper((cellData.getValue().getEmployee().name)));
        project.setCellValueFactory(cellData -> new ReadOnlyStringWrapper((cellData.getValue().getProject().name)));
        time.setCellValueFactory(new PropertyValueFactory<TaskLizunovaVO, Integer>("time"));
        ArrayList<TaskLizunovaVO> list = new ArrayList<>();
        ArrayList<TaskLizunovaVO> workers = (ArrayList<TaskLizunovaVO>) cois.readObject();
        if (!workers.isEmpty()) {
            System.out.println(workers.get(0));
            for (TaskLizunovaVO u : workers) {
                list.add(u);
            }
            tableView.setItems((FXCollections.observableList(list)));
        }
        else {
            tableView.getItems().clear();
        }
    }

    private void deleteTaskData(int ID) throws IOException, ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Удаление задачи");
        alert.setHeaderText(null);
        if (tableView.getSelectionModel().getSelectedItem() != null) {
                coos.writeObject("удалить данные");
                coos.writeInt(ID);
                alert.setContentText("Задача удалена.");
                alert.showAndWait();
                coos.writeObject("главная");
                adminTable();
            } else {
            alert.setTitle("Задача не выбрана");
            alert.setHeaderText(null);
            alert.setContentText("Выберите задачу в таблице");
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

    private void editTaskData() throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if ( tableView.getSelectionModel().getSelectedItem() != null) {
                if(isInt(edit_modulesCount.getText())&&!edit_taskName.getText().equals("")){
                    alert.setTitle("Cохранение изменений");
                    alert.setHeaderText(null);
                    TaskLizunovaVO item = tableView.getSelectionModel().getSelectedItem();
                    coos.writeObject("cохранение изменений");

                    coos.writeInt(item.getID());
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
                alert.setTitle("Задача не выбрана");
                alert.setHeaderText(null);
                alert.setContentText("Выберите задачу в таблице");
                alert.showAndWait();
            }
        try {
            coos.writeObject("главная");
            adminTable();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void searchTask() throws IOException, ClassNotFoundException {
        String workerSearch = searchField.getText();

        if (!workerSearch.equals("")) {
                ObservableList<TaskLizunovaVO> filteredData = FXCollections.observableArrayList();
                for (TaskLizunovaVO product : tableView.getItems()) {
                    if(product.getTaskName().contains(workerSearch))
                        filteredData.add(product);
                }
                tableView.setItems(filteredData);
        }
        else {
            coos.writeObject("главная");
            adminTable();
        }
    }
}
