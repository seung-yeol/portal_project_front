package kr.ac.jejunu.portal_front.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import kr.ac.jejunu.portal_front.R;
import kr.ac.jejunu.portal_front.task.post.JoinMemberTask;
import kr.ac.jejunu.portal_front.task.post.LoginCheckTask;

public class LoginActivity extends AppCompatActivity {

    private EditText editId;
    private EditText editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        editId = findViewById(R.id.edit_id);
        editPassword = findViewById(R.id.edit_password);

        Button btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(v -> {
            LoginCheckTask loginCheckTask = new LoginCheckTask(result -> {
                if ((Boolean) result){
                    Toast.makeText(LoginActivity.this,
                            "로그인! ", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(LoginActivity.this,
                            "아이디 또는 비밀번호를 잘못 입력하셨습니다 ", Toast.LENGTH_LONG).show();
                }
            });

            loginCheckTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,
                    editId.getText().toString(), editPassword.getText().toString());
        });

        Button btnJoinMem = findViewById(R.id.btn_join_mem);
        btnJoinMem.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, JoinMemActivity.class);
            startActivity(intent);
        });
    }
}
