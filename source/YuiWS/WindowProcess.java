package YuiWS;

import kernel.Process;
import kernel.ProcessMaster;

/**
 * Created by akichi on 16/03/14.
 */
public class WindowProcess extends Process {
    /*
    /////////////kernel.Process class field///////////////////////
    public int process_id;

    public byte process_type;

    abstract public boolean isLive(){}

    public int getProcessID(){
        return process_id;
    }
    public void setProcessID(int process_id){
        this.process_id = process_id;
    }

    abstract public void run();
     */

    Window window;

    public WindowProcess(Window wnd, int process_id){
        this.window = wnd;
        this.process_id = process_id;
        process_type = ProcessMaster.WINDOW_PROCESS;

    }

    public WindowProcess(Window wnd){
        this.window = wnd;
        process_type = ProcessMaster.WINDOW_PROCESS;

    }

    public boolean isLive(){
        return window.isLive();
    }

    public void kill(){
        window.kill();
    }

    public Window getWindow(){
        return window;
    }

    public void run(){

    }

}
