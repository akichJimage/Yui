package YuiWS;

import javafx.scene.paint.Color;
import kernel.MasterProcess;
import kernel.ProcessMaster;

/**
 * Created by akichi on 16/03/15.
 */
public class GUIMasterProcess extends MasterProcess{
    /*
    /////////////kernel.MasteerProcess class field///////////////////////
    public int process_id;

    abstract public boolean isLive(){}

    public int getProcessID(){
        return process_id;
    }
    public void setProcessID(int process_id){
        this.process_id = process_id;
    }

    abstract public void run();
     */

    /*
    //MUST OVERRIDE!!
    public boolean isLive(){
        return true;
    }

    public void kill(){}

    @Override
    public void run(){

    }
    */

    private GUIMaster guiMaster;

    public GUIMasterProcess(Color col){
        guiMaster = new GUIMaster(col.getRed(), col.getGreen(), col.getBlue(), 1.0);
        process_id = ProcessMaster.issue_pid();
        process_type = ProcessMaster.MASTER_PROCESS;
        ProcessMaster.addProcess(this);
        guiMaster.start();
    }

    public GUIMaster getGuiMaster(){
        return guiMaster;
    }

    public boolean isLive(){
        return guiMaster.isLive();
    }

    public void kill(){
        guiMaster.kill();
    }

}
