package ControllersLizunovaVO;

import ClassesLizunovaVO.DefaultParamsEnumLizunovaVO;
import ClassesLizunovaVO.DefaultParamsTypeEnumLizunovaVO;
import ClassesLizunovaVO.TaskLizunovaVO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static MainLizunovaVO.ConnectLizunovaVO.cois;

public class PieChartStatisticsControllerLizunovaVO implements Initializable, ControllerLizunovaVO {
        @FXML
        private PieChart pieChart;

        @FXML
        private Button back;

        TaskLizunovaVO task;

        public void setTask(TaskLizunovaVO task){
                this.task = task;
                double complexity =  DefaultParamsEnumLizunovaVO.fromInteger(task.getComplexity()).getCalcValue(DefaultParamsTypeEnumLizunovaVO.COMPLEXITY);
                double modulesCount = task.getModulesCount() * DefaultParamsTypeEnumLizunovaVO.MODULES_COUNT.getBaseValue();
                double priority = DefaultParamsEnumLizunovaVO.fromInteger(task.getPriority()).getCalcValue(DefaultParamsTypeEnumLizunovaVO.PRIORITY);
                double detailsReq = DefaultParamsEnumLizunovaVO.fromInteger(task.getRequirementsSpecification()).getCalcValue(DefaultParamsTypeEnumLizunovaVO.DETAILS_REQ);
                double performance = task.getPerformance() * DefaultParamsTypeEnumLizunovaVO.PERFORMANCE.getBaseValue();

                ObservableList<PieChart.Data> PieChartData= FXCollections.observableArrayList(
                        new PieChart.Data("Сложность логики", complexity),
                        new PieChart.Data("Производительность", performance),
                        new PieChart.Data("Приоритетность", priority),
                        new PieChart.Data("Детализация требований", detailsReq),
                        new PieChart.Data("Число модулей", modulesCount));
                pieChart.setData(PieChartData);
        }

        @Override
        public void initialize(URL url, ResourceBundle rb) {
                back.getStyleClass().add("secondary");
                back.setText("Назад");
                back.setOnAction(event -> {
                        SignInControllerLizunovaVO x = new SignInControllerLizunovaVO();
                        SignInControllerLizunovaVO.openNewSceneAdmin("../ViewsLizunovaVO/AdminWindow.fxml", back, "Главное меню");
                });
        }
}


