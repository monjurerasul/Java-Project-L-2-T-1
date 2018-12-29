package Client;

import javafx.application.Platform;

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
    //GameController gameController;
    ResultController resultController;
    ClientMain clientMain;
//    private  MessageFromServer message;

    public ReadThread(Socket cn,ClientMain clientMain) {
        this.cn = cn;
        //this.gameController=gameController;
        this.clientMain=clientMain;
        try {
            din=new DataInputStream(cn.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.thr = new Thread(this);
        thr.start();
    }

    public void setResultController(ResultController resultController) {
        this.resultController = resultController;
    }

    @Override
    public void run()
    {
        while (true)
        {
            try {
                String str=din.readUTF();
                if (str.startsWith("READY")) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println(str);
                            clientMain.setReady(str);
                            //gameController.setReady();
                        }
                    });

                }

                if(str.startsWith("Line"))
                {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            clientMain.setStr(str.split("#")[1]);
                            //gameController.setStr(str.split("#")[1]);

                        }
                    });


                }
                if(str.startsWith("score"))
                {
                    clientMain.setOpponentScore(str);
                }
                if(str.startsWith("Result"))
                {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                clientMain.showResultPage(str.split("#")[1]);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    });


                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


}
