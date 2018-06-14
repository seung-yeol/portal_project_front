package kr.ac.jejunu.portal_front.task.get;

import android.util.Log;

import kr.ac.jejunu.portal_front.task.OnTaskResultListener;

/**
 * Created by seung-yeol on 2018. 6. 15..
 */

public class WriteEnableCheckTask extends BaseGetTask<Boolean> {
    public WriteEnableCheckTask(OnTaskResultListener onTaskResultListener) {
        super(onTaskResultListener);
    }

    @Override
    String url(String[] params) {
        return BASE_URL + "/diary/writeEnableCheck/" + params[0] + "/" + params[1];
    }

    @Override
    Boolean parse(String responseString) {
        Log.e(this.toString(), "parse: " + responseString);

        return responseString.equals("true");
    }
}
