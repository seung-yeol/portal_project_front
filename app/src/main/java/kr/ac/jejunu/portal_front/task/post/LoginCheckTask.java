package kr.ac.jejunu.portal_front.task.post;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedList;
import java.util.List;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import kr.ac.jejunu.portal_front.task.OnTaskResultListener;
import kr.ac.jejunu.portal_front.task.post.read.Read;
import kr.ac.jejunu.portal_front.task.post.read.SendParameterRead;

/**
 * Created by seung-yeol on 2018. 6. 13..
 */

public class LoginCheckTask extends BasePostTask<Boolean> {
    private String userId;
    private String password;

    public LoginCheckTask(OnTaskResultListener<Boolean> onTaskResultListener) {
        super(onTaskResultListener);
    }

    @Override
    String setUrl() {
        return BASE_URL + "/user/login";
    }

    @Override
    void getPostParam(Object[] param) {
        userId = (String)param[0];
        password = (String)param[1];
    }

    @Override
    Read setRead() {
        List<NameValuePair> nameValues = new LinkedList<>();

        try {
            nameValues.add(new BasicNameValuePair(
                    "userId", URLDecoder.decode(userId, "UTF-8")));
            nameValues.add(new BasicNameValuePair(
                    "password", URLDecoder.decode(password, "UTF-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return new SendParameterRead(nameValues);
    }

    @Override
    Boolean parse(String responseString) {
        Log.e(this.toString(), "parse: " + responseString );

        return responseString.equals("true");
    }
}
