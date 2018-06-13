package kr.ac.jejunu.portal_front;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import kr.ac.jejunu.portal_front.task.LoginCheckTask;
import kr.ac.jejunu.portal_front.task.OnTaskResultListener;

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
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginCheckTask loginCheckTask = new LoginCheckTask(new OnTaskResultListener<Boolean>() {
                    @Override
                    public void onTaskResult(Boolean result) {
                        if (result){
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호를 잘못 입력하셨습니다 ", Toast.LENGTH_LONG);
                        }
                    }
                });

                loginCheckTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,
                        editId.getText().toString(), editPassword.getText().toString());
            }
        });
    }
}
