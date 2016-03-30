package YuiWS;

import kernel.MasterProcess;
import kernel.ProcessMaster;

/**
 * Created by akichi on 16/03/15.
 */
public class WindowMasterProcess extends MasterProcess{
    /////////////kernel.MasterProcess calls field//////////////////
    /*
    /////////////kernel.Process class field///////////////////////
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
    public boolean isLive(){}

    public void kill(){}

    @Override
    public void run(){

    }
    */
    ///////////////////////////////////////////////////////////////////////


    private WindowMaster windowMaster;

    public WindowMasterProcess(){
        windowMaster = new WindowMaster();
        process_id = ProcessMaster.issue_pid();
        process_type = ProcessMaster.MASTER_PROCESS;
        ProcessMaster.addProcess(this);
        windowMaster.start();
    }

    public WindowMaster getWindowMaster(){
        return windowMaster;
    }

    public boolean isLive(){
        return windowMaster.isLive();
    }

    public void kill(){
        windowMaster.kill();
    }

}
