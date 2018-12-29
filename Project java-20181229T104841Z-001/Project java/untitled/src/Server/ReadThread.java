package Server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by User on 12/7/2015.
 */
public class ReadThread implements Runnable {
    private Thread thr;
    private Socket cn;
    DataInputStream din;
    String name;
    Socket socket;
    ServerController serverController;
//    private  MessageFromServer message;

    public ReadThread(Socket cn , ServerController serverController , String name) {
        this.cn = cn;
        socket=cn;
        this.serverController = serverController;
        this.name = name;
        try {
            din=new DataInputStream(cn.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.thr = new Thread(this);

        thr.start();
    }
    @Override
    public void run()
    {
        while (true){
        try {
            String string = din.readUTF();
            if(string.startsWith("score")){
                serverController.addForward(string,socket);


            }
            else serverController.addScore(string);
        } catch (IOException e) {
            e.printStackTrace();
        }}
    }


}
