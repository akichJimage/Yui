package kernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by akichi on 16/03/13.
 */
public class ProcessMaster extends Thread{

    public static byte MASTER_PROCESS = 0;
    public static byte WINDOW_PROCESS = 1;
    public static byte NONMASTER_PROCESS = 2;

    public static boolean WAIT_ADD = false;
    public static boolean ADDING = false;

    private static ArrayList<Integer> kill_list = new ArrayList<>();
    private static ArrayList<Integer> UsedPID_list = new ArrayList<>();
    private static Map<Integer, Process> process_list = new HashMap<>();
    static int IDs = 0;

    public static boolean SHUTDOWN_ORDER = false;

    public ProcessMaster() {
    }

    synchronized public static int issue_pid(){
        return ++IDs;
    }

    public static void kill_process(int process_id){
        if(process_list.containsKey(process_id)){
            process_list.remove(process_id);
        }
        if(UsedPID_list.contains(process_id)){
            UsedPID_list.remove(UsedPID_list.indexOf(process_id));
        }
        System.out.println(process_id + "killed");
    }

    synchronized public static void addProcess(Process process){
        while(true) {
            if (WAIT_ADD) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }else{
                break;
            }
        }

        ADDING = true;
        process_list.put(process.getProcessID(), process);
        UsedPID_list.add(process.getProcessID());
        System.out.println(process.getProcessID() + ":" + process.getProcess_type());
        ADDING = false;
    }

    synchronized public static boolean getShutdownOrderState(){
        return SHUTDOWN_ORDER;
    }

    public static void ShutdownOrder(){
        SHUTDOWN_ORDER = true;
    }


    public void run(){
        mainloop:while(true){
            while(true) {
                try {
                    WAIT_ADD = false;
                    this.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
                if(!ADDING) break;
            }
            WAIT_ADD = true;
            kill_list.clear();
            if(!process_list.isEmpty()){
                for(int i = 0;i < process_list.size();i++){
                    if(!(process_list.get(UsedPID_list.get(i)).isLive())){
                        kill_list.add(process_list.get(UsedPID_list.get(i)).getProcessID());
                    }
                }
            }
            for(Integer i: kill_list){
                kill_process(i);
            }
            if(process_list.isEmpty() && SHUTDOWN_ORDER){
                break mainloop;
            }
        }
        Boot.shutdown();
    }
}
