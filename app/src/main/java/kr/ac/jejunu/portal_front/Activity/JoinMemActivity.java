package kr.ac.jejunu.portal_front.Activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import kr.ac.jejunu.portal_front.R;
import kr.ac.jejunu.portal_front.task.OnTaskResultListener;
import kr.ac.jejunu.portal_front.task.get.SameUserIdCheckTask;
import kr.ac.jejunu.portal_front.task.post.JoinMemberTask;
import kr.ac.jejunu.portal_front.vo.UserVO;

public class JoinMemActivity extends AppCompatActivity {
    private boolean idSameCheck = false;
    private String gender;
    private String checkedId = "";

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
        Button btnSameCheck = findViewById(R.id.btn_same_check);

        btnJoinMem.setOnClickListener(v -> {
            switch (radioGroup.getCheckedRadioButtonId()) {
                case R.id.radio_man:
                    gender = "남";
                    break;
                case R.id.radio_woman:
                    gender = "여";
                    break;
                default:
                    gender = null;
            }

            if (editId.getText() == null) {
                Toast.makeText(this, "아이디를 작성주세요", Toast.LENGTH_SHORT).show();
            } else if (!idSameCheck || !checkedId.equals(editId.getText().toString())) {
                Toast.makeText(this, "아이디 중복확인을 해주세요", Toast.LENGTH_SHORT).show();
            } else if (editPassword.getText() == null) {
                Toast.makeText(this, "비밀번호를 작성주세요", Toast.LENGTH_SHORT).show();
            } else if (editPasswordRe.getText() == null) {
                Toast.makeText(this, "비밀번호 재입력을 작성주세요", Toast.LENGTH_SHORT).show();
            } else if (gender == null) {
                Toast.makeText(this, "성별을 선택해주세요", Toast.LENGTH_SHORT).show();
            } else if (editName.getText() == null) {
                Toast.makeText(this, "이름을 작성주세요", Toast.LENGTH_SHORT).show();
            } else if (editPhoneNum.getText() == null) {
                Toast.makeText(this, "휴대폰번호를 작성주세요", Toast.LENGTH_SHORT).show();
            } else if (!editPassword.getText().toString().equals(editPasswordRe.getText().toString())) {
                Toast.makeText(this, "패스워드 재입력이 잘못 작성되었습니다", Toast.LENGTH_LONG).show();
            } else {
                UserVO vo = new UserVO();
                vo.setUserId(editId.getText().toString());
                vo.setPassword(editPassword.getText().toString());
                vo.setUserName(editName.getText().toString());
                vo.setBirthday(editBirthday.getText().toString());
                vo.setPhoneNo(editPhoneNum.getText().toString());
                vo.setGender(gender);

                JoinMemberTask joinMemberTask = new JoinMemberTask(result -> {
                    if (result) {
                        Toast.makeText(JoinMemActivity.this,
                                "회원가입을 완료하셨습니다", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(JoinMemActivity.this,
                                "회원가입에 실패하였습니다", Toast.LENGTH_LONG).show();
                    }
                });
                joinMemberTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, vo);
            }
        });
        btnSameCheck.setOnClickListener(v -> {
            SameUserIdCheckTask task = new SameUserIdCheckTask(result -> {
                if ((Boolean) result) {
                    Toast.makeText(JoinMemActivity.this, "사용가능한 아이디입니다", Toast.LENGTH_SHORT).show();
                    checkedId = editId.getText().toString();
                    idSameCheck = true;
                }
                else{
                    Toast.makeText(JoinMemActivity.this, "사용 불가능한 아이디입니다", Toast.LENGTH_SHORT).show();
                    idSameCheck = false;
                }
            });
            task.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, editId.getText().toString());
        });
    }
}
