package kr.ac.jejunu.portal_front.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import kr.ac.jejunu.portal_front.CommonData;
import kr.ac.jejunu.portal_front.R;
import kr.ac.jejunu.portal_front.task.get.WriteEnableCheckTask;

public class MainActivity extends AppCompatActivity {
    private boolean writable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initBtn();
        executeTask();
    }

    private void initBtn() {
        Button btnWriteDiary = findViewById(R.id.btn_write_diary);
        Button btnReadAnonymity = findViewById(R.id.btn_read_anonymity);
        Button btnReadMy = findViewById(R.id.btn_read_my);

        btnWriteDiary.setOnClickListener(v -> {
            if (writable) {
                Intent intent = new Intent(MainActivity.this, WriteDiaryActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "이미 오늘 일기를 작성하셨습니다.", Toast.LENGTH_SHORT).show();
            }

        });

        btnReadAnonymity.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, OthersDiaryListActivity.class);
            startActivity(intent);
        });

        btnReadMy.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MyDiaryListActivity.class);
            startActivity(intent);
        });
    }

    private void executeTask() {
        WriteEnableCheckTask task = new WriteEnableCheckTask(result -> {
            writable = (Boolean) result;
        });

        task.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,
                ((CommonData) getApplication()).getUserId(), CommonData.getCurrentDay());
    }
}
