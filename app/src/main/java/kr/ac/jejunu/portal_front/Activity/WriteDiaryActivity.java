package kr.ac.jejunu.portal_front.Activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import kr.ac.jejunu.portal_front.CommonData;
import kr.ac.jejunu.portal_front.R;
import kr.ac.jejunu.portal_front.task.OnTaskResultListener;
import kr.ac.jejunu.portal_front.task.post.WriteDiaryTask;
import kr.ac.jejunu.portal_front.vo.DiaryVO;

public class WriteDiaryActivity extends AppCompatActivity implements OnTaskResultListener<Boolean>{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_diary);

        initView();
    }

    private void initView() {
        EditText editTitle = findViewById(R.id.edit_title);
        EditText editStory = findViewById(R.id.edit_contents);

        final String userId = ((CommonData)getApplication()).getUserId();

        Button btnWrite = findViewById(R.id.btn_write);
        btnWrite.setOnClickListener(v -> {
            DiaryVO vo = new DiaryVO();
            vo.setUserId(userId);
            vo.setTitle(editTitle.getText().toString());
            vo.setStory(editStory.getText().toString());
            vo.setWriteDate(getCurrentDay());

            WriteDiaryTask task = new WriteDiaryTask(this);
            task.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, vo);
        });
    }

    private String getCurrentDay(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return sdf.format(date);
    }

    @Override
    public void onTaskResult(Boolean result) {
        if (result){
            Toast.makeText(this, "일기작성을 완료하셨습니다", Toast.LENGTH_SHORT).show();
            finish();
        }
        else {
            Toast.makeText(this, "일기작성을 실패하였습니다", Toast.LENGTH_SHORT).show();
        }
    }
}
