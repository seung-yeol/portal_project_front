package kr.ac.jejunu.portal_front;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class JoinMemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_mem);

        initView();
    }

    private void initView() {
        EditText editId = findViewById(R.id.edit_id);
        EditText editPassword = findViewById(R.id.edit_password);
        EditText editPasswordRe = findViewById(R.id.edit_password_re);
        EditText editName = findViewById(R.id.edit_name);
        EditText editBirthday = findViewById(R.id.edit_birthday);
        EditText editPhoneNum = findViewById(R.id.edit_phone_num);
        Button btnJoinMem = findViewById(R.id.btn_join_mem);
    }
}
