package application.Table;

import YuiWS.Window;
import YuiWS.WindowDesign.BasicAPI;
import YuiWS.WindowDesign.Label;
import YuiWS.WindowDesign.Point;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import kernel.Boot;
import java.util.ArrayList;

/**
 * Created by akichi on 16/03/18.
 */
public class Table extends Window {

    public static final int icon_size = 48;
    private ArrayList<Application> icons_list = new ArrayList<>();
    private int icons_count = 0;

    public Table(){
        y = Boot.display_height*0.93;
        height = 57;
    }

    public void drawWindow(){

        x = (Boot.display_width/2)-(icon_size*(icons_list.size()/2));
        width = icon_size*icons_list.size();

        Boot.gc.setFill(Color.INDIANRED);
        Boot.gc.fillRect(x, y, width, height);
        Boot.gc.setStroke(Color.BLACK);
        Boot.gc.setLineWidth(0.5);
        Boot.gc.strokeLine(x, y, x+width, y);
        Boot.gc.strokeLine(x, y, x, y+height);
        Boot.gc.strokeLine(x+width, y, x+width, y+height);
        Boot.gc.strokeLine(x, y+height, x+width, y+height);

        for(Application applicationIcon: icons_list){
            Boot.gc.drawImage(applicationIcon.getIcon(), x+icons_count, y+5);
            icons_count += icon_size;
        }

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

    public void addImage(Application... args){
        for(Application application: args) {
            icons_list.add(application);
        }
    }

    public void occ_mouse_clickedevent(double x, double y){
        if(y >= this.y) {
            execute_application(icons_list.get((int) (Math.floor((x - this.x) / 48))));
        }
    }

    public void occ_mouse_dragstart(double x, double y){
        if(this.x+60 <= x && this.x+width >= x && this.y <= y && this.y+20 >= y){
            drag = true;
            dragX = x;
            dragY = y;
        }
    }

    private void execute_application(Application application){
        application.app.run();
    }

    public void occ_mouse_release(double x, double y){
    }

    public void occ_keybord_typed(String ch){
    }

    public boolean amIdrag(){
        return false;
    }

    public void addIndent() {
    }
}

