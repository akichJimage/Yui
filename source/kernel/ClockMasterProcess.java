package kernel;



/**
 * Created by akichi on 16/03/15.
 */
public class ClockMasterProcess extends MasterProcess {

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
    public boolean isLive(){
        return true;
    }

    @Override
    public void run(){

    }
    */
    /////////////////

    ClockMaster clockMaster;

    public ClockMasterProcess(String place){
        clockMaster = new ClockMaster(place);
        process_id = ProcessMaster.issue_pid();
        process_type = ProcessMaster.MASTER_PROCESS;
        ProcessMaster.addProcess(this);
        clockMaster.start();
    }

    public ClockMaster getClockMaster(){
        return clockMaster;
    }

    public boolean isLive(){
        return clockMaster.isLive();
    }

    public void kill(){
        clockMaster.kill();
    }


}
