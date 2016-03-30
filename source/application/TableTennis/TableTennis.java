package application.TableTennis;

import YuiWS.Window;
import YuiWS.WindowDesign.BasicAPI;
import YuiWS.WindowDesign.Button;
import YuiWS.WindowDesign.Label;
import YuiWS.WindowDesign.Point;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.util.Duration;
import kernel.Boot;
import kernel.ProcessMaster;

/**
 * Created by ぴろ on 2016/03/25.
 */
public class TableTennis extends Window {

    private int racketX, racketY;

    private Ball ping;
    private Racket player, com;
    private boolean start;
    private boolean q, a, p, l, serve, isdeuce, com_st;
    private byte player_score, player_game, com_score, com_game;
    private double ping_defaultX, ping_defaultY;
    int player_racket_defaultX, player_racket_defaultY,
            com_racket_defaultX, com_racket_defaultY;
    private int deuce_point;
    private double com_speed;

    public TableTennis(double x, double y, double width, double height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        ping_defaultX = (x+width-40);
        ping_defaultY = (y+(height/2));
        ping = new Ball(ping_defaultX, ping_defaultY, 20, 20);

        player_racket_defaultX = (int)(x+width-20);
        player_racket_defaultY = (int)(y+(height/2));
        player = new Racket(player_racket_defaultX, player_racket_defaultY);

        com_racket_defaultX = (int)(x+20);
        com_racket_defaultY = (int)(y+(height/2));
        com = new Racket(com_racket_defaultX, com_racket_defaultY);

        racketX = 10;
        racketY = 60;
        start = false;
        q = a = p = l = serve = isdeuce = com_st =  false;
        player_score = player_game = com_score = com_game = 0;
        deuce_point = 0;
        com_speed = 1.8;
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
        Boot.gc.setFill(Color.BLACK);
        Boot.gc.fillRect(this.x, this.y+20, width, height-20);
        Boot.gc.setStroke(Color.BLACK);
        Boot.gc.setLineWidth(0.5);
        Boot.gc.strokeLine(x, y, x, y+height);
        Boot.gc.strokeLine(x+width, y, x+width, y+height);
        Boot.gc.strokeLine(x, y+height, x+width, y+height);

        BasicAPI.setLabel(new Label("Table Tennis", new Font(70)), new Point(x+60, y+160));
        BasicAPI.setButton(new Button("START", new Font(14)), new Point(x+215, y+365), new Point(x+285, y+390));
        BasicAPI.setButton(new Button(" EXIT", new Font(14)), new Point(x+215, y+400), new Point(x+285, y+425));

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

        ping_defaultX = (x+width-40);
        ping_defaultY = (y+(height/2));
        ping = new Ball(ping_defaultX, ping_defaultY, 20, 20);

        player_racket_defaultX = (int)(x+width-20);
        player_racket_defaultY = (int)(y+(height/2));
        player = new Racket(player_racket_defaultX, player_racket_defaultY);

        com_racket_defaultX = (int)(x+20);
        com_racket_defaultY = (int)(y+(height/2));
        com = new Racket(com_racket_defaultX, com_racket_defaultY);

        q = a = p = l = serve = false;
        RacketsReset();
        reset();
    }

    public void RacketsReset(){
        Boot.gc.setFill(Color.BLACK);
        Boot.gc.fillRect(this.x, this.y+20, width, height-20);
        Boot.gc.setFill(Color.WHITE);
        Boot.gc.fillArc(ping.getX(), ping.getY(), ping.getWidth(), ping.getHeight(), 0, 360, ArcType.ROUND);
        Boot.gc.fillRect(player.getX(), player.getY(), racketX, racketY);
        Boot.gc.fillRect(com.getX(), com.getY(), racketX, racketY);
        print_score();
    }

