package kr.ac.jejunu.portal_front.task.post;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import kr.ac.jejunu.portal_front.task.OnTaskResultListener;
import kr.ac.jejunu.portal_front.task.post.read.Read;
import kr.ac.jejunu.portal_front.task.post.read.SendJsonRead;
import kr.ac.jejunu.portal_front.vo.DiaryVO;

/**
 * Created by seung-yeol on 2018. 6. 15..
 */

public class WriteDiaryTask extends BasePostTask<Boolean> {
    private JSONObject jsonObject;

    public WriteDiaryTask(OnTaskResultListener<Boolean> onTaskResultListener) {
        super(onTaskResultListener);
    }

    @Override
    String setUrl() {
        return BASE_URL + "/diary/create";
    }

    @Override
    void getPostParam(Object[] param) {
        DiaryVO vo = (DiaryVO) param[0];

        jsonObject = new JSONObject();
        try {
            jsonObject.put("userId", vo.getUserId());
            jsonObject.put("title", vo.getTitle());
            jsonObject.put("story", vo.getStory());
            jsonObject.put("writeDate", vo.getWriteDate());
        } catch (JSONException e) {
            Log.e(this.toString(), "getPostParam:error " + e.toString() );
        }
    }

    @Override
    Read setRead() {
        return new SendJsonRead(jsonObject);
    }

    @Override
    Boolean parse(String responseString) {
        Log.e(this.toString(), "parse: " + responseString );
        return responseString.equals("true");
    }
}
