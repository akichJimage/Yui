package kernel;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Created by akichi on 16/03/14.
 */
public class ClockMaster extends MasterProcess{
    int year, month, day, hour, minute, seconde;
    String place;
    ZonedDateTime clock;
    boolean live;

    public ClockMaster(String place){
        this.place = place;
        clock = ZonedDateTime.now(ZoneId.of(this.place));
        live = true;
    }

    public void run(){
        mainloop:while(true) {
            year = clock.getYear();
            month = clock.getMonthValue();
            day = clock.getDayOfMonth();
            hour = clock.getHour();
            minute = clock.getMinute();
            seconde = clock.getSecond();
            try {
                this.sleep(500);
            }catch (InterruptedException e){
                System.out.println(e);
            }
            clock = ZonedDateTime.now(ZoneId.of(place));
            if(ProcessMaster.getShutdownOrderState()){
                kill();
                break;
            }
        }
    }

    public int getYear(){
        return year;
    }

    public int getMonth(){
        return month;
    }

    public int getDay(){
        return day;
    }

    public int getHour(){
        return hour;
    }

    public int getMinute(){
        return minute;
    }

    public int getSeconde(){
        return seconde;
    }

    public boolean isLive(){
        return live;
    }

    public void kill(){
        live = false;
    }
}
