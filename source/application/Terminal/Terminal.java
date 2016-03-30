
package application.Terminal;


import YuiWS.Window;
import YuiWS.WindowDesign.BasicAPI;
import YuiWS.WindowDesign.Label;
import YuiWS.WindowDesign.Point;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import kernel.Boot;
import kernel.ProcessMaster;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by akichi on 16/03/17.
 */
public class Terminal extends Window {

    public static final byte FONT_SIZE = 11;

    private short typed = 0;
    private short indent = 0;
    private StringBuilder sb;
    private StringBuilder sb2;
    private String input;
    private ArrayList<String> history;
    private ArrayList<String> answer;
    private String current;

    public Terminal(double x, double y, double width, double height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        drawOrder = new ArrayList<>();
        sb = new StringBuilder();
        sb2 = new StringBuilder();
        history = new ArrayList<>();
        answer = new ArrayList<>();
        current = System.getProperty("user.dir");
    }

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

        BasicAPI.setLabel(new Label("Command >", new Font(11)), new Point(x, y+35));

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


        indent = 0;
        Boot.gc.setFill(Color.WHITE);
        Boot.gc.fillRect(x, y+25, width, height-25);
        ////////////////////////////////////////

        for(int i = 0;i < history.size();i++){
            BasicAPI.setLabel(new Label(history.get(i), new Font(FONT_SIZE)), new Point(getX(), getY()+35+indent));
            indent+=14;
            BasicAPI.setLabel(new Label(answer.get(i), new Font(FONT_SIZE)), new Point(getX(), getY()+35+indent));
            if(!(answer.get(i).equals(""))) {
                indent += 14;
            }
        }


        typed = 0;
        BasicAPI.setLabel(new Label("Command >", new Font(FONT_SIZE)), new Point(getX(), getY()+35+indent));
    }

    public void occ_keybord_typed(String ch){
        System.out.println(ch);
        sb.append(ch);
        BasicAPI.setLabel(new Label(ch, new Font(FONT_SIZE)), new Point(x+62+typed, y+35+indent));
        typed += 6;
    }

    public void addIndent(){
        input = sb.toString();

        if(input.isEmpty()){
            typed = 0;
            indent += 14;
            BasicAPI.setLabel(new Label("Command >", new Font(FONT_SIZE)), new Point(getX(), getY()+35+indent));
            history.add("Command >");
            answer.add("");
            //System.out.println(indent + ":" + height);
            if(!(indent > height-60)) {
                System.out.println("return");
                return;
            }
            if(history.size()*14 > height-60){
                history.remove(0);
                answer.remove(0);
            }
        }

        if(indent > height-60){
            Boot.gc.setFill(Color.WHITE);
            Boot.gc.fillRect(this.x, y+indent+22, this.width, 9);
            typed = 0;
        }else {
            typed = 0;
            indent += 14;
            command(input);
            if(history.size()*28 > height-60){
                history.remove(0);
                answer.remove(0);
            }
            indent += 14;
        }
        BasicAPI.setLabel(new Label("Command >", new Font(FONT_SIZE)), new Point(getX(), getY()+35+indent));
        input = null;
        sb.delete(0, sb.length());

        ////////////////////////////////////////
        indent = 0;
        Boot.gc.setFill(Color.WHITE);
        Boot.gc.fillRect(x, y+25, width, height-25);
        ////////////////////////////////////////

        for(int i = 0;i < history.size();i++){
            BasicAPI.setLabel(new Label(history.get(i), new Font(FONT_SIZE)), new Point(getX(), getY()+35+indent));
            indent+=14;
            BasicAPI.setLabel(new Label(answer.get(i), new Font(FONT_SIZE)), new Point(getX(), getY()+35+indent));
            if(!(answer.get(i).equals(""))) {
                indent += 14;
            }
        }


        typed = 0;
        BasicAPI.setLabel(new Label("Command >", new Font(FONT_SIZE)), new Point(getX(), getY()+35+indent));

    }

    public void command(String cmd){
        history.add(("Command >"+cmd));
        Scanner sc = new Scanner(cmd);
        try {
            switch (sc.next()) {
                case "echo":
                    String str = sc.nextLine();
                    answer.add(str);
                    BasicAPI.setLabel(new Label(str, new Font(FONT_SIZE)), new Point(getX(), getY() + 35 + indent));
                    break;
                case "exit":
                    eraseWindow();
                    kill();
                    break;
                case "ls":
                    BasicAPI.setLabel(new Label(Boot.fileSystem.ls(), new Font(FONT_SIZE)), new Point(getX(), getY() + 35 + indent));
                    answer.add(Boot.fileSystem.ls());
                    sb2.delete(0, sb2.length());
                    break;
                case "pwd":
                    if(Boot.fileSystem.getItelator().equals("")){
                        BasicAPI.setLabel(new Label("/", new Font(FONT_SIZE)), new Point(getX(), getY() + 35 + indent));
                        answer.add("/");
                    }else {
                        BasicAPI.setLabel(new Label(Boot.fileSystem.getItelator(), new Font(FONT_SIZE)), new Point(getX(), getY() + 35 + indent));
                        answer.add(Boot.fileSystem.getItelator());
                    }
                    break;
                case "cd":
                    if(!Boot.fileSystem.cd(sc.next())){
                        answer.add("No such file or directory");
                        BasicAPI.setLabel(new Label("No such file or directory", new Font(FONT_SIZE)), new Point(getX(), getY() + 35 + indent));
                    }else {
                        answer.add("");
                    }
                    break;
                case "shutdown":
                    ProcessMaster.ShutdownOrder();
                default:
                    answer.add("Command not found");
                    BasicAPI.setLabel(new Label("Command not found", new Font(FONT_SIZE)), new Point(getX(), getY() + 35 + indent));
            }
        }catch (Exception e){
            answer.add("Command not found(error)");
            BasicAPI.setLabel(new Label("Command not found", new Font(FONT_SIZE)), new Point(getX(), getY() + 35 + indent));
        }
    }

}
