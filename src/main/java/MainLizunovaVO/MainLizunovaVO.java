package MainLizunovaVO;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.UnknownHostException;
import ControllersLizunovaVO.SignInControllerLizunovaVO;

public class MainLizunovaVO extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setController(new SignInControllerLizunovaVO());
        Parent root = FXMLLoader.load(getClass().getResource("../ViewsLizunovaVO/SignIn.fxml"));
        primaryStage.setTitle("Авторизация");
        Scene scene = new Scene(root, 1219, 662);
        scene.getStylesheets().add("style.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        try {
            ConnectLizunovaVO.servConnect();
            launch(args);
        }
        catch (UnknownHostException e){}
        catch (IOException e){}
        finally {
        }
        try{
            ConnectLizunovaVO.clientSocket.close();
            ConnectLizunovaVO.coos.close();
            ConnectLizunovaVO.cois.close();
            System.out.println("Сonnection ended.");
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}