    public void print_score(){
        if(player_score == 10+deuce_point && com_score == 10+deuce_point){
            isdeuce = true;
        }
        if(isdeuce){
            if(player_score-com_score == 2){
                com_score = 0;
                player_score = 0;
                player_game++;
                isdeuce = false;
            }else if(player_score-com_score == -2){
                com_score = 0;
                player_score = 0;
                com_game++;
                isdeuce = false;
            }
        }else {
            if (com_score == 11) {
                com_score = 0;
                player_score = 0;
                com_game++;
            }
            if (player_score == 11) {
                com_score = 0;
                player_score = 0;
                player_game++;
            }
        }

        if(player_score - com_score > 4 && !com_st){
            com_st = true;
            com_speed = 4;
        }
        if(player_score-com_score == 0 && com_st){
            com_st = false;
            com_speed = 1.8;
        }

        BasicAPI.setLabel(new Label(com_game+":"+com_score+" x "+player_score+":"+player_game, new Font(20)),
                new Point(x+width/2-50, y+height-50));
    }

    public void occ_mouse_clickedevent(double x, double y){
        if(this.x <= x && this.x+20 >= x && this.y <= y && this.y+20 >= y){
            ping_master.stop();
            eraseWindow();
            kill();
        }else if(x >= this.x+215 && x <= this.x+285 && y >= this.y+365 && y <= this.y+390){
            /////////////////////START BUTTON PUSHED
            start = true;
            reset();
        }else if(x >= this.x+215 && x <= this.x+285 && y >= this.y+400 && y <= this.y+425){
            /////////////////////CANCEL BUTTON PUSHED
            ping_master.stop();
            eraseWindow();
            kill();
        }
    }

    public void reset(){
        reset_default_value();
        Boot.gc.setFill(Color.BLACK);
        Boot.gc.fillRect(this.x, this.y+20, width, height-20);
        Boot.gc.setFill(Color.WHITE);
        Boot.gc.fillArc(ping.getX(), ping.getY(), ping.getWidth(), ping.getHeight(), 0, 360, ArcType.ROUND);
        Boot.gc.fillRect(player.getX(), player.getY(), racketX, racketY);
        Boot.gc.fillRect(com.getX(), com.getY(), racketX, racketY);
        print_score();
        ping.setGoX(-4);
        ping.setGoY(0);
        serve = false;
    }

    public void reset_default_value(){
        ping.setX(ping_defaultX);
        ping.setY(ping_defaultY);
        player.setX(player_racket_defaultX);
        player.setY(player_racket_defaultY);
        com.setX(com_racket_defaultX);
        com.setY(com_racket_defaultY);
    }

    public void occ_keybord_typed(String ch){
        if(start) {
            if (ch.equals("a")) {
                q = true;
            } else if (ch.equals("d")) {
                a = true;
            } else if (ch.equals("p")) {
                p = true;
            } else if (ch.equals("l")) {
                l = true;
            }else if(ch.equals(" ")){
                serve = true;
            }
        }
    }

