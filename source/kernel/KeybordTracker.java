package kernel;


import javafx.scene.input.KeyCode;

/**
 * Created by akichi on 16/03/17.
 */
public class KeybordTracker {

    public static void keybord_typed(javafx.scene.input.KeyEvent keyEvent){

        ////////////////////////////////////////////

        if(keyEvent.getCode() == KeyCode.ENTER){
            Boot.windowMasterProcess.getWindowMaster().window_list.get("FIRST").getWindow().addIndent();
            return;
        }


        Boot.windowMasterProcess.getWindowMaster().keybord_reception(keyEvent.getText());


        //////////////////////////////////////////////////
    }
}
