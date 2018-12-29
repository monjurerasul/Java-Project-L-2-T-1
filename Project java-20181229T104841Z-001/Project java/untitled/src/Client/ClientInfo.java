package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by User on 12/7/2015.
 */
public class ClientInfo{
//    HashMap<String,Integer> hashMap=new HashMap<>();
    Socket socket;
    //Thread thread;
    String clientName;
    Integer score;

    ClientInfo(Socket socket)
    {
        this.socket=socket;
        //thread = new Thread(this);
        try {
            DataOutputStream inputStream = new DataOutputStream(socket.getOutputStream());
            String string = clientName + " " + String.valueOf(score);
            inputStream.writeUTF(string) ;
        } catch ( IOException e) {
            e.printStackTrace();
        }
        //hashMap.put(clientName,score);

    }


}
