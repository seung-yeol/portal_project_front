package kr.ac.jejunu.portal_front;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import kr.ac.jejunu.portal_front.task.OnTaskResultListener;
import kr.ac.jejunu.portal_front.task.post.JoinMemberTask;

public class JoinMemActivity extends AppCompatActivity {

    private String gender;

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
        RadioGroup radioGroup = findViewById(R.id.radio_group);

        Button btnJoinMem = findViewById(R.id.btn_join_mem);

        btnJoinMem.setOnClickListener(v -> {
            switch (radioGroup.getCheckedRadioButtonId()) {
                case R.id.radio_man:
                    gender = "man";
                    break;
                case R.id.radio_woman:
                    gender = "woman";
                    break;
                default:
                    gender = null;
            }

            if (gender != null && editPassword.getText().toString().equals(editPasswordRe.getText().toString())) {
                JoinMemberTask joinMemberTask = new JoinMemberTask(result -> {
                    if ((Boolean) result) {
                        finish();
                    } else {
                        Toast.makeText(JoinMemActivity.this,
                                "중복되는 아이디 또는 패스워드와 재입력이 잘못 작성되었습니다", Toast.LENGTH_LONG).show();
                    }
                });
                joinMemberTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,
                        editId.getText().toString(),
                        editPassword.getText().toString(),
                        editName.getText().toString(),
                        editBirthday.getText().toString(),
                        editPhoneNum.getText().toString(),
                        gender);
            }
        });
    }
}
