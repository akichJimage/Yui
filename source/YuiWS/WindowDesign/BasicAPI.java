package YuiWS.WindowDesign;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import kernel.Boot;

/**
 * Created by akichi on 16/03/17.
 */
public class BasicAPI {

    public static void setLabel(Label label, Point point){
        Boot.gc.setFill(Color.GRAY);
        Boot.gc.setFont(label.getFontSize());
        Boot.gc.fillText(label.getText(), point.getX(), point.getY());
    }

    public static void setButton(Button button, Point begin, Point end){
        Boot.gc.setFont(button.getFont());
        Boot.gc.setFill(Color.WHITE);
        Boot.gc.fillRect(begin.getX(), begin.getY(), end.getX()-begin.getX(), end.getY()-begin.getY());
        Boot.gc.setLineWidth(0.5);
        Boot.gc.setStroke(Color.BLACK);
        Boot.gc.strokeLine(begin.getX(), begin.getY(), end.getX(), begin.getY());

        Boot.gc.strokeLine(begin.getX(), begin.getY()+(end.getY()-begin.getY()),
                end.getX(), begin.getY()+(end.getY()-begin.getY()));

        Boot.gc.strokeLine(begin.getX(), begin.getY(), begin.getX(), end.getY());
        Boot.gc.strokeLine(end.getX(), begin.getY(), end.getX(), end.getY());
        Boot.gc.setFill(Color.BLACK);
        Boot.gc.fillText(button.getStr(), begin.getX()+((end.getX()-begin.getX())/3.6),
                begin.getY()+((end.getY()-begin.getY())/1.6), end.getX()-begin.getX());
    }

    public static void write_log(String log){
        Boot.gc.setFill(Color.BLACK);
        Boot.gc.fillRect(Boot.display_width-150, Boot.display_height-20, 150, 50);
        Boot.gc.setFill(Color.WHITE);
        Boot.gc.setFont(new Font(20));
        Boot.gc.fillText(log, Boot.display_width-150, Boot.display_height);
    }
}
