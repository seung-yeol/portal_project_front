package kr.ac.jejunu.portal_front;

import android.app.Application;

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
}
