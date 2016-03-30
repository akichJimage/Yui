package YuiWS;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import kernel.Boot;
import kernel.MasterProcess;
import kernel.ProcessMaster;

/**
 * Created by akichi on 16/03/14.
 */
public class GUIMaster extends MasterProcess{
    /*
MOUSE_SIGNAL

public static final byte MOUSE_CLICKED = 0; //when clicked

*/
    public static Color background_color;

    double r, g, b, a;
    int minute;
    boolean live;

    public GUIMaster(double r, double g, double b, double a){
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        live = true;
        background_color = new Color(0.7254901960784313, 0.3098039215686274, 0.3411764705882352, 1.0);
    }

    public void mouse_reception(double x, double y, byte type){
        if(Boot.display_width-30 < x && 30 > y){
            System.out.println(ProcessMaster.SHUTDOWN_ORDER);
            ProcessMaster.ShutdownOrder();
        }
    }

    private void makeDefault(){
        Boot.gc.setFill(Color.BLACK);
        Boot.gc.fillRect(0, 0, Boot.display_width, 25);

        Boot.gc.setFill(Color.WHITE);
        minute = Boot.clockMasterProcess.getClockMaster().getMinute();
        Boot.gc.setFont(new Font(15));
        Boot.gc.fillText(Boot.clockMasterProcess.getClockMaster().getHour() + ":" + String.format("%1$02d", minute),
                Boot.display_width/2, 18);
        Boot.gc.fillText("Yui", 2, 18);
        Boot.gc.setFill(Color.WHITE);
        Boot.gc.fillOval(Boot.display_width-20, 7.5, 15, 15);
        Boot.gc.setFill(Color.BLACK);
        Boot.gc.fillOval(Boot.display_width-17.5, 9.9, 10, 10);
        Boot.gc.setFill(Color.WHITE);
        Boot.gc.fillRect(Boot.display_width-13.7, 5.2, 2.3, 9.5);
    }

    private void drawPoint(double x1, double y1, Color col){
        Boot.gc.setFill(col);
        Boot.gc.fillRect(x1, y1, 1, 1);
    }

    public boolean isLive(){
        return live;
    }

    public void kill(){
        live = false;
    }


    public void run(){
        try{
            this.sleep(250);
        }catch (InterruptedException e){
            System.out.println(e);
        }
        makeDefault();
        mainloop:while(true){
            if(ProcessMaster.getShutdownOrderState()){
                kill();
                break mainloop;
            }
            try{
                this.sleep(1000);
            }catch (InterruptedException e){
                System.out.println(e);
            }

            if(minute == Boot.clockMasterProcess.getClockMaster().getMinute()) {
                continue;
            }else {
                minute = Boot.clockMasterProcess.getClockMaster().getMinute();
                Boot.gc.setFill(Color.BLACK);
                Boot.gc.fillRect((Boot.display_width/2) - 30, 0, 200, 25);
                Boot.gc.setFill(Color.WHITE);
                Boot.gc.setFont(new Font(15));
                Boot.gc.fillText(Boot.clockMasterProcess.getClockMaster().getHour() + ":" + String.format("%1$02d", minute),
                        Boot.display_width / 2, 18);
            }
        }
    }
}
