package YuiWS.WindowDesign;

import javafx.scene.text.Font;

/**
 * Created by akichi on 16/03/18.
 */
public class Button {

    private String str;
    private Font font;

    public Button(String str, Font font){
        this.str = str;
        this.font = font;
    }

    public String getStr(){
        return str;
    }

    public void setStr(String str){
        this.str = str;
    }

    public Font getFont(){
      return font;
    }

}
