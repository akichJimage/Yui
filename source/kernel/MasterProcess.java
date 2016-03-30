package kernel;

/**
 * Created by akichi on 16/03/14.
 */
public class MasterProcess extends Process {
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

    //MUST OVERRIDE!!
    public boolean isLive(){
        return true;
    }

    public void kill(){}

    @Override
    public void run(){

    }

}
