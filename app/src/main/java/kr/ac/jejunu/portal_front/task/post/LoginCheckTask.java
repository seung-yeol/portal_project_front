package kr.ac.jejunu.portal_front.task.post;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedList;
import java.util.List;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import kr.ac.jejunu.portal_front.task.OnTaskResultListener;

/**
 * Created by seung-yeol on 2018. 6. 13..
 */

public class LoginCheckTask extends BasePostTask<Boolean> {
    private String userId;
    private String password;

    public LoginCheckTask(OnTaskResultListener onTaskResultListener) {
        super(onTaskResultListener);
    }

    @Override
    String url() {
        return BASE_URL + "user/login";
    }

    @Override
    void setPostParam(String[] param) {
        userId = param[0];
        password = param[1];
    }

    @Override
    List<NameValuePair> setNameValues() throws UnsupportedEncodingException {
        List<NameValuePair> nameValues = new LinkedList<>();

        nameValues.add(new BasicNameValuePair(
                "userId", URLDecoder.decode(userId, "UTF-8")));
        nameValues.add(new BasicNameValuePair(
                "password", URLDecoder.decode(password, "UTF-8")));

        return nameValues;
    }

    @Override
    Boolean parse(String responseString) {
        Log.e(this.toString(), "parse: " + responseString );

        return responseString.equals("true");
    }
}
