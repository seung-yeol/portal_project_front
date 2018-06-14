package kr.ac.jejunu.portal_front.task.post;

/**
 * Created by seung-yeol on 2018. 6. 13..
 */

import android.os.AsyncTask;
import android.util.Log;

import kr.ac.jejunu.portal_front.task.OnTaskResultListener;

public abstract class BasePostTask<E> extends AsyncTask<Object, Void, E> {
    private OnTaskResultListener<E> onTaskResultListener;
    private Read read;

    final String BASE_URL = "http://117.17.102.230:8080";

    BasePostTask(OnTaskResultListener<E> onTaskResultListener){
        this.onTaskResultListener = onTaskResultListener;

    }

    @Override
    protected E doInBackground(Object[] params) {
        String urlStr = setUrl();
        getPostParam(params);
        Log.e(this.toString(), "URL :  " + urlStr);

        read = setRead();
        String response = read.read(urlStr);

        return parse(response);
    }
    abstract String setUrl();
    abstract void getPostParam(Object[] param);
    abstract Read setRead();

    abstract E parse(String responseString);

    @Override
    protected void onPostExecute(E resultList) {
        onTaskResultListener.onTaskResult(resultList);
    }
}