package kr.ac.jejunu.portal_front.task;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

/**
 * Created by seung-yeol on 2018. 6. 13..
 */

public class LoginCheckTask extends BasePostTask<LoginVO> {
    private String userId;
    private String password;

    LoginCheckTask(OnTaskResultListener onTaskResultListener) {
        super(onTaskResultListener);
        this.userId = userId;
        this.password = password;
    }

    @Override
    String url() {
        return "http://192.168.1.101/login";
    }

    @Override
    void setPostParam(String[] param) {
        userId = param[0];
        password = param[1];
    }

    @Override
    ArrayList<NameValuePair> setNameValues(ArrayList<NameValuePair> nameValues) throws UnsupportedEncodingException {
        nameValues.add(new BasicNameValuePair(
                "userId", URLDecoder.decode(userId, "UTF-8")));
        nameValues.add(new BasicNameValuePair(
                "password", URLDecoder.decode(password, "UTF-8")));

        return nameValues;
    }

    @Override
    LoginVO parse(String responseString) {
        return null;
    }
}
