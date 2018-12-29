package Server;

import com.sun.scenario.effect.Effect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


/**
 * Created by MONJUR-E-RASUL on 12/12/2015.
 */
public class ServerController {
    List<String> strings = new ArrayList<>();
    List<Socket> clientSocket = new ArrayList<>();
    public void setClientAcceptor(ClientAcceptor clientAcceptor) {
        this.clientAcceptor = clientAcceptor;
    }
    HashMap<String,Socket> hashMap = new HashMap<>();
    ClientAcceptor clientAcceptor;
    public Button Start;
    public ListView ClientList;
    public ListView Result;
    @FXML
    public void initialize(){
        Start.setDisable(true);

    }
    public void addScore(String string)
    {
        strings.add(string);
        Result.getItems().add(string);

        if(strings.size()== ClientList.getItems().size())
        {
            String str="";
            for(String s : strings)
            {
                str+=s+"&";
            }
            for(Socket socket : clientSocket){
                WriteThread.Write(socket , "Result#"+str);
            }
        }
    }
    String []lines ={
            "He is not the hero Gotham needs, but the hero Gotham deserves." ,
            "If you are good at something, never do it for free." ,
            "With great power, comes great responsibilty."
    };
    @FXML
    public  void buttonClick(ActionEvent event)
    {
        Start.setDisable(true);
     clientAcceptor.stop();
        Random random=new Random();
        int c =random.nextInt(lines.length-1);
        String str = lines[c];
        String nameList="";
        for(int i=0;i<ClientList.getItems().size();i++){
            String s=(String)ClientList.getItems().get(i);
            nameList+=s+" ";
        }

        for(Socket socket : clientSocket){

            WriteThread.Write(socket , "READY "+nameList);
        }
        Thread t = new Thread();
        try {
            t.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(Socket socket : clientSocket){
            WriteThread.Write(socket ,"Line#"+ str);
        }


    }
    public void addClient(String name , Socket socket){
    ClientList.getItems().add(name);
        clientSocket.add(socket);
        new ReadThread(socket,this,name);
        hashMap.put(name,socket);
        if(ClientList.getItems().size() >=  2)
            Start.setDisable(false);
    }
    public  void addForward(String string, Socket socket)
    {
        for(Socket  s : clientSocket)
        {
            if(s==socket) continue;
            else
            {
                WriteThread.Write(s,string);
            }
        }
    }

}