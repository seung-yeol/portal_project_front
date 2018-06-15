package kr.ac.jejunu.portal_front;

import android.app.Application;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import kr.ac.jejunu.portal_front.vo.DiaryVO;

/**
 * Created by seung-yeol on 2018. 6. 14..
 */

public class CommonData extends Application {
    private String userId;
    private SharedPreferences preferences;

    @Override
    public void onCreate() {
        super.onCreate();

        preferences = getSharedPreferences("my", MODE_PRIVATE);
    }

    public void setUserId(String userId){
        this.userId = userId;
    }
    public String getUserId() {
        return userId;
    }

    public void setOthersDiaryList(List<DiaryVO> vos, String date){
        Set<String> ids = new HashSet<>();
        for (DiaryVO vo : vos ) {
            ids.add(String.valueOf(vo.getId()));
        }

        preferences.edit().putStringSet("ids", ids).apply();
        preferences.edit().putString(userId, date).apply();
    }

    public List<String> getOtherDiaryIdList(String date){
        Set<String> ids = preferences.getStringSet("ids", null);
        String preDate = preferences.getString(userId, "");

        Iterator<String> it = ids.iterator();
        List<String> idsList = new LinkedList<>();
        while (it.hasNext()){
            idsList.add(it.next());
        }

        if (preDate.equals(date)){
            Ascending ascending = new Ascending();
            Collections.sort(idsList, ascending);
            return idsList;
        }
        else return new ArrayList<>();
    }

    public boolean dateCheck(String date){
        String preDate = preferences.getString("date", "");
        return preDate.equals(date);
    }

    public static String getCurrentDay(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return sdf.format(date);
    }

    class Ascending implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            Integer first = Integer.parseInt(o1);
            Integer second = Integer.parseInt(o2);

            return first.compareTo(second);
        }

    }
}
