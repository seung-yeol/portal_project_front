package kr.ac.jejunu.portal_front.task;

/**
 * Created by seung-yeol on 2018. 6. 13..
 */

import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.protocol.HTTP;
import cz.msebera.android.httpclient.util.EntityUtils;

public abstract class BasePostTask<E> extends AsyncTask<String, Void, E> {
    private OnTaskResultListener<E> onTaskResultListener;

    BasePostTask(OnTaskResultListener onTaskResultListener){
        this.onTaskResultListener = onTaskResultListener;
    }

    @Override
    protected E doInBackground(String[] params) {
        String urlStr = url();
        setPostParam(params);
        Log.e(this.toString(), "URL :  " + urlStr);

        String response = read(urlStr);

        return parse(response);
    }

    abstract String url();
    abstract void setPostParam(String[] param);

    private String read(String urlStr){
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(urlStr);
        ArrayList<NameValuePair> nameValues =
                new ArrayList<>();

        try {
            //Post방식으로 넘길 값들을 각각 지정을 해주어야 한다.
            setNameValues(nameValues);

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
            Log.i("Insert Log", "response.getStatusCode:" + response.getStatusLine().getStatusCode());

            return EntityUtils.toString(response.getEntity(), HTTP.UTF_8);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    abstract ArrayList<NameValuePair> setNameValues(ArrayList<NameValuePair> nameValues) throws UnsupportedEncodingException;

    abstract E parse(String responseString);

    @Override
    protected void onPostExecute(E resultList) {
        onTaskResultListener.onTaskResult(resultList);
    }
}