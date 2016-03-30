package YuiWS.WindowDesign;

import java.util.ArrayList;
import java.util.Deque;

/**
 * Created by akichi on 16/03/17.
 */
public class akichWSFunctionCode {
    public static final short SETLABEL = 0;

    public void setLabel(Deque<Object> FunctionInfo){
        switch ((short)(FunctionInfo.removeFirst())) {
            case SETLABEL:
                BasicAPI.setLabel((Label) (FunctionInfo.removeFirst()), (Point) (FunctionInfo.removeFirst()));
                break;
            default:
                break;
        }
    }
}
