package kr.ac.jejunu.portal_front.task.post;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import kr.ac.jejunu.portal_front.task.OnTaskResultListener;
import kr.ac.jejunu.portal_front.task.post.read.Read;
import kr.ac.jejunu.portal_front.task.post.read.SendJsonRead;
import kr.ac.jejunu.portal_front.vo.UserVO;

/**
 * Created by seung-yeol on 2018. 6. 14..
 */

public class JoinMemberTask extends BasePostTask<Boolean> {
    private JSONObject jsonObject;
    private String str;

    public JoinMemberTask(OnTaskResultListener<Boolean> onTaskResultListener) {
        super(onTaskResultListener);
    }

    @Override
    Read setRead() {
        return new SendJsonRead(jsonObject);
    }

    @Override
    String setUrl() {
        return BASE_URL + "/user/join_mem";
    }

    @Override
    void getPostParam(Object[] param) {
        UserVO vo = (UserVO) param[0];

        str = vo.toString();

        jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("userId", vo.getUserId());
            jsonObject.accumulate("password", vo.getPassword());
            jsonObject.accumulate("name", vo.getUserName());
            jsonObject.accumulate("birthday", vo.getBirthday());
            jsonObject.accumulate("phoneNo", vo.getPhoneNo());
            jsonObject.accumulate("gender", vo.getGender());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    Boolean parse(String responseString) {
        Log.e(this.toString(), "parse: " + responseString );
        return responseString.equals("true");
    }
}
