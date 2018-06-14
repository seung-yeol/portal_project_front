package kr.ac.jejunu.portal_front.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import kr.ac.jejunu.portal_front.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initBtn();
    }

    private void initBtn() {
        Button btnWriteDiary = findViewById(R.id.btn_write_diary);
        Button btnReadAnonymity = findViewById(R.id.btn_read_anonymity);
        Button btnReadMy = findViewById(R.id.btn_read_my);

        btnWriteDiary.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, WriteDiaryActivity.class);
            startActivity(intent);
        });

        btnReadAnonymity.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ReadAnonymityActivity.class);
            startActivity(intent);
        });

        btnReadMy.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MyDiaryListActivity.class);
            startActivity(intent);
        });
    }
}
