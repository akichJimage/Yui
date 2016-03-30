package YuiWS;

import kernel.MasterProcess;
import kernel.ProcessMaster;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by akichi on 16/03/13.
 */

/*
MOUSE_SIGNAL

public static final byte MOUSE_CLICKED = 0; //when clicked

*/

public class WindowMaster extends MasterProcess{

    public Map<String, WindowProcess> window_list;
    public ArrayList<WindowProcess> window_array;
    private boolean live;
    private int window_array_size;


    public WindowMaster(){
        window_list = new HashMap<>();
        window_array = new ArrayList<>();
        live = true;
    }

    public boolean isLive(){
        return live;
    }

    public void kill(){
        this.live = false;
    }

    /*
    public void createWindow(String windowname, int x, int y, int width, int height){
        WindowProcess windowProcess =  new WindowProcess(new Window(x ,y, width, height),
                                            ProcessMaster.issue_pid());
        window_list.put(windowname, windowProcess);
        window_array.add(windowProcess);
        windowProcess.getWindow().drawWindow();
        ProcessMaster.addProcess(windowProcess);
        windowProcess.getWindow().start();
    }
    */

    public void createWindow(String windowname, WindowProcess windowProcess){
        windowProcess.setProcessID(ProcessMaster.issue_pid());
        windowProcess.setProcess_type(ProcessMaster.WINDOW_PROCESS);
        window_list.put(windowname, windowProcess);
        window_array.add(windowProcess);
        windowProcess.getWindow().drawWindow();
        ProcessMaster.addProcess(windowProcess);
        windowProcess.getWindow().start();
    }


    public void mouse_reception(double x, double y, byte type){
        window_array_size = window_array.size();
        swt: switch(type){
            case 0:
                for(int n = 0;n < window_array_size;n++){
                    /*

                    WHY use for(int n = 0;n < window_array_size;n++){~~}??

                    BECAUSE=>

                    Table ->{
                        clicked => add window_array => window_array.size() <= changed
                        SO
                        java.util.ConcurrentModificationException
                    }
                     */

                    window_array.get(n).getWindow().occ_mouse_clickedevent(x, y);
                }
                break;
            case 1:
                for(WindowProcess windowProcess: window_array){

                    if(!MouseTracker.getIsMouse_Pressed()) break swt;
                    //windowProcess.getWindow().occ_mouse_pressedevent(x, y);   WARNING
                }
                break;
            case 2:
                for(WindowProcess windowProcess: window_array){
                    windowProcess.getWindow().occ_mouse_dragstart(x, y);
                }
                break;
            case 3:
                for(WindowProcess windowProcess: window_array){
                    windowProcess.getWindow().occ_mouse_release(x, y);
                }
            default:
                break;
        }
    }

    public void keybord_reception(String ch){
        for(WindowProcess windowProcess: window_array){
            windowProcess.getWindow().occ_keybord_typed(ch);
        }

    }

    public void run(){
        while(true){
            if(ProcessMaster.getShutdownOrderState()){
                kill();
                break;
            }
        }
    }
}

