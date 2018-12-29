package Client;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by User on 12/7/2015.
 */
public class Group implements Runnable{

    HashMap<String,Integer> hashMap=new HashMap<>();
    ClientInfo clientInfo;
    Thread thread;

    public Group(ClientInfo clientInfo)
    {
        this.clientInfo=clientInfo;
        //thread = new Thread(this);
        hashMap.put(clientInfo.clientName,clientInfo.score);
    }



    @Override
    public void run() {


    }
}
