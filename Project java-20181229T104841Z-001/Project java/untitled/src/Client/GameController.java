package Client;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.DataOutputStream;
import java.net.Socket;

/**
 * Created by MONJUR-E-RASUL on 12/12/2015.
 */
public class GameController {
    public Label Info;
    public TextField Input;
    public Label Line;
    String str;
    long starting;
    String ClientName;

    public void setClientName(String clientName) {
        ClientName = clientName;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    Socket socket;

    public void setStr(String str) {
        this.str = str;
        Line.setText(str);
        Info.setText("Type the line below");
        starting=System.currentTimeMillis();

        //Info.setDisable(false);
    }
    @FXML
    public void initialize()
    {
        Info.setText("Waiting for server");
        Line.setText("");
        //Input.setDisable(true);
        Input.textProperty().addListener((observable,oldValue,newValue)->{
            if(str.startsWith(newValue))
            {

                if(str.equals(newValue))
                {
                    long typingTime=System.currentTimeMillis()-starting;
                    double score=str.length()*500000000/typingTime;
                    WriteThread.Write(socket,ClientName+" "+String.valueOf(score));
                    Input.setDisable(true);
                    Info.setText("Your score is "+score+"wpm .Waiting for final result.");
                }
                else Info.setText("Go on typing.");
            }
            else
            {
                Info.setText("Wrong.Please correct it.");
            }
        });
    }
    public void setReady()
    {
        Info.setText("Server will Send message within 5 seconds.So get ready.");

    }
}
