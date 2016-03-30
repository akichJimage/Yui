package application.invader;

import YuiWS.Window;
import YuiWS.WindowDesign.BasicAPI;
import YuiWS.WindowDesign.Button;
import YuiWS.WindowDesign.Label;
import YuiWS.WindowDesign.Point;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import kernel.Boot;
import kernel.ProcessMaster;
import java.nio.file.Paths;

/**
 * Created by ぴろ on 2016/03/27.
 */

public class Invader extends Window {

    private final int shooter_oneblock = 16;
    private final int shooter_width = shooter_oneblock*3;
    private final int shooter_height = shooter_oneblock*2;
    private final int enemy_space = 15;
    private final int bullet_width = 5;
    private final int bullet_height = 20;
    private final int bullet_speed = 2;
    private Enemy[][] enemies = new Enemy[5][3];
    Image enemy_image = new Image(Paths.get("root/sys/enemy.png").toUri().toString());
    private static Bullet b1, b2, b3;
    private int bullet_counter = 0;
    private Shooter shooter;
    private boolean start, a, d;

    public Invader(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        a = d = false;

        start = false;
        shooter = new Shooter(x + (width / 2), y + height - 100);
    }

    public void drawWindow() {
        Boot.gc.setFill(Color.TOMATO);
        Boot.gc.fillRect(x, y, 20, 20);
        Boot.gc.setFill(Color.YELLOWGREEN);
        Boot.gc.fillRect(x + 20, y, 20, 20);
        Boot.gc.setFill(Color.AQUAMARINE);
        Boot.gc.fillRect(x + 40, y, 20, 20);
        Boot.gc.setFill(Color.DARKGRAY);
        Boot.gc.fillRect(x + 60, y, width - 60, 20);
        Boot.gc.setFill(Color.BLACK);
        Boot.gc.fillRect(this.x, this.y + 20, width, height - 20);
        Boot.gc.setStroke(Color.BLACK);
        Boot.gc.setLineWidth(0.5);
        Boot.gc.strokeLine(x, y, x, y + height);
        Boot.gc.strokeLine(x + width, y, x + width, y + height);
        Boot.gc.strokeLine(x, y + height, x + width, y + height);

        BasicAPI.setLabel(new Label("Invader Game", new Font(70)), new Point(x + 60, y + 160));
        BasicAPI.setButton(new Button("START", new Font(14)), new Point(x + 215, y + 365), new Point(x + 285, y + 390));
        BasicAPI.setButton(new Button(" EXIT", new Font(14)), new Point(x + 215, y + 400), new Point(x + 285, y + 425));

    }


    public void occ_mouse_clickedevent(double x, double y) {
        if (this.x <= x && this.x + 20 >= x && this.y <= y && this.y + 20 >= y) {
            eraseWindow();
            kill();
        } else if (x >= this.x + 215 && x <= this.x + 285 && y >= this.y + 365 && y <= this.y + 390) {
            /////////////////////START BUTTON PUSHED
            start = true;
            reset();
        } else if (x >= this.x + 215 && x <= this.x + 285 && y >= this.y + 400 && y <= this.y + 425) {
            /////////////////////CANCEL BUTTON PUSHED
            eraseWindow();
            kill();
        }
    }

