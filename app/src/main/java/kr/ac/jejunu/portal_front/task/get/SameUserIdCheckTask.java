package kr.ac.jejunu.portal_front.task.get;

import android.util.Log;

import kr.ac.jejunu.portal_front.task.OnTaskResultListener;

/**
 * Created by seung-yeol on 2018. 6. 15..
 */

public class SameUserIdCheckTask extends BaseGetTask<Boolean> {
    public SameUserIdCheckTask(OnTaskResultListener onTaskResultListener) {
        super(onTaskResultListener);
    }

    @Override
    String url(String[] params) {
        return BASE_URL + "/user/same/"+params[0];
    }

    @Override
    Boolean parse(String responseString) {
        Log.e(this.toString(), "parse: " +responseString );
        return responseString.equals("true");
    }
}
