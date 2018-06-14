package kr.ac.jejunu.portal_front;

import android.app.Application;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by seung-yeol on 2018. 6. 14..
 */

public class CommonData extends Application {
    private String userId;

    public void setUserId(String userId){
        this.userId = userId;
    }
    public String getUserId() {
        return userId;
    }

    public static String getCurrentDay(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return sdf.format(date);
    }
}
