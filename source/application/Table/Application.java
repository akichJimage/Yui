package application.Table;

import javafx.scene.image.Image;

/**
 * Created by ぴろ on 2016/03/27.
 */
public class Application {

    private double x, y;
    private Image icon;
    private String application_name;
    public Runnable app;

    public Application(Image icon, String application_name, Runnable app){
        this.icon = icon;
        this.application_name = application_name;
        this.app = app;
    }

    public Image getIcon() {
        return icon;
    }

    public String getApplication_name() {
        return application_name;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }

    public void setApplication_name(String application_name) {
        this.application_name = application_name;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
