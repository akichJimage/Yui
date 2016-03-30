package YuiWS;

import YuiWS.WindowDesign.BasicAPI;
import YuiWS.WindowDesign.Label;
import YuiWS.WindowDesign.Point;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import kernel.Boot;
import kernel.ProcessMaster;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Deque;


/**
 * Created by akichi on 16/03/15.
 */
abstract public class Window extends Thread{
    public double x, y, width, height, dragX, dragY;
    public boolean live = true, drag = false;
    public ArrayList<Deque<Object>> drawOrder;
    public Deque<Object> FunctionInfo;
    private short typed = 0;
    private short indent = 0;

    /*
    public Window(double x, double y, double width, double height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        drawOrder = new ArrayList<>();
    }

    */

    public void drawWindow(){
        Boot.gc.setFill(Color.TOMATO);
        Boot.gc.fillRect(x, y, 20, 20);
        Boot.gc.setFill(Color.YELLOWGREEN);
        Boot.gc.fillRect(x+20, y, 20, 20);
        Boot.gc.setFill(Color.AQUAMARINE);
        Boot.gc.fillRect(x+40, y, 20, 20);
        Boot.gc.setFill(Color.DARKGRAY);
        Boot.gc.fillRect(x+60, y, width-60, 20);
        Boot.gc.setFill(Color.WHITE);
        Boot.gc.fillRect(this.x, this.y+20, width, height-20);
        Boot.gc.setStroke(Color.BLACK);
        Boot.gc.setLineWidth(0.5);
        Boot.gc.strokeLine(x, y, x, y+height);
        Boot.gc.strokeLine(x+width, y, x+width, y+height);
        Boot.gc.strokeLine(x, y+height, x+width, y+height);


    }

    public void RedrawWindow(double x, double y){
        Boot.gc.setFill(Color.TOMATO);
        Boot.gc.fillRect(x, y, 20, 20);
        Boot.gc.setFill(Color.YELLOWGREEN);
        Boot.gc.fillRect(x+20, y, 20, 20);
        Boot.gc.setFill(Color.AQUAMARINE);
        Boot.gc.fillRect(x+40, y, 20, 20);
        Boot.gc.setFill(Color.DARKGRAY);
        Boot.gc.fillRect(x+60, y, width-60, 20);
        Boot.gc.setFill(Color.WHITE);
        Boot.gc.fillRect(x, y+20, width, height-20);
        Boot.gc.setStroke(Color.BLACK);
        Boot.gc.setLineWidth(0.5);
        Boot.gc.strokeLine(x, y, x, y+height);
        Boot.gc.strokeLine(x+width, y, x+width, y+height);
        Boot.gc.strokeLine(x, y+height, x+width, y+height);
        BasicAPI.setLabel(new Label("Redraw", new Font(20)), new Point(x+100, y+100));
    }

    public void eraseWindow(){
        Boot.gc.setFill(GUIMaster.background_color);
        Boot.gc.fillRect(x-7, y, width+7, height+7);
        Boot.gc.setStroke(GUIMaster.background_color);
        for(int n = 0;n < 8;n++) {
            Boot.gc.setLineWidth(1);
            Boot.gc.strokeLine(x, y, x, y + height);
            Boot.gc.strokeLine(x + width, y, x + width, y + height);
            Boot.gc.strokeLine(x, y + height, x + width, y + height);
        }
    }


    public void occ_mouse_clickedevent(double x, double y){
        if(this.x <= x && this.x+20 >= x && this.y <= y && this.y+20 >= y){
            eraseWindow();
            kill();
        }
    }

    public void occ_mouse_dragstart(double x, double y){
        if(this.x+60 <= x && this.x+width >= x && this.y <= y && this.y+20 >= y){
            drag = true;
            dragX = x;
            dragY = y;
        }
    }

    public void occ_mouse_release(double x, double y){
        if(amIdrag()) {
            eraseWindow();
            RedrawWindow(x-(dragX-this.x), y);

            this.x = x-(dragX-this.x);
            this.y = y;

            drag = false;
        }
    }

    public void occ_keybord_typed(String ch){
        BasicAPI.setLabel(new Label(ch, new Font(20)), new Point(x+100+typed, y+35+indent));
        typed += 14;
    }

    public boolean amIdrag(){
        return drag;
    }

    public boolean isLive(){
        return live;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public double getWidth(){
        return width;
    }

    public double getHeight(){
        return height;
    }

    public short getIndent(){
        return indent;
    }

    public void addIndent(){
        typed = 0;
        indent += 18;
        BasicAPI.setLabel(new Label("akichOS >", new Font(20)), new Point(getX(), getY()+35+indent));
    }

    public void kill(){
        live = false;
    }

    public void run(){
        mainloop:while(true) {
            if (ProcessMaster.getShutdownOrderState()){
                kill();
                break;
            }
        }
    }
}
