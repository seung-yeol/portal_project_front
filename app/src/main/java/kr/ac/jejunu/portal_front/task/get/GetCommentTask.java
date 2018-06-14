package kr.ac.jejunu.portal_front.task.get;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import kr.ac.jejunu.portal_front.task.OnTaskResultListener;
import kr.ac.jejunu.portal_front.vo.CommentVO;
import kr.ac.jejunu.portal_front.vo.DiaryVO;

/**
 * Created by seung-yeol on 2018. 6. 15..
 */

public class GetCommentTask extends BaseGetTask<List<CommentVO>> {
    public GetCommentTask(OnTaskResultListener onTaskResultListener) {
        super(onTaskResultListener);
    }

    @Override
    String url(String[] params) {
        return BASE_URL + "/comment/read/" + params[0] + "/" + params[1];
    }

    @Override
    List<CommentVO> parse(String responseString) {
        Log.e(this.toString(), "parse: " + responseString );

        List<CommentVO> commentVOS = new LinkedList<>();

        try {
            JSONObject resultObject = new JSONObject(responseString);
            JSONArray resultArray = resultObject.getJSONArray("content");

            for (int i = 0; i < resultArray.length(); i++) {
                CommentVO commentVO = new CommentVO();

                JSONObject itemObject = resultArray.getJSONObject(i);
                commentVO.setUserId(itemObject.has("userId") ? itemObject.getString("userId") : null);
                commentVO.setComment(itemObject.has("comment") ? itemObject.getString("comment") : null);
                commentVO.setWriteDate(itemObject.has("writeDate") ? itemObject.getString("writeDate") : null);

                commentVOS.add(commentVO);
            }
        } catch (JSONException e) {
            Log.e( this.toString(), "parse: " + e.toString() );
        }

        return commentVOS;


    }
}
