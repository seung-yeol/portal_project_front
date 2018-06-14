package kr.ac.jejunu.portal_front.task.post.read;

import android.util.Log;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by seung-yeol on 2018. 6. 14..
 */

public class SendJsonRead implements Read {
    private final JSONObject jsonObject;

    public SendJsonRead(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Override
    public String read(String url) {
        InputStream is = null;
        String result = "";

        try {
            URL urlCon = new URL(url);
            HttpURLConnection httpCon = (HttpURLConnection) urlCon.openConnection();

            String json = "";
            json = jsonObject.toString();

            // Set some headers to inform server about the type of the comment
            httpCon.setRequestProperty("Accept", "application/json");
            httpCon.setRequestProperty("Content-type", "application/json");

            // OutputStream으로 POST 데이터를 넘겨주겠다는 옵션.
            httpCon.setDoOutput(true);
            // InputStream으로 서버로 부터 응답을 받겠다는 옵션.
            httpCon.setDoInput(true);
            OutputStream os = httpCon.getOutputStream();

            os.write(json.getBytes("utf-8"));

            os.flush();

            try {
                is = httpCon.getInputStream();
                if (is != null) {
                    result = convertInputStreamToString(is);

                } else {
                    result = "Did not work!";
                }
            } catch (IOException e) {
                Log.e("IOException", e.toString());
            } finally {
                httpCon.disconnect();
            }
        } catch (IOException e) {
            Log.e("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    private String convertInputStreamToString(InputStream is) {
        byte[] byteData = null;

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] byteBuffer = new byte[1024];
            int nLength = 0;

            while ((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                byteArrayOutputStream.write(byteBuffer, 0, nLength);
            }

            byteData = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            Log.e("IOException", e.getLocalizedMessage());
        }
        return new String(byteData);
    }
}
