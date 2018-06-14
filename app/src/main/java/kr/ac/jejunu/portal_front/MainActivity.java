package kr.ac.jejunu.portal_front;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

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

        });
    }
}
