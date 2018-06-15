package kr.ac.jejunu.portal_front.task.get;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import kr.ac.jejunu.portal_front.task.OnTaskResultListener;
import kr.ac.jejunu.portal_front.vo.DiaryVO;

/**
 * Created by seung-yeol on 2018. 6. 15..
 */

public class GetDiaryByIdTask extends BaseGetTask<List<DiaryVO>> {
    public GetDiaryByIdTask(OnTaskResultListener onTaskResultListener) {
        super(onTaskResultListener);
    }

    @Override
    String url(String[] params) {
        StringBuilder s = new StringBuilder();
        String str= "";

        if (params[0].length() != 0){
            for (String param : params) {
                s.append(param).append("z");
            }
            str  = s.toString();
            str = str.substring(0, str.length()-1);
        }


        return BASE_URL + "/diary/read/"+ str;
    }

    @Override
    List<DiaryVO> parse(String responseString) {
        Log.e(this.toString(), "parse: " + responseString );

        List<DiaryVO> vos = new LinkedList<>();

        try {
            JSONArray resultArray = new JSONArray(responseString);
            for (int i = 0; i < resultArray.length(); i++) {
                DiaryVO vo = new DiaryVO();
                JSONObject object = resultArray.getJSONObject(i);
                vo.setId(object.has("id") ? object.getInt("id") : null);
                vo.setUserId(object.has("userId") ? object.getString("userId") : null);
                vo.setTitle(object.has("title") ? object.getString("title") : null);
                vo.setStory(object.has("story") ? object.getString("story") : null);
                vo.setWriteDate(object.has("writeDate") ? object.getString("writeDate") : null);

                vos.add(vo);

            }
        } catch (JSONException e) {
            Log.e(this.toString(), "parse:error " +  e.toString());
        }

        return vos;
    }
}
