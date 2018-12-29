package Client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by User on 12/7/2015.
 */
public class WriteThread {
    public static void Write(Socket socket, String string)
    {
        try {
            DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
            dout.writeUTF(string);
            dout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
