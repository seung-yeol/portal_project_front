package kr.ac.jejunu.portal_front.Activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kr.ac.jejunu.portal_front.CommonData;
import kr.ac.jejunu.portal_front.R;
import kr.ac.jejunu.portal_front.task.OnTaskResultListener;
import kr.ac.jejunu.portal_front.task.get.GetCommentTask;
import kr.ac.jejunu.portal_front.task.post.WriteCommentTask;
import kr.ac.jejunu.portal_front.vo.CommentVO;

public class ReadOthersDiaryActivity extends AppCompatActivity implements OnTaskResultListener<Boolean>{
    private String title;
    private String story;
    private String userId;
    private int diaryId;
    private int page = 0;
    private EditText editComment;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_others_diary);

        title = getIntent().getStringExtra("title");
        story = getIntent().getStringExtra("story");
        diaryId = getIntent().getIntExtra("diaryId", -1);

        userId = ((CommonData)getApplication()).getUserId();

        initView();
        initRecyclerView();

        executeTask();
    }

    private void executeTask() {
        GetCommentTask getCommentTask = new GetCommentTask(result -> {
            adapter.addVOs((List<CommentVO>) result);
        });
        getCommentTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,
                String.valueOf(diaryId), String.valueOf(page));
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_comment);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(getApplicationContext(),new LinearLayoutManager(this).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void initView() {
        TextView txtTitle = findViewById(R.id.txt_title);
        TextView txtStory = findViewById(R.id.txt_story);

        editComment = findViewById(R.id.edit_comment);

        txtTitle.setText(title);
        txtStory.setText(story);

        Button btnWriteComment = findViewById(R.id.btn_write_comment);
        btnWriteComment.setOnClickListener(v -> {
            CommentVO vo = new CommentVO();
            vo.setUserId(userId);
            vo.setDiaryId(diaryId);
            vo.setComment(editComment.getText().toString());
            vo.setWriteDate(CommonData.getCurrentDay());

            WriteCommentTask task = new WriteCommentTask(this);
            task.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, vo);
        });
    }

    @Override
    public void onTaskResult(Boolean result) {
        if (result){
            editComment.setText("");
            Toast.makeText(this, "댓글작성을 완료하였습니다", Toast.LENGTH_SHORT).show();
            executeTask();
        }
        else {
            Toast.makeText(this, "댓글작성을 실패하였습니다", Toast.LENGTH_SHORT).show();
        }
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        private List<CommentVO> vos;

        MyAdapter() {
            this.vos = new ArrayList<>();
        }

        @NonNull
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);

            return new MyAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
            holder.txtComment.setText(vos.get(position).getComment());
            holder.txtUserId.setText(vos.get(position).getUserId());
        }

        @Override
        public int getItemCount() {
            return vos.size();
        }

        void addVOs(List<CommentVO> vos) {
            this.vos.clear();
            this.vos = new ArrayList<>(vos);
            notifyDataSetChanged();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            private TextView txtComment;
            private TextView txtUserId;

            MyViewHolder(View itemView) {
                super(itemView);

                txtComment = itemView.findViewById(R.id.txt_comment);
                txtUserId = itemView.findViewById(R.id.txt_user_id);
            }
        }
    }
}
