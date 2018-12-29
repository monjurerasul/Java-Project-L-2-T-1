package Client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by User on 12/7/2015.
 */
public class ClientMain extends Application {

    Stage stage;
    ReadThread readThread;

    public static void main(String[] args) {
        Application.launch();
    }



    @Override
    public void start(Stage primaryStage) throws Exception{
        stage=primaryStage;
        showConnectionPage();

    }
    public void showConnectionPage() throws Exception
    {
        FXMLLoader FXML = new FXMLLoader(getClass().getResource("Connection.fxml"));
        Parent Root = FXML.load();

        ConnectionController connectionController=FXML.getController();
        connectionController.setClientMain(this);

        stage.setTitle("Connector");
        stage.setScene(new Scene(Root));
        stage.show();


    }
    HashMap<String, ImageView> player= new HashMap<>();
    List<ImageView> imageViews=new ArrayList<>();
    Label Line;
    Label Info;
    String str;
    AnchorPane Root;
    public void showGamePage(Socket socket,String name) throws Exception
    {
        //FXMLLoader FXML = new FXMLLoader(getClass().getResource("game.fxml"));
        //Parent Root = FXML.load();
        //GameController gameController=FXML.getController();
        //gameController.setClientName(name);
        //gameController.setSocket(socket);
        /*<children>
        <Label fx:id="Info" text="Label" />
        <TextField fx:id="Input" layoutX="84.0" layoutY="109.0" prefHeight="85.0" prefWidth="444.0" />
        <Label fx:id="Line" layoutX="14.0" layoutY="24.0" text="Label" />
        <ImageView fitHeight="49.0" fitWidth="77.0" layoutX="53.0" layoutY="235.0" pickOnBounds="true" preserveRatio="true">
        <image>
        <Image url="@c1.png" />
        </image>
        </ImageView>
        <ImageView fitHeight="49.0" fitWidth="77.0" layoutX="46.0" layoutY="319.0" pickOnBounds="true" preserveRatio="true">
        <image>
        <Image url="@c2.png" />
        </image>
        </ImageView>
        </children>
        </AnchorPane>*/
        Root = new AnchorPane();
        ClientName = name;
        Info =  new Label();
        TextField Input = new TextField();
        Input.setLayoutX(84);
        Input.setLayoutY(109);
        Input.prefHeight(85.0);
        Input.prefWidth(444.0);
        Input.setMinWidth(444);
        Line = new Label();
        Line.setLayoutX(14);
        Line.setLayoutY(24);
        Line.setFont(new Font(18));
        Line.setText("Label");

        readThread=new ReadThread(socket,this);
        for (int i=0;i<5;i++){
            ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource("c1.png"))));
            imageView.setFitHeight(49);
            imageView.setFitWidth(77);
            imageView.setLayoutX(53);
            imageView.setLayoutY(235 + i * 100);
            imageView.setPickOnBounds(true);
            imageView.setPreserveRatio(true);
            imageView.setVisible(false);
            Root.getChildren().add(imageView);
            imageViews.add(imageView);
        }



        Root.getChildren().addAll(Input, Info, Line);
        stage.setTitle("Connector");
        stage.setScene(new Scene(Root, 600, 600));

        Info.setText("Waiting for server");
        Line.setText("");
        //Input.setDisable(true);
        Input.textProperty().addListener((observable, oldValue, newValue) -> {
            if (str.startsWith(newValue)) {
                Line.setTextFill(Color.GREEN);
                double dis = newValue.length()*400.0/str.length();
                ImageView P1=player.get(ClientName);
                P1.setLayoutX(53 + dis);
                WriteThread.Write(socket,"score " +ClientName+" "+ dis);
                if (str.equals(newValue)) {
                    long typingTime = System.currentTimeMillis() - starting;
                    double score = str.length() * 60000 / typingTime;
                    WriteThread.Write(socket, ClientName + " " + String.valueOf(score));
                    Input.setDisable(true);
                    Info.setText("Your score is " + score + "Letter per min .Waiting for final result.");
                } else Info.setText("Go on typing.");
            } else {
                Line.setTextFill(Color.RED);
                Info.setText("Wrong.Please correct it.");
            }
        });
        stage.show();

    }

    public void showResultPage(String str) throws Exception
    {
        FXMLLoader FXML = new FXMLLoader(getClass().getResource("result.fxml"));
        Parent Root = FXML.load();
        ResultController Controller=FXML.getController();
        String s[]=str.split("&");
        for (String st:s){
            Controller.addResult(st);
        }
        stage.setTitle("Connector");
        stage.setScene(new Scene(Root));
        stage.show();

    }
    public  void setOpponentScore(String str)
    {
        String []s=str.split(" ");
        ImageView P2=player.get(s[1]);
        double dis=Double.parseDouble(s[2]);
        P2.setLayoutX(53 + dis);
    }
    public void setReady(String str)
    {
        Info.setText("Server will Send message within 5 seconds.So get ready.");
        String n[]=str.split(" ");
        for (int i=1;i<n.length;i++){
            System.out.println(n[i]);
            Label l=new Label(n[i]);
            if (n[i]==ClientName) l.setText("You");
            l.setLayoutX(5);
            ImageView imageView=imageViews.get(i-1);
            l.setLayoutY(imageView.getLayoutY()+10);
            Root.getChildren().add(l);
            player.put(n[i], imageView);
            imageView.setVisible(true);

        }

    }
    String ClientName;

    public void setClientName(String clientName) {
        ClientName = clientName;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    Socket socket;


    Long starting;
    public void setStr(String str) {
        this.str = str;
        Line.setText(str);
        Info.setText("Type the line below");
        starting=System.currentTimeMillis();

        //Info.setDisable(false);
    }

}
