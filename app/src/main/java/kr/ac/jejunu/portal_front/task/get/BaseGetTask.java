package kr.ac.jejunu.portal_front.task.get;

/**
 * Created by seung-yeol on 2018. 6. 13..
 */

import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import kr.ac.jejunu.portal_front.task.OnTaskResultListener;

public abstract class BaseGetTask<E> extends AsyncTask<String, Void, E> {
    private OnTaskResultListener<E> onTaskResultListener;

    BaseGetTask(OnTaskResultListener onTaskResultListener){
        this.onTaskResultListener = onTaskResultListener;
    }

    @Override
    protected E doInBackground(String[] params) {
        String urlStr = url(params);
        Log.e(this.toString(), "URL :  " + urlStr);

        String response = read(urlStr);

        return parse(response);
    }

    abstract String url(String[] params);

    private String read(String urlStr){
        byte [ ] byteData = null;

        try {
            URL url = new URL(urlStr);

            URLConnection conn = url.openConnection ( );
            conn.setUseCaches ( false );
            conn.connect();
            InputStream is = conn.getInputStream ( );

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream ( );
            byte [ ] byteBuffer = new byte [ 1024 ];
            int nLength = 0;

            while ( ( nLength = is.read ( byteBuffer , 0 , byteBuffer.length ) ) != -1 ){
                byteArrayOutputStream.write ( byteBuffer , 0 , nLength );
            }

            byteData = byteArrayOutputStream.toByteArray ( );


        } catch (IOException e) {
            Log.e(this.toString(), " 아이오익셉션 " + e.getMessage());
        }

        if ( byteData == null || byteData.length <= 0 ){
            return null;
        }

        return new String(byteData);
    }

    abstract E parse(String responseString);

    @Override
    protected void onPostExecute(E resultList) {
        onTaskResultListener.onTaskResult(resultList);
    }
}