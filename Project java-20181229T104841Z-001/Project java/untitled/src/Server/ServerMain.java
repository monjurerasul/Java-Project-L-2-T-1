package Server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.applet.Applet;
import java.util.Random;

/**
 * Created by User on 12/7/2015.
 */
public class ServerMain extends Application {

    public static void main(String[] args) {
        Application.launch();

    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader serverFXML = new FXMLLoader(getClass().getResource("Server.fxml"));
        Parent Root = serverFXML.load();
        ServerController serverSide = (ServerController) serverFXML.getController();
        new ClientAcceptor(serverSide);
        //Server server = new Server(12345, serverSide);

        primaryStage.setTitle("Connector");
        primaryStage.setScene(new Scene(Root));
        primaryStage.show();
    }


}
