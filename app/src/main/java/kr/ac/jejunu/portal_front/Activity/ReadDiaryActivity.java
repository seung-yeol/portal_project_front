package kr.ac.jejunu.portal_front.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import kr.ac.jejunu.portal_front.R;

public class ReadDiaryActivity extends AppCompatActivity {
    private String title;
    private String story;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_diary);

        title = getIntent().getStringExtra("title");
        story = getIntent().getStringExtra("story");

        initView();

    }

    private void initView() {
        TextView txtTitle = findViewById(R.id.txt_title);
        TextView txtStory = findViewById(R.id.txt_story);

        txtTitle.setText(title);
        txtStory.setText(story);
    }
}