    Timeline bullet_master = new Timeline(new KeyFrame(Duration.millis(16),
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    drawed_bullet();
                    bullet_enemy();

                    BasicAPI.write_log(String.valueOf(b1.getX()) + ":" + String.valueOf(b1.getY()));
                    if(b1 != null) {
                        if (b1.getY() <= y + 20) {
                            Boot.gc.setFill(Color.BLACK);
                            Boot.gc.fillRect(b1.getX(), b1.getY(),
                                    bullet_width, bullet_height);
                            bullet_counter--;
                            b1 = null;
                        }
                    }else

                    if(b2 != null) {
                        if (b2.getY() <= y + 20) {
                            Boot.gc.setFill(Color.BLACK);
                            Boot.gc.fillRect(b2.getX(), b2.getY(),
                                    bullet_width, bullet_height);
                            bullet_counter--;
                            b2 = null;
                        }
                    }else

                    if(b3 != null) {
                        if (b3.getY() <= y + 20) {
                            Boot.gc.setFill(Color.BLACK);
                            Boot.gc.fillRect(b3.getX(), b3.getY(),
                                    bullet_width, bullet_height);
                            bullet_counter--;
                            b3 = null;
                        }
                    }

                }
            }));

    public void reset(){
        Boot.gc.setFill(Color.BLACK);
        Boot.gc.fillRect(x, y+20, width, height-20);
        draw_shooter(shooter.getX(), shooter.getY());
        first_draw_enemy(x+(width/2.5), y+100);
    }

    public void drawed_bullet(){
        if(b1 != null) {
            Boot.gc.setFill(Color.BLACK);
            Boot.gc.fillRect(b1.getX(), b1.getY(), bullet_width, bullet_height);
            b1.setY(b1.getY() - bullet_speed);
            Boot.gc.setFill(Color.WHITE);
            Boot.gc.fillRect(b1.getX(), b1.getY(), bullet_width, bullet_height);
        }

        if(b2 != null) {
            Boot.gc.setFill(Color.BLACK);
            Boot.gc.fillRect(b2.getX(), b2.getY(), bullet_width, bullet_height);
            b2.setY(b2.getY() - bullet_speed);
            Boot.gc.setFill(Color.WHITE);
            Boot.gc.fillRect(b2.getX(), b2.getY(), bullet_width, bullet_height);
        }

        if(b3 != null) {
            Boot.gc.setFill(Color.BLACK);
            Boot.gc.fillRect(b3.getX(), b3.getY(), bullet_width, bullet_height);
            b3.setY(b3.getY() - bullet_speed);
            Boot.gc.setFill(Color.WHITE);
            Boot.gc.fillRect(b3.getX(), b3.getY(), bullet_width, bullet_height);
        }

    }

    public void erase_bullet(Bullet bullet){
        Boot.gc.setFill(Color.BLACK);
        Boot.gc.fillRect(bullet.getX(), bullet.getY(),
                bullet_width, bullet_height);
        if(bullet.getNumber() == 1){
            b1 = null;
        }

        if(bullet.getNumber() == 2){
            b2 = null;
        }

        if(bullet.getNumber() == 3){
            b3 = null;
        }
        bullet_counter--;
    }


    public void first_draw_bullet(){
        if(!(bullet_counter >= 3)){
            Boot.gc.setFill(Color.WHITE);
            if(b1 == null){
                b1 = new Bullet(shooter.getX(), shooter.getY()-bullet_height, (byte)1);
                Boot.gc.fillRect(shooter.getX(), shooter.getY()-bullet_height, bullet_width, bullet_height);
                bullet_counter++;
                BasicAPI.write_log(String.valueOf(b1==null));
                return;
            }
            if(b2 == null){
                b2 = new Bullet(shooter.getX(), shooter.getY()-bullet_height, (byte)2);
                Boot.gc.fillRect(shooter.getX(), shooter.getY()-bullet_height, bullet_width, bullet_height);
                bullet_counter++;
                BasicAPI.write_log("b2");
                return;
            }
            if(b3 == null){
                b3 = new Bullet(shooter.getX(), shooter.getY()-bullet_height, (byte)3);
                Boot.gc.fillRect(shooter.getX(), shooter.getY()-bullet_height, bullet_width, bullet_height);
                bullet_counter++;
                BasicAPI.write_log("b3");
                return;
            }

        }
    }

    public void first_draw_enemy(double x, double y){
        for(int yp = 0;yp < 3;yp++){
            for(int xp = 0;xp < 5;xp++){
                enemies[xp][yp] = new Enemy(x+(20*xp)+(enemy_space*xp), y + (100*yp));
                Boot.gc.drawImage(enemy_image, enemies[xp][yp].getX(), enemies[xp][yp].getY());
            }
        }
    }

    public void erase_enemy(int xp, int yp){
        if(!(enemies[xp][yp] == null)) {
            Boot.gc.setFill(Color.BLACK);
            Boot.gc.fillRect(enemies[xp][yp].getX(), enemies[xp][yp].getY(), 20, 20);
            enemies[xp][yp] = null;
        }
    }

    public void bullet_enemy(){
        for(int yp = 0;yp < 3;yp++) {
            for (int xp = 0;xp < 5;xp++){
                if(b1 != null) {
                    if (Math.abs(enemies[xp][yp].getX() - b1.getX()) < 20 &&
                            Math.abs(enemies[xp][yp].getY() - b1.getY()) < 2) {
                        erase_enemy(xp, yp);

                        Boot.gc.setFill(Color.BLACK);
                        Boot.gc.fillRect(b1.getX(), b1.getY(),
                                bullet_width, bullet_height);
                        bullet_counter--;
                        b1 = null;

                        return;
                    }
                }

                if(b2 != null) {
                    if (Math.abs(enemies[xp][yp].getX() - b2.getX()) < 20 &&
                            Math.abs(enemies[xp][yp].getY() - b2.getY()) < 2) {
                        erase_enemy(xp, yp);

                        Boot.gc.setFill(Color.BLACK);
                        Boot.gc.fillRect(b2.getX(), b2.getY(),
                                bullet_width, bullet_height);
                        bullet_counter--;
                        b2 = null;

                        return;
                    }
                }

                if(b3 != null) {
                    if (Math.abs(enemies[xp][yp].getX() - b3.getX()) < 20 &&
                            Math.abs(enemies[xp][yp].getY() - b3.getY()) < 2) {
                        erase_enemy(xp, yp);

                        Boot.gc.setFill(Color.BLACK);
                        Boot.gc.fillRect(b3.getX(), b3.getY(),
                                bullet_width, bullet_height);
                        bullet_counter--;
                        b3 = null;

                        return;
                    }
                }
            }
        }
    }


    public void draw_shooter(double x, double y){
        /*
            o  <= this block's right high
           ooo

           right high
         */

        Boot.gc.setFill(Color.WHITE);

        //top block
        Boot.gc.fillRect(x, y, shooter_oneblock, shooter_oneblock);

        //left block
        Boot.gc.fillRect(x-shooter_oneblock, y+shooter_oneblock
                , shooter_oneblock, shooter_oneblock);

        //center block
        Boot.gc.fillRect(x, y+shooter_oneblock,
                shooter_oneblock, shooter_oneblock);

        //right block
        Boot.gc.fillRect(x+shooter_oneblock, y+shooter_oneblock,
                shooter_oneblock, shooter_oneblock);

        shooter.setX(x);
        shooter.setY(y);

    }

    public void erase_shooter(){
        Boot.gc.setFill(Color.BLACK);
        Boot.gc.fillRect(shooter.getX()-shooter_oneblock, shooter.getY(),
                shooter_width, shooter_height);
        System.out.println(shooter.getX() + ":" + shooter.getY());
    }

    public void occ_keybord_typed(String ch){
        if(start) {
            if (ch.equals("a")) {
                a = true;
            } else if (ch.equals("d")) {
                d = true;
            }else if (ch.endsWith(" ")){
                first_draw_bullet();
            }
        }
    }

    public void run() {
        bullet_master.setCycleCount(Timeline.INDEFINITE);
        bullet_master.play();
        mainloop:while (true) {
            if (ProcessMaster.getShutdownOrderState()) {
                kill();
                break;
            }
            if (!start) {
                continue mainloop;
            }
            if(a){
                erase_shooter();
                draw_shooter(shooter.getX()-5, shooter.getY());
            }
            if(d){
                erase_shooter();
                draw_shooter(shooter.getX()+5, shooter.getY());
            }
            a = d = false;
        }

    }
}

class Shooter{

    double x, y;

    public Shooter(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getX(){
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}

class Bullet{

    private double x, y;
    private byte number;
    public Bullet(double x, double y, byte number){
        this.x = x;
        this.y = y;
        this.number = number;
    }

    public double getX(){
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public byte getNumber() {
        return number;
    }

    public void setNumber(byte number) {
        this.number = number;
    }
}

class Enemy{

    double x, y;

    public Enemy(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getX(){
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

}