package kernel;

import YuiWS.*;
import YuiWS.WindowDesign.BasicAPI;
import YuiWS.WindowDesign.Label;
import YuiWS.WindowDesign.Point;
import application.Table.Application;
import application.Table.Table;
import application.TableTennis.TableTennis;
import application.Terminal.Terminal;
import application.invader.Invader;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.nio.file.Paths;

/**
 * Created by akichi on 16/03/15.
 */
public class Boot extends javafx.application.Application {

    public static GraphicsContext gc;
    public static Canvas canvas;
    public static Group root;

    public static ProcessMaster processMaster;
    public static WindowMasterProcess windowMasterProcess;
    public static GUIMasterProcess guiMasterProcess;
    public static ClockMasterProcess clockMasterProcess;
    public static FileSystem fileSystem;
    public static Table application_table;


    private java.awt.GraphicsEnvironment env = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment();
    private java.awt.DisplayMode displayMode = env.getDefaultScreenDevice().getDisplayMode();

    public static int display_width;
    public static int display_height;
    public static Scene scene;

    public static void main(String[] args){
        javafx.application.Application.launch();
    }

    @Override
    public void start(Stage display) throws Exception{

        display_width = displayMode.getWidth();
        display_height = displayMode.getHeight();

        display.setTitle("Yui");

        display.setWidth(display_width);
        display.setHeight(display_height);

        canvas = new Canvas(display_width, display_height);
        gc = canvas.getGraphicsContext2D();

        root = new Group();
        root.getChildren().addAll(canvas);

        scene = new Scene(root);
        display.setScene(scene);
        display.setFullScreen(true);
        display.show();

        akichOS_init();



        /*
        windowMasterProcess.getWindowMaster().createWindow("FIRST", 200, 200, 500, 500);
        BasicAPI.setLabel(new Label("akichOS >", new Font(20)),
                new Point(windowMasterProcess.getWindowMaster().window_list.get("FIRST").getWindow().getX(),
                        windowMasterProcess.getWindowMaster().window_list.get("FIRST").getWindow().getY()+35));
                        */


    }

    private void akichOS_init(){
        processMaster = new ProcessMaster();
        processMaster.start();
        windowMasterProcess = new WindowMasterProcess();
        application_table = new Table();
        guiMasterProcess = new GUIMasterProcess(Color.BLACK);
        clockMasterProcess = new ClockMasterProcess("Asia/Tokyo");
        fileSystem = new FileSystem();
        fileSystem.start();


        scene.setOnKeyPressed(event1 -> KeybordTracker.keybord_typed(event1));

        root.setOnMouseClicked(event -> MouseTracker.Cliked(event));
        root.setOnMouseReleased(event -> MouseTracker.MouseRelease());
        root.setOnMouseDragged(event -> MouseTracker.MouseDragged(event));


        //WALLPAPER
        gc.drawImage(new Image(Paths.get("root/sys/wallpaper.png").toUri().toString()), 0, 0);


        //ICONS
        Application terminal = new Application(
                new Image(Paths.get("root/sys/terminal.png").toUri().toString()), "Terminal", () -> {
            Boot.windowMasterProcess.getWindowMaster().createWindow("FIRST",
                    new WindowProcess(new Terminal(200.0, 200.0, 500.0, 485.0)));
        });

        Application table_tennis = new Application(
                new Image(Paths.get("root/sys/tabletennis.png").toUri().toString()), "Table_Tennis", () ->{
            Boot.windowMasterProcess.getWindowMaster().createWindow("tabletennis",
                    new WindowProcess(new TableTennis(200.0, 200.0, 900.0, 650.0)));
        });

        Application invader = new Application(
                new Image(Paths.get("root/sys/invader.png").toUri().toString()), "Invader", () ->{
            Boot.windowMasterProcess.getWindowMaster().createWindow("invader",
                    new WindowProcess(new Invader(200.0, 200.0, 650.0, 650.0)));
        });

        application_table.addImage(terminal, table_tennis, invader);
        windowMasterProcess.getWindowMaster().createWindow("application_table",
                new WindowProcess(application_table));

    }

    public static void shutdown(){
        Platform.exit();
    }
}
