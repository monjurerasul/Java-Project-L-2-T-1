package Client;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by MONJUR-E-RASUL on 12/12/2015.
 */
public class ConnectionController {
    public TextField nameField;
    public javafx.scene.control.Button Button;
    public ClientMain clientMain;

    public void setClientMain(ClientMain clientMain) {
        this.clientMain = clientMain;
    }

    public void setNameField(TextField nameField) {

        this.nameField = nameField;
    }

    public void onButtonClick(ActionEvent actionEvent) {
        String name=nameField.getText();
        try {
            Socket socket=new Socket("127.0.0.1",44444);
            DataOutputStream dout=new DataOutputStream(socket.getOutputStream());
            dout.writeUTF(nameField.getText());
            dout.flush();
            try {
                clientMain.showGamePage(socket,name);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
