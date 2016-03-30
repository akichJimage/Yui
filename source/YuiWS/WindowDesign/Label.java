package YuiWS.WindowDesign;

import javafx.scene.text.Font;

/**
 * Created by akichi on 16/03/17.
 */
public class Label {
    String text;
    Font FontSize;

    public Label(String label, Font FontSize){
        this.text = label;
        this.FontSize = FontSize;
    }

    public String getText(){
        return text;
    }

    public Font getFontSize(){
        return FontSize;
    }
}
