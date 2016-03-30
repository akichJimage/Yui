package YuiWS;


import YuiWS.WindowDesign.BasicAPI;
import YuiWS.WindowDesign.Label;
import YuiWS.WindowDesign.Point;
import application.TableTennis.TableTennis;
import application.Terminal.Terminal;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import kernel.Boot;

/**
 * Created by akichi on 16/03/13.
 */
public class MouseTracker {

    public static final byte MOUSE_CLICKED = 0;
    public static final byte MOUSE_PRESSED = 1;
    public static final byte MOUSE_DRAGSTART = 2;
    public static final byte MOUSE_RELEASE = 3;

    static double x, x2, y, y2;
    static boolean second;

    private static boolean isMouse_Pressed;

    public static void Cliked(MouseEvent event){

        x = event.getSceneX();
        y = event.getSceneY();

        /*
        if(x > 900 && x < 952 && y > 950 && y < 1002){
            Boot.windowMasterProcess.getWindowMaster().createWindow("FIRST",
                    new WindowProcess(new Terminal(200.0, 200.0, 500.0, 485.0)));
            BasicAPI.setLabel(new Label("Command >", new Font(11)),
                    new Point(Boot.windowMasterProcess.getWindowMaster().window_list.get("FIRST").getWindow().getX(),
                            Boot.windowMasterProcess.getWindowMaster().window_list.get("FIRST").getWindow().getY()+35));
            return;
        }

        if(x > 960 && x < 1000 && y > 950 && y < 1002){
            Boot.windowMasterProcess.getWindowMaster().createWindow("tabletennis",
                    new WindowProcess(new TableTennis(200.0, 200.0, 900.0, 650.0)));
        }
        */



        Boot.windowMasterProcess.getWindowMaster().mouse_reception(x, y, MOUSE_CLICKED);
        Boot.guiMasterProcess.getGuiMaster().mouse_reception(x, y, MOUSE_CLICKED);
    }

    public static void Pressed(MouseEvent event){

        x = event.getSceneX();
        y = event.getSceneY();

        isMouse_Pressed = true;
        Boot.windowMasterProcess.getWindowMaster().mouse_reception(x, y, MOUSE_PRESSED);
    }

    public static void DragStart(MouseEvent event){
        x = event.getSceneX();
        y = event.getSceneY();

        Boot.windowMasterProcess.getWindowMaster().mouse_reception(x, y, MOUSE_DRAGSTART);
    }

    public static void MouseRelease(){
        second = false;
    }

    public static void MouseDragged(MouseEvent event){
        x2 = event.getSceneX();
        y2 = event.getSceneY();
        if(y2 < 26)
            return;
        if(!second){
            x = event.getSceneX();
            y = event.getSceneY();
            second = true;
        }

        Boot.windowMasterProcess.getWindowMaster().mouse_reception(x, y, MOUSE_DRAGSTART);
        Boot.windowMasterProcess.getWindowMaster().mouse_reception(x2, y2, MOUSE_RELEASE);

        x = x2;
        y = y2;
    }

    public static boolean getIsMouse_Pressed(){
        return isMouse_Pressed;
    }

    public static void setIsMouse_Pressed(boolean tf){
        isMouse_Pressed = tf;
    }


}
