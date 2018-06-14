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
 * Created by seung-yeol on 2018. 6. 14..
 */

public class ReadMyDiaryTask extends BaseGetTask<List<DiaryVO>> {
    public ReadMyDiaryTask(OnTaskResultListener onTaskResultListener) {
        super(onTaskResultListener);
    }

    @Override
    String url(String[] params) {
        return BASE_URL + "/diary/read/my/"+params[0]+"/"+params[1];
    }

    @Override
    List<DiaryVO> parse(String responseString) {
        Log.e(this.toString(), "parse: " + responseString );

        List<DiaryVO> diaryVOS = new LinkedList<>();

        try {
            JSONObject resultObject = new JSONObject(responseString);
            JSONArray resultArray = resultObject.getJSONArray("content");

            for (int i = 0; i < resultArray.length(); i++) {
                DiaryVO diaryVO = new DiaryVO();

                JSONObject itemObject = resultArray.getJSONObject(i);
                diaryVO.setId(itemObject.has("id") ? itemObject.getInt("id") : null);
                diaryVO.setTitle(itemObject.has("title") ? itemObject.getString("title") : null);
                diaryVO.setStory(itemObject.has("story") ? itemObject.getString("story") : null);
                diaryVO.setWriteDate(itemObject.has("writeDate") ? itemObject.getString("writeDate") : null);

                diaryVOS.add(diaryVO);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return diaryVOS;
    }
}