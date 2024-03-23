package adegas.fago.helpers;

import java.util.Date;

public class TimestampHelper {
    public static boolean IsTimestampIsExpired(long timestamp){
        try {
            if(timestamp > (new Date().getTime()/1000)){
                return false;
            }
        } catch (Exception ignored){
            return false;
        }
        return true;
    }
}
