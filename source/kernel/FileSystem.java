package kernel;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by akichi on 16/03/23.
 */
public class FileSystem extends Process {
    /*
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
     */
    private Directry root;

    private boolean live;
    private String itelator;
    private StringBuilder sb;
    private Directry workspace;
    private String itelatorBackUp;
    private String lsString;

    public FileSystem(){
        process_id = ProcessMaster.issue_pid();
        process_type = ProcessMaster.NONMASTER_PROCESS;
        ProcessMaster.addProcess(this);
        live = true;
        root = new Directry("/");
        workspace = root;
        root.addDirectory("sys");
        sb = new StringBuilder();
        itelator = "";
        linkItelator();
    }


    public boolean isLive(){
        return live;
    }

    public void kill(){
        live = false;
    }

    public String getItelator(){
        return itelator;
    }

    public boolean cd(String directory){
        for(Directry dir :root.getTree()){
            if(dir.getDirname().equals(directory)) {
                itelator = sb.append(itelator+"/").append(directory).toString();
                linkItelator();
                sb.delete(0, sb.length());
                return true;
            }
        }
        sb.delete(0, sb.length());
        return false;
    }

    public String ls(){
        /*
        最初に"/"がでるまでまち、"/"が出たらそれ以前の文字列を切り出す。
        その文字列でツリーの中を潜っていく
        これをループ
         */


        if(itelator.contains("/")) {
            itelator = itelator.substring(itelator.indexOf('/')+1);
            for(Directry dir: workspace.getTree()) {
                if (dir.getDirname().equals(itelator)) {
                    System.out.println("contains"+dir.getDirname());
                    workspace = dir;
                }
            }
            return ls();
        }else{
            for(Directry dir: workspace.getTree()) {
                System.out.println("aaaaaaaaaaaaaaaaaa");
                sb.append(dir.getDirname());
            }
            lsString = sb.toString();
            sb.delete(0, sb.length());
            System.out.println(lsString + "lala");
            return lsString;
        }
    }

    public void linkItelator(){
        itelatorBackUp = itelator;
    }

    public void run(){
        while(true){
            try{
                Thread.sleep(500);
            }catch (InterruptedException e){
                System.out.println(e);
            }
            if(ProcessMaster.SHUTDOWN_ORDER){
                kill();
                break;
            }
        }
    }
}

class Directry{
    private String dirname;
    private LinkedList<Directry> tree;
    private Iterator<Directry> it;

    public Directry(String dirname){
        this.dirname = dirname;
        tree = new LinkedList<>();
        it = tree.iterator();
    }

    public void addDirectory(String dirname){
        tree.add(new Directry(dirname));
    }


    public LinkedList<Directry> getTree(){
        return tree;
    }

    public String getDirname(){
        return dirname;
    }

    public void setDirname(String dirname){
        this.dirname = dirname;
    }

    public Iterator<Directry> getIterator(){
        return it;
    }
}