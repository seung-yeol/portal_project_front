package kr.ac.jejunu.portal_front.task.post;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedList;
import java.util.List;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import kr.ac.jejunu.portal_front.task.OnTaskResultListener;

/**
 * Created by seung-yeol on 2018. 6. 14..
 */

public class JoinMemberTask extends BasePostTask<Boolean> {
    private String userId;
    private String password;
    private String userName;
    private String birthday;
    private String phoneNum;
    private String gender;


    public JoinMemberTask(OnTaskResultListener onTaskResultListener) {
        super(onTaskResultListener);
    }

    @Override
    String url() {
        return BASE_URL + "join_mem";
    }

    @Override
    void setPostParam(String[] param) {
        userId = param[0];
        password = param[1];
        userName = param[2];
        birthday = param[3];
        phoneNum = param[4];
        gender = param[5];
    }

    @Override
    List<NameValuePair> setNameValues() throws UnsupportedEncodingException {
        List<NameValuePair> nameValues = new LinkedList<>();

        nameValues.add(new BasicNameValuePair(
                "userId", URLDecoder.decode(userId, "UTF-8")));
        nameValues.add(new BasicNameValuePair(
                "password", URLDecoder.decode(password, "UTF-8")));
        nameValues.add(new BasicNameValuePair(
                "userName", URLDecoder.decode(userName, "UTF-8")));
        nameValues.add(new BasicNameValuePair(
                "birthday", URLDecoder.decode(birthday, "UTF-8")));
        nameValues.add(new BasicNameValuePair(
                "phoneNum", URLDecoder.decode(phoneNum, "UTF-8")));
        nameValues.add(new BasicNameValuePair(
                "gender", URLDecoder.decode(gender, "UTF-8")));


        return nameValues;
    }

    @Override
    Boolean parse(String responseString) {
        return responseString.equals("true");
    }
}
