package Client;

import javafx.scene.control.ListView;

/**
 * Created by MONJUR-E-RASUL on 12/12/2015.
 */
public class ResultController {
    public ListView Result;

    public void addResult(String str)
    {
        Result.getItems().add(str);

    }
}
