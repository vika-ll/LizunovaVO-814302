package ControllersLizunovaVO;


import java.io.IOException;
import java.util.ArrayList;

import ClassesLizunovaVO.DefaultParamsEnumLizunovaVO;
import ClassesLizunovaVO.TaskLizunovaVO;
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

import static MainLizunovaVO.ConnectLizunovaVO.cois;
import static MainLizunovaVO.ConnectLizunovaVO.coos;

public class UserWindowControllerLizunovaVO implements ControllerLizunovaVO {

    public static int id;

    @FXML
    private Button back, search;

    @FXML
    private TextField searchField;

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
    void initialize() {
        try {
            tableView.setPlaceholder(new Label(""));
            autoResizeColumns(tableView);
            tableView.getStyleClass().add("table-view");

            back.getStyleClass().add("toggle-button");
            back.setText("Сменить аккаунт");
            search.getStyleClass().add("toggle-button");
            search.setText("Поиск");
            coos.writeObject("главная");
            getUserWindow();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        back.setOnAction(event -> {
            SignInControllerLizunovaVO e = new SignInControllerLizunovaVO();
            SignInControllerLizunovaVO.openNewSceneAdmin("/ViewsLizunovaVO/SignIn.fxml", back, "Авторизация");
        });

        search.setOnAction(event -> {
            try {
                searchUser();
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

    public static boolean isInt(String x) throws NumberFormatException
    {
        try {
            Integer.parseInt(x);
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    public static void  getUserID(int ID){
        id = ID;
        System.out.println(id);
    }

    public void getUserWindow() throws IOException, ClassNotFoundException {

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

    private void searchUser() throws IOException, ClassNotFoundException {
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
            getUserWindow();
        }
    }

        public static void openNewScene (String window, Button button, String title){
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
            stage.setScene(new Scene(root));
            stage.show();
        }
}
