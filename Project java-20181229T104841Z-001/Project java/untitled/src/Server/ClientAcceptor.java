package Server;

import javafx.application.Platform;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by MONJUR-E-RASUL on 12/12/2015.
 */
public class ClientAcceptor implements Runnable {
    boolean isRunning;
    ServerController serverController;
    ServerSocket serverSocket;
    Thread thread;
    ClientAcceptor(ServerController serverController)
    {
        this.serverController=serverController;
        try {
            System.out.println(InetAddress.getLocalHost().toString());
            serverSocket=new ServerSocket(44444);
        } catch (IOException e) {
            e.printStackTrace();
        }
        isRunning = true;
        serverController.setClientAcceptor(this);
        thread=new Thread(this);
        thread.start();
    }
    public  void  stop()
    {
        isRunning = false;
        thread.stop();
    }
    @Override
    public void run() {

        while(isRunning)
        {
            try {
                Socket socket = serverSocket.accept();
                DataInputStream din = new DataInputStream(socket.getInputStream());
                String name = din.readUTF();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        serverController.addClient(name , socket);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