    Timeline ping_master = new Timeline(new KeyFrame(Duration.millis(16),
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if(start && serve) {
                        Boot.gc.setFill(Color.BLACK);
                        ping.setX(ping.getX() + ping.getGoX());
                        ping.setY(ping.getY() + ping.getGoY());

                        //上下当たり判定
                        if(ping.getY() <= y+22 || ping.getY() >= y+height-23){
                            ping.setGoY(-1*ping.getGoY());
                        }

                        //COMラケット当たり判定
                        if (ping.getX() <= com.getX()+racketX && ping.getY() >= com.getY()
                                && ping.getY() <= com.getY()+racketY) {
                            ping.setGoX(-1.1*ping.getGoX());
                            ping.setGoY(0.1*(ping.getY()-(com.getY()+(racketY/2))));
                        }

                        //自ラケット当たり判定
                        if (ping.getX()+20 > player.getX() && ping.getY() >= player.getY()-10 &&
                                ping.getY() <= player.getY() + racketY) {
                            if((ping.getY()-(player.getY()+(racketY/2)-2)) > 0){
                                ping.setGoX(-2*ping.getGoX());
                                ping.setGoY(0.04*(ping.getY()-(player.getY()+(racketY/2)-2)));
                            }else {
                                ping.setGoX(-1 * ping.getGoX());
                                ping.setGoY(0.09 * (ping.getY() - (player.getY() + (racketY / 2) - 2)));
                            }
                        }

                        //COMポイント判定
                        if (ping.getX() < x) {
                            player_score++;
                            reset();
                        }

                        //自ポイント判定
                        if (ping.getX() > x+width-racketX-9) {
                            com_score++;
                            reset();
                        }

                        if((com.getY()+(racketY/2))-ping.getY() > 0.0 && com.getY() > y+20 &&
                                Math.abs((com.getY()-(racketY/2))-ping.getY()) < racketY){
                            Boot.gc.setFill(Color.BLACK);
                            Boot.gc.fillRect(com.getX(), com.getY(), racketX, racketY);
                            Boot.gc.setFill(Color.WHITE);
                            com.setY(com.getY() -com_speed);
                            Boot.gc.fillRect(com.getX(), com.getY(), racketX, racketY);
                        }else if((com.getY()+(racketY/2))-ping.getY() < racketX && com.getY() < y+height &&
                                Math.abs((com.getY()-(racketY/2))-ping.getY()) > racketY){
                            Boot.gc.setFill(Color.BLACK);
                            Boot.gc.fillRect(com.getX(), com.getY(), racketX, racketY);
                            Boot.gc.setFill(Color.WHITE);
                            com.setY(com.getY() + com_speed);
                            Boot.gc.fillRect(com.getX(), com.getY(), racketX, racketY);
                        }



                        RacketsReset();
                    }
                }
            }));

    public void run(){
        ping_master.setCycleCount(Timeline.INDEFINITE);
        ping_master.play();
        mainloop:while(true) {
            if (ProcessMaster.getShutdownOrderState()){
                kill();
                break;
            }
            if(!start && !serve){
                continue mainloop;
            }
            if(q){
                if(player.getY() >= y+27) {
                    Boot.gc.setFill(Color.BLACK);
                    Boot.gc.fillRect(player.getX(), player.getY()-2, racketX, racketY+2);
                    Boot.gc.setFill(Color.WHITE);
                    player.setY(player.getY() - 6.5);
                    Boot.gc.fillRect(player.getX(), player.getY(), racketX, racketY);
                }
            }
            if(a){
                if(player.getY()+racketY+7 < y+height) {
                    Boot.gc.setFill(Color.BLACK);
                    Boot.gc.fillRect(player.getX(), player.getY()-2, racketX, racketY+2);
                    Boot.gc.setFill(Color.WHITE);
                    player.setY(player.getY() + 6.5);
                    Boot.gc.fillRect(player.getX(), player.getY(), racketX, racketY);
                }
            }


            //for 2players code
            /*
            if(p){
                Boot.gc.setFill(Color.BLACK);
                Boot.gc.fillRect(com.getX(), com.getY(), racketX, racketY);
                Boot.gc.setFill(Color.WHITE);
                com.setY(com.getY() - 6);
                Boot.gc.fillRect(com.getX(), com.getY(), racketX, racketY);
            }
            if(l){
                Boot.gc.setFill(Color.BLACK);
                Boot.gc.fillRect(com.getX(), com.getY(), racketX, racketY);
                Boot.gc.setFill(Color.WHITE);
                com.setY(com.getY() + 6);
                Boot.gc.fillRect(com.getX(), com.getY(), racketX, racketY);
            }
            */




            q = a = p = l = false;
        }
    }
}

class Ball{
    double x, y, width, height, goX, goY;

    public Ball(double x, double y, double width, double height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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

    public void setX(double x){
        this.x = x;
    }

    public  void setY(double y){
        this.y = y;
    }

    public void setGoX(double goX){
        this.goX = goX;
    }

    public void setGoY(double goY){
        this.goY = goY;
    }

    public double getGoX(){
        return goX;
    }

    public double getGoY(){
        return goY;
    }
}

class Racket{
    double x, y;

    public Racket(int x, int y){
        this.x = x;
        this.y = y;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public void setX(double x){
        this.x = x;
    }

    public void setY(double y){
        this.y = y;
    }

}
