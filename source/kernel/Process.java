package kernel;


/**
 * Created by akichi on 16/03/14.
 */
abstract public class Process extends Thread{

    public int process_id;

    public byte process_type;

    public int getProcessID(){
        return process_id;
    }

    public void setProcessID(int process_id){
        this.process_id = process_id;
    }

    public byte getProcess_type(){
        return process_type;
    }

    public void setProcess_type(byte process_type){ this.process_type = process_type; }

    abstract public boolean isLive();

    abstract public void kill();

    abstract public void run();

}
