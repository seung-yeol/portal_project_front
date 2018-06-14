package kr.ac.jejunu.portal_front.task.post.read;

import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.protocol.HTTP;
import cz.msebera.android.httpclient.util.EntityUtils;

/**
 * Created by seung-yeol on 2018. 6. 14..
 */

public class SendParameterRead implements Read {
    private final List<NameValuePair> nameValues;

    public SendParameterRead(List<NameValuePair> nameValues){
        this.nameValues = new ArrayList<>(nameValues);
    }

    @Override
    public String read(String urlStr){
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(urlStr);

        try {
            //Post방식으로 넘길 값들을 각각 지정을 해주어야 한다.

            post.addHeader("Content-Type", "application/x-www-form-urlencoded");
            //HttpPost에 넘길 값을들 Set해주기
            post.setEntity(
                    new UrlEncodedFormEntity(
                            nameValues, "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Log.e("Insert Log", ex.toString());
        }

        try {
            //설정한 URL을 실행시키기
            HttpResponse response = client.execute(post);
            //통신 값을 받은 Log 생성. (200이 나오는지 확인할 것~) 200이 나오면 통신이 잘 되었다는 뜻!
            Log.e("Insert Log", "response.getStatusCode:" + response.getStatusLine().getStatusCode());

            return EntityUtils.toString(response.getEntity(), HTTP.UTF_8);

        } catch (IOException e) {
            Log.e(this.toString(), "read:error " + e.toString() );
        }

        return null;
    }
}
